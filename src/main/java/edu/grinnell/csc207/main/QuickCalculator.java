package edu.grinnell.csc207.main;


import java.io.PrintWriter;
import java.math.BigInteger;

import edu.grinnell.csc207.util.BFCalculator;
import edu.grinnell.csc207.util.BFRegisterSet;
import edu.grinnell.csc207.util.BigFraction;


/**
 * a main method that will take the expressions from the command line (rather than user input) and
 * then print out the results. This is created for CSC207 fall 2024 section.
 *
 * @author Tiffany Yan
 */
public class QuickCalculator {
  /**
   * @param args
   */
  public static void main(String[] args) {
    // If QUIT is typed:
    if (args[0].equals("QUIT") && args.length == 0) {
      return;
    } // if


    PrintWriter pen = new PrintWriter(System.out, true);
    BFCalculator calculator = new BFCalculator(); // New Calculator setup
    BFRegisterSet registerset = new BFRegisterSet(); // new RegisterSet setup


    if (args[0].equals("")) { // checks if empty string is typed
      System.err.println("FAILED [Invalid expression]"); // give error message
      pen.close();
      return;
    } // if

    for (String str : args) { // loop through the command line argument
      BigFraction previous = calculator.get().simplify(); // keep record of the value before
      if (str.startsWith("STORE")) { // check if asked to store a value
        String[] parts = str.split(" ");
        if (parts.length == 2 && Character.isLetter(parts[1].charAt(0))) { // check valid command
          registerset.store(parts[1].charAt(0), previous); // store the value
          pen.println("STORED " + parts[1].charAt(0) + " -> STORED");
          continue;
        } else {
          System.err.println("FAILED [Invalid expression]");
          continue;
        } // if
      } // if
      calculator.clear(); // clear the stored value


      // declare variables to keep record of operators, operand, and current value
      // also record expectnum and validExpression for error + edge cases
      String operator = null;
      BigFraction current = null;
      boolean expectnum = true;
      boolean validExpression = true;
      String[] inpuarr = str.split(" ");


      for (String inp : inpuarr) {
        if (inp.equals("+") || inp.equals("-") || inp.equals("/") || inp.equals("*")) {
          if (expectnum) { // error checks: expecting operand but gives operator
            validExpression = false;
            break;
          } // if
          operator = inp; // store operators
          expectnum = true; // now expects an operand/number
        } else { // error checks for any other cases
          if (!expectnum) {
            validExpression = false;
            break;
          } // if


          BigFraction operand;
          if (inp.length() == 1 && Character.isLetter(inp.charAt(0))) { // check for single char
            operand = registerset.get(inp.charAt(0));
            if (operand == null) { // error check
              validExpression = false;
              break;
            } // if
          } else {
            operand = new BigFraction(inp);
          } // if
          if (current == null) {
            current = operand;
          } else if (operator != null) { // if no operator, store the first value
            if (operator.equals("+")) { // then check for operators and update value
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


      if (!validExpression || expectnum || current == null) { // check for valid statements
        System.err.println("FAILED [Invalid expression]");
      } else {
        calculator.clear(); // clear the calcualtor from last round
        calculator.add(current);
        if (inpuarr.length == 1) { // for single character str, print out equation
          pen.printf(str + " -> ");
        } else {
          pen.printf(str + " -> ");
        } // if
        BigFraction result = calculator.get().simplify();
        if (result.denominator().equals(BigInteger.ONE)) { // print out results
          pen.println(result.numerator());
        } else {
          pen.println(result);
        } // if
      } // if
    } // if
    pen.close();
  } // main
} // Calculator
