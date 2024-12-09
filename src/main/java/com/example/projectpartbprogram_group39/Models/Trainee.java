package com.example.projectpartbprogram_group39.Models;

public class Trainee {
    private String username;
    private int age;
    private String gender;
    private String phoneNo;
    private String email;
    private double height;
    private double weight;
    private String password;


    public Trainee(String username, int age, String gender, String phoneNo, String email, double height, double weight, String password) {
        this.username = username;
        this.age = age;
        this.gender = gender;
        this.phoneNo = phoneNo;
        this.email = email;
        this.height = height;
        this.weight = weight;
        this.password = password;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return username + "/"  + age + "/" + gender + "/" + phoneNo + "/" + email + "/" + height
                + "/" + weight + "/" + password;
    }

    public static Trainee splitString(String line){
        String[] parts = line.split("/");

        if(parts.length != 8){
            throw new IllegalArgumentException("Invalid number of input");
        }

        String username = parts[0];
        int age = Integer.parseInt(parts[1]);
        String gender = parts[2];
        String phoneNo = parts[3];
        String email = parts[4];
        double height = Double.parseDouble(parts[5]);
        double weight = Double.parseDouble(parts[6]);
        String password = parts[7];

        return new Trainee(username, age, gender, phoneNo, email, height, weight, password);
    }
}
