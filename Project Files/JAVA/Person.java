package com.example.hemant.sharedlock;

/**
 * Created by Hemant on 17-03-2017.
 */
public class Person {

    String id;
    String name;
    String email;
    String password;
    String contact;
    String lock_no;

    public Person(String id, String name, String email,String password,String contact,String lock_no){
        this.id=id;
        this.name=name;
        this.email=email;
        this.password=password;
        this.contact=contact;
        this.lock_no=lock_no;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getLock_no() {
        return lock_no;
    }

    public void setLock_no(String lock_no) {
        this.lock_no = lock_no;
    }

}
