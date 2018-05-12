package es.deusto.bspq18.e6.DeustoBox.Server.utils;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class Error_log implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger;

	
	
	
	public Error_log() {
		this.logger = Logger.getLogger("DeustoBox Logger");
	}




	public Logger getLogger() {
		return logger;
	}
	
	
	

}
