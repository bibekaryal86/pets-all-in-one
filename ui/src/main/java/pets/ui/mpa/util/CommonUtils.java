package pets.ui.mpa.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CommonUtils {

	public static String toUppercase(String inputString) {
		return inputString == null ? null : inputString.toUpperCase();
	}
	
	public static String removeApostropheForJavascript(String inputString) {
		return inputString == null ? null : inputString.replace("'", "");
	}
	
	public static BigDecimal formatAmountBalance(BigDecimal inputBigDecimal) {
		return inputBigDecimal == null ? null : inputBigDecimal.setScale(2, RoundingMode.HALF_UP);
	}
}
