package com.clouway.downloadagent;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.*;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class DownloadAgent {
    private ProgressViewer progress;

    public DownloadAgent(ProgressViewer progress) {
        this.progress = progress;
    }

    public int downloаdFile(URI downloadUrl, Path target) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        URL url = null;
        int counter = 0;
        try {
            url = downloadUrl.toURL();
            String fileName = url.getFile();
            String targetLocation = target + fileName.substring(fileName.lastIndexOf('/'));
            inputStream = new BufferedInputStream(url.openStream());
            outputStream = new BufferedOutputStream(new FileOutputStream(targetLocation));
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
                    progress.progress(percent);
                }
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


}