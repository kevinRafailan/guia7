package bone.guia7;

import android.content.Intent;
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

import modelo.AdaptadorCategoria;
import modelo.Categoria;
import modelo.DB;

public class MainActivity extends AppCompatActivity {
   private DB db;
    private AdaptadorCategoria adaptadorCategoria;
    private ListView listView;
    private TextView lblId_Cat;
    private EditText txtNombre_cat;
    private Button btnGuardar, btnEliminar, btnacceder;
    //lista de datos (categoria)
    private ArrayList<Categoria> lstCategoria;
    //sirve para manejar la eliminacion
    private Categoria categoria_temp= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //inicializando los controles

        lblId_Cat = (TextView) findViewById(R.id.lblId_cat_main);
        txtNombre_cat = (EditText) findViewById(R.id.txtCategoria);
        btnGuardar= (Button) findViewById(R.id.btnGuardar);
        btnEliminar= (Button) findViewById(R.id.btnEliminar);
        listView= (ListView) findViewById(R.id.lstCategoria);
        btnacceder = (Button) findViewById(R.id.btnAcceder);

        db  = new DB(this);
        lstCategoria= db.getArrayCategoria(db.getCursorCategoria());

        if(lstCategoria==null)// si no hay datos
        lstCategoria = new ArrayList<>();
        adaptadorCategoria = new AdaptadorCategoria(this, lstCategoria);
        listView.setAdapter(adaptadorCategoria);
        //listeners

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
                seleccionar(lstCategoria.get(position));
            }
        });
        //limpienado
        limpiar();
        btnacceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new Intent(MainActivity.this,Productos.class);
               intent.putExtra("ID",lblId_Cat.getText().toString());
                startActivity(intent);
            }
        });
    }

    private void guardar(){
        Categoria categoria = new Categoria(lblId_Cat.getText().toString(),txtNombre_cat.getText().toString());
        categoria_temp=null;
        if(db.guardar_O_ActualizarCategoria(categoria)){
            Toast.makeText(this,"Se Guardo categoria", Toast.LENGTH_SHORT).show();
            //Todo limpiar los que existen y agregar los nuevos
            lstCategoria.clear();
            lstCategoria.addAll(db.getArrayCategoria(db.getCursorCategoria()));
            adaptadorCategoria.notifyDataSetChanged();
            limpiar();

        }else{
            Toast.makeText(this,"Ocurrio un error al guardar",Toast.LENGTH_SHORT).show();
        }

        }
    private void eliminar(){
        if(categoria_temp!=null){
            db.borrarCategoria(categoria_temp.getId_categoria());
            lstCategoria.remove(categoria_temp);
            adaptadorCategoria.notifyDataSetChanged();
            categoria_temp=null;
            Toast.makeText(this, "Se elimino categoria", Toast.LENGTH_SHORT).show();
        }

    }
    private void seleccionar(Categoria categoria){

        categoria_temp= categoria;
        lblId_Cat.setText(categoria_temp.getId_categoria());
        txtNombre_cat.setText(categoria_temp.getNombre());
    }

    private void limpiar(){
        lblId_Cat.setText(null);
        txtNombre_cat.setText(null);
    }

    }

