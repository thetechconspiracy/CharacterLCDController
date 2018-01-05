/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charlcdcontroller;

/**
 *
 * @author thete
 */
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
