package model.productos;

import java.util.List;

public class PromoPorcentual extends Promo {

	public PromoPorcentual(TipoAtraccion tipoPack, String nombrePromo, String descripcion, List<Integer> id_atracciones,
			Integer valorDescuento) {
		super(tipoPack, nombrePromo, descripcion, id_atracciones, valorDescuento);
	}

	public PromoPorcentual(Integer id, TipoAtraccion tipoPack, String nombrePromo, String descripcion,
			List<Integer> id_atracciones, Integer valorDescuento) {
		super(id, nombrePromo, descripcion, id_atracciones, valorDescuento, tipoPack);
	}

	// comprando 2 o mas sacar un porcentaje
	@Override
	public Integer precio(List<Atraccion> atracciones) {
		Integer resultado = 0;
		for (Integer id_atraccion : this.getId_atracciones()) {
			// buscar atraccion en atracciones, si esta lo sumo
			for (Atraccion atraccion : atracciones) {
				if (atraccion.getId().equals(id_atraccion)) {
					resultado += atraccion.getValor();
				}
			}
		}
		return resultado - ((resultado * valor) / 100);
	}

	@Override
	public String getTipoPromo() {
		return "PORCENTUAL";
	}
}
