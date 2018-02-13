package tp.pr3.util;

import java.io.*;

public class MyStringUtils {
	public static final int MARGIN_SIZE = 4;
	
	public static String repeat (String elmnt, int numOfTimes) {
		String result = "";
		for (int i = 0; i < numOfTimes; ++i){
			result += elmnt;
		}
		return result;
	}
	
	public static String centre (String text, int len) {
		String out = String.format(" %" + len + "s %s %" + len + "s", " ", text, " ");
		float mid = (out.length()/2);
		float start = mid - (len/2);
		float end = start + len;
		
		return out.substring((int)start, (int)end);
	}
	
	public static boolean validFileName (String filename) {
		File file = new File(filename);
		
		if(file.exists()) {
			return canWriteLocal(file);
		} else {
			try {
				file.createNewFile();
				file.delete();
				return true;
			} catch (Exception e) {
				return false;
			}
		}
	}
	
	
	
	public static boolean canWriteLocal (File file) {
		if (!file.canWrite()){
			return false;
		}
		
		try{
			new FileOutputStream(file, true).close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
}
