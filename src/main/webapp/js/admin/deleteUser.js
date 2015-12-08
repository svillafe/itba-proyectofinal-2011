$(document).ready(function(){
	$("#cancelar-button").click(function(){
		window.location="<c:out value="${mainUrl}"/>/admin/listUsers";
		return false;
	});
});
