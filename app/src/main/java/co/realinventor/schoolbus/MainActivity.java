package co.realinventor.schoolbus;

import androidx.appcompat.app.AppCompatActivity;
import co.realinventor.schoolbus.Admin.AdminHomeActivity;
import co.realinventor.schoolbus.Parent.ParentLoginActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void driverModeSelected(View view){

    }
    public void parentModeSelected(View view){
        startActivity(new Intent(this, ParentLoginActivity.class));
    }
    public void adminModeSelected(View view){
        startActivity(new Intent(this, AdminHomeActivity.class));
    }
}
