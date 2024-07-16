import java.util.Scanner;

public class Calculator {
		
	public static final String VERSION = "1.0.0";
	
	public void printVersion() {
	    System.out.println("Calculator Version: " + VERSION);
	    System.out.println("**********");
	}
		
	public static void main(String[] args) {
				
	    Calculator calculator = new Calculator();
	    calculator.printVersion();
		
		Scanner input = new Scanner(System.in);
		
		boolean repeat = true;
		
		do {
				
		System.out.println("Enter the first number:");
		System.out.println("Enter the second number:");
		
		double firstNumber = input.nextDouble();
		double secondNumber = input.nextDouble();
		
		// To choose the operations to perform :
		
//		System.out.println("Show the Menu: Display Calculator Options.");
//		System.out.println();
		
	    boolean validOperation = false;
	    int choice = 0;
	    
	    while (!validOperation) {
	    	System.out.println("Select an operation from below Menu :");
			System.out.println("1. Add");
			System.out.println("2. Subtract");
			System.out.println("3. Multiply");
			System.out.println("4. Divide");
			System.out.println("5. Exit");
			
			choice = input.nextInt();
			break;
	    }
	    
	    
//		Scanner input1 = new Scanner(System.in);
//			
//		System.out.println("Select an operation from below Menu :");
//		System.out.println("1. Add");
//		System.out.println("2. Subtract");
//		System.out.println("3. Multiply");
//		System.out.println("4. Divide");
//		System.out.println("5. Exit");
//				
//		int choice = input1.nextInt();
		
//		if (choice == 1) {
//		    System.out.println("You chose to add.");
//		} else if (choice == 2) {
//		    System.out.println("You chose to subtract.");
//		} else if (choice == 3) {
//		    System.out.println("You chose to multiply.");
//		} else if (choice == 4) {
//		    System.out.println("You chose to divide.");
//		} else if (choice == 5) {
//		    System.out.println("Exiting...");
//		    input.close();
//		    System.exit(0);
//		} else {
//		    System.out.println("Invalid choice.");
//		    input.close();
//		    System.exit(0);
//		}
		
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
			
		System.out.println("**********");
		
		Scanner times = new Scanner(System.in);
		System.out.println("How many times do you want to repeat this operation : ");
		int n = times.nextInt();
					
		double Add = firstNumber + secondNumber;
		double Diff = firstNumber - secondNumber;
		double Product = firstNumber * secondNumber;
		double Div = firstNumber / secondNumber;
		
		// for loop to print the result for specified no. of times
		
		for (int i=1; i<=n; i++) {   
		
		if (choice == 1) {
			System.out.println("The Sum of given numbers is " + Add);
		} else if (choice == 2) {
			System.out.println("The Subtraction of given numbers is " + Diff);
		}else if (choice == 3) {
			System.out.println("The Product/Multiplication of given numbers is " + Product);
		}else if (choice == 4) {
			System.out.println("The Quotient of given numbers is " + Div);
		}
		
		System.out.println("**********");
		}
		
		System.out.println("Do you want to perform another calculation ? - (Yes/No)");
		
		String userResponse = input.next();
		
		if (userResponse.equalsIgnoreCase("No")) {
			repeat = false;
		} 
		
		
	} while(repeat);
		
	  input.close();
}
}
