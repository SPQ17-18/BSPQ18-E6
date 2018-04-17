package es.deusto.bspq18.e6.DeustoBox.Server.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeustoBoxLog {
	
private Logger logger;

public DeustoBoxLog(){
	this.logger = LoggerFactory.getLogger(DeustoBoxLog.class);
	logger.info("DeustoBox Log has been inicialized correctly");
	
}

public Logger getLogger() {
	return logger;
}

public void setLogger(Logger logger) {
	this.logger = logger;
}



}
