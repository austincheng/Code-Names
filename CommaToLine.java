import java.io.*;
import java.util.*;

public class CommaToLine {
	public static void main(String[] args) {
		ArrayList<String> ALL = new ArrayList<String>();
        try {
            FileReader fileReader = new FileReader("codeNames/wordSets/words.txt");
            BufferedReader bf = new BufferedReader(fileReader);
            int x = bf.read();
            while (x != -1) {
                String word = "";
                char c = (char) x;
                while (c != ',' && x != -1) {
                    word += c;
                    x = bf.read();
                    c = (char) x;
                }
                while (x == ',' || x== ' ') {
                    x = bf.read();
                }
                ALL.add(word);
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter("codeNames/wordSets/wordsL.txt"));
	        for (int i = 0; i < ALL.size() - 1; i++) {
	        	writer.write(ALL.get(i) + "\r\n");
	        }
            writer.write(ALL.get(ALL.size() - 1));
	        writer.close();
        } catch (Exception e) {
            System.out.println("Error: Could not find file");
            System.exit(0);
        }
	}
}