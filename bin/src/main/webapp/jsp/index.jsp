<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="ISO-8859-1">
<title>Login</title>
</head>

<body>
	<%
	String messaggio = (String) request.getAttribute("errorMessage");
	if (messaggio != null)
		out.println("<font color=red size=4px>"+messaggio+"</font>");
	// l'attributo deve corrispondere al parametro passato nel SetAttribute della servlet
	%>

	<h1>Form di autenticazione</h1>
	<form action="<%=request.getContextPath()%>/login" method="post">
		<!--  il bottone manda alla pagina /login (la servlet) -->
		<table style="with: 100%">
			<tr>
				<td>Nome utente</td>
				<td><input type="text" name="nomeUtente" /></td>
			</tr>
			<tr>
				<td>Password</td>
				<td><input type="password" name="passwordUtente" /></td>
			</tr>

		</table>
		<input type="submit" value="Login" />
	</form>
</body>

</html>

<!-- Form di inserimento dati username/password e click di submit -->