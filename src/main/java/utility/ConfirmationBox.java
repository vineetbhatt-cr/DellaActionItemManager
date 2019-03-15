package utility;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.*;
import javafx.geometry.*;

public class ConfirmationBox {

	static Stage stage;
	static boolean btnYesClicked;

	public static boolean show(String message, String title, String textYes, String textNo) {
		btnYesClicked = false;

		stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle(title);
		stage.setMinWidth(500);
		stage.setMinHeight(275);

		Label lbl = new Label();
		lbl.setFont(Font.font("Dialog", FontWeight.BOLD, 12));
		lbl.setText(message);

		Button btnYes = new Button();
		btnYes.setText(textYes);
		btnYes.setOnAction(e -> btnYes_Clicked());

		Button btnNo = new Button();
		btnNo.setText(textNo);
		btnNo.setOnAction(e -> btnNo_Clicked());

		HBox paneBtn = new HBox(20);
		paneBtn.getChildren().addAll(btnYes, btnNo);
		paneBtn.setAlignment(Pos.CENTER);

		VBox pane = new VBox(20);
		pane.getChildren().addAll(lbl, paneBtn);
		pane.setAlignment(Pos.CENTER);

		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.showAndWait();
		return btnYesClicked;
	}

	private static void btnYes_Clicked() {
		stage.close();
		btnYesClicked = true;
	}

	private static void btnNo_Clicked() {
		stage.close();
		btnYesClicked = false;
	}

}
