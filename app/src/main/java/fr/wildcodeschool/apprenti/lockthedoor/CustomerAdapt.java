package fr.wildcodeschool.apprenti.lockthedoor;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by apprenti on 26/10/16.
 */
public class CustomerAdapt extends ArrayAdapter<School> {

    private final Activity context;
    private final ArrayList<School> arraySchool;
    final String TAG = "LTD-url";

    public CustomerAdapt(Activity context, ArrayList arraySchool) {
        super(context, R.layout.item_school, arraySchool);
        this.context = context;
        this.arraySchool = arraySchool;
    }
    // Création de la view qui va remplir l'Arraylist<Schools>
    public View getView(int position, View View, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.item_school, null );
        //Récupération de l'IP et du nom de l'école correspondant
        final School s = getItem(position);
        TextView nameSchool = (TextView) rowView.findViewById(R.id.name);
        ToggleButton linkRasp = (ToggleButton) rowView.findViewById(R.id.linkRasp);
        nameSchool.setText(s.getName());
        // le ToggleButton permet de changer les paramètres en fonction de son êtat
        linkRasp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //On récupère l'url de la Raspberry actuelle
                String url = s.getUrl();
                Log.i(TAG, url);
                if (isChecked){
                    // si le bouton et "Checked" alors la commande Background_get envoie ces 2 requètes
                    new Background_get().execute(url,"/?led1=0");
                    new Background_get().execute(url,"/?led2=1");
                }else {
                    //sinon Background_get envoie ces 2 requètes
                    new Background_get().execute(url,"/?led1=1");
                    new Background_get().execute(url,"/?led2=0");
                }
            }
        });
        // et on envoie la romView pour changer l'état de l'Arraylist
        return rowView;
    }
    // Envoie de la Requète http définit dans le Togglebutton linkRasp
    private class Background_get extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0] + params[1]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String inputLine;
                Log.i(TAG, params[0] + params[1]);

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