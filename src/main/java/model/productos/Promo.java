package model.productos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Promo extends Producto {
	protected List<Integer> id_atracciones;
	protected Integer id;
	private Map<String, String> errors;

	// valor : si es absoluta sera el valor final de la promo, si es porcentual sera
	// el valor del descuento, si es AxB no se usara
	public Promo(TipoAtraccion tipoAtraccionPack, String nombrePromo, String descripcion, List<Integer> id_atracciones,
			Integer valor) {
		super(tipoAtraccionPack, nombrePromo, descripcion, valor, 0d);
		this.id_atracciones = id_atracciones;
	}

	public Promo(Integer idPromo, String nombrePromo, String descripcion, List<Integer> id_atracciones, Integer valor,
			TipoAtraccion tipo) {
		this(tipo, nombrePromo, descripcion, id_atracciones, valor);
		this.setId(idPromo);
	}

	public abstract Integer precio(List<Atraccion> atracciones);

	public abstract String getTipoPromo();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		if (id > 0) {
			this.id = id;
		}
	}

	public void setCupos(List<Atraccion> atracciones) {
		for (Atraccion atraccion : atracciones) {
			for (Integer id_element : id_atracciones) {
				if (id_element.equals(atraccion.getId())) {
					atraccion.reducirCupo(1);
					;
					break;
				}
			}
		}
	}

	public Boolean hayCupo(List<Atraccion> atracciones) {
		for (Atraccion atraccion : atracciones) {
			for (Integer id_element : id_atracciones) {
				if (id_element.equals(atraccion.getId())) {
					if (!atraccion.hayCupoPara(1)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public void establecerHsPromo(List<Atraccion> atracciones) {
		Double sumaTiempos = 0d;
		for (Integer id_atraccion : this.id_atracciones) {
			// filtro
			for (Atraccion atraccion : atracciones) {
				if (atraccion.getId().equals(id_atraccion)) {
					sumaTiempos += atraccion.getTiempo();
					break;
				}
			}
		}
		this.setTiempo(sumaTiempos);
	}

	public boolean isValid(List<Atraccion> atracciones) {
		validate(atracciones);
		return errors.isEmpty();
	}

	public void validate(List<Atraccion> atracciones) {
		errors = new HashMap<String, String>();

		if (valor <= 0) {
			errors.put("cost", "Debe ser un valor positivo");
		}
		if (tiempo <= 0) {
			errors.put("duration", "Debe ser positivo");
		}
		if (tiempo >= 60) {
			errors.put("duration", "Excede el tiempo máximo");
		}
		if (this.hayCupo(atracciones)) {
			errors.put("capacity", "No hay cupo");
		}
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	public void establecerPrecioPromo(List<Atraccion> atracciones) {
		this.setPrecio(this.precio(atracciones));
	}

	public Integer getCupo(List<Atraccion> atracciones) {
		Integer cupo=0;
		Boolean flagFirst=true;
		for (Integer id_atraccion : this.id_atracciones) {
			for (Atraccion atraccion : atracciones) {
				if (atraccion.getId().equals(id_atraccion)) {
					if(flagFirst) {
						cupo=atraccion.getCupo();
						flagFirst=false;
					}else {
						if(cupo>atraccion.getCupo()) {
							cupo=atraccion.getCupo();
						}
					}
				}
			}
		}
		return cupo;
	}

	public List<Integer> getId_atracciones() {
		return id_atracciones;
	}
	public String getId_atraccionesStr() {
		StringBuilder str = new StringBuilder();
		for(Integer idAtr : id_atracciones) {
			str.append(idAtr+"-");
		}
		return str.toString();
	}

	@Override
	public String toString() {
		return "Promo ( " + nombre + ", Tipo de Promo : " + tipo + ", Precio : " + valor + ", Tiempo total : " + tiempo
				+ ")";
	}

}
