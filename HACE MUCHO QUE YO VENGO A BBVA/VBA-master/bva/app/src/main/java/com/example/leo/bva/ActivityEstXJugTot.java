package com.example.leo.bva;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ActivityEstXJugTot extends AppCompatActivity {

    ProyectoFinalDB accesoBaseDatos;
    SQLiteDatabase baseDatos;
    TextView porcTl,txtPuntos,txtAsitencias,txtRebs,txtRobos,txtTapones,txtPerdidas,txtFaltas,txtPorDobles,txtPorTriples,txtPorTirosLibres;
    EstadisticasXJugador MisEstadisticasTotales;
    Integer CantidadPartidos=0;
    ArrayList<Jugador> ListaNombreJugadores = new ArrayList<Jugador>();
    ArrayList<ListaJugador> ListarJugadores = new ArrayList<>();
    ListView ListaMostrar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_est_xjug_tot);
        AbrirBaseDatos();
        ObtenerReferencias();


        ListaNombreJugadores = TraerTodosJugadores();
         ArrayAdapter<Jugador> localArrayAdapter = new ArrayAdapter(this,  android.R.layout.simple_list_item_1, ListaNombreJugadores);
        this.ListaMostrar.setAdapter(localArrayAdapter);

        //
        // jugadoresView();



        ListaMostrar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Jugador mijug = new Jugador();
                mijug = ListaNombreJugadores.get(i);
                MisEstadisticasTotales=TraerEstadisticasPartido(mijug.id);
                Integer TirosLibTirados = MisEstadisticasTotales.TirosLibErrados+MisEstadisticasTotales.TirosLibMetidos;
                Double tltirados = Double.parseDouble(TirosLibTirados.toString());
                Double tlmetidos = Double.parseDouble(MisEstadisticasTotales.TirosLibMetidos.toString());
                Double porTL = (tlmetidos*100)/tltirados;

                Integer DoblesTirados = MisEstadisticasTotales.DoblesErrados+MisEstadisticasTotales.DoblesMetidos;
                Double doblestirados = Double.parseDouble(DoblesTirados.toString());
                Double doblesmetidos = Double.parseDouble(MisEstadisticasTotales.DoblesMetidos.toString());
                Double pordobles = (doblesmetidos*100)/doblestirados;

                Integer TriplesTirados = MisEstadisticasTotales.TriplesErrados+MisEstadisticasTotales.TriplesMetidos;
                Double triplestirados = Double.parseDouble(TriplesTirados.toString());
                Double triplesmetidos = Double.parseDouble(MisEstadisticasTotales.TriplesMetidos.toString());
                Double porctriples = (triplesmetidos*100)/triplestirados;


                txtPuntos.setText("Puntos: " + MisEstadisticasTotales.Puntos/CantidadPartidos);
                txtAsitencias.setText("Asistencias: " + MisEstadisticasTotales.Asistencias/CantidadPartidos);
                txtPerdidas.setText("Perdidas: " + MisEstadisticasTotales.Perdidas/CantidadPartidos);
                txtFaltas.setText("Faltas: " + MisEstadisticasTotales.Faltas/CantidadPartidos);
                txtTapones.setText("Tapones: " + MisEstadisticasTotales.Tapones/CantidadPartidos);
                txtRobos.setText("Robos: " + MisEstadisticasTotales.Robos/CantidadPartidos);
                Integer Rebotes = MisEstadisticasTotales.RebOff + MisEstadisticasTotales.RebDef;
                txtRebs.setText("Rebotes: " + Rebotes/CantidadPartidos);
                txtPorTirosLibres.setText("%1: "+ String.format("%.2f", porTL));
                txtPorDobles.setText("%2: "+String.format("%.2f", pordobles));
                txtPorTriples.setText("%3: "+ String.format("%.2f", porctriples));

            }
        });


    }

    private ArrayList<Jugador> TraerTodosJugadores() {
        ArrayList localArrayList = new ArrayList();
        Log.d("SQLITE", "Ejecuto la lectura de todos");
        Cursor MisRegistros = this.baseDatos.rawQuery("select nombre,edad,altura,posicion,mail,direccion,peso,rowid from Jugadores", null);
        Log.d("SQLITE", "Verifico si trajo registros");
        if (MisRegistros.moveToFirst())
            do {
                Jugador MiJugador = new Jugador();
                Log.d("sql", "Cargarjugador provisorio");
                MiJugador.Nombre = MisRegistros.getString(0);
                MiJugador.Edad = MisRegistros.getString(1);
                MiJugador.Altura = MisRegistros.getString(2);
                MiJugador.Posicion = MisRegistros.getString(3);
                MiJugador.Mail = MisRegistros.getString(4);
                MiJugador.Direccion = MisRegistros.getString(5);
                MiJugador.Peso = MisRegistros.getString(6);
                MiJugador.id = MisRegistros.getString(7);

                ListarJugadores.add((new ListaJugador(MiJugador.Nombre.toString(), MiJugador.Posicion.toString(), R.drawable.pelota)));
                localArrayList.add(MiJugador);
            }
            while (MisRegistros.moveToNext() == true);
        else {
            Toast.makeText(getApplicationContext(), "Null en mis registros", Toast.LENGTH_LONG).show();
        }
        return localArrayList;
    }
    private void ObtenerReferencias()
    {
        porcTl = (TextView) findViewById(R.id.txtPorcTirosLib);
        txtPuntos = (TextView) findViewById(R.id.txtPuntos);
        txtAsitencias = (TextView) findViewById(R.id.txtAsitencias);
        txtRebs = (TextView) findViewById(R.id.txtRebs);
        txtRobos=(TextView)findViewById(R.id.txtRobos);
        txtTapones=(TextView)findViewById(R.id.txtTapones);
        txtPerdidas=(TextView)findViewById(R.id.txtPerdidas);
        txtFaltas=(TextView)findViewById(R.id.txtFaltas);
        txtPorDobles=(TextView)findViewById(R.id.txtPorcDobles);
        txtPorTriples=(TextView)findViewById(R.id.txtPorcTripl);
        txtPorTirosLibres= (TextView) findViewById(R.id.txtPorcTirosLib);
        ListaMostrar=(ListView) findViewById(R.id.lstJugador);
    }
    private EstadisticasXJugador TraerEstadisticasPartido (String IDJUG){

        EstadisticasXJugador EstadisticasTotalesEquipo=new EstadisticasXJugador();
        CantidadPartidos=0;
        EstadisticasTotalesEquipo.Puntos=0;
        EstadisticasTotalesEquipo.Asistencias=0;
        EstadisticasTotalesEquipo.TriplesMetidos=0;
        EstadisticasTotalesEquipo.TriplesErrados=0;
        EstadisticasTotalesEquipo.DoblesErrados=0;
        EstadisticasTotalesEquipo.DoblesMetidos=0;
        EstadisticasTotalesEquipo.TirosLibMetidos=0;
        EstadisticasTotalesEquipo.TirosLibErrados=0;
        EstadisticasTotalesEquipo.RebOff=0;
        EstadisticasTotalesEquipo.RebDef=0;
        EstadisticasTotalesEquipo.Tapones=0;
        EstadisticasTotalesEquipo.Faltas=0;
        EstadisticasTotalesEquipo.Perdidas=0;
        EstadisticasTotalesEquipo.Robos=0;



        EstadisticasXJugador misEstadisticas = new EstadisticasXJugador();
        Cursor MisRegistros = this.baseDatos.rawQuery("select Puntos,Asistencias,Robos,Faltas,Tapones,RebOff,RebDef,Perdidas,TirosLibErrados,DoblesErrados,TriplesErrados,TirosLibMetidos,DoblesMetidos,TriplesMetidos from EstadisticasXJugador WHERE IdJugador="+IDJUG,null);

        if(MisRegistros.moveToFirst())
        {
            do {
                misEstadisticas.Puntos = MisRegistros.getInt(0);
                EstadisticasTotalesEquipo.Puntos += misEstadisticas.Puntos;

                misEstadisticas.Asistencias = MisRegistros.getInt(1);
                EstadisticasTotalesEquipo.Asistencias += misEstadisticas.Asistencias;

                misEstadisticas.Robos = MisRegistros.getInt(2);
                EstadisticasTotalesEquipo.Robos += misEstadisticas.Robos;

                misEstadisticas.Faltas = MisRegistros.getInt(3);
                EstadisticasTotalesEquipo.Faltas += misEstadisticas.Faltas;

                misEstadisticas.Tapones = MisRegistros.getInt(4);
                EstadisticasTotalesEquipo.Tapones += misEstadisticas.Tapones;

                misEstadisticas.RebOff = MisRegistros.getInt(5);
                EstadisticasTotalesEquipo.RebOff += misEstadisticas.RebOff;

                misEstadisticas.RebDef = MisRegistros.getInt(6);
                EstadisticasTotalesEquipo.RebDef += misEstadisticas.RebDef;

                misEstadisticas.Perdidas = MisRegistros.getInt(7);
                EstadisticasTotalesEquipo.Perdidas += misEstadisticas.Perdidas;

                misEstadisticas.TirosLibErrados = MisRegistros.getInt(8);
                EstadisticasTotalesEquipo.TirosLibErrados += misEstadisticas.TirosLibErrados;

                misEstadisticas.DoblesErrados = MisRegistros.getInt(9);
                EstadisticasTotalesEquipo.DoblesErrados += misEstadisticas.DoblesErrados;

                misEstadisticas.TriplesErrados = MisRegistros.getInt(10);
                EstadisticasTotalesEquipo.TriplesErrados += misEstadisticas.TriplesErrados;

                misEstadisticas.TirosLibMetidos = MisRegistros.getInt(11);
                EstadisticasTotalesEquipo.TirosLibMetidos += misEstadisticas.TirosLibMetidos;

                misEstadisticas.DoblesMetidos = MisRegistros.getInt(12);
                EstadisticasTotalesEquipo.DoblesMetidos += misEstadisticas.DoblesMetidos;

                misEstadisticas.TriplesMetidos = MisRegistros.getInt(13);
                EstadisticasTotalesEquipo.TriplesMetidos += misEstadisticas.TriplesMetidos;


                CantidadPartidos++;
            }while(MisRegistros.moveToNext()==true);

        }
        return EstadisticasTotalesEquipo;
    }

    private boolean AbrirBaseDatos() {

        Boolean BaseAbierta = false;
        Log.d("SQLite", "Inicializo el accesor de la base dandole por nombre el nombre de la base en el segundo parametro");
        accesoBaseDatos = new ProyectoFinalDB(this, "BaseBVA", null, 1);

        Log.d("SQLite", "Abro el modo escritura");
        baseDatos = accesoBaseDatos.getWritableDatabase();

        Log.d("SQLite", "Verifico que se abrio la base");

        if (baseDatos != null) {
            BaseAbierta = true;
            Log.d("SQLite", "Base Datos abierta");
        }
        return BaseAbierta;
    }
    public void jugadoresView() {

        ArrayAdapter<ListaJugador> adapter = new MyListAdapter();
        ListaMostrar.setAdapter(adapter);
    }

    private class MyListAdapter extends ArrayAdapter<ListaJugador> {

        public MyListAdapter() {
            super(ActivityEstXJugTot.this, R.layout.itemview, ListarJugadores);
        }

        public View getView(int Position, View convertView, ViewGroup parent) {


            View itemView = convertView;

            if(itemView==null) {
                itemView = getLayoutInflater().inflate(R.layout.itemview, parent, false);

                ListaJugador MiJugador = ListarJugadores.get(Position);
                ImageView imagen = (ImageView) itemView.findViewById(R.id.imageViewPelota);

                imagen.setImageResource(MiJugador.getPelota());

                TextView Nombre = (TextView) itemView.findViewById(R.id.txtNombrazo);
                String Name = MiJugador.getNombre();
                Nombre.setText(MiJugador.getNombre());

                TextView Posicione = (TextView) itemView.findViewById(R.id.txtPosicionazo);
                Posicione.setText(MiJugador.getPosicion());

            }
            return itemView;

        }
    }
}




