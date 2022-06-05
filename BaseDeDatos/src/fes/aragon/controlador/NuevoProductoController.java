package fes.aragon.controlador;

import fes.aragon.modelo.Productos;
import fes.aragon.mysql.Conexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

public class NuevoProductoController {

    @FXML
    private TextField txtNombreProd;

    @FXML
    private TextField txtPrecio;
    
    @FXML
    private Label lbModifica;

    @FXML
    private Label lbNuevo;
    
	private Productos produ = null;
	
	Alert alertaG = new Alert(Alert.AlertType.INFORMATION);

    @FXML
    void guardarProd(ActionEvent event) {
		if(produ == null) {
			produ = new Productos();
		}
		
		if (validar()) {
			if(produ.getId()==null) {
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

    @FXML
    void limpiarProd(ActionEvent event) {
    	this.limpiar();
    }
    
	private void limpiar() {
		this.txtNombreProd.setText("");
		this.txtPrecio.setText("");
	}

	private boolean validar() {
		boolean validos = true;
		if (this.txtNombreProd.getText().isEmpty() || this.txtNombreProd.getText().regionMatches(0, " ", 0, 1)) {
			validos = false;
		}
		if (this.txtPrecio.getText().isEmpty() || this.txtPrecio.getText().regionMatches(0, " ", 0, 1)) {
			validos = false;
		}
		return validos;
	}

	private void almacenar() {
		try {
			Conexion cnn = new Conexion();
			produ.setNombreP(txtNombreProd.getText());
			produ.setPreciop(Double.parseDouble(txtPrecio.getText()));
			cnn.almacenarProductos(produ);
			alertaG.setTitle("Mensaje");
			alertaG.setHeaderText(null);
			alertaG.setContentText("Se almaceno el producto.");
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

	public void modificarProdu(Productos produ) {
		this.lbNuevo.setVisible(false);
		this.lbModifica.setVisible(true);
		this.produ = produ;
		this.txtNombreProd.setText(produ.getNombreP());
		this.txtPrecio.setText(String.valueOf(produ.getPreciop()));
	}
	
	private void modificar() {
		try {
			Conexion cnn = new Conexion();
			produ.setNombreP(txtNombreProd.getText());
			produ.setPreciop(Double.parseDouble(txtPrecio.getText()));
			cnn.modificarProducto(produ);
			alertaG.setTitle("Mensaje");
			alertaG.setHeaderText(null);
			alertaG.setContentText("Se modifico el producto.");
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
