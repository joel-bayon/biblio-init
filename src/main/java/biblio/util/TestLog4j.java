package biblio.util;

import org.apache.log4j.Logger;

public class TestLog4j {
	static Logger logger = Logger.getLogger("biblio.util.TestLog4j");
	
	public static void main(String[] args) {
		logger.error("Khaled ... error  ... ?");

	}

}
