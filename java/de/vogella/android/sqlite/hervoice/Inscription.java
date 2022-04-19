package de.vogella.android.sqlite.hervoice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;

import android.widget.Spinner;
import android.widget.Toast;


public class Inscription extends AppCompatActivity {

    String login_inscription;
    String Pass_inscription;
    String Email_inscription;
    String Status = "";
    int avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);


    }

    public void Inscription(View view) {

        EditText Login_ins = findViewById(R.id.login_inscription); // EditText login
        EditText Pass_ins = findViewById(R.id.password_inscription); //EditText Password
        EditText Email_ins = findViewById(R.id.email_inscription); //EditText Mail

        // On prend les informations écrites dans les EditText, qu'on retranscrit en string :
        login_inscription = Login_ins.getText().toString();
        Pass_inscription = Pass_ins.getText().toString();
        Email_inscription = Email_ins.getText().toString();


        // Test de validation de l'inscription :
        if ((Status.trim().length() > 0 ) && (Pass_inscription.trim().length()) > 5 && (Email_inscription.contains("@")) && (!login_inscription.equals(MainActivity.BDD_Utilisateurs.getLogin(login_inscription)))) {

            // Si tout est ok :
            MainActivity.BDD_Utilisateurs.insertContact(login_inscription, Pass_inscription, Status, "Aucune localisation", "Aucune description", Email_inscription, "Aucun tag sélectionné", avatar); // BDD Class "BDUtilisateurs" , Fonction "insertContact"
            Intent c = new Intent(Inscription.this, MainActivity.class); // Intent vers MainActivity
            startActivity(c);

            Toast.makeText(Inscription.this, "Inscription validée", Toast.LENGTH_LONG).show(); // Message de validation

           // si un élément est faux, message personnalisé :
        } else if (login_inscription.equals(MainActivity.BDD_Utilisateurs.getLogin(login_inscription))) {  // Vérifie que le nom d'utilisateur est disponible
            Toast.makeText(Inscription.this, "Utilisateur déjà existant", Toast.LENGTH_LONG).show();
        } else if (Status.trim().length() == 0) {    // Vérifie que le status est bien coché
            Toast.makeText(Inscription.this, "Catégorie non valide", Toast.LENGTH_LONG).show();
        } else if (Pass_inscription.trim().length() <= 5 ) {   // Vérifier que le pass est supérieur à 5 lettres
            Toast.makeText(Inscription.this, "Pass inférieur à 6 caractères", Toast.LENGTH_LONG).show();
            Pass_ins.setBackgroundColor(Color.RED);
        } else if (!Email_inscription.contains("@")) {  // Vérifie que le mail contient un @
            Toast.makeText(Inscription.this, "Email invalide", Toast.LENGTH_LONG).show();
        }

    }

    // Choix du status

    public void Status (View view) {

        RadioButton button_Media = findViewById(R.id.button_Media);

        if (button_Media.isChecked()) { // Si le bouton média est coché, c'est un média
            Status = "Media";
            avatar = (R.drawable.radio);
        } else { // Sinon c'est une intervenante
            Status = "Intervenante";
            avatar = R.drawable.profilf1;
        }

    }



}