package encryptdecrypt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // в main методе происходит обработка строки введенной из командной строки
        String operation = "enc"; // это какой-то монстр
        String alg = "shift"; // по умолчанию применяю шифровальщик и смещение
        String str = "";
        String in = "";
        String out = "";
        int key = 0; // the key defalult value is 0
        for (int i = 0; i < args.length; i++) { // here i just process command line arguments
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
            if ("-alg".equals(args[i])) {
                alg = args[++i];
            }
        } // а вот уже здесь начинается веселье
        if (operation == "enc") {
            if (alg == "shift") {
                Encryption obj = new EncryptionAlphabet(str, key, in);
                String value = obj.encryption(str, key, in);
                I_am_fool(out, value);
            } else {
                Encryption obj = new EncryptionUnicode(str, key, in);
                String value = obj.encryption(str, key, in);
                I_am_fool(out, value);
            }
        } else {
            if (alg == "shift") {
                Decryption obj = new DecryptionAlphabet(str, key, in);
                String value = obj.decryption(str, key, in);
                I_am_fool(out, value);
            } else {
                Decryption obj = new DecryptionUnicode(str, key, in);
                String value = obj.decryption(str, key, in);
                I_am_fool(out, value);
            }
        }
    }

    // статичкский метод для печати
    static void I_am_fool(String out, String value) {
        if (out == "") {
            System.out.println(value);
        } else {
            printAnswer(out, value);
        }
    }

    public static String getString(String in) { // метод для перевода данных из файла в строку
        String allValue = "";
        File fileIn = new File(in);
        try (Scanner scanner = new Scanner(fileIn)) {
            while (scanner.hasNextLine()) {
                allValue = allValue + scanner.nextLine() + "\n";
            }
        } catch (FileNotFoundException e) {
            System.out.println("Boss we have a problem! It's Error");
        }
        return allValue;
    }

    public static void printAnswer(String out, String value) { // метод для печати ответа, а точнее записи ответа в  файл
        File fileOut = new File(out);
        try {
            FileWriter writer = new FileWriter(fileOut);
            writer.write(value);
            writer.close();
        } catch (IOException e) {
            System.out.println("Boss something is wrong. Is it Error ?!\n");
            System.out.println(value);
        }

    }

    // интерфейсы для шифрования/дешифрования
    interface Encryption {
        public String encryption(String str, int key, String in);
    }

    interface Decryption {
        public String decryption(String str, int key, String in);
    }

    static class EncryptionUnicode implements Encryption {
        String str;
        int key;
        String in;

        EncryptionUnicode(String str, int key, String in) {
            this.str = str;
            this.key = key;
            this.in = in;
        }

        @Override
        public String encryption(String str, int key, String in) {
            String answer = ""; // шифрование по unicode
            if ("".equals(str)) {
                str = getString(in);
            }
            for (int i = 0; i < str.length(); i++) {
                answer = answer + (char) ((str.charAt(i) + key) % 128);
            }
            return (answer);
        }
    }

    static class DecryptionUnicode implements Decryption {
        String str;
        int key;
        String in;

        DecryptionUnicode(String str, int key, String in) {
            this.in = in;
            this.key = key;
            this.str = str;
        }

        @Override
        public String decryption(String str, int key, String in) {
            String answer = ""; // дешифрование по unicode
            if (str.equals("")) {
                str = getString(in);
            }
            for (int i = 0; i < str.length(); i++) {
                answer = answer + (char) ((128 + str.charAt(i) - key) % 128);
            }
            return (answer);
        }
    }

    static class DecryptionAlphabet implements Decryption {
        String str;
        int key;
        String in;

        DecryptionAlphabet(String str, int key, String in) {
            this.in = in;
            this.key = key;
            this.str = str;
        }

        @Override
        public String decryption(String str, int key, String in) {
            String answer = ""; // поле для ответа, будем дешифровать буквы
            if ("".equals(str)) {
                str = getString(in);
            }
            char alphabetL[] = "abcdefghijklmnopqrstuvwxyz".toCharArray();
            char alphabetU[] = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
            for (int i = 0; i < str.length(); i++) {
                boolean temp = true;
                for (int j = 0; j < alphabetL.length; j++) {
                    if (str.charAt(i) == alphabetL[j] || str.charAt(i) == alphabetU[j]) {
                        for (int index = 0; index < alphabetL.length; index++) {
                            if ((index + key) % 26 == j) {
                                if (str.charAt(i) == alphabetL[j]) {
                                    temp = false;
                                    answer += alphabetL[index];
                                } else {
                                    temp = false;
                                    answer += alphabetU[index];
                                }
                            }
                        }
                    }
                }
                if (temp) {
                    answer += str.charAt(i);
                }
            }
            return answer;
        }
    }

    // encrypton alphabet
    static class EncryptionAlphabet implements Encryption {
        String str;
        int key;
        String in;

        EncryptionAlphabet(String str, int key, String in) {
            this.in = in;
            this.key = key;
            this.str = str;
        }

        @Override
        public String encryption(String str, int key, String in) {
            String answer = ""; // поле для ответа, будем дешифровать буквы
            if ("".equals(str)) {
                str = getString(in);
            }
            char alphabetL[] = "abcdefghijklmnopqrstuvwxyz".toCharArray();
            char alphabetU[] = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
            for (int i = 0; i < str.length(); i++) {
                boolean temp = true;
                for (int j = 0; j < alphabetL.length; j++) {
                    if (str.charAt(i) == alphabetL[j] || str.charAt(i) == alphabetU[j]) {
                        if (str.charAt(i) == alphabetL[j]) {
                            answer += alphabetL[(j + key) % 26];
                            temp = false;
                        } else {
                            answer += alphabetU[(j + key) % 26];
                            temp = false;
                        }
                    }
                }
                if (temp) {
                    answer += str.charAt(i);
                }
            }
            return answer;
        }
    }
}
