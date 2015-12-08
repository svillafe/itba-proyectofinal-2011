<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Argendata - Listar Usuarios</title>
		<c:import url = "/WEB-INF/head.jsp" />
	</head>
	<body>
	<div class="container">
		<c:import url = "/WEB-INF/header.jsp" />

		<div id="cuerpo" class="span-24">
			<div id="breadcrumb" class= "prepend-1 span-22 append-1" >
				Administrador > Listar Usuarios
			</div>
		
     
      
       
		<div id="whatis" class="prepend-1 span-22 append-1 last append-bottom" style="margin-top:20px;">
			<h1> Usuarios </h1>
			
			<p class="notice" style="font-size:15px;padding-left:10px;">
			<c:out value="${msg}" />
			</p>
			
				<c:import url="adminMenu.jsp" /> 
				
				<div class="sectionTitle prepenend-1 span-15 last" style="text-align:justify">
					<table>
			<thead>
			<tr>
				<th> User </th>
				<th> Email </th>
				<th> Admin </th>
				<th> Activado </th>
				<th> Acciones </th>
				<th></th>
				<th></th>
			</tr>
			</thead>
			<tbody>
			    <c:forEach items="${users}" var="user">
			    	<tr>
					
						<td><c:out value="${user.username}" /> </td> 
											
						<td><c:out value="${user.email}" /> </td>
							
						<td> <c:choose>
	      					<c:when test="${user.admin==false}">
								NO
							</c:when>
							<c:otherwise>
								SI
							</c:otherwise>
							</c:choose> 
						</td>
						
						<td>
							<c:choose>
	      					<c:when test="${user.activated==false}">
								NO
							</c:when>
							<c:otherwise>
								SI
							</c:otherwise>
							</c:choose> 
						</td>
						
						<td>
							<a href="../user/profile?username=<c:out value="${user.username}" />">Perfil</a>
						</td>
						<td>
							<a href="deleteUser?id=<c:out value="${user.id}"/>">Eliminar</a>
						</td>
						
						<td>
						<c:choose>
	      					<c:when test="${user.admin==false}">
								<form action="makeAdmin" method="post"><input type="hidden" name="id" value="<c:out value="${user.id}" />"/> <input type="submit" value="Hacer admin" style="border:none;background:#9DBAC7;font-size:inherit" /></form> 
							</c:when>
							<c:otherwise>
									<form action="revertAdmin" method="post"><input type="hidden" name="id" value="<c:out value="${user.id}" />"/> <input type="submit" value="deshacer admin" style="border:none;background:#7DBAf7;font-size:inherit" /></form>
							</c:otherwise>
						</c:choose> 
						</td>
					
					</tr>
				</c:forEach>
			</tbody>
		</table>
					
				</div>
			</div>
		
  </div>
	<div style="clear:both"></div>
		
		
		
		<c:import url = "/WEB-INF/footer.jsp" />
	</div>
		
	
	</body>
</html>

