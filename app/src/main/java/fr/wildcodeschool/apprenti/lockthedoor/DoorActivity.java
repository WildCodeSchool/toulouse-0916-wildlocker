package fr.wildcodeschool.apprenti.lockthedoor;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by apprenti on 23/09/2016.
 */

public class DoorActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser user;
    private Intent intent;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mFirebaseAuth = FirebaseAuth.getInstance();
        user = mFirebaseAuth.getCurrentUser();

        //Police
        TextView title = (TextView) findViewById(R.id.textView);
        TextView wcs = (TextView) findViewById(R.id.textView2);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"Stroke-Bold.otf");
        title.setTypeface(typeface);
        wcs.setTypeface(typeface);


        if (user == null) {
            Intent intent = new Intent(this, AuthActivity.class);
            startActivity(intent);
            finish();
        }
      /*************************************************/
     /*     Define a Toggle Button for switch Led     */
    /*************************************************/

        ToggleButton led = (ToggleButton) findViewById(R.id.Led);
        Button logoutbutton = (Button) findViewById(R.id.logout_button);


        /***************************************************************************/
       /*               Set a CheckChange listener for the button                 */
      /* If checked led2 is ON  and led1 is off / else led2 is OFF and led1 is ON*/
     /*          led1 is RED (oor closed) / led2 is GREEN (door open)           */
     /**************************************************************************/

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

    /*************************/
    /* Disconnecting Button */
   /************************/

        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //logOut firebase
                FirebaseAuth.getInstance().signOut();
                // Redirection vers AuthActivity
                intent = new Intent(DoorActivity.this, AuthActivity.class); // lien entre la door activity et l'auth */
                startActivity(intent); // retour à l'AuthActivity */
                Toast toToast = Toast.makeText(DoorActivity.this, "Déconnecté", Toast.LENGTH_LONG);
                toToast.setGravity(Gravity.CENTER, 0, 0);
                toToast.show();
                finish();            }
        });

    }

        /*****************************************************/
       /*  This is a background process for connecting      */
      /*   to the Raspberry Pi server and sending          */
     /*     the GET request with the added data           */
    /*****************************************************/

    private class Background_get extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                /* Change the IP to the RaspberryPi's IP */
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


