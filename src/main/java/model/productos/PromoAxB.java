package model.productos;

import java.util.List;

public class PromoAxB extends Promo{
	
	private String atraccionGratuita;
	
	public String getAtraccionGratuita() {
		return atraccionGratuita;
	}
	public void setAtraccionGratuita(String atraccionGratuita) {
		this.atraccionGratuita = atraccionGratuita;
	}
	public PromoAxB(TipoAtraccion tipoPack, String nombrePromo, String nombres_atracciones, String atraccionGratis) {
		super(tipoPack, nombrePromo, nombres_atracciones, 0);
		setAtraccionGratuita(atraccionGratis);
	}
	
	//devuelve la suma de precios de las atracciones compradas, menos la que es gratis
	@Override
	public Integer precio(List<Atraccion> atracciones) {
		Integer resultado = 0;
		for (String nombreAtraccion:this.getNombres_atracciones()) {
			for (Atraccion atraccion : atracciones) {
				if (nombreAtraccion.equals(atraccion.getNombre()) && !nombreAtraccion.equals(atraccionGratuita)) {
					 resultado += atraccion.getValor();
				}
			}
		}
		return resultado;
	}
	@Override
	public String getTipoPromo() {
		return "AxB";
	}
}
