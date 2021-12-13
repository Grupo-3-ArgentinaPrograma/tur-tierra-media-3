package model.productos;

import java.util.List;

public abstract class Promo extends Producto {
	protected String nombres_atracciones;

	// valor : si es absoluta sera el valor final de la promo, si es porcentual sera
	// el valor del descuento, si es AxB no se usara
	public Promo(TipoAtraccion nombrePack, String nombrePromo, String nombres_atracciones, Integer valor) {
		super(nombrePack, nombrePromo, valor, 0d);
		this.nombres_atracciones = nombres_atracciones;
	}

	public abstract Integer precio(List<Atraccion> atracciones);

	public abstract String getTipoPromo();

	public void setCupos(List<Atraccion> atracciones, String[] elementos) {
		for (Atraccion atraccion : atracciones) {
			for (String elemento : elementos) {
				if (elemento.equals(atraccion.getNombre())) {
					atraccion.setCupo(atraccion.getCupo() - 1);
					break;
				}
			}
		}
	}

	public Boolean hayCupo(List<Atraccion> atracciones, String[] elementos) {
		for (Atraccion atraccion : atracciones) {
			for (String elemento : elementos) {
				if (elemento.equals(atraccion.getNombre())) {
					if (!atraccion.hayCupo()) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public void establecerHsPromo(Promo promo, List<Atraccion> atracciones) {
		Double sumaTiempos = 0d;
		for (String nombre_atraccion : promo.getNombres_atracciones()) {
			// filtro
			for (Atraccion atraccion : atracciones) {
				if (atraccion.getNombre().equals(nombre_atraccion)) {
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

	public String[] getNombres_atracciones() {
		return nombres_atracciones.split("-");
	}

	@Override
	public String toString() {
		return "Promo ( " + nombre + ", Tipo de Promo : " + tipo + ", Atracciones incluidas : " + nombres_atracciones
				+ ", Precio : " + valor + ", Tiempo total : " + tiempo + ")";
	}

	public String nombresAtracciones() {
		return nombres_atracciones;
	}
}
