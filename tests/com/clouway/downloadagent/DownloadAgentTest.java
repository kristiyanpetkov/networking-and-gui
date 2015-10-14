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
    private File output;
    private BufferedWriter out;

    @Before
    public void createTestData() {
        try {
            input = testFolder.newFile("test.txt");
            out = new BufferedWriter(new FileWriter(input));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void downloadTest(){
        try {
            out.write("wkgkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkksssssssssssssssssssssssssssssssssssssss;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String  abspath=new File("").getAbsolutePath();
        System.out.println(abspath);
        URI url = input.toURI();
        DownloadAgent agent = new DownloadAgent();
        assertThat(agent.downloаdFile(url.toString(), abspath), is(176));
    }

    @After
    public void cleanUp() {
        input.delete();
        assertThat(input.exists(), is(false));
    }
}
