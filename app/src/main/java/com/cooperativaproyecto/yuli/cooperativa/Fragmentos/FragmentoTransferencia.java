package com.cooperativaproyecto.yuli.cooperativa.Fragmentos;

import android.app.Dialog;
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
import android.widget.EditText;
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
 * {@link FragmentoTransferencia.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentoTransferencia#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentoTransferencia extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SearchView searchViewOrigen, searchViewDestino;
    private TextView lblNumeroOrigen, lblEstadoOrigen, lblSaldoOrigen, lblTipoCuentaOrigen, lblNumeroDestino, lblEstadoDestino, lblSaldoDestino, lblTipoCuentaDestino;
    private RequestQueue request;
    private ProgressDialog progreso;
    private Ip ip = new Ip();
    private Button btn_Transferencia;
    private  Button regresar;


    private OnFragmentInteractionListener mListener;

    public FragmentoTransferencia() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentoTransferencia.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentoTransferencia newInstance(String param1, String param2) {
        FragmentoTransferencia fragment = new FragmentoTransferencia();
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

        View vista=inflater.inflate(R.layout.fragmento_transferencia, container, false);
        lblNumeroOrigen = (TextView)vista.findViewById(R.id.lbl_numeroCuentaBusquedaOrigen);
        lblEstadoOrigen =(TextView)vista.findViewById(R.id.lbl_estadoCuentaBusquedaOrigen);
        lblSaldoOrigen =(TextView)vista.findViewById(R.id.lbl_saldoCuentaBusquedaOrigen);
        lblTipoCuentaOrigen =(TextView)vista.findViewById(R.id.lbl_tipoCuentaBusquedaOrigen);
        searchViewOrigen =(SearchView)vista.findViewById(R.id.search_busquedaTransfereaciaOrigen);

        //btn_transferir
        regresar = (Button)vista.findViewById(R.id.btn_cancelarTransferir);

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment miFragmento = new FragmentoMenu();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.principal,miFragmento).commit();
            }
        });

        searchViewOrigen.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                progreso = new ProgressDialog(getActivity());
                progreso.setMessage("cargando datos.....");
                progreso.show();
                request = Volley.newRequestQueue(getActivity());

                String url = "http://"+ip.getIp()+"/ProyectoCooperativa/api/cuenta/buscarCuenta?numero="+ searchViewOrigen.getQuery();




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

                                    boolean estado=response.getBoolean("estado");

                                    String estadoResult= "Inactivo";

                                    if(estado)
                                        estadoResult="Activo";

                                    lblEstadoOrigen.setText(estadoResult);
                                    lblNumeroOrigen.setText(numero);
                                    lblSaldoOrigen.setText(saldo);
                                    lblTipoCuentaOrigen.setText(tipoCuenta);



                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        },new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO Auto-generated method stub
                                progreso.dismiss();
                                Toast.makeText(getActivity(), "Cuenta no encontrada",Toast.LENGTH_SHORT).show();
                                lblEstadoOrigen.setText("");
                                lblNumeroOrigen.setText("");
                                lblSaldoOrigen.setText("");
                                lblTipoCuentaOrigen.setText("");
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


        lblNumeroDestino = (TextView)vista.findViewById(R.id.lbl_numeroCuentaBusquedaDestino);
        lblEstadoDestino =(TextView)vista.findViewById(R.id.lbl_estadoCuentaBusquedaDestino);
        lblSaldoDestino =(TextView)vista.findViewById(R.id.lbl_saldoCuentaBusquedaDestino);
        lblTipoCuentaDestino =(TextView)vista.findViewById(R.id.lbl_tipoCuentaBusquedaDestino);
        searchViewDestino =(SearchView)vista.findViewById(R.id.search_busquedaTransfereaciaDestino);

        searchViewDestino.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                progreso = new ProgressDialog(getActivity());
                progreso.setMessage("cargando datos.....");
                progreso.show();
                request = Volley.newRequestQueue(getActivity());

                String url = "http://"+ip.getIp()+"/ProyectoCooperativa/api/cuenta/buscarCuenta?numero="+ searchViewDestino.getQuery();




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

                                    boolean estado=response.getBoolean("estado");

                                    String estadoResult= "Inactivo";

                                    if(estado)
                                        estadoResult="Activo";

                                    lblEstadoDestino.setText(estadoResult);
                                    lblNumeroDestino.setText(numero);
                                    lblSaldoDestino.setText(saldo);
                                    lblTipoCuentaDestino.setText(tipoCuenta);



                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        },new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO Auto-generated method stub
                                progreso.dismiss();
                                Toast.makeText(getActivity(), "Cuenta no encontrada",Toast.LENGTH_SHORT).show();
                                lblEstadoDestino.setText("");
                                lblNumeroDestino.setText("");
                                lblSaldoDestino.setText("");
                                lblTipoCuentaDestino.setText("");
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

        btn_Transferencia =(Button)vista.findViewById(R.id.btn_transferir);

        btn_Transferencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lblNumeroOrigen==null||lblNumeroDestino==null){
                    Toast.makeText(getActivity(),"Asegurese de buscar las dos cuentas",Toast.LENGTH_LONG).show();


                }else if(lblNumeroOrigen.getText().toString().equalsIgnoreCase("")||lblNumeroDestino.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(getActivity(),"Asegurese de buscar las dos cuentas",Toast.LENGTH_LONG).show();
                }else{

                    final Dialog dlgTransferir = new Dialog(getContext());
                    dlgTransferir.setContentView(R.layout.dialog_transferencia);

                    //control de los inputs del dialogo
                    final EditText cajaMontoTransferir = (EditText) dlgTransferir.findViewById(R.id.txt_montoTransferir);
                    final EditText cajaDescripcionTransferir = (EditText) dlgTransferir.findViewById(R.id.txt_descripcionTansferir);

                    Button btnDialogoGenerarTransferencia=(Button)dlgTransferir.findViewById(R.id.btn_dialogoTransferir);



                    btnDialogoGenerarTransferencia.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final double saldoActual = Double.parseDouble(lblSaldoOrigen.getText().toString());
                            final double saldoTransferir= Double.parseDouble(cajaMontoTransferir.getText().toString());

                            if(saldoTransferir>saldoActual){
                                Toast.makeText(getActivity(),"El monto es mayor al saldo actual",Toast.LENGTH_LONG).show();
                            }else if (cajaDescripcionTransferir.getText().toString().equalsIgnoreCase("")||cajaMontoTransferir.getText().toString().equalsIgnoreCase("")){
                                Toast.makeText(getActivity(),"Ingrese monto y descripcion",Toast.LENGTH_LONG).show();
                            }else{

                                final JSONObject datoDescripcion= new JSONObject();

                                try {
                                    datoDescripcion.put("numeroR",searchViewOrigen.getQuery());
                                    datoDescripcion.put("numeroD",searchViewDestino.getQuery());


                                datoDescripcion.put("valor",cajaMontoTransferir.getText());
                                datoDescripcion.put("descripcion",cajaDescripcionTransferir.getText());



                                            //enviamos la peticion al servidor
                                            progreso = new ProgressDialog(getActivity());
                                            progreso.setMessage("cargando datos.....");
                                            progreso.show();
                                            request = Volley.newRequestQueue(getActivity());

                                            String urlDeposito = "http://"+ip.getIp()+"/ProyectoCooperativa/api/cuenta/transferencia";

                                            Log.i("ddddddddddddddddddddd",datoDescripcion.toString());

                                            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                                                    (Request.Method.POST,urlDeposito,datoDescripcion, new Response.Listener<JSONObject>(){

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
                                                            Toast.makeText(getActivity(), "Transferencia realizadacon exito",Toast.LENGTH_LONG).show();
                                                            cajaMontoTransferir.setText("");
                                                            cajaDescripcionTransferir.setText("");
                                                            dlgTransferir.hide();


                                                        }
                                                    },new Response.ErrorListener() {

                                                        @Override
                                                        public void onErrorResponse(VolleyError error) {
                                                            // TODO Auto-generated method stub
                                                            progreso.dismiss();
                                                            Toast.makeText(getActivity(), "A ocurrido un error al realizar la transferencia",Toast.LENGTH_LONG).show();

                                                        }
                                                    });


                                            request.add(jsonObjectRequest);




                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }


                        }
                    });

                    dlgTransferir.show();

                }
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
