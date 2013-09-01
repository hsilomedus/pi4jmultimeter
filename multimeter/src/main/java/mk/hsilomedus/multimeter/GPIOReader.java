package mk.hsilomedus.multimeter;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalInput;


/*
 * Copyright (C) 2013 by Netcetera AG.
 * All rights reserved.
 *
 * The copyright to the computer program(s) herein is the property of Netcetera AG, Switzerland.
 * The program(s) may be used and/or copied only with the written permission of Netcetera AG or
 * in accordance with the terms and conditions stipulated in the agreement/contract under which 
 * the program(s) have been supplied.
 */

public class GPIOReader implements Runnable {
  
  
  private GpioController gpio;
  private GpioPinDigitalInput[] pins;
  
  private static int value;
  public static int getValue() {
    return value;
  }
  
  
  public GPIOReader(GpioController gpio,  GpioPinDigitalInput[] pins) {
    this.gpio = gpio;
    this.pins = pins;
  }
  
  public void run() {
    while (true) {
      int number = 0;
      for (int i = 0; i < 10; i++) {
        number <<= 1;
        number += pins[i].getState().isHigh()? 1 : 0;
      }
      value = number;
      try {
        Thread.sleep(20);
      } catch (InterruptedException e) {
        System.out.println("Exception: Interrupted!");
      }
    }
  }

}
