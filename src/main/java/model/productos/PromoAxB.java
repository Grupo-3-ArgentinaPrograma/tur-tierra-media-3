package model.productos;

import java.util.List;

public class PromoAxB extends Promo {

	private Integer idAtraccionGratuita;

	public Integer getAtraccionGratuita() {
		return idAtraccionGratuita;
	}

	public void setAtraccionGratuita(Integer atraccionGratuita) {
		this.idAtraccionGratuita = atraccionGratuita;
	}

	public PromoAxB(TipoAtraccion tipoPack, String nombrePromo, String descripcion, List<Integer> id_atracciones,
			Integer idAtraccionGratis) {
		super(tipoPack, nombrePromo, descripcion, id_atracciones, 0);
		this.setAtraccionGratuita(idAtraccionGratis);
	}

	public PromoAxB(Integer id, TipoAtraccion tipoPack, String nombrePromo, String descripcion,
			List<Integer> id_atracciones, Integer idAtraccionGratis) {
		super(id, nombrePromo, descripcion, id_atracciones, 0, tipoPack);
		this.setAtraccionGratuita(idAtraccionGratis);
	}

	// devuelve la suma de precios de las atracciones compradas, menos la que es
	// gratis
	@Override
	public Integer precio(List<Atraccion> atracciones) {
		Integer resultado = 0;
		for (Integer idAtraccion : this.getId_atracciones()) {
			for (Atraccion atraccion : atracciones) {
				if (idAtraccion.equals(atraccion.getId()) && !idAtraccion.equals(idAtraccionGratuita)) {
					resultado += atraccion.getValor();
				}
			}
		}
		return resultado;
	}

	@Override
	public String getTipoPromo() {
		return "AXB";
	}
}
