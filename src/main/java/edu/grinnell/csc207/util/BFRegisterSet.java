package edu.grinnell.csc207.util;
/**
 * A simple implementation of BFRegisterSet.
 * This is created for CSC207 fall 2024 section
 * @author Tiffany Yan
 */
public class BFRegisterSet {

  //+--------+-------------------------------------------------------
  //| Fields |
  //+--------+
  /**initialize the character of the register.*/
  private BigFraction[] registers = new BigFraction[NUM]; // Creating an array of 26 BigFractions.
  //+--------------+-------------------------------------------------
  //| Constructors |
  //+--------------+

  /**
   * Set everything in the array to 0/1.
   */
  public BFRegisterSet() {
    for (int i = 0; i < NUM; i++) {
      registers[i] = new BigFraction(0, 1);
    } //for
  } //BFRegisterSet

  // +---------+------------------------------------------------------
  // | Methods |
  // +---------+
  /**
   *stores the given value in the specified register.
   *@param register
   *@param val
   */
  public void store(char register, BigFraction val) {
    if (register < 'a' || register > 'z') {
      System.err.printf("ERROR: Invalid character!\n");
      return;
    } //if
    int index = (char) register - (char) 'a';
    registers[index] = val;
  } // store(char, BigFraction)

  /**
   * retrieves the value from the given register.
   * @return BigFraction
   * @param register
   */
  public BigFraction get(char register) {
    if (register < 'a' || register > 'z') {
      System.err.printf("ERROR: Invalid character!\n");
      return null;
    } //if
    int index = (char) register - (char) 'a';
    return registers[index];
  } //BigFraction

  /**
   * This is the definition to avoid magic number.
   */
  private static final int NUM = 26;
} //BFRegisterSet
