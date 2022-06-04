package fes.aragon.controlador; 

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.event.ActionEvent;

public class PrincipalController {
	@FXML
	private BorderPane idPrincipal;
	@FXML
	private Button btnClientes;
	@FXML
	private Button btnFactura;
	@FXML
	private Button btnFacturaProd;
	@FXML
	private Button btnProducto;

	// Event Listener on Button[#btnClientes].onAction
	@FXML
	public void accionCliente(ActionEvent event) {
		Contenido contenido = new Contenido("/fes/aragon/vista/Cliente.fxml");
		idPrincipal.setCenter(contenido);
	}
	// Event Listener on Button[#btnFactura].onAction
	@FXML
	public void accionFactura(ActionEvent event) {
		Contenido contenido = new Contenido("/fes/aragon/vista/Facturas.fxml");
		idPrincipal.setCenter(contenido);
	}
	// Event Listener on Button[#btnFacturaProd].onAction
	@FXML
	public void accionFacturaProd(ActionEvent event) {
		Contenido contenido = new Contenido("/fes/aragon/vista/FacturasProd.fxml");
		idPrincipal.setCenter(contenido);
	}
	// Event Listener on Button[#btnProducto].onAction
	@FXML
	public void accionProducto(ActionEvent event) {
		Contenido contenido = new Contenido("/fes/aragon/vista/Productos.fxml");
		idPrincipal.setCenter(contenido);
	}
}