package FiveDimentionChess;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIO {
    
    public static String getTxtFile(String path) {

        try {
            Path filePath = Paths.get(path);
            String text = Files.readString(filePath);
            
            return text;
        } catch (IOException e) {
            System.out.println(e);

            return "IOException";
        }
    }
}
