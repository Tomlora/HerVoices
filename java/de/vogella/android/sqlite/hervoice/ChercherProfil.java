package de.vogella.android.sqlite.hervoice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Calendar;

import java.util.HashMap;
import java.util.TimeZone;

public class ChercherProfil extends AppCompatActivity {

    static SimpleAdapter ListeContacts;
    static ArrayList<HashMap<String, String>> ListeBDD;
    static private ListView ListeAffichée;
    static EditText Critère1_box;
    static ArrayList<HashMap<String, String>> listItemTriée = null;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chercher_profil);

        ListeAffichée = (ListView) findViewById(R.id.ListeMedia);

        listItemTriée = new ArrayList<HashMap<String, String>>();

        if (Profil.Status.equals("Media")) { // si le profil est un media
            ListeBDD = MainActivity.BDD_Utilisateurs.getAllContactsHashMap("Intervenante"); // get tous les contacts Intervenantes

        } else { // sinon
            ListeBDD = MainActivity.BDD_Utilisateurs.getAllContactsHashMap("Media"); // get tous les contacts Media car le profil sera forcément intervenante

        }
        ListeContacts = new SimpleAdapter(this.getBaseContext(), ListeBDD, R.layout.list_layout,
                new String[]{"avatar","name","description","tag", "mail"}, new int[]{R.id.Avatar, R.id.Nom, R.id.tag_profil, R.id.description}); // Tableau



        ListeAffichée.setAdapter(ListeContacts);

        ListeAffichée.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            @SuppressWarnings("unchecked")
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                //on récupère la HashMap contenant les infos de notre item (titre, description, img)
                HashMap<String, String> map = (HashMap<String, String>) ListeAffichée.getItemAtPosition(position);
                //on créé une boite de dialogue
                AlertDialog.Builder adb = new AlertDialog.Builder(ChercherProfil.this);
                //on attribue un titre à notre boite de dialogue. Ici le nom de la personne sélectionné
                adb.setTitle(map.get("name"));
                //on insère un message à notre boite de dialogue, et ici on affiche la localisation de la personne sélectionnée
                adb.setMessage("Localisation : " + map.get("localisation"));
                //on indique que l'on veut le bouton ok à notre boite de dialogue
                adb.setPositiveButton("Demande de RDV", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String Titre_contact = map.get("name");
                        String Description_contact = map.get("description");
                        String Mail_contact = map.get("mail");
                        calendarEvent(Titre_contact, Description_contact, Mail_contact);
                        Log.d("Mail selectionne", Mail_contact);
                    }
                });
                adb.setNegativeButton("Fermer", null);
                //on affiche la boite de dialogue
                adb.show();
            }
        });



    }

    public void calendarEvent(String name, String description, String mail) {

     // Initialisation de l'intent vers Google calendar


        TimeZone tz = TimeZone.getDefault();
        Calendar beginTime = Calendar.getInstance();

        beginTime.set(2021, 4, 19, 7, 30); // Attention les mois commencent à 0 !
        Calendar endTime = Calendar.getInstance();
        endTime.set(2021, 4, 19, 8, 30);
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.Events.EVENT_TIMEZONE, tz.getID())
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                        beginTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                .putExtra(CalendarContract.Events.TITLE, name)
                .putExtra(CalendarContract.Events.DESCRIPTION, description)
                .putExtra(CalendarContract.Events.EVENT_LOCATION, "Skype")
                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
                .putExtra(CalendarContract.Events.ORGANIZER, MainActivity.BDD_Utilisateurs.getEmail(Profil.login))
                .putExtra(Intent.EXTRA_EMAIL, mail);
        startActivity(intent);
    }

    public void Search(View view) {

        // Filtrer dans la liste en fonction du tag

        //on créé une boite de dialogue
        AlertDialog.Builder Recherche = new AlertDialog.Builder(ChercherProfil.this);
        // Recherche.setView(R.layout.box_recherche);
        final View LayoutR = getLayoutInflater().inflate(R.layout.box_recherche, null);

        Recherche.setView(LayoutR);
        //on attribue un titre à notre boite de dialogue
        Recherche.setTitle("Rechercher par Tag");
        //on insère un message à notre boite de dialogue, et ici on affiche le titre de l'item cliqué

        DialogInterface.OnClickListener Critère1 = new DialogInterface.OnClickListener() { // Evenement Critère 1 en cas de "clic"
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Critère1_box = LayoutR.findViewById(R.id.recherche1); // Critère défini par la zone de recherche 1
                Tri(Critère1_box.getText().toString()); // Tri avec notre critère....

            }
        };
        //bouton
        Recherche.setPositiveButton("Valider", Critère1); // Evenement Critère 1 défini précédemment en cas de clic sur Critère 1
        Recherche.setNegativeButton("Annuler", null);


        AlertDialog dialogR = Recherche.create();  // on crée la boite
        dialogR.show(); // on l'affiche


    }



    private void Tri(String data) {

        //Fonction de tri


        listItemTriée.clear();

        Toast.makeText(this, "Critère : " + data, Toast.LENGTH_LONG).show();

        for (int i=0;i<ListeBDD.size();i++) {

            if (ListeBDD.get(i).get("tag").contains(data)) {
                listItemTriée.add(ListeBDD.get(i));

            }


            //Création d'un SimpleAdapter qui se chargera de mettre les items présents dans notre list (listItem) dans la vue affichageitem
            ListeContacts = new SimpleAdapter(this.getBaseContext(), listItemTriée, R.layout.list_layout,
                    new String[]{"avatar","name","localisation","tag", "mail"}, new int[]{R.id.Avatar, R.id.Nom, R.id.tag_profil, R.id.description}); // Tableau

            //On attribue à notre listView l'adapter précédent
            ListeAffichée.setAdapter(ListeContacts);
        }
    }
}
