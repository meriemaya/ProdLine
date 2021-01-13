package com.e.chaincontrol.ui.main_activity;

import android.content.Context;

import com.e.chaincontrol.data.DBHelper;
import com.e.chaincontrol.models.MachineModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivityViewModel {

    private List<MachineModel> myMachines;
    Context context;
    DBHelper db;



    public MainActivityViewModel(Context ctx) {
        context=ctx;
        db=DBHelper.getInstance(context);


    }

    public List<MachineModel> getMyMachines() {
        // get machines from local base
        if(this.db.getMachinesCount() == 0) return null ;

        myMachines = this.db.getAllMachines();

        return myMachines;

    }


    public List<String> getHostNames() {
        List<MachineModel> tmp = getMyMachines();
        List<String> result = new ArrayList<>();
        for (MachineModel machine : tmp) {
            result.add(machine.getName());
        }
        return result;
    }


    public int getMachineId(int position){
        return myMachines.get(position).getId();
    }


}