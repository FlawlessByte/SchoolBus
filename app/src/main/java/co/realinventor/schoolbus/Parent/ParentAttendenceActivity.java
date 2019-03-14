package co.realinventor.schoolbus.Parent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.realinventor.schoolbus.Common.Attendence;
import co.realinventor.schoolbus.Common.Student;
import co.realinventor.schoolbus.R;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ParentAttendenceActivity extends AppCompatActivity {
    private Student student;
    private DatabaseReference ref;
    private List<ParentAttendence> attendenceList = new ArrayList<ParentAttendence>();
    private ParentAttendenceAdapter mAdapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_attendence);

        student = (Student) getIntent().getSerializableExtra("student");
        ref = FirebaseDatabase.getInstance().getReference();

        progressBar = findViewById(R.id.progessBarParentAttendence);

        mAdapter = new ParentAttendenceAdapter(attendenceList);
        recyclerView = findViewById(R.id.parentAttendenceRecyclerView);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);


        ref.child("attandanceList").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    for (DataSnapshot rfObject: data.getChildren()){
                        Attendence attendence = rfObject.getValue(Attendence.class);
                        if(rfObject.getKey().equals(student.getRfid())){
                            boolean am,pm;
                            try{
                                switch ((int)attendence.am){
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
                                switch ((int)attendence.pm){
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



                            ParentAttendence parentAttendence = new ParentAttendence(data.getKey(),student.getName(),am, pm);
                            attendenceList.add(parentAttendence);
                        }
                    }
                }
                //Notify adapter
                progressBar.setVisibility(View.GONE);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
