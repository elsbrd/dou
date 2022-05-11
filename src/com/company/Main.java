package com.company;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {

        Broker broker = new Broker();
        FileReader bufferedReader  = new FileReader("input.txt");
        BufferedReader reader = new BufferedReader(bufferedReader);
        FileWriter bufferedWriter = new FileWriter("output.txt");
        BufferedWriter writer  = new BufferedWriter(bufferedWriter);

        try {
            String input;
            StringBuilder sb = new StringBuilder();

            while ((input = reader.readLine()) != null) {
                String[] str = input.split(",");

                if (str[0].equals("u")) broker.proceedUpdate(str);
                if (str[0].equals("o")) broker.proceedOrder(str);
                if (str[0].equals("q")) broker.proceedQuery(str, sb);
            }
            writer.write(sb.toString().trim());
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

