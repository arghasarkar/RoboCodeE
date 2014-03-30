package MyBotEclipse;

public class mathFunctions {

	public mathFunctions() {
		// TODO Auto-generated constructor stub
	}
	
	public static double mySin(double degrees) {
		double radians = Math.toRadians(degrees);
		return Math.sin(radians);
	}
	
	public static double myCos(double degrees) {
		double radians = Math.toRadians(degrees);
		return Math.cos(radians);
	}

	public static  double my_a_Sin(double input) {
		double degrees = Math.toDegrees(Math.asin(input));
		return degrees;
	}
	
	public static double my_a_Cos(double input) {
		double degrees = Math.toDegrees(Math.acos(input));
		return degrees;
	}
	
	public static void main(String[] args) {
		double val = -500;
		System.out.println("Sin of " + val + " is " + myCos(val) + " the asin is " + my_a_Cos(myCos(val)));
		
	}
	
}
