package application;
	
import dao.DAOSeguridad;
import excepciones.AeropuertosException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			pantallaLogin();
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Aeropuertos.fxml"));
			Scene scene = new Scene(root,400,400);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void pantallaLogin() {
		
		GridPane gpLogin = new GridPane(10, 20);
		Label labelUser = new Label("Usuario");
		GridPane.setColumnIndex(labelUser, 0);
		TextField tfUser = new TextField();
		tfUser.setId("tfUser");
		GridPane.setColumnIndex(tfUser, 1);

		Label labelPass = new Label("Contraseña");
		GridPane.setColumnIndex(labelUser, 0);
		GridPane.setRowIndex(labelUser, 1);
		TextField tfPass = new TextField();
		tfUser.setId("tfPass");
		GridPane.setColumnIndex(tfPass, 1);
		GridPane.setRowIndex(tfPass, 1);
		
		Button btnLogin = new Button("Acceder");
		GridPane.setRowIndex(btnLogin, 2);
		GridPane.setColumnIndex(btnLogin, 0);
		GridPane.setColumnSpan(btnLogin, 2);
		GridPane.setHalignment(btnLogin, HPos.CENTER);
		
		gpLogin.getChildren().addAll(labelUser, tfUser, labelPass, tfPass, btnLogin);
		gpLogin.setAlignment(Pos.CENTER);
		
		BorderPane loginPane = new BorderPane();
		loginPane.setCenter(gpLogin);
		
		Stage loginStage = new Stage();
		Scene scene = new Scene(loginPane,300,150);
		loginStage.setScene(scene);
		
		btnLogin.setOnAction(e -> {
			try {
				DAOSeguridad.login(tfUser.getText(), tfPass.getText());
				loginStage.close();
			} catch (AeropuertosException e1) {
				System.err.println("CONTRASEÑA INCORRECTA");
			}
		});
		loginStage.showAndWait();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
