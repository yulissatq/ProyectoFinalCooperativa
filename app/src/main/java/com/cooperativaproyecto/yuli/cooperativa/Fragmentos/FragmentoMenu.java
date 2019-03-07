package com.cooperativaproyecto.yuli.cooperativa.Fragmentos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cooperativaproyecto.yuli.cooperativa.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentoMenu.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentoMenu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentoMenu extends Fragment implements  View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private CardView btnCrearUsuario,btnBuscarCliente,btnBuscarTodoCliente,btnModificarCliente,btnConsultaCuenta,btnModificarEstadoCuenta,
    btnDepositar,btnRetirar,btnTransferencia,btnCerrarCesion;

    private OnFragmentInteractionListener mListener;

    public FragmentoMenu() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentoMenu.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentoMenu newInstance(String param1, String param2) {
        FragmentoMenu fragment = new FragmentoMenu();
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
        View vista= inflater.inflate(R.layout.fragmento_menu, container, false);
        // Inflate the layout for this fragment

        btnCrearUsuario =(CardView) vista.findViewById(R.id.card_crearUser);
        btnCrearUsuario.setOnClickListener(this);

        btnBuscarCliente =(CardView) vista.findViewById(R.id.card_buscarCliente);
        btnBuscarCliente.setOnClickListener(this);

        btnBuscarTodoCliente =(CardView)vista.findViewById(R.id.card_buscarTodosClientes);
        btnBuscarTodoCliente.setOnClickListener(this);

        btnModificarCliente=(CardView)vista.findViewById(R.id.card_modificar);
        btnModificarCliente.setOnClickListener(this);

        btnConsultaCuenta=(CardView)vista.findViewById(R.id.card_consultaCuenta);
        btnConsultaCuenta.setOnClickListener(this);

        btnModificarEstadoCuenta =(CardView)vista.findViewById(R.id.card_desactivar);
        btnModificarEstadoCuenta.setOnClickListener(this);

        btnDepositar =(CardView)vista.findViewById(R.id.card_deposito);
        btnDepositar.setOnClickListener(this);

        btnRetirar = (CardView)vista.findViewById(R.id.card_retiro);
        btnRetirar.setOnClickListener(this);

        btnTransferencia = (CardView)vista.findViewById(R.id.card_transferencia);
        btnTransferencia.setOnClickListener(this);

        btnCerrarCesion = (CardView) vista.findViewById(R.id.card_cerrarSesion);
        btnCerrarCesion.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        Fragment miFragmento = null;
        switch (v.getId()){
            case (R.id.card_crearUser):
                miFragmento = new FragmentoCrearCuenta();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.principal,miFragmento).commit();

                break;
            case (R.id.card_buscarCliente):
                miFragmento = new FragmentoBuscarCliente();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.principal,miFragmento).commit();

                break;

            case (R.id.card_buscarTodosClientes):
                miFragmento = new FragmentoListarCliente();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.principal,miFragmento).commit();

                break;
            case(R.id.card_modificar):
                miFragmento = new FragmetoEditarCliente();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.principal,miFragmento).commit();

                break;

            case(R.id.card_consultaCuenta):
               Fragment miFragmento2 = new FragmentoConsultaCuenta();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.principal,miFragmento2).commit();

                break;

            case(R.id.card_desactivar):
                miFragmento = new FragmentoDesactivarCuenta();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.principal,miFragmento).commit();

                break;

            case (R.id.card_deposito):
                miFragmento = new FragmentoDeposito();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.principal,miFragmento).commit();

                break;

            case (R.id.card_retiro):
                miFragmento = new FragmentoRetiro();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.principal,miFragmento).commit();

                break;
            case (R.id.card_transferencia):
                miFragmento = new FragmentoTransferencia();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.principal,miFragmento).commit();

                break;

            case (R.id.card_cerrarSesion):
                miFragmento = new FragmentoLogin();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.principal,miFragmento).commit();

                break;

        }
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
