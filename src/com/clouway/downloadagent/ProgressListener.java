package com.clouway.downloadagent;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public interface ProgressListener {
    void onProgressUpdated(int progressPercent);
}
