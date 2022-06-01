package com.example.ex5json201;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ArrayList<Etudiant> etudiants;
    Spinner sp;
    TextView t1, t2, t3;
    MyDBEtudiants db;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.m1: Toast.makeText(this, "Menu 1", Toast.LENGTH_SHORT).show(); break;
            case R.id.m2: Toast.makeText(this, "Menu 2", Toast.LENGTH_SHORT).show(); break;
            case R.id.m3: Toast.makeText(this, "Menu 3", Toast.LENGTH_SHORT).show(); break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new MyDBEtudiants(this);
        etudiants = getAlletudiants();


        for(Etudiant e : etudiants)
            MyDBEtudiants.insert_etudiants(db.getWritableDatabase(),e);

        sp = findViewById(R.id.sp);
        t1 = findViewById(R.id.nom);
        t2 = findViewById(R.id.ville);
        t3 = findViewById(R.id.naissance);

        ArrayList<HashMap<String,Object>> list_etds = new ArrayList<>();

        for(Etudiant e:etudiants){
            HashMap<String, Object> item = new HashMap<>();
            item.put("nom",e.getNom());
            if(e.getGenre().equalsIgnoreCase("homme"))
                item.put("image",R.drawable.homme);
            else
                item.put("image",R.drawable.femme);
            list_etds.add(item);
        }

        String[] from = {"nom", "image"};
        int[] to ={R.id.itemNom,R.id.itemImage};

        SimpleAdapter ad = new SimpleAdapter(this,list_etds,R.layout.item_etudiant,from,to);
        sp.setAdapter(ad);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                Etudiant ee = etudiants.get(pos);

                t1.setText(ee.getNom());
                t2.setText(ee.getVille());
                t3.setText(ee.getNaissance());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public String loadJsonFromRaw(int resID){
        try {
            InputStream in = getResources().openRawResource(resID);
            byte[] data = new byte[in.available()];
            in.read(data);
            in.close();
            return new String(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public ArrayList<Etudiant> getAlletudiants(){
        ArrayList<Etudiant> res = new ArrayList<>();

        try {
            JSONArray arr = new JSONArray(loadJsonFromRaw(R.raw.etudiants));

            for(int i=0;i<arr.length();i++){
                JSONObject obj = arr.getJSONObject(i);
                Etudiant e = new Etudiant();
                e.setIdent(obj.getString("ident"));
                e.setNom(obj.getString("nom"));
                e.setVille(obj.getString("ville"));
                e.setGenre(obj.getString("genre"));
                e.setNaissance(obj.getString("naissance"));
                e.setNoteFr(obj.getJSONObject("resultat").getDouble("fr"));
                e.setNoteMath(obj.getJSONObject("resultat").getDouble("math"));
                e.setNoteHist(obj.getJSONObject("resultat").getDouble("hist"));
                e.setNotePhys(obj.getJSONObject("resultat").getDouble("phys"));

                res.add(e);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return res;
    }
}