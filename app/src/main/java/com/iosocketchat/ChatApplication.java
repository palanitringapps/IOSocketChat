package com.iosocketchat;

import android.app.Application;

import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URISyntaxException;


public class ChatApplication extends Application {

    private Socket mSocket;

    {
        try {
            mSocket = IO.socket("http://108.59.82.80:8080/");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        return mSocket;
    }
}
