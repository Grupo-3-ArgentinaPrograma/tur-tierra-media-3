package model.productos;

import java.util.List;

public abstract class Producto{
	protected TipoAtraccion tipo;
	protected String nombre;
	protected Integer valor;
	protected Double tiempo;
	
	public Producto(TipoAtraccion tipo, String nombre, Integer valor, Double tiempo) {
		this.tipo = tipo;
		this.setTiempo(tiempo);;
		this.setPrecio(valor);
		this.setNombre(nombre);
	}
	
	public TipoAtraccion getTipo() {
		return tipo;
	}

	public String getNombre() {
		return this.nombre;
	}
	
	public void setNombre(String nombre) {
		if(nombre !=null && nombre != "") {
			this.nombre = nombre;
		}
	}

	public Integer getPrecio() {
		return valor;
	}

	public void setPrecio(Integer precio) {
		if(precio>0) {
			this.valor = precio;	
		}
	}

	public Double getTiempo() {
		return tiempo;
	}

	public void setTiempo(Double tiempo) {
		if(tiempo>0) {
			this.tiempo = tiempo;	
		}
	}	
	
	public Boolean contieneAtraccion(List<Producto> compras) {
		if (this instanceof Promo) {
			for (Producto compra : compras) {
				if (compra instanceof Promo) { // Promo - Promo
					for (String nombreP : ((Promo) this).getNombres_atracciones()) {
						for (String nombreC : ((Promo) compra).getNombres_atracciones()) {
							if ( nombreP.equals(nombreC) ) {
								return true;	
							}
						}
						
					}					
				} else { // Promo - Atraccion
					for (String nombreP : ((Promo) this).getNombres_atracciones()) {
						if ( nombreP.equals(((Atraccion) compra).getNombre()) ) {
							return true;	
						}
					}
				}
			}
		} else {
			for (Producto compra : compras) {
				if (compra instanceof Promo) { // Atraccion - Promo
					for (String nombreC : ((Promo) compra).getNombres_atracciones()) {
						if ( nombreC.equals( ((Atraccion) this).getNombre() ) ) {
							return true;	
						}
					}					
				} else { // Atraccion - Atraccion
					if ( ((Atraccion) compra).getNombre().equals( ((Atraccion) this).getNombre() ) ) {
							return true;	
						}
					
				}
			}
		}
		return false; //no contiene la atraccion
	}
}
