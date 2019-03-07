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
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
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
 * {@link FragmentoBuscarCliente.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentoBuscarCliente#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentoBuscarCliente extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SearchView cajaBuscarCliente;
    private ListView listaBuscarCliente;
    private RequestQueue request;
    private ProgressDialog progreso;
    private Ip ip = new Ip();
    private ArrayList<Cliente> arrayCliente;
    private AdaptadorCliente clienteAdapter;
    private Button regresar;


    private OnFragmentInteractionListener mListener;

    public FragmentoBuscarCliente() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentoBuscarCliente.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentoBuscarCliente newInstance(String param1, String param2) {
        FragmentoBuscarCliente fragment = new FragmentoBuscarCliente();
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
        View vista= inflater.inflate(R.layout.fragmento_buscar_cliente, container, false);
        cajaBuscarCliente =(SearchView) vista.findViewById(R.id.txt_busquedaCliente);
        listaBuscarCliente =(ListView) vista.findViewById(R.id.list_busquedaCliente);
        //btn_cancelarConsultaCliente
        regresar = (Button)vista.findViewById(R.id.btn_cancelarConsultaCliente);

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment miFragmento = new FragmentoMenu();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.principal,miFragmento).commit();
            }
        });




        cajaBuscarCliente.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                progreso = new ProgressDialog(getActivity());
                progreso.setMessage("cargando datos.....");
                progreso.show();
                request = Volley.newRequestQueue(getActivity());

                String url = "http://"+ip.getIp()+"/ProyectoCooperativa/api/cuenta/buscarCedula?cedula="+cajaBuscarCliente.getQuery();

                arrayCliente = new ArrayList<>();


                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET,url,null, new Response.Listener<JSONObject>(){

                            @Override
                            public void onResponse(JSONObject response) {
                                progreso.dismiss();
                                try {
                                    String responseAux = new String(response.toString().getBytes("UTF-8"));
                                    response = new JSONObject(responseAux);


                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                try {
                                    int clienteId =response.getInt("clienteId");
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
                                    boolean estado=response.getBoolean("estado");

                                    Cliente cli = new Cliente(clienteId,cedula,nombres,apellidos,genero,estadoCivil,fechaNacimiento,correo,telefono,celular,direccion,estado);
                                    arrayCliente.add(cli);
                                    clienteAdapter = new AdaptadorCliente(getActivity(),arrayCliente);
                                    listaBuscarCliente.setAdapter(clienteAdapter);
                                    clienteAdapter.notifyDataSetChanged();



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
