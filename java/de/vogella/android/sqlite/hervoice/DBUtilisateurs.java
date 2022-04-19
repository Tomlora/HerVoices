package de.vogella.android.sqlite.hervoice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class DBUtilisateurs extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyBDD_inscription.db"; // On met "static" car il n'y a aucune référence avec "this.variable"
    public static final String CONTACTS_TABLE_NAME = "Utilisateur";  // final = constant
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_PASS = "pass";
    public static final String CONTACTS_COLUMN_STATUS = "status";
    public static final String CONTACTS_COLUMN_LOCALISATION = "localisation";
    public static final String CONTACTS_COLUMN_DESCRIPTION = "description";
    public static final String CONTACTS_COLUMN_MAIL = "mail";
    public static final String CONTACTS_COLUMN_DISPONIBILITE = "disponibilité";
    public static final String CONTACTS_COLUMN_TAG = "tag";
    public static final String CONTACTS_COLUMN_AVATAR = "avatar";

    public DBUtilisateurs(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    public HashMap<String, String> Hash;

    @Override
    public void onCreate(SQLiteDatabase db) { // On crée toutes les tables d'un coup... Comme ça, c'est fait.
        // TODO Auto-generated method stub
        db.execSQL(
                "create table "+ CONTACTS_TABLE_NAME +
                        "(id integer primary key, name text ,pass text, status text, localisation text, description text,mail text, disponibilité integer, tag text, avatar integer)"); // BDD Utilisateurs

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + CONTACTS_TABLE_NAME);
        onCreate(db);
    }

    /*
    Créer un contact
     */

    public boolean insertContact (String name, String pass, String status, String localisation, String description,String mail, String tag, int avatar) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACTS_COLUMN_NAME, name);
        contentValues.put(CONTACTS_COLUMN_PASS, pass);
        contentValues.put(CONTACTS_COLUMN_STATUS, status);
        contentValues.put(CONTACTS_COLUMN_LOCALISATION, localisation);
        contentValues.put(CONTACTS_COLUMN_DESCRIPTION, description);
        contentValues.put(CONTACTS_COLUMN_MAIL, mail);
        contentValues.put(CONTACTS_COLUMN_DISPONIBILITE, "1"); // 1 = disponible (par défaut)
        contentValues.put(CONTACTS_COLUMN_TAG, tag);
            contentValues.put(CONTACTS_COLUMN_AVATAR, String.valueOf(avatar));
        db.insert(CONTACTS_TABLE_NAME, null, contentValues);
        return true;
    }

    /*
    Obtenir des informations de la BDD :
     */

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + CONTACTS_TABLE_NAME + " where id="+id+"", null );
        // select * from inscription where id = id(celui retenu);
        return res;
    }

    public String getId(String login) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select "+ CONTACTS_COLUMN_ID +" from " + CONTACTS_TABLE_NAME + " where name= ?";
        // select ID from inscription where name = "?";
        String [] selectionArgs = {login};
        Cursor mdp = db.rawQuery(query, selectionArgs);
        if(mdp != null && mdp.moveToFirst()) {

            return mdp.getString(mdp.getColumnIndex("id")); // Convertit en string
        } else {
            Log.d("mdp","Erreur");
            return null;
        }

    }

    public String getPass(String login) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select "+ CONTACTS_COLUMN_PASS +" from " + CONTACTS_TABLE_NAME + " where name= ?";
        // select pass from inscription where name = ?;
        String [] selectionArgs = {login};
        Cursor mdp = db.rawQuery(query, selectionArgs);
        if(mdp != null && mdp.moveToFirst()) {

            return mdp.getString(mdp.getColumnIndex("pass")); // Convertit en string
        } else {
            Log.d("mdp","Erreur");
            return null;
        }

    }

    public String getEmail(String login) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select "+ CONTACTS_COLUMN_MAIL +" from " + CONTACTS_TABLE_NAME + " where name= ?";
        String [] selectionArgs = {login};
        // Cursor mdp =  db.rawQuery( "select "+ CONTACTS_COLUMN_PASS +" from " + CONTACTS_TABLE_NAME + " where name= ?", new String[] {login} ); // Les string doivent être en forme de tableau avec cursor
        Cursor mdp = db.rawQuery(query, selectionArgs);
        if(mdp != null && mdp.moveToFirst()) {

            return mdp.getString(mdp.getColumnIndex("mail")); // Convertit en string
        } else {

            return null;
        }
    }

    public Integer getDisponible(String login) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select "+ CONTACTS_COLUMN_DISPONIBILITE +" from " + CONTACTS_TABLE_NAME + " where name= ?";
        String [] selectionArgs = {login};
        // Cursor mdp =  db.rawQuery( "select "+ CONTACTS_COLUMN_PASS +" from " + CONTACTS_TABLE_NAME + " where name= ?", new String[] {login} ); // Les string doivent être en forme de tableau avec cursor
        Cursor mdp = db.rawQuery(query, selectionArgs);
        if(mdp != null && mdp.moveToFirst()) {

            return Integer.parseInt(mdp.getString(mdp.getColumnIndex("disponibilité"))); // Convertit en integer
        } else {

            return null;
        }
    }

    public String getStatus(String login) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select "+ CONTACTS_COLUMN_STATUS +" from " + CONTACTS_TABLE_NAME + " where name= ?";
        String [] selectionArgs = {login};
        Cursor mdp = db.rawQuery(query, selectionArgs);
        if(mdp != null && mdp.moveToFirst()) {

            return mdp.getString(mdp.getColumnIndex("status")); // Convertit en string
        } else {

            return null;
        }

    }

    public String getLocalisation(String login) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select "+ CONTACTS_COLUMN_LOCALISATION +" from " + CONTACTS_TABLE_NAME + " where name= ?";
        String [] selectionArgs = {login};
        Cursor mdp = db.rawQuery(query, selectionArgs);
        if(mdp != null && mdp.moveToFirst()) {

            return mdp.getString(mdp.getColumnIndex("localisation")); // Convertit en string
        } else {

            return null;
        }

    }

    public String getDescription(String login) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select "+ CONTACTS_COLUMN_DESCRIPTION +" from " + CONTACTS_TABLE_NAME + " where name= ?";
        String [] selectionArgs = {login};
        Cursor mdp = db.rawQuery(query, selectionArgs);
        if(mdp != null && mdp.moveToFirst()) {

            return mdp.getString(mdp.getColumnIndex("description")); // Convertit en string
        } else {

            return null;
        }

    }

    public String getLogin(String login) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select " + CONTACTS_COLUMN_NAME + " from " + CONTACTS_TABLE_NAME + " where name= ?";
        String[] selectionArgs = {login};
        Cursor mdp = db.rawQuery(query, selectionArgs);
        if (mdp != null && mdp.moveToFirst()) {

            return mdp.getString(mdp.getColumnIndex("name")); // Convertit en string
        } else {

            return null;
        }
    }

        public String getTag(String login) {
            SQLiteDatabase db = this.getReadableDatabase();
            String query = "select "+ CONTACTS_COLUMN_TAG +" from " + CONTACTS_TABLE_NAME + " where name= ?";
            // select ID from inscription where name = "?";
            String [] selectionArgs = {login};
            Cursor mdp = db.rawQuery(query, selectionArgs);
            if(mdp != null && mdp.moveToFirst()) {

                return mdp.getString(mdp.getColumnIndex("tag")); // Convertit en string
            } else {
                Log.d("mdp","Erreur");
                return null;
            }

    }

    public String getAvatar(String login) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select "+ CONTACTS_COLUMN_AVATAR +" from " + CONTACTS_TABLE_NAME + " where name= ?";
        String [] selectionArgs = {login};
        // Cursor mdp =  db.rawQuery( "select "+ CONTACTS_COLUMN_PASS +" from " + CONTACTS_TABLE_NAME + " where name= ?", new String[] {login} ); // Les string doivent être en forme de tableau avec cursor
        Cursor mdp = db.rawQuery(query, selectionArgs);
        if(mdp != null && mdp.moveToFirst()) {

            return mdp.getString(mdp.getColumnIndex("avatar")); // Convertit en string
        } else {

            return null;
        }
    }

    public int numberOfRows(){  // Nombre d'inscrits ?
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }

    /*

    Mettre à jour la BDD :
     */

    public boolean updateContactTag (String id, String Tag) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACTS_COLUMN_TAG, Tag);
           String [] selectionArgs = {id};
        db.update(CONTACTS_TABLE_NAME, contentValues, "id = ? ", selectionArgs );
        return true;
    }

    public boolean updateContactDisponibilité (String id, Integer Disponibilité) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACTS_COLUMN_DISPONIBILITE, Disponibilité);
        String [] selectionArgs = {id};
        db.update(CONTACTS_TABLE_NAME, contentValues, "id = ? ", selectionArgs );
        return true;
    }

    public boolean updateContactLocalisation (String id, String Localisation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACTS_COLUMN_LOCALISATION, Localisation);
        String [] selectionArgs = {id};
        db.update(CONTACTS_TABLE_NAME, contentValues, "id = ? ", selectionArgs );
        return true;
    }

    public boolean updateContactDescription (String id, String Description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACTS_COLUMN_DESCRIPTION, Description);
        String [] selectionArgs = {id};
        db.update(CONTACTS_TABLE_NAME, contentValues, "id = ? ", selectionArgs );
        return true;
    }

    public boolean updateContact (String id, String name, String pass, String status, String localisation, String description,String mail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACTS_COLUMN_NAME, name);
        contentValues.put(CONTACTS_COLUMN_PASS, pass);
        contentValues.put(CONTACTS_COLUMN_STATUS, status);
        contentValues.put(CONTACTS_COLUMN_LOCALISATION, localisation);
        contentValues.put(CONTACTS_COLUMN_DESCRIPTION, description);
        contentValues.put(CONTACTS_COLUMN_MAIL, mail);
        String [] selectionArgs = {id};
        db.update(CONTACTS_TABLE_NAME, contentValues, "id = ? ", selectionArgs );
        return true;
    }

    public Integer deleteContact (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String [] selectionArgs = {Integer.toString(id)};
        return db.delete(CONTACTS_TABLE_NAME,"id = ? ", selectionArgs);
    }

    public ArrayList<String> getAllContacts() {
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + CONTACTS_TABLE_NAME, null ); // Selectionne tt les éléments
        res.moveToFirst(); // Va au premier de la liste

        while(res.isAfterLast() == false){ // Tant que nous n'avons pas dépassé le dernier
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME))); // Ajout à la liste
            res.moveToNext(); // Va à la ligne suivante
        }
        return array_list; // Affiche la liste
    }

    public ArrayList<HashMap<String, String>> getAllContactsHashMap(String status) {
        ArrayList<HashMap<String, String>> array_list;
        array_list = new ArrayList<HashMap<String, String>>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + CONTACTS_TABLE_NAME + " where status=?";
        String [] selectionArgs = {status};
        Cursor res =  db.rawQuery(query, selectionArgs);// Selectionne tt les éléments
        res.moveToFirst(); // Va au premier de la liste

        while(res.isAfterLast() == false){ // Tant que nous n'avons pas dépassé le dernier
            Hash = new HashMap<String, String>();
            Hash.put(CONTACTS_COLUMN_AVATAR, String.valueOf(res.getInt(res.getColumnIndex(CONTACTS_COLUMN_AVATAR))));
            Hash.put(CONTACTS_COLUMN_NAME, res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
            Hash.put(CONTACTS_COLUMN_DESCRIPTION, res.getString(res.getColumnIndex(CONTACTS_COLUMN_DESCRIPTION)));
            Hash.put(CONTACTS_COLUMN_TAG, res.getString(res.getColumnIndex(CONTACTS_COLUMN_TAG)));
            Hash.put(CONTACTS_COLUMN_LOCALISATION, res.getString(res.getColumnIndex(CONTACTS_COLUMN_LOCALISATION)));
            Hash.put(CONTACTS_COLUMN_MAIL, res.getString(res.getColumnIndex(CONTACTS_COLUMN_MAIL)));
            array_list.add(Hash);

            res.moveToNext(); // Va à la ligne suivante
        }
        return array_list; // Affiche la liste
    }
}

