// Assignment #: Arizona State University CSE205 #11
//         Name: Your name
//    StudentID: Your id
//      Lecture: Your lecture
//  Description: This is a utility class that provide a static method that
//				 takes an infix string, checked and determine if parentheses
//				 are matching, if matching, returns a postfix string.

import java.util.Stack;

public class InfixToPostfixConverter
{
	//**********************************************************************
	//The precedence method determines the precedence between two operators.
	//If the first operator is of higher or equal precedence than the second
	//operator, it returns true, otherwise it returns false.
	//***********************************************************************
	public static boolean precedence(char first, char second)
	{
		if((first == '-' || first == '+') && (second == '*' || second == '/')) { //returns false because * and / have precedence over + and -
			return false;
		}

		else if((first == '*' || first == '/') && (second == '*' || second == '/')) { //returns false because * and / don't have precedence over each other
			return false;
		}

		else if((first == '+' || first == '-') && (second == '+' || second == '-')) { // returns false because + and - don't have precedence over each other
			return false;
		}

		else { //the operators will have precedence in all other cases
			return true;
		}

	}

	//*************************************************************************
	//The static convertToPostfix method will convert the infixString
	//into the corresponding postfix string. Check the algorithm on
	//assignment #11's description page. Mark each case clearly inside the code
	//*************************************************************************
	public static String convertToPostfix(String infixString)
	{
		//initialize the resulting postfix string
		String postfixString = "The Postfix Expression is: ";

		//initialize the stack
		Stack<Character> stack1 = new Stack<Character>();

		//Obtain the character at index i in the string
		for (int i=0; i < infixString.length(); i++)
		{
			char currentChar = infixString.charAt(i);

			//Case A:
			if(currentChar != '(' && currentChar != '+' && currentChar != '-' && currentChar != '*' && currentChar != '/' && currentChar != ')') { // checks if the current character is an operand
				postfixString += currentChar;
			}

			//Case B:
			if(currentChar == '(') {
				stack1.push(currentChar);
			}

			//Case C:
			if(currentChar == '+' || currentChar == '-' || currentChar == '*' || currentChar == '/') { // checks if the current character is an operator
				if(stack1.isEmpty()) {
					stack1.push(currentChar);
				}


				//Case D:
				else {
					if(precedence(currentChar, stack1.peek())) { //If the current character has precedence over the top of the stack, it is added to the stack
						stack1.push(currentChar);
					}

					else {
						if(stack1.isEmpty()) {
							postfixString += currentChar;
						}
						else {
							if(stack1.peek() == '(') { //will stay on this character until a ( is encountered or the stack is emptied
								stack1.pop();
								i--;
							}
							else {
								postfixString += stack1.pop();
								i--;
							}
						}
					}
				}

			}

			//Case E:
			if(currentChar == ')') {
				if(!stack1.contains('(')) {
					postfixString = "No matching open parenthesis error";
					infixString = ""; //stops going through loop
					stack1.clear(); //doesn't add anything else to postfixString
				}

				else if(stack1.lastIndexOf('(') == 0 && stack1.size() == 1) { //if ( is the only object in the stack
						stack1.pop();
				
				}
				
				else if(stack1.lastIndexOf('(') == 0) { //special case made to make sure last parenthesis is cleared
					for(int l = 0; l < stack1.size(); l++) {
						postfixString += stack1.pop();
					}
					if(!stack1.isEmpty()) {
						stack1.pop();
					}
				}

				else {
					for(int k = 0; k <= stack1.size(); k++) {

						if(stack1.peek() == '(') {
							stack1.pop();
							k = stack1.size();
						}

						else {
							postfixString += stack1.pop();
						}
					}
				}
			}

		} //end of for loop


		//Case F:
		if(!stack1.isEmpty()) {
			if(stack1.contains('(')) {
				postfixString = "No matching close parenthesis error";
				stack1.clear(); //nothing else is added to postfixString
				infixString = "";
			}

			else {
				for(int j = 0; j <= stack1.size(); j++) { //add the rest since no parentheses are present
					postfixString += stack1.pop();
				}
			}

		}
		return postfixString;
	}//end of convertToPostfix method
}//end of the InfixToPostfixConverter class