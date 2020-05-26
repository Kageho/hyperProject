package encryptdecrypt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //System.out.println("dv ulfmw z givzhfiv!");
        /*Scanner scanner = new Scanner(System.in);
        String operation = scanner.nextLine();
        String str = scanner.nextLine();
        int key = scanner.nextInt();*/
        String operation = "enc";
        String str = "";
        String in = "";
        String out = "";
        int key = 0;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-mode")) {
                operation = args[i + 1];
                i++;
            }
            if (args[i].equals("-key")) {
                key = Integer.parseInt(args[i + 1]);
                i++;
            }
            if (args[i].equals("-data")) {
                str = args[i + 1];
                i++;
            }
            if (args[i].equals("-in")) {
                in = args[i + 1];
                i++;
            }
            if (args[i].equals("-out")) {
                out = args[i + 1];
                i++;
            }
        }
        operation(operation, str, key, in, out);
    }
    public static void printAnswer(String out, String value){
        File fileOut = new File(out);
        try{
            FileWriter writer = new FileWriter(fileOut);
            writer.write(value);
            writer.close();
        } catch (IOException e) {
            System.out.println("Boss something is wrong. Is it Error ?!\n");
            System.out.println(value);
        }

    }
    public static String getString(String in) {
        String allValue = "";
        File fileIn = new File(in);
        try (Scanner scanner = new Scanner(fileIn)) {
            while (scanner.hasNextLine()) {
                allValue = allValue + scanner.nextLine() + "\n";
            }
        } catch (FileNotFoundException e) {
            System.out.println("Boss we have problem! It's Error");
        }
        return allValue;
    }

    public static String encryption(String str, int key, String in) {
        String answer = "";
        if (str.equals("")) {
            str = getString(in);
        }
        for (int i = 0; i < str.length(); i++) {
            answer = answer + (char) ((str.charAt(i) + key) % 128);
        }
        return (answer);
    }

    public static String decryption(String str, int key, String in) {
        String answer = "";
        if (str.equals("")) {
            str = getString(in);
        }
        for (int i = 0; i < str.length(); i++) {
            answer = answer + (char) ((128 + str.charAt(i) - key) % 128);
        }
        return (answer);
    }

    public static void operation(String operation, String str, int key, String in, String out) {
        String value = "";
        switch (operation) {
            case "enc":
                value = encryption(str, key, in);
                break;
            case "dec":
                value = decryption(str, key, in);
                break;
        }
        if(out.equals("")){
            System.out.println(value);
        } else{
            printAnswer(out, value);
        }
    }
    // 2 stage
    /*public static void cypher (String str, int key){
        String answer = "";
        for(int i = 0; i < str.length(); i++){
            answer = answer + alpha(str.charAt(i), key);
        }
        System.out.println(answer);
    }*/
    /*public static char alpha (char symbol, int key){
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        int index = 80;
        for(int i = 0; i < alphabet.length; i++){
            if(symbol == alphabet[i]){
                index = i;
            }
        }
        if(index == 80){
            return symbol;
        }
        else {
            return alphabet[(key + index) % 26];
        }
    }*/
}
