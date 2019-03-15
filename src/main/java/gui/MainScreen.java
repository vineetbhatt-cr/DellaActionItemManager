package gui;

import utility.*;
import control.Controller;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.ActionItemManager;

/**
 * <p>
 * Title: MainScreen
 * </p>
 * 
 * <p>
 * Description: A manually generated screen for Della. The navigation buttons
 * are defined here and the other subordinate screens fill in the subscreen
 * details (e.g Console Screen and ActionItems Screen)
 * </p>
 * 
 * <p>
 * Copyright: Copyright 2005, 2006, 2007, 2018
 * </p>
 *
 * @author Harry Sameshima, Lynn Robert Carter, Pankaj Kumar(For JavaFX
 *         Implementation)
 * @version 2.0
 */

public class MainScreen {

	// Main (common) screen attributes

	Stage theStage;
	BorderPane contentPane = new BorderPane();
	FlowPane buttonPane = new FlowPane();

	Button consoleButton = new Button();
	Button actionItemsButton = new Button();
	Button membersButton = new Button();
	Button teamsButton = new Button();
	Button quitButton = new Button();

	private ConsoleScreen consoleScreen = new ConsoleScreen();
	private ActionItemsScreen actionItemsScreen = null;
	private MemberScreen memberScreen = null;
	private TeamsScreen teamsScreen = null;

	Pane mainPane = consoleScreen; // Default the system to start at the Console Screen

	private Controller theController = null;

	public MainScreen(Stage theStage) {

		this.theStage = theStage;

		consoleButton.setOnAction(e -> showConsole());
		actionItemsButton.setOnAction(e -> showActionItems());
		membersButton.setOnAction(e -> showMembers());
		teamsButton.setOnAction(e -> showTeams());
		quitButton.setOnAction(e -> doQuit());

		// Force a read of the Controller from Persistent Store
		theController = Controller.getInstance();

		// Now Finish our initialization and Exit the Stage Right
		theStage.setOnCloseRequest(e -> {
			e.consume();
			doQuit();
		});

		guiInit();
	}

	private void guiInit() {

		theStage.setWidth(665);
		theStage.setHeight(530);

		// Center the Window
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

		int X = (int) ((primaryScreenBounds.getWidth() - theStage.getWidth()) / 2);
		int Y = (int) ((primaryScreenBounds.getHeight() - theStage.getHeight()) / 2);

		theStage.setX(X);
		theStage.setY(Y);

		theStage.setTitle("DellaFX-04"); // Label the stage's window

		consoleButton.setText("    Console    ");
		actionItemsButton.setText("    Action Items    ");
		membersButton.setText("    Members    ");
		teamsButton.setText("    Teams    ");
		quitButton.setText("    Quit   ");

		buttonPane.getChildren().addAll(consoleButton, actionItemsButton, membersButton, teamsButton, quitButton);
		buttonPane.setOrientation(Orientation.HORIZONTAL);
		buttonPane.setPadding(new Insets(10, 10, 10, 10));
		buttonPane.setAlignment(Pos.CENTER);
		buttonPane.setHgap(20);

		contentPane.setTop(buttonPane);
		contentPane.setCenter(mainPane);

		Scene theScene = new Scene(contentPane);
		theStage.setScene(theScene);
		theStage.show();

	}

	/**
	 * This shared routine checks to see if edits are pending and warns the user if
	 * editing work might be lost.
	 * 
	 */

	private boolean okayToChangeScreens() {
		ActionItemManager aiM = theController.getActionItemManager();
		if (aiM.getEditChangesPending()) {
			boolean reallyQuit = ConfirmationBox.show(
					" \n" + "A Screen Change or a Quit has been requested and there		\n"
							+ "		are pending edits to this Action Item!\n\n"
							+ "Do you want to discard these edits?\n\n" + "Click \"Yes\" to discard these edits.\n\n"
							+ "Click \"No\" to return to Action Items Screen.\n ",
					"Screen Change or Quit Requested with Pending Edits!", "Yes", "No");
			if (reallyQuit) {
				aiM.setEditChangesPending(false);
				return true;
			} else
				return false;
		} else
			return true;
	}

	private void showConsole() {

		if (okayToChangeScreens()) {
			contentPane.getChildren().remove(mainPane);
			if (consoleScreen == null) {
				consoleScreen = new ConsoleScreen();
			}
			consoleScreen.loadScreen();
			mainPane = consoleScreen;
			contentPane.setCenter(mainPane);
			theStage.show();
		}
	}

	private void showActionItems() {

		if (okayToChangeScreens()) {
			contentPane.getChildren().remove(mainPane);
			if (actionItemsScreen == null) {
				actionItemsScreen = new ActionItemsScreen();
			}
			actionItemsScreen.loadScreen();
			mainPane = actionItemsScreen;
			contentPane.setCenter(mainPane);
			theStage.show();
		}
	}

	private void showMembers() {

		if (okayToChangeScreens()) {
			contentPane.getChildren().remove(mainPane);
			if (memberScreen == null) {
				memberScreen = new MemberScreen();
			}
			memberScreen.loadScreen();	// Added for Della04
			mainPane = memberScreen;
			contentPane.setCenter(mainPane);
			theStage.show();
		}
	}

	private void showTeams() {

		if (okayToChangeScreens()) {
			contentPane.getChildren().remove(mainPane);
			if (teamsScreen == null) {
				teamsScreen = new TeamsScreen();
			}
			mainPane = teamsScreen;
			contentPane.setCenter(mainPane);
			theStage.show();
		}
	}

	private void doQuit() {
		if (okayToChangeScreens()) {
			if (theController.getDirtyFlag()) {
				boolean reallyQuit = ConfirmationBox.show(
						" \n" + "A Quit has been requested and there are updated		\n"
								+ "		Action Items that have not been saved!\n\n"
								+ "Do you want to save these Action Items?\n\n"
								+ "Click \"Yes\" to save the changed Action Items.\n\n"
								+ "Click \"No\" to ignore the changes.",
						"Quit requested with unsaved Action Items!\n", "Yes", "No");
				if (reallyQuit) {
					theController.save();
				}
			}
			theStage.close();
		}
	}
}
