package game;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

import game.server.Client;
import game.server.Server;

/**
 * --------------------------------------------------------------------------------------------
 * This class is where all the magic begins. The user runs this class which depending on the user input
 * will create a Server or a Client (when a client is created the user can play the game).
 * To play the game 1 server must be started, then multiple clients can connect and play the game.
 * --------------------------------------------------------------------------------------------
 * This class has to be run 1 time for each client/server which the user want's to create.
 * --------------------------------------------------------------------------------------------
 */
public class BattleShips {
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        boolean conditon = true;
        while (conditon) {
            System.out.println("Welcome to BattleShips: Online version!\n****************************************************\n"
                    + "Do you want to be a client (type 'client') or create a server (type 'server')?");
            String input = scnr.nextLine();
            if (input.toLowerCase(Locale.ROOT).equals("client")) {
                System.out.println("----------------------------------------------------");
                (new Client()).start();
                System.out.println("\n-----" +
                        "-----------------------------------------------\nGoodbye!");
                break;
            } else if (input.toLowerCase(Locale.ROOT).equals("server")) {
                boolean conditon2 = true;
                while (conditon2) {
                    //System.out.println("What is the address?");
                    //  String address = scnr.nextLine(); //TODO uncommented when done testing!
                    String address = "localhost";//TODO delete when done testing
                    int port = 5000; //TODO delete when done testing!
                    // System.out.println("Want to change default port? Type 'Yes' if yes or press any other key to continue with default port 5000."); //TODO uncommented when done testing!

                    try {
//                        if (scnr.nextLine().equals("Yes")) {
//                            System.out.println("What is the port?"); //TODO uncommented when done testing!
//                            port = scnr.nextInt();
//
//                        }


                        ServerSocket serverSock = new ServerSocket(port, 0, InetAddress.getByName(address));
                        System.out.println("----------------------------------------------------");
                        serverSock.close();
                        (new Thread(new Server(address, port))).start();
                        conditon = false;
                        conditon2 = false;
                    } catch (InputMismatchException e) {
                        System.out.println("The port needs to be an integer value!");
                    } catch (IOException e) {
                        System.out.println("Server could not be created on " + address + ":" + port);
                    }

                    if (conditon && conditon2) {
                        System.out.println("Would you" +
                                " like to try again? (yes/no)");
                        scnr.nextLine();
                        if (!scnr.nextLine().trim().equals("yes")) {
                            conditon = false;
                            conditon2 = false;
                            System.out.println("\n----------------------------------------------------\nGoodbye!");
                        }
                    }
                }
            }
        }
        scnr.close();
    }

}


