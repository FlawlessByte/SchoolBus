package co.realinventor.schoolbus.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import co.realinventor.schoolbus.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewAttendenceActivity extends AppCompatActivity {
    private ListView attendenceListView;
    private DatabaseReference ref;
    private ArrayAdapter adapter;
    private String[] dateList;
    private String USER_TYPE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendence);

        attendenceListView = findViewById(R.id.attendenceListView);

        try{
            USER_TYPE = getIntent().getStringExtra("usertype");
        }
        catch (Exception e){
            USER_TYPE = "none";
        }

        ref = FirebaseDatabase.getInstance().getReference();

        ref.child("attandanceList").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int count = 0;
                dateList = new String[(int)dataSnapshot.getChildrenCount()];

                for(DataSnapshot data : dataSnapshot.getChildren()){
                    dateList[count] = data.getKey();
                    count++;
                }

                adapter = new ArrayAdapter<String>(ViewAttendenceActivity.this,R.layout.date_list_item, dateList);
                attendenceListView.setAdapter(adapter);
                attendenceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(ViewAttendenceActivity.this, AttendenceDayActivity.class);
                        intent.putExtra("date",dateList[position]);
                        intent.putExtra("usertype", USER_TYPE);
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
