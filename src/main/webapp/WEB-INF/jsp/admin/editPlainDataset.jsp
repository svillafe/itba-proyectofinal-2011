<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Argendata - Editar preDataset</title>
		<c:import url = "/WEB-INF/head.jsp" />
		<script src="../../js/common/modified.js"></script>
		<script src="../../js/common/mytags.js"></script>
	</head>
	<body>
	<div class="container">
		<c:import url = "/WEB-INF/header.jsp" />

		<div id="cuerpo" class="span-24">
			<div id="breadcrumb" class= "prepend-1 span-22 append-1" >
				Administrador > Editar Dataset
			</div>
	
	
<div class="prepend-1 prepend-top append-1">
	<br/>
  <h2>Editar Dataset NO publicado</h2>
  	<form:form action="editPlainDataset" method="post" commandName="editDatasetForm" class="myForm" id="datasetForm">
  		
  		<fieldset>
  		<legend>Complete los datos</legend>
  		<span class="error" style="">
    		<form:errors path="*" />
    	</span>
    	<p>Los campos se&ntilde;alados con <span style="color:green">(*)</span> son obligatorios.<br/>
    	</p>
    	<div class="prepend-1 span-18 column last" >
		<br/>
		<label>T&iacute;tulo:</label>
		<br/>
		<form:input path="title" style="width:720px; font-size:150%;"/><span style="color:green">(*)</span>
		<br/>
		<small>Con este nombre se destacar&aacute; al dataset. Debe ser descriptivo de los datos que contiene. </small>
		<br/> 
		<br/>
		<form:input type="hidden" path="oldTitleIdForEdition"  title=""/>
		<form:input type="hidden" path="username"  title=""/>
		<form:input type="hidden" path="id"  title=""/>
		<hr/>
		</div>	 
		<div class="prepend-1 span-8 append-1">	         
		
		
		<label>Licencia:</label>
		<br/>
		<form:input path="license" />
		<br/>
		<small>Tipo de licencia. </small>
		<br/> 
		
		<label>Palabras Claves:</label>
		<form:input path="keyword" style="visibility:hidden"/>
	
		<div class="line">
			<ul id="mytags"></ul>
			
		</div>
		<span style="color:green">(*)</span><br/>
<small>Estas ser&aacute;n las palabras claves que permitir&aacute;n categorizar y localizar f&aacute;cilmente los datasets. </small>
		<br/> 
		<br/>
		
		
		
		<label>Calidad de la informaci&oacute;n:</label>
		<br/>
		<form:input path="quality" title="Describe la calidad de los datos por ejemplo, precision."/>
		<br/>
		<small>Califica al dataset otorg&aacute;ndole una calidad a la utilidad de los mismos.</small>
		<br/> 
		<br/>
		
		
		
		
				
		<label>Modificado:</label>
		<br/>
		<form:input path="modified" />
		<br/>
		<small>De ser posible identific&aacute; una fecha estimativa a la cual pertenecen los datos. </small>
		<br/> 
		<br/>
		
		<label>Granularidad Geografica:</label>
		<br/>
		<form:input path="spatial" title="Granularidad geografica." />
		<br/>
		<small>Ejemplo: Nacional, Provincial, Municipal. </small>
		<br/> 
		<br/>
		
		<label>Ubicaci&oacute;n:</label>
		<br/>
		<form:input path="location" title="Provincia de la cual proviene la informacion."/>
		<br/>
		<small>Selecciones la provincia a la que respondan los datos.</small>
		<br/> 
		<br/>
		
		</div>
		<div class="prepend-1 span-8 append-1">
		
		<label>Granularidad Temporal:</label>
		<br/>
		<form:input path="temporal"  title="Granularidad temporal."/>
		<br/>
		<small>Indique a que per&iacute;odo de tiempo pertenecen los datos. Ejemplo: Semanal, Trimestral, Anual. </small>
		<br/> 
		<br/>
		
		<!-- Campos para el publisher -->
		<label>Publicante:</label>
		<br/>
		<form:input path="publisher"  title="Entidad que publica la informaciï¿½n."/><br/><span style="color:green">(*)</span>
		<br/>
		<small>Entidad que publica la informaci&oacute;n. </small>
		<br/> 
		<br/>
		
		<!-- Campos para la distribution -->
		
		<label>Direcci&oacute;n Web del recurso:</label>
		<br/>
		<form:input path="accessURL"  title="Direccion de acceso a la informaciï¿½n."/><br/><span style="color:green">(*)</span>
		<br/>
		<small>Direcci&oacute;n de internet de la ubicaci&oacute;n de los datos. </small>
		<br/> 
		<br/>
		
		<label>Tama&ntilde;o:</label>
		<br/>
		<form:input path="size"  title="Tamaï¿½o del archivo"/>
		<br/>
		<small>De tenerlo, indique el tama&ntilde;. </small>
		<br/> 
		<br/>
		
		<label>Formato:</label>
		<br/>
		
		<form:input path="format"  title="Formato de la informaciï¿½n."/>
		<br/>
		<small>De tenerlo, indique el formato en el cual se encuentran los datos. </small>
		<br/> 
		<br/>
		
		<label>Tipo de distribuci&oacute;n:</label>
		<br/>
		<form:input path="typeDistribution" title="Describe que tipo de distribuciï¿½n es."/>
		<br/>
		<small>Especifique el tipo de recurso. </small>
		<br/> 
		<br/>
		
		<br/>
		<br/>
		
        </div>
        
        
        <div class="clear">
			<input type="submit" value="Aceptar" />
		</div>
		<small>Aceptar no implica publicar. Esto lo puede hacer posteriormente con la opci&oacute;n correspondiente.</small>
		</fieldset>
	</form:form>

</div>	
</div>
	
	<div style="clear:both"></div>
		
		<c:import url = "/WEB-INF/footer.jsp" />
	</div>
		
	
	</body>
</html>
