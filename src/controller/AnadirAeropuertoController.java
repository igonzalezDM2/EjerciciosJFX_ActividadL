package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;

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

}
