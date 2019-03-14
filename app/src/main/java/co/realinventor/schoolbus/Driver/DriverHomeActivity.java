package co.realinventor.schoolbus.Driver;

import androidx.appcompat.app.AppCompatActivity;
import co.realinventor.schoolbus.Admin.ViewAttendenceActivity;
import co.realinventor.schoolbus.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DriverHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_home);
    }

    public void checkButtonClicked(View view){
        startActivity(new Intent(this, ViewAttendenceActivity.class).putExtra("usertype", "driver"));
    }
}
