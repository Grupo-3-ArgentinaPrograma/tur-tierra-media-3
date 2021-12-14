package model.productos;

import java.util.HashMap;
import java.util.Map;

public class Atraccion extends Producto{
	private Integer cupo;
	private Map<String, String> errors;
	
	public Atraccion(Integer id,String nombre, Integer costo, Double duracion, Integer cupo, TipoAtraccion tipo) {
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
	
	public boolean isValid() {
		validate();
		return errors.isEmpty();
	}
	
	public void validate() {
		errors = new HashMap<String, String>();

		if (valor <= 0) {
			errors.put("costo", "Debe ser positivo");
		}
		if (tiempo <= 0) {
			errors.put("duracion", "Debe ser positivo");
		}
		if (tiempo > 60) {
			errors.put("duracion", "Excede el tiempo máximo");
		}
		if (cupo < 0) {
			errors.put("cupo", "Debe ser positivo");
		}
	}
	
	
	public Map<String, String> getErrors() {
		return errors;
	}
	
	@Override
	public String toString() {
		return "Atraccion ( " + super.getNombre() + ", Precio : " + super.getValor() + ", Duracion : " + super.getTiempo() + ", Cupos Restantes: " + cupo
				+ ", Tipo : " + super.getTipo() + ") ";
	}	
}
