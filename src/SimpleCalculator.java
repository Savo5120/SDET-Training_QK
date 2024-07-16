import java.util.Scanner;

public class SimpleCalculator {
	
//	public static final String VERSION = "1.0.0";
	
//	public void printVersion() {
//	    System.out.println("Calculator Version: " + VERSION);
//	    System.out.println("**********");
	    
//	}

	static int operand1 = 10;
	static double operand2 = 5.5;
	
	public static void main(String[] args) {

	    Calculator calculator = new Calculator();
	    calculator.printVersion();
		
//	    byte tooLarge = 150;       // Error will be there due to outside the valid range for a byte variable
	    short largeNumber = 150;   // Replaced byte with short for valid range
	    
	    byte num1 = 10;
		short num2 = 20;
		int num3 = 30;
		long num4 = 40;
		
		int sum = num1 + num2;

		int difference = num3 - num1;

		long product = num2 * num4;

		long quotient = num4 / num2;
		
		System.out.println("Sum: " + sum);

		System.out.println("Difference: " + difference);

		System.out.println("Product: " + product);

		System.out.println("Quotient: " + quotient);
		
		System.out.println("**********");
		
		//performing calculations with mixed data types below
		
		double resultAdd = operand1 + operand2;
		
		double resultSub = operand2 - operand1;
		
		System.out.println("Addition: " + resultAdd);

		System.out.println("Subtraction: " + resultSub);
		
		System.out.println("**********");
		
		double no1 = 7.5;
		double no2 = 2.0;
		
		double summation = no1 + no2;
		System.out.println("The sum of " + no1 + " and " + no2 + " is " + summation);
		
		double diff = no1 - no2;
		System.out.println("The difference between " + no1 + " and " + no2 + " is " + diff);
		
		double mult = no1 * no2;
		System.out.println("The product of " + no1 + " and " + no2 + " is " + mult);
		
		double div = no1 / no2;
		System.out.println("The quotient of " + no1 + " divided by " + no2 + " is " + div);
		
		System.out.println("**********");
		
		// Handle User Choices: Conditional Statements
		
		System.out.println("Show the Menu: Display Calculator Options.");  
		System.out.println();
		
		Scanner input1 = new Scanner(System.in);
			
		System.out.println("Select an operation from below Menu :");
		System.out.println("1. Add");
		System.out.println("2. Subtract");
		System.out.println("3. Multiply");
		System.out.println("4. Divide");
		System.out.println("5. Exit");
		
		int choice = input1.nextInt();
		
		switch (choice) {
	    case 1:
	        System.out.println("You chose to add.");
	        break;
	    case 2:
	        System.out.println("You chose to subtract.");
	        break;
	    case 3:
	        System.out.println("You chose to multiply.");
	        break;
	    case 4:
	        System.out.println("You chose to divide.");
	        break;
	    case 5:
	        System.out.println("Exiting...");
	        break;
	    default:
	        System.out.println("Invalid choice.");
	        break;
	}
	}

}
