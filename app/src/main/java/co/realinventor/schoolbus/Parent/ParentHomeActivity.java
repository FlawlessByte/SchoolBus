package co.realinventor.schoolbus.Parent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import co.realinventor.schoolbus.Common.Student;
import co.realinventor.schoolbus.R;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ParentHomeActivity extends AppCompatActivity {
    private String phone;
    private DatabaseReference ref;
    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_home);

        try {
            phone = getIntent().getStringExtra("phone");
        }
        catch (Exception e){
            phone = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        }


        ref = FirebaseDatabase.getInstance().getReference();

        Query rfidQuery = ref.child("student_list").orderByChild("contact").equalTo(phone);
        rfidQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()) {
                    student = data.getValue(Student.class);
                    Log.d("Student Name", student.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void attendenceSelected(View view ){
        Intent intent = new Intent(this, ParentAttendenceActivity.class);
        intent.putExtra("student", student);
        startActivity(intent);
    }

    public void notificationSelected(View view ){
        startActivity(new Intent(this, NotificationViewActivity.class));
    }
}
