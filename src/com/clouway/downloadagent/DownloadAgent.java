package com.clouway.downloadagent;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by clouway on 15-12-1.
 */
public class DownloadAgent {
    private ProgressSpectator progress;
    public final String localFileName;

    public DownloadAgent(ProgressSpectator progress, String localFileName) {
        this.progress = progress;
        this.localFileName = localFileName;
    }

    public void downloadFile(URI uri, OutputStream out) {
        try {
            URL url = uri.toURL();
            URLConnection connection = url.openConnection();
            connection.connect();
            long fileLength = connection.getContentLength();
            InputStream in = new BufferedInputStream(connection.getInputStream());
            out = new FileOutputStream(localFileName);
            int numberOfBytesRead;
            final byte buffer[] = new byte[2048];
            long total = 0;
            while ((numberOfBytesRead = in.read(buffer)) != -1) {
                total += numberOfBytesRead;
                long percent = ((int) ((total * 100) / fileLength));
                out.write(buffer, 0, numberOfBytesRead);
                progress.progressUpdate(percent);
            }
            out.close();
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }
}
