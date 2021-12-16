package model;

import java.util.*;
import model.productos.*;
import utils.Crypt;

public class User {
	private Integer id;
	private String nombre;
	private String password;
	private Boolean admin;
	private TipoAtraccion atraccionPreferida;// preferencia
	private Integer monedas;// presupuesto
	private Double tiempo;
	private Integer gasto;
	private Double hsAConsumir;
	private HashMap<String, String> errors;
	private List<Integer> id_atraccionesCompradas;
	private List<Integer> id_promosCompradas;
	private List<Producto> compras;

	public User(Integer id, String nombre, String password, TipoAtraccion atraccionPreferida, Integer monedas,
			Double tiempoDisponible, Boolean admin) {
		this(nombre,password,atraccionPreferida,monedas,tiempoDisponible, admin);
		this.id = id;
	}
	
	public User(String nombre, String password, TipoAtraccion atraccionPreferida, Integer monedas,
			Double tiempoDisponible, Boolean admin) {
		this.nombre = nombre;
		this.password = password;
		this.monedas = monedas;
		this.tiempo = tiempoDisponible;
		this.atraccionPreferida = atraccionPreferida;
		this.compras = new LinkedList<Producto>();
		this.gasto = 0;
		this.hsAConsumir = 0d;
		this.setAdmin(admin);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public TipoAtraccion getAtraccionPreferida() {
		return atraccionPreferida;
	}
	
	public String getAtraccionPreferidaStr() {
		return atraccionPreferida.name().toLowerCase();
	}
	
	public void setAtraccionPreferida(TipoAtraccion tipoAtraccion) {
		this.atraccionPreferida=tipoAtraccion;
	}

	public Integer getMonedas() {
		return monedas;
	}

	public void setMonedas(Integer monedas) {
		this.monedas = monedas;
	}

	public Double getTiempoDisponible() {
		return tiempo;
	}

	public void setTiempoDisponible(Double tiempo) {
		this.tiempo = tiempo;
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

	public List<Integer> getId_atraccionesCompradas() {
		return id_atraccionesCompradas;
	}

	public void setId_atraccionesCompradas(List<Integer> id_atraccionesCompradas) {
		this.id_atraccionesCompradas = id_atraccionesCompradas;
	}

	public List<Integer> getId_promosCompradas() {
		return id_promosCompradas;
	}

	public void setId_promosCompradas(List<Integer> id_promosCompradas) {
		this.id_promosCompradas = id_promosCompradas;
	}

	public String getPassword() {
		return password;
	}

	public boolean checkPassword(String password) {
		// this.password en realidad es el hash del password
		return Crypt.match(password, this.password);
	}

	public void setComprasRealizadas(List<Promo> lista_promos, List<Atraccion> lista_atracciones) {
		for (Promo P : lista_promos) {
			for (Integer idPromo : getId_promosCompradas()) {
				if (idPromo.equals(P.getId())) {
					compras.add(P);
				}
			}
		}
		for (Atraccion A : lista_atracciones) {
			for (Integer idAtraccion : getId_atraccionesCompradas()) {
				if (idAtraccion.equals(A.getId())) {
					compras.add(A);
				}
			}
		}
	}

	public List<Producto> getCompras() {
		return this.compras;
	}
	
	public Boolean comprasIsEmpty() {
		return this.compras.isEmpty();
	}

	public Boolean puedeComprar(Producto producto) {
		return (this.tieneTiempoPara(producto) && this.tieneMonedasPara(producto))
				&& (!producto.contieneAtraccion(compras));
	}

	public Boolean tieneTiempoPara(Producto p) {
		return getTiempoDisponible() >= p.getTiempo();
	}

	public Boolean tieneMonedasPara(Producto p) {
		return getMonedas() >= p.getValor();
	}

	// compra las atracciones de una promo
	public void comprar(List<Atraccion> atracciones, Promo promo) {
		this.monedas -= promo.precio(atracciones);
		this.tiempo -= promo.getTiempo();
		this.gasto += promo.precio(atracciones);
		this.hsAConsumir += promo.getTiempo();
		promo.setCupos(atracciones);
		compras.add(promo);
	}

	// compra una sola atraccion
	public void comprar(Atraccion atraccion) {
		this.monedas -= atraccion.getValor();
		this.tiempo -= atraccion.getTiempo();
		this.gasto += atraccion.getValor();
		this.hsAConsumir += atraccion.getTiempo();
		atraccion.reducirCupo(1);
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

	/*
	 * hashea la password
	 */
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
		return false;
	}

	public Boolean isAdmin() {
		return admin;
	}
	
	public Integer getAdmin() {
		return isAdmin()?1:0;
	}
	public String getAdminStr() {
		return isAdmin()?"true":"false";
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}
}
