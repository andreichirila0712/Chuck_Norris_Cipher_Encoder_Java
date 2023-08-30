package chucknorris;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please input operation (encode/decode/exit):");
            String input = scanner.nextLine();

            if (!input.contains("encode") && !input.contains("decode") && !input.contains("exit")) {
                System.out.println("There is no '" + input + "' operation");
                System.out.println();
                continue;
            }

            switch (input) {
                case "encode" -> {
                    System.out.println("Input string:");
                    String givenInput = scanner.nextLine();
                    encryption(givenInput);
                    System.out.println();
                }

                case "decode" -> {
                    System.out.println("Input encoded string:");
                    String givenEncodedInput = scanner.nextLine();
                    String[] inputs = givenEncodedInput.split(" ");

                    StringBuilder sb = new StringBuilder(), decodedBinary = new StringBuilder();
                    boolean check = false;

                    for (int i = 0; i < inputs.length; i++) {
                        sb.append(inputs[i]);
                        if (i % 2 != 0) {
                            decodedBinary.append(inputs[i]);
                        }

                        if (i % 2 == 0) {
                            if (!inputs[i].equals("0") && !inputs[i].equals("00")) {
                                check = true;
                            }
                        }
                    }

                    if (!sb.toString().matches("^[0]+$") || inputs.length % 2 != 0 || check
                            || decodedBinary.length() % 7 != 0) {
                        System.out.println("Encoded string is not valid.");
                        System.out.println();
                        continue;
                    }
                    decryption(givenEncodedInput);
                    System.out.println();
                }

                case "exit" -> {
                    System.out.println("Bye!");
                    System.exit(0);
                }
            }

        }
    }


    // STAGE 2
    static String charToBinary(String word) {
        byte[] bytes = word.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 7; i++) {
                binary.append((val & 64) == 0 ? 0 : 1);
                val <<= 1;
            }
        }

        return binary.toString();
    }

    // STAGE 3

    static void encryption(String word) {
        String binaryString = charToBinary(word);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < binaryString.length(); i++) {
            int counter1 = 0, counter0 = 0;
            int j = i;

            while (binaryString.charAt(j) == '1') {
                counter1++;
                j++;

                if (j == binaryString.length()) {
                    break;
                }
            }

            j = i;

            while (binaryString.charAt(j) == '0') {
                counter0++;
                j++;

                if (j == binaryString.length()) {
                    break;
                }
            }

            if (binaryString.charAt(i) == '1') {
                sb.append('0');
                sb.append(" ");
                sb.append("0".repeat(counter1));
                sb.append(" ");
            }

            if (binaryString.charAt(i) == '0') {
                sb.append("00");
                sb.append(" ");
                sb.append("0".repeat(counter0));
                sb.append(" ");
            }

            if (counter1 > 0) {
                i = i + counter1 - 1;
            }

            if (counter0 > 0) {
                i = i + counter0 - 1;
            }
        }
        System.out.println("Encoded string:");
        System.out.println(sb);
    }

    // STAGE 4

    static void decryption(String input) {
        StringBuilder sb = new StringBuilder();

        String[] inputs = input.split(" ");
        for (int i = 0; i < inputs.length; i = i + 2) {
            int counter1 = 0, counter0 = 0;

            if (inputs[i].equals("0")) {
                counter1 = counter1 + inputs[i + 1].length();
                sb.append("1".repeat(counter1));

            }

            if (inputs[i].equals("00")) {
                counter0 = counter0 + inputs[i + 1].length();
                sb.append("0".repeat(counter0));

            }

        }

        String[] decryptedString = sb.toString().split("(?<=\\G.{" + 7 + "})");

        StringBuilder decryptedText = new StringBuilder();

        for (int i = 0; i < decryptedString.length; i++) {
            int number = Integer.parseInt(decryptedString[i], 2);
            char c = (char) number;
            decryptedText.append(c);
        }

        System.out.println("Decoded string:");
        System.out.println(decryptedText);

    }
}