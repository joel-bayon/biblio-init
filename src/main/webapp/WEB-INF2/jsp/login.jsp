<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
		"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Authentification</title>
</head>
<body>
<form action="../authentification/execute">
<table>
	<tr><td><label>Identifiant : </label></td><td> <input type="text" name="identifiant"/></td></tr>
	<tr><td><label>Mot de passe : </label></td><td> <input type="password" name="motDePasse"/></td></tr>
	<tr><td colspan="2" align="right"><input type="submit" value="Valider"/></td></tr>
</table>
</form>
</body>
</html>
