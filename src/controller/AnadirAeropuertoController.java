package controller;

import static utilities.Utilidades.parseDouble;
import static utilities.Utilidades.parseInt;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dao.DAOAeropuertos;
import excepciones.AeropuertosException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import model.Aeropuerto;
import model.Direccion;

public class AnadirAeropuertoController implements Initializable {


    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnGuardar;

    @FXML
    private GridPane gridVariable;

    @FXML
    private Label labelVariable1;

    @FXML
    private Label labelVariable2;

    @FXML
    private RadioButton rbPrivado;

    @FXML
    private RadioButton rbPublico;

    @FXML
    private TextField tfAno;

    @FXML
    private TextField tfCalle;

    @FXML
    private TextField tfCapacidad;

    @FXML
    private TextField tfCiudad;

    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfNumero;

    @FXML
    private TextField tfPais;

    @FXML
    private TextField tfVariable1;

    @FXML
    private TextField tfVariable2;

    @FXML
    private ToggleGroup tgTipo;

    @FXML
    void cancelar(ActionEvent event) {

    }

    @FXML
    void guardar(ActionEvent event) {
    	try {
			comprobarDatos();
			Aeropuerto aeropuerto = construirAeropuerto();
			DAOAeropuertos.anadirAeropuerto(aeropuerto);
			//TODO: AVISAR DE QUE SE HA INSERTADO Y CERRAR LA MODAL
			
		} catch (AeropuertosException e) {
    		Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
    		alert.showAndWait();
		}
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tgTipo.selectedToggleProperty().addListener(e -> {
			if (rbPrivado == tgTipo.getSelectedToggle()) {
				labelVariable1.setText("Número de socios");
				tfVariable1.setText("");
				labelVariable2.setVisible(false);
				tfVariable2.setVisible(false);
			} else {
				labelVariable1.setText("Financiación:");
				tfVariable1.setText("");
				labelVariable2.setText("Número de trabajadores:");
				tfVariable2.setText("");
				labelVariable2.setVisible(true);
				tfVariable2.setVisible(true);
			}
		});
	}
	
	private void comprobarDatos() throws AeropuertosException {
		checkCampoStrNotNull(tfNombre);
		checkCampoStrNotNull(tfPais);
		checkCampoStrNotNull(tfCiudad);
		checkCampoStrNotNull(tfCalle);
		checkCampoInt(tfNumero);
		checkCampoInt(tfAno);
		checkCampoInt(tfCapacidad);
		if (rbPrivado.isSelected()) {
			checkCampoInt(tfVariable1);
		} else {
			checkCampoDouble(tfVariable1);
			checkCampoInt(tfVariable2);
		}
	}
	
	private Aeropuerto construirAeropuerto() throws AeropuertosException {
		Aeropuerto aeropuerto = new Aeropuerto()
				.setNombre(tfNombre.getText())
				.setDireccion(new Direccion()
						.setPais(tfPais.getText())
						.setCiudad(tfCiudad.getText())
						.setCalle(tfCalle.getText())
						.setNumero(parseInt(tfNumero.getText()))
						)
				.setAnioInauguracion(parseInt(tfAno.getText()))
				.setCapacidad(parseInt(tfCapacidad.getText()));
		if (rbPrivado.isSelected()) {
			aeropuerto.setNumeroSocios(parseInt(tfVariable1.getText()));
		} else {
			aeropuerto.setFinanciacion(parseDouble(tfVariable1.getText()));
			aeropuerto.setNumTrabajadores(parseInt(tfVariable2.getText()));
		}
		return aeropuerto;
	}
	
	
	private void checkCampoDouble(TextField tf) throws AeropuertosException {
		String strNum = tf.getText();
		Pattern doublePattern = Pattern.compile("\\d+(\\.\\d+)?");
		Matcher matcher = doublePattern.matcher(strNum);
		if (!matcher.matches()) {
			throw new AeropuertosException("El campo " + tf.getId() + " contiene un formato incorrecto o está vacío");
		}
	}
	
	private void checkCampoInt(TextField tf) throws AeropuertosException {
		String strNum = tf.getText();
		Pattern intPattern = Pattern.compile("\\d+");
		Matcher matcher = intPattern.matcher(strNum);
		if (!matcher.matches()) {
			throw new AeropuertosException("El campo " + tf.getId() + " contiene un formato incorrecto o está vacío");
		}
	}
	
	private void checkCampoStrNotNull(TextField tf) throws AeropuertosException {
		String str = tf.getText();
		if (str == null || str.isBlank()) {
			throw new AeropuertosException("El campo" + tf.getId() + " está vacío");
		}
	}

}
