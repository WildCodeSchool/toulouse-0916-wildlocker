package fr.wildcodeschool.apprenti.lockthedoor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;




import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    // récupération de la Fire base
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();
    // Récupération de la table
    private DatabaseReference rootTable = FirebaseDatabase.getInstance().getReference().child("users");
    //écouté les donné de ma base
    private static final String TAG = "LTD-Main";
    private Intent intent;
    public FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    intent = new Intent(MainActivity.this,SecondActivity.class);
                    startActivity(intent);
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    intent = new Intent(MainActivity.this,AuthActivity.class);
                    startActivity(intent);
                }
                // ...
            }
        };
    }
}

