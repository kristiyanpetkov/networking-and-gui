package com.clouway.downloadagent;

import com.google.common.io.ByteStreams;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by clouway on 15-12-1.
 */
public class DownloadAgent {
    private ProgressSpectator progress;

    public DownloadAgent(ProgressSpectator progress) {
        this.progress = progress;
    }

    public void downloadFile(URI uri, OutputStream out) {
        try {
            URL url = uri.toURL();
            URLConnection connection = url.openConnection();
            connection.connect();
            long fileLength = connection.getContentLength();
            InputStream in = new BufferedInputStream(connection.getInputStream());
            int numberOfBytesRead;
            final byte buffer[] = new byte[2048];
            long total = 0;
            long tempPercent = 0;
            while ((numberOfBytesRead = in.read(buffer)) != -1) {
                total += numberOfBytesRead;
                long percent = ((int) ((total * 100) / fileLength));
                out.write(buffer, 0, numberOfBytesRead);
                if (percent == tempPercent) {
                    progress.progressUpdate(percent);
                    tempPercent++;
                }
            }
            out.close();
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }
}
