/*
 * Copyright (c) 2016.
 * Created by Alban DELATTRE, Edwin RIBEIRO, Arthur DEVAUX, Lucas SUCHET.
 */

package fr.wildcodeschool.apprenti.lockthedoor;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    private Intent intent;
    private DatabaseReference dataroot = FirebaseDatabase.getInstance().getReference();
    private Button logoutbutton;
    private ArrayList<School> schools;
    private Button crateuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //On assigne la police "Stroke-Bold" au Titre
        TextView title = (TextView) findViewById(R.id.titleAdmin);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"Stroke-Bold.otf");
        title.setTypeface(typeface);
        crateuser = (Button) findViewById(R.id.User);
        // on instancie le button
        logoutbutton = (Button) findViewById(R.id.logout_button);
        // on instancie l'Arraylist
        this.schools = new ArrayList<School>();
        //on récupère les Données de Firebase stockées dans la table "Schools"
        dataroot.child("schools").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // on envoie les données à notre class Objet
                AdminActivity.this.schools.add(dataSnapshot.getValue(School.class));
                // on met les données dans l'Arraylist
                ListAdapter listAdap = new CustomerAdapt(AdminActivity.this, schools);
                // on crée notre listView
                final ListView mListeView = (ListView) findViewById(R.id.list);
                //qui ce remplis grâce à notre Adaptateur
                mListeView.setAdapter(listAdap);
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

      /************************/
     /* Disconnecting Button */
    /************************/


        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                intent = new Intent(AdminActivity.this, AuthActivity.class);
                startActivity(intent);
                Toast toToast = Toast.makeText(AdminActivity.this, "Déconnecté", Toast.LENGTH_LONG);
                toToast.setGravity(Gravity.CENTER, 0, 0);
                toToast.show();
                finish();
            }
        });
        crateuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(AdminActivity.this, Create_User.class);
                startActivity(intent);
                finish();
            }
        });
    }


}


