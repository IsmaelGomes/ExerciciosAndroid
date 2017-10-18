package layout;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.ismaelgomes.conceitosbasicos.Aluno;
import com.example.ismaelgomes.conceitosbasicos.AlunoAdapter;
import com.example.ismaelgomes.conceitosbasicos.AlunoDAO;
import com.example.ismaelgomes.conceitosbasicos.AlunoEdit;
import com.example.ismaelgomes.conceitosbasicos.Alunos;
import com.example.ismaelgomes.conceitosbasicos.R;

import java.util.ArrayList;
import java.util.List;

public class alunosFragment extends android.support.v4.app.Fragment {

    AlunoAdapter adapter;
    List<Aluno> alunos;
    AlunoDAO dao;
    Cursor cursor;

    private final static String PARAMTER = "condition";

    public alunosFragment() {
    }

    public static alunosFragment newInstance(String condition) {
        alunosFragment fragment = new alunosFragment();
        Bundle args = new Bundle();
        args.putString(PARAMTER,condition);
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

        return inflater.inflate(R.layout.fragment_aulunos, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstaceState){
        ListView lv = (ListView) view.findViewById(R.id.alunosList);

        Bitmap foto = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        alunos = getAlunosFromDb(getArguments().getString(PARAMTER));

        adapter = new AlunoAdapter(getActivity(), alunos);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Alunos pai = (Alunos)getActivity();
                pai.onNotifyList(alunos.get(position));
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

        Button addButton = (Button) view.findViewById(R.id.button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Alunos parent = (Alunos)getActivity();
                parent.onNotifyList(null);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private ArrayList<Aluno> getAlunosFromDb(String condition){
        dao = new AlunoDAO(getActivity());
        cursor = dao.getReadableDatabase().rawQuery("SELECT ID, Nome, Telefone, Endereco, Foto, Site, Nota FROM Alunos" + condition, null);
        ArrayList<Aluno> all = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                Aluno a = new Aluno();
                a.setId(cursor.getInt(0));
                a.setNome(cursor.getString(1));
                a.setTelefone(cursor.getString(2));
                a.setEndereco(cursor.getString(3));

                if(cursor.getBlob(4) != null)
                    a.setSerializableFoto(cursor.getBlob(4));

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
}
