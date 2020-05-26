package encryptdecrypt;

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
        int key = 0;
        for(int i = 0; i < args.length; i++){
            if(args[i].equals("-mode")){
                operation = args[i + 1];
                i++;
            }
            if(args[i].equals("-key")){
                key = Integer.parseInt(args[i + 1]);
                i++;
            }
            if(args[i].equals("-data")){
                str = args[i + 1];
                i++;
            }
        }
        operation(operation, str, key);
    }

    public static void encryption(String str, int key) {
        String answer = "";
        for (int i = 0; i < str.length(); i++) {
            answer = answer + (char) ((str.charAt(i) + key) % 128);
        }
        System.out.println(answer);
    }

    public static void decryption(String str, int key) {
        String answer = "";
        for (int i = 0; i < str.length(); i++) {
            answer = answer + (char) ((128 + str.charAt(i) - key) % 128);
        }
        System.out.println(answer);
    }

    public static void operation(String operation, String str, int key) {
        switch (operation) {
            case "enc":
                encryption(str, key);
                break;
            case "dec":
                decryption(str, key);
                break;
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
