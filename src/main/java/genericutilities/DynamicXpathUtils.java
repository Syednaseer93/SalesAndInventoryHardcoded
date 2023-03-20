package genericutilities;

public class DynamicXpathUtils {
	public static String getDynamicXpath(String xpath, String value) {
		String dynamicXpath = xpath.replace("%replaceable%", value);
		return dynamicXpath;
	}
}