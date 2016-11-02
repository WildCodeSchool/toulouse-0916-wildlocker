/*
 * Copyright (c) 2016.
 * Created by Alban DELATTRE, Edwin RIBEIRO, Arthur DEVAUX, Lucas SUCHET.
 */

package fr.wildcodeschool.apprenti.lockthedoor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by apprenti on 28/09/16.
 */
public class AuthActivity extends AppCompatActivity {

    // On déclare nos variables !
    private static final String TAG = "LTD-Auth";
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference rootBase = FirebaseDatabase.getInstance().getReference().child("Lockthedor");
    String author;
    public EditText editLogin;
    public EditText editPass;
    public Button loginButton;
    private Intent intent;
    private FirebaseUser user;
    public String login;
    public String pass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_activity);
        // on récupère nos utilisateurs stockés dans "Auth" de Firebase
        mFirebaseAuth = FirebaseAuth.getInstance();
        user = mFirebaseAuth.getCurrentUser();
        // on dit que si la variable user et différente de "nul" on passe à la 2nd activity
        if (user != null) {

            AuthActivity.this.rootBase.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String authore = dataSnapshot.child("status").getValue().toString();
                    if (!authore.equals("Admin")) {
                        // User is signed in
                        Log.d(TAG, "User already logged: " + user.getUid());
                        intent = new Intent(AuthActivity.this, DoorActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Log.d(TAG, "User already logged: " + user.getUid());
                        intent = new Intent(AuthActivity.this, AdminActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
        loginButton = (Button) findViewById(R.id.log_button);
        editLogin = (EditText) findViewById(R.id.name);
        editPass = (EditText) findViewById(R.id.passe);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // on rentre dans l'exécution du bouton

                login = editLogin.getText().toString(); // on tranforme le text en string
                pass = editPass.getText().toString();
                // on vérifie si les champs sont vide
                if (login.isEmpty() || pass.isEmpty()) {
                    Log.i("Lockthedoor", "Les champs sont vides !!");
                }
                // s'il ne sont pas vide alors on se connecte
                else {
                    // Demande au service Auth de Firebase si l'email et le mot de passe correspond avec un utilisateur
                    mFirebaseAuth.signInWithEmailAndPassword(login, pass)
                            .addOnCompleteListener(AuthActivity.this, new OnCompleteListener<AuthResult>() {

                                @Override
                                public void onComplete(final @NonNull Task<AuthResult> task) {
                                    Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                                    user = task.getResult().getUser();
                                    AuthActivity.this.rootBase.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            author = dataSnapshot.child("status").getValue().toString();

                                            // If sign in fails, display a message to the user. If sign in succeeds
                                            // the auth state listener will be notified and logic to handle the
                                            // signed in user can be handled in the listener.
                                            // si la "task" est différente de "isSuccessful"
                                            if (!task.isSuccessful()) {
                                                Log.w(TAG, "signInWithEmail:failed", task.getException());
                                                Toast myToast = Toast.makeText(AuthActivity.this, "E-mail ou Mot de Passe incorrect !", Toast.LENGTH_LONG);
                                                myToast.setGravity(Gravity.CENTER, 0, 0);
                                                myToast.show();
                                            }
                                            // sinon on se connecte
                                            else {
                                                // si author et différent de Admin nous redirige vers la page user
                                                if (!author.equals("Admin")) {
                                                    intent = new Intent(AuthActivity.this, DoorActivity.class);
                                                    // création de l'intent qui permet de lier les deux activités
                                                    startActivity(intent); // ici on lance l'autre activité
                                                    Toast toToast = Toast.makeText(AuthActivity.this, "Connexion Réussie !", Toast.LENGTH_LONG);
                                                    toToast.setGravity(Gravity.CENTER, 0, 0);
                                                    toToast.show();
                                                    finish();
                                                }
                                                //sinon vers la page Admin
                                                else {
                                                    intent = new Intent(AuthActivity.this, AdminActivity.class);
                                                    startActivity(intent);
                                                    Toast toToast = Toast.makeText(AuthActivity.this, "Connexion Réussie !", Toast.LENGTH_LONG);
                                                    toToast.setGravity(Gravity.CENTER, 0, 0);
                                                    toToast.show();
                                                    finish();
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            Log.i(TAG,"Meh !! ");
                                        }
                                    });

                                }

                            });
                }

            }
        });
    }
}
