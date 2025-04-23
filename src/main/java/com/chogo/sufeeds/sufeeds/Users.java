package com.chogo.sufeeds.sufeeds;

//Corban Chogo, ICS 1.2 Group B, 165558, 13/11/2024

public class Users {
    private int user_id;
    private String student_regno;
    private String fname;
    private String lname;
    private String password;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getStudent_regno() {
        return student_regno;
    }

    public void setStudent_regno(String student_regno) {
        this.student_regno = student_regno;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
