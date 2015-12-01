package com.clouway.downloadagent;

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
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void happyPath() throws Exception {
        DownloadAgent downloadAgent = new DownloadAgent();
        URI uri = new URI("http://www.javaguru.co/wp-content/uploads/2015/02/java.jpg");
        File fileName = folder.newFile("snimka.jpg");
        downloadAgent.downloadFile(uri, fileName);
        assertEquals(100, downloadAgent.progress);
    }
}
