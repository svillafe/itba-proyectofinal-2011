<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- reCaptcha things -->
<%@ page import="net.tanesha.recaptcha.ReCaptcha" %>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory" %>
<!--  end things -->



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Argendata - Nuevo dataset</title>
		<c:import url = "/WEB-INF/head.jsp" />
		<script src="../../js/common/modified.js"></script>
		<script src="../../js/common/mytags.js"></script>
		<script src="../../js/dataset/add.js"></script>
	</head>
	<body>
	
	<script type="text/javascript">
	var RecaptchaOptions = {
   		lang : 'es',
	};
	</script>
	<div class="container">
		<c:import url = "/WEB-INF/header.jsp" />

		<div id="cuerpo" class="span-24">
			<div id="breadcrumb" class= "prepend-1 span-22 append-1" >
				Proponer Dataset >
			</div>
	
	
<div class="prepend-1 prepend-top append-1">
	<br/>
  <h2>Nuevo Dataset</h2>
  	<form:form action="add" method="post" commandName="datasetForm" class="myForm">
  		
  		<fieldset>
  		<legend>Complet&aacute; los datos</legend>
  		<span class="error" style="">
    		<form:errors path="*" />
    	</span>
    	<p>Los campos se&ntilde;alados con <span style="color:green">(*)</span> son obligatorios.<br/>
    	</p>
    	<div class="prepend-1 span-18 column last" >
		<br/>
		<label>Titulo:</label>
		<br/>
		<form:input path="title" style="width:720px; font-size:150%;"/><span style="color:green">(*)</span>
		<br/>
		<small>Con este nombre se destacar&aacute; al dataset. Debe ser descriptivo de los datos que contiene. </small>
		<br/> 
		<br/>
		<hr/>
		</div>	 
		
		
		<div class="prepend-1 span-8 append-1">	
		
		<label>Descripci&oacute;n:</label>
		<br/>
		<form:textarea path="description" />
		<br/>
		<small>Una bre descripci&oacute;n del dataset. </small>
		<br/>          
		
		
		<label>Licencia:</label>
		<br/>
		<form:select path="license" >
				<option value="desconocida">desconocida</option>
				<option value="publico">publico</option>
				<option value="privado">privado</option>
				<option value="otra">otra</option>
		</form:select>
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
		<form:select path="quality" title="Describe la calidad de los datos por ejemplo, precision. ">
			<option value="Baja">Baja</option>
			<option value="Media">Media</option>
			<option value="Alta">Alta</option>
		</form:select>
		<br/>
		<small>Califica al dataset otorg&aacute;ndole una calidad a la utilidad de los mismos.</small>
		<br/> 
		<br/>
		
		
		
		<label>Granularidad Temporal:</label>
		<br/>
		<form:select path="temporal"  title="Granularidad temporal.">
				<option value="desconocida">desconocida</option>
				<option value="anual">anual</option>
				<option value="semestral">semestral</option>
				<option value="cuatrimestral">cuatrimestral</option>
				<option value="trimestral">trimestral</option>
				<option value="mensual">mensual</option>
				<option value="semanal">semanal</option>
				<option value="diaria">diaria</option>
				<option value="otra">otra</option>
			</form:select>
		<br/>
		<small>Indic&aacute; a que per&iacute;odo de tiempo pertenecen los datos. Ejemplo: Semanal, Trimestral, Anual. </small>
		<br/> 
		<br/>
		
				
		
		
	
		</div>
		<div class="prepend-1 span-8 append-1">
		<label>Granularidad Geografica:</label>
		<br/>
		<form:select path="spatial" title="Granularidad geografica." >
				<option value="desconocida">desconocida</option>
				<option value="nacional">nacional</option>
				<option value="regional">regional</option>
				<option value="provincial">provincial</option>
				<option value="municipal">municipal</option>
				<option value="comunal">comunal</option>
				<option value="otra">otra</option>
		</form:select>
		<br/>
		<small>Ejemplo: Nacional, Provincial, Municipal. </small>
		<br/> 
		<br/>
		
		<div id="location-div">
			<label>Ubicaci&oacute;n:</label>
			<br/>
			<form:select path="location" title="Provincia de la cual proviene la informacion.">
				<option value="desconocida">desconocida</option>
				<option value="Buenos Aires">Buenos Aires</option>
				<option value="Catamarca">Catamarca</option>
				<option value="Chaco">Chaco</option>
				<option value="Chubut">Chubut</option>
				<option value="Ciudad Autonoma de Buenos Aires">Ciudad Autonoma de Buenos Aires</option>
				<option value="Cordoba">Cordoba</option>
				<option value="Corrientes">Corrientes</option>
				<option value="Entre Rios">Entre Rios</option>
				<option value="Formosa">Formosa</option>
				<option value="Jujuy">Jujuy</option>
				<option value="La Pampa">La Pampa</option>
				<option value="La Rioja">La Rioja</option>
				<option value="Mendoza">Mendoza</option>
				<option value="Misiones">Misiones</option>
				<option value="Neuquen">Neuquen</option>
				<option value="Rio Negro">Rio Negro</option>
				<option value="Salta">Salta</option>
				<option value="San Juan">San Juan</option>
				<option value="San Luis">San Luis</option>
				<option value="Santa Cruz">Santa Cruz</option>
				<option value="Santa Fe">Santa Fe</option>
				<option value="Santiago del Estero">Santiago del Estero</option>
				<option value="Tierra del Fuego">Tierra del Fuego</option>
				<option value="Tucuman">Tucuman</option>
			</form:select>
			<br/>
			<small>Seleccion&aacute; la provincia a la que respondan los datos.</small>
			<br/> 
			<br/>
		</div>
		
		
		<!-- Campos para el publisher -->
		<label>Publicante:</label>
		<br/>
		<form:input path="publisher" id="publisher" title="Entidad que publica la informacion."/><br/><span style="color:green">(*)</span>
		<br/>
		<small>Entidad que publica la informaci&oacute;n. </small>
		<br/> 
		<br/>
		
		<!-- Campos para la distribution -->
		
		<label>Direcci&oacute;n Web del recurso:</label>
		<br/>
		<form:input path="accessURL"  title="Direccion de acceso a la informacion."/><br/><span style="color:green">(*)</span>
		<br/>
		<small>Direcci&oacute;n de internet de la ubicaci&oacute;n de los datos. Ejemplo: http://www.primarias2011.gov.ar</small>
		<br/> 
		<br/>
		
		<label>Tipo de distribuci&oacute;n:</label>
		<br/>
		<form:select id="typeDistribution" path="typeDistribution" title="Describe que tipo de distribucion es.">
			<option value="Feed">Feed</option>
			<option value="Download">Descarga</option>
			<option value="WebService">Web Service</option>
		</form:select>
		<br/>
		<small>Especific&aacute; el tipo de recurso. "Feed" se utiliza para los recursos de tipo RSS/Atom, "Web Service" para las APIs o p&aacute;ginas con informaci&oacute;n o de b&uacute;squeda, "Descarga" para aquellos recursos directamente descargables. </small>
		<br/> 
		<br/>

		<div id="downloadInfo">
			
			
			<label>Formato:</label>
			<br/>
			
			<form:select path="format"  title="Formato de la informacion.">
				<option value="pdf">pdf</option>
				<option value="xls">xls</option>
				<option value="csv">csv</option>
				<option value="doc">doc</option>
				<option value="doc">txt</option>
				<option value="otro">otro</option>
			</form:select>
			<br/>
			<small>De tenerlo, indic&aacute; el formato en el cual se encuentran los datos. </small>
			<br/> 
			<br/>
		</div>
		<label>Modificado:</label>
		<br/>
		<form:input path="modified" />
		<br/><span style="color:green">(*)</span><br/>
		<small>De ser posible identific&aacute; una fecha estimativa a la cual pertenecen los datos.</small>
		<br/> 
		<br/>
		
		<br/>
		<br/>
		
        </div>
        
        
        
        <form:hidden path="username" />
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
