<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@page import="java.util.List"%>
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


	<h2>Liste des livres de la bibliothèque</h2>

	<c:choose>
		<c:when test="${empty livres}">
			<h2>Pas de livres dans la bibliothèque</h2>
		</c:when>
		<c:otherwise>
			<table width="600" border="1">
				<tr>
					<td align="center">Id du livre</td>
					<td align="center">Titre</td>
					<td align="center">Auteur</td>
					<td align="center">Date de parution</td>
				</tr>
				<c:forEach items="${livres }" var="livre">
					<tr>
						<td align="center"><a
							href="../gestionLivre/editerLivre?id=${livre.id }">${livre.id}</a></td>
						<td align="center">${livre.titre }</td>
						<td align="center">${livre.auteur}</td>
						<td align="center">${livre.parution }</td>
					</tr>
				</c:forEach>
				
				<tr>
					<td align="right" colspan="4">
						<form action="../gestionLivre/editerLivre" method="get">
							<input type="hidden" name="id" value="0"/>
							<input type="submit" value="Nouveau livre"/>
						</form>
					</td>
				</tr>
				
			</table>
		</c:otherwise>
	</c:choose>
</body>
</html>