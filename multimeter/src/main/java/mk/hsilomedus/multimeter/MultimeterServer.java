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
    System.out.println("New connection from " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
  }

  @Override
  public void onClose(WebSocket conn, int code, String reason, boolean remote) {
    conns.remove(conn);
    System.out.println("Closed connection to " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
  }

  @Override
  public void onMessage(WebSocket conn, String message) {
    // TODO IT WANTS SOMETHING!!!!

  }

  @Override
  public void onError(WebSocket conn, Exception ex) {
    conns.remove(conn);
    System.out.println("ERROR from " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
  }
  
  private ReadValues currentData = new ReadValues();
  public void setData(ReadValues data) {
    this.currentData = data;
    
  }
  
  public void broadcastLastData() {
    if (currentData == null) {
      return;
    }
    String toSend = currentData.toString();
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
    System.out.println("MultimeterServer started on port: " + server.getPort());
    
    final Serial serial = SerialFactory.createInstance();

    // create and register the serial data listener
    serial.addListener(new SerialDataListener() {
      
      private ReadValues readValues = new ReadValues();
      private int curValue = -1;
      
      @Override
      public void dataReceived(SerialDataEvent event) {
        String received = event.getData();
        String[]parts = received.split("\\s+");
        
        for (int i = 0; i < parts.length; i++) {
          if (parts[i].equals("S")) {
            //commit the stuff
            server.setData(readValues);
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
    
    //send data from time to time
    while (true) {
      try {
        Thread.sleep(10);
        server.broadcastLastData();
      } catch (Exception exc) {
        
      }
      
    }
    
  }

}
