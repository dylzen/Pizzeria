package dao;

import model.Utente;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;


public class UtenteDAO {

	public static Utente validate(Utente utente) throws ClassNotFoundException {
		
		boolean status = false;
		String nomeUtente = utente.getNomeUtente();
		String passwordUtente = utente.getPasswordUtente();
		
		EntityManagerFactory entityFactory = Persistence.createEntityManagerFactory("Pizzeria");
		EntityManager entityManager = entityFactory.createEntityManager();

		String select = "SELECT u FROM Utente u WHERE u.nomeUtente=:nomeUtente and u.passwordUtente=:passwordUtente";

		Query query = entityManager.createQuery(select);
		query.setParameter("nomeUtente", nomeUtente);
		query.setParameter("passwordUtente", passwordUtente);

		if (query.getResultList().size() == 0) {
			System.out.println("Account non trovato!");
		} else {
			status = true;
			System.out.println("Login effettuato correttamente!");
			utente = (Utente) query.getSingleResult();
		}
		if (status) {
			return utente;
		} else {
			return null;
		}
	}

}
