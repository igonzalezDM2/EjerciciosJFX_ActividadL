package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import model.Aeropuerto;

public class AeropuertosController implements Initializable {

    @FXML
    private MenuItem miAnadirAeropuerto;

    @FXML
    private MenuItem miAnadirAvion;

    @FXML
    private MenuItem miEditarAeropuerto;

    @FXML
    private MenuItem miEditarAvion;

    @FXML
    private RadioButton rbPrivados;

    @FXML
    private RadioButton rbPublicos;

    @FXML
    private TableColumn<Aeropuerto, Integer> tcAno;

    @FXML
    private TableColumn<Aeropuerto, Integer> tcCapacidad;

    @FXML
    private TableColumn<Aeropuerto, String> tcCiudad;

    @FXML
    private TableColumn<Aeropuerto, Integer> tcId;

    @FXML
    private TableColumn<Aeropuerto, String> tcNombre;

    @FXML
    private TableColumn<Aeropuerto, Integer> tcNumSocios;

    @FXML
    private TableColumn<Aeropuerto, Integer> tcNumero;

    @FXML
    private TableColumn<Aeropuerto, String> tcPais;

    @FXML
    private TextField tfNombre;

    @FXML
    private ToggleGroup tipoAeropuerto;

    @FXML
    private TableView<Aeropuerto> tvAeropuertos;

    @FXML
    void anadirAeropuerto(ActionEvent event) {

    }

    @FXML
    void anadirAvion(ActionEvent event) {

    }

    @FXML
    void buscar(InputMethodEvent event) {

    }

    @FXML
    void editarAeropuerto(ActionEvent event) {

    }

    @FXML
    void editarAvion(ActionEvent event) {

    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tcAno.setCellValueFactory(new PropertyValueFactory<Aeropuerto, Integer>("anioInauguracion"));
		tcCapacidad.setCellValueFactory(new PropertyValueFactory<Aeropuerto, Integer>("capacidad"));
		tcCiudad.setCellValueFactory(new PropertyValueFactory<Aeropuerto, String>("ciudad"));
		tcId.setCellValueFactory(new PropertyValueFactory<Aeropuerto, Integer>("id"));
		tcNombre.setCellValueFactory(new PropertyValueFactory<Aeropuerto, String>("nombre"));
		tcNumSocios.setCellValueFactory(new PropertyValueFactory<Aeropuerto, Integer>("numeroSocios"));
		//TODO: ESTO NO VA A FUNCIONAR; USAR UN CALLBACK O AÑADIR UN GET PARA UNA PROPIEDAD QUE ACCEDA DIRECTAMENTE A DIRECCION.LOQUESEA EN LA CLASE AEROPUERTO
//		tcNumero.setCellValueFactory(new PropertyValueFactory<Aeropuerto, Integer>("direccion.numero"));
		
	}

}

