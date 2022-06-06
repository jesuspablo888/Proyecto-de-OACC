package fes.aragon.modelo;

import java.sql.Date;

public class FactProd {
	private Double cantidad;
	private Facturas factura ;
	private Productos producto;	
	public FactProd() {
		// TODO Auto-generated constructor stub
		 //factura = new Facturas();
		 // producto = new Productos();	
	}
	
	public FactProd(Double cantidad, Facturas factura, Productos producto) {
		super();
		this.cantidad = cantidad;
		this.factura = factura;
		this.producto = producto;
	}

	// PRODUCTO
	public String getNombreP() {
		return producto.getNombreP();
	}
	
	public Double getPreciop() {
		return producto.getPreciop();
	}
	public Integer getIdProducto() {
		return producto.getId();
	}

	
	// FACTURA
	public String getReferencia() {
		return factura.getReferencia();
	}
	public Date getFecha() {
		return factura.getFecha();
	}
	public Integer getIdFactura() {
		return factura.getIdF();
	}
	//CLASE
	public Double getCantidad() {
		return cantidad;
	}
	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}
	public Facturas getFactura() {
		return factura;
	}
	public void setFactura(Facturas factura) {
		this.factura = factura;
	}
	public Productos getProducto() {
		return producto;
	}
	public void setProducto(Productos producto) {
		this.producto = producto;
	}
	

}
