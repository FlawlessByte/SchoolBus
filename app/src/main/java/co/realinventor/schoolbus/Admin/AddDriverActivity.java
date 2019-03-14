package co.realinventor.schoolbus.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import co.realinventor.schoolbus.Common.Driver;
import co.realinventor.schoolbus.Common.ValidUsers;
import co.realinventor.schoolbus.R;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddDriverActivity extends AppCompatActivity {
    private TextInputEditText textInputDriverName,textInputDriverId, textInputDriverBusNo,textInputDriverPhone;
    private DatabaseReference ref;
    private String name, id, busNo, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_driver);

        textInputDriverName = findViewById(R.id.textInputDriverName);
        textInputDriverId = findViewById(R.id.textInputDriverId);
        textInputDriverBusNo = findViewById(R.id.textInputDriverBusNo);
        textInputDriverPhone = findViewById(R.id.textInputDriverPhone);

        ref = FirebaseDatabase.getInstance().getReference();
    }

    public void driverRegisterButtonClicked(View view){
        if(inputsOk()){
            Task task = new Driver().addDriver(id, name, busNo, phone);
            new ValidUsers().addUser(phone, "driver");
            task.addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if(task.isSuccessful()){
                        Toast.makeText(AddDriverActivity.this, "Added driver data successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else{
                        Toast.makeText(AddDriverActivity.this, "Some error occurred!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    private boolean inputsOk(){
        boolean status = true;
        name = textInputDriverName.getText().toString();
        id = textInputDriverId.getText().toString();
        busNo = textInputDriverBusNo.getText().toString();
        phone = textInputDriverPhone.getText().toString();
        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(id) || TextUtils.isEmpty(busNo) || TextUtils.isEmpty(phone)){
            status = false;
        }

        return status;
    }
}
