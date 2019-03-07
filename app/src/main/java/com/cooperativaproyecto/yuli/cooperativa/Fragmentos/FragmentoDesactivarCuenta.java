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
import android.widget.SearchView;
import android.widget.TextView;
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

import java.io.UnsupportedEncodingException;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentoDesactivarCuenta.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentoDesactivarCuenta#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentoDesactivarCuenta extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SearchView searchView;
    private ListView listaEstado;
    private Button enviarEstado;
    private TextView lblNumero,lblEstado,lblSaldo,lblTipoCuenta;
    private RequestQueue request;
    private ProgressDialog progreso;
    private Ip ip = new Ip();
    private Button regresar;

    private OnFragmentInteractionListener mListener;

    public FragmentoDesactivarCuenta() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentoDesactivarCuenta.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentoDesactivarCuenta newInstance(String param1, String param2) {
        FragmentoDesactivarCuenta fragment = new FragmentoDesactivarCuenta();
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
        View vista=inflater.inflate(R.layout.fragmento_desactivar_cuenta, container, false);
        searchView = (SearchView)vista.findViewById(R.id.search_buscarModificar);
        enviarEstado=(Button) vista.findViewById(R.id.btn_cambiarEstado);

        lblNumero = (TextView)vista.findViewById(R.id.lbl_numeroCuentaBusquedaE);
        lblEstado=(TextView)vista.findViewById(R.id.lbl_estadoCuentaBusquedaE);
        lblSaldo=(TextView)vista.findViewById(R.id.lbl_saldoCuentaBusquedaE);
        lblTipoCuenta=(TextView)vista.findViewById(R.id.lbl_tipoCuentaBusquedaE);
        searchView =(SearchView)vista.findViewById(R.id.search_busquedaEstado);

        //btn_cancelarDesactivarCuenta
        regresar = (Button)vista.findViewById(R.id.btn_cancelarDesactivarCuenta);

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment miFragmento = new FragmentoMenu();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.principal,miFragmento).commit();
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                progreso = new ProgressDialog(getActivity());
                progreso.setMessage("cargando datos.....");
                progreso.show();
                request = Volley.newRequestQueue(getActivity());

                String url = "http://"+ip.getIp()+"/ProyectoCooperativa/api/cuenta/buscarCuenta?numero="+searchView.getQuery();




                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET,url,null, new Response.Listener<JSONObject>(){

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

                                try {

                                    String numero = response.getString("numero");
                                    String saldo=response.getString("saldo");
                                    String tipoCuenta=response.getString("tipoCuenta");
                                    final boolean estado=response.getBoolean("estado");

                                    String estadoResult= "Inactivo";

                                    if(estado)
                                        estadoResult="Activo";

                                    lblEstado.setText(estadoResult);
                                    lblNumero.setText(numero);
                                    lblSaldo.setText(saldo);
                                    lblTipoCuenta.setText(tipoCuenta);
                                    enviarEstado.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            progreso = new ProgressDialog(getActivity());
                                            progreso.setMessage("cargando datos.....");
                                            progreso.show();
                                            request = Volley.newRequestQueue(getActivity());
                                            boolean estEnviar=true;
                                            if(estado){
                                                estEnviar=false;
                                            }

                                            String url = "http://"+ip.getIp()+"/ProyectoCooperativa/api/cuenta/cambiarEstadoCuentas?cedula="+searchView.getQuery()+"&estado="+estEnviar;

                                            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                                                    (Request.Method.GET,url,null, new Response.Listener<JSONObject>(){

                                                        @Override
                                                        public void onResponse(JSONObject response) {
                                                            progreso.dismiss();

                                                            lblEstado.setText("");
                                                            lblNumero.setText("");
                                                            lblSaldo.setText("");
                                                            lblTipoCuenta.setText("");
                                                            Toast.makeText(getActivity(), "Estado cambiado con exito",Toast.LENGTH_LONG).show();


                                                        }
                                                    },new Response.ErrorListener() {

                                                        @Override
                                                        public void onErrorResponse(VolleyError error) {
                                                            // TODO Auto-generated method stub
                                                            progreso.dismiss();
                                                            Toast.makeText(getActivity(), "Error al cambiar estado",Toast.LENGTH_LONG).show();

                                                        }
                                                    });


                                            request.add(jsonObjectRequest);
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
                                Toast.makeText(getActivity(), "Usuario no encontrado",Toast.LENGTH_SHORT).show();
                                lblEstado.setText("");
                                lblNumero.setText("");
                                lblSaldo.setText("");
                                lblTipoCuenta.setText("");
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


    private void enviarEstado(){






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
