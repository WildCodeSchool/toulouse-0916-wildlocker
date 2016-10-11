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

/**
 * Created by apprenti on 28/09/16.
 */
public class AuthActivity extends AppCompatActivity {

// On déclare nos variable !
    private static final String TAG = "LTD-Auth";
    public FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mFirebaseAuth;

    public EditText editLogin;
    public EditText editPass;
    public Button loginButton;
    private Intent intent;

    public String login;
    public String pass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_activity);
        // on récupére nos utilisateur stocké dans "Auth" de firebase
        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mFirebaseAuth.getCurrentUser();

        // on dit que si la variable user et différante de "nul" on passe a la 2nd activity

       if (user != null) {
            // User is signed in
            Log.d(TAG, "User already logged: " + user.getUid());
            intent = new Intent(AuthActivity.this, DoorActivity.class);
            startActivity(intent);
            finish();
        }
        // sinon on execute nos code pour le login

        loginButton = (Button) findViewById(R.id.log_button);
        editLogin = (EditText) findViewById(R.id.name);
        editPass = (EditText) findViewById(R.id.passe);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // on rentre dans l'execution du bouton

                login = editLogin.getText().toString(); // on tranforme le text en string
                pass = editPass.getText().toString();
                //idem pour le pass
                if (login.isEmpty() || pass.isEmpty()) {
                    Log.i("Lockthedoor", "Les champs sont vide !!");
                } else {
                    //Demande au service Auth de firebase si l'email et le mot de passe match avec un utilisateur
                    mFirebaseAuth.signInWithEmailAndPassword(login, pass)
                            .addOnCompleteListener(AuthActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    if (!task.isSuccessful()) {
                                        Log.w(TAG, "signInWithEmail:failed", task.getException());
                                        Toast myToast = Toast.makeText(AuthActivity.this, "t'es pas connecté  !", Toast.LENGTH_LONG);
                                        myToast.setGravity(Gravity.CENTER, 0, 0);
                                        myToast.show();
                                    }
                                    else {
                                        //email & pass OK

                                        //SI j'ai l'id User ( Firebase Auth ) dans ma table users
                                        //ALORS tout va bien et on charge l'activité suivante
                                        //SINON Envoyer vers un Formulaire ( Activité ) qui demandera l'autorisation et le status de l'utilsateur
                                        //Une fois le formulaire bien rempli créer l'utilisateur en base de dpnnées.
                                        intent = new Intent(AuthActivity.this, DoorActivity.class); // création de l'intent qui permet de lier les deux activités */
                                        startActivity(intent); // ici on lance l'autre activité */
                                        Toast toToast = Toast.makeText(AuthActivity.this, "Connection Réussi tu va pouvoir entrée !", Toast.LENGTH_LONG);
                                        toToast.setGravity(Gravity.CENTER, 0, 0);
                                        toToast.show();
                                        finish();
                                    }

                                }

                            });
                }

            }
        });

    }
}
