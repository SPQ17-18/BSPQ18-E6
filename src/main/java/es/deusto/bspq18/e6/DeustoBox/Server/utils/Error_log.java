package es.deusto.bspq18.e6.DeustoBox.Server.utils;

import org.apache.log4j.Logger;

public class Error_log {
	
	private Logger logger;

	
	
	
	public Error_log() {
		this.logger = Logger.getLogger("DeustoBox Logger");
	}




	public Logger getLogger() {
		return logger;
	}
	
	
	

}
