<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/> 
<title>Argendata - Listar Datasets</title>
<link href="../../css/style.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div id="topPan"><a href="/argendata/bin/main/home"><img
	border="0" src="../../images/agendata-v2 copia.png" alt="logo"
	width="281" height="56" class="logo" title="logo" /></a>
<div style="text-align: right"><a href="http://linkeddata.org/"><img
	border="0" src="../../images/blue.png" alt="linkeddata"
	title="we will rock you!" /></a></div>
</div>

<div id="headerPan">
<div id="headerleftPan"></div>
<div id="headermiddlePan">
<div id="menuPan">
<ul>
	<li class="home">Buscar</li>
	<li><a href="/argendata/bin/main/home">Inicio</a></li>
	<li><a href="/argendata/bin/dataset/add">Nuevo Dataset</a></li>
	<li><a href="/argendata/bin/dataset/findAll">Listar Datasets</a></li>
	<li><a href="/argendata/bin/dataset/bulkUpload">Carga Masiva</a></li>
</ul>
</div>

<div id="headerbodyPan"></div>
</div>
<div id="headerrightPan"><img src="../../images/blank.gif" alt=""
	width="1" height="1" /></div>
</div>

<div id="bodyPan">
	<div id="SearchForm">
		<h2>Busqueda Facetada</h2>
		<form:form action="facetedSearch" method="post"
			commandName="facetedSearchForm">
			<form:errors path="*" />
			<br />
			<form:input path="searchText" />
			<br />
			<input type="submit" value="Buscar" />
		</form:form></div>
	</div>
	<c:if test="${! empty dataset }">
		<div id="response">
			<table style="margin:0px auto; padding:0px;">
				<thead>
					<tr>
	      				<th>Title</th>
      					<th>License</th>
    				</tr>
				</thead>
				<tbody >
				    <c:forEach items="${datasets}" var="dataset">
						<tr>
							<td><c:out value="${dataset.title}" /></td>
							<td><c:out value="${dataset.license}" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</c:if>
	<c:if test="${ empty dataset }">
		<span>No se encontraron resultados.</span>
	</c:if>
	


<div id="bodybottomPan"></div>
<div><br />
&nbsp;<br />
</div>
<div id="footermainPan">
<div id="footerPan" style="text-align: center">

<p class="copyright">All lefts reserved.</p>

</div>
</div>
</body>
</html>