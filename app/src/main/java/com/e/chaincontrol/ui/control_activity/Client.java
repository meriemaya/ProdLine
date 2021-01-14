package com.e.chaincontrol.ui.control_activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import androidx.lifecycle.MutableLiveData;

import com.e.chaincontrol.R;
import com.e.chaincontrol.models.MachineModel;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

public class Client  {


    private Socket soc;
    private DataOutputStream out;
    private DataInputStream in ;
    private MachineModel machine;
    private boolean connected = false ;
    Context context ;
    MutableLiveData<Bitmap> image ;

    public Client(MachineModel machine , MutableLiveData<Bitmap>  im , Context con ){
        this.machine=machine;
        image=im ;
        context=con ;
        createSocket();



    }

    void createSocket (){
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    soc = new Socket(machine.getIP(), 7800);
                    Log.d("Connected to: ", machine.getName());
                    connected=true;
                    InputStream fromServer=soc.getInputStream();
                    OutputStream toServer = soc.getOutputStream();
                    out = new DataOutputStream(toServer);
                    in = new DataInputStream(fromServer);
                   // receive();
                }catch(IOException e){
                    e.printStackTrace();

                }
            }
        });

        thread.start();

    }

    public void receive(){
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {

                while (true){
                      byte[] sizeAr = new byte[8];
                    try {
                       if (in.available() > 0) {
                           in.read(sizeAr);
                           int size = ByteBuffer.wrap(sizeAr).getInt();
                           Log.d("HHHH", String.valueOf(size));
                           byte[] imageArray = new byte[size];
                           in.read(imageArray);
                           Bitmap bmp = BitmapFactory.decodeByteArray(imageArray, 0, imageArray.length);
                           image.postValue(bmp);
                       }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }



                 /*     image.setImageBitmap(Bitmap.createScaledBitmap(bmp2, image.getWidth(), image.getHeight(), false));
                        image.invalidate();*/

                  }

            }
        });

        thread.start();

    }





    public boolean doInBackground(String... params) {
        String command=params[0];
        if(connected == false) return false ;
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


        return true;
    }


    public void close() {

        try {
            if(out!=null){


            out.close();
            soc.close();}
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

/*   while (true){
                      byte[] sizeAr = new byte[4];
                         in.read(sizeAr);
                            int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();
                            byte[] imageAr = new byte[size];
                            in.read(imageAr);
Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.test);
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                      bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
    byte[] byteArray = stream.toByteArray();

    Bitmap bmp2 = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                      image.postValue(bmp2);

                try {
        Thread.sleep(5000);

        bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.line);
        stream = new ByteArrayOutputStream();

        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byteArray=null;
        byteArray = stream.toByteArray();

        Bitmap  bmp3 = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        image.postValue(bmp3);

    } catch (InterruptedException e) {
        e.printStackTrace();
    }


     }*/
