package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.text.NumberFormat;

/**
 * The Interface GuiInterface holds application settings we want to be implemented to all
 * components
 */
public interface GuiInterface {

	// Form Field Arrays
	static final String[] ARR_CCARD_USES = {"Emergencies", "Non-Emergency"};
	static final String[] ARR_CHILDREN_COUNT = {"1", "2", "3", "4", "5" };
	static final String[] ARR_EDUCATION = { "High School",
			"On-the-Job Training", "Community College", "Technical School",
			"Some College, Bachelor's Degree", "College + Graduate School" };
	static final String[] ARR_GPA = { "Select GPA", "Under 1.5", "1.5 - 1.9 ", "2.0 - 2.4",
			"2.5 - 2.9", "3.0 - 3.4", "3.5 - 4.0" };
	static final String[] ARR_MARITAL_STATUS = {"Single", "Married", "Divorced"};
	static final String[] ARR_PERIOD = { "1", "2", "3", "4", "5", "6", "7", "8" };
	
	// Fonts
	static final Font BORDER_FONT = new Font ("Tahoma", 0, 12);
	static final Font FNT_SURVEY_TEXT = new Font("Segoi UI", Font.TRUETYPE_FONT, 32);
	static final Font FNT_JOB_TEXT = new Font("Segoi UI", Font.TRUETYPE_FONT, 32);
	static final Font FNT_BIG_AND_BOLD = new Font("Tahoma", Font.BOLD, 14); // by Steve

	
	// Background Colors & Shadows
	static final Color ADD_SURVEY = new Color(0 ,0, 255); // Blue
	static final Color BORDER_COLOR = new Color(35, 75, 125); // Dark Blue
	static final Color CLR_SURVEY_TEXT = new Color(26, 69, 147);
	static final Color CLR_JOB_TEXT = new Color(26, 69, 147);
	static final Color DEL_SURVEY = new Color(255 ,0, 0); // Red
	static final Color FRAME_BACKGROUND = new Color(155, 185, 210); // Light Blue
	static final Color HOVER_COLOR = new Color(0, 0, 255); // Dark Blue
	static final Color PANEL_BACKGROUNDLIGHTGREEN = new Color(127, 255, 0); // Light green by Steve
	static final Color PANEL_BACKGROUNDTAN = new Color(230, 220, 200); // Tan
	static final Color TOPGROUPPANEL_BACKGROUND_YELLOW = new Color(255, 255, 0); // Yellow by Steve
	static final Color FOOTPANEL_BACKGROUND_INDIANRED = new Color(205, 92, 92); // Indian Red by Steve
	static final Color STATUSTIP_BACKGROUND = new Color(75, 75, 75); // Yellow
	static final Color SURVEY_SHADOW = new Color(128, 128, 128); // Grey

	// Miscellaneous
	static final NumberFormat FMT_CURRENCY = NumberFormat.getCurrencyInstance();
	static final String FRAME_TITLE = "RealityU Surveyor";
	
	// Sizes
	static final int EDIT_JOB_HEIGHT = 725;
	static final int EDIT_JOB_WIDTH = 650;
	static final int EDIT_SURVEY_HEIGHT = 800;
	static final int EDIT_SURVEY_WIDTH = 650;
	static final int FRAME_HEIGHT = 850;
	static final int FRAME_WIDTH = 900;
	static final int SETTINGS_HEIGHT = 600;
	static final int SETTINGS_WIDTH = 800;
	static final int SIDEBAR_HEIGHT = 775;
	static final int SIDEBAR_WIDTH = 275;
	//static final int SIDEBAR_WIDTH = 300;
	static final int SURVEY_HEIGHT = 680;
	static final int SURVEY_WIDTH = 750;
	static final int WELCOME_HEIGHT = 450;
	static final int WELCOME_WIDTH = 750;

	// Images & Icons
	static final Image LG_CAUTION = Toolkit.getDefaultToolkit().getImage(GuiInterface.class.getResource("/img/LG_caution.png"));
	static final Image LG_EXCEPTION = Toolkit.getDefaultToolkit().getImage(GuiInterface.class.getResource("/img/LG_exception.png"));
	static final Image LG_EXIT = Toolkit.getDefaultToolkit().getImage(GuiInterface.class.getResource("/img/LG_exit.png"));
	static final Image LG_HELP = Toolkit.getDefaultToolkit().getImage(GuiInterface.class.getResource("/img/LG_help.png"));
	static final Image LG_NEW_GROUP = Toolkit.getDefaultToolkit().getImage(GuiInterface.class.getResource("/img/LG_new_group.png"));
	static final Image LG_OPEN_GROUP = Toolkit.getDefaultToolkit().getImage(GuiInterface.class.getResource("/img/LG_open_group.png"));
	static final Image LG_SETTINGS = Toolkit.getDefaultToolkit().getImage(GuiInterface.class.getResource("/img/LG_settings.png"));
	static final Image LG_SUCCESS = Toolkit.getDefaultToolkit().getImage(GuiInterface.class.getResource("/img/LG_success.png"));
	static final Image LG_SURVEY = Toolkit.getDefaultToolkit().getImage(GuiInterface.class.getResource("/img/LG_survey.png"));
	static final Image MED_CIS_LOGO = Toolkit.getDefaultToolkit().getImage(GuiInterface.class.getResource("/img/MED_cis_logo.png"));
	static final Image MISC_ABOUT_LOGO = Toolkit.getDefaultToolkit().getImage(GuiInterface.class.getResource("/img/MISC_about_01.png"));
	static final Image MISC_01 = Toolkit.getDefaultToolkit().getImage(GuiInterface.class.getResource("/img/MISC_new_survey_01.png"));
	static final Image MISC_02 = Toolkit.getDefaultToolkit().getImage(GuiInterface.class.getResource("/img/MISC_new_survey_02.png"));
	static final Image MISC_WELCOME_01 = Toolkit.getDefaultToolkit().getImage(GuiInterface.class.getResource("/img/MISC_welcome_01.png"));
	static final Image MISC_WELCOME_02 = Toolkit.getDefaultToolkit().getImage(GuiInterface.class.getResource("/img/MISC_welcome_02.png"));
	static final Image SM_CAUTION = Toolkit.getDefaultToolkit().getImage(GuiInterface.class.getResource("/img/SM_caution.png"));
	static final Image SM_COPY = Toolkit.getDefaultToolkit().getImage(GuiInterface.class.getResource("/img/SM_copy.png"));
	static final Image SM_CUT = Toolkit.getDefaultToolkit().getImage(GuiInterface.class.getResource("/img/SM_cut.png"));
	static final Image SM_DELETE = Toolkit.getDefaultToolkit().getImage(GuiInterface.class.getResource("/img/SM_delete.png"));
	static final Image SM_EDIT = Toolkit.getDefaultToolkit().getImage(GuiInterface.class.getResource("/img/SM_edit.png"));
	static final Image SM_EXCEPTION = Toolkit.getDefaultToolkit().getImage(GuiInterface.class.getResource("/img/SM_exception.png"));
	static final Image SM_EXIT = Toolkit.getDefaultToolkit().getImage(GuiInterface.class.getResource("/img/SM_exit.png"));
	static final Image SM_HELP = Toolkit.getDefaultToolkit().getImage(GuiInterface.class.getResource("/img/SM_help.png"));
	static final Image SM_NEW_GROUP = Toolkit.getDefaultToolkit().getImage(GuiInterface.class.getResource("/img/SM_new_group.png"));
	static final Image SM_OPEN_GROUP = Toolkit.getDefaultToolkit().getImage(GuiInterface.class.getResource("/img/SM_open_group.png"));
	static final Image SM_PASTE = Toolkit.getDefaultToolkit().getImage(GuiInterface.class.getResource("/img/SM_paste.png"));
	static final Image SM_PRINT = Toolkit.getDefaultToolkit().getImage(GuiInterface.class.getResource("/img/SM_print.png"));
	static final Image SM_PROCESS = Toolkit.getDefaultToolkit().getImage(GuiInterface.class.getResource("/img/SM_process.png"));
	static final Image MD_PROCESS = Toolkit.getDefaultToolkit().getImage(GuiInterface.class.getResource("/img/MD_process.png"));
	static final Image SM_SAVE = Toolkit.getDefaultToolkit().getImage(GuiInterface.class.getResource("/img/SM_save.png"));
	static final Image SM_SAVE_ALL = Toolkit.getDefaultToolkit().getImage(GuiInterface.class.getResource("/img/SM_save_all.png"));
	static final Image SM_SETTINGS = Toolkit.getDefaultToolkit().getImage(GuiInterface.class.getResource("/img/SM_settings.png"));
	static final Image SM_SUCCESS = Toolkit.getDefaultToolkit().getImage(GuiInterface.class.getResource("/img/SM_success.png"));
	static final Image SM_SURVEY = Toolkit.getDefaultToolkit().getImage(GuiInterface.class.getResource("/img/SM_survey.png"));
	static final Image SM_UNDO = Toolkit.getDefaultToolkit().getImage(GuiInterface.class.getResource("/img/SM_undo.png"));
	static final Image SM_VIEW = Toolkit.getDefaultToolkit().getImage(GuiInterface.class.getResource("/img/SM_view.png"));

}
