package co.realinventor.schoolbus.Admin;

import androidx.appcompat.app.AppCompatActivity;
import co.realinventor.schoolbus.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdminHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
    }

    public void addStudentClicked(View view){
        startActivity(new Intent(this, AddStudentActivity.class));
    }

    public void studentListClicked(View view){
        startActivity(new Intent(this, ViewStudentActivity.class));
    }

    public void addDriverClicked(View view){
        startActivity(new Intent(this, AddDriverActivity.class));
    }

    public void driverListClicked(View view){
        startActivity(new Intent(this, ViewDriverActivity.class));
    }

    public void viewAttendanceClicked(View view){
        startActivity(new Intent(this, ViewAttendenceActivity.class));
    }
}
