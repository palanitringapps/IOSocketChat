package com.iosocketchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity extends AppCompatActivity {

    private Socket mSocket;
    private Boolean isConnected = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ChatApplication app = (ChatApplication) getApplication();
        mSocket = app.getSocket();
        mSocket.connect();
        mSocket.on(Socket.EVENT_CONNECT, onConnect);
        mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.on("new message", onNewMessage);
        mSocket.connect();
        Button bt = (Button) findViewById(R.id.test);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSend("hello....");
            }
        });

    }

    private void attemptSend(String test) {
        try {

            JSONObject object = new JSONObject();
            object.put("userId", "ramya.k@tringapps.com");
            object.put("channelToken", "z8j9htC7vzYvQwMKyVSBUZj2vSfz3yeC");
            object.put("socketId", mSocket.id());
            mSocket.emit("add user", object);
            Log.i("bvsavdhjs", "" + mSocket.id());
        } catch (JSONException e) {

        }
        Log.i("dsjhfgsdjv", "Test test");
    }

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!isConnected) {
                        if (null != "Test")
                            mSocket.emit("add user", "Test");
                        Toast.makeText(getApplicationContext(),
                                "connect", Toast.LENGTH_LONG).show();
                        isConnected = true;
                    }
                }
            });
        }
    };

    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i("jhgh", "diconnected");
                    isConnected = false;
                    Toast.makeText(getApplicationContext(),
                            "disconnect", Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e("", "Error connecting");
                    Toast.makeText(getApplicationContext(),
                            "error_connect", Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String username;
                    String message;
                    try {
                        username = data.getString("username");
                        message = data.getString("message");
                        Log.i("hgsafdjsh","gasdfhgsaf"+message);
                    } catch (JSONException e) {
                        Log.e("", e.getMessage());
                        return;
                    }

                    /*removeTyping(username);
                    addMessage(username, message);*/
                }
            });
        }
    };
}
