package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.PizzaDAO;
import model.Pizza;
import model.Utente;
import model.Impasto;
import model.Ingrediente;

/**
 * Servlet implementation class PizzaServlet
 */
@WebServlet(urlPatterns = "/pizzeria") // l'URL della servlet al quale la pagina di login fa rediredt nel form action
public class PizzaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PizzaServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("#################### PIZZA SERVLET ##################");
		String flag = request.getParameter("flag");

		if ("salvaPizza".equalsIgnoreCase(flag)) {
			try {
				salvaPizza(request, response);
			} catch (SQLException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		PizzaDAO pizzaDAO = new PizzaDAO();

		String idUtente = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			System.out.println("SONO NEI COOKIES");
			for (Cookie cookie : cookies) {
				System.out.println(cookie.getName() + " - " + cookie.getValue());
				if (cookie.getName().equals("idUtente")) {
					idUtente = cookie.getValue();
					// value can be retrieved using #cookie.getValue()
				}
			}
		} else {
			System.out.println("NON CI SONO COOKIES!");
		}

		List<Pizza> listaPizze = pizzaDAO.findPizzaByIdUtente(idUtente);
		request.setAttribute("pizze", listaPizze);

		List<Impasto> listaImpasti = pizzaDAO.findAllImpasti();
		request.setAttribute("listaImpasti", listaImpasti);

		List<Ingrediente> listaIngredienti = pizzaDAO.findAllIngredienti();
		request.setAttribute("listaIngredienti", listaIngredienti);

		List<Utente> listaUtenti = pizzaDAO.findAllUtenti();
		request.setAttribute("utenti", listaUtenti);

		// Se il flag nell'url indica la modifica
		if ("modificaPizza".equalsIgnoreCase(flag)) {
			int idPizza = Integer.valueOf(request.getParameter("idPizza"));
			System.out.println("######## TEST UPDATE PIZZA FLAG MODIFICA ###########");
			System.out.println("ID della pizza da modificare: " + idPizza);
			Pizza pizza = pizzaDAO.findPizzaByIdPizza(idPizza);
			request.setAttribute("pizza", pizza);

//			request.getRequestDispatcher("/jsp/modifica_pizza.jsp").forward(request, response);
			try {
				modificaPizzaForm(request, response);
			} catch (SQLException | ServletException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if ("eliminaPizza".equalsIgnoreCase(flag)) { // Se il flag nell'url indica l'eliminazione
			String idPizza = request.getParameter("idPizza");
			if (idPizza != "") {
				pizzaDAO.deletePizzaByID(Integer.valueOf(idPizza));
			}
			// dopo l'eliminazione, mostro la lista delle pizze dell'utente e aggiorno alla
			// dashboard
			listaPizze = pizzaDAO.findPizzaByIdUtente(idUtente);
			request.setAttribute("pizze", listaPizze);
			// Se uso il dispatcher funziona ma rimane il flag di eliminazione nell'URL dopo
			// il refresh
			response.sendRedirect(request.getContextPath() + "/pizzeria");
		} else {
			request.getRequestDispatcher("/jsp/dashboard.jsp").forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String flag = request.getParameter("flag");
		if ("salvaPizza".equalsIgnoreCase(flag)) {
			try {
				salvaPizza(request, response);
			} catch (SQLException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		PizzaDAO pizzaDAO = new PizzaDAO();

		String idPizza = request.getParameter("idPizza");
		System.out.println("ID PIZZA PER LA CREAZIONE: " + idPizza);
		String nomePizza = request.getParameter("nomePizza");
		System.out.println("NOME PIZZA PER LA CREAZIONE: " + nomePizza);
		String idImpasto = request.getParameter("idImpasto");
		System.out.println("ID IMPASTO PER LA CREAZIONE: " + idImpasto);

		String idUtente = request.getParameter("idUtente");
		System.out.println("ID UTENTE PER LA CREAZIONE: " + idUtente);
		String nomeUtente = request.getParameter("nomeUtente");
		System.out.println("NOME UTENTE PER LA CREAZIONE: " + nomeUtente);
		Utente utente = new Utente();
		utente.setIdUtente(Integer.valueOf(idUtente));
		utente.setNomeUtente(nomeUtente);

		Impasto impasto = new Impasto();
		if (idImpasto != "" && Integer.valueOf(idImpasto) > 0) {
			impasto.setIdImpasto(Integer.valueOf(idImpasto));
		}

		List<Ingrediente> listaIngredienti = new ArrayList<>();
		// Recupero la lista degl ingredienti selezionati durante la creazione
		String[] idIngredienti = request.getParameterValues("idIngrediente");

		for (String idIngrediente : idIngredienti) {
			System.out.println("ID INGREDIENTI PER LA CREAZIONE: " + idIngrediente);
			Ingrediente ingrediente = new Ingrediente();
			if (idIngrediente != "" && Integer.valueOf(idIngrediente) > 0) {
				ingrediente.setIdIngrediente(Integer.valueOf(idIngrediente));
				listaIngredienti.add(ingrediente);
			}
		}

		if ("creaPizza".equalsIgnoreCase(flag)) {
			Pizza pizza = new Pizza();
			pizza.setNomePizza(nomePizza);
			pizza.setImpasto(impasto);
			pizza.setIngredientes(listaIngredienti);
			pizza.setUtente(utente);

			if (idPizza != "" && Integer.valueOf(idPizza) > 0) {
				pizza.setIdPizza(Integer.valueOf(idPizza));
			}

			pizzaDAO.createPizzaRecord(pizza);
			response.sendRedirect(request.getContextPath() + "/pizzeria");
		}
		

	}

	private void modificaPizzaForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		System.out.println("######## TEST UPDATE PIZZA modificaPizzaForm ###########");

		PizzaDAO pizzaDAO = new PizzaDAO();
		System.out.println("1 modificaPizzaForm - CERCO LA PIZZA DALL'ID");
		Pizza pizza = pizzaDAO.findPizzaByIdPizza(Integer.parseInt(request.getParameter("idPizza")));
		
		
		// Recupero la lista completa di impasti ed ingredienti
		List<Impasto> listaImpasti = pizzaDAO.findAllImpasti();
		List<Ingrediente> listaIngredienti = pizzaDAO.findAllIngredienti();
		
		List<Impasto> impastoScelto = new ArrayList<>();
		impastoScelto.add(pizza.getImpasto());
		request.setAttribute("impastoScelto", impastoScelto);
		
		listaImpasti.removeIf(c -> impastoScelto.stream()
                .map(Impasto::getNomeImpasto)
                .anyMatch(n -> n.equals(c.getNomeImpasto())));
		request.setAttribute("impastiScartati", listaImpasti);
		
		request.setAttribute("ingredientiScelti", pizza.getIngredientes());
		
		listaIngredienti.removeIf(c -> pizza.getIngredientes().stream()
                .map(Ingrediente::getNomeIngrediente)
                .anyMatch(n -> n.equals(c.getNomeIngrediente())));
		
		System.out.println("Lista degli ingredienti dopo la rimozioni di quelli scelti: " + listaIngredienti);
		
		request.setAttribute("ingredientiScartati", listaIngredienti);
		request.getRequestDispatcher("/jsp/modifica_pizza.jsp").forward(request, response);

	}

	private void salvaPizza(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {

		PizzaDAO pizzaDAO = new PizzaDAO();
		int idPizza = Integer.parseInt(request.getParameter("idPizza"));
		Pizza pizza = pizzaDAO.findPizzaByIdPizza(idPizza);

		String nomePizza = request.getParameter("nomePizza");
		int idImpasto = Integer.parseInt(request.getParameter("idImpasto"));
		Impasto impasto = new Impasto();
		impasto.setIdImpasto(idImpasto);
		List<Ingrediente> listaIngredienti = new ArrayList<>();
		// Recupero la lista degl ingredienti selezionati durante la creazione
		String[] idIngredienti = request.getParameterValues("idIngrediente");

		for (String idIngrediente : idIngredienti) {
			System.out.println("ID INGREDIENTI PER LA CREAZIONE: " + idIngrediente);
			Ingrediente ingrediente = new Ingrediente();
			if (idIngrediente != "" && Integer.valueOf(idIngrediente) > 0) {
				ingrediente.setIdIngrediente(Integer.valueOf(idIngrediente));
				listaIngredienti.add(ingrediente);
			}
		}
		pizza.setNomePizza(nomePizza);
		pizza.setImpasto(impasto);
		pizza.setIngredientes(listaIngredienti);
		pizzaDAO.updatePizzaByID(pizza);

		response.sendRedirect(request.getContextPath() + "/pizzeria");
	}

}
