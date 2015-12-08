package argendata.exceptions;

public class IndexStoreException extends Exception {
	
	public IndexStoreException(){
		super();
	}
	
	public IndexStoreException(String message){
		super(message);
	}
	
	public IndexStoreException(Throwable t){
		super(t);
	}
	
	public IndexStoreException(String message, Throwable t){
		super(message,t);
	}
}
