package com.clouway.multiclientserver;

/**
 * Created by clouway on 16-1-12.
 */
public interface ConnectionStateChangeListener {
    void onClose(ClientConnection clientConnection);
}
