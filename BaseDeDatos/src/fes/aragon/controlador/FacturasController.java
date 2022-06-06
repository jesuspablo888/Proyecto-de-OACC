package fes.aragon.controlador;

import static javafx.scene.control.ButtonType.OK;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fes.aragon.modelo.Facturas;
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

public class FacturasController implements Initializable{

    @FXML
    private TableColumn<Facturas, Integer> FacturaID;

    @FXML
    private TableColumn<Facturas, Integer> clienteFacturaID;

    @FXML
    private TableColumn<Facturas, String> comando;

    @FXML
    private TableColumn<Facturas, Date> facturaFecha;

    @FXML
    private TableColumn<Facturas, String> facturaClienteAP;

    @FXML
    private TableColumn<Facturas, String> facturaNomCliente;

    @FXML
    private TableColumn<Facturas, String> facturaRefencia;

    @FXML
    private TableView<Facturas> tblTablaFacturas;

    @FXML
    void buscarFactura(MouseEvent event) {

    }

    @FXML
    void nuevaFactura(MouseEvent event) {
    	try {
			Parent parent = FXMLLoader.load(getClass().getResource("/fes/aragon/vista/NuevaFactura.fxml"));
			Scene escena = new Scene(parent);
			Stage escenario = new Stage();

			escenario.initModality(Modality.APPLICATION_MODAL);
			escenario.initOwner(tblTablaFacturas.getScene().getWindow());

			escenario.setScene(escena);
			escenario.initStyle(StageStyle.UTILITY);
			escenario.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void refrescarFacturas(MouseEvent event) {
    	traerDatosF();
    }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.FacturaID.setCellValueFactory(new PropertyValueFactory<>("idF"));
		this.facturaRefencia.setCellValueFactory(new PropertyValueFactory<>("referencia"));
		this.facturaFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
		this.clienteFacturaID.setCellValueFactory(new PropertyValueFactory<>("idCl"));
		this.facturaNomCliente.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		this.facturaClienteAP.setCellValueFactory(new PropertyValueFactory<>("apellido"));

		Callback<TableColumn<Facturas, String>, TableCell<Facturas, String>> celda = (
				TableColumn<Facturas, String> parametros) -> {
			final TableCell<Facturas, String> cel = new TableCell<Facturas, String>() {

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
							Facturas fac = tblTablaFacturas.getSelectionModel().getSelectedItem();
							borrarFact(fac.getIdF());
						});
						modificarIcono.setOnMouseClicked((MouseEvent evento) -> {
							System.out.println("evento modificar");
							Facturas fac = tblTablaFacturas.getSelectionModel().getSelectedItem();
							modificarFact(fac);
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
		this.traerDatosF();

	}
	
	private void traerDatosF() {
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

	private void borrarFact(Integer id) {
		Alert alerta = new Alert(Alert.AlertType.WARNING);
		try {
			Alert alertaC = new Alert(Alert.AlertType.CONFIRMATION);
			alertaC.setTitle("CUIDADO");
			alertaC.setHeaderText("OJO, ESTA SEGURO QUE LA QUIERE ELIMINAR?");
			alertaC.setContentText("Afectara a Facturas-Producto si es que se esta usando");
			Conexion cnn = new Conexion();
			Optional<ButtonType> resultado=alertaC.showAndWait();
	        if(resultado.get().equals(OK)){
				cnn.eliminarFactura(id);
	        }
			this.traerDatosF();
		}catch (Exception e) {
			alerta.setTitle("Problema en B.D,");
			alerta.setHeaderText("Error en la app");
			alerta.setContentText("Consulta al fabricante, por favor.");
			alerta.showAndWait();
			e.printStackTrace();
		}

	}

	private void modificarFact(Facturas produ) {
		try {
			FXMLLoader alta = new FXMLLoader(getClass().getResource("/fes/aragon/vista/NuevaFactura.fxml"));
			Parent parent = (Parent) alta.load();
			((NuevoFacturaController) alta.getController()).modificarFac(produ);
			Scene escena = new Scene(parent);
			Stage escenario = new Stage();

			escenario.initModality(Modality.APPLICATION_MODAL);
			escenario.initOwner(tblTablaFacturas.getScene().getWindow());

			escenario.setScene(escena);
			escenario.initStyle(StageStyle.UTILITY);
			escenario.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
}
