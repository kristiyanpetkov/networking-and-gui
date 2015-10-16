package com.clouway.downloadagent;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public interface ProgressListener {
    int onProgressUpdated(int progressPercent);
}
