/*
 * Copyright (C) 2013 by Netcetera AG.
 * All rights reserved.
 *
 * The copyright to the computer program(s) herein is the property of Netcetera AG, Switzerland.
 * The program(s) may be used and/or copied only with the written permission of Netcetera AG or
 * in accordance with the terms and conditions stipulated in the agreement/contract under which 
 * the program(s) have been supplied.
 */
package mk.hsilomedus.multimeter;


public class ReadValues {
  public int value;
  public int[] bands = new int[20];
  
  @Override
  public String toString() {
    StringBuilder bld = new StringBuilder();
    bld.append("{\"v\":");
    bld.append(value);
    bld.append(",\"d\":[");
    for (int i = 0; i < bands.length; i++) {
      if (i > 0) {
        bld.append(',');
      }
      bld.append(bands[i]);
    }
    bld.append("]}");
    return super.toString();
  }
}
