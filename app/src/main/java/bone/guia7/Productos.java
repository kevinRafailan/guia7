package bone.guia7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import modelo.AdapatadorProducto;
import modelo.DB;
import modelo.Producto;

public class Productos extends AppCompatActivity {
    private DB db;
    private AdapatadorProducto adapatadorProducto;
    private ListView listView;
    private TextView lblId_producto,lblId_categoria;
    private EditText txtNombre_prod, txtDescripcion_prod;
    private Button btnGuardar, btnEliminar;
    //lista de datos (productos)
    private ArrayList<Producto> lstProductos;
    String valor;
    //sirve para manejar la eliminacion
    private Producto productos_temp = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);
        //incializando los controles
        lblId_producto= (TextView) findViewById(R.id.lblId_producto);
        lblId_categoria= (TextView) findViewById(R.id.txtid);
        txtNombre_prod= (EditText) findViewById(R.id.txtProdcuto);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnEliminar= (Button) findViewById(R.id.btnEliminar);
        txtDescripcion_prod= (EditText) findViewById(R.id.txtCategoria);
        listView= (ListView) findViewById(R.id.lstProdcuto);
       valor=getIntent().getStringExtra("ID");
        lblId_categoria.setText(valor);
        //inicializando lista y db
        db= new DB(this);
        lstProductos= db.getArrayProducto(
                db.getCursorProducto()
        );
        if(lstProductos==null)// si no hay datos
            lstProductos= new ArrayList<>();
        adapatadorProducto = new AdapatadorProducto(this,lstProductos);
        listView.setAdapter(adapatadorProducto);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar();
            }
        });
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar();
            }
        });
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                seleccionar(lstProductos.get(position));
            }
        });
        //limpiando
        limpiar();
    }
   private void guardar(){
        Producto producto = new Producto(lblId_producto.getText().toString(),
                txtNombre_prod.getText().toString(),
                txtNombre_prod.getText().toString(),
                lblId_categoria.getText().toString()


        );
        productos_temp=null;
        if(db.guardar_O_ActualizarProducto(producto)){
            Toast.makeText(this,"Se guardo producto",Toast.LENGTH_SHORT).show();
           //Todo limpiar los que existen y agregar los nuevo
            lstProductos.clear();
            lstProductos.addAll(db.getArrayProducto(db.getCursorProducto()));
            adapatadorProducto.notifyDataSetChanged();
            limpiar();
        }else{
            Toast.makeText(this,"Ocurrio un error al guardar", Toast.LENGTH_SHORT).show();
        }

    }
    private void eliminar(){
        if(productos_temp!=null){
            db.borrarProducto(productos_temp.getId_producto());
            lstProductos.remove(productos_temp);
            adapatadorProducto.notifyDataSetChanged();
            productos_temp= null;
            Toast.makeText(this,"Se elimino producto", Toast.LENGTH_SHORT).show();
            limpiar();
        }

    }
    private void seleccionar(Producto prodcuto){
        productos_temp = prodcuto;
        lblId_producto.setText(productos_temp.getId_producto());
        txtNombre_prod.setText(productos_temp.getNombre());
        txtDescripcion_prod.setText(productos_temp.getDescripcion());
        lblId_categoria.setText(productos_temp.getId_categoria());

    }
    private void limpiar(){
        lblId_producto.setText(null);
        txtNombre_prod.setText(null);
        txtDescripcion_prod.setText(null);
        lblId_categoria.setText(null);
    }
}
