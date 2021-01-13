package com.e.chaincontrol.ui.main_activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.e.chaincontrol.models.MachineModel;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client  {


    private Socket soc;
    private DataOutputStream out;
    private MachineModel machine;

    public Client(MachineModel machine ){
        this.machine=machine;
        createSocket();
    }

    void createSocket (){
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    soc = new Socket(machine.getIP(), 7800);
                    Log.d("Connected to: ", machine.getName());
                    OutputStream toServer = soc.getOutputStream();
                    out = new DataOutputStream(toServer);
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        });

        thread.start();

    }





    public Void doInBackground(String... params) {
        String command=params[0];
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {


                    if(command.equals("S") ) {
                        try {
                            out.writeUTF(command);
                            out.flush();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else if(command == "U"){

                        try {
                            out.writeUTF(command);
                            out.flush();

                        }catch (IOException e) {
                            e.printStackTrace();
                        }

                    }else{
                        try {
                            out.writeUTF(command);
                            out.flush();

                        }catch (IOException e) {
                            e.printStackTrace();

                        }
                    }
            }
        });

        thread.start();


        return null;
    }


    public void close() {

        try {
            out.close();
            soc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
