package com.e.chaincontrol.ui.control_activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.lifecycle.MutableLiveData;

import com.e.chaincontrol.data.DBHelper;
import com.e.chaincontrol.models.MachineModel;

public class ControlViewModel {
    private MachineModel machine;
    MutableLiveData<Bitmap> bitmap =new MutableLiveData<>(null);
    Client client;
    Context context;
    DBHelper db;

    public ControlViewModel(Context context){
        this.context=context;
        db=DBHelper.getInstance(context);
    }

    public boolean sendCommand(String command) {
        //pop up
       return client.doInBackground(command, machine.getIP());
    }

    public MachineModel findMachine(int id){
        return db.getMachine(id);
    }
    public void setMachine(MachineModel machine){
        this.machine=machine;
        this.client=new Client(machine,bitmap,context);
    }
    public MachineModel getMachine(){
        return this.machine;
    }
}
