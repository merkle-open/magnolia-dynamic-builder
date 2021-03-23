package com.namics.oss.magnolia.appbuilder;

import com.namics.oss.magnolia.appbuilder.dropconstraint.AbstractNodeDropConstraint;
import com.namics.oss.magnolia.powernode.PowerNodeService;
import info.magnolia.test.RepositoryTestCase;
import info.magnolia.test.mock.jcr.MockNode;
import info.magnolia.ui.vaadin.integration.jcr.JcrItemAdapter;
import info.magnolia.ui.workbench.tree.drop.DropConstraint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class AbstractNodeDropConstraintTest extends RepositoryTestCase {
	private static final String FOLDER_NODE_TYPE = "mgnl::folder";
	private static final String FILE_NODE_TYPE = "mgnl::file";
	private static final String ROOT_NODE_TYPE = "rep:root";
	private static final String UNKNOWN_NODE_TYPE = "mgnl:unknownType";

	@Override
	@BeforeEach
	public void setUp() throws Exception {
		super.setUp();
	}

	@Test
	void allowedAsChild_sourceNodeTypeFile_targetNodeTypeFolder_shouldReturnTrue() {
		final MockNodeJcrItemAdapter folder = createItem(FOLDER_NODE_TYPE);
		final MockNodeJcrItemAdapter file = createItem(FILE_NODE_TYPE);
		assertTrue(constraint().allowedAsChild(file, folder));
	}

	@Test
	void allowedAsChild_sourceNodeTypeFile_targetNodeTypeRoot_fileNodesAllowedInRoot_shouldReturnTrue() {
		final MockNodeJcrItemAdapter root = createItem(ROOT_NODE_TYPE);
		final MockNodeJcrItemAdapter file = createItem(FILE_NODE_TYPE);
		assertTrue(constraint(false, true).allowedAsChild(file, root));
	}

	@Test
	void allowedAsChild_sourceNodeTypeFile_targetNodeTypeRoot_shouldReturnFalse() {
		final MockNodeJcrItemAdapter root = createItem(ROOT_NODE_TYPE);
		final MockNodeJcrItemAdapter file = createItem(FILE_NODE_TYPE);
		assertFalse(constraint().allowedAsChild(file, root));
	}

	@Test
	void allowedAsChild_sourceNodeTypeFile_targetNodeTypeFile_shouldReturnFalse() {
		final MockNodeJcrItemAdapter file1 = createItem(FILE_NODE_TYPE);
		final MockNodeJcrItemAdapter file2 = createItem(FILE_NODE_TYPE);
		assertFalse(constraint().allowedAsChild(file1, file2));
	}

	@Test
	void allowedAsChild_sourceNodeTypeFolder_targetNodeTypeRoot_shouldReturnTrue() {
		final MockNodeJcrItemAdapter folder = createItem(FOLDER_NODE_TYPE);
		final MockNodeJcrItemAdapter root = createItem(ROOT_NODE_TYPE);
		assertTrue(constraint().allowedAsChild(folder, root));
	}

	@Test
	void allowedAsChild_sourceNodeTypeFolder_targetNodeTypeFolder_nestingAllowed_shouldReturnTrue() {
		final MockNodeJcrItemAdapter folder1 = createItem(FOLDER_NODE_TYPE);
		final MockNodeJcrItemAdapter folder2 = createItem(FOLDER_NODE_TYPE);
		assertTrue(constraint(true, false).allowedAsChild(folder1, folder2));
	}

	@Test
	void allowedAsChild_sourceNodeTypeFolder_targetNodeTypeFolder_shouldReturnFalse() {
		final MockNodeJcrItemAdapter folder1 = createItem(FOLDER_NODE_TYPE);
		final MockNodeJcrItemAdapter folder2 = createItem(FOLDER_NODE_TYPE);
		assertFalse(constraint().allowedAsChild(folder1, folder2));
	}

	@Test
	void allowedAsChild_sourceNodeTypeFolder_targetNodeTypeFile_shouldReturnFalse() {
		final MockNodeJcrItemAdapter folder = createItem(FOLDER_NODE_TYPE);
		final MockNodeJcrItemAdapter file2 = createItem(FILE_NODE_TYPE);
		assertFalse(constraint().allowedAsChild(folder, file2));
	}

	@Test
	void allowedAsChild_sourceNodeTypeUnknown_shouldReturnFalse() {
		final MockNodeJcrItemAdapter folder = createItem(FOLDER_NODE_TYPE);
		final MockNodeJcrItemAdapter unknown = createItem(UNKNOWN_NODE_TYPE);
		assertFalse(constraint().allowedAsChild(unknown, folder));
	}

	@Test
	void allowedAsChild_alreadyChild_shouldReturnFalse() {
		final MockNodeJcrItemAdapter folder = createItem(FOLDER_NODE_TYPE);
		final MockNodeJcrItemAdapter file = createItem(FILE_NODE_TYPE);
		folder.mockNode().setIdentifier("folder");
		file.mockNode().setParent(folder.mockNode());
		assertFalse(constraint().allowedAsChild(file, folder));
	}

	@Test
	void allowedAsChild_sourceEqualsTarget_shouldReturnFalse() {
		final MockNodeJcrItemAdapter folder = createItem(FOLDER_NODE_TYPE);
		folder.mockNode().setIdentifier("folder");
		assertFalse(constraint().allowedAsChild(folder, folder));
	}

	@Test
	void allowedBefore_sourceNodeTypeFile_targetParentNodeTypeFolder_shouldReturnTrue() {
		final MockNodeJcrItemAdapter folder = createItem(FOLDER_NODE_TYPE);
		final MockNodeJcrItemAdapter file1 = createItem(FILE_NODE_TYPE);
		final MockNodeJcrItemAdapter file2 = createItem(FILE_NODE_TYPE);
		file2.mockNode().setParent(folder.mockNode());
		assertTrue(constraint().allowedBefore(file1, file2));
	}

	@Test
	void allowedBefore_sourceNodeTypeFile_targetParentNodeTypeRoot_fileNodesAllowedInRoot_shouldReturnTrue() {
		final MockNodeJcrItemAdapter root = createItem(ROOT_NODE_TYPE);
		final MockNodeJcrItemAdapter file1 = createItem(FILE_NODE_TYPE);
		final MockNodeJcrItemAdapter file2 = createItem(FILE_NODE_TYPE);
		file2.mockNode().setParent(root.mockNode());
		assertTrue(constraint(false, true).allowedBefore(file1, file2));
	}

	@Test
	void allowedBefore_sourceNodeTypeFile_targetParentNodeTypeRoot_shouldReturnFalse() {
		final MockNodeJcrItemAdapter root = createItem(ROOT_NODE_TYPE);
		final MockNodeJcrItemAdapter file1 = createItem(FILE_NODE_TYPE);
		final MockNodeJcrItemAdapter file2 = createItem(FILE_NODE_TYPE);
		file2.mockNode().setParent(root.mockNode());
		assertFalse(constraint().allowedBefore(file1, file2));
	}

	@Test
	void allowedBefore_sourceNodeTypeFolder_targetParentNodeTypeRoot_shouldReturnTrue() {
		final MockNodeJcrItemAdapter root = createItem(ROOT_NODE_TYPE);
		final MockNodeJcrItemAdapter folder1 = createItem(FOLDER_NODE_TYPE);
		final MockNodeJcrItemAdapter folder2 = createItem(FOLDER_NODE_TYPE);
		folder2.mockNode().setParent(root.mockNode());
		assertTrue(constraint().allowedBefore(folder1, folder2));
	}

	@Test
	void allowedBefore_sourceNodeTypeFolder_targetParentNodeTypeFolder_nestingAllowed_shouldReturnTrue() {
		final MockNodeJcrItemAdapter parentFolder = createItem(FOLDER_NODE_TYPE);
		final MockNodeJcrItemAdapter folder1 = createItem(FOLDER_NODE_TYPE);
		final MockNodeJcrItemAdapter folder2 = createItem(FOLDER_NODE_TYPE);
		folder2.mockNode().setParent(parentFolder.mockNode());
		assertTrue(constraint(true, false).allowedBefore(folder1, folder2));
	}

	@Test
	void allowedBefore_sourceNodeTypeFolder_targetParentNodeTypeFolder_shouldReturnFalse() {
		final MockNodeJcrItemAdapter parentFolder = createItem(FOLDER_NODE_TYPE);
		final MockNodeJcrItemAdapter folder1 = createItem(FOLDER_NODE_TYPE);
		final MockNodeJcrItemAdapter folder2 = createItem(FOLDER_NODE_TYPE);
		folder2.mockNode().setParent(parentFolder.mockNode());
		assertFalse(constraint().allowedBefore(folder1, folder2));
	}

	@Test
	void allowedBefore_sourceNodeTypeUnknown_shouldReturnFalse() {
		final MockNodeJcrItemAdapter unknown = createItem(UNKNOWN_NODE_TYPE);
		final MockNodeJcrItemAdapter file = createItem(FILE_NODE_TYPE);
		assertFalse(constraint().allowedBefore(unknown, file));
	}

	@Test
	void allowedAfter_shouldDelegateToAllowedBefore() {
		final MockNodeJcrItemAdapter file1 = createItem(FILE_NODE_TYPE);
		final MockNodeJcrItemAdapter file2 = createItem(FILE_NODE_TYPE);
		final DropConstraint constraint = constraint();
		constraint.allowedAfter(file1, file2);
		verify(constraint, times(1)).allowedBefore(file1, file2);
	}

	@Test
	void allowedToMove_nodeTypeFile_shouldReturnTrue() {
		final MockNodeJcrItemAdapter file = createItem(FILE_NODE_TYPE);
		assertTrue(constraint().allowedToMove(file));
	}

	@Test
	void allowedToMove_nodeTypeFolder_shouldReturnTrue() {
		final MockNodeJcrItemAdapter file = createItem(FOLDER_NODE_TYPE);
		assertTrue(constraint().allowedToMove(file));
	}

	@Test
	void allowedToMove_nodeTypeUnknown_shouldReturnFalse() {
		final MockNodeJcrItemAdapter unknown = createItem(UNKNOWN_NODE_TYPE);
		assertFalse(constraint().allowedToMove(unknown));
	}

	private MockNodeJcrItemAdapter createItem(final String nodeType) {
		final MockNode node = new MockNode("test", nodeType);
		node.setParent(new MockNode()); //root node
		MockNodeJcrItemAdapter item = Mockito.mock(MockNodeJcrItemAdapter.class);
		Mockito.doReturn(node).when(item).getJcrItem();
		Mockito.doReturn(true).when(item).isNode();
		Mockito.doReturn(node).when(item).mockNode();
		return item;
	}

	private interface MockNodeJcrItemAdapter extends JcrItemAdapter {
		MockNode mockNode();
	}

	private DropConstraint constraint() {
		return constraint(false, false);
	}

	private DropConstraint constraint(final boolean nestingAllowed, final boolean fileNodesAllowedInRoot) {
		return spy(new AbstractNodeDropConstraint(
				new PowerNodeService(),
				FOLDER_NODE_TYPE,
				Set.of(FILE_NODE_TYPE),
				nestingAllowed,
				fileNodesAllowedInRoot
		) {});
	}
}