<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Accueil</title>
</head>
<body>
	<jsp:include page="header.jsp" />
	<jsp:include page="menu.jsp" />


	<h2>Edition d'un livre</h2>
	<div style="color: fuchsia;">
		${erreur}
	</div>
	<form action="../gestionLivre/execute">
	<table width="600" border="1">
		<tr><td align="center">Id du livre : </td>
			<td align="center"><input type="text" name="id" value="${livre.id }" readonly="readonly"/></td>
		</tr>
		<tr><td align="center">Titre : </td>
			<td align="center"><input type="text" name="titre" value="${livre.titre }" /></td>
		</tr>
		<tr><td align="center">Auteur : </td>
			<td align="center"><input type="text" name="auteur" value="${livre.auteur}" /></td>
		</tr>
		<tr><td align="center">Date de parution : </td>
			<td align="center"><input type="text" name="dateParution" value="${livre.parution }" /></td>
		</tr>
		<tr><td align="left" colspan="2">
		<c:choose>
			<c:when test="${livre.id == 0 }">
				<input type="submit" name="creer" value="Creer"/>
			</c:when>
			<c:otherwise>
			<input type="submit" name="modifier" value="Modifier"/> &nbsp;
			<input type="submit" name="supprimer" value="Supprimer"/> 
			</c:otherwise>
		</c:choose>
		&nbsp; <input type="submit" name="retour" value="Retour"/> 
		</td>
		</tr>
		</table>
		
	</form>

	
	
</body>
</html>