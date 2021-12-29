import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Callable;

public class SearchInFile implements Callable<Integer> {
    private File file;
    private String word;

    public SearchInFile(File file, String word){
        this.file = file;
        this.word = word;
    }

    public Integer call(){
        int counter = 0;
        try (Scanner sc = new Scanner(new FileInputStream(file))){
            while (sc.hasNextLine()){
                String s = sc.nextLine();

                if (s.contains(word)){
                    counter++;
                }
            }

            if (counter > 0) System.out.println(counter + "\t" + file);

            return counter;
        } catch(IOException e){
            return 0;
        }
    }
}
