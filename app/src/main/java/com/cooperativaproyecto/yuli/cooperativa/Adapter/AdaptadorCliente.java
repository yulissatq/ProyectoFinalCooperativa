package com.cooperativaproyecto.yuli.cooperativa.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cooperativaproyecto.yuli.cooperativa.R;
import com.cooperativaproyecto.yuli.cooperativa.modelo.Cliente;

import java.util.ArrayList;

public class AdaptadorCliente extends BaseAdapter {
    private Context contexto;
    private ArrayList<Cliente> clientes;
    private  boolean[]item ;

    public AdaptadorCliente(Context contexto, ArrayList<Cliente> clientes) {
        this.item= new boolean[clientes.size()];
        this.contexto = contexto;
        this.clientes = clientes;

    }


    @Override
    public int getCount() {

        return clientes.size();
    }

    @Override
    public Object getItem(int position) {
        return clientes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = View.inflate(contexto, R.layout.display_cliente,null);


        }

        TextView lblCedula = (TextView)convertView.findViewById(R.id.lbl_cedulaBusqueda);
        TextView lblNombre = (TextView)convertView.findViewById(R.id.lbl_nombreBusqueda);
        TextView lblApellido = (TextView)convertView.findViewById(R.id.lbl_apellidoBusqueda);
        TextView lblGenero = (TextView)convertView.findViewById(R.id.lbl_generoBusqueda);
        TextView lblEstadoCivil = (TextView)convertView.findViewById(R.id.lbl_estadoCivilBusqueda);
        TextView lblFechaNacimiento = (TextView)convertView.findViewById(R.id.lbl_FechaNacimientoBusqueda);
        TextView lblCorreo = (TextView)convertView.findViewById(R.id.lbl_correoBusqueda);
        TextView lblTelefono = (TextView)convertView.findViewById(R.id.lbl_telefonoBusqueda);
        TextView lblCelular = (TextView)convertView.findViewById(R.id.lbl_celularBusqueda);
        TextView lblDireccion = (TextView)convertView.findViewById(R.id.lbl_direccionBusqueda);
        TextView lblEstado = (TextView)convertView.findViewById(R.id.lbl_estadoBusqueda);





        Cliente clientesData = clientes.get(position);
        //new DownloadImageTask(imagen)
        //      .execute(zonasTuristicas.getImagen());
        lblCedula.setText(clientesData.getCedula());
        lblNombre.setText(clientesData.getNombres());
        lblApellido.setText(clientesData.getApellidos());
        lblGenero.setText(clientesData.getGenero());
        lblEstadoCivil.setText(clientesData.getEstadoCivil());
        lblFechaNacimiento.setText(clientesData.getFechaNacimiento());
        lblCorreo.setText(clientesData.getCorreo());
        lblTelefono.setText(clientesData.getTelefono());
        lblCelular.setText(clientesData.getCelular());
        lblDireccion.setText(clientesData.getDireccion());
        String estado="Inactivo";
        if(clientesData.isEstado()){
            estado="Activo";
        }

        lblEstado.setText(estado);


        return convertView;

    }

}
