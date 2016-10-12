package fr.wildcodeschool.apprenti.lockthedoor;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by Shooty31 on 23/09/2016.
 */

public class DoorActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser user;
    public DatabaseReference rootUsers =  FirebaseDatabase.getInstance().getReference().child("Lockthedor");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mFirebaseAuth = FirebaseAuth.getInstance();
        user = mFirebaseAuth.getCurrentUser();

        /*rootUsers.child(user.getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User me = dataSnapshot.child("lieu").getValue(User.class);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        }); */
        if (user == null) {
            Intent intent = new Intent(this, AuthActivity.class);
            startActivity(intent);
            finish();
        }
        /********************************/
         /*    Define all the buttons    */
        /********************************/
        ToggleButton led = (ToggleButton) findViewById(R.id.Led);



        /*******************************************************/
         /*  Set an onclick/onchange listener for every button  */
        /*******************************************************/
        led.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    new Background_get().execute("led2=1");
                    new Background_get().execute("led1=0");
                } else {
                    new Background_get().execute("led2=0");
                    new Background_get().execute("led1=1");
                }
            }
        });

    }
    // Connection Raspberry
    private class Background_get extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                /* Change the IP to the IP you set in the arduino sketch */
                URL url = new URL("http://192.168.1.30/?" + params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null)
                    result.append(inputLine).append("\n");

                in.close();
                connection.disconnect();
                return result.toString();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}


