package de.vogella.android.sqlite.hervoice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Profil extends AppCompatActivity {


    static String id;
    static String Status;
    static String login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        login = MainActivity.Login.getText().toString();
        TextView Identifiant = findViewById(R.id.identifiant);
        TextView Txtview_Status = findViewById(R.id.status);
        ImageView Avatar = findViewById(R.id.icon);
        //ImageView Avatar = findViewById(R.id.avatar);

        id = MainActivity.BDD_Utilisateurs.getId(login); // Récupérer l'ID du compte connecté
        Status = MainActivity.BDD_Utilisateurs.getStatus(login);
        Identifiant.setText(login);
        Txtview_Status.setText(Status);

        Avatar.setImageResource(Integer.parseInt(MainActivity.BDD_Utilisateurs.getAvatar(login)));


    }


    public void ChercherProfile(View view) {
     Intent c = new Intent(Profil.this, ChercherProfil.class);
     startActivity(c);
    }

    public void ModifierProfil(View view) {
        Intent d = new Intent(Profil.this, ModifierProfil.class);
        startActivity(d);
    }

}