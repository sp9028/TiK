package ltpo.Seznami;
import java.io.*;
import java.util.Objects;

public class Vozniki {
    public static void main(String[] args) throws IOException {

        SeznamiUV seznamiUV = new SeznamiUV();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input;
        String output;
        try {
            System.out.print("ukaz> ");
            input = br.readLine();
            while (!input.trim().equals("izhod")){
                output = seznamiUV.processInput(input);
                System.out.print(output);
                if (seznamiUV.addPhase < 0 && seznamiUV.removePhase < 0 && seznamiUV.searchPhase < 0 &&
                seznamiUV.resetPhase < 0){
                    System.out.print("\n");
                    System.out.print("ukaz> ");
                }
                input = br.readLine();
            }
            System.out.println(">> Nasvidenje");
        }
        catch (IOException e) {
            System.err.println("Failed to retrieve the next command.");
            System.exit(1);
        }
    }
}
