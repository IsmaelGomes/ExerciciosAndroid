package com.example.ismaelgomes.conceitosbasicos;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AlunoEdit extends AppCompatActivity {

    //Views
    ImageView alunoimage;
    Button changeImage, save, cancel;
    EditText name, phone, nota, address, site;

    //Vars
    Aluno aluno;
    Bitmap alunoImageBitMap;
    AlunoDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aluno_edit);
        Intent intent = getIntent();
        aluno = (Aluno)intent.getSerializableExtra("aluno");
        dao = new AlunoDAO(AlunoEdit.this);
        setViews();
        setViewValues(aluno);
        setListeners();
    }

    private void setViews(){
        //ImageView
        alunoimage = (ImageView)findViewById(R.id.imageView);

        //Button
        changeImage = (Button)findViewById(R.id.changeImageButton);
        save = (Button)findViewById(R.id.saveButton);
        cancel = (Button)findViewById(R.id.cancelButton);

        //EditText
        name = (EditText)findViewById(R.id.editNome);
        phone = (EditText)findViewById(R.id.editTelefone);
        nota = (EditText)findViewById(R.id.editNota);
        address = (EditText)findViewById(R.id.editEndereco);
        site = (EditText)findViewById(R.id.editSite);
    }

    private void setViewValues(Aluno aluno){
        if(aluno != null){
            if(aluno.getFoto() != null)
                alunoimage.setImageBitmap(aluno.getFoto());
            name.setText(aluno.getNome());
            phone.setText(aluno.getTelefone());
            nota.setText(aluno.getNota().toString());
            address.setText(aluno.getEndereco());
            site.setText(aluno.getSite());
        }
    }

    private void setListeners(){

        //ChangeImage
        changeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Escolha uma imagem");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

                startActivityForResult(chooserIntent, 1);
            }
        });

        //Save
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Aluno aluno = getAlunoFromViews();
                if(aluno.getId() < 1)
                    dao.getWritableDatabase().insert("Alunos", null, getContentValues(aluno));
                else
                    dao.getWritableDatabase().update("Alunos", getContentValues(aluno), ("ID="+aluno.getId()), null);

                Intent it = new Intent();
                it.putExtra("aluno", aluno);
                setResult(RESULT_OK, it);
                Toast.makeText(AlunoEdit.this, "Aluno salvo com sucesso", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        //Cancel
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    private ContentValues getContentValues(Aluno a){
        ContentValues cv = new ContentValues();
        cv.put("Nome", a.getNome());
        cv.put("Telefone", a.getTelefone());
        cv.put("Endereco", a.getEndereco());
        //cv.put("Foto", a.getFoto()); RESOLVER PROBLEMA COM SERIALIZAÇÃO
        cv.put("Site", a.getSite());
        cv.put("Nota", a.getNota());
        return cv;
    }

    private Aluno getAlunoFromViews(){

        Aluno a = new Aluno();
        //PROBLEMA COM SERIALIZAÇÃO DO BITMAP.
        //a.setFoto(((BitmapDrawable)alunoimage.getDrawable()).getBitmap());
        if(aluno != null)
            a.setId(aluno.getId());
        a.setNome(name.getText().toString());
        a.setTelefone(phone.getText().toString());
        a.setEndereco(address.getText().toString());
        a.setNota(Double.parseDouble(nota.getText().toString()));
        a.setSite(site.getText().toString());
        return a;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if(data != null){
                try {
                    InputStream inputStream = getContentResolver().openInputStream(data.getData());
                    alunoImageBitMap = BitmapFactory.decodeStream(inputStream);
                    alunoimage.setImageBitmap(alunoImageBitMap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
