package com.example.hemant.sharedlock;

/**
 * Created by Hemant on 27-03-2017.
 */

public class Lock {
    String lock_no;
    String isbn;

    public Lock(String lock_no,String isbn){
        this.lock_no=lock_no;
        this.isbn=isbn;
    }

    public String getLock_no() {
        return lock_no;
    }

    public void setLock_no(String lock_no) {
        this.lock_no = lock_no;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
