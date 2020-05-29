package com.aa183.ErsaPutra;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    Spinner spJns, spGenre;
    EditText etJudul, etAlbum, etThn, etPenyanyi;
    Button btnSave;
    SqlHelper helperDB;
    boolean doubleBackToExitPressedOnce = false;
    String sId = "0", sJns, sGenre, sJdl, sAlbm, sPe, sThn, cameraFilePath, encodedImage;
    String [] stJns = {"INDONESIAN MUSIC", "KOREAN MUSIC", "JAPAN MUSIC", "ASIAN MUSIC", "WEST MUSIC"};
    String [] stGenre = {"POP", "JAZZ", "METAL", "ROCK", "HIP HOP", "EDM"};
    ModelList mList;
    private static final int REQUEST_PICK_PHOTO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setID();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sJns = spJns.getSelectedItem().toString();
                sGenre = spGenre.getSelectedItem().toString();
                sJdl = etJudul.getText().toString().toUpperCase();
                sAlbm = etAlbum.getText().toString().toUpperCase();
                sPe = etPenyanyi.getText().toString().toUpperCase();
                sThn = etThn.getText().toString().toUpperCase();
                if (!sJns.equals("") && !sGenre.equals("") && !sJdl.equals("") && !sAlbm.equals("")
                        && !sPe.equals("") && !sThn.equals("")){
                    if (!sId.equals("0")) {
                        updateDB(sJns, sGenre, sAlbm, sThn);
                    }
                    else {
                        saveDB(sJns, sGenre, sJdl, sAlbm, sPe, sThn);
                    }
                }
                else {
                    Toast.makeText(MainActivity.this, "The Information cannot be empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setID(){
        spJns = findViewById(R.id.spJns);
        spGenre = findViewById(R.id.spGenre);
        etJudul = findViewById(R.id.etJudul);
        etAlbum = findViewById(R.id.etAlbum);
        etThn = findViewById(R.id.etThn);
        etPenyanyi = findViewById(R.id.etPenyanyi);
        btnSave = findViewById(R.id.btnSave);
        setSpinner();
    }

    private void setSpinner(){
        ArrayAdapter<String> adpJns = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stJns);
        adpJns.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spJns.setAdapter(adpJns);

        ArrayAdapter<String> adpGenre = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stGenre);
        adpGenre.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGenre.setAdapter(adpGenre);

        setModel();
    }

    private void setModel(){
        mList = (ModelList) getIntent().getSerializableExtra("mList");
        if (mList != null) {
            sId = mList.getIdMsk();
            if (!sId.equals("0")){
                for (int i = 0; i < spJns.getCount(); i++) {
                    if (spJns.getItemAtPosition(i).equals(mList.getJnsMsk())) {
                        spJns.setSelection(i);
                    }
                }
                for (int i = 0; i < spGenre.getCount(); i++) {
                    if (spGenre.getItemAtPosition(i).equals(mList.getJnsMsk())) {
                        spGenre.setSelection(i);
                    }
                }
                etJudul.setText(mList.getJdlMsk());
                etAlbum.setText(mList.getAlbm());
                etThn.setText(mList.getThnMsk());
                etPenyanyi.setText(mList.getPenyanyi());
                btnSave.setText("Update Data");
            }
            else {
                setClear();
            }
        }
        setToolbar();
    }

    private void setClear (){
        spJns.setSelection(0);
        spGenre.setSelection(0);
        etJudul.setText("");
        etAlbum.setText("");
        etThn.setText("");
        etPenyanyi.setText("");
        btnSave.setText("Save Data");
        etJudul.setEnabled(true);
        etPenyanyi.setEnabled(true);
        sId = "0";
    }

    private void setToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle("Music");

        helperDB = new SqlHelper(this);
    }

    private void saveDB(String xJns, String xGenre, String xJdl, String xAlbm, String xPe, String xThn){
        String ckData = helperDB.cekData(xJdl, xPe);
        String[] dataArry = ckData.split("#");
        if (dataArry[0].isEmpty()){ //belum ada data & save data
            helperDB.InsertImg(xJns, xGenre, xJdl, xAlbm, xPe, xThn);
            if (VariableGlobal.varSqlHelper.equals("YA")){
                Toast.makeText(MainActivity.this, "Save Data Successfully", Toast.LENGTH_SHORT).show();
                setClear();
            }
            else{
                Toast.makeText(MainActivity.this, "Save Data Failed!", Toast.LENGTH_SHORT).show();
            }
        }
        else { //data yang di masukan sudah ada
            Toast.makeText(MainActivity.this, "Title & Singer already available!", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateDB(String xJns, String xGenre, String xAlbm, String xThn){
        String xIns = " UPDATE tbMusik SET JNS_MSK = '"+xJns+"', GENRE_MSK = '"+xGenre+"', " +
                " ALBUM = '"+xAlbm+"', THN_MSK = '"+xThn+"' WHERE ID_MSK = "+sId+" ";
        helperDB.UpdateData(xIns);
        if (VariableGlobal.varSqlHelper.equals("YA")){
            Toast.makeText(MainActivity.this, "Update Data Successfully", Toast.LENGTH_SHORT).show();
            setClear();
        }
        else{
            Toast.makeText(MainActivity.this, "Update Data Failed!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.listMusik) {
            String ckRmp = helperDB.cekEmpty();
            String[] empArry = ckRmp.split("#");
            if (empArry[0].isEmpty()){
                Toast.makeText(MainActivity.this, "Data Empty!", Toast.LENGTH_SHORT).show();
            }
            else {
                finish();
                startActivity(new Intent(MainActivity.this, ListActivity.class));
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity();
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Click again to close the aplication", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}
