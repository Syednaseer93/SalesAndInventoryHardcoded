package genericutilities;

import java.io.FileInputStream;
import java.util.Properties;

public class FileUtility {

	public String getPropertyValue(String key) {
		String value="";
		try {
			Properties pro = new Properties();
			pro.load(new FileInputStream(IPathConst.PROPERTYFILEPATH));
			value=pro.getProperty(key);

		}
		catch(Exception e) {
		}
		return value;
	}
}
