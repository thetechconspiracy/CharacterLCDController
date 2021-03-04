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
//package charlcdcontroller;

public interface displayInterface {
    public void InitializeDisplay();
    public void InitializeDisplay(boolean dispOn, boolean cursor, boolean blink);
    public void setPinState(int pinNum, boolean state) throws InvalidInputException;
    public void setRegister(boolean state);
    public void moveCursorLeft();
    public void moveCursorRight();
    public void moveDisplayLeft();
    public void moveDisplayRight();
    public void setDisplayType(boolean type);
    public boolean getDisplayType();
    public void moveCursor();
    public void setDisplayDimensions(int aDimX, int aDimY);
    public void setDisplayDimensions(int dimension);
    public void sendData(String binData);
    public void StringToLCD(String input);
    public void sendInstruction(String binData);
    public void homeCursor();
    public void enable();
    public void clearDisplay();
    public void shutdown();
    public void exit();
    public void charToLCD(char input);
}
