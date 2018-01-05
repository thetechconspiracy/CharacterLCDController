/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charlcdcontroller;

import static charlcdcontroller.CharLCDController.keyboard;

/**
 *
 * @author thete
 */
public class ControllerCLI {
    private displayInterface Screen;
    public ControllerCLI(){
        CLI(false);
    }
    public ControllerCLI(boolean debug){
        CLI(debug);
    }
    private void CLI(boolean debug){
        /*Pin Layout:
        *GPIO 1: Enable
        *GPIO 2: Register
        *GPIO 3-10: Data Bus
        */
        if(debug){
            Screen = new displayDebug();
        }else{
            Screen = new display();
        }
        Screen.InitializeDisplay(true, false, false);
//        while(true){
//            System.out.println("Please input binary data to send to the screen as a character, or enter \"next\" to continue to the next part");
//            //keyboard.nextLine();
//            String input = keyboard.nextLine();
//            if(input.equalsIgnoreCase("next")){
//                break;
//            }
//            Screen.sendData(input);
//        }
        while(true){
            System.out.println("Menu:\n1. Send Instruction\n2. Send String\n3. Shift Display\n4. Move Cursor\n5. Clear Display\n6. Initialize Display\n7. Send Binary Characters\n8. Send character\n0. Exit");
            int input = keyboard.nextInt();
            keyboard.nextLine();
            OUTER:
            switch (input) {
                case 1:
                    System.out.println("Enter Instruction in binary form.  DB0 - DB7");
                    String inst = keyboard.nextLine();
                    Screen.sendInstruction(inst);
                    break;
                case 2:
                    System.out.println("Input String...");
                    String stringInput = keyboard.nextLine();
                    Screen.StringToLCD(stringInput);
                    break;
                case 3:
                    System.out.println("Shift what direction in x?");
                    int moveX = keyboard.nextInt();
                    System.out.println("Shift what direction in Y?");
                    int moveY = keyboard.nextInt();
                    //Screen.moveDisplay(moveX, moveY);
                    break;
                case 4:
                    System.out.println("1. Relative\n2. Absolute");
                    input = keyboard.nextInt();
                    boolean relative;
                    switch (input) {
                        case 1:
                            relative = true;
                            break;
                        case 2:
                            relative = false;
                            break;
                        default:
                            System.out.println("Invalid input");
                            break OUTER;
                    }
                    System.out.println("Enter direction to move in X:");
                    int moveCurX = keyboard.nextInt();
                    System.out.println("Enter direction to move in Y:");
                    int moveCurY = keyboard.nextInt();
                    //Screen.moveCursor(moveCurX, moveCurY, relative);
                    break;
                case 5:
                    Screen.clearDisplay();
                    break;
                case 6:
                    Screen.InitializeDisplay();
                    break;
                case 7:
                    System.out.println("Enter binary data to send as characters");
                    //keyboard.nextLine();
                    String input2 = keyboard.nextLine();
                    Screen.sendData(input2);
                    break;
                case 8:
                    System.out.println("Enter character to send to the LCD");
                    String inputStr = keyboard.nextLine();
                    char CharInput = inputStr.charAt(0);
                    Screen.charToLCD(CharInput);
                case 0:
                    Screen.exit();
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        }
    }
}
