package co.realinventor.schoolbus.Common;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ValidUsers {
    public String uid, contact, type;

    public ValidUsers() {
    }

    public ValidUsers(String uid, String contact, String type) {
        this.uid = uid;
        this.contact = contact;
        this.type = type;
    }

    public Task addUser(String contact, String type){
        this.contact = contact;
        this.type = type;

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        uid = ref.child("valid_users").push().getKey();

        return ref.child("valid_users").child(uid).setValue(this);
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
