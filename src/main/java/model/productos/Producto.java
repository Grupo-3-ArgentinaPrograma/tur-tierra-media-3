package model.productos;

import java.util.List;


public abstract class Producto{
	protected TipoAtraccion tipo;
	protected String nombre;
	protected Integer valor;
	protected Double tiempo;
	protected String descripcion;
	
	public Producto(TipoAtraccion tipo, String nombre,String descripcion, Integer valor, Double tiempo) {
		this.setTipo(tipo);
		this.setTiempo(tiempo);
		this.setPrecio(valor);
		this.setNombre(nombre);
		this.setDescripcion(descripcion);
	}
	
	public TipoAtraccion getTipo() {
		return tipo;
	}
	
	public String getTipoAtraccionStr() {
		return tipo.name().toLowerCase();
	}
	
	public void setTipo(TipoAtraccion tipo) {
		this.tipo=tipo;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public void setNombre(String nombre) {
		if (!nombre.isBlank()) {
			this.nombre = nombre;
		}
	}
	
	public String getDescripcion() {
		return this.descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		if (!descripcion.isBlank()) {
			this.descripcion = descripcion;
		}
	}

	public Integer getValor() {
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
					for (Integer id_P : ((Promo) this).getId_atracciones()) {
						for (Integer id_C : ((Promo) compra).getId_atracciones()) {
							if ( id_P.equals(id_C) ) {
								return true;	
							}
						}
						
					}					
				} else { // Promo - Atraccion
					for (Integer id_P : ((Promo) this).getId_atracciones()) {
						if ( id_P.equals(((Atraccion) compra).getId()) ) {
							return true;	
						}
					}
				}
			}
		} else {
			for (Producto compra : compras) {
				if (compra instanceof Promo) { // Atraccion - Promo
					for (Integer id_C : ((Promo) compra).getId_atracciones()) {
						if ( id_C.equals( ((Atraccion) this).getId() ) ) {
							return true;	
						}
					}					
				} else { // Atraccion - Atraccion
					if ( ((Atraccion) compra).getId().equals( ((Atraccion) this).getId() ) ) {
							return true;	
						}
					
				}
			}
		}
		return false; //no contiene la atraccion
	}
}
