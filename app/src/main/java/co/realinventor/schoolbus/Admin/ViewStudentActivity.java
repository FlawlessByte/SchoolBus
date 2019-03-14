package co.realinventor.schoolbus.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import co.realinventor.schoolbus.Common.Student;
import co.realinventor.schoolbus.R;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ViewStudentActivity extends AppCompatActivity {
    private TextInputEditText textInputStudentSearchRFID;
    private ProgressBar progressBar;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student);

        textInputStudentSearchRFID = findViewById(R.id.textInputStudentSearchRFID);
        progressBar = findViewById(R.id.studentProgressBar);

        ref = FirebaseDatabase.getInstance().getReference();

    }

    public void studentViewButtonClicked(View view){
        progressBar.setVisibility(View.VISIBLE);
        String rfid = textInputStudentSearchRFID.getText().toString();
        if(TextUtils.isEmpty(rfid)){
            Toast.makeText(this, "The RFID field is empty!", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.INVISIBLE);
        }
        else{

            Query rfidQuery = ref.child("student_list").orderByChild("rfid").equalTo(rfid);
            rfidQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Student student = dataSnapshot.getValue(Student.class);
                    if(student != null){
                        StudentViewDialog.currentStudent = student;
                        StudentViewDialog dialog = new StudentViewDialog();
                        dialog.show(getSupportFragmentManager().beginTransaction(), StudentViewDialog.TAG);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                    else{
                        Toast.makeText(ViewStudentActivity.this, "No result found!", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });

        }
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        progressBar.setVisibility(View.INVISIBLE);
        super.onResume();
    }
}
