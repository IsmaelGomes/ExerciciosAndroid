package com.example.ismaelgomes.conceitosbasicos;


import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import layout.alunosEditFragment;
import layout.alunosFragment;

public class Alunos extends AppCompatActivity{

    private FragmentManager fm;
    private Boolean orientationPortrait = false;
    private final static String OK = "ok";
    private final static String CONDITION_BASE = " WHERE";
    MyPageAdapter pageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alunos);
        fm = getSupportFragmentManager();
        orientationPortrait = (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);

        FragmentTransaction ft = fm.beginTransaction();
        alunosFragment fragment = alunosFragment.newInstance("");

        if(orientationPortrait == false) {
            //Inicializando o fragment
            ft.add(R.id.listFragment_content, fragment);
            ft.commit();
        }else{
            ViewPager vp = (ViewPager)findViewById(R.id.view_pager);
            List<Fragment> fragments = getFragments();
            pageAdapter = new MyPageAdapter(fm, fragments);
            vp.setAdapter(pageAdapter);
        }

    }

    public void onNotifyList(Aluno aluno){

        FragmentTransaction ft = fm.beginTransaction();
        alunosEditFragment fragment = alunosEditFragment.newInstance(aluno);

        if(orientationPortrait == false){
            //remover antes
            try{
                ft.remove(getSupportFragmentManager().findFragmentById(R.id.editFragment_content));
                ft.commit();
            }catch(Exception e){

            }
            finally {
                //adicionar outro fragment
                ft = fm.beginTransaction();
                ft.add(R.id.editFragment_content, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        }
    }

    public void onNotifyEdit(String message){
        if(message.equals(OK)){

            FragmentTransaction ft = fm.beginTransaction();

            if(orientationPortrait == false){
                ft.replace(R.id.listFragment_content, new alunosFragment().newInstance(""));
                ft.commit();
            }

            Toast.makeText(this, "Aluno salvo com sucesso", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private List<Fragment> getFragments(){
        List<Fragment> fgs = new ArrayList<Fragment>();
        fgs.add(alunosFragment.newInstance(""));
        fgs.add(alunosFragment.newInstance(CONDITION_BASE + " Telefone IS NOT NULL"));
        fgs.add(alunosFragment.newInstance(CONDITION_BASE + " Nota < 7"));

        return fgs;
    }

    class MyPageAdapter extends FragmentPagerAdapter{
        private List<Fragment> fragments;

        public MyPageAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }
        @Override
        public Fragment getItem(int position) {
            return this.fragments.get(position);
        }
        @Override
        public int getCount() {
            return this.fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = "";
            switch (position){
                case 0: title = "Todos"; break;
                case 1: title = "Com Telefone"; break;
                case 2: title = "Nota < 7"; break;
            }
            return title;
        }
    }
}
