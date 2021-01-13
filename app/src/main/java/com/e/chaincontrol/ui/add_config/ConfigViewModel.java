package com.e.chaincontrol.ui.add_config;

import android.content.Context;

import com.e.chaincontrol.data.DBHelper;
import com.e.chaincontrol.models.MachineModel;
import com.e.chaincontrol.ui.main_activity.MainActivity;

public class ConfigViewModel {
    Context context;
    DBHelper db;

    public ConfigViewModel(Context context){
        this.context=context;
        db=DBHelper.getInstance(context);
    }

    public void AddMachine(String name, String ip, int port){

        this.db.addMachine(new MachineModel(name,ip,port));

    }
    public boolean checkIp(String ip){
        //regex
        return true;
    }

}
