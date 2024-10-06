package edu.grinnell.csc207.main;

import java.io.PrintWriter;
import java.util.Scanner;

import edu.grinnell.csc207.util.BFCalculator;
import edu.grinnell.csc207.util.BFRegisterSet;
import edu.grinnell.csc207.util.BigFraction;

/**
 * A main method that will repeatedly read a line the user types.
 * Use a BFCalculator to compute the result, and print the result for the user.
 * This type of interaction is often called a “REPL”, for “Read-Eval-Print loop.”
 *
 * This is created for CSC207 fall 2024 section
 * @author Tiffany Yan
 */
public class InteractiveCalculator {
  /**
   * @param args
   */
  public static void main(String[] args) {
    PrintWriter pen = new PrintWriter(System.out, true); //declare variables to store values
    Scanner scan = new Scanner(System.in);
    String input = "";
    BFCalculator calculator = new BFCalculator(); //setup calculator
    BFRegisterSet registerset = new BFRegisterSet(); //set up registerset
    while (true) { //while QUIT is not called
      BigFraction previous = calculator.get().simplify(); //store previous results
      input = scan.nextLine().trim(); //scan for user input

      if (input.equals("QUIT")) { //break if QUIT typed, exit the program
        break;
      } // if

      if (input.startsWith("STORE")) { //check for the start of STORE
        String[] parts = input.split(" "); // split the array
        if (parts.length == 2 && parts[1].length() == 1 && Character.isLetter(parts[1].charAt(0))) {
          registerset.store(parts[1].charAt(0), previous); //store the value to the register
          continue;
        } else {
          pen.println("*** ERROR [STORE command received invalid register] ***"); //gives error msg
          continue;
        } // if
      } // if

      calculator.clear(); //clear calculator

      String[] inputarr = input.split(" "); //declare variables to store info
      String operator = null;
      BigFraction current = null;
      boolean expectnum = true;
      boolean validExpression = true;

      pen.printf("> ");
      for (String str : inputarr) { // Loop through user input
        if (str.equals("+") || str.equals("-") || str.equals("/") || str.equals("*")) {
          if (expectnum) {
            validExpression = false; //invalid expression!
            break;
          } // if
          operator = str; //store the operator
          expectnum = true;
        } else {
          if (!expectnum) {
            validExpression = false; //invalid expression!
            break;
          } // if
          BigFraction operand;
          if (str.length() == 1 && Character.isLetter(str.charAt(0))) { //check for single char
            operand = registerset.get(str.charAt(0));
            if (operand == null) {
              validExpression = false; //Invalid expression
              break;
            } // if
          } else {
            operand = new BigFraction(str);
          } // if

          if (current == null) {
            current = operand;
          } else if (operator != null) {
            if (operator.equals("+")) { // do operation based on operators
              current = current.add(operand);
            } else if (operator.equals("-")) {
              current = current.subtract(operand);
            } else if (operator.equals("/")) {
              current = current.divide(operand);
            } else {
              current = current.multiply(operand);
            } // if
          } // if
          expectnum = false;
        } // if
      } // for

      if (!validExpression || expectnum || current == null) { //check for error
        pen.println("*** ERROR [Invalid expression] ***");
      } else { //rpint out result
        calculator.add(current);
        if (calculator.get().simplify().wholenumber()) {
          pen.println(calculator.get().simplify().numerator());
        } else {
          pen.println(calculator.get().simplify());
        } // if
      } // if
    } // while

    scan.close();
    pen.close();
  } // main
} // InteractiveCalculator



