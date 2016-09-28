package fr.wildcodeschool.apprenti.lockthedoor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
public class AuthActivity extends AppCompatActivity{

 //   public final static String EXTRA_LOGIN = "user_login"; //On déclare les deux données de log */
   // public final static String EXTRA_PASSWORD = "user_password";
    private static final String TAG = "LTD-Auth";

    final EditText editLogin = (EditText) findViewById(R.id.name); //on créer les zones ou l'on doit entrer les logs */
    final EditText editPass = (EditText) findViewById(R.id.passe);
    final Button loginButton = (Button) findViewById(R.id.button3); //création du bouton */
    private Intent intent;
    private FirebaseAuth mAuth;
    private String login;
    private String pass;
// ...



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_activity);

        mAuth = FirebaseAuth.getInstance();
        String user = mAuth.getCurrentUser().getUid();


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                login = editLogin.getText().toString();
                pass = editPass.getText().toString();
                //idem pour le pass

                //Demande au service Auth de firebase si l'email et le mot de passe match avec un utilisateur
                mAuth.signInWithEmailAndPassword(login, pass)
                .addOnCompleteListener(AuthActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                        }
                        else {
                            //email & pass OK
                            intent = new Intent(AuthActivity.this, MainActivity.class); // création de l'intent qui permet de lier les deux activités */
                            startActivity(intent); // ici on lance l'autre activité */
                        }


                        // ...
                    }
                });




            }
        });
    }
}
