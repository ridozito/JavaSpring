package inventory.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static String dateToString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return sdf.format(date);
	}
}
