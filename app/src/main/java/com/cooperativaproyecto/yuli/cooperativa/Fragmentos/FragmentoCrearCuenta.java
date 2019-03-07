package com.cooperativaproyecto.yuli.cooperativa.Fragmentos;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cooperativaproyecto.yuli.cooperativa.Ip;
import com.cooperativaproyecto.yuli.cooperativa.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentoCrearCuenta.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentoCrearCuenta#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentoCrearCuenta extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText cajaNumero,cajasaldo,cajaCedula,cajaApellidos,cajaNombres,cajaCelular,cajaCorreo,cajaDireccion,cajaFechaNacimiento,cajaTelefono;
    private Spinner listatipoCuenta,listaEstadoCivil,listaGenero;
    private Button btnGuardar;
    private RequestQueue request;
    private ProgressDialog progreso;
    private  String datoTipoCuenta,datoEstadoCivil,datoGenero;
    private Ip ip=new Ip();
    private  Button regresar;



    private OnFragmentInteractionListener mListener;

    public FragmentoCrearCuenta() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentoCrearCuenta.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentoCrearCuenta newInstance(String param1, String param2) {
        FragmentoCrearCuenta fragment = new FragmentoCrearCuenta();
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
        // Inflate the layout for this fragment
        View vista= inflater.inflate(R.layout.fragmento_crear_usuario, container, false);

        cajaNumero = (EditText) vista.findViewById(R.id.txt_NumeroCuenta);
        cajasaldo = (EditText) vista.findViewById(R.id.txt_saldo);
        cajaCedula = (EditText) vista.findViewById(R.id.txt_cedula_crear);
        cajaApellidos = (EditText) vista.findViewById(R.id.txt_apellidos);
        cajaNombres = (EditText) vista.findViewById(R.id.txt_nombres);
        cajaCelular = (EditText) vista.findViewById(R.id.txt_celular);
        cajaCorreo = (EditText) vista.findViewById(R.id.txt_correo);
        cajaDireccion = (EditText) vista.findViewById(R.id.txt_direccion);
        cajaNumero = (EditText) vista.findViewById(R.id.txt_NumeroCuenta);
        cajaFechaNacimiento = (EditText) vista.findViewById(R.id.txt_fechaNacimiento);
        cajaTelefono = (EditText) vista.findViewById(R.id.txt_telefono);

        listatipoCuenta =(Spinner)vista.findViewById(R.id.sp_tipoCuenta);
        listaEstadoCivil=(Spinner)vista.findViewById(R.id.sp_estadoCivil);
        listaGenero =(Spinner)vista.findViewById(R.id.sp_genero);

        //btn_cancelarCrear

        regresar = (Button)vista.findViewById(R.id.btn_cancelarCrear);

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment miFragmento = new FragmentoMenu();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.principal,miFragmento).commit();
            }
        });


        //tipos de cuenta
        final String[] tiposCuenta = {"Ahorros","Corriente","Estudiantil"};
        listatipoCuenta.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, tiposCuenta));
        listatipoCuenta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                datoTipoCuenta = tiposCuenta[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //estadoCivil
        final String[] tiposEstadoCivil = {"Soltero","Casado","Divorciado","Union libre"};
        listaEstadoCivil.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, tiposEstadoCivil));

        listaEstadoCivil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                datoEstadoCivil = tiposEstadoCivil[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //genero
        final String[] tiposGenero= {"Masculino","Femenino"};
        listaGenero.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, tiposGenero));
        listaGenero.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                datoGenero = tiposGenero[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        btnGuardar =(Button)vista.findViewById(R.id.btn_enviar);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progreso = new ProgressDialog(getActivity());
                progreso.setMessage("cargando datos.....");
                progreso.show();
                request = Volley.newRequestQueue(getActivity());


                String url = "http://"+ip.getIp()+"/ProyectoCooperativa/api/cuenta";



                JSONObject cuenta= new JSONObject();
                JSONObject cliente = new JSONObject();

                String cedula = cajaCedula.getText().toString();
                try {
                    //cuenta= new JSONObject(datos);
                    cliente.put("cedula",cajaCedula.getText().toString());
                    cliente.put("apellidos",cajaApellidos.getText().toString());
                    cliente.put("nombres",cajaNombres.getText().toString());
                    cliente.put("celular",cajaCelular.getText().toString());
                    cliente.put("correo",cajaCorreo.getText().toString());
                    cliente.put("direccion",cajaDireccion.getText().toString());
                    cliente.put("estadoCivil",datoEstadoCivil);
                    cliente.put("fechaNacimiento","1982-05-30T00:00:00-05:00");
                    cliente.put("genero",datoGenero);
                    cliente.put("telefono",cajaTelefono.getText().toString());
                    cliente.put("estado",true);

                    cuenta.put("estado",true);
                    cuenta.put("fechaApertura","2019-02-22T00:00:00-05:00");
                    cuenta.put("numero",cajaNumero.getText().toString());
                    cuenta.put("saldo",cajasaldo.getText().toString());
                    cuenta.put("tipoCuenta",datoTipoCuenta);
                    cuenta.put("clienteId",cliente);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.POST,url,cuenta, new Response.Listener<JSONObject>(){

                            @Override
                            public void onResponse(JSONObject response) {

                                progreso.dismiss();
                                Toast.makeText(getActivity(), "Respuesta del server:"+response.toString(), Toast.LENGTH_SHORT).show();
                                Log.i("ddddddddd",response.toString());


                            }
                        },new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO Auto-generated method stub
                                progreso.dismiss();
                                Toast.makeText(getActivity(), "Error en la conexión"+error.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });


                request.add(jsonObjectRequest);

            }
        });





        return vista;
    }

    private void  guardar(){
        progreso = new ProgressDialog(getActivity());
        progreso.setMessage("cargando datos.....");
        progreso.show();
        request = Volley.newRequestQueue(getActivity());

        Date d = new Date();
        String FechaActual = new SimpleDateFormat("yyyy-MM-dd").format(new Date());


        String datos="{\"estado\":true,\"fechaApertura\":\"2019-02-22T00:00:00-05:00\",\"numero\":\""+cajaCedula.getText().toString()+
                "\",\"saldo\":"+cajasaldo.getText().toString()
                +",\"tipoCuenta\":\"ahorros\",\"clienteId\":{\"cedula\":\"3333322\",\"apellidos\":\"prueba\",\"nombres\":\"nuevo\",\"celular\":\"0984932377\",\"correo\":\"prueba@gmail.com\",\"direccion\":\"Daniel\",\"estadoCivil\":\"Casado\",\"fechaNacimiento\":\"1982-05-30T00:00:00-05:00\",\"genero\":\"m\",\"telefono\":\"07111254\",\"estado\":true}}";
        String url = "http://192.168.1.6:8080/ProyectoCooperativa/api/cuenta";

        Log.i("datos enviados %%%%%%%",datos);
/*
         StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progreso.hide();
                        Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();


                    }
                }, new Response.ErrorListener() {
             @Override
             public void onErrorResponse(VolleyError error) {
                 Toast.makeText(getContext(),error.toString(),Toast.LENGTH_SHORT).show();
             }


        });
*/
        JSONObject cuenta=null;
        JSONObject cliente=null;
        try {
            //cuenta= new JSONObject(datos);

            cliente.put("cedula",cajaCedula.getText().toString());
            cliente.put("apellidos",cajaApellidos.getText().toString());
            cliente.put("nombres",cajaNombres.getText().toString());
            cliente.put("celular",cajaCelular.getText().toString());
            cliente.put("correo",cajaCorreo.getText().toString());
            cliente.put("direccion",cajaDireccion.getText().toString());
            cliente.put("estadoCivil",datoEstadoCivil);
            cliente.put("fechaNacimiento","1982-05-30T00:00:00-05:00");
            cliente.put("genero",datoGenero);
            cliente.put("telefono",cajaTelefono.getText().toString());
            cliente.put("estado",true);

            cuenta.put("estado",true);
            cuenta.put("fechaApertura","2019-02-22T00:00:00-05:00");
            cuenta.put("numero",cajaNumero.getText().toString());
            cuenta.put("tipoCuenta",datoTipoCuenta);
            cuenta.put("clienteId",cliente);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("ddddddddd",cuenta.toString());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST,url,cuenta, new Response.Listener<JSONObject>(){

                    @Override
                    public void onResponse(JSONObject response) {

                            progreso.dismiss();
                            Toast.makeText(getActivity(), "Respuesta del server:"+response.toString(), Toast.LENGTH_SHORT).show();
                            Log.i("ddddddddd",response.toString());


                    }
                },new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        progreso.dismiss();
                        Toast.makeText(getActivity(), "Error en la conexión"+error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });


        request.add(jsonObjectRequest);
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
