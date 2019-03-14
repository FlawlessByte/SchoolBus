package co.realinventor.schoolbus.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import co.realinventor.schoolbus.R;

import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendence);

        attendenceListView = findViewById(R.id.attendenceListView);

        ref = FirebaseDatabase.getInstance().getReference();

        ref.child("attandanceList").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int count = 0;
                String[] dateList = new String[(int)dataSnapshot.getChildrenCount()];

                for(DataSnapshot data : dataSnapshot.getChildren()){
                    dateList[count] = data.getKey();
                    count++;
                }

                adapter = new ArrayAdapter<String>(ViewAttendenceActivity.this,R.layout.date_list_item, dateList);
                attendenceListView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
