package co.realinventor.schoolbus.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import co.realinventor.schoolbus.Common.Student;
import co.realinventor.schoolbus.Common.ValidUsers;
import co.realinventor.schoolbus.R;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddStudentActivity extends AppCompatActivity {
    private TextInputEditText textInputStudentName,textInputStudentRegNo, textInputStudentAddress,
            textInputStudentStop, textInputStudentBus,textInputStudetPhone, textInputStudentRFID;
    private DatabaseReference ref;
    private String name, regNo, address, stop, bus, phone, rfid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        textInputStudentName = findViewById(R.id.textInputStudentName);
        textInputStudentRegNo = findViewById(R.id.textInputStudentRegNo);
        textInputStudentAddress = findViewById(R.id.textInputStudentAddress);
        textInputStudentStop = findViewById(R.id.textInputStudentStop);
        textInputStudentBus = findViewById(R.id.textInputStudentBus);
        textInputStudetPhone = findViewById(R.id.textInputStudetPhone);
        textInputStudentRFID = findViewById(R.id.textInputStudentRFID);

        ref = FirebaseDatabase.getInstance().getReference();
    }

    public void studentRegisterButtonClicked(View view){
        if(inputsOk()){
            Task task = new Student().addStudent(name, regNo, address, stop, bus, phone, rfid);
            new ValidUsers().addUser(phone, "parent");
            task.addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if(task.isSuccessful()){
                        Toast.makeText(AddStudentActivity.this, "Added student data successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else{
                        Toast.makeText(AddStudentActivity.this, "Some error occurred!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private boolean inputsOk(){
        boolean status = true;
        name = textInputStudentName.getText().toString();
        regNo = textInputStudentRegNo.getText().toString();
        address = textInputStudentAddress.getText().toString();
        stop = textInputStudentStop.getText().toString();
        bus = textInputStudentBus.getText().toString();
        phone = textInputStudetPhone.getText().toString();
        rfid = textInputStudentRFID.getText().toString();
        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(regNo) || TextUtils.isEmpty(address) || TextUtils.isEmpty(stop) ||
                TextUtils.isEmpty(bus) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(rfid)){
            status = false;
        }

        return status;
    }
}
