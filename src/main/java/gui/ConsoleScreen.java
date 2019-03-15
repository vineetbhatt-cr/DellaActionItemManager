package gui;

import control.Controller;
import java.text.SimpleDateFormat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.ActionItem;
import model.ActionItemManager;

/**
 * <p>
 * Title: ConsoleScreen
 * </p>
 *
 * <p>
 * Description: A manually generated action item screen for Della
 * </p>
 *
 * <p>
 * Copyright: Copyright ï¿½ 2007
 * </p>
 *
 * @author Lynn Robert Carter Many thanks to Harry Sameshima for his original
 *         work.
 * @version 1.03
 */

public class ConsoleScreen extends Pane {
	// ---------------------------------------------------------------------------------------------------------------------
	// Console Screen constants

	public static final int noItemSelected = -1; // Added for Della03

	// ---------------------------------------------------------------------------------------------------------------------
	// Console Screen attributes

	// Added for Della03 (start)
	private boolean updatingGUI = false;
	private Controller theController = null;
	private ActionItemManager aiM = null;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	/// Added for Della03 (end)

	// ---------------------------------------------------------------------------------------------------------------------
	// Console Screen GUI elements

	Label consoleLabel = new Label();

	// Added for Della03 (start)
	Label actionItemsLabel = new Label();
	ListView<String> aiSelectList = new ListView<String>();
	ScrollPane scrollableListPane = new ScrollPane();

	ObservableList<String> sort_direction_options = FXCollections
			.observableArrayList(ActionItemManager.sortDirectionStrings);
	ComboBox<String> sortDirectionComboBox = new ComboBox<String>(sort_direction_options);

	ObservableList<String> sort_factor1_options = FXCollections
			.observableArrayList(ActionItemManager.sortingFactorStrings);
	ComboBox<String> sortFactor1ComboBox = new ComboBox<String>(sort_factor1_options);

	ObservableList<String> sort_factor2_options = FXCollections
			.observableArrayList(ActionItemManager.sortingFactorStrings);
	ComboBox<String> sortFactor2ComboBox = new ComboBox<String>(sort_factor2_options);

	Label firstSortingLabel = new Label();
	Label secondSortingLabel = new Label();
	Label sortDirectionLabel = new Label();

	Label selectedLabel = new Label();
	Label nameLabel = new Label();
	TextField nameTextField = new TextField();
	Label descriptionLabel = new Label();
	ScrollPane descriptionScrollPane = new ScrollPane();
	TextArea descriptionTextArea = new TextArea();
	Label resolutionLabel = new Label();
	ScrollPane resolutionScrollPane = new ScrollPane();
	TextArea resolutionTextArea = new TextArea();

	Label datesLabel = new Label();
	Label creationDateLabel = new Label();
	Label creationDateValueLabel = new Label();
	Label dueLabel = new Label();
	Label dueDateTextLabel = new Label();
	Label actionItemLabel2 = new Label();
	Label statusLabel = new Label();
	Label statusValueLabel = new Label();

	// Added for Della03 (end)

	Label copyrightLabel = new Label();

	// ---------------------------------------------------------------------------------------------------------------------

	/**
	 * The ConsoleScreen class constructor.
	 * 
	 */
	public ConsoleScreen() {
		// Use a modified singleton pattern to access the application's state; Added for
		// Della03
		theController = Controller.getInstance();
		aiM = theController.getActionItemManager();

		// Set up all of the Graphical User Interface elements and position them on the
		// screen
		guiInit();

		// Initialize the screen with the current action item; Added for Della03
		loadScreen();
	}

	/**
	 * Initialize each graphic element, position it on the screen, and add it to the
	 * loayout.
	 * 
	 */
	private void guiInit() {

		consoleLabel.setFont(Font.font("Dialog", FontWeight.BOLD, 14));
		consoleLabel.setStyle("-fx-border-color: black");
		consoleLabel.setText("Console");
		consoleLabel.setPadding(new Insets(3, 296, 3, 296));

		// Added for Della03 (start)

		actionItemsLabel.setFont(Font.font("Dialog", FontWeight.BOLD, 11));
		actionItemsLabel.setText("Action Items:");
		actionItemsLabel.setLayoutX(7);
		actionItemsLabel.setLayoutY(27);

		aiSelectList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			selectActionItem();
		});
		aiSelectList.setMinWidth(435);
		
		scrollableListPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		scrollableListPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		scrollableListPane.setContent(aiSelectList);
		scrollableListPane.setLayoutX(5);
		scrollableListPane.setLayoutY(42);
		scrollableListPane.setMinWidth(450);
		scrollableListPane.setMaxWidth(450);
		scrollableListPane.setMinHeight(175);
		scrollableListPane.setMaxHeight(175);

		sortDirectionLabel.setFont(Font.font("Dialog", FontWeight.BOLD, 11));
		sortDirectionLabel.setText("Sorting Direction:");
		sortDirectionLabel.setLayoutX(470);
		sortDirectionLabel.setLayoutY(50);

		sortDirectionComboBox.setLayoutX(470);
		sortDirectionComboBox.setLayoutY(65);
		sortDirectionComboBox.setMinWidth(177);
		sortDirectionComboBox.setMaxWidth(177);
		sortDirectionComboBox.setMinHeight(25);
		sortDirectionComboBox.setMaxHeight(25);
		sortDirectionComboBox.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> {
					sortDirection();
				});

		firstSortingLabel.setFont(Font.font("Dialog", FontWeight.BOLD, 11));
		firstSortingLabel.setText("First Sorting Factor:");
		firstSortingLabel.setLayoutX(470);
		firstSortingLabel.setLayoutY(95);

		sortFactor1ComboBox.setLayoutX(470);
		sortFactor1ComboBox.setLayoutY(110);
		sortFactor1ComboBox.setMinWidth(176);
		sortFactor1ComboBox.setMaxWidth(176);
		sortFactor1ComboBox.setMinHeight(25);
		sortFactor1ComboBox.setMaxHeight(25);
		sortFactor1ComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			sortFactor1();
		});

		secondSortingLabel.setFont(Font.font("Dialog", FontWeight.BOLD, 11));
		secondSortingLabel.setText("Second Sorting Factor:");
		secondSortingLabel.setLayoutX(470);
		secondSortingLabel.setLayoutY(140);

		sortFactor2ComboBox.setLayoutX(470);
		sortFactor2ComboBox.setLayoutY(155);
		sortFactor2ComboBox.setMinWidth(176);
		sortFactor2ComboBox.setMaxWidth(176);
		sortFactor2ComboBox.setMinHeight(25);
		sortFactor2ComboBox.setMaxHeight(25);
		sortFactor2ComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			sortFactor2();
		});

		selectedLabel.setFont(Font.font("Dialog", FontWeight.BOLD, 11));
		selectedLabel.setText("Selected Action Item:");
		selectedLabel.setAlignment(Pos.BASELINE_LEFT);
		selectedLabel.setLayoutX(6);
		selectedLabel.setLayoutY(222);

		nameLabel.setFont(Font.font("Dialog", FontWeight.BOLD, 11));
		nameLabel.setText("Name:");
		nameLabel.setLayoutX(7);
		nameLabel.setLayoutY(239);

		nameTextField.setText("name goes here");
		nameTextField.setAlignment(Pos.BASELINE_LEFT);
		nameTextField.setLayoutX(47);
		nameTextField.setLayoutY(237);
		nameTextField.setMinWidth(390);
		nameTextField.setMaxWidth(390);
		nameTextField.setMinHeight(21);
		nameTextField.setMaxHeight(21);
		nameTextField.setEditable(false);

		descriptionLabel.setFont(Font.font("Dialog", FontWeight.BOLD, 11));
		descriptionLabel.setText("Description:");
		descriptionLabel.setLayoutX(6);
		descriptionLabel.setLayoutY(257);

		descriptionTextArea.setText("Description text");
		descriptionTextArea.setEditable(false);

		descriptionScrollPane.setContent(descriptionTextArea);
		descriptionScrollPane.setLayoutX(7);
		descriptionScrollPane.setLayoutY(272);
		descriptionScrollPane.setMinWidth(430);
		descriptionScrollPane.setMaxWidth(430);
		descriptionScrollPane.setMinHeight(75);
		descriptionScrollPane.setMaxHeight(75);

		resolutionLabel.setFont(Font.font("Dialog", FontWeight.BOLD, 11));
		resolutionLabel.setText("Resolution:");
		resolutionLabel.setLayoutX(6);
		resolutionLabel.setLayoutY(352);

		resolutionTextArea.setText("Resolution text");
		resolutionTextArea.setEditable(false);

		resolutionScrollPane.setContent(resolutionTextArea);
		resolutionScrollPane.setLayoutX(7);
		resolutionScrollPane.setLayoutY(367);
		resolutionScrollPane.setMinWidth(430);
		resolutionScrollPane.setMaxWidth(430);
		resolutionScrollPane.setMinHeight(75);
		resolutionScrollPane.setMaxHeight(75);

		datesLabel.setFont(Font.font("Dialog", FontWeight.BOLD, 11));
		datesLabel.setText("Dates");
		datesLabel.setLayoutX(460);
		datesLabel.setLayoutY(242);

		creationDateLabel.setFont(Font.font("Dialog", FontWeight.BOLD, 11));
		creationDateLabel.setText("Creation:");
		creationDateLabel.setAlignment(Pos.BASELINE_RIGHT);
		creationDateLabel.setLayoutX(479);
		creationDateLabel.setLayoutY(258);

		creationDateValueLabel.setText("");
		creationDateValueLabel.setLayoutX(538);
		creationDateValueLabel.setLayoutY(258);

		dueLabel.setFont(Font.font("Dialog", FontWeight.BOLD, 11));
		dueLabel.setText("Due:");
		dueLabel.setAlignment(Pos.BASELINE_RIGHT);
		dueLabel.setLayoutX(479);
		dueLabel.setLayoutY(274);
		dueLabel.setMinWidth(51);
		dueLabel.setMaxWidth(51);
		dueLabel.setMinHeight(16);
		dueLabel.setMaxHeight(16);

		dueDateTextLabel.setLayoutX(538);
		dueDateTextLabel.setLayoutY(274);
		dueDateTextLabel.setMinWidth(90);
		dueDateTextLabel.setMaxWidth(90);
		dueDateTextLabel.setMinHeight(16);
		dueDateTextLabel.setMaxHeight(16);

		actionItemLabel2.setFont(Font.font("Dialog", FontWeight.BOLD, 11));
		actionItemLabel2.setText("Action Item");
		actionItemLabel2.setLayoutX(460);
		actionItemLabel2.setLayoutY(290);
		actionItemLabel2.setMinWidth(67);
		actionItemLabel2.setMaxWidth(67);
		actionItemLabel2.setMinHeight(15);
		actionItemLabel2.setMaxHeight(15);

		statusLabel.setFont(Font.font("Dialog", FontWeight.BOLD, 11));
		statusLabel.setText("Status:");
		statusLabel.setAlignment(Pos.BASELINE_RIGHT);
		statusLabel.setLayoutX(479);
		statusLabel.setLayoutY(305);
		statusLabel.setMinWidth(51);
		statusLabel.setMaxWidth(51);
		statusLabel.setMinHeight(16);
		statusLabel.setMaxHeight(16);

		statusValueLabel.setLayoutX(538);
		statusValueLabel.setLayoutY(305);
		statusValueLabel.setMinWidth(85);
		statusValueLabel.setMaxWidth(85);
		statusValueLabel.setMinHeight(16);
		statusValueLabel.setMaxHeight(16);
		// Added for Della03 (end)

		copyrightLabel.setFont(Font.font("Dialog", FontWeight.BOLD, 11));
		copyrightLabel.setAlignment(Pos.BASELINE_RIGHT);
		copyrightLabel.setText("Copyright © 2018 CarterRadley");
		copyrightLabel.setLayoutX(0);
		copyrightLabel.setLayoutY(400);
		copyrightLabel.setMinWidth(640);
		copyrightLabel.setMaxWidth(640);
		copyrightLabel.setMinHeight(15);
		copyrightLabel.setMaxHeight(15);

		Image img = new Image("CR_Logo.png");
		ImageView logo = new ImageView(img);
		logo.setFitWidth(120);
		logo.setFitHeight(40);
		logo.setPreserveRatio(true);
		logo.setLayoutX(500);
		logo.setLayoutY(414);

		this.getChildren().addAll(consoleLabel, actionItemsLabel, scrollableListPane, sortDirectionLabel,
				sortDirectionComboBox, firstSortingLabel, sortFactor1ComboBox, secondSortingLabel, sortFactor2ComboBox,
				selectedLabel, nameLabel, nameTextField, descriptionLabel, descriptionScrollPane, resolutionLabel,
				resolutionScrollPane, datesLabel, creationDateLabel, creationDateValueLabel, dueLabel, dueDateTextLabel,
				actionItemLabel2, statusLabel, statusValueLabel, copyrightLabel, logo); // Added Della03 controls

	}

	/**
	 * Based on a combobox selection, establish the screen's fields
	 * 
	 * @param evt ActionEvent - Any event that gets here is treated as a selection
	 *            event
	 * 
	 *            Added for Della03
	 */
	private void selectActionItem() {
		if (updatingGUI == false) {
			// The select list yields an index. Use that to find the corresponding action
			// item
			ActionItem selectedAI = aiM.getActionItem(aiSelectList.getSelectionModel().getSelectedIndex());

			// Establish the current action item fields
			aiM.setCurrentActionItem(selectedAI);

			// Establish the screen editing fields
			nameTextField.setText(selectedAI.getActionItemName());
			descriptionTextArea.setText(selectedAI.getDescription());
			descriptionTextArea.positionCaret(0);
			resolutionTextArea.setText(selectedAI.getResolution());
			resolutionTextArea.positionCaret(0);
			creationDateValueLabel.setText(dateFormat.format(selectedAI.getCreatedDate()));
			if (selectedAI.getDueDate() != null)
				dueDateTextLabel.setText(dateFormat.format(selectedAI.getDueDate()));
			else
				dueDateTextLabel.setText("");

			// Establish the status display
			if (selectedAI.getStatus() == "Closed")
				statusValueLabel.setText("Closed");
			else
				statusValueLabel.setText("Open");

			// The selected action item has changed so the state has changed
			theController.setDirtyFlag(true);
		}
	}

	/**
	 * The action item selection select list is dynamic. What is displayed there
	 * comes from the current names for each of the action items and since the user
	 * can change those at will, the select list must change as well
	 * 
	 * The buildingSelectList flag is used to signal the rest of this screen that
	 * the select list is in the process of being updated. Change change to the
	 * select list whether brought about by the user or by code, results in change
	 * events. We do not want change events that come from defining the select list
	 * to cause changes to the display.
	 * 
	 * This routine assumes that the order of the action items in the vector is
	 * precisely the correct order for display in the select list. Sorting must be
	 * done elsewhere.
	 * 
	 * @param names String[] - This is the sorted array of names for the select list
	 * 
	 *              Added for Della03
	 */
	private void loadSelectListData(String[] names, int newIndex) {
		// Ignore comboBox change events
		updatingGUI = true;

		// Define the select list
		aiSelectList.getItems().clear();
		if (names != null) // If names is null, there are no action items
			for (int i = 0; i < names.length; i++) {
				aiSelectList.getItems().add(names[i]);
			}

		// Set the current selected item
		aiSelectList.getSelectionModel().select(newIndex);

		// Done updating the Select List
		updatingGUI = false;
	}

	/**
	 * Based on a comboBox selection, establish the sorting direction
	 * 
	 * @param evt ActionEvent - Any event that get's here we process as a combo box
	 *            selection
	 * 
	 *            Added for Della03
	 */
	private void sortDirection() {
		if (updatingGUI == false) {
			// Get the sorting direction indicator and use that to rebuild the select list
			aiM.setSortDirection(sortDirectionComboBox.getSelectionModel().getSelectedIndex());
			loadSelectListData(aiM.getActionItemNames(), aiM.getActionItemIndex(aiM.getCurrentActionItemName()));

			// The sorting direction has changed so the state has changed
			theController.setDirtyFlag(true);
		}
	}

	/**
	 * Based on a combobox selection, establish the sorting factor
	 * 
	 * @param evt ActionEvent - Any event that get's here we process as a combo box
	 *            selection
	 * 
	 *            Added for Della03
	 */
	private void sortFactor1() {
		if (updatingGUI == false) {
			// Get the sorting factor 1 indicator and use that to rebuild the select list
			aiM.setSortFactor1(sortFactor1ComboBox.getSelectionModel().getSelectedIndex());
			loadSelectListData(aiM.getActionItemNames(), aiM.getActionItemIndex(aiM.getCurrentActionItemName()));

			// A sorting factor has changed so the state has changed
			theController.setDirtyFlag(true);
		}
	}

	/**
	 * Based on a combobox selection, establish the sorting factor
	 * 
	 * @param evt ActionEvent - Any event that get's here we process as a combo box
	 *            selection
	 * 
	 *            Added for Della03
	 */
	private void sortFactor2() {
		if (updatingGUI == false) {
			// Get the sorting factor 2 indicator and use that to rebuild the select list
			aiM.setSortFactor2(sortFactor2ComboBox.getSelectionModel().getSelectedIndex());
			loadSelectListData(aiM.getActionItemNames(), aiM.getActionItemIndex(aiM.getCurrentActionItemName()));

			// A sorting factor has changed so the state has changed
			theController.setDirtyFlag(true);
		}
	}

	/**
	 * Clear the current action item and reset the text fields on the display
	 * 
	 * Added for Della03
	 */
	private void clearAI() {
		updatingGUI = true;
		aiM.setCurrentActionItem(aiM.getActionItem(noItemSelected));
		aiM.clearCurrentActionItem();
		nameTextField.setText("");
		descriptionTextArea.setText("");
		descriptionTextArea.positionCaret(0);
		resolutionTextArea.setText("");
		resolutionTextArea.positionCaret(0);
		statusValueLabel.setText("Open");
		creationDateValueLabel.setText("");
		creationDateValueLabel.setText("");
		updatingGUI = false;
	}

	/**
	 * Fill the screen with the values of the current action item, if we have one
	 * 
	 * Added for Della03
	 */
	public void loadScreen() {
		updatingGUI = true;
		// Fetch the current action item. If there isn't one, set up a blank action item
		ActionItem ai = aiM.getCurrentActionItem();
		if (ai == null) {
			clearAI();
		} else {
			// Define the text fields for the existing action item
			nameTextField.setText(ai.getActionItemName());
			descriptionTextArea.setText(ai.getDescription());
			descriptionTextArea.positionCaret(0);
			resolutionTextArea.setText(ai.getResolution());
			resolutionTextArea.positionCaret(0);

			// Establish the status display
			if (ai.getStatus() == "Closed")
				statusValueLabel.setText("Closed");
			else
				statusValueLabel.setText("Open");

			// Define the creation and due dates
			if (ai.getCreatedDate() != null)
				creationDateValueLabel.setText(dateFormat.format(ai.getCreatedDate()));
			else
				creationDateValueLabel.setText("");
			if (ai.getDueDate() != null)
				dueDateTextLabel.setText(dateFormat.format(ai.getDueDate()));
			else
				creationDateValueLabel.setText("");
		}
		// Set up the selection ComboBoxes and the select list - Modified for Della03
		sortDirectionComboBox.getSelectionModel().select(aiM.getSortDirection());
		sortFactor1ComboBox.getSelectionModel().select(aiM.getSortFactor1());
		sortFactor2ComboBox.getSelectionModel().select(aiM.getSortFactor2());
		loadSelectListData(aiM.getActionItemNames(), aiM.getActionItemIndex(aiM.getCurrentActionItemName()));
		updatingGUI = false;
	}

}
