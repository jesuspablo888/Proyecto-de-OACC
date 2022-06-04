package fes.aragon.controlador;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class Contenido extends Pane {

	public Contenido(String ruta) {
		FXMLLoader fxml=new FXMLLoader(getClass().getResource(ruta));
		fxml.setRoot(this);
		try {
			fxml.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}