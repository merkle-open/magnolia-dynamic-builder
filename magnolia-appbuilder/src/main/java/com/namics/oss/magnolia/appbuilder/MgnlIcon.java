package com.namics.oss.magnolia.appbuilder;


/**
 * Icon list from https://documentation.magnolia-cms.com/display/DOCS57/Icons.
 * updated: 2019-01-15
 * <p>
 * How to generate this class:
 * - Copy icon names from Magnolia website into IntelliJ scratch file
 * - One icon name per line
 * - Run search replace with regex two times:
 * <p>
 * First run:
 * Search for: (icon-(.*))
 * Replace with: public static final String \U$2\E = "$1";
 * <p>
 * Second run (with 'Match Case'):
 * Search for: (?<![a-z])-(?![a-z])
 * Replace with: _
 *
 * @deprecated use info.magnolia.icons.MagnoliaIcons
 */
@Deprecated
public final class MgnlIcon {

	public static final String APPSLAUNCHER = "icon-appslauncher";
	public static final String PULSE = "icon-pulse";
	public static final String FAVORITES = "icon-favorites";
	public static final String RSSFEED = "icon-rssfeed";
	public static final String WEBPAGES_APP = "icon-webpages-app";
	public static final String ASSETS_APP = "icon-assets-app";
	public static final String DOCUMENTS_APP = "icon-documents-app";
	public static final String NEWS = "icon-news";
	public static final String PEOPLE = "icon-people";
	public static final String COMPANIES = "icon-companies";
	public static final String ITEMS = "icon-items";
	public static final String FILES = "icon-files";
	public static final String CONTENTS = "icon-contents";
	public static final String CONTENT_APP = "icon-content-app";
	public static final String CONFIGURATION_APP = "icon-configuration-app";
	public static final String SECURITY_APP = "icon-security-app";
	public static final String JCR_APP = "icon-jcr-app";
	public static final String LOGGING_APP = "icon-logging-app";
	public static final String AUDIT_APP = "icon-audit-app";
	public static final String IMPORT_EXPORT_APP = "icon-import-export-app";
	public static final String LANGUAGE_APP = "icon-language-app";
	public static final String PACKAGER_APP = "icon-packager-app";
	public static final String BACKUP_APP = "icon-backup-app";
	public static final String FOLDER = "icon-folder";
	public static final String FILE = "icon-file";
	public static final String FILE_TEXT = "icon-file-text";
	public static final String FILE_WORD = "icon-file-word";
	public static final String FILE_EXCEL = "icon-file-excel";
	public static final String FILE_POWERPOINT = "icon-file-powerpoint";
	public static final String FILE_PDF = "icon-file-pdf";
	public static final String FILE_WEBPAGE = "icon-file-webpage";
	public static final String FILE_IMAGE = "icon-file-image";
	public static final String FILE_VIDEO = "icon-file-video";
	public static final String FILE_AUDIO = "icon-file-audio";
	public static final String CONTENT_ITEM = "icon-content-item";
	public static final String WORK_ITEM = "icon-work-item";
	public static final String USER_ME = "icon-user-me";
	public static final String USER_ANYONE = "icon-user-anyone";
	public static final String USER_MAGNOLIA = "icon-user-magnolia";
	public static final String USER_SYSTEM = "icon-user-system";
	public static final String USER_PUBLIC = "icon-user-public";
	public static final String USER_GROUP = "icon-user-group";
	public static final String USER_ROLE = "icon-user-role";
	public static final String NODE_FOLDER = "icon-node-folder";
	public static final String NODE_CONTENT = "icon-node-content";
	public static final String NODE_DATA = "icon-node-data";
	public static final String ACTION = "icon-action";
	public static final String SWITCH_PREVIEW = "icon-switch-preview";
	public static final String SWITCH_EDIT = "icon-switch-edit";
	public static final String SWITCH_REVIEW = "icon-switch-review";
	public static final String OPEN_FULLSCREEN = "icon-open-fullscreen";
	public static final String CLOSE_FULLSCREEN = "icon-close-fullscreen";
	public static final String SHOW_CHANGES = "icon-show-changes";
	public static final String HIDE_CHANGES = "icon-hide-changes";
	public static final String ADD_FILE = "icon-add-file";
	public static final String ADD_FOLDER = "icon-add-folder";
	public static final String DELETE = "icon-delete";
	public static final String EDIT = "icon-edit";
	public static final String MOVE = "icon-move";
	public static final String VIEW = "icon-view";
	public static final String COPY = "icon-copy";
	public static final String PASTE = "icon-paste";
	public static final String DUPLICATE = "icon-duplicate";
	public static final String PUBLISH = "icon-publish";
	public static final String PUBLISH_INCL_SUB = "icon-publish-incl-sub";
	public static final String UNPUBLISH = "icon-unpublish";
	public static final String MARK = "icon-mark";
	public static final String UNMARK = "icon-unmark";
	public static final String SHARE = "icon-share";
	public static final String ADD_NODE_CONTENT = "icon-add-node-content";
	public static final String ADD_NODE_DATA = "icon-add-node-data";
	public static final String UNDO = "icon-undo";
	public static final String REDO = "icon-redo";
	public static final String ADD_FAV = "icon-add-fav";
	public static final String REMOVE_FAV = "icon-remove-fav";
	public static final String IMPORT = "icon-import";
	public static final String EXPORT = "icon-export";
	public static final String UPLOAD = "icon-upload";
	public static final String DOWNLOAD = "icon-download";
	public static final String OPEN_NEW_WINDOW = "icon-open-new-window";
	public static final String VIEW_TREE = "icon-view-tree";
	public static final String VIEW_LIST = "icon-view-list";
	public static final String VIEW_THUMBNAILS = "icon-view-thumbnails";
	public static final String SEARCH = "icon-search";
	public static final String ARROW1_E = "icon-arrow1_e";
	public static final String ARROW1_N = "icon-arrow1_n";
	public static final String ARROW1_S = "icon-arrow1_s";
	public static final String ARROW1_W = "icon-arrow1_w";
	public static final String SLIDER_HANDLE = "icon-slider-handle";
	public static final String SLIDER_MAX = "icon-slider-max";
	public static final String SLIDER_MIN = "icon-slider-min";
	public static final String SLIDER_RAIL = "icon-slider-rail";
	public static final String ARROW2_E = "icon-arrow2_e";
	public static final String ARROW2_N = "icon-arrow2_n";
	public static final String ARROW2_S = "icon-arrow2_s";
	public static final String ARROW2_W = "icon-arrow2_w";
	public static final String CONFIRM_TICK = "icon-confirm-tick";
	public static final String CONFIRM = "icon-confirm";
	public static final String ERROR_MARK = "icon-error-mark";
	public static final String ERROR = "icon-error";
	public static final String WARNING_MARK = "icon-warning-mark";
	public static final String WARNING = "icon-warning";
	public static final String INFO_MARK = "icon-info_mark";
	public static final String INFO = "icon-info";
	public static final String HELP_MARK = "icon-help-mark";
	public static final String HELP = "icon-help";
	public static final String CHECKBOX_OFF = "icon-checkbox_off";
	public static final String CHECKBOX_ON = "icon-checkbox_on";
	public static final String CHECKBOX_TICK = "icon-checkbox-tick";
	public static final String RADIO_DOT = "icon-radio-dot";
	public static final String RADIO_OFF = "icon-radio-off";
	public static final String RADIO_ON = "icon-radio-on";
	public static final String DATEPICKER = "icon-datepicker";
	public static final String TRASH = "icon-trash";
	public static final String WIZARD_STEP = "icon-wizard-step";
	public static final String OPEN_FULLSCREEN_2 = "icon-open-fullscreen-2";
	public static final String CLOSE_FULLSCREEN_2 = "icon-close-fullscreen-2";
	public static final String SPINNER_1 = "icon-spinner-1";
	public static final String SPINNER_2 = "icon-spinner-2";
	public static final String SPINNER_3 = "icon-spinner-3";
	public static final String SPINNER_4 = "icon-spinner-4";
	public static final String SPINNER_5 = "icon-spinner-5";
	public static final String SPINNER_6 = "icon-spinner-6";
	public static final String SPINNER_7 = "icon-spinner-7";
	public static final String SPINNER_8 = "icon-spinner-8";
	public static final String SPINNER_FULL = "icon-spinner-full";
	public static final String NOTIFICATION_BADGE_PLUS = "icon-notification-badge-plus";
	public static final String NOTIFICATION_BADGE = "icon-notification-badge";
	public static final String TICK = "icon-tick";
	public static final String APP = "icon-app";
	public static final String INSTANT_PREVIEW = "icon-instant_preview";
	public static final String DEVELOPMENT_APP = "icon-development-app";
	public static final String ADD_ITEM = "icon-add-item";
	public static final String ARTICLES_APP = "icon-articles-app";
	public static final String FORUMS = "icon-forums";
	public static final String ASSET_POOL = "icon-asset-pool";
	public static final String FOLDER_L = "icon-folder-l";
	public static final String NODE_FOLDER_L = "icon-node-folder-l";
	public static final String EDIT_WO = "icon-edit-wo";
	public static final String CROP_IMAGE = "icon-crop-image";
	public static final String ROTATE_IMAGE_CW = "icon-rotate-image-cw";
	public static final String ROTATE_IMAGE_CCW = "icon-rotate-image-ccw";
	public static final String FLIP_HORIZONTALLY = "icon-flip-horizontally";
	public static final String FLIP_VERTICALLY = "icon-flip-vertically";
	public static final String ZOOM_TO_FIT = "icon-zoom-to-fit";
	public static final String FIT_CANVAS = "icon-fit-canvas";
	public static final String VIEW_IN_ACTUAL_SIZE = "icon-view-in-actual-size";
	public static final String CACHE_APP = "icon-cache-app";
	public static final String SERVER_CONFIG_INFO = "icon-server-config-info";
	public static final String RESERVE = "icon-reserve";
	public static final String STATUS_GREEN = "icon-status-green";
	public static final String STATUS_ORANGE = "icon-status-orange";
	public static final String STATUS_RED = "icon-status-red";
	public static final String CATEGORIES = "icon-categories";
	public static final String TAG = "icon-tag";
	public static final String TAGS = "icon-tags";
	public static final String DELETE_SEARCH = "icon-delete-search";
	public static final String SELECT = "icon-select";
	public static final String SHOW_VERSIONS = "icon-show-versions";
	public static final String COMPARE_VERSIONS = "icon-compare-versions";
	public static final String RETRIEVE_VERSIONS = "icon-retrieve-versions";
	public static final String READ_ONLY = "icon-read-only";
	public static final String LOCK = "icon-lock";
	public static final String UNLOCK = "icon-unlock";
	public static final String FORUMS_APP = "icon-forums-app";
	public static final String FORUMS_THREAD = "icon-forums-thread";
	public static final String FORUMS_ARTICLE = "icon-forums-article";
	public static final String MESSAGE_APP = "icon-message-app";
	public static final String MESSAGE = "icon-message";
	public static final String MAIL_SETTING = "icon-mail-setting";
	public static final String CONTENT_TRANSLATION_APP = "icon-content-translation-app";
	public static final String ERROR_L = "icon-error-l";
	public static final String WARNING_L = "icon-warning-l";
	public static final String INFO_L = "icon-info-l";
	public static final String HELP_L = "icon-help-l";
	public static final String SEGMENTS_APP = "icon-segments-app";
	public static final String SEGMENTATION = "icon-segmentation";
	public static final String ADD_SEGMENTATION = "icon-add-segmentation";
	public static final String SEGMENT = "icon-segment";
	public static final String ADD_SEGMENT = "icon-add-segment";
	public static final String PERSONAS_APP = "icon-personas-app";
	public static final String PERSONA = "icon-persona";
	public static final String ADD_PERSONA = "icon-add-persona";
	public static final String PREVIEW_APP = "icon-preview-app";
	public static final String HAS_VARIANTS = "icon-has-variants";
	public static final String CREATE_VARIANTS = "icon-create-variants";
	public static final String CHOOSE_AUDIENCE = "icon-choose-audience";
	public static final String TAG_2_APP = "icon-tag-2-app";
	public static final String TAG_2 = "icon-tag-2";
	public static final String ADD_TAG_2 = "icon-add-tag-2";
	public static final String TARGET_APP = "icon-target-app";
	public static final String TARGET = "icon-target";
	public static final String ADD_TARGET = "icon-add-target";
	public static final String SITEMAPS_APP = "icon-sitemaps-app";
	public static final String SITEMAPS = "icon-sitemaps";
	public static final String ADD_SITEMAPS = "icon-add-sitemaps";
	public static final String COLLAPSE_HEADER = "icon-collapse-header";
	public static final String EXTEND_HEADER = "icon-extend-header";
	public static final String EXTERNAL_WEBPAGE = "icon-external-webpage";
	public static final String ARROW_LINK = "icon-arrow-link";
	public static final String PLAY_BUTTON = "icon-play-button";
	public static final String STORY_BUTTON = "icon-story-button";
	public static final String I_BEACON = "icon-i-beacon";
	public static final String LINK = "icon-link";
	public static final String LINK_PAGE = "icon-link-page";
	public static final String LINK_DOCUMENT = "icon-link-document";
	public static final String LINK_ASSETS = "icon-link-assets";
	public static final String LINK_IMAGE = "icon-link-image";
	public static final String UNLINK = "icon-unlink";
	public static final String SHAPE_CIRCLE_PLUS = "icon-shape-circle-plus";
	public static final String SHAPE_CIRCLE = "icon-shape-circle";
	public static final String SHAPE_TRIANGLE_PLUS = "icon-shape-triangle-plus";
	public static final String SHAPE_TRIANGLE = "icon-shape-triangle";
	public static final String OPEN_NODE = "icon-open-node";
	public static final String CLOSE_NODE = "icon-close-node";
	public static final String RESOURCE_FILES_APP = "icon-resource-files-app";
	public static final String CONFIGURATION_FILES_APP = "icon-configuration-files-app";
	public static final String WEB_RESOURCES_APP = "icon-web-resources-app";
	public static final String RFT_TEMPLATE = "icon-rft-template";
	public static final String RFT_CONFIGURATION_FILE = "icon-rft-configuration-file";
	public static final String RFT_WEB_RESOURCE = "icon-rft-web-resource";
	public static final String FROM_JCR = "icon-from-jcr";
	public static final String FROM_FS = "icon-from-fs";
	public static final String FROM_CLASSPATH = "icon-from-classpath";
	public static final String DEFINITIONS_APP = "icon-definitions-app";
	public static final String DT_GENERIC_DEFINITION = "icon-dt-generic-definition";
	public static final String DT_SUBAPP_DEFINITION = "icon-dt-subapp-definition";
	public static final String DT_TEMPLATE_DEFINITION = "icon-dt-template-definition";
	public static final String DT_DIALOG_DEFINITION = "icon-dt-dialog-definition";
	public static final String DT_FORM_DEFINITION = "icon-dt-form-definition";
	public static final String DT_FORM_FIELD_DEFINITION = "icon-dt-form-field-definition";
	public static final String CUT = "icon-cut";
	public static final String MARKER = "icon-marker";
	public static final String OPEN = "icon-open";
	public static final String CLOSE = "icon-close";
	public static final String LEAF_NODE = "icon-leaf-node";
	public static final String REFERENCE_NODE = "icon-reference-node";
	public static final String TEMPLATING_APP = "icon-templating-app";

	private MgnlIcon() {
		// do not instantiate
	}

}
