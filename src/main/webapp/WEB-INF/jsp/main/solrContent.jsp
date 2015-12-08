<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/> 
<title>Argendata</title>
<link href="../../css/style.css" rel="stylesheet" type="text/css" />
</head>
<body>

<div id="topPan">
  
  <a href="/<c:out value="${projectName}" />/bin/main/home"><img border="0" src="../../images/agendata-v2 copia.png" alt="logo" width="281" height="56" class="logo" title="logo" /></a>
  <div style="text-align:right">
  <a href="http://linkeddata.org/" ><img border="0" src="../../images/blue.png" alt="linkeddata" title="we will rock you!"/></a>
   </div>
</div>
  
<div id="headerPan">
  <div id="headerleftPan"></div>
  <div id="headermiddlePan">
    <div id="menuPan">
      <ul>
        <li class="home">Inicio</li>
        <li><a href="/<c:out value="${projectName}" />/bin/dataset/findAll">Listar Datasets</a></li>
        <li><a href="/<c:out value="${projectName}" />/bin/dataset/add">Nuevo Dataset</a></li>
        <li><a href="/<c:out value="${projectName}" />/bin/search/view">Buscar</a></li>
        <li><a href="/<c:out value="${projectName}" />/bin/dataset/bulkUpload">Carga Masiva</a></li>
      </ul>
    </div>
    <div id="headerbodyPan">
      
    </div>
  </div>
  <div id="headerrightPan"><img src="../../images/blank.gif" alt="" width="1" height="1" /></div>
</div>

<div style="clear:both"/>

<div id="cuerpo" >

	<div id="datasets">
		<h2 >Datasets</h2>
		<br/>
		<ul>
		<c:forEach items="${datasets}" var="dataset">
			<li>
				<a href="/<c:out value="${projectName}" />/bin/dataset/view?qName=Dataset:<c:out value="${dataset.titleId}" />"><c:out value="${dataset.title}" /></a>
			</li>
		</c:forEach>
		</ul>
	</div>
	
</div>

<div style="clear:both"></div>

<div id="bodyPan">
  
	
</div>
<div id="bodybottomPan">
  <div id="bottomleftPan">
    <h2>Quienes somos? <br />
      <span>Los 3 mosqueteros.</span></h2>
    <ul>
      
    </ul>
    <br/>
  </div>
  <div id="bottomrightPan">
    <h2>Qu&eacute; es esto?<br />
      <span>informaci&oacute;n p&uacute;blica, para humanos y computadoras</span></h2>
    <ul>
      
    </ul>
    <br/>
  </div>
  
</div>

<div><br/>&nbsp;<br/></div>
<div id="footermainPan">
  <div id="footerPan" style="text-align:center">
    
    <p class="copyright">All lefts reserved.</p>
    
  </div>
</div>
</body>
</html>
