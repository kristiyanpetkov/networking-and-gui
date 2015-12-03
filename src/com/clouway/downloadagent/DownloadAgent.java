package com.clouway.downloadagent;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by clouway on 15-12-1.
 */
public class DownloadAgent {
    ProgressSpectator progress;

    public DownloadAgent(ProgressSpectator progress) {
        this.progress = progress;
    }

    public void downloadFile(URI uri, File fileName) {
        try {
            URL url = uri.toURL();
            URLConnection connection = url.openConnection();
            connection.connect();
            long fileLength = connection.getContentLength();
            InputStream in = new BufferedInputStream(connection.getInputStream());
            FileOutputStream out = new FileOutputStream(fileName);
            int readByte;
            long size = fileLength / 100;
            long count = 1;
            long percent;
            while ((readByte = in.read()) != -1) {
                out.write(readByte);
                if ((count % size) == 0) {
                    percent = count / size;
                    progress.progressUpdate(percent);
                }
                count++;
            }
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }
}
