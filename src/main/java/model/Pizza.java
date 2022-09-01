package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the pizza database table.
 * 
 */
@Entity
@NamedQuery(name="Pizza.findAll", query="SELECT p FROM Pizza p")
public class Pizza implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_pizza")
	private int idPizza;

	@Column(name="nome_pizza")
	private String nomePizza;

	//bi-directional many-to-one association to Impasto
	@ManyToOne
	@JoinColumn(name="id_impasto")
	private Impasto impasto;

	//bi-directional many-to-many association to Ingrediente
	@ManyToMany
	@JoinTable(
		name="pizza_ingrediente"
		, joinColumns={
			@JoinColumn(name="id_pizza")
			}
		, inverseJoinColumns={
			@JoinColumn(name="id_ingrediente")
			}
		)
	private List<Ingrediente> ingredientes;

	//bi-directional many-to-one association to Utente
	@ManyToOne
	@JoinColumn(name="id_utente")
	private Utente utente;

	public Pizza() {
	}

	public int getIdPizza() {
		return this.idPizza;
	}

	public void setIdPizza(int idPizza) {
		this.idPizza = idPizza;
	}

	public String getNomePizza() {
		return this.nomePizza;
	}

	public void setNomePizza(String nomePizza) {
		this.nomePizza = nomePizza;
	}

	public Impasto getImpasto() {
		return this.impasto;
	}

	public void setImpasto(Impasto impasto) {
		this.impasto = impasto;
	}

	public List<Ingrediente> getIngredientes() {
		return this.ingredientes;
	}

	public void setIngredientes(List<Ingrediente> ingredientes) {
		this.ingredientes = ingredientes;
	}

	public Utente getUtente() {
		return this.utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

}