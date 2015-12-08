<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- reCaptcha things -->
<%@ page import="net.tanesha.recaptcha.ReCaptcha"%>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory"%>
<!--  end things -->


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Argendata - Nueva Aplicaci&oacute;n</title>
	<c:import url="/WEB-INF/head.jsp" />
	<script src="../../js/common/mytags.js"></script>
	<script src="../../js/common/mydatasets.js"></script>
</head>
<body>
<script type="text/javascript">
	var RecaptchaOptions = {
   		lang : 'es',
	};
	</script>
<div class="container"><c:import url="/WEB-INF/header.jsp" />

<div id="cuerpo" class="span-24">
<div id="breadcrumb" class="prepend-1 span-22 append-1">
Aplicaciones > add ></div>

<div class="prepend-1 prepend-top append-1"><br />
<h2>Nueva Aplicaci&oacute;n</h2>
<form:form action="add" method="post" commandName="appForm"
	enctype="multipart/form-data" class="myForm">
	
	<fieldset><legend>Complet&aacute; los datos</legend> 
	<span
		class="error"><c:out value="${errorMaxUp}"/></span>
	<span
	
		class="error"><form:errors path="*" /></span> <br />
		


	<div class="prepend-1 span-8 append-1"><label>Nombre:</label> <br />
	<form:input path="name" /> <br />
	<span style="color: green">(*)</span><br />
	<small>Nombre de la aplicaci&oacute;n. Deber&aacute; ser
	&uacute;nico.</small>
	<p><br />
	</p>

	<label>Descripci&oacute;n:</label> <br />
	<form:textarea path="description" rows="3" cols="5" /><br />
	<span style="color: green">(*)</span><br />
	<small>Ingres&aacute; la descripci&oacute;n de aplicaci&oacute;n.
	Ser&aacute; la forma en que los demas usuarios sepan en que consiste la
	misma.</small>
	<p><br />
	</p>

	<label>URL:</label> <br />
	<form:input path="url" rows="6" cols="15" /><br />
	<span style="color: green">(*)</span><br />
	<small>Ingres&aacute; la direcci&oacute;n web de la aplicaci&oacute;n.</small>
	<p><br />
	</p>

	</div>

	<div class="prepend-1 span-8 append-1"><label>Icono:</label> <br />
	<form:input path="logo" type="file" /><br />
	<small>Icono de tu aplicaci&oacute;n. (m&aacute;ximo 200 KB)</small>
	<p><br />
	</p>

	<label>Screenshot:</label> <br />
	<form:input path="screenshot" type="file" /><br />
	<small>Screenshot de tu aplicaci&oacute;n en funcionamiento. (m&aacute;ximo 200 KB)</small>
	<p><br />
	</p>


	<label>Palabras Claves:</label> <form:input path="keyword"
		style="visibility:hidden" />

	<div class="line">
	<ul id="mytags"></ul>

	</div>
	<span style="color: green">(*)</span><br />
	<small>Estas ser&aacute;n las palabras claves que
	permitir&aacute;n categorizar y localizar f&aacute;cilmente las
	apps. </small> <br />
	<br />

	<label>Dataset:</label> <form:input path="dataset"
		style="visibility:hidden" />

	<div class="line">
	<ul id="mydatasets"></ul>

	</div>
	<span style="color: green">(*)</span><br />
	<small>Estos son los datasets que tienen que ver con tu
	aplicaci&oacute;n. </small> <br />
	<br />
	</script></div>

	<form:hidden path="username" /> <br />
	<div class="clear"><input type="submit" value="Aceptar" /></div>
	</fieldset>

</form:form></div>
</div>


<div style="clear: both">
<c:import url="/WEB-INF/footer.jsp" />
</div>
</div>

</body>
</html>