package co.realinventor.schoolbus.Parent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.realinventor.schoolbus.Common.Notification;
import co.realinventor.schoolbus.Common.NotificationAdapter;
import co.realinventor.schoolbus.Common.Student;
import co.realinventor.schoolbus.R;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NotificationViewActivity extends AppCompatActivity {
    private DatabaseReference ref;
    private RecyclerView recyclerView;
    private NotificationAdapter mAdapter;
    private List<Notification> notificationList = new ArrayList<Notification>();
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_view);

        ref = FirebaseDatabase.getInstance().getReference();

        progressBar = findViewById(R.id.progessBarParentNotification);

        mAdapter = new NotificationAdapter(notificationList);
        recyclerView = findViewById(R.id.parentNotificationRecyclerView);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        String phone = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

        Query rfidQuery = ref.child("student_list").orderByChild("contact").equalTo(phone);
        rfidQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    Student student = data.getValue(Student.class);
                    if(student != null){
                        getNotifications(student.getRfid());
                    }
                    else{
                        Toast.makeText(NotificationViewActivity.this, "No result found!", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void getNotifications(final String rfid) {
        Query rfidQuery = ref.child("notifications").orderByChild("rfid").equalTo(rfid);
        rfidQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    Notification notification = data.getValue(Notification.class);
                    if(notification != null){
                        notificationList.add(notification);
                    }
                    else{
                        Toast.makeText(NotificationViewActivity.this, "No result found!", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }
                mAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }
}
