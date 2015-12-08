<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="sectionTitle span-7" style="text-align:justify;">
	<h2>Opciones de usuario</h2>
	<div style="margin:15px;">
		<ul class="linkList">
			<li><h3><a href="changePassword" >Cambiar contrase&ntilde;a</a></h3></li>
			<li><h3><a href="editProfile" >Editar mi perfil</a></h3></li>
			<li><h3><a href="listMyApps" >Ver mis apps</a></h3></li>
			<li><h3><a href="profile?username=<c:out value="${user.username}" />" >Ver mi perfil</a></h3></li>
		</ul>
	</div>
</div>