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

import static charlcdcontroller.CharLCDController.keyboard;

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
