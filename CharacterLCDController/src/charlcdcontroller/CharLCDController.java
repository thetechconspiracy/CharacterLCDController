/*Copyright 2017 Joseph Still, thetechconspiracy@outlook.com

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.

3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.*/
package charlcdcontroller;
//import com.pi4j.io.gpio.*;
import java.util.Scanner;
/**
 *
 * @author thete
 */
public class CharLCDController { 
    static Scanner keyboard = new Scanner(System.in);
    //static display Screen = new display();
    private static boolean debug;
    private static boolean CLI = false;
    private static boolean GUI = true;
    public static void main(String[] args){
        boolean debug = false;
        for(int i=0; i<args.length; i++){
            System.out.println(args[0]);
            if(args[i].charAt(0) == '-'){
                //System.out.println("Found \"-\"");
                switch(args[i].charAt(1)){
                    case 'd'://Debug mode
                        System.out.println("Enabling debug mode");
                        debug = true;
                        break;
                    case 'c':
                        System.out.println("Using CLI");
                        CLI = true;
                    case 'g':
                        System.out.println("Using GUI");
                        GUI = true;
                    default:
                        //System.out.println("Parameters:\n"+
                        //        "-d: Debug mode\n"+
                        //        "-c: Force CLI");
                                
                }
            }
        }
        menu(debug);
        
    }
    public static void menu(boolean debug){
        if(CLI){
            CLI(debug);
        }else if(GUI){
            GUI(debug);
        }
        System.out.println("1. GUI\n2. CLI");
        int input = keyboard.nextInt();
        switch (input) {
            case 1:
                GUI(debug);
                break;
            case 2:
                CLI(debug);
                break;
            default:
                System.out.println("Invalid Input");
                menu(debug);
                break;
        }
    }
    
    public static void CLI(boolean debug) {
        ControllerCLI cli = new ControllerCLI(debug);
    }
    public static void GUI(boolean debug){
        ControllerGUI gui = new ControllerGUI(debug);
        gui.setVisible(true);
    }

}
