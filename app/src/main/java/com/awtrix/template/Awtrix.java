package com.awtrix.template;

import android.content.Intent;
import android.media.MediaScannerConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;


public class Awtrix {

    public static TcpClient mTcpClient;
    private String packageName = "";

    public void init(String packageName){
        new ConnectTask().execute("");
        this.packageName = packageName;

        JSONObject sendJson = new JSONObject();

        try {
            sendJson.put("app",packageName);
            mTcpClient.sendMessage(sendJson.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("Senden", sendJson.toString());

        //key=app -> value= packetName (com.awtrix.template)
        //key=cmd (comand)
    }

    public void init(){
        new ConnectTask().execute("");
        JSONObject sendJson = new JSONObject();
        try {
            sendJson.put("app",packageName);
            mTcpClient.sendMessage(sendJson.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("Senden", sendJson.toString());
    }

    public void sendToServer(String messageType, String message){
        if(!mTcpClient.getSocketIsConnected()){
            Log.d("Socket", "is closed...");
            this.init();
        }
        JSONObject sendJson = new JSONObject();
        try {
            sendJson.put("app",this.packageName);
            sendJson.put(messageType,message);
            mTcpClient.sendMessage(sendJson.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
