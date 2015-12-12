package com.clouway.downloadagent;


import com.google.common.io.ByteStreams;
import org.junit.Test;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertArrayEquals;


/**
 * Created by clouway on 15-12-1.
 */
public class DownloadAgentTest {

    public class DownloadProgressSpectator implements ProgressSpectator {
        long testPercent = 0;

        @Override
        public void progressUpdate(long percent) {
            testPercent=percent;
        }

        public void assertLastProgressUpdateIs(long finalPercent){
            assertThat(finalPercent,is(equalTo(testPercent)));
        }
    }

    @Test
    public void happyPath() throws Exception {
        DownloadProgressSpectator downloadProgressSpectator = new DownloadProgressSpectator();
        DownloadAgent downloadAgent = new DownloadAgent(downloadProgressSpectator);
        URI uri1 = this.getClass().getResource("abv.jpeg").toURI();
        ByteArrayOutputStream imageOutputStream = new ByteArrayOutputStream();
        downloadAgent.downloadFile(uri1, imageOutputStream);
        byte[] expectedContent = ByteStreams.toByteArray(getClass().getResourceAsStream("abv.jpeg"));
        assertArrayEquals(expectedContent, imageOutputStream.toByteArray());
        downloadProgressSpectator.assertLastProgressUpdateIs(100);
    }
}

