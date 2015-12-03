package com.clouway.downloadagent;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.net.URI;
import java.nio.file.Path;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

/**
 * Created by clouway on 15-12-1.
 */
public class DownloadAgentTest {

    public class DownloadProgressSpectator implements ProgressSpectator {
        private long downloadPercent = 1;

        @Override
        public void progressUpdate(long percent) {
            assertEquals(percent, downloadPercent);
            System.out.println(downloadPercent + "%");
            downloadPercent++;
        }
    }

    File fileName;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @After
    public void clean(){
        folder.delete();
        assertEquals(fileName.exists(), false);
    }

    @Test
    public void happyPath() throws Exception {
        DownloadProgressSpectator downloadProgressSpectator = new DownloadProgressSpectator();
        DownloadAgent downloadAgent = new DownloadAgent(downloadProgressSpectator);
        URI uri1 = this.getClass().getResource("javalogo.jpg").toURI();
        fileName = folder.newFile("javalogo.jpg");
        downloadAgent.downloadFile(uri1, fileName);
    }
}
