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
public class AuthActivity extends AppCompatActivity{

 //   public final static String EXTRA_LOGIN = "user_login"; //On déclare les deux données de log */
   // public final static String EXTRA_PASSWORD = "user_password";
    private static final String TAG = "LTD-Auth";

    public EditText editLogin;
    public EditText editPass;
    public Button loginButton;
    private Intent intent;
    private FirebaseAuth mAuth;
    public String login;
    public String pass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_activity);

        editLogin = (EditText) findViewById(R.id.name); //on créer les zones ou l'on doit entrer les logs */
        editPass = (EditText) findViewById(R.id.passe);
        loginButton = (Button) findViewById(R.id.button3); //création du bouton */

        mAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login = editLogin.getText().toString();
                pass = editPass.getText().toString();
                //idem pour le pass
                if(login.isEmpty() || pass.isEmpty()){
                    Log.i("MABITE", "Les champs sont vide !!");
                }else {
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
                                        Toast myToast = Toast.makeText(AuthActivity.this, "t'es pas connecté Conard !", Toast.LENGTH_LONG);
                                        myToast.setGravity(Gravity.CENTER, 0, 0);
                                        myToast.show();
                                    }
                                    else {
                                        //email & pass OK
                                        intent = new Intent(AuthActivity.this, MainActivity.class); // création de l'intent qui permet de lier les deux activités */
                                        startActivity(intent); // ici on lance l'autre activité */
                                        Toast toToast = Toast.makeText(AuthActivity.this, "Connection Réussi tu va pouvoir entrée !", Toast.LENGTH_LONG);
                                        toToast.setGravity(Gravity.CENTER, 0, 0);
                                        toToast.show();

                                    }
                                    // ...
                                }
                            });
                }

            }
        });
    }
}
