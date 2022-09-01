<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Modifica pizza</title>
</head>
<body>

<div id="pagina"
		style="width: 75%; margin: auto; top: 5%; left: 5%;">
	<form action="<%=request.getContextPath()%>/pizzeria?flag=salvaPizza&idPizza=${pizza.idPizza}" method="post">

		<h2 align="center">Modifica la tua pizza</h2>
		<div id="impasto"
			style="width: 40%; margin: 10px; padding: 10px; float: left; position: relative; left: 100px;">
			<h2>Scegli l'impasto:</h2>
			<c:forEach var="impasto" items="${ impastoScelto }">
				<input type="radio" name="idImpasto" value="${impasto.idImpasto}"
					 checked />
							${impasto.getNomeImpasto()}<br>
			</c:forEach>
			<c:forEach var="impasto" items="${ impastiScartati }">
				<input type="radio" name="idImpasto" value="${impasto.idImpasto}"
					  />
							${impasto.getNomeImpasto()}<br>
			</c:forEach>
		</div>
		<div id="ingredienti"
			style="width: 40%; margin: 10px; padding: 10px; float: right;">
			<h2>Scegli gli ingredienti:</h2>
			<c:forEach items="${ ingredientiScelti }" var="ingrediente">
				<input type="checkbox" name="idIngrediente"
					value="${ingrediente.idIngrediente}" checked />
							${ingrediente.getNomeIngrediente()}<br>
			</c:forEach>
			<c:forEach items="${ ingredientiScartati }" var="ingrediente">
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
				type="submit" value="Salva le modifiche">
		</div>
	</form>
</div>

</body>
</html>