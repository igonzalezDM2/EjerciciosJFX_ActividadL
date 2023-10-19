package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dao.DAOAeropuertos;
import enums.TipoAeropuerto;
import excepciones.AeropuertosException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import model.Aeropuerto;
import model.Direccion;

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
    	abrirEditor();
    }

    @FXML
    void anadirAvion(ActionEvent event) {

    }

    @FXML
    void buscar(KeyEvent event) {
    	filtrarFilas();
    }

    @FXML
    void editarAeropuerto(ActionEvent event) {
//    	abrirEditor();
    }

    @FXML
    void editarAvion(ActionEvent event) {

    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tcAno.setCellValueFactory(new PropertyValueFactory<Aeropuerto, Integer>("anioInauguracion"));
		tcCapacidad.setCellValueFactory(new PropertyValueFactory<Aeropuerto, Integer>("capacidad"));
		tcId.setCellValueFactory(new PropertyValueFactory<Aeropuerto, Integer>("id"));
		tcNombre.setCellValueFactory(new PropertyValueFactory<Aeropuerto, String>("nombre"));
		tcNumSocios.setCellValueFactory(new PropertyValueFactory<Aeropuerto, Integer>("numeroSocios"));
		
		//PARA ESTOS CAMPOS HAY QUE ACCEDER A LA PROPIEDAD DIRECCIÓN, POR LO QUE USO CALLBACKS
		tcCiudad.setCellValueFactory(param -> {
			Aeropuerto aeropuerto = param.getValue();
			Direccion direccion = aeropuerto.getDireccion();
			if (direccion != null && direccion.getCiudad() != null) {
				return new SimpleStringProperty(direccion.getCiudad());
			}
			return new SimpleStringProperty();
		});
		tcNumero.setCellValueFactory(param -> {
			Aeropuerto aeropuerto = param.getValue();
			Direccion direccion = aeropuerto.getDireccion();
			if (direccion != null && direccion.getNumero() > 0) {
				return new SimpleIntegerProperty(direccion.getNumero()).asObject();
			}
			return new SimpleIntegerProperty().asObject();
		});
		tcPais.setCellValueFactory(param -> {
			Aeropuerto aeropuerto = param.getValue();
			Direccion direccion = aeropuerto.getDireccion();
			if (direccion != null && direccion.getPais() != null) {
				return new SimpleStringProperty(direccion.getPais());
			}
			return new SimpleStringProperty();
		});
		
		tipoAeropuerto.selectedToggleProperty().addListener(e -> filtrarFilas());
		
		//PARA CARGAR POR PRIMERA VEZ
		filtrarFilas();
		
		
	}

	private void filtrarFilas() {
		String busqueda = tfNombre.getText() != null ? tfNombre.getText().toLowerCase() : "";
		tvAeropuertos.getItems().clear();
		TipoAeropuerto tipo = rbPrivados == tipoAeropuerto.getSelectedToggle() ? TipoAeropuerto.PRIVADO : TipoAeropuerto.PUBLICO;
		try {
			tvAeropuertos.getItems().addAll(DAOAeropuertos.getAeropuertos(tipo, busqueda));
		} catch (AeropuertosException e) {
			e.printStackTrace();
		}
	}
	
	private void abrirEditor() {
		FlowPane root;
		try {
			root = (FlowPane)FXMLLoader.load(getClass().getResource("/fxml/AnadirAeropuerto.fxml"));
			Stage stage = new Stage();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

