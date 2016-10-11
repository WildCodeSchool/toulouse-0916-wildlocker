package fr.wildcodeschool.apprenti.lockthedoor;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * Created by Shooty31 on 23/09/2016.
 */

public class DoorActivity extends AppCompatActivity {

    public Button unlock;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser user;
    public DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();
    public DatabaseReference rootUsers =  FirebaseDatabase.getInstance().getReference().child("Lockthedor");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        unlock = (Button) findViewById(R.id.button2);
        mFirebaseAuth = FirebaseAuth.getInstance();
        user = mFirebaseAuth.getCurrentUser();
        if (user == null) {
            Intent intent = new Intent(this, AuthActivity.class);
            startActivity(intent);
            finish();
        }

        else{
            unlock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rootUsers.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            String Author = dataSnapshot.child("lieu").getValue().toString();
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
            });


        }






    }
}

