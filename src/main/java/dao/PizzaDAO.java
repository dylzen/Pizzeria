package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.Impasto;
import model.Ingrediente;
import model.Pizza;
import model.Utente;

public class PizzaDAO {
	
	EntityManagerFactory emf = null;
	EntityManager entityManager = null;
	
	private void gestisciEntityManager() {
		if (emf == null || entityManager == null) {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("Pizzeria");
			entityManager = emf.createEntityManager();
		}
	}

	public void createPizzaRecord(Pizza pizza) {
		gestisciEntityManager();
		entityManager.getTransaction().begin();

		Pizza newPizza = new Pizza();

		newPizza.setNomePizza(pizza.getNomePizza()); // deve venire da text field
		newPizza.setImpasto(pizza.getImpasto()); // deve venire da radio button
		newPizza.setIngredientes(pizza.getIngredientes()); // venire da checkbox
		newPizza.setUtente(pizza.getUtente());

		entityManager.persist(newPizza);

		entityManager.getTransaction().commit();
	}

	public List<Pizza> findAllPizza() {
		gestisciEntityManager();
		entityManager.getTransaction().begin();
		
		return entityManager.createQuery("from Pizza", Pizza.class).getResultList();
	}
	
	public Pizza findPizzaByIdPizza(int idPizza) {
		gestisciEntityManager();
		entityManager.getTransaction().begin();
		
		Pizza retrievedPizza = entityManager.find(Pizza.class, idPizza);
		System.out.println("Ora sono nella classe PizzaDAO, funzione findPizzaByIdPizza");
		System.out.println(retrievedPizza.getNomePizza() + " - " + retrievedPizza.getImpasto().getNomeImpasto());

		entityManager.getTransaction().commit();
		return retrievedPizza;
	}
	
	public List<Pizza> findPizzaByIdUtente(String idUtente) {
		gestisciEntityManager();
		entityManager.getTransaction().begin();

		@SuppressWarnings("unchecked")
		List<Pizza> retrievedPizze = entityManager.createQuery("select p from Pizza p where p.utente.idUtente = ?")
				.setParameter(1, Integer.valueOf(idUtente)).getResultList();
		System.out.println("Ora sono nella classe PizzaDAO, funzione findPizzaByIdUtente");
		for (Pizza pizza : retrievedPizze) {
			System.out.println(pizza.getImpasto().getNomeImpasto());
			
		}
		return retrievedPizze;

	}

	public List<Impasto> findAllImpasti() {
		gestisciEntityManager();
		entityManager.getTransaction().begin();

		return entityManager.createQuery("from Impasto", Impasto.class).getResultList();
		
	}

	public List<Ingrediente> findAllIngredienti() {
		gestisciEntityManager();
		entityManager.getTransaction().begin();

		return entityManager.createQuery("from Ingrediente", Ingrediente.class).getResultList();
	}

	public List<Utente> findAllUtenti() {
		gestisciEntityManager();
		entityManager.getTransaction().begin();

		return entityManager.createQuery("from Utente", Utente.class).getResultList();
	}
	
	public void deletePizzaByID(int idPizza) {

		gestisciEntityManager();
		entityManager.getTransaction().begin();
	
		Pizza pizzaDaEliminare = entityManager.find(Pizza.class, idPizza);
		entityManager.remove(pizzaDaEliminare); // cancella la pizza dal DB
		entityManager.getTransaction().commit();
	}
	
	public void updatePizzaByID(Pizza pizza) {
		gestisciEntityManager();
		entityManager.getTransaction().begin();
		
		Pizza pizzaDaModificare = entityManager.find(Pizza.class, pizza.getIdPizza());
		System.out.println("################ TEST UPDATE PIZZA ##################");
		System.out.println(pizzaDaModificare.getNomePizza() + " - " + pizzaDaModificare.getImpasto().getNomeImpasto());
		
		
		pizzaDaModificare.setNomePizza(pizza.getNomePizza());
		pizzaDaModificare.setImpasto(pizza.getImpasto());
		pizzaDaModificare.setIngredientes(pizza.getIngredientes());
//		pizzaDaModificare.setUtente(pizza.getUtente());
	
		entityManager.merge(pizzaDaModificare);
		System.out.println("######### CI ARRIVO QUA? ##########################################################");
		entityManager.getTransaction().commit();
		
//		return pizzaDaModificare;
	}

}