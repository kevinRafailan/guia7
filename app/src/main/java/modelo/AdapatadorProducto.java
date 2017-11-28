package modelo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import bone.guia7.R;

/**
 * Created by devin on 26/11/2017.
 */

public class AdapatadorProducto extends ArrayAdapter<Producto> {
    public AdapatadorProducto(Context context, List<Producto> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obteniendo el dato
        Producto contacto = getItem(position);
        //TODO inicializando el layout correspondiente al item (Categoria)
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_producto, parent, false);
        }
        TextView lblNombre = (TextView) convertView.findViewById(R.id.lblnombreproducto);
        TextView lblId_prodcuto = (TextView) convertView.findViewById(R.id.lblId_producto);
        TextView lbldescripcion = (TextView) convertView.findViewById(R.id.lbldescripcion);
        TextView lblID_cate = (TextView) convertView.findViewById(R.id.lblidcat);
        lblNombre.setText(contacto.getNombre());
        lblId_prodcuto.setText(contacto.getId_categoria());
        lbldescripcion.setText(contacto.getId_producto());
        lblID_cate.setText(contacto.getId_categoria());
        // Return la convertView ya con los datos
        return convertView;
    }
}
