package co.realinventor.schoolbus.Common;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Notification {
    public String unique_id, senderName, msg, time, date, rfid;

    public Notification() {
    }

    public Notification(String unique_id, String senderName, String msg, String time, String date, String rfid) {
        this.unique_id = unique_id;
        this.senderName = senderName;
        this.msg = msg;
        this.time = time;
        this.date = date;
        this.rfid = rfid;
    }

    public Task makeNotification(String msg, String rfid){
        this.msg = msg;
        this.rfid = rfid;
        senderName = "Driver";
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        this.date = df.format(Calendar.getInstance().getTime());
        df = new SimpleDateFormat("HH:mm:ss");
        this.time = df.format(Calendar.getInstance().getTime());

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        unique_id = ref.child("notifications").push().getKey();

        return ref.child("notifications").child(unique_id).setValue(this);
    }

    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRfid() {
        return rfid;
    }

    public void setRfid(String rfid) {
        this.rfid = rfid;
    }
}
