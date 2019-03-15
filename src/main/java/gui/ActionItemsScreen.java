package gui;

import utility.*;
import java.text.SimpleDateFormat;

import control.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.ActionItem;
import model.ActionItemManager;

public class ActionItemsScreen extends Pane {

	// Action Item Screen Constants

	public static final int noItemSelected = -1;

	// Action Item Screen Attributes

	private boolean updatingGUI = false;
	private Controller theController = null;
	private ActionItemManager aiM = null;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	// Action Item Screen GUI elements

	Label actionItemsLabel = new Label();

	// Added for Della01 (start)
	Label comboBoxLabel = new Label();
	ComboBox<String> aiListComboBox = new ComboBox<String>();
	Label selectGuidanceLabel = new Label();
	// Added for Della01 (end)

	// Added for Della02 (start)
	ObservableList<String> sort_direction_options = FXCollections
			.observableArrayList(ActionItemManager.sortDirectionStrings);
	ComboBox<String> sortDirectionComboBox = new ComboBox<String>(sort_direction_options);

	ObservableList<String> sort_factor1_options = FXCollections
			.observableArrayList(ActionItemManager.sortingFactorStrings);
	ComboBox<String> sortFactor1ComboBox = new ComboBox<String>(sort_factor1_options);

	ObservableList<String> sort_factor2_options = FXCollections
			.observableArrayList(ActionItemManager.sortingFactorStrings);
	ComboBox<String> sortFactor2ComboBox = new ComboBox<String>(sort_factor2_options);

	Label sortFactor1Label = new Label();
	Label sortFactor2Label = new Label();
	Label sortDirectionLabel = new Label();
	// Added for Della02 (end)

	Label selectedLabel = new Label();
	Label nameLabel = new Label();
	TextField nameTextField = new TextField();
	Label descriptionLabel = new Label();
	ScrollPane descriptionScrollPane = new ScrollPane();
	TextArea descriptionTextArea = new TextArea();
	Label resolutionLabel = new Label();
	ScrollPane resolutionScrollPane = new ScrollPane();
	TextArea resolutionTextArea = new TextArea();

	Label unsavedChangesLabel = new Label();

	Label datesLabel = new Label();
	Label creationLabel = new Label();
	Label creationValueLabel = new Label();
	Label dueDateLabel = new Label();
	TextField dueDateTextField = new TextField();
	Label formatLabel = new Label();
	Label actionItemLabel2 = new Label();
	Label statusLabel = new Label();

	ObservableList<String> options = FXCollections.observableArrayList(ActionItemManager.statusStrings);
	ComboBox<String> statusComboBox = new ComboBox<String>(options);

	Button updateButton = new Button();
	Button clearButton = new Button();
	Button createButton = new Button();

	/**
	 * The ActionItemScreen class constructor.
	 */
	public ActionItemsScreen() {
		// Use a modified singleton pattern to access the application's state
		theController = Controller.getInstance();
		aiM = theController.getActionItemManager();

		// Set up all of the Graphical User Interface elements and place them on the
		// screen
		guiInit();

		// Initialize the screen with current action item
		loadScreen();
	}

	/**
	 * Initialize each graphic element, position it on the screen, and add it to the
	 * layout
	 */

	private void guiInit() {
		// Updating the GUI
		updatingGUI = true;

		actionItemsLabel.setFont(Font.font("Dialog", FontWeight.BOLD, 14));
		actionItemsLabel.setStyle("-fx-border-color: black");
		actionItemsLabel.setText("Action Items");
		actionItemsLabel.setPadding(new Insets(3, 267, 3, 296));

		comboBoxLabel.setFont(Font.font("Dialog", FontWeight.BOLD, 11));
		comboBoxLabel.setText("Action Items:");
		comboBoxLabel.setLayoutX(7);
		comboBoxLabel.setLayoutY(27);
		comboBoxLabel.setMinWidth(100);
		comboBoxLabel.setMaxWidth(100);
		comboBoxLabel.setMinHeight(15);
		comboBoxLabel.setMaxHeight(15);

		aiListComboBox.setLayoutX(5);
		aiListComboBox.setLayoutY(42);
		aiListComboBox.setMinWidth(640);
		aiListComboBox.setMaxWidth(640);
		aiListComboBox.setMinHeight(25);
		aiListComboBox.setMaxHeight(25);
		aiListComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			selectActionItem();
		});

		selectGuidanceLabel.setFont(Font.font("Dialog", FontWeight.BOLD, 10));
		selectGuidanceLabel.setText("Select an Action Item from the pull-down list above to examine and update it.");
		selectGuidanceLabel.setLayoutX(15);
		selectGuidanceLabel.setLayoutY(67);
		selectGuidanceLabel.setMinWidth(500);
		selectGuidanceLabel.setMaxWidth(500);
		selectGuidanceLabel.setMinHeight(15);
		selectGuidanceLabel.setMaxHeight(15);

		// Added for Della02 (start)

		sortDirectionLabel.setFont(Font.font("Dialog", FontWeight.BOLD, 11));
		sortDirectionLabel.setText("Sorting Direction:");
		sortDirectionLabel.setLayoutX(195);
		sortDirectionLabel.setLayoutY(90);

		sortDirectionComboBox.setLayoutX(195);
		sortDirectionComboBox.setLayoutY(105);
		sortDirectionComboBox.setMinWidth(140);
		sortDirectionComboBox.setMaxWidth(140);
		sortDirectionComboBox.setMinHeight(25);
		sortDirectionComboBox.setMaxHeight(25);
		sortDirectionComboBox.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> {
					sortDirection();
				});

		sortFactor1Label.setFont(Font.font("Dialog", FontWeight.BOLD, 11));
		sortFactor1Label.setText("First Sorting Factor:");
		sortFactor1Label.setLayoutX(335);
		sortFactor1Label.setLayoutY(90);

		sortFactor1ComboBox.setLayoutX(340);
		sortFactor1ComboBox.setLayoutY(105);
		sortFactor1ComboBox.setMinWidth(150);
		sortFactor1ComboBox.setMaxWidth(150);
		sortFactor1ComboBox.setMinHeight(25);
		sortFactor1ComboBox.setMaxHeight(25);
		sortFactor1ComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			sortingFactor1();
		});

		sortFactor2Label.setFont(Font.font("Dialog", FontWeight.BOLD, 11));
		sortFactor2Label.setText("Second Sorting Factor:");
		sortFactor2Label.setLayoutX(495);
		sortFactor2Label.setLayoutY(90);

		sortFactor2ComboBox.setLayoutX(495);
		sortFactor2ComboBox.setLayoutY(105);
		sortFactor2ComboBox.setMinWidth(150);
		sortFactor2ComboBox.setMaxWidth(150);
		sortFactor2ComboBox.setMinHeight(25);
		sortFactor2ComboBox.setMaxHeight(25);
		sortFactor2ComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			sortingFactor2();
		});

		// Added for Della02 (end)

		selectedLabel.setFont(Font.font("Dialog", FontWeight.BOLD, 11));
		selectedLabel.setText("Selected Action Item:");
		selectedLabel.setAlignment(Pos.BASELINE_LEFT);
		selectedLabel.setLayoutX(7);
		selectedLabel.setLayoutY(145);

		nameLabel.setFont(Font.font("Dialog", FontWeight.BOLD, 11));
		nameLabel.setText("Name:");
		nameLabel.setLayoutX(7);
		nameLabel.setLayoutY(165);

		nameTextField.setText("");
		nameTextField.setAlignment(Pos.BASELINE_LEFT);
		nameTextField.setMinWidth(390);
		nameTextField.setMaxWidth(390);
		nameTextField.setLayoutX(46);
		nameTextField.setLayoutY(165);
		nameTextField.setEditable(true);
		nameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			checkForUnsavedUpdates();
		});

		descriptionLabel.setFont(Font.font("Dialog", FontWeight.BOLD, 11));
		descriptionLabel.setText("Description:");
		descriptionLabel.setLayoutX(6);
		descriptionLabel.setLayoutY(190);
		descriptionScrollPane.setContent(descriptionTextArea);
		descriptionScrollPane.setLayoutX(7);
		descriptionScrollPane.setLayoutY(210);
		descriptionScrollPane.setMinWidth(430);
		descriptionScrollPane.setMaxWidth(430);
		descriptionScrollPane.setMinHeight(75);
		descriptionScrollPane.setMaxHeight(75);
		descriptionTextArea.setText("");
		descriptionTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
			checkForUnsavedUpdates();
		});

		resolutionLabel.setFont(Font.font("Dialog", FontWeight.BOLD, 11));
		resolutionLabel.setText("Resolution:");
		resolutionLabel.setLayoutX(6);
		resolutionLabel.setLayoutY(295);
		resolutionScrollPane.setContent(resolutionTextArea);
		resolutionScrollPane.setLayoutX(7);
		resolutionScrollPane.setLayoutY(315);
		resolutionScrollPane.setMinWidth(430);
		resolutionScrollPane.setMaxWidth(430);
		resolutionScrollPane.setMinHeight(75);
		resolutionScrollPane.setMaxHeight(75);
		resolutionTextArea.setText("");
		resolutionTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
			checkForUnsavedUpdates();
		});

		datesLabel.setFont(Font.font("Dialog", FontWeight.BOLD, 11));
		datesLabel.setText("Dates");
		datesLabel.setLayoutX(450);
		datesLabel.setLayoutY(175);

		creationLabel.setFont(Font.font("Dialog", FontWeight.BOLD, 11));
		creationLabel.setText("Creation:");
		creationLabel.setAlignment(Pos.BASELINE_RIGHT);
		creationLabel.setLayoutX(469);
		creationLabel.setLayoutY(195);
		creationValueLabel.setText("");
		creationValueLabel.setLayoutX(528);
		creationValueLabel.setLayoutY(195);

		dueDateLabel.setFont(Font.font("Dialog", FontWeight.BOLD, 11));
		dueDateLabel.setText("Due:");
		dueDateLabel.setAlignment(Pos.BASELINE_RIGHT);
		dueDateLabel.setLayoutX(469);
		dueDateLabel.setLayoutY(217);
		dueDateLabel.setMinWidth(51);
		dueDateLabel.setMaxWidth(51);
		dueDateLabel.setMinHeight(16);
		dueDateLabel.setMaxHeight(16);
		dueDateTextField.setLayoutX(524);
		dueDateTextField.setLayoutY(215);
		dueDateTextField.setMinWidth(90);
		dueDateTextField.setMaxWidth(90);
		dueDateTextField.setMinHeight(20);
		dueDateTextField.setMaxHeight(20);
		dueDateTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			checkForUnsavedUpdates();
		});

		formatLabel.setFont(Font.font("Dialog", FontWeight.NORMAL, 10));
		formatLabel.setText("Use yyyy-mm-dd format");
		formatLabel.setLayoutX(495);
		formatLabel.setLayoutY(238);
		formatLabel.setMinWidth(125);
		formatLabel.setMaxWidth(125);
		formatLabel.setMinHeight(11);
		formatLabel.setMaxHeight(11);

		actionItemLabel2.setFont(Font.font("Dialog", FontWeight.BOLD, 11));
		actionItemLabel2.setText("Action Item");
		actionItemLabel2.setLayoutX(450);
		actionItemLabel2.setLayoutY(260);
		actionItemLabel2.setMinWidth(67);
		actionItemLabel2.setMaxWidth(67);
		actionItemLabel2.setMinHeight(15);
		actionItemLabel2.setMaxHeight(15);

		statusLabel.setFont(Font.font("Dialog", FontWeight.BOLD, 11));
		statusLabel.setText("Status:");
		statusLabel.setAlignment(Pos.BASELINE_RIGHT);
		statusLabel.setLayoutX(469);
		statusLabel.setLayoutY(277);
		statusLabel.setMinWidth(51);
		statusLabel.setMaxWidth(51);
		statusLabel.setMinHeight(16);
		statusLabel.setMaxHeight(16);
		statusComboBox.getSelectionModel().select(ActionItemManager.statusOpen);
		statusComboBox.setLayoutX(524);
		statusComboBox.setLayoutY(275);
		statusComboBox.setMinWidth(100);
		statusComboBox.setMaxWidth(100);
		statusComboBox.setMinHeight(25);
		statusComboBox.setMaxHeight(25);

		updateButton.setFont(Font.font("Dialog", FontWeight.BOLD, 11));
		updateButton.setText("Update This Action Item");
		updateButton.setLayoutX(3);
		updateButton.setLayoutY(395);
		updateButton.setMinWidth(170);
		updateButton.setMaxWidth(170);
		updateButton.setMinHeight(30);
		updateButton.setMaxHeight(30);
		updateButton.setOnAction(e -> updateActionItem());

		clearButton.setFont(Font.font("Dialog", FontWeight.BOLD, 11));
		clearButton.setText("Clear This Form");
		clearButton.setLayoutX(173);
		clearButton.setLayoutY(395);
		clearButton.setMinWidth(126);
		clearButton.setMaxWidth(126);
		clearButton.setMinHeight(30);
		clearButton.setMaxHeight(30);
		clearButton.setOnAction(e -> clearActionItemForm());

		createButton.setFont(Font.font("Dialog", FontWeight.BOLD, 11));
		createButton.setText("Create a new Action Item");
		createButton.setLayoutX(299);
		createButton.setLayoutY(395);
		createButton.setMinWidth(180);
		createButton.setMaxWidth(180);
		createButton.setMinHeight(30);
		createButton.setMaxHeight(30);
		createButton.setOnAction(e -> createActionItem());

		unsavedChangesLabel.setFont(Font.font("Dialog", FontWeight.BOLD, 11));
		unsavedChangesLabel.setText("");
		unsavedChangesLabel.setTextFill(Color.RED);
		unsavedChangesLabel.setLayoutX(250);
		unsavedChangesLabel.setLayoutY(430);
		unsavedChangesLabel.setMinWidth(200);
		unsavedChangesLabel.setMaxWidth(200);
		unsavedChangesLabel.setMinHeight(15);
		unsavedChangesLabel.setMaxHeight(15);

		this.getChildren().addAll(actionItemsLabel, comboBoxLabel, aiListComboBox, selectGuidanceLabel,
				sortDirectionLabel, sortDirectionComboBox, sortFactor1Label, sortFactor1ComboBox, sortFactor2Label,
				sortFactor2ComboBox, selectedLabel, nameLabel, nameTextField, descriptionLabel, descriptionScrollPane,
				resolutionLabel, resolutionScrollPane, datesLabel, creationLabel, creationValueLabel, dueDateLabel,
				dueDateTextField, formatLabel, actionItemLabel2, statusLabel, statusComboBox, updateButton, clearButton,
				createButton, unsavedChangesLabel);

		// Done updating the GUI
		updatingGUI = false;
	}

	/**
	 * Clear the current action item and the attribute related combo boxes
	 */

	private void clearAI() {
		updatingGUI = true;
		aiM.clearCurrentActionItem();
		nameTextField.setText("");
		descriptionTextArea.setText("");
		resolutionTextArea.setText("");
		creationValueLabel.setText("");
		dueDateTextField.setText("");

		// Select the Open Status
		statusComboBox.getSelectionModel().select(ActionItemManager.statusOpen);

		// Reset the Action Item ComboBox so no item is selected Added for Della01
		aiListComboBox.getSelectionModel().select(noItemSelected);
		updatingGUI = false;
	}

	/**
	 * Process a "Clear This Form" button click request Clear out the current action
	 * item and inform the user if this results in unsaved changes
	 */

	private void clearActionItemForm() {
		// Reset the current Action Item Fields
		clearAI();
		theController.setDirtyFlag(true);
		checkForUnsavedUpdates();
	}

	/**
	 * Create a new action item
	 * 
	 * @param e ActionEvent
	 * 
	 *          Added for Della01
	 */
	private void createActionItem() {
		ActionItem ai = null;
		try {
			ai = aiM.createActionItem(nameTextField.getText(), descriptionTextArea.getText(),
					resolutionTextArea.getText(), statusComboBox.getSelectionModel().getSelectedItem().toString(),
					dueDateTextField.getText());
		} catch (Exception ex) {
			MessageBox.show(ex.getMessage(), "Error");
			return;
		}

		// Update the creation date for the action item
		creationValueLabel.setText(dateFormat.format(ai.getCreatedDate()));
		updatingGUI = true;
		loadComboBoxData(aiM.getActionItemNames(), aiM.getActionItemIndex(aiM.getCurrentActionItemName())); // Modified
																											// for
																											// Della02
		updatingGUI = false;

		theController.setDirtyFlag(true);
		checkForUnsavedUpdates();
	}

	/**
	 * Update the current action item in memory
	 * 
	 * @param e ActionEvent
	 */
	private void updateActionItem() {
		// Tell the ActionItemManager to save the update

		try {
			aiM.updateActionItem(nameTextField.getText(), descriptionTextArea.getText(), resolutionTextArea.getText(),
					statusComboBox.getSelectionModel().getSelectedItem().toString(), dueDateTextField.getText(),
					aiListComboBox.getSelectionModel().getSelectedIndex());
		} catch (Exception ex) {
			MessageBox.show(ex.getMessage(), "Error");
			return;
		}

		// It is possible that the Action Item name has been changed
		// so we must re-sort the Action Items and re-establish the
		// ComboBox select list. - Added for Della01
		updatingGUI = true;
		loadComboBoxData(aiM.getActionItemNames(), aiM.getActionItemIndex(aiM.getCurrentActionItemName())); // Modified
																											// for
																											// Della02
		updatingGUI = false;

		theController.setDirtyFlag(true);
		checkForUnsavedUpdates();
	}

	/**
	 * Fill the screen with the values of the current action item, if we have one,
	 * and display it.
	 */
	public void loadScreen() {
		updatingGUI = true;
		// Fetch the current action item. If there isn't one, leave now
		ActionItem ai = aiM.getCurrentActionItem();
		if (ai == null) {
			clearAI();
			updatingGUI = true;
			statusComboBox.getSelectionModel().select(ActionItemManager.statusOpen);
			creationValueLabel.setText("");
			dueDateTextField.setText("");
		} else {
			// Define the text fields
			updatingGUI = true;
			nameTextField.setText(ai.getActionItemName());
			descriptionTextArea.setText(ai.getDescription());
			descriptionTextArea.positionCaret(0);
			resolutionTextArea.setText(ai.getResolution());
			resolutionTextArea.positionCaret(0);
		}
		// Define the status ComboBox value
		for (int i = 0; i < ActionItemManager.statusStrings.length; ++i)
			if (ai.getStatus().compareTo(ActionItemManager.statusStrings[i]) == 0) {
				statusComboBox.getSelectionModel().select(i);
				break;
			}

		// Define the creation and due dates
		if (ai.getCreatedDate() != null)
			creationValueLabel.setText(dateFormat.format(ai.getCreatedDate()));
		else
			creationValueLabel.setText("");
		if (ai.getDueDate() != null)
			dueDateTextField.setText(dateFormat.format(ai.getDueDate()));
		else
			dueDateTextField.setText("");

		// Set up the selection Combo Boxes - Modified for Della02
		sortDirectionComboBox.getSelectionModel().select(aiM.getSortDirection());
		sortFactor1ComboBox.getSelectionModel().select(aiM.getSortFactor1());
		sortFactor2ComboBox.getSelectionModel().select(aiM.getSortFactor2());

		// Set up the selection ComboBox - Modified for Della02
		loadComboBoxData(aiM.getActionItemNames(), aiM.getActionItemIndex(aiM.getCurrentActionItemName()));

		updatingGUI = false;
	}

	/**
	 * Based on a combo box selection, establish the screen's fields
	 * 
	 * @param evt ActionEvent - Any event that gets here is treated as a selection
	 *            event
	 * 
	 *            Added for Della01
	 */
	private void selectActionItem() {
		if (updatingGUI == false) {
			updatingGUI = true;
			try {
				// The combobox return an index. Use that to find the corresponding action item
				ActionItem selectedAI = new ActionItem();
				selectedAI = aiM.getActionItem(aiListComboBox.getSelectionModel().getSelectedIndex());

				// Establish the current action item fields
				aiM.setCurrentActionItem(selectedAI);

				if (selectedAI == null)
					clearAI();
				else {
					// Establish the screen editing fields
					nameTextField.setText(selectedAI.getActionItemName());
					descriptionTextArea.setText(selectedAI.getDescription());
					descriptionTextArea.positionCaret(0);
					resolutionTextArea.setText(selectedAI.getResolution());
					resolutionTextArea.positionCaret(0);
					if (selectedAI.getCreatedDate() != null)
						creationValueLabel.setText(dateFormat.format(selectedAI.getCreatedDate()));
					else
						creationValueLabel.setText("");
					if (selectedAI.getDueDate() != null)
						dueDateTextField.setText(dateFormat.format(selectedAI.getDueDate()));
					else
						dueDateTextField.setText("");

					// Establish the status combo box
					if (selectedAI.getStatus() == "Closed")
						statusComboBox.getSelectionModel().select(ActionItemManager.statusClosed);
					else
						statusComboBox.getSelectionModel().select(ActionItemManager.statusOpen);
				}
				// The selected action item has changed so the state has changed
				theController.setDirtyFlag(true);

			} catch (Exception e) {
				e.printStackTrace();
			}
			updatingGUI = false;
		}
	}

	/**
	 * Based on a combobox selection, establish the sorting direction
	 * 
	 * @param evt ActionEvent - Any event that get's here we process as a combo box
	 *            selection
	 * 
	 *            Added for Della02
	 */
	private void sortDirection() {
		if (updatingGUI == false) {
			aiM.setSortDirection(sortDirectionComboBox.getSelectionModel().getSelectedIndex());
			loadComboBoxData(aiM.getActionItemNames(), aiM.getActionItemIndex(aiM.getCurrentActionItemName()));

			// The sorting direction has changed so the state has changed
			theController.setDirtyFlag(true);
		}
	}

	/**
	 * Based on a combobox selection, establish the first sorting factor
	 * 
	 * @param evt ActionEvent - Any event that get's here we process as a combo box
	 *            selection
	 * 
	 *            Added for Della02
	 */
	private void sortingFactor1() {
		if (updatingGUI == false) {
			aiM.setSortFactor1(sortFactor1ComboBox.getSelectionModel().getSelectedIndex());
			loadComboBoxData(aiM.getActionItemNames(), aiM.getActionItemIndex(aiM.getCurrentActionItemName()));

			// The first sorting factor has changed so the state has changed
			theController.setDirtyFlag(true);
		}
	}

	/**
	 * Based on a combo box selection, establish the second sorting factor
	 * 
	 * @param evt ActionEvent - Any event that get's here we process as a combo box
	 *            selection
	 * 
	 *            Added for Della02
	 */
	private void sortingFactor2() {
		if (updatingGUI == false) {
			aiM.setSortFactor2(sortFactor2ComboBox.getSelectionModel().getSelectedIndex());
			loadComboBoxData(aiM.getActionItemNames(), aiM.getActionItemIndex(aiM.getCurrentActionItemName()));

			// The second sorting factor has changed so the state has changed
			theController.setDirtyFlag(true);
		}
	}

	/**
	 * The action item selection combo box is dynamic. What is displayed there comes
	 * from the current names for each of the action items and since the user can
	 * change those at will, the combo box select list must change as well.
	 * 
	 * This routine assumes that the order of the action items in the vector is
	 * precisely the correct order for display in the combo box. Sorting must be
	 * done elsewhere.
	 * 
	 * @param names String[] - This is the sorted array of names for the select list
	 * 
	 *              Added for Della01 updated for Della02
	 */
	private void loadComboBoxData(String[] names, int newIndex) {
		// Define the combo box select list
		aiListComboBox.getItems().clear();
		if ((names != null) && (names.length > 0)) {
			// If names is null or the length is zero, there are no action items
			for (int i = 0; i < names.length; i++)
				aiListComboBox.getItems().add(names[i]);

			// Set the current selected item; updated for Della02
			aiListComboBox.getSelectionModel().select(newIndex);
		}
	}

	/**
	 * Any number of events has occurred that could change the display. See if the
	 * current edit values still match the current action item. If so, then no
	 * warning is needed. If not, then given a warning (red text) that informs the
	 * user that there are edits to the action item that have not been saved.
	 * 
	 */
	private void checkForUnsavedUpdates() {
		if (updatingGUI)
			return;
		if (nameTextField.getText().equals(aiM.getCurrentActionItem().getActionItemName())
				&& descriptionTextArea.getText().equals(aiM.getCurrentActionItem().getDescription())
				&& resolutionTextArea.getText().equals(aiM.getCurrentActionItem().getResolution())
				&& dueDateTextField.getText()
						.equals(aiM.getCurrentActionItem().getDueDate() != null
								? dateFormat.format(aiM.getCurrentActionItem().getDueDate())
								: "")
				&& ((statusComboBox.getSelectionModel().getSelectedIndex() == 0
						&& aiM.getCurrentActionItem().getStatus().equals(""))
						|| (statusComboBox.getSelectionModel().getSelectedIndex() == 0
								&& aiM.getCurrentActionItem().getStatus().equals("Open"))
						|| (statusComboBox.getSelectionModel().getSelectedIndex() == 1
								&& aiM.getCurrentActionItem().getStatus().equals("Closed")))) {
			unsavedChangesLabel.setText("");
			aiM.setEditChangesPending(false);
		} else {
			unsavedChangesLabel.setText("There are unsaved changes!");
			aiM.setEditChangesPending(true);
		}
	}

}
