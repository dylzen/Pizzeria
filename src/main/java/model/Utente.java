package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the utente database table.
 * 
 */
@Entity
@NamedQuery(name="Utente.findAll", query="SELECT u FROM Utente u")
public class Utente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_utente")
	private int idUtente;

	@Column(name="nome_utente")
	private String nomeUtente;

	@Column(name="password_utente")
	private String passwordUtente;

	//bi-directional many-to-one association to Pizza
	@OneToMany(mappedBy="utente")
	private List<Pizza> pizzas;

	public Utente() {
	}

	public int getIdUtente() {
		return this.idUtente;
	}

	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}

	public String getNomeUtente() {
		return this.nomeUtente;
	}

	public void setNomeUtente(String nomeUtente) {
		this.nomeUtente = nomeUtente;
	}

	public String getPasswordUtente() {
		return this.passwordUtente;
	}

	public void setPasswordUtente(String passwordUtente) {
		this.passwordUtente = passwordUtente;
	}

	public List<Pizza> getPizzas() {
		return this.pizzas;
	}

	public void setPizzas(List<Pizza> pizzas) {
		this.pizzas = pizzas;
	}

	public Pizza addPizza(Pizza pizza) {
		getPizzas().add(pizza);
		pizza.setUtente(this);

		return pizza;
	}

	public Pizza removePizza(Pizza pizza) {
		getPizzas().remove(pizza);
		pizza.setUtente(null);

		return pizza;
	}

}