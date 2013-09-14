package mk.hsilomedus.multimeter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Date;

import org.java_websocket.WebSocket;
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
    
//    WebSocketImpl.DEBUG = false;
//    int port = 8887; // 843 flash policy port
//    try {
//      port = Integer.parseInt(args[0]);
//    } catch (Exception ex) {
//    }
//    MultimeterServer s = new MultimeterServer(port);
//    s.start();
//    System.out.println("ChatServer started on port: " + s.getPort());
//
//    BufferedReader sysin = new BufferedReader(new InputStreamReader(System.in));
//    while (true) {
//      String in = sysin.readLine();
////      s.sendToAll(in);
//    }
    
    final Serial serial = SerialFactory.createInstance();

    // create and register the serial data listener
    serial.addListener(new SerialDataListener() {
        @Override
        public void dataReceived(SerialDataEvent event) {
            // print out the data received to the console
            System.out.println(System.currentTimeMillis() + " " + event.getData());
        }            
    });
            
    try {
        // open the default serial port provided on the GPIO header
        serial.open(Serial.DEFAULT_COM_PORT, 115200);
        
        // continuous loop to keep the program running until the user terminates the program
        for (;;) {
            try {
              //serial.write(""+ ((int)(Math.random() * 200)));
              //Thread.sleep(100);
                // write a formatted string to the serial transmit buffer
                //serial.write("CURRENT TIME: %s", new Date().toString());

                // write a individual bytes to the serial transmit buffer
                //serial.write((byte) 13);
                //serial.write((byte) 10);

                // write a simple string to the serial transmit buffer
                //serial.write("Second Line");

                // write a individual characters to the serial transmit buffer
                //serial.write('\r');
                //serial.write('\n');

                // write a string terminating with CR+LF to the serial transmit buffer
                //serial.writeln("Third Line");
            }
            catch(IllegalStateException ex){
                ex.printStackTrace();                    
            }
            
            // wait 1 second before continuing
            Thread.sleep(1000);
        }
        
    }
    catch(SerialPortException ex) {
        System.out.println(" ==>> SERIAL SETUP FAILED : " + ex.getMessage());
        return;
    }
    
    
  }

}
