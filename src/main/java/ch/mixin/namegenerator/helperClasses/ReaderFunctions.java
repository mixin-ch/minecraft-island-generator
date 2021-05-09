package ch.mixin.namegenerator.helperClasses;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReaderFunctions {
    public static String readFile(String filePath) {
        // this is the path within the jar file
        InputStream input = ReaderFunctions.class.getResourceAsStream("/" + filePath);
        if (input == null) {
            // this is how we load file within editor (eg eclipse)
            input = ReaderFunctions.class.getClassLoader().getResourceAsStream(filePath);
        }

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(input));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String text = "";

        try {
            for (String line; (line = reader.readLine()) != null; ) {
                text += line + "\n";
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
}
