<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>




<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Argendata - Decisi&oacute;n - <c:out value="${dataset.title}" /></title>
		<c:import url = "/WEB-INF/head.jsp" />
		<script src="../../js/admin/desicionDataset.js"></script>
	</head>
	<body>
	<div class="container">
		<c:import url = "/WEB-INF/header.jsp" />

		<div id="cuerpo" class="span-24">
			<div id="breadcrumb" class= "prepend-1 span-22 append-1" >
				Administraci&oacute;n > Lista para aprobaci&oacute;n > Decisi&oacute;n
			</div>
	
	
<div class="prepend-1 prepend-top append-bottom">
	<br/>
  	
  	<div class="datasetPreview span-10 ">
  	<h2><c:out value="${dataset.title}" /></h2>
	<p>
		<b>Usuario:</b> <c:out value="${dataset.username}" />
	
		<br/></br>
		<b>Descripci&oacute;n:</b> <br/><c:out value="${dataset.description}" /><br/>
	
		<b>Publisher:</b> <c:out value="${dataset.publisher}" />
							
		<br/></br>
		<b>Palabra clave:</b> <c:out value="${dataset.keyword}" />
							
		<br/><br/>
		<b>Granularidad temporal:</b> <c:out value="${dataset.temporal}" />

		<br/><br/>
		<b>Granularidad espacial:</b> <c:out value="${dataset.spatial}" />						
						
		<br/></br>
		<b>URL:</b> <a target="new" href="<c:out value="${dataset.accessURL}" />"><c:out value="${dataset.accessURL}" /></a> 
						
		<br/></br>
		<b>Formato:</b> <c:out value="${dataset.format}" />
							
		<br/></br>
		<c:set var="string" value="${dataset.modified}"/>
		<i> &Uacute;ltima Modificaci&oacute;n: <c:out value="${fn:substring(string,0,10)}" /> </i>
	</p>
  	</div>
  	
  	
  	
		
		 
		<div class="prepend-1 span-7 append-1 last append-bottom">	
		<form:form action="decisionDataset" method="post" commandName="decisionForm" class="myForm">
    	<form:errors path="*" />
		<br/>         
		<label>Aprobar:</label>
		<br/>
		<form:select path="approved" >
			<option value="true" selected>Si</option>
			<option value="false">No</option>
		</form:select>
		<br/>
		
		<label>Decisi&oacute;n:</label>
		<br/>
		<form:textarea path="decision" />
		<br/>
		
		<br/>
		
		<form:hidden path="id"/>
		
		<input type="submit" value="Confirmar" />
		
		
		<input type="button" id="cancelar" value="Cancelar" />
			</form:form>
        </div>


</div>	
</div>
	
	<div style="clear:both"></div>
		
		<c:import url = "/WEB-INF/footer.jsp" />
	</div>
		
	
	</body>
</html>
