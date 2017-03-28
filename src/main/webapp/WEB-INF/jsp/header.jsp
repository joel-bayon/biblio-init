<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<fmt:setBundle basename="bundle.bundles" />
<fmt:message  key="header.date.pattern" var="pattern" scope="request"/>

<table width="700">
  <tr>
    <td align="left"> <fmt:message  key="header.nom"/> <b>${user.userName }</b></td>
    <td align="right"><fmt:message  key="header.date"/> <b>
    <fmt:formatDate value="${dateConnexion }"  pattern="${pattern }"/>
  </b> </td>
  </tr>
</table>



