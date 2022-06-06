package fes.aragon.modelo;

public class Clientes {

	private Integer id;
	private String nombre;
	private String apellidoPaterno;
	
	public Clientes() {
		// TODO Auto-generated constructor stub
	}

	public Clientes(Integer id, String nombre, String apellidoPaterno) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellidoPaterno = apellidoPaterno;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	
}
