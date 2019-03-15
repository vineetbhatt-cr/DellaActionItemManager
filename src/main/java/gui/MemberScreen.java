package gui;

import control.Controller;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.ActionItemManager;
import model.ElementList;
import utility.MessageBox;

/**
 * <p>
 * Title: MemberScreen
 * </p>
 *
 * <p>
 * Description: The Della Member Screen code
 * </p>
 *
 * <p>
 * Copyright: Copyright ï¿½ 2007
 * </p>
 *
 * @author Lynn Robert Carter
 * @version 1.04 Many thanks to Harry Sameshima for his original work.
 */

public class MemberScreen extends Pane {

	// ---------------------------------------------------------------------------------------------------------------------
	// Member Screen constants

	private static final int noItemSelected = -1; // Added for Della04

	// ---------------------------------------------------------------------------------------------------------------------
	// Member Screen attributes

	// Added for Della04
	private Boolean updatingGUI = false;

	private Controller theController = null;
	private ActionItemManager aiM = null;

	// ---------------------------------------------------------------------------------------------------------------------
	// Member Screen GUI elements

	Label membersLabel = new Label();

	// Added for Della04 (start)
	Label nameLabel = new Label();
	TextField nameTextField = new TextField();

	Label guidanceR1Label = new Label();
	Label guidanceR2Label = new Label();
	Label guidanceR3Label = new Label();
	Label guidanceR4Label = new Label();
	Label guidanceR5Label = new Label();
	Label guidanceR6Label = new Label();
	Label guidanceR7Label = new Label();

	// Get current classloader
	ClassLoader cl = this.getClass().getClassLoader();

	// Create icons
	Image image = new Image(cl.getResourceAsStream("TrashCan.gif"));
	Label trashCanLabel = new Label();

	Button addMemberButton = new Button();
	Button removeMemberButton = new Button();
	Label memberListLabel = new Label();

	ListView<String> memberSelectList = new ListView<String>();
	ScrollPane scrollableMemberListPane = new ScrollPane();

	// Added for Della04 (end)

	// ---------------------------------------------------------------------------------------------------------------------

	/**
	 * The MemberScreen class constructor.
	 * 
	 */
	public MemberScreen() {
		// Use a modified singleton pattern to access the application's state; Added for
		// Della04
		theController = Controller.getInstance();
		aiM = theController.getActionItemManager();

		// Set up all of the Graphical User Interface elements and place them on the
		// screen
		guiInit();

		// Initialize the screen; Added for Della04
		loadScreen();
	}

	/**
	 * Initialize each graphic element, position it on the screen, and add it to the
	 * layout.
	 * 
	 */
	private void guiInit() {

		membersLabel.setFont(Font.font("Dialog", FontWeight.BOLD, 14));
		membersLabel.setStyle("-fx-border-color: black");
		membersLabel.setText("Members");
		membersLabel.setPadding(new Insets(3, 290, 3, 296));

		// Added for Della04 (start)
		nameLabel.setFont(Font.font("Dialog", FontWeight.BOLD, 11));
		nameLabel.setText("Name of someone new (Last, First Middle)");
		nameLabel.setLayoutX(5);
		nameLabel.setLayoutY(23);
		nameLabel.setMinWidth(240);
		nameLabel.setMaxWidth(240);
		nameLabel.setMinHeight(15);
		nameLabel.setMaxHeight(15);

		nameTextField.setText("");
		nameTextField.setLayoutX(5);
		nameTextField.setLayoutY(40);
		nameTextField.setMinWidth(190);
		nameTextField.setMaxWidth(190);
		nameTextField.setMinHeight(20);
		nameTextField.setMaxHeight(20);

		guidanceR1Label.setFont(Font.font("Dialog", FontWeight.BOLD, 11));
		guidanceR1Label.setText("To add a name to the list:");
		guidanceR1Label.setLayoutX(5);
		guidanceR1Label.setLayoutY(70);
		guidanceR1Label.setMinWidth(240);
		guidanceR1Label.setMaxWidth(240);
		guidanceR1Label.setMinHeight(15);
		guidanceR1Label.setMaxHeight(15);

		guidanceR2Label.setFont(Font.font("Dialog", FontWeight.NORMAL, 11));
		guidanceR2Label.setText("1. Click on the box above.");
		guidanceR2Label.setLayoutX(5);
		guidanceR2Label.setLayoutY(85);
		guidanceR2Label.setMinWidth(240);
		guidanceR2Label.setMaxWidth(240);
		guidanceR2Label.setMinHeight(15);
		guidanceR2Label.setMaxHeight(15);

		guidanceR3Label.setFont(Font.font("Dialog", FontWeight.NORMAL, 11));
		guidanceR3Label.setText("2. Type the name.");
		guidanceR3Label.setLayoutX(5);
		guidanceR3Label.setLayoutY(100);
		guidanceR3Label.setMinWidth(240);
		guidanceR3Label.setMaxWidth(240);
		guidanceR3Label.setMinHeight(15);
		guidanceR3Label.setMaxHeight(15);

		guidanceR4Label.setFont(Font.font("Dialog", FontWeight.NORMAL, 11));
		guidanceR4Label.setText("3. Click the \"Add to List\" button.");
		guidanceR4Label.setLayoutX(5);
		guidanceR4Label.setLayoutY(115);
		guidanceR4Label.setMinWidth(240);
		guidanceR4Label.setMaxWidth(240);
		guidanceR4Label.setMinHeight(15);
		guidanceR4Label.setMaxHeight(15);

		guidanceR5Label.setFont(Font.font("Dialog", FontWeight.BOLD, 11));
		guidanceR5Label.setText("To remove a name from the list:");
		guidanceR5Label.setLayoutX(5);
		guidanceR5Label.setLayoutY(150);
		guidanceR5Label.setMinWidth(240);
		guidanceR5Label.setMaxWidth(240);
		guidanceR5Label.setMinHeight(15);
		guidanceR5Label.setMaxHeight(15);

		guidanceR6Label.setFont(Font.font("Dialog", FontWeight.NORMAL, 11));
		guidanceR6Label.setText("1. Click on the name to remove.");
		guidanceR6Label.setLayoutX(5);
		guidanceR6Label.setLayoutY(165);
		guidanceR6Label.setMinWidth(240);
		guidanceR6Label.setMaxWidth(240);
		guidanceR6Label.setMinHeight(15);
		guidanceR6Label.setMaxHeight(15);

		guidanceR7Label.setFont(Font.font("Dialog", FontWeight.NORMAL, 11));
		guidanceR7Label.setText("2. Click on \"Remove from List\" button.");
		guidanceR7Label.setLayoutX(5);
		guidanceR7Label.setLayoutY(180);
		guidanceR7Label.setMinWidth(240);
		guidanceR7Label.setMaxWidth(240);
		guidanceR7Label.setMinHeight(15);
		guidanceR7Label.setMaxHeight(15);

		trashCanLabel.setGraphic(new ImageView(image));
		trashCanLabel.setLayoutX(185);
		trashCanLabel.setLayoutY(70);
		trashCanLabel.setMinWidth(50);
		trashCanLabel.setMaxWidth(50);
		trashCanLabel.setMinHeight(83);
		trashCanLabel.setMaxHeight(83);

		addMemberButton.setFont(Font.font("Dialog", FontWeight.BOLD, 12));
		addMemberButton.setText("Add to List ->");
		addMemberButton.setLayoutX(243);
		addMemberButton.setLayoutY(35);
		addMemberButton.setMinWidth(170);
		addMemberButton.setMaxWidth(170);
		addMemberButton.setMinHeight(35);
		addMemberButton.setMaxHeight(35);
		addMemberButton.setOnAction(e -> addMember());

		removeMemberButton.setFont(Font.font("Dialog", FontWeight.BOLD, 12));
		removeMemberButton.setText("<- Remove from List");
		removeMemberButton.setLayoutX(243);
		removeMemberButton.setLayoutY(95);
		removeMemberButton.setMinWidth(170);
		removeMemberButton.setMaxWidth(170);
		removeMemberButton.setMinHeight(35);
		removeMemberButton.setMaxHeight(35);
		removeMemberButton.setOnAction(e -> removeMember());

		memberListLabel.setFont(Font.font("Dialog", FontWeight.BOLD, 11));
		memberListLabel.setText("Individuals known by Della");
		memberListLabel.setLayoutX(460);
		memberListLabel.setLayoutY(23);
		memberListLabel.setMinWidth(200);
		memberListLabel.setMaxWidth(200);
		memberListLabel.setMinHeight(15);
		memberListLabel.setMaxHeight(15);

		memberSelectList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			selectMember();
		});
		//memberSelectList.setMinWidth(50); //enable if required

		scrollableMemberListPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		scrollableMemberListPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		scrollableMemberListPane.setContent(memberSelectList);
		scrollableMemberListPane.setLayoutX(460);
		scrollableMemberListPane.setLayoutY(40);
		scrollableMemberListPane.setMinWidth(180);
		scrollableMemberListPane.setMaxWidth(180);
		scrollableMemberListPane.setMinHeight(155);
		scrollableMemberListPane.setMaxHeight(155);

		// Added for Della04 (end)

		// ----------------------------------------------------------------------------
		// Add the objects to the layout
		this.getChildren().add(membersLabel);

		// Added for Della04 (start)
		this.getChildren().addAll(nameLabel, nameTextField, guidanceR1Label, guidanceR2Label, guidanceR3Label,
				guidanceR4Label, guidanceR5Label, guidanceR6Label, guidanceR7Label, trashCanLabel, addMemberButton,
				removeMemberButton, memberListLabel, scrollableMemberListPane);
		// Added for Della04 (end)

	}

	/**
	 * Process a "Add to List" button click request Add the new name, if valid, to
	 * the member list
	 * 
	 * @param e ActionEvent
	 * 
	 *          Added for Della04
	 */
	private void addMember() {
		ElementList memberList = aiM.getMemberList();

		try {
			String newName = nameTextField.getText();
			loadScreenAndLists(memberList.addElement(newName));
			nameTextField.setText(""); // If name was accepted, blank out the input field
			memberList.setUnaddedName(""); // and reset the persistent input field copy
		} catch (Exception ex) {
			MessageBox.show(ex.getMessage(), "Error");
			return;
		}
		theController.setDirtyFlag(true);
	}

	/**
	 * Process a "Remove from List" button click request Remove the selected name
	 * from the member list
	 * 
	 * @param e ActionEvent
	 * 
	 *          Added for Della04
	 */
	private void removeMember() {
		int selectedIndex = memberSelectList.getSelectionModel().getSelectedIndex();
		if (selectedIndex == noItemSelected) {
			MessageBox.show("No member was selected!", "Error");
			return;
		} else {
			String memberName = aiM.getMemberList().getName(selectedIndex);
			aiM.removeAssignedMember(memberName);
			nameTextField.setText(memberName);
			loadScreenAndLists(noItemSelected);
			theController.setDirtyFlag(true);
		}
	}

	/**
	 * Process a member select list selection action
	 * 
	 * Added for Della04
	 */
	private void selectMember() {
		if (updatingGUI == false) {
			ElementList memberList = aiM.getMemberList();
			int selectedIndex = memberSelectList.getSelectionModel().getSelectedIndex();
			memberList.setCurrentSelectedElementIndex(selectedIndex);
			theController.setDirtyFlag(true);
		}
	}

	/**
	 * When a shut down occurs or transfer to some other screen occurs, this routine
	 * is called to cause the UI state of the text input to be saved to the
	 * persistent store
	 * 
	 * Added for Della04
	 */
	public void saveScreenState() {
		ElementList memberList = aiM.getMemberList();
		memberList.setUnaddedName(nameTextField.getText());
	}

	/**
	 * When a navigation button click requires this screen to be activated, this
	 * routine is called to load the screen and re-establish the perishable fields
	 * 
	 * Added for Della04
	 */
	public void loadScreen() {
		ElementList memberList = aiM.getMemberList();
		loadScreenAndLists(memberList.getCurrentSelectedElementIndex());
		nameTextField.setText(memberList.getUnaddedName());
	}

	/**
	 * This shared private routine does the heavy lifting of actually setting up the
	 * GUI for this screen
	 * 
	 * Added for Della04
	 */
	private void loadScreenAndLists(int selectedIndex) {
		updatingGUI = true;
		// Set the flag so that no select events are processed by these actions
		ElementList memberList = aiM.getMemberList();
		// Fetch the list of members to populate the select list
		memberSelectList.getItems().clear();
		// Reset the select list so it contains no elements
		int listSize = memberList.getListSize();
		// Fetch the size of the list of members and use this to iterate over all
		// members
		for (int i = 0; i < listSize; i++)
			memberSelectList.getItems().add(memberList.getName(i));
		// Add each member to the select list
		if (selectedIndex == noItemSelected) { // See if a member is selected
			// If not, make sure the member list has no element selected
			memberSelectList.getSelectionModel().clearSelection();
		} else { // A member was selected
			// Select that member
			memberSelectList.getSelectionModel().select(selectedIndex);
		}
		memberList.setCurrentSelectedElementIndex(selectedIndex);
		updatingGUI = false;
	}

}
