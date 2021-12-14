package model;

import java.util.*;

import model.productos.*;
import utils.Crypt;

public class User {
	private Integer id;
	private String nombre;
	private String password;
	private Boolean admin;
	private TipoAtraccion atraccionPreferida;// preferenci@Override
	private Integer monedas;// presupuesto
	private Double tiempo;
	private List<Producto> compras;
	private Integer gasto;
	private Double hsAConsumir;
	private HashMap<String, String> errors;

	public User(Integer id, String nombre, String password, TipoAtraccion atraccionPreferida, Integer monedas,
			Double tiempoDisponible, Boolean admin) {
		this.id = id;
		this.nombre = nombre;
		this.password = password;
		this.monedas = monedas;
		this.tiempo = tiempoDisponible;
		this.atraccionPreferida = atraccionPreferida;
		this.compras = new LinkedList<Producto>();
		this.gasto = 0;
		this.hsAConsumir = 0d;
		this.admin = admin;
	}

	public String getNombre() {
		return nombre;
	}

	public TipoAtraccion getAtraccionPreferida() {
		return atraccionPreferida;
	}

	public Integer getMonedas() {
		return monedas;
	}

	public Double getTiempoDisponible() {
		return tiempo;
	}

	public Double getHsAConsumir() {
		return hsAConsumir;
	}

	public Integer getGasto() {
		return gasto;
	}
	
	public Integer getId() {
		return id;
	}

	public List<Producto> getCompras() {
		return compras;
	}
	
	public String getPassword() {
		return password;
	}

	public boolean checkPassword(String password) {
		// this.password en realidad es el hash del password
		return Crypt.match(password, this.password);
	}
	
	public void setComprasRealizadas(String comprasStr, List<Atraccion> atracciones, List<Promo> promociones) {
		for (String compra : comprasStr.split("/")) {
			for (Atraccion atraccion : atracciones) {
				if (compra.equals(atraccion.getNombre())) {
					this.comprar(atraccion);
				} else {
					for (String elemento : compra.split(":")) {
						for (Promo promo : promociones) {
							if (elemento.equals(promo.getNombre())) {
								this.comprar(atracciones, promo);
							}
						}
					}
				}
			}
		}
	}

	public String getStringCompras() {
		String comprasStr = new String();
		for (Producto compra : compras) {
			if (compra instanceof Promo) {
				comprasStr += (compra.getNombre() + ":" + ((Promo) compra).nombresAtracciones() + "/");
			}
			if (compra instanceof Atraccion) {
				comprasStr += (compra.getNombre() + "/");
			}
		}
		return comprasStr;
	}

	public Boolean puedeComprar(Producto producto) {
		return ((this.getMonedas() >= producto.getValor()) && (this.getTiempoDisponible() >= producto.getTiempo()))
				&& (!producto.contieneAtraccion(compras));
	}

	public Boolean tieneTiempo() {
		return tiempo > 0;
	}

	public Boolean tieneMonedas() {
		return monedas > 0;
	}

	// compra las atracciones de una promo
	public void comprar(List<Atraccion> atracciones, Promo promo) {
		this.monedas -= promo.precio(atracciones);
		this.tiempo -= promo.getTiempo();
		this.gasto += promo.precio(atracciones);
		this.hsAConsumir += promo.getTiempo();
		promo.setCupos(atracciones, promo.getNombres_atracciones());
		compras.add(promo);
	}

	// compra una sola atraccion
	public void comprar(Atraccion atraccion) {
		this.monedas -= atraccion.getValor();
		this.tiempo -= atraccion.getTiempo();
		this.gasto += atraccion.getValor();
		this.hsAConsumir += atraccion.getTiempo();
		atraccion.setCupo(atraccion.getCupo() - 1);
		compras.add(atraccion);
	}

	public void sugerencia(List<Producto> productos) {
		System.out.println("------------------------------ LISTA SUGERIDA PARA " + nombre
				+ "---------------------------------------------");
		for (Producto producto : productos) {
			System.out.println(producto);
		}
		System.out.println(
				"-----------------------------------------------------------------------------------------------------");
	}

	public void datosUsuario() {
		System.out.println("----------------------------------------------------------------------------");
		System.out.println("Nombre : " + nombre + ", Atraccion Preferida : " + atraccionPreferida + ", Monedas : "
				+ monedas + ", Tiempo : " + tiempo);
		System.out.println("----------------------------------------------------------------------------");
	}

	@Override
	public String toString() {
		return "Nombre : " + nombre + ", Atraccion Preferida : " + atraccionPreferida + ", Monedas Disponibles : "
				+ monedas + ", Tiempo Disponible : " + tiempo + "\nCompras Realizadas : " + compras.toString()
				+ "\nGasto Total : " + gasto + ", Horas a Consumir : " + hsAConsumir;
	}
	
	public void setPassword(String password) {
		this.password = Crypt.hash(password);
	}
	
	public boolean isValid() {
		validate();
		return errors.isEmpty();
	}
	
	public void validate() {
		errors = new HashMap<String, String>();

		if (monedas < 0) {
			errors.put("coins", "No debe ser negativo");
		}
		if (tiempo < 0) {
			errors.put("time", "No debe ser negativo");
		}
	}
	
	public Map<String, String> getErrors() {
		return errors;
	}
	
	public boolean isNull() {
		// TODO Auto-generated method stub
		return false;
	}
}
