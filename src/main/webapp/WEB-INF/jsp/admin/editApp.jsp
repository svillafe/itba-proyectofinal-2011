<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Argendata - Editar App </title>
		<c:import url = "/WEB-INF/head.jsp" />
		<script src="../../js/common/mytags.js"></script>
		<script src="../../js/common/mydatasets.js"></script>
	</head>
	<body>
	<div class="container">
		<c:import url = "/WEB-INF/header.jsp" />

		<div id="cuerpo" class="span-24">
			<div id="breadcrumb" class= "prepend-1 span-22 append-1" >
				Administrador > Editar App
			</div>
	
	
<div class="prepend-1 prepend-top append-1">
	<br/>
  <h2>Editar App</h2>
  	<form:form action="editApp" method="post" commandName="appForm" enctype="multipart/form-data"	class="myForm">
  		
  		<fieldset>
  		<legend>Complet&aacute; los datos</legend>
  		<span class="error" style="">
    		<form:errors path="*" />
    	</span>
    	<p>Los campos se&ntilde;alados con <span style="color:green">(*)</span> son obligatorios.<br/>
    	</p>
    	<div class="prepend-1 span-18 column last" >
		<br/>
		<label>Nombre:</label>
		<br/>
		<form:input path="name" style="width:720px; font-size:150%;"/><span style="color:green">(*)</span>
		<br/>
		<small>Con este nombre se destacar&aacute; a la aplicaci&oacute;n. Debe ser descriptivo. </small>
		<br/> 
		<br/>
		<form:hidden path="oldTitleIdForEdition"/>
		<form:hidden path="isAllowed"/>
		<form:hidden path="username" />
		<hr/>
		</div>	 
		<div class="prepend-1 span-8 append-1">	         
		
		
		<label>Descripci&oacute;n:</label>
		<br/>
		<form:textarea path="description" rows="6" cols="15" />
		<br/>
		<small>Breve descripci&oacute;n de la aplicaci&oacute;n. </small>
		<br/>
		
		<label>Direcci&oacute;n web:</label>
		<br/>
		<form:input path="url" />
		<br/>
		<small>Direcci&oacute; web de la aplicaci&oacute;n.</small>
		<br/> 
		
		<label>Dataset:</label>
		<form:input path="dataset" style="visibility:hidden"/>
	
			<div class="line">
			<ul id="mydatasets"></ul>
			
			</div>
		<span style="color:green">(*)</span><br/>
		<small>Introduc&iacute; los t&iacute;tulos de los datasets que tienen que ver con tu aplicaci&oacute;n. </small>
		<br/> 
		<br/>
		</div> 
		
		
		<div class="prepend-1 span-8 append-1">	     
		<label>Icono:</label>
		<label>Actual</label><br/>
		<img class="appImageDetail"
		src="../apps/iconOfApp?idApp=Semanticapp:<c:out value="${app.nameId}"/>" /><br />
		<label>Nuevo:</label><br/>
		<form:input path="logo" type="file" /><br />
		<small>Icono de tu aplicaci&oacute;n.</small>
		<p><br/></p>
	
		<label>ScreenShot:</label>
		<label>Actual</label><br/>
		
		<img class="appScreenDetail" src="../apps/screenshotOfApp?idApp=Semanticapp:<c:out value="${app.nameId}"/>" /><br />
		<label>Nuevo:</label><br/>
		<form:input path="screenshot" type="file" /><br />
		<small>Screenshot de tu aplicaci&oacute;n en funcionamiento.</small>
		<p><br/></p>
		
		
		
		
		<!-- Aca empieza los tags -->
		
			
		<label>Palabras Claves:</label>
		<form:input path="keyword" style="visibility:hidden"/>
	

		<div class="line">
			<ul id="mytags"></ul>
			
		</div>
		<span style="color:green">(*)</span><br/>
		<small>Estas ser&aacute;n las palabras claves que permitir&aacute;n categorizar y localizar f&aacute;cilmente los datasets. </small>
		<br/> 
		<br/>
		
		
		<!-- Aca terminan los tags-->
		
		
		</div>
		
		<div class="clear">
			<input type="submit" value="Aceptar" />
		</div>
		</fieldset>
	</form:form>

</div>	
</div>
	
	<div style="clear:both"></div>
		<c:import url = "/WEB-INF/footer.jsp" />
	</div>
		
	
	</body>
</html>
