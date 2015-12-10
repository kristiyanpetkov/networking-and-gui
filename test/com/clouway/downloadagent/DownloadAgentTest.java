package com.clouway.downloadagent;


import com.google.common.io.ByteStreams;
import org.junit.Test;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Created by clouway on 15-12-1.
 */
public class DownloadAgentTest {

    public class DownloadProgressSpectator implements ProgressSpectator {
        int testPercent=0;
        @Override
        public void progressUpdate(long percent) {
            if(testPercent!=percent){
                System.out.println(percent + "%");
                testPercent++;
            }

        }
    }

    @Test
    public void happyPath() throws Exception {
        DownloadProgressSpectator downloadProgressSpectator = new DownloadProgressSpectator();
        DownloadAgent downloadAgent = new DownloadAgent(downloadProgressSpectator, "abvCopy.jpeg");
        URI uri1 = this.getClass().getResource("abv.jpeg").toURI();
        OutputStream out = new ByteArrayOutputStream();
        downloadAgent.downloadFile(uri1, out);
        InputStream in = new FileInputStream(downloadAgent.localFileName);
        byte data1[] = ByteStreams.toByteArray(in);
        URL url = uri1.toURL();
        URLConnection urlConnection = url.openConnection();
        byte data2[] = ByteStreams.toByteArray(urlConnection.getInputStream());
        assertArrayEquals(data1, data2);
    }
}
