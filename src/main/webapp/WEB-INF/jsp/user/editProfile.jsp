<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Argendata - Editar perfil</title>
<c:import url="/WEB-INF/head.jsp" />
</head>
<body>
<div class="container"><c:import url="/WEB-INF/header.jsp" />

<div id="cuerpo" class="span-24">
<div id="breadcrumb" class="prepend-1 span-22 append-1">Usuario > Editar Perfil</div>

<div id="whatis" class="prepend-1 span-22 append-1 last append-bottom" style="margin-top:20px;">
			<h1> Editar Perfil </h1>
			
				<c:choose>
					<c:when test="${user.admin}">
						<c:import url="../admin/adminMenu.jsp"/>
					</c:when>
					<c:otherwise>
						<c:import url="userMenu.jsp"/>
					</c:otherwise>
				</c:choose>
				
				<div class="sectionTitle prepenend-1 span-15 last" style="text-align:justify">
				<form:form method="POST" action="../user/editProfile" commandName="userForm">
					<fieldset><legend>Actualiz&aacute; tus datos</legend>
					<div class="clear">
					<p>Todos los campos son obligatorios.</p>
					</div>
					<div class="prepend-1 span-6 ">
					
					<label for="name">Nombre:</label> <br />
					<form:input path="name" /> <br />
					<small>Su nombre.</small>
					<br/> <span class="error"><form:errors
						path="name" /></span><br />
					<br />
					
					<label for="email">Email:</label><br/>
					<form:input path="email" size="30"/> <br />
					<small>Si cambi&aacute;s el mail, tendr&aacute;s que activar la cuenta nuevamente haciendo click en el link que le enviaremos por mail.</small> 
					<br/><span class="error"><form:errors path="email" /></span><br />
					
					
					<label for="facebook">Facebook:</label> <br />
					<form:input path="facebook" /> <br />
					<small>Su cuenta de facebook.</small>
					<br/> <span class="error"><form:errors
						path="facebook" /></span><br />
					<br />
					</div>
					<div class="prepend-1 span-6 last">
				
					
					
				
					<label for="lastName">Apellido:</label> <br />
					<form:input path="lastName" /> <br />
					<small>Su apellido.</small> 
					<br/><span class="error"><form:errors
						path="lastName" /></span><br />
					<br />
				
					<label for="twitter">Twitter:</label> <br />
					<form:input path="twitter" /> <br />
					<small>Su cuenta de twitter, ejemplo: @argendata</small> 
					<br/><span class="error"><form:errors
						path="twitter" /></span><br />
					<br />
				
					
				</div>
				<div class="prepend-1 span-8 append-1 last">
					<label for="minibio">Minibio:</label> <br />
					<form:textarea path="minibio" /> <br />
					<small>Aprovech&aacute; este espacio para decir algo sobre vos.</small>
					<br/> <span class="error"><form:errors
						path="minibio" /></span><br />
					<br />
					
					</div>
					<div class="clear"><input type="submit" value="Guardar" /></div>
					</fieldset>
				</form:form>
					
				</div>
			</div>



<div style="clear: both"></div>



<c:import url="/WEB-INF/footer.jsp" /></div>


</body>
</html>
