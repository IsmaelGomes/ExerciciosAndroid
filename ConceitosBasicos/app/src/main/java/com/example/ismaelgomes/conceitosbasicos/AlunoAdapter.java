package com.example.ismaelgomes.conceitosbasicos;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AlunoAdapter extends BaseAdapter{
    Context ctx;
    List<Aluno> alunos;

    public AlunoAdapter(Context context, List<Aluno> alunos){
        this.ctx = context;
        this.alunos = alunos;
    }
    @Override
    public int getCount() {
        return alunos.size();
    }
    @Override
    public Object getItem(int position) {
        return alunos.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Aluno aluno = alunos.get(position);

        ViewHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(ctx)
                    .inflate(R.layout.item_aluno, null);
            holder = new ViewHolder();
            holder.imgLogo = (ImageView) convertView.findViewById(R.id.imgLogo);
            holder.txtNome = (TextView) convertView.findViewById(R.id.txtNome);
            holder.txtSite = (TextView) convertView.findViewById(R.id.txtSite);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        if(aluno.getSerializableFoto() != null)
            holder.imgLogo.setImageBitmap(BitmapUtility.getImage(aluno.getSerializableFoto()));
        holder.txtNome.setText(aluno.getNome());
        holder.txtSite.setText(aluno.getSite());

        return convertView;
    }

    static class ViewHolder {
        ImageView imgLogo;
        TextView txtNome;
        TextView txtSite;
    }
}
