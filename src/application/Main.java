package application;
	
import dao.DAOSeguridad;
import excepciones.AeropuertosException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class Main extends Application {
	private boolean detener = false;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			pantallaLogin();
			if (!detener) {
				BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("/fxml/Aeropuertos.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setTitle("AVIONES - AEROPUERTOS");
				primaryStage.setScene(scene);
				primaryStage.show();
			}
		} catch(Exception e) {
			e.printStackTrace();
			Platform.exit();
		}
	}
	
	private void pantallaLogin() {
		
		GridPane gpLogin = new GridPane(10, 20);
		Label labelUser = new Label("Usuario");
		GridPane.setColumnIndex(labelUser, 0);
		GridPane.setRowIndex(labelUser, 0);
		TextField tfUser = new TextField();
		tfUser.setId("tfUser");
		GridPane.setColumnIndex(tfUser, 1);
		GridPane.setRowIndex(tfUser, 0);

		Label labelPass = new Label("Contraseña");
		GridPane.setColumnIndex(labelPass, 0);
		GridPane.setRowIndex(labelPass, 1);
		PasswordField pfPass = new PasswordField();
		pfPass.setId("tfPass");
		GridPane.setColumnIndex(pfPass, 1);
		GridPane.setRowIndex(pfPass, 1);
		
		Button btnLogin = new Button("Acceder");
		GridPane.setRowIndex(btnLogin, 2);
		GridPane.setColumnIndex(btnLogin, 0);
		GridPane.setColumnSpan(btnLogin, 2);
		GridPane.setHalignment(btnLogin, HPos.CENTER);
		
		gpLogin.getChildren().addAll(labelUser, btnLogin, labelPass, tfUser, pfPass);
		gpLogin.setAlignment(Pos.CENTER);
		
		BorderPane loginPane = new BorderPane();
		loginPane.setCenter(gpLogin);
		
		Stage loginStage = new Stage();
		loginStage.setTitle("AVIONES - LOGIN");
		Scene scene = new Scene(loginPane,300,150);
		loginStage.setScene(scene);
		
		btnLogin.setOnAction(e -> {
			try {
				DAOSeguridad.login(tfUser.getText(), pfPass.getText());
				loginStage.close();
			} catch (AeropuertosException e1) {
				System.err.println("CONTRASEÑA INCORRECTA");
			}
		});
		loginStage.setOnCloseRequest(e -> detener = true);
		loginStage.showAndWait();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
