package com.e.chaincontrol.ui.main_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.e.chaincontrol.R;
import com.e.chaincontrol.data.DBHelper;
import com.e.chaincontrol.models.MachineModel;
import com.e.chaincontrol.ui.add_config.AddConfigActivity;
import com.e.chaincontrol.ui.control_activity.ControlActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnMachineClicked {

    public static DBHelper db;
    RecyclerView recyclerView;
    Button btnDisplay;
    Button btnAddConfig;
    TextView title ;
    MainActivityViewModel viewModel = new MainActivityViewModel(this);
    MyAdapter myAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();


    }

    public void initViews() {
       recyclerView = findViewById(R.id.list_machine);
        btnDisplay = findViewById(R.id.btn_all_machines);
        btnAddConfig = findViewById(R.id.btn_add_machine);
        title =findViewById(R.id.title);
        btnDisplay.setOnClickListener(this);
        btnAddConfig.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_all_machines:
                List<MachineModel> listMachine=viewModel.getMyMachines();
                if(listMachine == null){
                    Toast.makeText(this, "Please add config first!",
                            Toast.LENGTH_LONG).show();
                }
                myAdapter = new MyAdapter(listMachine, this);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                        DividerItemDecoration.VERTICAL);
                recyclerView.addItemDecoration(dividerItemDecoration);
                recyclerView.setAdapter(myAdapter);
                recyclerView.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_add_machine:
                Intent intent= new Intent(this, AddConfigActivity.class);
                startActivity(intent);


        }


    }

    @Override
    public void onMachineClicked(int position) {

        int tmpId= viewModel.getMachineId(position);
        Intent intent= new Intent(this, ControlActivity.class);
        intent.putExtra("ID",tmpId);
        startActivity(intent);


    }

    @Override
    protected void onResume() {
        super.onResume();
        if(myAdapter!=null){
            myAdapter.setMachines(viewModel.getMyMachines());
            myAdapter.notifyDataSetChanged();
        }


    }
}