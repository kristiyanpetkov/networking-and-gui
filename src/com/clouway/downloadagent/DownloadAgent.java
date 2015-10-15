package com.clouway.downloadagent;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;
import java.nio.file.*;

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
            Path path = Paths.get(URL).getFileName();
            Path projectDirectory = Paths.get(target);
            String fileName = projectDirectory.getParent() + "/" + String.valueOf(path.toString());
            inputStream = new BufferedInputStream(url.openStream());
            outputStream = new BufferedOutputStream(new FileOutputStream(fileName));
            double length = inputStream.available();
            double hundredth = length / 100;
            double temp = hundredth;
            int readByte;
            int percent = 0;
            while ((readByte = inputStream.read()) != -1) {
                outputStream.write(readByte);
                counter++;
                if (counter >= temp) {
                    temp += hundredth;
                    percent++;
                    progress(percent);
                }
                outputStream.flush();
            }
            inputStream.close();
            outputStream.close();
        } catch (MalformedURLException e) {
            return 0;
        } catch (IOException e) {
            return 0;
        }
        return counter;
    }

    public void progress(int progressPercent){
        System.out.println(progressPercent+"%");
    }
}