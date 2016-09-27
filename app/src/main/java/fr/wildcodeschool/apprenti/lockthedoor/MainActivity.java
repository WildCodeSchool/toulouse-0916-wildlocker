package fr.wildcodeschool.apprenti.lockthedoor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_LOGIN = "user_login"; //On déclare les deux données de log */
    public final static String EXTRA_PASSWORD = "user_password";

    // récupération de la Fire base
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();
    // Récupération de la table
    private DatabaseReference rootTable = FirebaseDatabase.getInstance().getReference().child("users");
    //écouté les donné de ma base

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_activity);

        final EditText login = (EditText) findViewById(R.id.editText); //on créer les zones ou l'on doit entrer les logs */
        final EditText pass = (EditText) findViewById(R.id.editText2);
        final Button loginButton = (Button) findViewById(R.id.button3); //création du bouton */
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class); // création de l'intent qui permet de lier les deux activités */
                intent.putExtra(EXTRA_LOGIN, login.getText().toString());
                intent.putExtra(EXTRA_PASSWORD, pass.getText().toString()); // c'est ici qu'on réécupère et associe les données a l'intent */
                startActivity(intent); // ici on lance l'autre activité */

            }
        });
        FirebaseDatabase.getInstance().getReference().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

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
        });
    }
}

