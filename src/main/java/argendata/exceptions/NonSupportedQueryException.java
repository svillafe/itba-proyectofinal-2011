package argendata.exceptions;

public class NonSupportedQueryException extends Exception {
	
	public NonSupportedQueryException(){
		super();
	}
	
	public NonSupportedQueryException(String message){
		super(message);
	}
	
	public NonSupportedQueryException(Throwable t){
		super(t);
	}
	
	public NonSupportedQueryException(String message, Throwable t){
		super(message,t);
	}
}
