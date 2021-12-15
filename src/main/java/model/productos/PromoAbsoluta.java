package model.productos;

import java.util.List;

public class PromoAbsoluta extends Promo {

	public PromoAbsoluta(TipoAtraccion tipoPack, String nombrePromo, String descripcion, List<Integer> id_atracciones,
			Integer valorAbs) {
		super(tipoPack, nombrePromo, descripcion, id_atracciones, valorAbs);
	}

	public PromoAbsoluta(Integer id, TipoAtraccion tipoPack, String nombrePromo, String descripcion,
			List<Integer> id_atracciones, Integer valorAbs) {
		super(id, nombrePromo, descripcion, id_atracciones, valorAbs, tipoPack);
	}

	@Override
	public String getTipoPromo() {
		return "ABSOLUTA";
	}

	@Override
	public Integer precio(List<Atraccion> atracciones) {
		return getValor();
	}
}
