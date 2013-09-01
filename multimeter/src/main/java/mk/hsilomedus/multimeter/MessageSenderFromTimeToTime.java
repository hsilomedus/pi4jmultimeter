package mk.hsilomedus.multimeter;
import org.java_websocket.WebSocket;


/*
 * Copyright (C) 2013 by Netcetera AG.
 * All rights reserved.
 *
 * The copyright to the computer program(s) herein is the property of Netcetera AG, Switzerland.
 * The program(s) may be used and/or copied only with the written permission of Netcetera AG or
 * in accordance with the terms and conditions stipulated in the agreement/contract under which 
 * the program(s) have been supplied.
 */

public class MessageSenderFromTimeToTime implements Runnable {
  private WebSocket conn;
  private MessageSenderFromTimeToTime(WebSocket conn) {
    this.conn = conn;
  }
  
  public void run() {
    while (true) {
//      conn.send("" + GPIOReader.getValue());
      if(conn.isClosed()) {
        break;
      }
      
      conn.send(""+Math.random()*100);
      try {
        Thread.sleep(20);
      } catch (InterruptedException e) {
        System.out.println("Exception occured!");
      }
    }
    
  }
  
  public static MessageSenderFromTimeToTime newInstance(WebSocket conn) {
    MessageSenderFromTimeToTime fromTimeToTime = new MessageSenderFromTimeToTime(conn);
    Thread thr = new Thread(fromTimeToTime);
    thr.start();
    return fromTimeToTime;
  }

}
