package model.productos;

import java.util.List;

public class PromoPorcentual extends Promo{

	public PromoPorcentual(TipoAtraccion tipoPack, String nombrePromo, String nombres_atracciones, Integer valorDescuento) {
		super(tipoPack, nombrePromo, nombres_atracciones, valorDescuento);
	}

	//comprando 2 o mas sacar un porcentaje
	@Override
	public Integer precio(List<Atraccion> atracciones) {
		Integer resultado = 0;
		for(String nombre_atraccion : this.getNombres_atracciones()) {
			 //buscar atraccion en atracciones, si esta lo sumo
			for (Atraccion atraccion : atracciones) {
				if (atraccion.getNombre().equals(nombre_atraccion)) {
					 resultado += atraccion.getValor();
				}
			}
		}
		return resultado - ((resultado * valor) / 100);
	}

	@Override
	public String getTipoPromo() {
		return "Porcentual";
	}
}
