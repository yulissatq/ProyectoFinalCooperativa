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
import android.widget.SearchView;
import android.widget.Spinner;
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
 * {@link FragmetoEditarCliente.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmetoEditarCliente#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmetoEditarCliente extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText cajaApellidos,cajaNombres,cajaCelular,cajaCorreo,cajaDireccion,cajaTelefono;
    private Spinner listatipoCuenta,listaEstadoCivil;
    private Button btnGuardar;
    private SearchView searchViewModificar;

    private RequestQueue request;
    private ProgressDialog progreso;
    private  String datoTipoCuenta,datoEstadoCivil;
    private Ip ip=new Ip();
    private Button regresar;

    private OnFragmentInteractionListener mListener;

    public FragmetoEditarCliente() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmetoEditarCliente.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmetoEditarCliente newInstance(String param1, String param2) {
        FragmetoEditarCliente fragment = new FragmetoEditarCliente();
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

        View vista=inflater.inflate(R.layout.fragment_fragmeto_editar_cliente, container, false);
        // Inflate the layout for this fragment

        searchViewModificar =(SearchView)vista.findViewById(R.id.search_buscarModificar);
        cajaApellidos = (EditText) vista.findViewById(R.id.txt_apellidosModificar);
        cajaNombres = (EditText) vista.findViewById(R.id.txt_nombresModificar);
        cajaCelular = (EditText) vista.findViewById(R.id.txt_celularModificar);
        cajaCorreo = (EditText) vista.findViewById(R.id.txt_correoModificar);
        cajaDireccion = (EditText) vista.findViewById(R.id.txt_direccionModificar);


        cajaTelefono = (EditText) vista.findViewById(R.id.txt_telefonoModificar);


        listaEstadoCivil=(Spinner)vista.findViewById(R.id.sp_estadoCivilModificar);


        //btn_cancelarModificar

        regresar = (Button)vista.findViewById(R.id.btn_cancelarModificar);

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment miFragmento = new FragmentoMenu();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.principal,miFragmento).commit();
            }
        });



        //search parala bustqueda del clietne con la cedula
        final String[] cedulaBuscar = {""};

        searchViewModificar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                progreso = new ProgressDialog(getActivity());
                progreso.setMessage("cargando datos.....");
                progreso.show();
                request = Volley.newRequestQueue(getActivity());


                String url = "http://"+ip.getIp()+"/ProyectoCooperativa/api/cuenta/buscarCedula?cedula="+searchViewModificar.getQuery();



                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET,url,null, new Response.Listener<JSONObject>(){

                            @Override
                            public void onResponse(JSONObject response) {
                                progreso.dismiss();
                                try {
                                    String responseAux = new String("UTF-8");
                                    response = new JSONObject(responseAux);


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                try {

                                    String cedula = response.getString("cedula");
                                    String nombres=response.getString("nombres");
                                    String apellidos=response.getString("apellidos");
                                    String genero=response.getString("genero");
                                    String estadoCivil=response.getString("estadoCivil");
                                    String fechaNacimiento=response.getString("fechaNacimiento");
                                    String correo=response.getString("correo");
                                    String telefono=response.getString("telefono");
                                    String celular=response.getString("celular");
                                    String direccion=response.getString("direccion");
                                    cedulaBuscar[0] =cedula;

                                    cajaNombres.setText(nombres);
                                    cajaApellidos.setText(apellidos);
                                    cajaCorreo.setText(correo);
                                    cajaTelefono.setText(telefono);
                                    cajaCelular.setText(celular);
                                    cajaDireccion.setText(direccion);

                                    //estadoCivil
                                    final String[] tiposEstadoCivil = {"Soltero","Casado","Divorciado","Union libre"};

                                    //////////////////////////////////////////////////////////////
                                    String dato=tiposEstadoCivil[0];
                                    for (int i = 0 ; i<tiposEstadoCivil.length;i++){
                                        if(tiposEstadoCivil[i].equalsIgnoreCase(estadoCivil)){
                                            tiposEstadoCivil[i]=dato;
                                            tiposEstadoCivil[0]=estadoCivil;
                                        }
                                    }

                                    //////////////////////////////////////////////////////////////////////////
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






                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


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

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        ///carga busqueda fin





        btnGuardar =(Button)vista.findViewById(R.id.btn_enviarModificar);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progreso = new ProgressDialog(getActivity());
                progreso.setMessage("cargando datos.....");
                progreso.show();
                request = Volley.newRequestQueue(getActivity());


                String url = "http://"+ip.getIp()+"/ProyectoCooperativa/api/cuenta/modificarCliente";




                JSONObject cliente = new JSONObject();


                try {
                    //cuenta= new JSONObject(datos);
                    cliente.put("cedula",cedulaBuscar[0]);
                    cliente.put("apellidos",cajaApellidos.getText().toString());
                    cliente.put("nombres",cajaNombres.getText().toString());
                    cliente.put("celular",cajaCelular.getText().toString());
                    cliente.put("correo",cajaCorreo.getText().toString());
                    cliente.put("direccion",cajaDireccion.getText().toString());
                    cliente.put("estadoCivil",datoEstadoCivil);
                    cliente.put("telefono",cajaTelefono.getText().toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ///el metodo entra al error trabajar con el error como success

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.POST,url,cliente, new Response.Listener<JSONObject>(){

                            @Override
                            public void onResponse(JSONObject response) {

                                progreso.dismiss();
                                Toast.makeText(getActivity(), "Respuesta del server:"+response.toString(), Toast.LENGTH_SHORT).show();
                                Log.i("ffffff",response.toString());


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
