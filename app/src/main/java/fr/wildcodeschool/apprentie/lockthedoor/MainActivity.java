package fr.wildcodeschool.apprentie.lockthedoor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_LOGIN = "user_login"; //On déclare les deux données de log */
    public final static String EXTRA_PASSWORD = "user_password";

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
    }
}

