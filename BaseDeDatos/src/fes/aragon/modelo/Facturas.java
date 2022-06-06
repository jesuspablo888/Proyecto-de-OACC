package fes.aragon.modelo;

import java.sql.Date;

public class Facturas {
	private Integer idF;
	private String referencia;
	private Date fecha;
	private Clientes cliente;
	public Facturas() {
		// TODO Auto-generated constructor stub
		//cliente=new Clientes();
		
	}
	public Integer getIdF() {
		return idF;
	}
	public void setIdF(Integer id) {
		this.idF = id;
	}
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Clientes getCliente() {
		return cliente;
	}
	public void setCliente(Clientes cliente) {
		this.cliente = cliente;
	}
	public String getNombre() {
		return cliente.getNombre();
	}
	public String getApellido() {
		return cliente.getApellidoPaterno();
	}
	public Integer getIdCl() {
		return cliente.getId();
	}
}