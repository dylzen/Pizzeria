<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="dao.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="ISO-8859-1">
<title>Dashboard</title>
</head>

<body>
	<% 
	
	String nomeUtente = (String) request.getAttribute("nomeUtente");
	// l'attributo deve corrispondere al parametro passato nel SetAttribute della servlet 
	%>
	<h1>Benvenuto, <%= nomeUtente %></h1>
	
	<br>
	
	<h2>Le tue pizze</h2>
	<table style="width:100%" rules="all" >
		<tr>
			<th>Pizza</th>
			<th>Impasto</th>
			
		</tr>
		<c:forEach items="${ pizze }" var="pizza">
			<tr>
				<td align="center">${pizza.nomePizza}</td>
				<td align="center"><c:out value="${pizza.utente}" /></td>
				
			</tr>
		</c:forEach>
	</table> 
	
</body>

</html>

<!--  Visualizza il nome dell'utente autenticato -->