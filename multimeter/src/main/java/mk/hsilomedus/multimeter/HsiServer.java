package mk.hsilomedus.multimeter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;


/*
 * Copyright (C) 2013 by Netcetera AG.
 * All rights reserved.
 *
 * The copyright to the computer program(s) herein is the property of Netcetera AG, Switzerland.
 * The program(s) may be used and/or copied only with the written permission of Netcetera AG or
 * in accordance with the terms and conditions stipulated in the agreement/contract under which 
 * the program(s) have been supplied.
 */

public class HsiServer extends WebSocketServer {

  public HsiServer(int port) throws UnknownHostException {
    super(new InetSocketAddress(port));
  }

  public HsiServer(InetSocketAddress address) {
    super(address);
  }

  @Override
  public void onOpen(WebSocket conn, ClientHandshake handshake) {
    MessageSenderFromTimeToTime.newInstance(conn);

  }

  @Override
  public void onClose(WebSocket conn, int code, String reason, boolean remote) {
    // TODO Auto-generated method stub

  }

  @Override
  public void onMessage(WebSocket conn, String message) {
    // TODO Auto-generated method stub

  }

  @Override
  public void onError(WebSocket conn, Exception ex) {
    // TODO Auto-generated method stub

  }
  
  

  public static void main(String[] args) throws InterruptedException, IOException {
    
    //Initialize the GPIO stuff
//    GpioController gpio = GpioFactory.getInstance();
//    
//    GpioPinDigitalInput[] pins = new GpioPinDigitalInput[10];
//    //Initialize all the pins
//    pins[0] = gpio.provisionDigitalInputPin(RaspiPin.GPIO_00,
//        "MyButton0",
//        PinPullResistance.PULL_DOWN);
//    pins[1] = gpio.provisionDigitalInputPin(RaspiPin.GPIO_01,
//        "MyButton1",
//        PinPullResistance.PULL_DOWN);
//    pins[2] = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02,
//        "MyButton2",
//        PinPullResistance.PULL_DOWN);
//    pins[3] = gpio.provisionDigitalInputPin(RaspiPin.GPIO_03,
//        "MyButton3",
//        PinPullResistance.PULL_DOWN);
//    pins[4] = gpio.provisionDigitalInputPin(RaspiPin.GPIO_04,
//        "MyButton4",
//        PinPullResistance.PULL_DOWN);
//    pins[5] = gpio.provisionDigitalInputPin(RaspiPin.GPIO_05,
//        "MyButton5",
//        PinPullResistance.PULL_DOWN);
//    pins[6] = gpio.provisionDigitalInputPin(RaspiPin.GPIO_06,
//        "MyButton6",
//        PinPullResistance.PULL_DOWN);
//    pins[7] = gpio.provisionDigitalInputPin(RaspiPin.GPIO_07,
//        "MyButton7",
//        PinPullResistance.PULL_DOWN);
//    pins[8] = gpio.provisionDigitalInputPin(RaspiPin.GPIO_08,
//        "MyButton8",
//        PinPullResistance.PULL_DOWN);
//    pins[9] = gpio.provisionDigitalInputPin(RaspiPin.GPIO_09,
//        "MyButton9",
//        PinPullResistance.PULL_DOWN);
//        
//    //initialize and start the thread
//    GPIOReader reader = new GPIOReader(gpio, pins);
//    Thread thr = new Thread(reader);
//    thr.start();
    
    WebSocketImpl.DEBUG = false;
    int port = 8887; // 843 flash policy port
    try {
      port = Integer.parseInt(args[0]);
    } catch (Exception ex) {
    }
    HsiServer s = new HsiServer(port);
    s.start();
    System.out.println("ChatServer started on port: " + s.getPort());

    BufferedReader sysin = new BufferedReader(new InputStreamReader(System.in));
    while (true) {
      String in = sysin.readLine();
//      s.sendToAll(in);
    }
  }

}
