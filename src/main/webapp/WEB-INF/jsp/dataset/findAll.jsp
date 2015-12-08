<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Argendata - :)</title>
<c:import url="/WEB-INF/head.jsp" />
</head>
<body>
<div class="container"><c:import url="/WEB-INF/header.jsp" />

<div id="cuerpo" class="span-24">
<div id="breadcrumb" class="prepend-1 span-22 append-1">Listado >
</div>



<div class="prepend-1 prepend-top"><br />
<h2>Datasets</h2>
</div>

<big>SE BORRO EL INDICE SOLR</big>

<div class="prepend-1 append-1 span-16">
<c:forEach items="${datasets}" var="dataset">

	<div class="datasetPreview">
	<h2><a class="anchorTitle" title="Detalles"
		href="/<c:out value="${projectName}" />/bin/dataset/view?qName=Dataset:<c:out value="${dataset.titleId}" />"><c:out
		value="${dataset.title}" /></a></h2>

	<small><c:out value="${dataset.publisher.name}" /></small>
	<p>Palabra clave: <c:out value="${dataset.keyword}" /> <br />
	<!-- Granularidad temporal: <c:out value="${dataset.temporal}" /></p>

	<p><b>URL:</b> <a target="_new" href="../external/link?qName=Dataset:<c:out value="${dataset.titleId}" />" ><c:out value="${dataset.distribution.accessURL}" /></a> | <b>Formato:</b> <c:out
		value="${dataset.distribution.format}" /></p>
		
		
	<small><i> &Uacute;ltima Modificaci&oacute;n: <c:out
		value="${dataset.modified}" /> </i></small><br />
	-->
	</div>
</c:forEach></div>
<div class="span-6 last">:)</div>
</div>
<div style="clear: both"></div>



<c:import url="/WEB-INF/footer.jsp" /></div>


</body>
</html>

