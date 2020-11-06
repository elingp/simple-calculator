package com.studentacademy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

public class Main {

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    Calculator calculator = new Calculator();

    while (true) {
      try {
        String[] commands = in.nextLine().split(" ");
        String command = commands[0];
        try {
          int number = Integer.parseInt(commands[1]);
          System.out.printf("%.1f\n", calculator.evaluate(command, number));
          // If parsing fails because there is no command with index > 0,
          // evaluate only the first command
        } catch (ArrayIndexOutOfBoundsException e) {
          calculator.evaluate(command);
          // If parsing fails because of other reasons (e.g. the number is real),
          // evaluate as a random input
        } catch (Exception e) {
          calculator.evaluate(e.getMessage());
        }
        // If the input fails, evaluate as a random input
      } catch (Exception e) {
        calculator.evaluate(e.getMessage());
      }
    }
  }
}

class Calculator {
  private double value;

  public double add(int adder) {
    this.value += adder;
    return this.value;
  }

  public double subtract(int subtractor) {
    this.value -= subtractor;
    return this.value;
  }

  public double multiply(int multiplier) {
    this.value *= multiplier;
    return this.value;
  }

  public double divide(int divider) {
    if (divider == 0) {
      this.value = 0.0;
    } else {
      this.value /= divider;
    }
    return this.value;
  }

  public void cancel() {
    this.value = 0.0;
  }

  public void exit() {
    System.exit(0);
  }

  private final Map<String, Function<Integer, Double>> MATH_OPERATIONS =
      new HashMap<>() {
        {
          put("add", (Integer adder) -> add(adder));
          put("subtract", (Integer subtractor) -> subtract(subtractor));
          put("multiply", (Integer multiplier) -> multiply(multiplier));
          put("divide", (Integer divider) -> divide(divider));
        }
      };

  private final Map<String, Runnable> OTHER_OPERATIONS =
      new HashMap<>() {
        {
          put("cancel", () -> cancel());
          put("exit", () -> exit());
        }
      };

  public double evaluate(String operationName, int input) {
    if (isMathOperation(operationName)) {
      return MATH_OPERATIONS.get(operationName).apply(input);
    }
    return this.value;
  }

  public void evaluate(String operationName) {
    if (isOtherOperation(operationName)) {
      OTHER_OPERATIONS.get(operationName).run();
    }
    System.out.printf("%.1f\n", this.value);
  }

  public boolean isMathOperation(String commandName) {
    return MATH_OPERATIONS.containsKey(commandName);
  }

  public boolean isOtherOperation(String commandName) {
    return OTHER_OPERATIONS.containsKey(commandName);
  }

  @Override
  public String toString() {
    return String.format("%.1f", this.value);
  }
}
