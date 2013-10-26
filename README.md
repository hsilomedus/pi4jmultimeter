pi4jmultimeter
==============

A basic voltage meter, waveline inspector, spectrum analyzer and resistance meter.

Hardware stack: Arduino mega 2560, integration board, Raspberry Pi Model B rev.2

Software stack: Arduino script, Java8 server with pi4j and JavaWebSocket, lighttpd server, d3js client browser application.

Instructions:
=============

Hardware:
---------

Required:
- Raspberry Pi Model B. Rev 2
- Arduino (can be done with Uno, here it's done with Mega 2560)
- Wi-Fi dongle capable to work in Access Point mode (in the example, Tenda Wireless N Pico USB Adapter 150Mbps W311MI is used)
- Adapter board (proto shield)
- Resistors (as in scheme, connectors, wires)

The scheme can be found at images/Scheme.png
Sample implementation images can be found in the same folder.

Hardware specific stuff:
- the serial communication needs a voltage divider. The arduino operates at 5V logic, while the Raspberry Pi at 3.3V.
- in the example, Serial1 interface of the arduino is used. Be aware if you make any changes.
- The AC voltage measurement is improvised to handle negative voltages as well. This is done by adding DC component via resistors and it's tuned to work with an iPhone connected (for testing purposes). A real implementation should add a DC component with a op-amp circuit.
- The DC input pin must be pulled down with a resistor.
- The resistor logic is done with precise measurement of the connected fixed resistor. If the results are not correct, measure the resistor and change the calculation in the source code.

Software:
---------

Arduino:
- the source script can be found at src/main/arduino
- flash the code on the arduino device. If you use a different device, or another Serial interface, change the source code accordingly.

Raspberry pi:
- Flash an SD card with the Raspbian Wheezy.
- Download and install Java8 SE Embedded. (can be found at the oracle web page)
- Make java and javac available to the path (edit .bashrc and extend PATH there)
- git should be present already. If not, install with apt-get
- clone this repository somewhere
- build the solution (invoke build.sh in the parent directory)
- install and configure lighttpd (the www folder should be configured as /var/www/)
- copy the static files (invoke copyhtml.sh in the parent directory)
- configure the Wi-Fi dongle to work in access point mode (http://elinux.org/RPI-Wireless-Hotspot)
- run the application (invoke run.sh in the parent directory)

Connect a device to the created wireless network. Open the IP specified in the wi-fi dongle configuration.
