package model.productos;

import java.util.List;

public class PromoAbsoluta extends Promo{
	
	public PromoAbsoluta(TipoAtraccion tipoPack, String nombrePromo, String nombres_atracciones, Integer valorAbs) {
		super(tipoPack, nombrePromo, nombres_atracciones, valorAbs);
	}
	
	@Override
	public String getTipoPromo() {
		return "Absoluta";
	}

	@Override
	public Integer precio(List<Atraccion> atracciones) {
		return getValor();
	}
}
