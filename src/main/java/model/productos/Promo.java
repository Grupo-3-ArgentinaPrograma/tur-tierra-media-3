package model.productos;

import java.util.List;

public abstract class Promo extends Producto {
	protected List<Integer> id_atracciones;
	protected Integer id;

	// valor : si es absoluta sera el valor final de la promo, si es porcentual sera
	// el valor del descuento, si es AxB no se usara
	public Promo(TipoAtraccion tipoAtraccionPack, String nombrePromo, String descripcion, List<Integer> id_atracciones, Integer valor) {
		super(tipoAtraccionPack, nombrePromo, descripcion, valor, 0d);
		this.id_atracciones = id_atracciones;
	}
	
	public Promo(Integer idPromo, String nombrePromo, String descripcion, List<Integer> id_atracciones, Integer valor, TipoAtraccion tipo) {
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
					atraccion.setCupo(atraccion.getCupo() - 1);
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

	public void establecerHsPromo(Promo promo, List<Atraccion> atracciones) {
		Double sumaTiempos = 0d;
		for (Integer id_atraccion : promo.id_atracciones) {
			// filtro
			for (Atraccion atraccion : atracciones) {
				if (atraccion.getId().equals(id_atraccion)) {
					sumaTiempos += atraccion.getTiempo();
					break;
				}
			}
		}
		promo.setTiempo(sumaTiempos);
	}

	public void establecerPrecioPromo(Promo promo, List<Atraccion> atracciones) {
		promo.setPrecio(promo.precio(atracciones));
	}

	public List<Integer> getId_atracciones() {
		return id_atracciones;
	}

	@Override
	public String toString() {
		return "Promo ( " + nombre + ", Tipo de Promo : " + tipo
				+ ", Precio : " + valor + ", Tiempo total : " + tiempo + ")";
	}

}
