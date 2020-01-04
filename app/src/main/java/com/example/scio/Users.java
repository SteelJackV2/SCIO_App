package com.example.scio;

import android.widget.ListView;

import com.firebase.ui.auth.data.model.User;

import java.util.ArrayList;
import java.util.List;

public class Users {
    private String display;
    private int chalenges;
    private int amount;
    private ArrayList<String> pending;
    private ArrayList<String> complted;

    public Users(){

    }

    public Users(String display, int chalenges, int amount) {
        this.display = display;
        this.chalenges = chalenges;
        this.amount = amount;
    }

     /*
    public Users(String display, int chalenges, int amount, ArrayList<String> pending, ArrayList<String> complted) {
        this.display = display;
        this.chalenges = chalenges;
        this.amount = amount;
        this.pending = pending;
        this.complted = complted;
    }*/

    public String getDisplay() {
        return display;
    }

    public String getChalenges() {
        return Integer.toString(chalenges);
    }
    public void addChallenge(int c){
        chalenges+=c;
    }

    public String getAmount() {
        return Integer.toString(amount);
    }
    public void addAmound(int a){
        amount+=a;
    }

    public ArrayList<String> getPending() {
        return pending;
    }

    public void addPending(String pending) {
        this.pending.add(pending);
    }

    public ArrayList<String> getComplted() {
        return complted;
    }

    public void setComplted(String complted) {
        this.complted.add(complted);
    }
}
