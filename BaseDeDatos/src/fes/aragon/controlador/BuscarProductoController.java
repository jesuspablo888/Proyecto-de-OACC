package fes.aragon.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class BuscarProductoController {

	@FXML
	private TextField txtNombreBuscar;
	@FXML
	private Button btnBuscar;

	@FXML
	void buscarProd(ActionEvent event) {
		if (validar()) {
			Node source = (Node) event.getSource();
			Stage stageBP = (Stage) source.getScene().getWindow();
			ProductosController.patron = txtNombreBuscar.getText();
			stageBP.close();
		} else {
			Alert alerta = new Alert(Alert.AlertType.INFORMATION);
			alerta.setTitle("ERROR");
			alerta.setHeaderText(null);
			alerta.setContentText("Lo siento, no deve estar vacia la casilla");
			alerta.showAndWait();
		}

	}

	private boolean validar() {
		boolean validos = true;
		if (this.txtNombreBuscar.getText().isEmpty() || this.txtNombreBuscar.getText().regionMatches(0, " ", 0, 1)) {
			validos = false;
		}

		return validos;
	}

}
