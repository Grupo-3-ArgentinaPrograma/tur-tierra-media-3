package model.productos;

import java.util.HashMap;
import java.util.Map;

public class Atraccion extends Producto {
	private Integer cupo;
	private Integer id;
	private Map<String, String> errors;

	public Atraccion(Integer id, String nombre,  String description, Integer costo, Double duracion, Integer cupo, TipoAtraccion tipo) {
		this(nombre, description, costo, duracion, cupo, tipo);
		this.setId(id);
	}

	public Atraccion(String name, String description, Integer cost, Double duration, Integer capacity,
			TipoAtraccion type) {
		super(type, name, description, cost, duration);
		this.setCupo(capacity);
	}

	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		if (id > 0) {
			this.id = id;
		}
	}
	
	public Integer getCupo() {
		return cupo;
	}

	public void setCupo(Integer cupo) {
		if (cupo >= 0) {
			this.cupo = cupo;
		}
	}

	public Boolean hayCupoPara(Integer i) {
		return this.getCupo() >= i;
	}

	public void reducirCupo(Integer i) {
		setCupo(getCupo() - i);
	}

	public boolean isValid() {
		validate();
		return errors.isEmpty();
	}

	public void validate() {
		errors = new HashMap<String, String>();

		if (valor <= 0) {
			errors.put("cost", "Debe ser positivo");
		}
		if (tiempo <= 0) {
			errors.put("duration", "Debe ser positivo");
		}
		if (tiempo >= 24) {
			errors.put("duration", "Excede el tiempo máximo");
		}
		if (cupo < 0) {
			errors.put("capacity", "Debe ser positivo");
		}
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	@Override
	public String toString() {
		return "Atraccion (ID: " + getId() + ", " + super.getNombre() + ", Precio: " + super.getValor()
				+ " monedas, Duracion: " + super.getTiempo() + " hs, Cupos Restantes: " + getCupo() + ", Tipo: "
				+ super.getTipo() + ") ";
	}
}
