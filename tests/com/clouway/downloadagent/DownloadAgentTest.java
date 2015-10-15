package com.clouway.downloadagent;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.net.URI;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class DownloadAgentTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    private File input;
    private BufferedWriter bufferedWriter;

    @Before
    public void createTestData() {
        try {
            input = testFolder.newFile("test.txt");
            bufferedWriter = new BufferedWriter(new FileWriter(input));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void cleanUp() {
        input.delete();
        assertThat(input.exists(), is(false));
    }

    @Test
    public void happyPath() {
        String current = "";
        try {
            bufferedWriter.write("0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789");
            bufferedWriter.close();
            current = new File(".").getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        URI inputUrl = input.toURI();
        DownloadAgent agent = new DownloadAgent();
        assertThat(agent.downloаdFile(inputUrl.toString(), current), is(100));
    }

    @Test
    public void incorrectUrl() {
        DownloadAgent agent = new DownloadAgent();
        assertThat(agent.downloаdFile("", ""), is(0));
    }
}
