package com.shoggoth.crudapp.view;

import java.io.InputStream;
import java.util.Scanner;

public class UserInterface {
    private final Scanner scanner;
    public UserInterface(InputStream is) {
        this.scanner = new Scanner(is);
    }

    public void writeToConsole(String output) {
        System.out.println(output);
    }

    public String readFromConsole() {
        return scanner.nextLine();
    }
}
