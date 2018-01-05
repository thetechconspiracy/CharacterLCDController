# CharacterLCDController
Requires the pi4j library: http://pi4j.com/download.html

# Pinout
|GPIO Pin|LCD Pin|Name in code|
|------|:-----:|:----------:|
|+5 VDC|2, 15|N/A|
|GND|1, 16|N/A|
|00|6|enable|
|01|4|register|
|02|2|readWrite|
|03|7|dataBus[0]|
|04|8|dataBus[1]|
|05|9|dataBus[2]|
|06|10|dataBus[3]|
|07|11|dataBus[4]|
|21|12|dataBus[5]|
|22|13|dataBus[6]|
|23|14|dataBus[7]|

### Please note that Pi4J uses the GPIO pinout used by WiringPi:
##### 26 pin header: http://pi4j.com/pins/model-a-rev2.html#P1_Pinout_26-pin_Header
##### 40 pin header: http://pi4j.com/pins/model-b-plus.html#J8_Pinout_40-pin_Header

# CLI Parameters:
  * -g: Force GUI
  * -c: Force CLI
  * -d: Debug mode (does not require Pi4J or a Raspberry Pi, used for debug purposes only).  Requires a terminal for full functionality
