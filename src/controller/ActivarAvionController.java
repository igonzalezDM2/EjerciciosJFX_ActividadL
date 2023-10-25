package controller;

import java.net.URL;
import java.util.ResourceBundle;

import dao.DAOAeropuertos;
import dao.DAOAviones;
import excepciones.AeropuertosException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import model.Aeropuerto;
import model.Avion;

public class ActivarAvionController implements Initializable {

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnGuardar;

    @FXML
    private ComboBox<Aeropuerto> cbAeropuertos;

    @FXML
    private ComboBox<Avion> cbAviones;

    @FXML
    private RadioButton rbActivado;

    @FXML
    private RadioButton rbDesactivado;

    @FXML
    private ToggleGroup tgActivo;

	private AeropuertosController controladorPrincipal;

    @FXML
    void cancelar(ActionEvent event) {

    }

    @FXML
    void guardar(ActionEvent event) {

    }
    
    @FXML
    void cambiarAviones(ActionEvent event) {
		Aeropuerto aeropuerto = cbAeropuertos.getSelectionModel().getSelectedItem();
		try {
			cbAviones.getItems().clear();
			cbAviones.getItems().addAll(DAOAviones.getAviones(aeropuerto));
			cbAviones.getSelectionModel().selectFirst();
		} catch (AeropuertosException e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
			alert.showAndWait();
			e.printStackTrace();
		}
    }
    
	public ActivarAvionController setControladorPrincipal(AeropuertosController controladorPrincipal) {
		this.controladorPrincipal = controladorPrincipal;
		return this;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			cbAeropuertos.getItems().addAll(DAOAeropuertos.getAeropuertos(null));
			cbAeropuertos.getSelectionModel().selectFirst();
			cambiarAviones(null);
		} catch (AeropuertosException e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
			alert.showAndWait();
		}
	}

}
