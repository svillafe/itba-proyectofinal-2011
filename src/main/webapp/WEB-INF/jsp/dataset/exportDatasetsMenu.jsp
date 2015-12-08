<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Argendata - Exportaci&oacute;n de Datasets</title>
<c:import url="/WEB-INF/head.jsp" />
</head>
<body>
<div class="container"><c:import url="/WEB-INF/header.jsp" />

<div id="cuerpo" class="span-24">
<div id="breadcrumb" class="prepend-1 span-22 append-1">
Exportaci&oacute;n de Datasets ></div>

<div class="prepend-1 prepend-top append-1"><br />
<h1>Exportaci&oacute;n de Datasets</h1>
<c:import url="../admin/adminMenu.jsp" />

Para obtener un archivo con extensi&oacute;n <em>.csv</em> con todos los
datasets almacenados en la base de datos haga click <a
	href="../dataset/exportDatasets">aqui</a>.</div>
</div>

<div style="clear: both"><c:import url="/WEB-INF/footer.jsp" /></div>
</div>

</body>
</html>
