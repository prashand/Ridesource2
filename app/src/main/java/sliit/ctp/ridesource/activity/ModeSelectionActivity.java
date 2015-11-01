package sliit.ctp.ridesource.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import sliit.ctp.ridesource.R;
import sliit.ctp.ridesource.endpoint.UpdateUserAsyncTask;
import sliit.ctp.ridesource.utility.SessionManager;
import sliit.ctp.rsserver.rsApi.model.User;

public class ModeSelectionActivity extends AppCompatActivity {

    SessionManager sessionManager = SessionManager.createManager(getApplicationContext());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_selection);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mode_selection, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onDriverModeClicked(View view){
        User user = sessionManager.getUserObject().setIsDriver(true);
        new UpdateUserAsyncTask().execute(user);
        startActivity(new Intent(new Intent(this, DriverModeActivity.class)));
    }

    public void onPassengerModeClicked(View view){
        User user = sessionManager.getUserObject().setIsDriver(false);
        new UpdateUserAsyncTask().execute(user);
        startActivity(new Intent(new Intent(this, PickupLocationActivity.class)));
    }
}
