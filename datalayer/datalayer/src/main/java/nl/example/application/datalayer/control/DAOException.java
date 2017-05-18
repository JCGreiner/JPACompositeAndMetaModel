package nl.example.application.datalayer.control;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class DAOException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DAOException(String msg){
		super(msg);
	}
	
	public DAOException(String msg, Throwable t){
		super(msg,t);
	}
}
