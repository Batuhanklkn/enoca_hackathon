package org.ClientManager;


public class Client {
    private int id;
    private String mail;
    private String password;

    private String address;
    private String city;
    private String district;

    //Constructor but not overrided
    public Client(String mail, String password,String address, String city, String district){
        this.mail = mail;
        this.password = password;
        this.address = address;
        this.city = city;
        this.district = district;
    }
    //a function manipulate data
    public void updateInfo(String mail, String password,String address, String city, String district){
        this.mail = mail;
        this.password = password;
        this.address = address;
        this.city = city;
        this.district = district;
    }
    //setters and getters
    public void setId(int id){this.id = id;}
    public int getId(){return this.id;}
    public String getMail(){return this.mail;}
    public String getPassword(){return this.password;}
    public String getAddress(){return this.address;}
    public String getCity(){return this.city;}
    public String getDistrict(){return this.district;}
    public String toString(){return this.id + " " + this.mail + " " + address;}
}

