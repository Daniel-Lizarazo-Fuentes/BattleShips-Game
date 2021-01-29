package game.server;

import java.io.PrintWriter;
import java.util.Scanner;

public class ServerTUI {
    private PrintWriter console;

    public ServerTUI() {
        console = new PrintWriter(System.out, true);
    }

    public void showMessage(String msg) {
        console.println(msg);
    }

    public boolean getBoolean(String question) {
        showMessage(question);
        Scanner scnr = new Scanner(System.in);
        return scnr.nextBoolean() ;
    }

    public String getString(String question) {
        showMessage(question);
        Scanner scnr = new Scanner(System.in);
        return scnr.nextLine();
    }

    public int getInt(String question) {
        showMessage(question);
        Scanner scnr = new Scanner(System.in);
        return scnr.nextInt();
    }
}
