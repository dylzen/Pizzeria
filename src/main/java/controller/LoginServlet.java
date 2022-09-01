package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import dao.UtenteDAO;
import model.Utente;

@WebServlet("/login") // l'URL della servlet al quale la pagina di login fa rediredt nel form action
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(LoginServlet.class);
	@SuppressWarnings("unused")
	private UtenteDAO loginDAO = new UtenteDAO();
       
    public LoginServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Salvo parametri ricevuti da login.jsp
		String nomeUtente = request.getParameter("nomeUtente");
		String passwordUtente = request.getParameter("passwordUtente");
		// creo oggetto utente per settare i parametri
		Utente utente = new Utente();
		
		// setto i parametri che varranno utlizzato nella classe LoginDAO dove è presente il metodo validate(...)
		utente.setNomeUtente(nomeUtente);
		utente.setPasswordUtente(passwordUtente);
		
		try {
			// se validate restituisce null, l'utente non è presente nel DB
			if (UtenteDAO.validate(utente) == null) {
				String errorMessage = "Utente non trovato!";
				request.setAttribute("errorMessage", errorMessage);
				request.getRequestDispatcher("jsp/index.jsp").forward(request, response);
			} else {
				utente = UtenteDAO.validate(utente);
				System.out.println("LoginServlet: ID dell'utente trovato ---->>>> " + utente.getIdUtente());
				System.out.println("LoginServlet: Nome dell'utente trovato ---->>>> " + utente.getNomeUtente());
				
				// SESSIONE
//				HttpSession session = request.getSession();
//				session.setAttribute("nomeUtente", utente.getNomeUtente());
				Cookie loginCookie = new Cookie("nomeUtente", utente.getNomeUtente());
				Cookie idCookie = new Cookie("idUtente", String.valueOf(utente.getIdUtente()));
				loginCookie.setMaxAge(30*60);
				idCookie.setMaxAge(30*60);
				response.addCookie(loginCookie);
				response.addCookie(idCookie);
				response.sendRedirect(request.getContextPath() + "/pizzeria");
				
//				request.setAttribute("nomeUtente", utente.getNomeUtente());
//				request.getSession().setAttribute("nomeUtente", nomeUtente);
				// con RD riesco a passare i parametri della richiesta al jsp
//				request.getRequestDispatcher("jsp/dashboard.jsp").forward(request, response);
//				request.getRequestDispatcher("/pizzeria").forward(request, response);
//				response.sendRedirect("http://localhost:8080/pizzeriadadylan/pizzeria");
				
			}
		} catch (ClassNotFoundException e) { // suggerito
			e.printStackTrace();
		}
	}

}
