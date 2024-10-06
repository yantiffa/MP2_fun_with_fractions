package edu.grinnell.csc207.util;

/**
 * A simple implementation of BFCalculator.
 * This is created for CSC207 fall 2024 section
 * @author Tiffany Yan
 */
public class BFCalculator {
  // +--------+-------------------------------------------------------
  // | Fields |
  // +--------+

  /** The last value stored.*/
  private BigFraction lastnum;

  // +--------------+-------------------------------------------------
  // | Constructors |
  // +--------------+
  /**
   * Intialize a Calculator.
   */
  public BFCalculator() {
    this.lastnum = new BigFraction(0, 1);
  } // BFCalculator()

  // +---------+------------------------------------------------------
  // | Methods |
  // +---------+

  /**
   *Get the last bigFraction stored in the calculator.
   *@return BigFraction.
   */
  public BigFraction get() {
    return this.lastnum;
  } // get()

   /**
   *add val to calculator.
   *@param val
   */
  public void add(BigFraction val) {
    this.lastnum = this.lastnum.add(val).simplify();
  } // add()

  /**
   *Subtracts val from the last computed value.
   *@param val
   */
  public void subtract(BigFraction val) {
    this.lastnum = this.lastnum.subtract(val).simplify();
  } // subtract()

  /**
   * Multiplies the last computed value by val.
   * @param val
   */
  public void multiply(BigFraction val) {
    this.lastnum = this.lastnum.multiply(val).simplify();
  } // multiply()

  /**
   * Divides the last computed value by val.
   * @param val
   */
  public void divide(BigFraction val) {
    this.lastnum = this.lastnum.divide(val).simplify();
  } // divide()

  /**
   *Resets the last computed value to 0.
   */
  public void clear() {
    this.lastnum = new BigFraction(0, 1);
  } //Clear()
} //BFCalculator



