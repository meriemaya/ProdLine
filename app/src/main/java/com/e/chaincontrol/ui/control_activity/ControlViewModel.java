package com.e.chaincontrol.ui.control_activity;

import android.content.Context;

import com.e.chaincontrol.data.DBHelper;
import com.e.chaincontrol.models.MachineModel;
import com.e.chaincontrol.ui.main_activity.Client;

public class ControlViewModel {
    private MachineModel machine;
    Client client;
    Context context;
    DBHelper db;

    public ControlViewModel(Context context){
        this.context=context;
        db=DBHelper.getInstance(context);
    }

    public void sendCommand(String command) {
        //pop up
        client.doInBackground(command, machine.getIP());
    }

    public MachineModel findMachine(int id){
        return db.getMachine(id);
    }
    public void setMachine(MachineModel machine){
        this.machine=machine;
        this.client=new Client(machine);
    }
    public MachineModel getMachine(){
        return this.machine;
    }
}