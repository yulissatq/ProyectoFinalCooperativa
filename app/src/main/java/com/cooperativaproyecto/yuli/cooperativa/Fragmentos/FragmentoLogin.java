package com.cooperativaproyecto.yuli.cooperativa.Fragmentos;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cooperativaproyecto.yuli.cooperativa.Adapter.AdaptadorCliente;
import com.cooperativaproyecto.yuli.cooperativa.Ip;
import com.cooperativaproyecto.yuli.cooperativa.R;
import com.cooperativaproyecto.yuli.cooperativa.modelo.Cliente;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentoLogin.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentoLogin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentoLogin extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView cajaUsuario;
    private EditText cajaContrasena;
    private CardView botonIngreso;
    private RequestQueue request;
    private ProgressDialog progreso;
    private Ip ip = new Ip();

    private OnFragmentInteractionListener mListener;

    public FragmentoLogin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentoLogin.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentoLogin newInstance(String param1, String param2) {
        FragmentoLogin fragment = new FragmentoLogin();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista =inflater.inflate(R.layout.fragment_login, container, false);
        // Inflate the layout for this fragment
        cajaUsuario = (EditText) vista.findViewById(R.id.txt_usuario);
//        getActivity().getActionBar().hide();
    //    getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        cajaContrasena = (EditText) vista.findViewById(R.id.txt_contrasena);

        botonIngreso= (CardView) vista.findViewById(R.id.btn_ingresar);

        botonIngreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //creamos el login para el usuario

                progreso = new ProgressDialog(getActivity());
                progreso.setMessage("cargando datos.....");
                progreso.show();
                request = Volley.newRequestQueue(getActivity());

                String url = "http://"+ip.getIp()+"/ProyectoCooperativa/api/cuenta/buscarUsuariosData";


                JSONObject datosEnviar = new JSONObject();
                try {
                    datosEnviar.put("nombre",cajaUsuario.getText());
                    datosEnviar.put("clave",cajaContrasena.getText());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.i("ddddddddddddddddddd",datosEnviar.toString());

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.POST,url,datosEnviar, new Response.Listener<JSONObject>(){

                            @Override
                            public void onResponse(JSONObject response) {
                                progreso.dismiss();
                                try {
                                    String responseAux = new String(response.toString().getBytes("ISO-8859-1"), "UTF-8");
                                    response = new JSONObject(responseAux);


                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Fragment miFragmento = new FragmentoMenu();
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.principal,miFragmento).commit();






                            }
                        },new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO Auto-generated method stub
                                progreso.dismiss();
                                Toast.makeText(getActivity(), "Introdusca correctamente el usuario y contraseña" ,Toast.LENGTH_SHORT).show();

                            }
                        });


                request.add(jsonObjectRequest);








            }
        });
        return vista;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
