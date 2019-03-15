package gui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TeamsScreen extends Pane {

	Label teamsLabel = new Label();

	public TeamsScreen() {

		guiInit();
	}

	private void guiInit() {
		teamsLabel.setFont(Font.font("Dialog", FontWeight.BOLD, 14));
		teamsLabel.setStyle("-fx-border-color: black");
		teamsLabel.setText("Teams");
		teamsLabel.setPadding(new Insets(3, 301, 3, 302));
		this.getChildren().add(teamsLabel);

	}

}
