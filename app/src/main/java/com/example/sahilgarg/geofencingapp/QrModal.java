
package com.example.sahilgarg.geofencingapp;

public class QrModal {

    public String email , name ,phone, timestamp,deviceId;

    public QrModal(String email, String name, String phone, String timestamp, String deviceId) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.timestamp = timestamp;
        this.deviceId = deviceId;
    }

    public QrModal(String email, String name, String phone) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.timestamp="time";
        this.deviceId="909089";

    }

    public QrModal() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
