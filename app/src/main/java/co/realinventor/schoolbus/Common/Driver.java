package co.realinventor.schoolbus.Common;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Driver {
    public String uid, name, driver_id, busNo, contact;

    public Driver() {
    }

    public Task addDriver(String driver_id, String name, String busNo, String contact){
        this.name = name;
        this.driver_id = driver_id;
        this.busNo = busNo;
        this.contact = contact;

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        uid = ref.child("driver_list").push().getKey();

        return ref.child("driver_list").child(uid).setValue(this);
    }

    public String getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getBusNo() {
        return busNo;
    }

    public void setBusNo(String busNo) {
        this.busNo = busNo;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
