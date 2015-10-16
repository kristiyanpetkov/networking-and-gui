package com.clouway.downloadagent;

import org.jmock.Expectations;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.jmock.auto.Mock;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class DownloadAgentTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    @Mock
    ProgressViewer progressViewer;

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
        DownloadAgent agent = new DownloadAgent(progressViewer);
        context.checking(new Expectations() {{
            exactly(100).of(progressViewer).progress(with(any(Integer.class)));
        }});
        assertThat(agent.downloаdFile(inputUrl, projectDirectory), is(100));
    }
}
