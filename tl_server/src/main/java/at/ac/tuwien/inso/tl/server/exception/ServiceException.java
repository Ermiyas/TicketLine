package at.ac.tuwien.inso.tl.server.exception;

public class ServiceException extends Exception{
	private static final long serialVersionUID = 8375241096351843000L;
	
	public ServiceException(){
		super();
	}
	
	public ServiceException(String message){
		super(message);
	}
	
	public ServiceException(Throwable throwable){
		super(throwable);
	}

}
