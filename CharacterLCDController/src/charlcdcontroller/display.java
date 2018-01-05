/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charlcdcontroller;

import static charlcdcontroller.CharLCDController.keyboard;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

/**
 *
 * @author thete
 */
public class display implements displayInterface{
    private GpioController gpio = GpioFactory.getInstance();
    private GpioPinDigitalOutput enable;
    private GpioPinDigitalOutput register; 
    private GpioPinDigitalOutput readWrite;
    
    private GpioPinDigitalOutput[] dataBus = new GpioPinDigitalOutput[8];
    
    private int dimX;
    private int dimY;//The X and Y dimensions of the screen (in characters)
    
    private int curPosX;//The position of the cursor.  Can max out at either 40 or 80, depending on dispType
    private int curPosY;//Y position of the cursor
    
    private boolean dispType;//True is a 2 line display.  False is a 1 line display
    
    public display(){
        this.enable = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, "Enable", PinState.LOW);
        this.register = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "Register", PinState.LOW);
        this.readWrite = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "ReadWrite", PinState.LOW);
        this.dataBus[0] = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, "DB0", PinState.LOW);
        this.dataBus[1] = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, "DB1", PinState.LOW);
        this.dataBus[2] = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_05, "DB2", PinState.LOW);
        this.dataBus[3] = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_06, "DB3", PinState.LOW);
        this.dataBus[4] = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07, "DB4", PinState.LOW);
        this.dataBus[5] = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_21, "DB5", PinState.LOW);
        this.dataBus[6] = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_22, "DB6", PinState.LOW);
        this.dataBus[7] = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_23, "DB7", PinState.LOW);
        
        this.dimX = 16;
        this.dimY = 2;
        
        this.curPosX = 0;
        this.curPosY = 0;
        
        this.enable.setShutdownOptions(true, PinState.LOW);
        this.register.setShutdownOptions(true, PinState.LOW);
        this.readWrite.setShutdownOptions(true, PinState.LOW);
        for(int i=0; i<dataBus.length; i++){
            this.dataBus[i].setShutdownOptions(true, PinState.LOW);
        }
    }
    
    public void InitializeDisplay(){
        /*this.db7.low();
        this.db6.low();
        this.db5.low();
        this.db4.low();
        this.db3.high();
        this.db2.high();
        this.db1.high();
        this.db0.high();*/
        for(int i = 0; i<4; i++){
            this.dataBus[i].high();
        }
        for(int i = 4; i<7; i++){
            this.dataBus[i].low();
        }
        
        this.register.low();
        
        this.enable.high();//Send the command
        this.enable.low();
    }
    public void InitializeDisplay(boolean dispOn, boolean cursor, boolean blink){
        //Add parameters
        //this.sendInstruction("11110000");
        for(int i = 0; i<4; i++){
            this.dataBus[i].low();
        }
        this.dataBus[3].high();
        for(int i = 4; i<7; i++){
            this.dataBus[i].low();
        }
        
        this.register.low();
        if(dispOn)
            this.dataBus[2].high();
        if(cursor)
            this.dataBus[1].high();
        if(blink)
            this.dataBus[0].high();
        for(int i=0; i<dataBus.length; i++){
            if(dataBus[i].isHigh()){
                System.out.print("1");
            }else{
                System.out.print("0");
            }
        }
        System.out.print("\n");
        this.enable.high();//Send the command
        this.enable.low();
    }
    public void setPinState(int pinNum, boolean state) throws InvalidInputException{
        if(pinNum > 7 || pinNum <  0){
            throw new InvalidInputException();
        }else{
            if(state){
                this.dataBus[pinNum].high();
            }else{
                this.dataBus[pinNum].low();
            }
        }
    }
    public void setRegister(boolean state){
        if(state){
            this.register.low();
        }else{
            this.register.high();
        }
    }
    public void moveCursorLeft(){
        this.sendInstruction("00001000");
        this.curPosX--;
    }
    public void moveCursorRight(){
        this.sendInstruction("00101000");
        //System.out.println("Moved cursor right");
        this.curPosX++;
    }
    public void moveDisplayLeft(){
        this.sendInstruction("00011000");
    }
    public void moveDisplayRight(){
        this.sendInstruction("00111000");
    }
    public void setDisplayType(boolean type){//True: 2 line, false: 1 line
        if(type){
            this.dispType = true;
            this.sendInstruction("00011100");
        }else{
            this.dispType = false;
            this.sendInstruction("00001100");
        }
    }
    public boolean getDisplayType(){
        return dispType;
    }
    public void moveCursor(){
        //TODO Fill in
        
    }
    public void setDisplayDimensions(int aDimX, int aDimY){
        this.dimX = aDimX;
        this.dimY = aDimY;
    }
    public void setDisplayDimensions(int dimension){//Only for setting the X dimension.  Used by the GUI
        if(dimension >= 1){
            this.dimX = dimension;
        }else{
            System.out.println("Invalid input");//May eventually throw an exception
        }
    }
    
    public void sendData(String binData){
        //clearDisplay();
        this.register.high();
        boolean invalid = false;
        this.curPosX++;//Increment the cursor position
        OUTER:
        for(int j=0; j<binData.length(); j += 8){
            for (int i = 7; i>=0 && !(i > binData.length()); i--) {
                switch (binData.charAt(i)) {
                    case '0':
                        this.dataBus[7-i].low();
                        //System.out.println("LOW, i="+i);
                        break;
                    case '1':
                        this.dataBus[7-i].high();
                        //System.out.println("HIGH, i="+i);
                        break;
                    default:
                        System.out.println("Invalid input");
                        invalid = true;
                        break OUTER;
                }
            }
        
        //if(!invalid)
            this.enable.high();
            //System.out.println("Pulsed Enable");
            this.enable.low();
        }
    }
    public void StringToLCD(String input){
        //input = input.toUpperCase();//Temporary solution until sending lowercase characters is implemented
        
        for(int i=0; i<input.length(); i++){
            char current = input.charAt(i);
            System.out.println(current);
            if(current == '\\'){
                current = input.charAt(i+1);
                System.out.println("\t"+current);
                switch(current){
                    case 'n':
                        System.out.println("Found n\nDimX:"+dimX+"CurPos:"+curPosX);
                        int cursorTemp = curPosX -1;
                        for(int j=1; j<40-cursorTemp; j++){//2 line display is 40 columns wide, 80 split across 2 rows
                            this.moveCursorRight();
                            System.out.println("Moving cursor\tJ:"+j+"CurPos:"+curPosX);
                            
                        }
                        this.curPosX = 0;
                        break;
                    case 't':
                        this.moveCursorRight();
                        this.moveCursorRight();
                        this.moveCursorRight();
                        this.moveCursorRight();
                        this.curPosX += 4;
                    default:
                        break;
                }
                i++;
                continue;
            }
            switch(current){
                
                case '!':
                   this.sendData("00100001");
                    break;                    
                case '"':
                   this.sendData("00100010");
                    break;                    
                case '#':
                   this.sendData("00100011");
                    break;
                case '$':
                   this.sendData("00100100");
                    break;
                case '%':
                   this.sendData("00100101");
                    break;
                case '&':
                   this.sendData("00100110");
                    break;
                case '(':
                   this.sendData("00101000");
                    break;    
                case ')':
                   this.sendData("00101001");
                    break;    
                case '\'':
                   this.sendData("00100111");
                    break;   
                case '*':
                   this.sendData("00101010");
                    break;
                case '+':
                   this.sendData("00101011");
                    break;
                case ',':
                   this.sendData("00101100");
                    break;
                case '-':
                   this.sendData("00101100");
                    break;
                case '.':
                   this.sendData("00101110");
                    break;
                case '/':
                   this.sendData("00101111");
                    break;
                case '0':
                   this.sendData("00110000");
                   break;
                case '1':
                    this.sendData("00110001");
                    break;
                case '2':
                    this.sendData("00110010");
                    break;
                case '3':
                    this.sendData("00110011");
                    break;
                case '4':
                    this.sendData("00110100");
                    break;
                case '5':
                    this.sendData("00110101");
                    break;
                case '6':
                    this.sendData("00110110");
                    break;
                case '7':
                    this.sendData("00110111");
                    break;
                case '8':
                   this.sendData("00111000");
                   break;
                case '9':
                   this.sendData("00111001");
                   break;
                case ':':
                   this.sendData("00111010");
                   break;
                case ';':
                   this.sendData("00111011");
                   break;
                case '<':
                   this.sendData("00111100");
                   break;
                case '=':
                   this.sendData("00111101");
                   break;
                case '>':
                   this.sendData("00111110");
                   break;
                case '?':
                   this.sendData("00111111");
                   break;
                case '@':
                   this.sendData("01000000");
                   break;
                case 'A':
                   this.sendData("01000001");
                    break;
                case 'B':
                   this.sendData("01000010");
                    break;
                case 'C':
                   this.sendData("01000011");
                    break;
                case 'D':
                   this.sendData("01000100");
                    break;
                case 'E':
                   this.sendData("01000101");
                    break;
                case 'F':
                   this.sendData("01000110");
                    break;
                case 'G':
                   this.sendData("01000111");
                    break;
                case 'H':
                   this.sendData("01001000");
                    break;
                case 'I':
                   this.sendData("01001001");
                    break;
                case 'J':
                   this.sendData("01001010");
                    break;
                case 'K':
                   this.sendData("01001011");
                    break;
                case 'L':
                   this.sendData("01001100");
                    break;
                case 'M':
                   this.sendData("01001101");
                    break;
                case 'N':
                   this.sendData("01001110");
                    break;
                case 'O':
                   this.sendData("01001111");
                    break;
                case 'P':
                   this.sendData("01010000");
                    break;
                case 'Q':
                   this.sendData("01010001");
                    break;
                case 'R':
                   this.sendData("01010010");
                    break;
                case 'S':
                   this.sendData("01010011");
                    break;
                case 'T':
                   this.sendData("01010100");
                    break;
                case 'U':
                   this.sendData("01010101");
                    break;
                case 'V':
                   this.sendData("01010110");
                    break;
                case 'W':
                   this.sendData("01010111");
                    break;
                case 'X':
                   this.sendData("01011000");
                    break;
                case 'Y':
                   this.sendData("01011001");
                    break;
                case 'Z':
                   this.sendData("01011010");
                    break;
                case '[':
                    this.sendData("01001011");
                    break;
                case ']':
                    this.sendData("01001101");
                    break;
                case '^':
                    this.sendData("01001101");
                    break;
                case '`':
                    this.sendData("01100000");
                    break;
                case 'a':
                    this.sendData("01100001");
                    break;
                case 'b':
                    this.sendData("01100010");
                    break;
                case 'c':
                    this.sendData("01100011");
                    break;
                case 'd':
                    this.sendData("01100100");
                    break;
                case 'e':
                    this.sendData("01100101");
                    break;
                case 'f':
                    this.sendData("01100110");
                    break;
                case 'g':
                    this.sendData("01100111");
                    break;
                case 'h':
                    this.sendData("01101000");
                    break;
                case 'i':
                    this.sendData("01101001");
                    break;
                case 'j':
                    this.sendData("01101010");
                    break;
                case 'k':
                    this.sendData("01101011");
                    break;
                case 'l':
                    this.sendData("01101100");
                    break;
                case 'm':
                    this.sendData("01101101");
                    break;
                case 'n':
                    this.sendData("01101110");
                    break;
                case 'o':
                    this.sendData("01101111");
                    break;
                case 'p':
                    this.sendData("01110000");
                    break;
                case 'q':
                    this.sendData("01110001");
                    break;
                case 'r':
                    this.sendData("01110010");
                    break;
                case 's':
                    this.sendData("01110011");
                    break;
                case 't':
                    this.sendData("01110100");
                    break;
                case 'u':
                    this.sendData("01110101");
                    break;
                case 'v':
                    this.sendData("01110110");
                    break;
                case 'w':
                    this.sendData("01110111");
                    break;
                case 'x':
                    this.sendData("01111000");
                    break;
                case 'y':
                    this.sendData("01111001");
                    break;
                case 'z':
                    this.sendData("01111010");
                    break;
                case '{':
                    this.sendData("01111011");
                    break;
                case '|':
                    this.sendData("01111100");
                    break;
                case '}':
                    this.sendData("01111101");
                    break;
                default:
                   this.sendData("10000001");
                    break;
            }
            
        }
    }
    public void charToLCD(char input){
        String StrInput = String.valueOf(input);
        this.StringToLCD(StrInput);
    }
    public void sendInstruction(String binData){
        this.register.low();
        boolean invalid = false;
        OUTER:
        for (int j=0; j<binData.length(); j += 8){

            
            for (int i = 0; i<binData.length() && i<8; i++) {
                switch (binData.charAt(i+j)) {
                    case '0':
                        this.dataBus[i].low();
                        break;
                    case '1':
                        this.dataBus[i].high();
                        break;
                    default:
                        System.out.println("Invalid input");
                        System.out.println(binData.charAt(i) + " was not expected");
                        invalid = true;
                        break OUTER;
                }
            }
            if(!invalid){
                this.enable.high();
                //System.out.println("Sent instruction "+binData);
                this.enable.low();
            }
        }
    }
    
    public void homeCursor(){
        this.sendInstruction("01000000");
        this.curPosX = 0;
        this.curPosY = 0;
    }
    
    public void enable(){
        this.enable.high();
        this.enable.low();
    }
    public void clearDisplay(){
        this.register.low();
        this.dataBus[0].high();
        for(int i=1; i<dataBus.length; i++){
            this.dataBus[i].low();
        }        
        this.curPosX = 0;
        this.enable.high();
        this.enable.low();
    }
    
    public void shutdown(){
        gpio.shutdown();
        System.out.println("GPIO Shutdown complete");
    }
    public void exit(){
        gpio.shutdown();
        System.exit(0);
    }
}
