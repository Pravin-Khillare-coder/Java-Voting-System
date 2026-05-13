package util;

import java.io.*;

public class FileUtil {

    public static BufferedReader getReader(String file) throws IOException {
        return new BufferedReader(new FileReader(file));
    }

    public static BufferedWriter getWriter(String file) throws IOException {
        return new BufferedWriter(new FileWriter(file));
    }
}