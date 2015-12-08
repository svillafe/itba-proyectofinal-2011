<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="sectionTitle span-7" style="text-align:justify;">
	<h2>Panel de administraci&oacute;n</h2>
	<div style="margin:15px;">
		<ul class="linkList">
			<li><h3><a href="../user/changePassword" >Cambiar contrase&ntilde;a</a></h3></li>
			<li><h3><a href="../user/editProfile" >Editar mi perfil</a></h3></li>
			<li><h3><a href="../user/profile?username=<c:out value="${user.username}" />" >Ver mi perfil</a></h3></li>
			<li><h3><a href="../admin/listUsers" >Listar Usuarios</a></h3></li>
			<li><h3><a href="../admin/datasetForApproval" >Aprobaci&oacute;n de datasets</a></h3></li>
			<li><h3><a href="../admin/appsForApproval" >Aprobaci&oacute;n de apps</a></h3></li>
			<li><h3><a href="../dataset/bulkUpload">Carga Masiva</a></h3></li>
			<li><h3><a href="../dataset/exportDatasetsMenu">Exportar Datasets</a></h3></li>
		</ul>
	</div>
</div>