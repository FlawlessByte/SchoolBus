package co.realinventor.schoolbus.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.realinventor.schoolbus.Common.Attendence;
import co.realinventor.schoolbus.Common.AttendenceAdapter;
import co.realinventor.schoolbus.Common.Notification;
import co.realinventor.schoolbus.Common.Student;
import co.realinventor.schoolbus.Common.StudentAttendence;
import co.realinventor.schoolbus.Common.ValidUsers;
import co.realinventor.schoolbus.R;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendenceDayActivity extends AppCompatActivity {
    private DatabaseReference ref;
    private Attendence attendence;
    private List<String> rfidList = new ArrayList<String>();
    private List<StudentAttendence> attendenceList = new ArrayList<StudentAttendence>();
    private Map<String, Student> rfidStudentMap = new HashMap<>();
    private Map<String, Attendence> rfidAttendenceMap = new HashMap<>();
    private AttendenceAdapter mAdapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private MaterialButton button;
    private String USER_TYPE = "none";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence_day);

        progressBar = findViewById(R.id.progessBarAttendence);
        button = findViewById(R.id.sendNotificationButton);

        try {
            USER_TYPE = getIntent().getStringExtra("usertype");
        }
        catch (Exception e){
            USER_TYPE = "none";
        }
        if(USER_TYPE == null){
            USER_TYPE = "";
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotifications();
            }
        });
        if(!(USER_TYPE.equals("driver")))
            button.setVisibility(View.GONE);

        mAdapter = new AttendenceAdapter(attendenceList);
        recyclerView = findViewById(R.id.attendenceRecyclerView);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        ref = FirebaseDatabase.getInstance().getReference();

        rfidList.add("18003D29767A");
        rfidList.add("18003DA223A4");

        for(final String s: rfidList){
            Query rfidQuery = ref.child("student_list").orderByChild("rfid").equalTo(s);
            rfidQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot data: dataSnapshot.getChildren()) {
                        Student student = data.getValue(Student.class);
                        Log.d("Student Name", student.getName());
                        if (student != null) {
                            rfidStudentMap.put(s, student);
                        } else {
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }



        final String date = getIntent().getStringExtra("date");

        ref.child("attandanceList").child(date).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    attendence = data.getValue(Attendence.class);
                    rfidAttendenceMap.put(data.getKey(), attendence);
                }
                prepareData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Error",databaseError.getMessage());
                Toast.makeText(AttendenceDayActivity.this, "Some error!", Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void prepareData(){

        for(String rfid : rfidList){
            boolean am,pm;
            StudentAttendence studentAttendence = new StudentAttendence();
            studentAttendence.setRfid(rfid);
            studentAttendence.setName(rfidStudentMap.get(rfid).getName());
            try{
                switch ((int)rfidAttendenceMap.get(rfid).am){
                    case 1:
                        am = true;
                        break;
                    default:
                        am = false;
                        break;
                }
            }
            catch (Exception e){
                am = false;
            }
            try{
                switch ((int)rfidAttendenceMap.get(rfid).pm){
                    case 1:
                        pm = true;
                        break;
                    default:
                        pm = false;
                        break;
                }
            }
            catch (Exception e){
                pm = false;
            }
            studentAttendence.setAm(am);
            studentAttendence.setPm(pm);

            attendenceList.add(studentAttendence);

        }

        progressBar.setVisibility(View.GONE);
        mAdapter.notifyDataSetChanged();

    }


    private void sendNotifications(){
        for(StudentAttendence studentAttendence: attendenceList){
            String msg = "";
            if(studentAttendence.am && studentAttendence.pm){
                //No notification needed
            }
            else if(!studentAttendence.am && !studentAttendence.pm){
                //Fully absent
                msg = getResources().getString(R.string.attendence_fully_absent);
            }
            else{
                if(!studentAttendence.am){
                    msg = getResources().getString(R.string.attendence_am_not_ok);
                }
                if(!studentAttendence.pm){
                    msg = getResources().getString(R.string.attendence_pm_not_ok);
                }
            }
            if(!msg.equals("")){
                new Notification().makeNotification(msg, studentAttendence.rfid);
                Toast.makeText(this, "Notifications are being sent!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
