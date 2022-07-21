package br.com.jetro.negocio;

public class NegocioException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6952954265725382565L;
	
	public NegocioException(String msg) {
		super(msg);
	}
	
	public NegocioException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
