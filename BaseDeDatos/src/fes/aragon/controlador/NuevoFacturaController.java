package fes.aragon.controlador;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

import fes.aragon.modelo.Clientes;
import fes.aragon.modelo.Facturas;
import fes.aragon.mysql.Conexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class NuevoFacturaController implements Initializable{

	@FXML
	private TableColumn<Clientes, String> clienmteApellidoPaterno;

	@FXML
	private TableColumn<Clientes, Integer> clienteID;

	@FXML
	private TableColumn<Clientes, String> clienteNombre;

	@FXML
	private Label lbModifica;

	@FXML
	private Label lbNueva;

	@FXML
	private TableView<Clientes> tblTablaCliente;

	@FXML
	private TextField txtApellidoCliente;

	@FXML
	private DatePicker txtFecha;

	@FXML
	private TextField txtNombreCliente;

	@FXML
	private TextField txtPatronBusqueda;

	@FXML
	private TextField txtReferencia;

	private Facturas fac = null;
	
    public static String patron="";

	private Alert alertaG = new Alert(Alert.AlertType.INFORMATION);

	@FXML
	void buscarFactu(ActionEvent event) {
		patron = txtPatronBusqueda.getText();
		try {
			System.out.println("patron: "+patron);
			Conexion cnn = new Conexion();
			this.tblTablaCliente.getItems().clear();
			this.tblTablaCliente.setItems(cnn.buscarClientes(patron));
	} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			Alert alerta = new Alert(Alert.AlertType.WARNING);
			alerta.setTitle("Problema");
			alerta.setHeaderText("Error en la app 3313");
			alerta.setContentText("Consulta al fabricante, por favor.");
			alerta.showAndWait();
			e.printStackTrace();
		}
	}
	

	@FXML
	void guardarFactura(ActionEvent event) {
    	if(fac == null) {
    		fac = new Facturas();
    		System.out.print("si es null idF: ");
    		System.out.println(fac.getIdF() );
		}
		
		if (validar()) {
			if(fac.getIdF()==null) {
				almacenar();
			}else {
				modificar();
			}
		}else {
			alertaG.setTitle("ERROR");
			alertaG.setHeaderText(null);
			alertaG.setContentText("Lo siento, no deben estar vacías las casillas.");
			alertaG.showAndWait();
		}


	}

	@FXML
	void limpiarFactura(ActionEvent event) {
		limpiar();

	}

	private void limpiar() {
		this.txtNombreCliente.setText("");
		this.txtReferencia.setText("");
		this.txtFecha.setValue(null);
		this.txtPatronBusqueda.setText("");
		this.txtApellidoCliente.setText("");
	}

	private boolean validar() {
		boolean validos = true;

		if (this.txtReferencia.getText().isEmpty() || this.txtReferencia.getText().regionMatches(0, " ", 0, 1)) {
			validos = false;
		}
		if (this.txtFecha.getValue() == null || this.txtFecha.getValue().toString().regionMatches(0, " ", 0, 1)) {
			validos = false;
		}
		if (this.txtNombreCliente.getText().isEmpty() || this.txtNombreCliente.getText().regionMatches(0, " ", 0, 1)) {
			validos = false;
		}
		return validos;
	}
	
	private void almacenar() {
		try {
			Conexion cnn = new Conexion();
			fac.setCliente(this.tblTablaCliente.getSelectionModel().getSelectedItem());
			fac.setReferencia(txtReferencia.getText());

			fac.setFecha(Date.valueOf(txtFecha.getValue()));
			cnn.almacenarFactura(fac);
			alertaG.setTitle("Mensaje");
			alertaG.setHeaderText(null);
			alertaG.setContentText("Se almaceno la factura.");
			alertaG.showAndWait();
			limpiar();
		} catch (Exception e) {
			e.printStackTrace();
			alertaG.setTitle("ERROR");
			alertaG.setHeaderText(null);
			alertaG.setContentText("Lo siento, ocurrio un problema en el almacenamiento.");
			alertaG.showAndWait();
			e.printStackTrace();
		}
	}

	public void modificarFac(Facturas fac) {
		this.lbNueva.setVisible(false);
		this.lbModifica.setVisible(true);
		this.fac = fac;
		this.txtNombreCliente.setText(String.valueOf(fac.getNombre()));
		this.txtApellidoCliente.setText(String.valueOf(fac.getApellido()));
		this.txtReferencia.setText(fac.getReferencia());
		this.txtFecha.setValue(fac.getFecha().toLocalDate() );
	}
	
	private void modificar() {
		try {
			Conexion cnn = new Conexion();
			
			this.tblTablaCliente.getSelectionModel().selectedItemProperty().addListener((obj, oldSeleccion, newSeleccion) -> {
				if (newSeleccion != null) {
					Clientes cl = tblTablaCliente.getSelectionModel().getSelectedItem();
					fac.setCliente(cl);

				}else {
					fac.setCliente(fac.getCliente());
				}
			});
			
			fac.setReferencia(txtReferencia.getText());
			fac.setFecha(Date.valueOf(txtFecha.getValue()));
			cnn.modificarFactura(fac);
			
			alertaG.setTitle("Mensaje");
			alertaG.setHeaderText(null);
			alertaG.setContentText("Se modifico la factura.");
			alertaG.showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
			alertaG.setTitle("ERROR");
			alertaG.setHeaderText(null);
			alertaG.setContentText("Lo siento, ocurrio un problema en la modificacion.");
			alertaG.showAndWait();
			e.printStackTrace();
		}

	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		this.clienteID.setCellValueFactory(new PropertyValueFactory<>("id"));
		this.clienteNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		this.clienmteApellidoPaterno.setCellValueFactory(new PropertyValueFactory<>("apellidoPaterno"));
		
		this.tblTablaCliente.getSelectionModel().selectedItemProperty().addListener((obj, oldSeleccion, newSeleccion) -> {
			if (newSeleccion != null) {
				Clientes cl = tblTablaCliente.getSelectionModel().getSelectedItem();
				txtNombreCliente.setText(cl.getNombre());
				txtApellidoCliente.setText(cl.getApellidoPaterno());
				try {
				fac.setCliente(cl);
				}catch (NullPointerException e) {
					//se omite el error de no inicializar fac, dado que necesitamos que sea nulo
				}
			}
		});
		
		this.traerDatos();
	}

	private void traerDatos() {
		try {
			Conexion cnn = new Conexion();
			this.tblTablaCliente.getItems().clear();
			this.tblTablaCliente.setItems(cnn.todosClientes());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			Alert alerta = new Alert(Alert.AlertType.WARNING);
			alerta.setTitle("Problema");
			alerta.setHeaderText("Error en la app");
			alerta.setContentText("Consulta al fabricante, por favor.");
			alerta.showAndWait();
			e.printStackTrace();
		}

	}
	

	

}
