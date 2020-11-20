package com.namics.oss.magnolia.appbuilder.definitionextender;

import com.namics.oss.magnolia.appbuilder.exception.AppBuilderException;
import com.squareup.javapoet.*;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ClassUtils;

import javax.annotation.processing.Generated;
import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.namics.oss.magnolia.appbuilder.definitionextender.DefinitionExtenderConfig.Entry;
import static com.namics.oss.magnolia.appbuilder.definitionextender.DefinitionExtenderConfig.getConfig;

/**
 * Standalone helper to generate classes which extend from
 * existing definition-classes and add builder methods.
 * <p>>
 * This should run once to generate all the builder classes,
 * the generated builder classes should be checked in.
 * If the generated class are not modified, class can be run
 * anytime to update/regenerate the builders.
 * If run again, already generated classes will be overwritten
 * not existing classes will be generated, including not
 * existing packages.
 * <p>
 * To run this in IntelliJ activate "Include dependencies with 'provided'
 * scope" in the run configuration.
 * <p>
 * Code is generated with JavaPoet, for documentation
 * see https://github.com/square/javapoet
 * <p>
 * Configuration in {@link DefinitionExtenderConfig}
 */
public class DefinitionExtender {

	private static final String SET_PREFIX = "set";
	private static final String ADD_PREFIX = "add";

	public static void main(String[] args) {
		List<Entry> config = getConfig();
		new DefinitionExtender().generateExtendedDefinition(config);
	}

	private void generateExtendedDefinition(List<Entry> config) {
		config.forEach(this::extend);
	}

	private void extend(Entry config) {
		// get full inheritance hierarchy of the given class
		List<Class<?>> classHierarchy = getClassHierarchyInTopToBottomOrder(config.getClassToExtend());

		// get all setter methods from the class-hierarchy:
		// - methods must not be static
		// - if a method is overridden the most specific will be collected
		// - methods must start with 'set' or 'add'
		// - methods must exactly one parameter
		List<Method> setter = classHierarchy.stream()
				.flatMap(clazz -> Arrays.stream(clazz.getDeclaredMethods()))
				.filter(method -> !java.lang.reflect.Modifier.isStatic(method.getModifiers()))
				.map(method -> ClassUtils.getMostSpecificMethod(method, config.getClassToExtend()))
				.filter(method -> StringUtils.startsWithAny(method.getName(), SET_PREFIX, ADD_PREFIX))
				.filter(method -> method.getParameters().length == 1)
				.collect(Collectors.toList());

		ClassName newClassName = ClassName.get(config.getTargetPackage(), config.getBuilderClassName());

		// generate normal builder methods
		List<MethodSpec> builderMethodSpecs = setter.stream()
				.map(method -> generateBuilderMethod(method, newClassName))
				.collect(Collectors.toList());

		// generate vararg methods for list setters
		List<MethodSpec> listBuilderMethodSpecs = setter.stream()
				.filter(this::isList)
				.map(method -> generateListBuilderMethod(method, newClassName))
				.collect(Collectors.toList());

		// generate Map.of methods for map setters
		List<MethodSpec> mapBuilderMethodSpecs = setter.stream()
				.filter(this::isMap)
				.flatMap(method -> generateMapBuilderMethod(method, newClassName))
				.collect(Collectors.toList());

		// generate @Generated annotation to mark the source code as generated
		AnnotationSpec generatedAnnotation = AnnotationSpec.builder(Generated.class)
				.addMember("value", "$S", this.getClass().getName())
				.addMember("date", "$S", LocalDateTime.now().toString())
				.build();

		// generate new class
		TypeSpec.Builder typeSpecBuilder = TypeSpec.classBuilder(newClassName)
				.superclass(config.getClassToExtend())
				.addAnnotation(generatedAnnotation)
				.addModifiers(Modifier.PUBLIC)
				.addMethods(builderMethodSpecs)
				.addMethods(listBuilderMethodSpecs)
				.addMethods(mapBuilderMethodSpecs);

		if(config.isDeprecated()) {
			AnnotationSpec deprecatedAnnotation = AnnotationSpec.builder(Deprecated.class)
					.build();
			typeSpecBuilder
					.addJavadoc("@deprecated see {@link $L} for details", config.getClassToExtend().getName())
					.addAnnotation(deprecatedAnnotation);
		}

		TypeSpec builderClass = typeSpecBuilder.build();

		// save new classfile to configured path
		JavaFile javaFile = JavaFile.builder(config.getTargetPackage(), builderClass)
				.indent("\t")
				.build();

		try {
			File saveToPath = new File(config.getTargetSourceRootPath());
			javaFile.writeTo(saveToPath);
		} catch (IOException e) {
			throw AppBuilderException.wrap(e);
		}
	}

	private MethodSpec generateBuilderMethod(Method setter, ClassName className) {
		String methodName = getBuilderMethodName(setter);
		Parameter param = getFirstParameter(setter);
		String paramName = getParamName(param, methodName);
		TypeName paramType = getParamType(param);

		return MethodSpec.methodBuilder(methodName)
				.addParameter(paramType, paramName)
				.addModifiers(Modifier.PUBLIC)
				.addStatement("this.$N($L)", setter.getName(), methodName)
				.addStatement("return this")
				.returns(className)
				.build();
	}

	private MethodSpec generateListBuilderMethod(Method setter, ClassName className) {
		String methodName = getBuilderMethodName(setter);
		Parameter param = getFirstParameter(setter);
		String paramName = getParamName(param, methodName);
		Type[] actualTypeArguments = ((ParameterizedType) param.getParameterizedType()).getActualTypeArguments();
		Type type = Arrays.stream(actualTypeArguments).findFirst().orElseThrow(() -> new AppBuilderException("Optional Item Not Found"));
		Class<?> arrayType = getArrayType(type);

		return MethodSpec.methodBuilder(methodName)
				.addParameter(arrayType, paramName)
				.varargs()
				.addModifiers(Modifier.PUBLIC)
				.addStatement("this.$N($T.asList($L))", setter.getName(), Arrays.class, methodName)
				.addStatement("return this")
				.returns(className)
				.build();
	}

	private Stream<MethodSpec> generateMapBuilderMethod(Method setter, ClassName className) {
		String methodName = getBuilderMethodName(setter);
		Parameter param = getFirstParameter(setter);
		Type[] actualTypeArguments = ((ParameterizedType) param.getParameterizedType()).getActualTypeArguments();
		Type keyType = actualTypeArguments[0];
		Type valueType = actualTypeArguments[1];
		List<MethodSpec> mapMethods = new ArrayList<>();

		int mapParamCount = 25;

		for (int i = 1; i <= mapParamCount; i++) {
			MethodSpec.Builder builder = MethodSpec.methodBuilder(methodName)
					.addModifiers(Modifier.PUBLIC);

			for (int j = i; j >= 1; j--) {
				String key = "key" + (i - j + 1);
				String value = "value" + (i - j + 1);
				builder.addParameter(keyType, key).addParameter(valueType, value);
			}

			String mapName = "paramMap";
			builder.addStatement("Map<$T, $T> $N = new $T<>()", keyType, valueType, mapName, HashMap.class);
			for (int k = i; k >= 1; k--) {
				String key = "key" + (i - k + 1);
				String value = "value" + (i - k + 1);
				builder.addStatement("$N.put($N, $N)", mapName, key, value);
			}
			builder.addStatement("this.$N($N)", setter.getName(), mapName)
					.addStatement("return this")
					.returns(className);

			mapMethods.add(builder.build());
		}
		return mapMethods.stream();
	}

	private Class<?> getArrayType(Type type) {
		try {
			if (type instanceof WildcardType) {
				Type[] upperBounds = ((WildcardType) type).getUpperBounds();
				Type[] lowerBounds = ((WildcardType) type).getLowerBounds();
				if (upperBounds.length > 0) {
					type = upperBounds[0];
				} else if (lowerBounds.length > 0) {
					type = lowerBounds[0];
				}
			}
			return Class.forName("[L" + type.getTypeName() + ";");
		} catch (ClassNotFoundException e) {
			throw AppBuilderException.wrap(e);
		}
	}

	private Parameter getFirstParameter(Method setter) {
		return Arrays.stream(setter.getParameters()).findFirst().orElseThrow(() -> new AppBuilderException("Optional Item Not Found"));
	}

	private TypeName getParamType(Parameter param) {
		try {
			Class<?> type = param.getType();
			if (shouldResolveGenerics(type)) {
				Type[] actualTypeArguments = ((ParameterizedType) param.getParameterizedType()).getActualTypeArguments();
				return ParameterizedTypeName.get(type, actualTypeArguments);
			}
			if (type.isMemberClass() || type.isPrimitive()) {
				return TypeName.get(type);
			}
			Class<?> aClass = Class.forName(type.getTypeName());
			String packageName = aClass.getPackage().getName();
			String simpleName = aClass.getSimpleName();
			return ClassName.get(packageName, simpleName);
		} catch (ClassNotFoundException e) {
			throw AppBuilderException.wrap(e);
		}
	}

	private boolean shouldResolveGenerics(Class<?> type) {
		return Collection.class.isAssignableFrom(type)
				|| Map.class.isAssignableFrom(type)
				|| Class.class.isAssignableFrom(type);
	}

	private String getParamName(Parameter p, String methodName) {
		// use name from source file if present
		// (compile with 'javac -parameters')
		if (p.isNamePresent()) {
			return p.getName();
		}
		// otherwise use same as method name
		return methodName;
	}

	private String getBuilderMethodName(Method setter) {
		String name = RegExUtils.removeAll(setter.getName(), SET_PREFIX + "|" + ADD_PREFIX);
		return StringUtils.uncapitalize(name);
	}

	private boolean isList(Method setter) {
		Parameter param = getFirstParameter(setter);
		Class<?> type = param.getType();
		return Collection.class.isAssignableFrom(type);
	}

	private boolean isMap(Method setter) {
		Parameter param = getFirstParameter(setter);
		Class<?> type = param.getType();
		return Map.class.isAssignableFrom(type);
	}

	private List<Class<?>> getClassHierarchyInTopToBottomOrder(Class<?> aClass) {
		List<Class<?>> hierarchy = new ArrayList<>();
		while (aClass != null) {
			hierarchy.add(aClass);
			aClass = aClass.getSuperclass();
		}
		Collections.reverse(hierarchy);
		return hierarchy;
	}

}
