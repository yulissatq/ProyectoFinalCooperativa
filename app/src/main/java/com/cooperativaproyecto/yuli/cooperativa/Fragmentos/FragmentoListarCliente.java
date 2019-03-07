package com.cooperativaproyecto.yuli.cooperativa.Fragmentos;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cooperativaproyecto.yuli.cooperativa.Adapter.AdaptadorCliente;
import com.cooperativaproyecto.yuli.cooperativa.Ip;
import com.cooperativaproyecto.yuli.cooperativa.R;
import com.cooperativaproyecto.yuli.cooperativa.modelo.Cliente;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentoListarCliente.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentoListarCliente#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentoListarCliente extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListView listaClientes;
    private RequestQueue request;
    private ProgressDialog progreso;
    private Ip ip = new Ip();
    private ArrayList<Cliente> arrayCliente;
    private AdaptadorCliente clienteAdapter;
private Button regresar;

    private OnFragmentInteractionListener mListener;

    public FragmentoListarCliente() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentoListarCliente.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentoListarCliente newInstance(String param1, String param2) {
        FragmentoListarCliente fragment = new FragmentoListarCliente();
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
        View vista= inflater.inflate(R.layout.fragmento_listar_cliente, container, false);
        listaClientes = vista.findViewById(R.id.list_ClietesTodos);
        //btn_cancelarListarCliente
        regresar = (Button)vista.findViewById(R.id.btn_cancelarListarCliente);

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment miFragmento = new FragmentoMenu();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.principal,miFragmento).commit();
            }
        });




        cargarTodo();

        return vista;
    }

    private  void cargarTodo(){
        progreso = new ProgressDialog(getActivity());
        progreso.setMessage("cargando datos.....");
        progreso.show();
        request = Volley.newRequestQueue(getActivity());

        String url = "http://"+ip.getIp()+"/ProyectoCooperativa/api/cuenta/lista";

        arrayCliente = new ArrayList<>();


        StringRequest jsonObjectRequest = new StringRequest
                (Request.Method.GET,url, new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        progreso.dismiss();
                        try {
                            response = new String(response.getBytes("ISO-8859-1"), "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        JSONArray jsonArray= new JSONArray();
                        try {
                             jsonArray= new JSONArray(response);

                             for (int i =0; i<jsonArray.length();i++){
                               JSONObject jsonObjectAux =  jsonArray.getJSONObject(i);
                                 int clienteId =jsonObjectAux.getInt("clienteId");
                                 String cedula = jsonObjectAux.getString("cedula");
                                 String nombres=jsonObjectAux.getString("nombres");
                                 String apellidos=jsonObjectAux.getString("apellidos");
                                 String genero=jsonObjectAux.getString("genero");
                                 String estadoCivil=jsonObjectAux.getString("estadoCivil");
                                 String fechaNacimiento=jsonObjectAux.getString("fechaNacimiento");
                                 String correo=jsonObjectAux.getString("correo");
                                 String telefono=jsonObjectAux.getString("telefono");
                                 String celular=jsonObjectAux.getString("celular");
                                 String direccion=jsonObjectAux.getString("direccion");
                                 boolean estado=jsonObjectAux.getBoolean("estado");

                                 Cliente cli = new Cliente(clienteId,cedula,nombres,apellidos,genero,estadoCivil,fechaNacimiento,correo,telefono,celular,direccion,estado);
                                 arrayCliente.add(cli);


                             }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        clienteAdapter = new AdaptadorCliente(getActivity(),arrayCliente);
                        listaClientes.setAdapter(clienteAdapter);
                        clienteAdapter.notifyDataSetChanged();


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
    };



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
