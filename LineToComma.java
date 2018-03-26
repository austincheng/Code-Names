import java.io.*;
import java.util.*;

public class LineToComma {
	public static void main(String[] args) {
		ArrayList<String> ALL = new ArrayList<String>();
        try {
            FileReader fileReader = new FileReader("codeNames/wordSets/PGwordsL.txt");
            BufferedReader bf = new BufferedReader(fileReader);
            String word = bf.readLine();
            while (word != null) {
                ALL.add(word);
                word = bf.readLine();
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter("codeNames/wordSets/PGwords.txt"));
	        for (int i = 0; i < ALL.size() - 1; i++) {
	        	writer.write(ALL.get(i) + ", ");
	        }
            writer.write(ALL.get(ALL.size() - 1));
	        writer.close();
        } catch (Exception e) {
            System.out.println("Error: Could not find file");
            System.exit(0);
        }
	}
}