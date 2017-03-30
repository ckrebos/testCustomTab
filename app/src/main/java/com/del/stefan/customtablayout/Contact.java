package com.del.stefan.customtablayout;

/**
 * Created by stefan on 3/30/17.
 */

public class Contact {
    String name, status;

    public Contact(String name, String status) {
        this.name = name;
        this.status = status;
    }

    public Contact() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
