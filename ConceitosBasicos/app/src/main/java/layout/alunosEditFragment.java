package layout;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ismaelgomes.conceitosbasicos.Aluno;
import com.example.ismaelgomes.conceitosbasicos.AlunoDAO;
import com.example.ismaelgomes.conceitosbasicos.AlunoEdit;
import com.example.ismaelgomes.conceitosbasicos.Alunos;
import com.example.ismaelgomes.conceitosbasicos.BitmapUtility;
import com.example.ismaelgomes.conceitosbasicos.R;

import java.io.FileNotFoundException;
import java.io.InputStream;


public class alunosEditFragment extends android.support.v4.app.Fragment {

    //Views
    ImageView alunoimage;
    Button changeImage, save, cancel;
    EditText name, phone, nota, address, site;

    //Vars
    Aluno aluno;
    Bitmap alunoImageBitMap;
    AlunoDAO dao;

    private final static String PARAMETER = "aluno";

    public alunosEditFragment() {
    }

    public static alunosEditFragment newInstance(Aluno aluno) {
        alunosEditFragment fragment = new alunosEditFragment();
        Bundle args = new Bundle();
        args.putSerializable(PARAMETER,aluno);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alunos_edit, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstaceState){
        aluno = (Aluno)getArguments().getSerializable(PARAMETER);
        dao = new AlunoDAO(getActivity());
        setViews(view);
        setViewValues(aluno);
        setListeners();
    }

    private void setViews(View view){
        //ImageView
        alunoimage = (ImageView)view.findViewById(R.id.imageView);

        //Button
        changeImage = (Button)view.findViewById(R.id.changeImageButton);
        save = (Button)view.findViewById(R.id.saveButton);
        cancel = (Button)view.findViewById(R.id.cancelButton);

        //EditText
        name = (EditText)view.findViewById(R.id.editNome);
        phone = (EditText)view.findViewById(R.id.editTelefone);
        nota = (EditText)view.findViewById(R.id.editNota);
        address = (EditText)view.findViewById(R.id.editEndereco);
        site = (EditText)view.findViewById(R.id.editSite);
    }

    private void setViewValues(Aluno aluno){
        if(aluno != null){
            if(aluno.getSerializableFoto() != null)
                alunoimage.setImageBitmap(BitmapUtility.getImage(aluno.getSerializableFoto()));
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

                Alunos parent = (Alunos)getActivity();
                parent.onNotifyEdit("ok");
            }
        });

        //Cancel
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Alunos parent = (Alunos)getActivity();
                parent.onNotifyEdit("cancel");
            }
        });
    }

    private ContentValues getContentValues(Aluno a){
        ContentValues cv = new ContentValues();
        cv.put("Nome", a.getNome());
        cv.put("Telefone", a.getTelefone());
        cv.put("Endereco", a.getEndereco());
        cv.put("Foto", a.getSerializableFoto());
        cv.put("Site", a.getSite());
        cv.put("Nota", a.getNota());
        return cv;
    }

    private Aluno getAlunoFromViews(){

        Aluno a = new Aluno();
        a.setSerializableFoto(BitmapUtility.getBytes(((BitmapDrawable)alunoimage.getDrawable()).getBitmap()));
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
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == getActivity().RESULT_OK) {
            if(data != null){
                try {
                    InputStream inputStream = getActivity().getContentResolver().openInputStream(data.getData());
                    alunoImageBitMap = BitmapFactory.decodeStream(inputStream);
                    alunoimage.setImageBitmap(alunoImageBitMap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
