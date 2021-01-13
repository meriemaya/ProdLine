package com.e.chaincontrol.ui.add_config;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.e.chaincontrol.R;

public class AddConfigActivity extends AppCompatActivity implements View.OnClickListener {

    EditText eTextNameConfig, eTextIpConfig, eTextPort;
    Button btnSave;
    ConfigViewModel configViewModel= new ConfigViewModel(this);
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_config);
        initViews();


    }
    public void initViews(){
        eTextNameConfig=findViewById(R.id.e_text_hostname);
        eTextIpConfig=findViewById(R.id.e_text_ip);
        btnSave=findViewById(R.id.btn_save_config);
        eTextPort=findViewById(R.id.e_text_Port);
        btnSave.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        String editName=eTextNameConfig.getText().toString();
        String editIp=eTextIpConfig.getText().toString();
        String editPort=eTextPort.getText().toString();
        if ((! editIp.equals("")) && (!editName.equals("")) && (!editPort.equals(""))){
            configViewModel.AddMachine(editName,editIp,Integer.parseInt(editPort));
            Toast.makeText(this,"Added Successfully",Toast.LENGTH_LONG).show();
            finish();

        }else{
            Toast.makeText(this,"All fields are required",Toast.LENGTH_LONG).show();
        }


    }
}
