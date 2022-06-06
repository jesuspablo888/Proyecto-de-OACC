package fes.aragon.controlador;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fes.aragon.modelo.FactProd;
import fes.aragon.mysql.Conexion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

public class FacturasProdController implements Initializable{

    @FXML
    private TableColumn<FactProd, Integer> FacturaID;
    
    @FXML
    private TableColumn<FactProd, Integer> productoID;

    @FXML
    private TableColumn<FactProd, Double> cantidadProductos;

    @FXML
    private TableColumn<FactProd, String> comando;

    @FXML
    private TableColumn<FactProd, Date> facturaFecha;

    @FXML
    private TableColumn<FactProd, String> facturaRefencia;

    @FXML
    private TableColumn<FactProd, Double> precio;

    @FXML
    private TableColumn<FactProd, String> producto;

    @FXML
    private TableView<FactProd> tblTablaFacturasProd;

    @FXML
    void nuevaFacturaProd(MouseEvent event) {
    	try {
			Parent parent = FXMLLoader.load(getClass().getResource("/fes/aragon/vista/NuevaFacturaProd.fxml"));
			Scene escena = new Scene(parent);
			Stage escenario = new Stage();

			escenario.initModality(Modality.APPLICATION_MODAL);
			escenario.initOwner(tblTablaFacturasProd.getScene().getWindow());

			escenario.setScene(escena);
			escenario.initStyle(StageStyle.UTILITY);
			escenario.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void refrescarFacturasProd(MouseEvent event) {
    	traerDatosFP();
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.FacturaID.setCellValueFactory(new PropertyValueFactory<>("IdFactura"));
		this.facturaRefencia.setCellValueFactory(new PropertyValueFactory<>("referencia"));
		this.facturaFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
		this.producto.setCellValueFactory(new PropertyValueFactory<>("NombreP"));
		this.precio.setCellValueFactory(new PropertyValueFactory<>("Preciop"));
		this.cantidadProductos.setCellValueFactory(new PropertyValueFactory<>("Cantidad"));
		this.productoID.setCellValueFactory(new PropertyValueFactory<>("IdProducto"));
		
		Callback<TableColumn<FactProd, String>, TableCell<FactProd, String>> celda = (
				TableColumn<FactProd, String> parametros) -> {
			final TableCell<FactProd, String> cel = new TableCell<FactProd, String>() {

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
							FactProd facprod = tblTablaFacturasProd.getSelectionModel().getSelectedItem();
							System.out.println("idF: "+facprod.getIdFactura()+" idP: "+facprod.getIdProducto());
							borrarFactP(facprod.getIdFactura(),facprod.getIdProducto());
						});
						modificarIcono.setOnMouseClicked((MouseEvent evento) -> {
							System.out.println("evento modificar");
							FactProd facprod = tblTablaFacturasProd.getSelectionModel().getSelectedItem();
							modificarFactP(facprod);
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
		this.traerDatosFP();		
	}

	private void traerDatosFP() {
		try {
			Conexion cnn = new Conexion();
			this.tblTablaFacturasProd.getItems().clear();
			this.tblTablaFacturasProd.setItems(cnn.todosFacturaProd());
		} catch (Exception e) {
			Alert alerta = new Alert(Alert.AlertType.WARNING);
			alerta.setTitle("Problema");
			alerta.setHeaderText("Error en la app");
			alerta.setContentText("Consulta al fabricante, por favor.");
			alerta.showAndWait();
			e.printStackTrace();
		}

	}

	private void borrarFactP(Integer idF, Integer idP) {
		try {
			Conexion cnn = new Conexion();
			System.out.println("idF: "+idF+" idP: "+idP);
			cnn.eliminarFacturaProd(idF,idP);
			this.traerDatosFP();
		} catch (Exception e) {
			Alert alerta = new Alert(Alert.AlertType.WARNING);
			alerta.setTitle("Problema en B.D,");
			alerta.setHeaderText("Error en la app");
			alerta.setContentText("Consulta al fabricante, por favor.");
			alerta.showAndWait();
			e.printStackTrace();
		}

	}

	private void modificarFactP(FactProd produ) {
		try {
			FXMLLoader alta = new FXMLLoader(getClass().getResource("/fes/aragon/vista/NuevaFacturaProd.fxml"));
			Parent parent = (Parent) alta.load();
			((NuevoFacturaProdController) alta.getController()).modificarFacProducto(produ);
			Scene escena = new Scene(parent);
			Stage escenario = new Stage();

			escenario.initModality(Modality.APPLICATION_MODAL);
			escenario.initOwner(tblTablaFacturasProd.getScene().getWindow());

			escenario.setScene(escena);
			escenario.initStyle(StageStyle.UTILITY);
			escenario.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
    

}
