package genericutilities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

public class JavaUtility {

	public String getRandomNumber(int Number) {
		String randomNumber=RandomStringUtils.randomNumeric(Number);
		return randomNumber;
	}
	public String getRandomString(int Number) {
		String randomString=RandomStringUtils.randomAlphabetic(Number);
		return randomString;
	}
	public String getSystemDate() {
		Date d = new Date();
		String date=d.toString();
		return date;
	}
	public String getSystemFormattedDate() {
		SimpleDateFormat sd= new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		Date d = new Date();
		String formattedDate=sd.format(d);
	//	formattedDate.replaceAll(formattedDate, formattedDate);
		return formattedDate;
	}
}
