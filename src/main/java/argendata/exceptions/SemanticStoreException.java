package argendata.exceptions;

public class SemanticStoreException extends Exception {
	
	public SemanticStoreException(){
		super();
	}
	
	public SemanticStoreException(String message){
		super(message);
	}
	
	public SemanticStoreException(Throwable t){
		super(t);
	}
	
	public SemanticStoreException(String message, Throwable t){
		super(message,t);
	}
}
