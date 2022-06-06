package fes.aragon.controlador;

import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

import fes.aragon.modelo.FactProd;
import fes.aragon.modelo.Facturas;
import fes.aragon.modelo.Productos;
import fes.aragon.mysql.Conexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class NuevoFacturaProdController implements Initializable{

    @FXML
    private TableColumn<Facturas, Integer> FacturaID;

    @FXML
    private TableColumn<Facturas, Integer> clienteFacturaID;

    @FXML
    private TableColumn<Facturas, Date> facturaFecha;

    @FXML
    private TableColumn<Facturas, String> facturaClienteAP;

    @FXML
    private TableColumn<Facturas, String> facturaNomCliente;

    @FXML
    private TableColumn<Facturas, String> facturaRefencia;

    @FXML
    private Label lbModifica;

    @FXML
    private Label lbNueva;

    @FXML
    private TableColumn<Productos, Integer> productoID;

    @FXML
    private TableColumn<Productos, String> productoNombre;

    @FXML
    private TableColumn<Productos, Double> productoPrecio;

    @FXML
    private TableView<Facturas> tblTablaFacturas;

    @FXML
    private TableView<Productos> tblTablaProductos;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtCantidad;

    @FXML
    private TextField txtFecha;

    @FXML
    private TextField txtProducto;

    @FXML
    private TextField txtPatronBusquedaF;

    @FXML
    private TextField txtPatronBusquedaP;

    @FXML
    private TextField txtReferencia;
    
    private FactProd facProducto = null;
	
    public static String patron="";
    public static String patronF="";
    
	private Alert alertaG = new Alert(Alert.AlertType.INFORMATION);

    @FXML
    void buscarFactu(ActionEvent event) {
    	patronF=txtPatronBusquedaF.getText();
    	try {
			System.out.println("patronF: "+patronF);
			Conexion cnn = new Conexion();
			this.tblTablaFacturas.getItems().clear();
			this.tblTablaFacturas.setItems(cnn.buscarFact(patronF));
	} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			Alert alerta = new Alert(Alert.AlertType.WARNING);
			alerta.setTitle("Problema");
			alerta.setHeaderText("Error en la app 3312");
			alerta.setContentText("Consulta al fabricante, por favor.");
			alerta.showAndWait();
			e.printStackTrace();
		}
    }

    @FXML
    void buscarProd(ActionEvent event) {}
    

    @FXML
    void guardarFacturaProd(ActionEvent event) {
    	if(facProducto == null) {
    		facProducto = new FactProd();
    		//System.out.print("si es null idPr: ");
    		//System.out.println(facProducto.getFactura() == null && facProducto.getProducto()==null);
		}
		
		if (validar()) {
			if(facProducto.getFactura() == null && facProducto.getProducto()==null) {
				System.out.println("Almacena FP");
				almacenar(event);
			}else {
				modificar(event);
			}
		}else {
			alertaG.setTitle("ERROR");
			alertaG.setHeaderText(null);
			alertaG.setContentText("Lo siento, no deben estar vacías las casillas.");
			alertaG.showAndWait();
		}


	}

    @FXML
    void limpiarFacturaProd(ActionEvent event) {
    	limpiar();
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.FacturaID.setCellValueFactory(new PropertyValueFactory<>("idF"));
		this.facturaRefencia.setCellValueFactory(new PropertyValueFactory<>("referencia"));
		this.facturaFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
		this.clienteFacturaID.setCellValueFactory(new PropertyValueFactory<>("idCl"));
		this.facturaNomCliente.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		this.facturaClienteAP.setCellValueFactory(new PropertyValueFactory<>("apellido"));
		
		this.tblTablaFacturas.getSelectionModel().selectedItemProperty().addListener((obj, oldSeleccion, newSeleccion) -> {
			if (newSeleccion != null) {
				Facturas fr = tblTablaFacturas.getSelectionModel().getSelectedItem();
				txtReferencia.setText(fr.getReferencia());
				txtFecha.setText(String.valueOf(fr.getFecha()));
				try {
				facProducto.setFactura(fr);
				}catch (NullPointerException e) {
					//se omite el error de no inicializar facProducto, dado que necersitamos que sea nulo
				}
				
			}
		});
		
		this.productoID.setCellValueFactory(new PropertyValueFactory<>("id"));
		this.productoNombre.setCellValueFactory(new PropertyValueFactory<>("nombreP"));
		this.productoPrecio.setCellValueFactory(new PropertyValueFactory<>("preciop"));
		
		this.tblTablaProductos.getSelectionModel().selectedItemProperty().addListener((obj, oldSeleccion, newSeleccion) -> {
			if (newSeleccion != null) {
				Productos pd = tblTablaProductos.getSelectionModel().getSelectedItem();
				txtProducto.setText(pd.getNombreP());
				txtPrecio.setText(String.valueOf(pd.getPreciop()));
				try {
				facProducto.setProducto(pd);
				}catch (NullPointerException e) {
					//se omite el error de no inicializar facProducto, dado que necersitamos que sea nulo
				}
				
			}
		});
		traerDatos();
		traerDatosP();
	}
	
	private void traerDatos() {
		try {
			Conexion cnn = new Conexion();
			this.tblTablaFacturas.getItems().clear();
			this.tblTablaFacturas.setItems(cnn.buscarFacturas());
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
	
	private void traerDatosP() {

	}
	
	private void limpiar() {
		this.txtProducto.setText("");	
		this.txtPrecio.setText("");
		this.txtCantidad.setText("");
		this.txtReferencia.setText("");
		this.txtFecha.setText("");
		this.txtPatronBusquedaF.setText("");
		this.txtPatronBusquedaP.setText("");

	}

	private boolean validar() {
		boolean validos = true;

		if (this.txtReferencia.getText().isEmpty() || this.txtReferencia.getText().regionMatches(0, " ", 0, 1)) {
			validos = false;
		}
		if (this.txtCantidad.getText().isEmpty() || this.txtCantidad.getText().regionMatches(0, " ", 0, 1)) {
			validos = false;
		}
		if (this.txtPrecio.getText().isEmpty() || this.txtPrecio.getText().regionMatches(0, " ", 0, 1)) {
			validos = false;
		}
		return validos;
	}

	private void almacenar(ActionEvent event) {
		
	}

	public void modificarFacProducto(FactProd fac) {

	}
	
	private void modificar(ActionEvent event) {

	}


	
	
	
	
}
