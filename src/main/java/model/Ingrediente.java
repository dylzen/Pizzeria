package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ingrediente database table.
 * 
 */
@Entity
@NamedQuery(name="Ingrediente.findAll", query="SELECT i FROM Ingrediente i")
public class Ingrediente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_ingrediente")
	private int idIngrediente;

	@Column(name="nome_ingrediente")
	private String nomeIngrediente;

	//bi-directional many-to-many association to Pizza
	@ManyToMany(mappedBy="ingredientes")
	private List<Pizza> pizzas;

	public Ingrediente() {
	}

	public int getIdIngrediente() {
		return this.idIngrediente;
	}

	public void setIdIngrediente(int idIngrediente) {
		this.idIngrediente = idIngrediente;
	}

	public String getNomeIngrediente() {
		return this.nomeIngrediente;
	}

	public void setNomeIngrediente(String nomeIngrediente) {
		this.nomeIngrediente = nomeIngrediente;
	}

	public List<Pizza> getPizzas() {
		return this.pizzas;
	}

	public void setPizzas(List<Pizza> pizzas) {
		this.pizzas = pizzas;
	}

}