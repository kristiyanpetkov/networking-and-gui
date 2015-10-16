package com.clouway.downloadagent;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class DownloadAgentTest {
    public class DownloadProgressListener implements ProgressListener {
        int lastUpdatedProgress = 1;

        @Override
        public void onProgressUpdated(int progressPercent) {
            assertThat(progressPercent, is(equalTo(lastUpdatedProgress)));
            lastUpdatedProgress++;
        }
    }

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private File input;
    private String current = "";
    private URI inputUrl;
    private Path projectDirectory;

    @Before
    public void createTestData() {
        try {
            input = testFolder.newFile("test.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(input));
            bufferedWriter.write("0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789");
            bufferedWriter.close();
            current = new File(".").getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        inputUrl = input.toURI();
        projectDirectory = Paths.get(current).getParent();
    }

    @After
    public void cleanUp() {
        input.delete();
        assertThat(input.exists(), is(false));
    }

    @Test
    public void happyPath() {
        DownloadProgressListener downloadProgressListener = new DownloadProgressListener();
        DownloadAgent agent = new DownloadAgent(downloadProgressListener);
        assertThat(agent.downloаdFile(inputUrl, projectDirectory), is(100));
    }
}
