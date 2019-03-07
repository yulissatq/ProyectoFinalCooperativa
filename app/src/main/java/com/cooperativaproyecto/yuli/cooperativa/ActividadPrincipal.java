package com.cooperativaproyecto.yuli.cooperativa;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import android.net.Uri;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cooperativaproyecto.yuli.cooperativa.Fragmentos.FragmentoLogin;
import com.cooperativaproyecto.yuli.cooperativa.Fragmentos.FragmentoMenu;
import com.cooperativaproyecto.yuli.cooperativa.Interfaces.interfaces;

/**
 * A login screen that offers login via email/password.
 */
public class ActividadPrincipal extends AppCompatActivity implements  View.OnClickListener,interfaces {

    // UI references.



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);
        // Set up the login form.
        Fragment miFragmento = new FragmentoLogin();
        getSupportFragmentManager().beginTransaction().replace(R.id.principal,miFragmento).commit();


    }


    @Override
    public void onClick(View v) {
        /*
        switch (v.getId()){
            case (v.findViewById(card_card_crearUser)):
                break;
        }*/
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}


