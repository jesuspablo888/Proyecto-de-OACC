package fes.aragon.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import fes.aragon.modelo.Clientes;
import fes.aragon.mysql.Conexion;
import javafx.event.ActionEvent;

public class NuevoUsuarioController {
	@FXML
	private TextField txtNombre;
	@FXML
	private TextField txtApellidoPaterno;
    @FXML
    private Label txtModificar;
    @FXML
    private Label txtNuevo;

	private Clientes cliente = null;
	private Alert alertaG = new Alert(Alert.AlertType.INFORMATION);

	// Event Listener on Button.onAction
	@FXML
	public void accionGuardar(ActionEvent event) {
		if(cliente == null) {
			cliente = new Clientes();
		}
		
		if (validar()) {
			if(cliente.getId()==null) {
				almacenar();
				limpiar();
			}else {
				modificar();
			}
		}else {
			alertaG.setTitle("ERROR");
			alertaG.setHeaderText(null);
			alertaG.setContentText("Lo siento, no debe estar vacía la casilla");
			alertaG.showAndWait();
		}
	}

	// Event Listener on Button.onAction
	@FXML
	public void accionLimpiar(ActionEvent event) {
		this.limpiar();
	}

	private void almacenar() {
		try {
			Conexion cnn = new Conexion();
			cliente.setNombre(txtNombre.getText());
			cliente.setApellidoPaterno(txtApellidoPaterno.getText());
			cnn.almacenarClientes(cliente);
			alertaG.setTitle("Mensaje");
			alertaG.setHeaderText(null);
			alertaG.setContentText("Se almaceno el cliente.");
			alertaG.showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
			alertaG.setTitle("ERROR");
			alertaG.setHeaderText(null);
			alertaG.setContentText("Lo siento, ocurrio un problema.");
			alertaG.showAndWait();
			e.printStackTrace();
		}

	}

	private void limpiar() {
		this.txtNombre.setText("");
		this.txtApellidoPaterno.setText("");
	}

	private boolean validar() {
		boolean validos = true;
		if (this.txtNombre.getText().isEmpty() || this.txtNombre.getText().regionMatches(0, " ", 0, 1)) {
			validos = false;
		}
		if (this.txtApellidoPaterno.getText().isEmpty() || this.txtApellidoPaterno.getText().regionMatches(0, " ", 0, 1)) {
			validos = false;
		}
		return validos;
	}

	public void modificarCliente(Clientes cliente) {
		this.txtNuevo.setVisible(false);
		this.txtModificar.setVisible(true);
		this.cliente = cliente;
		this.txtNombre.setText(cliente.getNombre());
		this.txtApellidoPaterno.setText(cliente.getApellidoPaterno());
	}
	
	private void modificar() {
		try {
			Conexion cnn = new Conexion();
			cliente.setNombre(txtNombre.getText());
			cliente.setApellidoPaterno(txtApellidoPaterno.getText());
			cnn.modificarCliente(cliente);
			alertaG.setTitle("Mensaje");
			alertaG.setHeaderText(null);
			alertaG.setContentText("Se modifico el cliente.");
			alertaG.showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
			alertaG.setTitle("ERROR");
			alertaG.setHeaderText(null);
			alertaG.setContentText("Lo siento, ocurrio un problema.");
			alertaG.showAndWait();
			e.printStackTrace();
		}

	}
}