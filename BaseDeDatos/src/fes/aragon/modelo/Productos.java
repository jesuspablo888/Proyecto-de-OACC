package fes.aragon.modelo;

public class Productos {
	private Integer id;
	private String nombreP;
	private Double preciop;
	
	public Productos() {
	}

	public Productos(Integer id, String nombreP, Double preciop) {
		super();
		this.id = id;
		this.nombreP = nombreP;
		this.preciop = preciop;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombreP() {
		return nombreP;
	}

	public void setNombreP(String nombreP) {
		this.nombreP = nombreP;
	}

	public Double getPreciop() {
		return preciop;
	}

	public void setPreciop(Double preciop) {
		this.preciop = preciop;
	}
	
	

}
