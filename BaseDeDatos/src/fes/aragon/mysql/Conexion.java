package fes.aragon.mysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import fes.aragon.modelo.Clientes;
import fes.aragon.modelo.Facturas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Conexion {
	private String url = "jdbc:mysql://127.0.0.1:3306/ventas?serverTimezone=UTC";
	private String usuario = "root";
	private String psw = "Pablo123#";
	private Connection conexion = null;

	public Conexion() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		conexion = DriverManager.getConnection(url, usuario, psw);
	}

	// CLIENTES

	public ObservableList<Clientes> todosClientes() throws SQLException {
		ObservableList<Clientes> lista = FXCollections.observableArrayList();
		String query = "{call todosClientes}";
		CallableStatement solicitud = conexion.prepareCall(query);
		ResultSet datos = solicitud.executeQuery(query);
		if (!datos.next()) {
			System.out.println("no hay datos");
		} else {
			do {
				Clientes cl = new Clientes();
				cl.setId(Integer.parseInt(datos.getString(1)));
				cl.setNombre(datos.getString(2));
				cl.setApellidoPaterno(datos.getString(3));
				lista.add(cl);
			} while (datos.next());
		}
		datos.close();
		solicitud.close();
		conexion.close();
		return lista;
	}
	
	public ObservableList<Clientes> buscarClientes(String patron) throws SQLException {
		ObservableList<Clientes> lista = FXCollections.observableArrayList();
		String queryBP = "{call buscarClientes(?)}";
		CallableStatement solicitud = conexion.prepareCall(queryBP);
		solicitud.setString(1, patron);
		ResultSet datos = solicitud.executeQuery();
		if (!datos.next()) {
			System.out.println("no hay datos");
		} else {
			do {
				Clientes cl = new Clientes();
				cl.setId(Integer.parseInt(datos.getString(1)));
				cl.setNombre(datos.getString(2));
				cl.setApellidoPaterno(datos.getString(3));
				lista.add(cl);
			} while (datos.next());
		}
		datos.close();
		solicitud.close();
		conexion.close();
		return lista;
	}

	public void almacenarClientes(Clientes cliente) throws SQLException {
		String query = "{call insertarClientes(?,?)}";
		CallableStatement solicitud = conexion.prepareCall(query);
		solicitud.setString(1, cliente.getNombre());
		solicitud.setString(2, cliente.getApellidoPaterno());
		solicitud.execute();
		solicitud.close();
		conexion.close();
	}

	public void eliminarCliente(int id) throws SQLException {
		String query = "{call eliminarClientes(?)}";
		CallableStatement solicitud = conexion.prepareCall(query);
		solicitud.setInt(1, id);
		solicitud.execute();
		solicitud.close();
		conexion.close();

	}

	public void modificarCliente(Clientes cliente) throws SQLException {
		String query = "{call modificarClientes(?,?,?)}";
		CallableStatement solicitud = conexion.prepareCall(query);
		solicitud.setInt(1, cliente.getId());
		solicitud.setString(2, cliente.getNombre());
		solicitud.setString(3, cliente.getApellidoPaterno());
		solicitud.execute();
		solicitud.close();
		conexion.close();
	}

	//FACTURAS
	
	public ObservableList<Facturas> buscarFacturas() throws SQLException {
		ObservableList<Facturas> lista = FXCollections.observableArrayList();
		String query = "{call buscarFacturas()}";
		CallableStatement solicitud = conexion.prepareCall(query);
		ResultSet datos = solicitud.executeQuery(query);
		if (!datos.next()) {
			System.out.println("no hay datos");
		} else {
			do {
				Facturas fac = new Facturas();
				fac.setIdF(datos.getInt(1));
				fac.setReferencia(datos.getString(2));
				fac.setFecha(datos.getDate(3));
				Clientes cl = new Clientes();
				cl.setId(datos.getInt(4));
				cl.setNombre(datos.getString(5));
				cl.setApellidoPaterno(datos.getString(6));
				fac.setCliente(cl);
				lista.add(fac);
			} while (datos.next());
		}
		datos.close();
		solicitud.close();
		conexion.close();
		return lista;
	}
	
	public ObservableList<Facturas> buscarFact(String patron) throws SQLException {
		ObservableList<Facturas> lista = FXCollections.observableArrayList();
		String queryBP = "{call buscarFact(?)}";
		CallableStatement solicitud = conexion.prepareCall(queryBP);
		solicitud.setString(1, patron);
		ResultSet datos = solicitud.executeQuery();
		if (!datos.next()) {
			System.out.println("no hay datos");
		} else {
			do {
				Facturas fac = new Facturas();
				fac.setIdF(datos.getInt(1));
				fac.setReferencia(datos.getString(2));
				fac.setFecha(datos.getDate(3));
				Clientes cl = new Clientes();
				cl.setId(datos.getInt(4));
				cl.setNombre(datos.getString(5));
				cl.setApellidoPaterno(datos.getString(6));
				fac.setCliente(cl);
				lista.add(fac);
			} while (datos.next());
		}
		datos.close();
		solicitud.close();
		conexion.close();
		return lista;
	}
	
	public void almacenarFactura(Facturas fac) throws SQLException {
		String query = "{call insertarFacturas(?,?,?)}";
		CallableStatement solicitud = conexion.prepareCall(query);
		solicitud.setInt(1, fac.getIdCl());
		solicitud.setString(2, fac.getReferencia());
		solicitud.setDate(3, fac.getFecha());
		solicitud.execute();
		solicitud.close();
		conexion.close();
	}

	public void eliminarFactura(Integer id) throws SQLException {
		String query = "{call eliminarFactura(?)}";
		CallableStatement solicitud = conexion.prepareCall(query);
		solicitud.setInt(1, id);
		solicitud.execute();
		solicitud.close();
		conexion.close();

	}

	public void modificarFactura(Facturas fac) throws SQLException {
		String query = "{call modificarFacturas(?,?,?,?)}";
		CallableStatement solicitud = conexion.prepareCall(query);
		solicitud.setInt(1, fac.getIdF());
		System.out.println("id cliente: "+fac.getIdCl());
		solicitud.setInt(2, fac.getIdCl());
		solicitud.setString(3, fac.getReferencia());
		solicitud.setDate(4, fac.getFecha());
		solicitud.execute();
		solicitud.close();
		conexion.close();
	}
}
