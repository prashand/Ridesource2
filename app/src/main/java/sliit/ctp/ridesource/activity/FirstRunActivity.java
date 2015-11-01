package sliit.ctp.ridesource.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import sliit.ctp.ridesource.R;
import sliit.ctp.ridesource.endpoint.AddUserAsyncTask;
import sliit.ctp.ridesource.endpoint.GetUserAsyncTask;
import sliit.ctp.ridesource.utility.SessionManager;
import sliit.ctp.rsserver.rsApi.RsApi;
import sliit.ctp.rsserver.rsApi.model.User;

public class FirstRunActivity extends AppCompatActivity{

    SessionManager sessionmgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_run);

        Button loginButton = (Button)findViewById(R.id.logInButton);
        final EditText userText = (EditText)findViewById(R.id.existingUserName);
        final EditText passText = (EditText)findViewById(R.id.existingPassword);

        sessionmgr = SessionManager.createManager(getApplicationContext());

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    User user = new GetUserAsyncTask().execute(userText.getText().toString()).get();

                    if (user != null) {
                        if (user.getPassword().equals(passText.getText().toString())) {
                            sessionmgr.createLoginSession(user);
                            startActivity(new Intent(new Intent(FirstRunActivity.this, ModeSelectionActivity.class)));
                        }
                    }

                } catch (InterruptedException e) {
                    Log.d("error",e.getMessage());
                } catch (ExecutionException e) {
                    Log.d("error", e.getMessage());
                }
            }
        });

        Button signUpButton = (Button)findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText userNameText = (EditText)findViewById(R.id.newUsernameText);
                User user = new User();
               // user.setUsername(userNameText.getText().toString());
                new AddUserAsyncTask().execute(user);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_first_run, menu);
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
}
