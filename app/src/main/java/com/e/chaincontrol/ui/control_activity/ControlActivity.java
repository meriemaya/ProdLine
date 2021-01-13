package com.e.chaincontrol.ui.control_activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.e.chaincontrol.R;

public class ControlActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private TextView textNameMachine;
    private ImageView imgPlus, imgMinus;
    private Button btnStart, btnStop;
    private VideoView vdoStream;
    private SeekBar speedBar;
    private ControlViewModel controlViewModel=new ControlViewModel(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.control_activity);
        initViews();
        displayMachine();
        speedListeners();
        //setStreamVideo
        startListener(btnStart);
        stopListener(btnStop);


    }



    public void displayMachine(){
        Intent mIntent = getIntent();
        int ID = mIntent.getIntExtra("ID", 0);
        controlViewModel.setMachine(controlViewModel.findMachine(ID));
        textNameMachine.setText(controlViewModel.getMachine().getName()+": "+controlViewModel.getMachine().getIP());

    }

    public void initViews() {
        textNameMachine=findViewById(R.id.machine_name);
        imgMinus=findViewById(R.id.img_minus);
        imgPlus=findViewById(R.id.img_plus);
        btnStart=findViewById(R.id.btn_start);
        btnStop=findViewById(R.id.btn_stop);
        vdoStream=findViewById(R.id.vdo_stream);
        speedBar=findViewById(R.id.speed_bar);
        speedBar.setOnSeekBarChangeListener(this);
        speedBar.setMax(255);
        
        imgMinus.setEnabled(false);
        imgPlus.setEnabled(false);
        btnStop.setEnabled(false);
    }
    public void speedListeners(){
        imgPlus.setOnClickListener(new View.OnClickListener()   {
            @Override
            public void onClick(View v) {
                speedBar.setProgress((int)speedBar.getProgress() + 10);
                speedBar.setEnabled(false);
                controlViewModel.sendCommand("U");
            }});
        imgMinus.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                speedBar.setProgress((int)speedBar.getProgress() - 10);
                speedBar.setEnabled(false);
                controlViewModel.sendCommand("D");
            }});
    }
    public void startListener(View v){
        v.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popup();
                        imgMinus.setEnabled(true);
                        imgPlus.setEnabled(true);
                        btnStop.setEnabled(true);

                    }
                }
        );

    }
    public void popup(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Seconds");
        alert.setMessage("Enter Time");

        // Set an EditText view to get user input
        EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String time=input.getText().toString();
                controlViewModel.sendCommand("S "+time);
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton)
            {

                imgMinus.setEnabled(false);
                imgPlus.setEnabled(false);
                btnStop.setEnabled(false);
                dialog.dismiss();
            }
        });

        alert.show();
    }
    private void stopListener(Button v) {
        v.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        controlViewModel.sendCommand("Q");
                        imgMinus.setEnabled(false);
                        imgPlus.setEnabled(false);
                        btnStop.setEnabled(false);

                    }
                }
        );
    }



    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        //view model calling for client doInbackground
        Toast.makeText(this,"Speed up to "+ seekBar.getProgress(), Toast.LENGTH_SHORT);

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        controlViewModel.client.close();
    }
}
