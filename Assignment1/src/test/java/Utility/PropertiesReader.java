package Utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class PropertiesReader {
	private static PropertiesReader fileReader;
	private HashMap<String, Properties> propertiesMap = new HashMap<>();

	private PropertiesReader() {

	}
	
	/**
	 * @param sPropFileName
	 * @return
	 * @throws IOException
	 */
	public Properties getPropFile(String sPropFileName) throws IOException {
		Properties prop;
		BufferedReader reader;

		if (propertiesMap.get(sPropFileName) == null) {
			prop = new Properties();
			reader = new BufferedReader(new FileReader(sPropFileName));
			prop.load(reader);
			propertiesMap.put(sPropFileName, prop);
		}
		return propertiesMap.get(sPropFileName);
	}

	/**
	 * @return
	 */
	public static PropertiesReader getInstance() {
		if (fileReader != null) {
			return fileReader;
		}
		synchronized (PropertiesReader.class) {
			if (fileReader == null) {
				fileReader = new PropertiesReader();
			}
			return fileReader;
		}
	}
}
