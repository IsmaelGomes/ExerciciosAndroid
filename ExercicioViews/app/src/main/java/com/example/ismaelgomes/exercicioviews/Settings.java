package com.example.ismaelgomes.exercicioviews;


import java.io.Serializable;

public class Settings implements Serializable{

    private boolean privateAccount;
    private boolean subjectField;
    private boolean accountActive;
    private int brightness;
    private boolean nightMode;

    public Settings(){

    }


    public boolean isPrivateAccount() {
        return privateAccount;
    }

    public void setPrivateAccount(boolean privateAccount) {
        this.privateAccount = privateAccount;
    }

    public boolean isSubjectField() {
        return subjectField;
    }

    public void setSubjectField(boolean subjectField) {
        this.subjectField = subjectField;
    }

    public boolean isAccountActive() {
        return accountActive;
    }

    public void setAccountActive(boolean accountActive) {
        this.accountActive = accountActive;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public boolean isNightMode() {
        return nightMode;
    }

    public void setNightMode(boolean nightMode) {
        this.nightMode = nightMode;
    }
}
