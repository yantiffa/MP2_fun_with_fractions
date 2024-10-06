package edu.grinnell.csc207.util;

import java.math.BigInteger;

/**
 * A simple implementation of arbitrary-precision Fractions.
 * This is created for CSC207 fall 2024 section
 * @author Samuel A. Rebelsky
 * @author Tiffany Yan
 */
public class BigFraction {
  // +------------------+---------------------------------------------
  // | Design Decisions |
  // +------------------+

  /*
   * (1) Denominators are always positive. Therefore, negative fractions
   * are represented with a negative numerator. Similarly, if a fraction
   * has a negative numerator, it is negative.
   *
   * (2) Fractions are not necessarily stored in simplified form. To
   * obtain a fraction in simplified form, one must call the `simplify`
   * method.
   */

  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+

  /** The default numerator when creating fractions. */
  private static final BigInteger DEFAULT_NUMERATOR = BigInteger.valueOf(2);

  /** The default denominator when creating fractions. */
  private static final BigInteger DEFAULT_DENOMINATOR = BigInteger.valueOf(1);

  // +--------+-------------------------------------------------------
  // | Fields |
  // +--------+

  /** The numerator of the fraction. Can be positive, zero or negative. */
  private BigInteger num;

  /** The denominator of the fraction. Must be non-negative. */
  private BigInteger denom;

  // +--------------+-------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Build a new fraction with numerator num and denominator denom.
   *
   * @param numerator
   *   The numerator of the fraction.
   * @param denominator
   *   The denominator of the fraction.
   */
  public BigFraction(BigInteger numerator, BigInteger denominator) {
    BigInteger simple = numerator.gcd(denominator);
    this.num = numerator.divide(simple);
    this.denom = denominator.divide(simple);
  } // BigFraction(BigInteger, BigInteger)

  /**
   * Build a new fraction with numerator num and denominator denom.
   *
   * @param numerator
   *   The numerator of the fraction.
   * @param denominator
   *   The denominator of the fraction.
   */
  public BigFraction(int numerator, int denominator) {
    BigInteger a = BigInteger.valueOf(numerator);
    BigInteger b = BigInteger.valueOf(denominator);
    BigInteger simple = a.gcd(b);
    this.num = BigInteger.valueOf(numerator).divide(simple);
    this.denom = BigInteger.valueOf(denominator).divide(simple);
  } // BigFraction(int, int)

  /**
   * Build a new fraction by parsing a string.
   *
   * @param str
   *   The fraction in string form
   */
  public BigFraction(String str) {
    // Start by changing the fraction in the string format into a an array fo numbers
    str = str.trim(); // trim off the start and the end of space
    String[] numarr = str.split("/"); // split string based on occurance of / char.
    if (numarr.length == 2) {
      this.num = new BigInteger(numarr[0]);
      this.denom = new BigInteger(numarr[1]);
      BigInteger gcd = this.num.gcd(this.denom);
      this.num = this.num.divide(gcd);
      this.denom = this.denom.divide(gcd);
    } else if (numarr.length == 1) {
      this.num = new BigInteger(numarr[0]);
      this.denom = BigInteger.valueOf(1);
    } else {
      System.err.printf("Error: The input is not a fraction!");
    } //if
  } // BigFraction

  // +---------+------------------------------------------------------
  // | Methods |
  // +---------+

  /**
   *Convert the fraction into the simplest form.
   *
   *@return a new BigFraction that returns a new fraction.
   */
  public BigFraction simplify() {
    BigInteger simple = this.num.gcd(this.denom);
    return new BigFraction(this.num.divide(simple), this.denom.divide(simple));
  } // Simplest()

  /**
   *Check for any whole numbers.
   *
   *@return true if it is whole number, else return false.
   */
  public Boolean wholenumber() {
    return this.denom.equals(BigInteger.ONE);
  } // WholeNumber()

  /**
   *Express this fraction as a double.
   *@return the fraction approxiamted as a double.
   */
  public double doubleValue() {
    return this.num.doubleValue() / this.denom.doubleValue();
  } // doubleValue()

  /**
   * Add another faction to this fraction.
   *
   * @param addend
   *   The fraction to add.
   *
   * @return the result of the addition.
   */
  public BigFraction add(BigFraction addend) {
    BigInteger resultNumerator; // stores the Numerator after the addition
    BigInteger resultDenominator; // Stores the Denominator after the addition

    // The denominator of the result is the product of this object's
    // denominator and addend's denominator
    resultDenominator = this.denom.multiply(addend.denom);

    // The numerator is more complicated
    resultNumerator = this.num.multiply(addend.denom).add(addend.num.multiply(this.denom));
    // Return the computed value, but the computed value is not simplified to its easiest form.
    return new BigFraction(resultNumerator, resultDenominator).simplify();
  } // add(BigFraction)

  /**
   * Substract a faction to this fraction.
   *
   * @param minuses
   *   The fraction to minus from.
   *
   * @return the result of the substraction.
   */
  public BigFraction subtract(BigFraction minuses) {
    BigInteger resultNumerator; // stores the Numerator after the substraction
    BigInteger resultDenominator; // Stores the Denominator after the substraction

    // The denominator of the result is the product of this object's
    // denominator and minuses's denominator
    resultDenominator = this.denom.multiply(minuses.denom);

    // The numerator is more complicated
    resultNumerator = (this.num.multiply(minuses.denom)).subtract(minuses.num.multiply(this.denom));
    // Return the computed value, but the computed value is not simplified to its easiest form.
    return new BigFraction(resultNumerator, resultDenominator).simplify();
  } // minus(BigFraction)


  /**
   * Multiply another faction to this fraction.
   *
   *The fraction to multiple from.
   *@param multiple
   *@return the result of the mutiplication.
   */
  public BigFraction multiply(BigFraction multiple) {
    BigInteger resultNumerator; // stores the Numerator after the mutiplication
    BigInteger resultDenominator; // Stores the Denominator after the mutiplication
    resultDenominator = this.denom.multiply(multiple.denom);
    // The numerator is more complicated
    resultNumerator = this.num.multiply(multiple.num);
    // Return the computed value, but the computed value is not simplified to its easiest form.
    return new BigFraction(resultNumerator, resultDenominator).simplify();
  } // multiply(BigFraction)


  /**
   *Divide another fraction to this fraction.
   *
   *@param dividend
   *The fraction to divide from.
   *
   *@return the fraction after divided by the dividend.
   */

  public BigFraction divide(BigFraction dividend) {
    BigInteger resultNumerator = this.num.multiply(dividend.denom);
    BigInteger resultDenominator = this.denom.multiply(dividend.num);
    //make sure the denominator is always positive
    if (resultDenominator.compareTo(BigInteger.ZERO) < 0) {
      resultDenominator = resultDenominator.negate();
      resultNumerator = resultNumerator.negate();
    } //if
    return new BigFraction(resultNumerator, resultDenominator).simplify();
  } //divide


  /**
   * Get the denominator of this fraction.
   *
   * @return the denominator
   */
  public BigInteger denominator() {
    return this.denom;
  } // denominator()

  /**
   * Get the numerator of this fraction.
   *
   * @return the numerator
   */
  public BigInteger numerator() {
    return this.num;
  } // numerator()

  /**
   * Convert this fraction to a string for ease of printing.
   *
   * @return a string that represents the fraction.
   */
  public String toString() {
    // Special case: It's zero
    if (this.num.equals(BigInteger.ZERO)) {
      return "0";
    } // if it's zero

    if (this.denom.equals(BigInteger.ONE)) {
      return this.num.toString();
    } //if
    // Lump together the string represention of the numerator,
    // a slash, and the string representation of the denominator
    // return this.num.toString().concat("/").concat(this.denom.toString());
    return this.num + "/" + this.denom;
  } // toString()
} //BigFraction
