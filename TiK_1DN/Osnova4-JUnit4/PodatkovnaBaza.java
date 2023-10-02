package ltpo.Seznami;

import java.io.*;

public class PodatkovnaBaza {

    public static void main(String[] args) {
        SeznamiUV seznamiUV = new SeznamiUV();

        seznamiUV.addImpl("sk", new Sklad<String>());
        seznamiUV.addImpl("pv", new PrioritetnaVrsta<String>());
        seznamiUV.addImpl("bst", new Bst<String>());

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input;
        String output;
        try {
            do {
                System.out.print("Enter command: ");
                input = br.readLine();
                output = seznamiUV.processInput(input);
                System.out.println(output);
            }
            while (!input.equalsIgnoreCase("exit"));
        }
        catch (IOException e) {
            System.err.println("Failed to retrieve the next command.");
            System.exit(1);
        }
    }
}
