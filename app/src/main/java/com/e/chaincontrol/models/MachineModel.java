package com.e.chaincontrol.models;

import java.util.UUID;

public class MachineModel {

    private int idMachine;
    private String nameMachine ;
    private String ipAddress ;
    private int port;

    public MachineModel(){

    }
    public MachineModel(String Name , String ipAddress, int port ){
        this.idMachine=0;
        this.nameMachine=Name;
        this.ipAddress=ipAddress;
        this.port=port;
    }
    public MachineModel(int id, String Name , String ipAddress, int port ){
        this.idMachine=id;
        this.nameMachine=Name;
        this.ipAddress=ipAddress;
        this.port=port;
    }

    public String getName() {
        return nameMachine;
    }

    public String getIP() {
        return this.ipAddress;
    }
    public void setIdMachine(int id){
        this.idMachine=id;
    }
    public void setNameMachine(String name){
        this.nameMachine=name;
    }
    public void setIPMachine(String ip){
        this.ipAddress=ip;
    }

    public int getId() {
        return this.idMachine;
    }
    public int getPort(){
        return this.port;
    }
    public void setPort(int port){
        this.port=port;
    }
}
