package com.clouway.downloadagent;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.net.URI;

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
        URI uri = new URI("file:/home/clouway/workspaces/idea/networking-and-gui/javalogo.jpg");
        fileName = folder.newFile("javalogo.jpg");
        downloadAgent.downloadFile(uri, fileName);
    }
}
