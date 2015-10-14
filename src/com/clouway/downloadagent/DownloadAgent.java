package com.clouway.downloadagent;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class DownloadAgent {
    public int downloаdFile(String URL, String target) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        java.net.URL url = null;
        int counter = 0;
        try {
            url = new URL(URL);
            Path path = Paths.get(URL);
            String nameOfFile = target + String.valueOf(path.getFileName());
            System.out.println(nameOfFile);
            inputStream = new BufferedInputStream(url.openStream());
            outputStream = new BufferedOutputStream(new FileOutputStream(nameOfFile));
            double lenght = inputStream.available();
            double hundredth = lenght / 100;
            double temp = hundredth;
            int readByte;
            int progress = 0;
            while ((readByte = inputStream.read()) != -1) {
                outputStream.write(readByte);
                counter++;
                if (counter > temp) {
                    temp += hundredth;
                    progress++;
                    System.out.println(progress);
                }
                outputStream.flush();
            }
            inputStream.close();
            outputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return counter;
    }
}