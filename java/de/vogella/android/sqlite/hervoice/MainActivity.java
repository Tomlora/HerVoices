package de.vogella.android.sqlite.hervoice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static EditText Login;
    EditText Password;
    TextView Nb_inscrits;

    static public DBUtilisateurs BDD_Utilisateurs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Login = findViewById(R.id.login); // EditText login
        Password = findViewById(R.id.password); // EditText Password
        Nb_inscrits = findViewById(R.id.nb_inscrits); // Textview Nombre d'inscrits

        BDD_Utilisateurs = new DBUtilisateurs(this); // BDD à partir de notre propre Class "DBUtilisateurs"

        Nb_inscrits.setText("Il y a " + BDD_Utilisateurs.numberOfRows() + " membres inscrits"); // Nombre d'inscrits

        Log.d("Profilf1",String.valueOf(R.drawable.profilf1)); // 2131165337
        Log.d("Profilf2",String.valueOf(R.drawable.profilf2)); // 2131165338
        Log.d("Profilh1",String.valueOf(R.drawable.profilh1)); // 2131165339
        Log.d("Profilh2",String.valueOf(R.drawable.profilh2)); // 2131165340
        Log.d("tv", String.valueOf(R.drawable.tv)); // 2131165345
        Log.d("radio", String.valueOf(R.drawable.radio)); // 2131165341
        Log.d("presse", String.valueOf(R.drawable.presse_papier)); //2131165336


    }


    public void Connexion(View view) {

      if (Password.getText().toString().equals(BDD_Utilisateurs.getPass(Login.getText().toString()))) {  // Si le mot de passe du EditText = Mot de passe dans la BDD pour le login écrit dans TextView, on connecte :

          Intent a = new Intent (MainActivity.this, Profil.class); // Intent vers Profil
          startActivity(a);
      } else {

          Toast.makeText(MainActivity.this, "Mauvais identifiant", Toast.LENGTH_LONG).show(); // Sinon l'identifiant est faux
      }

    }

    public void Inscription(View view) {

        Intent b = new Intent(MainActivity.this, Inscription.class);
        startActivity(b);
    }


    public void AddUsersAutomatique(View view) { // Création de 12 faux profils
        BDD_Utilisateurs.insertContact("Sophie","123456", "Intervenante","Paris","Experte","kevin@hotmail.fr", "Economie", R.drawable.profilf1);
        BDD_Utilisateurs.insertContact("Marie","123456", "Intervenante","Paris","Avocate","kevin@hotmail.fr", "Politique", R.drawable.profilf2);
        BDD_Utilisateurs.insertContact("Karima","123456", "Intervenante","Paris","Chercheuse","kevin@hotmail.fr", "Economie, Politique", R.drawable.profilf1);
        BDD_Utilisateurs.insertContact("Cosette","123456", "Intervenante","Paris","Avocate","kevin@hotmail.fr", "Criminologie, Droit", R.drawable.profilf2);
        BDD_Utilisateurs.insertContact("Julie","123456", "Intervenante","Paris","Entrepreneuse","kevin@hotmail.fr", "Sociologie", R.drawable.profilf1);
        BDD_Utilisateurs.insertContact("Brigitte","123456", "Intervenante","Paris","Entrepreneuse","kevin@hotmail.fr", "Ecologie, Politique", R.drawable.profilf2);
        BDD_Utilisateurs.insertContact("Radio Bordeaux","123456", "Media","Paris","Aucune description","kevin@hotmail.fr", "Radio locale", R.drawable.radio);
        BDD_Utilisateurs.insertContact("BFR","123456", "Media","Paris","Aucune description","kevin@hotmail.fr", "Chaine télévision",R.drawable.tv);
        BDD_Utilisateurs.insertContact("LCR","123456", "Media","Paris","Aucune description","kevin@hotmail.fr", "Radio locale",R.drawable.radio);
        BDD_Utilisateurs.insertContact("KNews","123456", "Media","Paris","Aucune description","kevin@hotmail.fr", "Chaine télévision",R.drawable.tv);
        BDD_Utilisateurs.insertContact("FranceEssonne","123456", "Media","Paris","Aucune description","kevin.@hotmail.fr", "Radio locale",R.drawable.radio);
        BDD_Utilisateurs.insertContact("Jupiter","123456", "Media","Paris","Aucune description","kevin@hotmail.fr", "Presse papier",R.drawable.presse_papier);

        Nb_inscrits.setText("Il y a " + BDD_Utilisateurs.numberOfRows() + " membres inscrits"); // Nombre d'inscrits

        Toast.makeText(MainActivity.this, "12 utilisateurs ont été ajoutés !", Toast.LENGTH_LONG).show();

    }
}


