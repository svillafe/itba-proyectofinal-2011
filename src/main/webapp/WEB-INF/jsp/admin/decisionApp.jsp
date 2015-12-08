<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Argendata - Decisi&oacute;n - <c:out value="${app.name}" /> </title>
	<c:import url="/WEB-INF/head.jsp" />
	
	<script src="../../js/admin/desicionApp.js"></script>

</head>
<body>
	<div class="container"><c:import url="/WEB-INF/header.jsp" />
	
	<div id="cuerpo" class="span-24">
	<div id="breadcrumb" class="prepend-1 span-22 append-1">
	Administraci&oacute;n > Lista para aprobaci&oacute;n > Decisi&oacute;n</div>
	
	
	<div class="prepend-1 prepend-top"><br />
	
	<div class="appPreview  span-10 append-bottom">
	<h2><c:out value="${app.name}" /></h2>
	<p><b>Publicante:</b> <a href="../user/profile?username=<c:out value="${app.publisher.username}" />"/><c:out value="${app.publisher.username}" /></a> <br /><br />
	<b>Descripci&oacute;n:</b> <c:out value="${app.description}" /> <br />
	<br />
	<b>URL:</b> <a target="_new" href="../external/linkWP?webPage=<c:out value="${app.url}" />" ><c:out value="${app.url}" /></a> <br/>
	<br />
	<b>Palabras claves:</b>
	<ul>
					<c:forEach items="${app.keyword}" var="keyword">
	                       <li> <a href="../search/search?type=app&terms=&filters=&sortBy=null&page=1&resPerPage=10&keyword=<c:out value="${keyword}" />"><c:out value="${keyword}" /></a> </li> 
	                </c:forEach>
					</ul>
	<b>Datasets:</b>
	<ul>
					<c:forEach items="${datasets}" var="dataset">
	                       <li> <a href="../dataset/view?qName=Dataset:<c:out value="${dataset.titleId}"/>"><c:out value="${dataset.title}" /></a> </li> 
	                </c:forEach>
					</ul>
	<br />
	
	</p>
	<div class="span-10 last">
		<div class="span-4">
			<b>Icono:</b><br />
			<img class="appImageDetail"
				src="../apps/iconOfApp?idApp=Semanticapp:<c:out value="${app.nameId}"/>" />
		</div>
		<div class="span-6 last">
			<b>Screenshot:</b> <c:out value="${screenError}" /> <br />
			<img class="appScreenDetail"
				src="../apps/screenshotOfApp?idApp=Semanticapp:<c:out value="${app.nameId}"/>" /><br />
		</div>
	</div>
	
	<p><br /><br /><br /></p>
	</div>
	
	
	
	
		<div class="prepend-1 span-9 append-1 append-bottom">
	
	<form:form action="decisionApp" method="post" commandName="decisionForm"
		class="myForm">
		<form:errors path="*" />
	<br/>
		<label>Aprobar:</label> 
	
		<form:select path="approved">
			<option value="true" selected>Si</option>
			<option value="false">No</option>
		</form:select> <br />
	<br/>
		<label>Decisi&oacute;n:</label> <br />
		<form:textarea path="decision" /> <br />
	
		<br />
		<br />
	
		<form:hidden path="id" /> <input type="submit" value="Confirmar" />
		
			<input type="button" id="cancelar" value="Cancelar" />
			</form:form>
		</div>
	</div>
	</div>
	
	<div style="clear: both"></div>
	
	<c:import url="/WEB-INF/footer.jsp" /></div>
	

</body>
</html>
