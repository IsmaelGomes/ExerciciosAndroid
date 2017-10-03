package com.example.ismaelgomes.conceitosbasicos;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

    public class Alunos extends AppCompatActivity {

        AlunoAdapter adapter;
        List<Aluno> alunos;
        AlunoDAO dao;
        Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alunos);
        ListView lv = (ListView) findViewById(R.id.alunosList);

        Bitmap foto = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        alunos = getAlunosFromDb();

        adapter = new AlunoAdapter(this, alunos);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent nextActivity = new Intent(Alunos.this, AlunoEdit.class);
                nextActivity.putExtra("aluno", alunos.get(position));
                startActivityForResult(nextActivity, 1);
            }
        });
        lv.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                contextMenu.add(Menu.NONE, 1, Menu.NONE, "Ligar");
                contextMenu.add(Menu.NONE, 2, Menu.NONE, "Mandar SMS");
                contextMenu.add(Menu.NONE, 3, Menu.NONE, "Ver localização");
                contextMenu.add(Menu.NONE, 4, Menu.NONE, "Acessar site");
            }
        });

        Button addButton = (Button) findViewById(R.id.button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Alunos.this, AlunoEdit.class);
                startActivityForResult(it, 1);
            }
        });
    }

    private ArrayList<Aluno> getAlunosFromDb(){
        dao = new AlunoDAO(Alunos.this);
        cursor = dao.getReadableDatabase().rawQuery("SELECT ID, Nome, Telefone, Endereco, Foto, Site, Nota FROM Alunos", null);
        ArrayList<Aluno> all = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                Aluno a = new Aluno();
                a.setId(cursor.getInt(0));
                a.setNome(cursor.getString(1));
                a.setTelefone(cursor.getString(2));
                a.setEndereco(cursor.getString(3));
                //a.setFoto(cursor.getBlob(4)); CONVERTER PARA BITMAP.
                a.setSite(cursor.getString(5));
                a.setNota(cursor.getDouble(6));
                all.add(a);
            }while (cursor.moveToNext());
        }
        return all;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = menuInfo.position;
        switch (item.getItemId()) {
            case 1:
                makeCall(position);
                break;
            case 2:
                sendSMS(position);
                break;
            case 3:
                showLocation(position);
                break;
            case 4:
                showWebSite(position);
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void makeCall(int position) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", alunos.get(position).getTelefone(), null));
        startActivity(intent);
    }
    private void sendSMS(int position){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", alunos.get(position).getTelefone(), null)));
    }
    private void showLocation(int position){

        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + alunos.get(position).getEndereco());
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
    private void showWebSite(int position){
        String url = "http://"+alunos.get(position).getSite();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            alunos.clear();
            alunos.addAll(getAlunosFromDb());
            adapter.notifyDataSetChanged();
        }
    }
}
