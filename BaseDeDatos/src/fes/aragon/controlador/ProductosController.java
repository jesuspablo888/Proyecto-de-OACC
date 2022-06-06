package fes.aragon.controlador;

import static javafx.scene.control.ButtonType.OK;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fes.aragon.modelo.Productos;
import fes.aragon.mysql.Conexion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

public class ProductosController implements Initializable{

    @FXML
    private TableColumn<Productos, String> comando;

    @FXML
    private TableColumn<Productos, Integer> productoID;

    @FXML
    private TableColumn<Productos, String> productoNombre;

    @FXML
    private TableColumn<Productos, Double> productoPrecio;

    @FXML
    private TableView<Productos> tblTablaProductos;
    
    public static String patron="";
    
    @FXML
    void buscarProducto(MouseEvent event) {
    	try {
			Parent parent = FXMLLoader.load(getClass().getResource("/fes/aragon/vista/BuscarProducto.fxml"));
			Scene escena = new Scene(parent);
			Stage escenario = new Stage();

			escenario.initModality(Modality.APPLICATION_MODAL);
			escenario.initOwner(tblTablaProductos.getScene().getWindow());

			escenario.setScene(escena);
			escenario.initStyle(StageStyle.UTILITY);
			escenario.showAndWait();
			datosBusqueda();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void nuevoProducto(MouseEvent event) {
    	try {
			Parent parent = FXMLLoader.load(getClass().getResource("/fes/aragon/vista/NuevoProducto.fxml"));
			Scene escena = new Scene(parent);
			Stage escenario = new Stage();

			escenario.initModality(Modality.APPLICATION_MODAL);
			escenario.initOwner(tblTablaProductos.getScene().getWindow());

			escenario.setScene(escena);
			escenario.initStyle(StageStyle.UTILITY);
			escenario.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void refrescarProdustos(MouseEvent event) {
    	traerDatosP();
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.productoID.setCellValueFactory(new PropertyValueFactory<>("id"));
		this.productoNombre.setCellValueFactory(new PropertyValueFactory<>("nombreP"));
		this.productoPrecio.setCellValueFactory(new PropertyValueFactory<>("preciop"));

		Callback<TableColumn<Productos, String>, TableCell<Productos, String>> celda = (
				TableColumn<Productos, String> parametros) -> {
			final TableCell<Productos, String> cel = new TableCell<Productos, String>() {

				@Override
				protected void updateItem(String arg0, boolean arg1) {
					super.updateItem(arg0, arg1);
					if (arg1) {
						setGraphic(null);
						setText(null);
					} else {
						FontAwesomeIconView borrarIcono = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
						FontAwesomeIconView modificarIcono = new FontAwesomeIconView(FontAwesomeIcon.PENCIL);
						borrarIcono.setGlyphStyle("-fx-cursor:hand;" + "-glyph-size:18px;" + "-fx-fill:RED;");
						modificarIcono.setGlyphStyle("-fx-cursor:hand;" + "-glyph-size:18px;" + "-fx-fill:#0a1ce8;");
						borrarIcono.setOnMouseClicked((MouseEvent evento) -> {
							System.out.println("evento borrar");
							Productos produ = tblTablaProductos.getSelectionModel().getSelectedItem();
							borrarProducto(produ.getId());
						});
						modificarIcono.setOnMouseClicked((MouseEvent evento) -> {
							System.out.println("evento modificar");
							Productos produ = tblTablaProductos.getSelectionModel().getSelectedItem();
							modificarProducto(produ);
						});
						HBox hbox = new HBox(borrarIcono, modificarIcono);
						hbox.setStyle("-fx-alignment:center");
						HBox.setMargin(borrarIcono, new Insets(2, 2, 0, 3));
						HBox.setMargin(modificarIcono, new Insets(2, 3, 0, 2));
						setGraphic(hbox);
						setText(null);

					}
				}

			};
			return cel;
		};
		this.comando.setCellFactory(celda);
		this.traerDatosP();
	}
	
	private void traerDatosP() {
		try {
			Conexion cnn = new Conexion();
			this.tblTablaProductos.getItems().clear();
			this.tblTablaProductos.setItems(cnn.todosProductos());
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

	private void borrarProducto(int id) {
		Alert alerta = new Alert(Alert.AlertType.WARNING);
		try {
			Alert alertaC = new Alert(Alert.AlertType.CONFIRMATION);
			alertaC.setTitle("CUIDADO");
			alertaC.setHeaderText("OJO, ESTA SEGURO QUE LA QUIERE ELIMINAR?");
			alertaC.setContentText("Afectara a Facturas-Producto si es que se esta usando");
			Conexion cnn = new Conexion();
			Optional<ButtonType> resultado=alertaC.showAndWait();
	        if(resultado.get().equals(OK)){
				cnn.eliminarProducto(id);
	        }
			this.traerDatosP();
		} catch (Exception e) {
			alerta.setTitle("Problema en B.D");
			alerta.setHeaderText("Error en la app");
			alerta.setContentText("Consulta al fabricante, por favor.");
			alerta.showAndWait();
			e.printStackTrace();
		}

	}

	private void modificarProducto(Productos produ) {
		try {
			FXMLLoader alta = new FXMLLoader(getClass().getResource("/fes/aragon/vista/NuevoProducto.fxml"));
			Parent parent = (Parent) alta.load();
			((NuevoProductoController) alta.getController()).modificarProdu(produ);
			Scene escena = new Scene(parent);
			Stage escenario = new Stage();

			escenario.initModality(Modality.APPLICATION_MODAL);
			escenario.initOwner(tblTablaProductos.getScene().getWindow());

			escenario.setScene(escena);
			escenario.initStyle(StageStyle.UTILITY);
			escenario.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void datosBusqueda() {
		try {
			System.out.println("patron: "+patron);
			Conexion cnn = new Conexion();
			this.tblTablaProductos.getItems().clear();
			this.tblTablaProductos.setItems(cnn.buscarProductos(patron));
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

}
