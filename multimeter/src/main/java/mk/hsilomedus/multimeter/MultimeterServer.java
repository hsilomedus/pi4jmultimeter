package mk.hsilomedus.multimeter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialDataEvent;
import com.pi4j.io.serial.SerialDataListener;
import com.pi4j.io.serial.SerialFactory;
import com.pi4j.io.serial.SerialPortException;


/*
 * Copyright (C) 2013 by Netcetera AG.
 * All rights reserved.
 *
 * The copyright to the computer program(s) herein is the property of Netcetera AG, Switzerland.
 * The program(s) may be used and/or copied only with the written permission of Netcetera AG or
 * in accordance with the terms and conditions stipulated in the agreement/contract under which 
 * the program(s) have been supplied.
 */

public class MultimeterServer extends WebSocketServer {

  public MultimeterServer(int port) throws UnknownHostException {
    super(new InetSocketAddress(port));
  }

  public MultimeterServer(InetSocketAddress address) {
    super(address);
  }
  
  Set<WebSocket> conns = new HashSet<>(); 

  @Override
  public void onOpen(WebSocket conn, ClientHandshake handshake) {
    conns.add(conn);

  }

  @Override
  public void onClose(WebSocket conn, int code, String reason, boolean remote) {
    conns.remove(conn);

  }

  @Override
  public void onMessage(WebSocket conn, String message) {
    // TODO IT WANTS SOMETHING!!!!

  }

  @Override
  public void onError(WebSocket conn, Exception ex) {
    conns.remove(conn);

  }
  
  public void broadcastData(ReadValues data) {
//    System.out.println(System.currentTimeMillis());
    String toSend = data.toString();
    for (WebSocket conn : conns) {
      try {
        conn.send(toSend);
      } catch (Exception exc) {
        
      }
    }
  }
  
  

  public static void main(String[] args) throws InterruptedException, IOException {
        
    WebSocketImpl.DEBUG = false;
    int port = 8887; 
    
    final MultimeterServer server = new MultimeterServer(port);
    server.start();
    System.out.println("ChatServer started on port: " + server.getPort());
    
    final Serial serial = SerialFactory.createInstance();

    // create and register the serial data listener
    serial.addListener(new SerialDataListener() {
      
      private ReadValues readValues = new ReadValues();
      private int curValue = -1;
      
      @Override
      public void dataReceived(SerialDataEvent event) {
        // print out the data received to the console
        String[]parts = event.getData().split("\\s+");
        
        for (int i = 0; i < parts.length; i++) {
          if (parts[i].equals("S")) {
            //commit the stuff
            server.broadcastData(readValues);
            readValues = new ReadValues(); //TODO: see if necessary
            curValue = -1;
          } else {
            int num = 0;
            try {
              num = Integer.parseInt(parts[i], 16);
            } catch (Exception exc) { }
            if (curValue == -1) {
              readValues.value = num;
            } else if (curValue < 20) {
              readValues.bands[curValue] = num;
            }
            curValue++;
          }
        }
      }            
    });
            
    try {
        // open the default serial port provided on the GPIO header
        serial.open(Serial.DEFAULT_COM_PORT, 115200);
                
    } catch (SerialPortException ex) {
        System.out.println(" ==>> SERIAL SETUP FAILED : " + ex.getMessage());
        return;
    }
    
    
  }

}
