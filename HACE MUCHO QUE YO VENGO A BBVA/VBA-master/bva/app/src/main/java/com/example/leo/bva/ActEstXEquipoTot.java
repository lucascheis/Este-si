package com.example.leo.bva;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ActEstXEquipoTot extends AppCompatActivity {
    ProyectoFinalDB accesoBaseDatos;
    SQLiteDatabase baseDatos;
    TextView porcTl,txtPuntos,txtAsitencias,txtRebs,txtRobos,txtTapones,txtPerdidas,txtFaltas,txtPorDobles,txtPorTriples,txtPorTirosLibres;
    EstadisticasXJugador MisEstadisticasTotales;
    Integer CantidadPartidos=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_est_xequipo_tot);
        AbrirBaseDatos();
        ObtenerReferencias();
        MisEstadisticasTotales=TraerEstadisticasPartido();
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
    }
    private EstadisticasXJugador TraerEstadisticasPartido (){

        EstadisticasXJugador EstadisticasTotalesEquipo=new EstadisticasXJugador();
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
        Cursor MisRegistros = this.baseDatos.rawQuery("select Puntos,Asistencias,Robos,Faltas,Tapones,RebOff,RebDef,Perdidas,TirosLibErrados,DoblesErrados,TriplesErrados,TirosLibMetidos,DoblesMetidos,TriplesMetidos from EstadisticasXJugador",null);

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
}
