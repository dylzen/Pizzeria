<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="dao.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="ISO-8859-1">
<title>Dashboard</title>
</head>

<body>
	<%
	String idUtente = null;
	String nomeUtente = null;
	Cookie[] cookies = request.getCookies();
	if (cookies != null) {
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("nomeUtente")) {
		nomeUtente = cookie.getValue();
			}
			if (cookie.getName().equals("idUtente")) {
		idUtente = cookie.getValue();
			}
		}
	}
	if (nomeUtente == null) {
		response.sendRedirect("jsp/index.jsp");
	}

	// l'attributo deve corrispondere al parametro passato nel SetAttribute della servlet
	%>
	<h1 align="center">
		Benvenuto,
		<%=nomeUtente%>! Il tuo ID è
		<%=idUtente%></h1>

	<br>
	<div id="pagina" style="width: 75%; margin: auto; top: 5%; left: 5%;">
		<form action="<%=request.getContextPath()%>/pizzeria?flag=creaPizza"
			method="post">


			<div id="impasto"
				style="width: 35%; margin: 10px; padding: 10px; float: left; position: relative; left: 80px;">
				<h2>Scegli l'impasto:</h2>
				<c:forEach var="impasto" items="${ listaImpasti }">

					<input type="radio" name="idImpasto" value="${impasto.idImpasto}" />
							${impasto.getNomeImpasto()}<br>
				</c:forEach>
			</div>
			<div id="ingredienti"
				style="width: 40%; margin: 10px; padding: 10px; float: right;">
				<h2>Scegli gli ingredienti:</h2>
				<c:forEach items="${ listaIngredienti }" var="ingrediente">
					<input type="checkbox" name="idIngrediente"
						value="${ingrediente.idIngrediente}" />
							${ingrediente.getNomeIngrediente()}<br>
				</c:forEach>
			</div>



			<br>
			<div id="nomePizza" style="clear: both; text-align: center;">
				<h2>Scegli un nome per la tua pizza</h2>
				<input type="text" name="nomePizza" value="${pizza.nomePizza}">
				<input type="hidden" name="idPizza" value="${pizza.idPizza}">
				<input type="hidden" name="idUtente"
					value="${cookie['idUtente'].getValue()}"> <input
					type="hidden" name="nomeUtente"
					value="${cookie['nomeUtente'].getValue()}"> <input
					type="submit" value="Aggiungi la pizza">
			</div>
		</form>



		<h2>Le tue pizze</h2>
		<table style="width: 100%; border: 1px solid black;">
			<tr>
				<th>Pizza</th>
				<th>Impasto</th>
				<th colspan="2">Azioni</th>
				<!-- 			<th>Ingredienti</th> -->

			</tr>
			<c:forEach items="${ pizze }" var="pizza">
				<tr>
					<td align="center">${pizza.nomePizza}</td>
					<td align="center">${pizza.getImpasto().getNomeImpasto()}</td>
					<td align="center"><a
						href="<%=request.getContextPath()%>/pizzeria?flag=modificaPizza&idPizza=${pizza.idPizza}">modifica</a></td>
					<td align="center"><a
						href="<%=request.getContextPath()%>/pizzeria?flag=eliminaPizza&idPizza=${pizza.idPizza}">elimina</a></td>
					<%-- 				<td align="center">${pizza.getIngredientes()[0]}</td> --%>

					<%-- <td align="center">${cookie['idUtente'].getName()}
				<td align="center">${cookie['idUtente'].getValue()} --%>
				</tr>
			</c:forEach>

		</table>
	</div>

</body>

</html>

<!--  Visualizza il nome dell'utente autenticato -->