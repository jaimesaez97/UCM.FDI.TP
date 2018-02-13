package tp.pr2.util;

public class MyMathsUtil {
	
	// convert from long to int since wewill not need to use large numbers
	public static int nexFib (int previous){
		double phi = (1 + Math.sqrt(5)) / 2;
		return (int) Math.round(phi * previous);
	}
}
