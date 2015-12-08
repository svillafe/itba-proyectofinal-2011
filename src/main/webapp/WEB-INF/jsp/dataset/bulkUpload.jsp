<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Argendata - Carga Masiva</title>
		<c:import url = "/WEB-INF/head.jsp" />
	</head>
	<body>
	<div class="container">
		<c:import url = "/WEB-INF/header.jsp" />

		<div id="cuerpo" class="span-24">
			<div id="breadcrumb" class= "prepend-1 span-22 append-1" >
				Carga Masiva >
			</div>

<div class="prepend-1 prepend-top append-1">
	<br/>
        <h1 >Carga Masiva de Datasets</h1>
        <c:import url="../admin/adminMenu.jsp" />
        
<form:form name="input" action="bulkUpload" method="post" commandName="bulkUploadForm" enctype="multipart/form-data">
<fieldset>
		<legend>Complete los datos</legend>
<div class="span-6">	

		
		<form:input path="file" type="file" /><br/>
		<form:errors path="file"/><br/>
	
		
		<h3><c:out value="${info}"/></h3>
		<h3><c:out value="${error}"/></h3>
		<input  type="submit" value="Cargar"/>

</div>
<div class="prepend-1  span-6">

<div id = "helpDialog">
Solo se permiten archivos con extensi&oacute;n <b>.txt</b> o <b>.csv</b> donde cada l&iacute;nea tiene el siguiente formato:<br/>

 T&iacute;tulo; Licencia; Tags (Separados con ,);Calidad de la informaci&oacute;n;
		  Modificado; Granularidad Geografica; Granularidad Temporal;
		  Publicante; URL de Acceso; Tama&ntilde;o; Formato; Tipo de Distribuci&oacute;n;
		  Locaci&oacute;n; Descripci&oacute;n.

</div>

</div>
<div class="clear prepend-1">
	
	</div>
	</fieldset>
	</form:form>

</div>
</div>

<div style="clear:both">
		
		<c:import url = "/WEB-INF/footer.jsp" />
</div>
</div>		
	
	</body>
</html>
