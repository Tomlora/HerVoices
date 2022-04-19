package de.vogella.android.sqlite.hervoice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;

public class ModifierProfil extends AppCompatActivity {

    // Variable
    TextView tag;
    boolean[] choixretenu;
    ArrayList<Integer> choix = new ArrayList<>();

    String [] listeChoix;
    Integer Disponible;
    Switch Switch_disponibilité;
    EditText localisation;
    EditText description;
    View BaseView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_profil);
        BaseView = findViewById(R.id.button3);

        TextView identifiant = findViewById(R.id.id_modif);
        identifiant.setText(Profil.login);

        // Localisation et description

        localisation = findViewById(R.id.Localisation);
        description = findViewById(R.id.Description);

        localisation.setText(MainActivity.BDD_Utilisateurs.getLocalisation(Profil.login));
        description.setText(MainActivity.BDD_Utilisateurs.getDescription(Profil.login));

        if (MainActivity.BDD_Utilisateurs.getStatus(Profil.login).equals("Intervenante")) {
            listeChoix = new String[]{"Economie", "Ecologie", "Politique", "Droit", "Mathématiques", "Sociologie", "Criminologie"}; // Liste des tags disponibles
        } else {
            listeChoix = new String[]{"Presse magazine", "Presse écrite", "Groupe de presse", "Radio locale", "Radio nationale", "Télévision", "Autres"}; // Liste des tags disponibles
        }
        // Disponibilité de la personne :

        Switch_disponibilité = findViewById(R.id.Switch_disponibilité);
        Disponible = MainActivity.BDD_Utilisateurs.getDisponible(Profil.login);

        if (Disponible == 1) {
            Switch_disponibilité.setChecked(true);
        } else {
            Switch_disponibilité.setChecked(false);
        }


        ///////////////////////////////////////////////////////////////////// TAG :

        // Assigner
        tag = findViewById(R.id.tag);
        tag.setText(MainActivity.BDD_Utilisateurs.getTag(Profil.login));

        // Initialisation de la liste
        choixretenu = new boolean[listeChoix.length];
        tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialiser la boite de dialogue
                AlertDialog.Builder builder = new AlertDialog.Builder(ModifierProfil.this);
                // Titre
                builder.setTitle("Selectionner les tags");
                // Désactive le bouton cancel
                builder.setCancelable(false);
                builder.setMultiChoiceItems(listeChoix, choixretenu, new DialogInterface.OnMultiChoiceClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i, boolean b) {
                                        //Condition
                                        if (b){
                                            //Quand le choix est selectionne, il faut ajouter le choix dans la liste
                                            choix.add(i);
                                            //On trie
                                            Collections.sort(choix);
                                        }else {
                                            // Quand elle est déselectionnée, on la retire de la liste
                                            //choix.remove(i); //Désactivé pour le moment car bug
                                            Toast.makeText(ModifierProfil.this,"Merci de cliquer sur Supprimer tout et de reselectionner les tags",Toast.LENGTH_SHORT ).show();
                                        }
                                    }
                                });
                builder.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                       // On initialise le string builder
                        StringBuilder stringBuilder = new StringBuilder();
                        // Loop
                        for (int j=0; j<choix.size(); j++) {
                            // On concatène les valeurs
                            stringBuilder.append(listeChoix[choix.get(j)]);
                            // Condition
                            if (j != choix.size()-1) {
                                // quand la valeur de J n'est pas égale à la liste - 1, on ajoute une virgule
                                stringBuilder.append(", ");
                            }
                        }
                        // Assigner le texte
                        tag.setText(stringBuilder.toString());
                        MainActivity.BDD_Utilisateurs.updateContactTag(Profil.id, stringBuilder.toString()); // On update dans la BDDUtilisateurs (Fonction updateContactTag)
                    }
                });
             builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int i) {
                  // Cancel
                     dialog.dismiss();

                 }
             });
             builder.setNeutralButton("Supprimer tout", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int i) {
                  // Loop
                   for (int j=0; j<choixretenu.length; j++) {
                       // On déselectionne tout
                       choixretenu[j] = false;
                       // On vide la liste
                       choix.clear();
                       // On vide le texte
                       tag.setText("Aucun tag selectionné");
                       MainActivity.BDD_Utilisateurs.updateContactTag(Profil.id, "Aucun texte sélectionné");
                     }
                 }
             });
             // Montrer le dialogue
                builder.show();
            }
        });

    }

    public void Modif_Disponibilité(View view) {
        if (Switch_disponibilité.isChecked()) {
            MainActivity.BDD_Utilisateurs.updateContactDisponibilité(Profil.id, 1); // 1 = dispo
        } else {
            MainActivity.BDD_Utilisateurs.updateContactDisponibilité(Profil.id, 0); // 0 = pas dispo
        }
    }

    public void Validation(View view) {
        MainActivity.BDD_Utilisateurs.updateContactLocalisation(Profil.id, localisation.getText().toString()); // Class DBUtilisateurs, Fonction updateContactLocalisation
        MainActivity.BDD_Utilisateurs.updateContactDescription(Profil.id, description.getText().toString()); // Class DBUtilisateurs, Fonction updateContactDescription


        Intent e = new Intent(ModifierProfil.this, Profil.class);
        Snackbar customSnackbar = Snackbar.make(this.BaseView, "Modifications sauvegardées", Snackbar.LENGTH_LONG); // Création d'une Snackbar
        customSnackbar.setAction("Retourner au profil", new View.OnClickListener() { // Initialiser des paramètres
            @Override
            public void onClick(View v) { startActivity(e); }

        });
        customSnackbar.show();

    }
}