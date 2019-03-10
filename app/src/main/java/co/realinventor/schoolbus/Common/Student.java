package co.realinventor.schoolbus.Common;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Student {
    public String uid, name, regNo, address, stop, busNo, contact, rfid;

    public Student() {
    }

    public Student(String uid, String name, String regNo, String address, String stop, String busNo, String contact, String rfid) {
        this.uid = uid;
        this.name = name;
        this.regNo = regNo;
        this.address = address;
        this.stop = stop;
        this.busNo = busNo;
        this.contact = contact;
        this.rfid = rfid;
    }

    public Task addStudent(String name, String regNo, String address, String stop, String busNo, String contact, String  rfid){
        this.name = name;
        this.regNo = regNo;
        this.address = address;
        this.stop = stop;
        this.busNo = busNo;
        this.contact = contact;
        this.rfid = rfid;

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        uid = ref.child("student_list").push().getKey();

        return ref.child("student_list").child(uid).setValue(this);
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

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStop() {
        return stop;
    }

    public void setStop(String stop) {
        this.stop = stop;
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

    public String getRfid() {
        return rfid;
    }

    public void setRfid(String rfid) {
        this.rfid = rfid;
    }
}
