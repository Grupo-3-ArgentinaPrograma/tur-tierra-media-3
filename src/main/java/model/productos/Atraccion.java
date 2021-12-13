package model.productos;

public class Atraccion extends Producto{
	private Integer cupo;
	
	public Atraccion(String nombre, Integer costo, Double duracion, Integer cupo, TipoAtraccion tipo) {
		super(tipo,nombre,costo,duracion);
		this.setCupo(cupo);
	}

	public Integer getCupo() {
		return cupo;
	}

	public void setCupo(Integer cupo) {
		if(cupo>=0) {
			this.cupo = cupo;
		}
	}
	
	public Boolean hayCupo() {
		return cupo > 0;
	}

	@Override
	public String toString() {
		return "Atraccion ( " + super.getNombre() + ", Precio : " + super.getPrecio() + ", Duracion : " + super.getTiempo() + ", Cupos Restantes: " + cupo
				+ ", Tipo : " + super.getTipo() + ") ";
	}	
}
