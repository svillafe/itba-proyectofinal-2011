	$("#deleteButton").click(function(){
		$.ajax({
			url:'../admin/deleteDataset',
			type:'POST',
			data:'qName=Dataset:<c:out value="${dto.dataset.titleId}" />',
			success:function(data){
				
				if(data=='0'){
    				window.location.href='../search/search?type=dataset&terms=';
    			}
    			else if(data=='-1'){
    				window.location.href='../bin/dataset/error';
    			}else if(data=='-2'){
    				window.location.href='../bin/static/error';
    			}
    			else{
    				var i=0;
    				var cadena="El dataset no se puede eliminar por que esta siendo apuntado por las siguientes aplicaciones: ";
    				cadena =cadena + data;
    				
    				
    				$('#msjError').html(cadena);
    			}
    			
			},
			error:function(){
				$("#login-errors").append("Ha ocurrido un error, reint&eacute;nte.");
			}
		});
		return false;
	});
