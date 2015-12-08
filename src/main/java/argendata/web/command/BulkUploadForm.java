package argendata.web.command;

import org.springframework.web.multipart.MultipartFile;

public class BulkUploadForm {
		
		private MultipartFile file;
	
		
		public boolean hayArchivo(){
			
			if(file.getContentType()==null)
				return false;
			if(file.getContentType().startsWith("text/plain") || file.getContentType().startsWith("text/csv") || file.getContentType().startsWith("application/vnd.ms-excel") ){
				String extension =  file.getOriginalFilename().split("\\.")[1];
				if(extension.equals("txt")||extension.equals("csv")){
					return true;
				}
			}
			
			return false;
			
		}
		
		public MultipartFile getFile() {
			return file;
		}

		public void setFile(MultipartFile file) {
			this.file = file;
		}


}
