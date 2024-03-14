package taxify;

public class StringPadding {
	
	public static String rightPadding(String str, int size) {
		return String.format("%-" + size + "s", str);  
	}

	public static String leftPadding(String str, int size) {
		return String.format("%" + size + "s", str);  
	}

}
