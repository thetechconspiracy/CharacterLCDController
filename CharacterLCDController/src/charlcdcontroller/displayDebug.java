/*
BSD 3-Clause License

Copyright (c) 2018, Joseph Trask Still
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

* Redistributions of source code must retain the above copyright notice, this
  list of conditions and the following disclaimer.

* Redistributions in binary form must reproduce the above copyright notice,
  this list of conditions and the following disclaimer in the documentation
  and/or other materials provided with the distribution.

* Neither the name of the copyright holder nor the names of its
  contributors may be used to endorse or promote products derived from
  this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package charlcdcontroller;

public class displayDebug implements displayInterface{
    private boolean[] pins = new boolean[8];
    private int dimX;
    private int dimY;
    
    
    
    public displayDebug(){
        System.out.println("Default Constructor");
        
    }
    public void print(String input){
        System.out.println(input);
    }
        
    public void InitializeDisplay(){
    }
    public void InitializeDisplay(boolean dispOn, boolean cursor, boolean blink){
        System.out.println("Display is initialized.  \nDisplay: "+dispOn+" \nCursor: "+cursor+"\nBlink: "+blink);
                
    }
    public void setPinState(int pinNum, boolean state) throws InvalidInputException{
        System.out.println("Pin "+pinNum+" is set to "+state);
        pins[pinNum] = state;
    }
    public void setRegister(boolean state){
        if(state){
            print("Inst");
        }else{
            print("Data");
        }
    }
    public void setDisplayDimensions(int aDimX, int aDimY){
        print(aDimX+"\t"+aDimY);
        this.dimX = aDimX;
        this.dimY = aDimY;
    }
    
    public void sendData(String binData){
        //clearDisplay();
        //this.register.high();
        boolean invalid = false;
        //this.curPosX++;//Increment the cursor position
        OUTER:
        for(int j=0; j<binData.length(); j += 8){
            for (int i = 7; i>=0 /*&& !(i>binData.length())*/; i--) {
                switch (binData.charAt(i+j)) {
                    case '0':
                        //this.dataBus[7-i].low();
                        System.out.println("DB "+ (7-i) +" pulled low");
                        //System.out.println("LOW, i="+i);
                        break;
                    case '1':
                        //this.dataBus[7-i].high();
                        System.out.println("DB "+ (7-i) +" pulled high");
                        //System.out.println("HIGH, i="+i);
                        break;
                    default:
                        System.out.println("Invalid input");
                        invalid = true;
                        break OUTER;
                }
            }
        
        print("Pulsed enable");
        //if(!invalid)
            //this.enable.high();
            //System.out.println("Pulsed Enable");
            //this.enable.low();
        }
    }
    
    public void sendInstruction(String binData){
        print("Sent "+binData);
    }
    
    public void homeCursor(){
        print("Cursor homed");
    }
    public void moveCursor(int x, int y, boolean relative){
        
        
    }
    
    public void moveDisplay(int x, int y){
        
    }
    public void charToLCD(char input){
        String StrInput = String.valueOf(input);
        this.StringToLCD(StrInput);
    }
    public void enable(){
        for(int i=0; i<pins.length; i++){
            System.out.println(i+", "+pins[i]);
        }
    }
    public void clearDisplay(){
        System.out.println("Display Cleared");
    }
    
    public void shutdown(){
        System.out.println("GPIO Shutdown");
    }
    public void exit(){
        System.exit(0);
    }

    @Override
    public void moveCursorLeft() {
        print("Moved cursor left");
    }

    @Override
    public void moveCursorRight() {
        print("Moved cursor right");
    }

    @Override
    public void moveDisplayLeft() {
        print("Moved display left");
    }

    @Override
    public void moveDisplayRight() {
        print("Moved display right");
    }

    @Override
    public void setDisplayType(boolean type) {
        print("Display type set to "+type);
    }

    @Override
    public boolean getDisplayType() {
        print("Pretending to be a 2 line");
        return true;
    }

    @Override
    public void moveCursor() {
        System.out.println("moveCursor called");
    }

    @Override
    public void setDisplayDimensions(int dimension) {
        print("Horizontal dimension:"+dimension);
    }

    @Override
    public void StringToLCD(String input) {
        System.out.println("Sending input: "+input);
    }
}
