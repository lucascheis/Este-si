package com.example.leo.bva;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ActivityDatosPartido extends AppCompatActivity {
    ProyectoFinalDB accesoBaseDatos;
    View vwAnterior = null;
    SQLiteDatabase baseDatos;
    ArrayList<Integer> TraerJugadoresId = new ArrayList<>();
    ArrayList<Jugador> ConvocadosArray = new ArrayList<>();
    RadioButton rdbPorEquipo,rdbPorJugador;
    RadioGroup rgroup;
    ListView lstConvocados;
    TextView porcTl,txtPuntos,txtAsitencias,txtRebs,txtRobos,txtTapones,txtPerdidas,txtFaltas,txtPorDobles,txtPorTriples,txtPorTirosLibres;
    EstadisticasXJugador MisEstadisticasTotales;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_datos_partido);
        ObtenerReferencias();
        Bundle datos = this.getIntent().getExtras();
        String idPartido = datos.getString("id");
        final String IdPartido = idPartido;
        AbrirBaseDatos();

        RadioGroup ItemtypeGroup = (RadioGroup) findViewById(R.id.rdg);
        RadioButton providerRadio = (RadioButton) findViewById(R.id.rdbPorEquipo);
        providerRadio.performClick();

        TraerJugadoresId = TraerJugadores(idPartido);
        ConvocadosArray = TraerConvocados(TraerJugadoresId);

        ArrayAdapter<Jugador> adapter = new ArrayAdapter<Jugador>(this,android.R.layout.simple_list_item_1, ConvocadosArray);
        lstConvocados.setAdapter(adapter);

        MisEstadisticasTotales=TraerEstadisticasPartido(IdPartido);


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

        txtPuntos.setText("Puntos: " + MisEstadisticasTotales.Puntos.toString());
        txtAsitencias.setText("Asistencias: " + MisEstadisticasTotales.Asistencias.toString());
        txtPerdidas.setText("Perdidas: " + MisEstadisticasTotales.Perdidas.toString());
        txtFaltas.setText("Faltas: " + MisEstadisticasTotales.Faltas.toString());
        txtTapones.setText("Tapones: " + MisEstadisticasTotales.Tapones.toString());
        txtRobos.setText("Robos: " + MisEstadisticasTotales.Robos.toString());
        Integer Rebotes = MisEstadisticasTotales.RebOff + MisEstadisticasTotales.RebDef;
        txtRebs.setText("Rebotes: " + Rebotes.toString());

        if(porTL.isNaN()==false) {
            txtPorTirosLibres.setText("%1: " + String.format("%.2f", porTL));
        }
        else
        {
            txtPorTirosLibres.setText("%1:0");
        }

        if(pordobles.isNaN()==false) {
            txtPorDobles.setText("%2: " + String.format("%.2f", pordobles));
        }
        else{
            txtPorDobles.setText("%2:0");
        }
        if(porctriples.isNaN()==false){
            txtPorTriples.setText("%3: "+ String.format("%.2f", porctriples));

        }
        else
        {
            txtPorTriples.setText("%3:0");

        }



        ItemtypeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rdbPorEquipo) {
                    Log.d("Ejectuando:","POR EQUIPO");
                    if(rdbPorJugador.isChecked()) {
                        lstConvocados.setEnabled(true);
                    }
                    else
                    {
                        lstConvocados.setEnabled(false);
                    }

                    MisEstadisticasTotales=TraerEstadisticasPartido(IdPartido);


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

                    txtPuntos.setText("Puntos: " + MisEstadisticasTotales.Puntos.toString());
                    txtAsitencias.setText("Asistencias: " + MisEstadisticasTotales.Asistencias.toString());
                    txtPerdidas.setText("Perdidas: " + MisEstadisticasTotales.Perdidas.toString());
                    txtFaltas.setText("Faltas: " + MisEstadisticasTotales.Faltas.toString());
                    txtTapones.setText("Tapones: " + MisEstadisticasTotales.Tapones.toString());
                    txtRobos.setText("Robos: " + MisEstadisticasTotales.Robos.toString());
                    Integer Rebotes = MisEstadisticasTotales.RebOff + MisEstadisticasTotales.RebDef;
                    txtRebs.setText("Rebotes: " + Rebotes.toString());

                    if(porTL.isNaN()==false) {
                        txtPorTirosLibres.setText("%1: " + String.format("%.2f", porTL));
                    }
                    else
                    {
                        txtPorTirosLibres.setText("%1:0");
                    }

                    if(pordobles.isNaN()==false) {
                        txtPorDobles.setText("%2: " + String.format("%.2f", pordobles));
                    }
                    else{
                        txtPorDobles.setText("%2:0");
                    }
                    if(porctriples.isNaN()==false){
                        txtPorTriples.setText("%3: "+ String.format("%.2f", porctriples));

                    }
                    else
                    {
                        txtPorTriples.setText("%3:0");

                    }



                } else if (i == R.id.rdbPorJugador) {

                    Log.d("Ejectuando:","POR JUGADOR");

                    if(rdbPorJugador.isChecked()) {
                        lstConvocados.setEnabled(true);
                    }
                    else{
                        lstConvocados.setEnabled(false);
                    }

                    lstConvocados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            lstConvocados.setItemChecked(i, true);
                            if (vwAnterior != null) {
                                vwAnterior.setBackgroundColor(Color.TRANSPARENT);
                            }
                            view.setBackgroundColor(Color.LTGRAY);
                            vwAnterior = view;
                            String idJugador = TraerJugadoresId.get(i).toString();
                            EstadisticasXJugador MiJugadorEstadisticas;
                            MiJugadorEstadisticas = TraerEstadisticas(idJugador, IdPartido);


                            Integer TirosLibTirados = MiJugadorEstadisticas.TirosLibErrados + MiJugadorEstadisticas.TirosLibMetidos;
                            Double tltirados = Double.parseDouble(TirosLibTirados.toString());
                            Double tlmetidos = Double.parseDouble(MiJugadorEstadisticas.TirosLibMetidos.toString());
                            Double porTL = (tlmetidos * 100) / tltirados;

                            Integer DoblesTirados = MiJugadorEstadisticas.DoblesErrados + MiJugadorEstadisticas.DoblesMetidos;
                            Double doblestirados = Double.parseDouble(DoblesTirados.toString());
                            Double doblesmetidos = Double.parseDouble(MiJugadorEstadisticas.DoblesMetidos.toString());
                            Double pordobles = (doblesmetidos * 100) / doblestirados;

                            Integer TriplesTirados = MiJugadorEstadisticas.TriplesErrados + MiJugadorEstadisticas.TriplesMetidos;
                            Double triplestirados = Double.parseDouble(TriplesTirados.toString());
                            Double triplesmetidos = Double.parseDouble(MiJugadorEstadisticas.TriplesMetidos.toString());
                            Double porctriples = (triplesmetidos * 100) / triplestirados;


                            txtPuntos.setText("Puntos: " + MiJugadorEstadisticas.Puntos.toString());
                            txtAsitencias.setText("Asistencias: " + MiJugadorEstadisticas.Asistencias.toString());
                            txtPerdidas.setText("Perdidas: " + MiJugadorEstadisticas.Perdidas.toString());
                            txtFaltas.setText("Faltas: " + MiJugadorEstadisticas.Faltas.toString());
                            txtTapones.setText("Tapones: " + MiJugadorEstadisticas.Tapones.toString());
                            txtRobos.setText("Robos: " + MiJugadorEstadisticas.Robos.toString());
                            Integer Rebotes = MiJugadorEstadisticas.RebOff + MiJugadorEstadisticas.RebDef;
                            txtRebs.setText("Rebotes: " + Rebotes.toString());
                            if(porTL.isNaN()==false) {
                                txtPorTirosLibres.setText("%1: " + String.format("%.2f", porTL));
                            }
                            else
                            {
                                txtPorTirosLibres.setText("%1:0");
                            }

                            if(pordobles.isNaN()==false) {
                                txtPorDobles.setText("%2: " + String.format("%.2f", pordobles));
                            }
                            else{
                                txtPorDobles.setText("%2:0");
                            }
                            if(porctriples.isNaN()==false){
                                txtPorTriples.setText("%3: "+ String.format("%.2f", porctriples));

                            }
                            else
                            {
                                txtPorTriples.setText("%3:0");

                            }




                        }

                    });

                }
            }
        });











        // SELECT IDJUG FROM ESTADISTICASXJUGADOR WHERE IDPARTIDO=AL QUE TRAJISTE DEL BUNDLE. GUARDAS EN UN ARRAYLIST DE INT TODOS LOS ID
        // FOR QUE RECORRA LEL ARRAYLIST Y ADENTRO DEL FOR UN (SELECT NOMBRE,POSICION FROM JUGADORES WHERE IDJUGADOR=ARRAYLIST(i) Y AGREGAS A UNN ARRAYLIST

    }




    private EstadisticasXJugador TraerEstadisticasPartido (String idPartido){

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
        Cursor MisRegistros = this.baseDatos.rawQuery("select Puntos,Asistencias,Robos,Faltas,Tapones,RebOff,RebDef,Perdidas,TirosLibErrados,DoblesErrados,TriplesErrados,TirosLibMetidos,DoblesMetidos,TriplesMetidos from EstadisticasXJugador WHERE IdPartido="+idPartido,null);

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
            }while(MisRegistros.moveToNext()==true);

        }
        return EstadisticasTotalesEquipo;
    }

    private EstadisticasXJugador TraerEstadisticas(String idJugador,String idPartido)
    {
        Cursor MisRegistros = this.baseDatos.rawQuery("select Puntos,Asistencias,Robos,Faltas,Tapones,RebOff,RebDef,Perdidas,TirosLibErrados,DoblesErrados,TriplesErrados,TirosLibMetidos,DoblesMetidos,TriplesMetidos from EstadisticasXJugador WHERE IdPartido="+idPartido+" AND IdJugador= "+idJugador,null);
        EstadisticasXJugador misEstadisticas = new EstadisticasXJugador();
        if(MisRegistros.moveToFirst())
        {
            misEstadisticas.Puntos = MisRegistros.getInt(0);
            misEstadisticas.Asistencias=MisRegistros.getInt(1);
            misEstadisticas.Robos=MisRegistros.getInt(2);
            misEstadisticas.Faltas=MisRegistros.getInt(3);
            misEstadisticas.Tapones=MisRegistros.getInt(4);
            misEstadisticas.RebOff=MisRegistros.getInt(5);
            misEstadisticas.RebDef=MisRegistros.getInt(6);
            misEstadisticas.Perdidas=MisRegistros.getInt(7);
            misEstadisticas.TirosLibErrados=MisRegistros.getInt(8);
            misEstadisticas.DoblesErrados=MisRegistros.getInt(9);
            misEstadisticas.TriplesErrados=MisRegistros.getInt(10);
            misEstadisticas.TirosLibMetidos=MisRegistros.getInt(11);
            misEstadisticas.DoblesMetidos=MisRegistros.getInt(12);
            misEstadisticas.TriplesMetidos=MisRegistros.getInt(13);


        }
        return misEstadisticas;
    }



    private ArrayList<Integer> TraerJugadores(String id)
    {

            Log.d("SQLITE", "Ejecuto la lectura de todos");
            Cursor MisRegistros = this.baseDatos.rawQuery("select IdJugador from EstadisticasXJugador WHERE IdPartido=?", new String[]{id});
            Log.d("SQLITE", "Verifico si trajo registros");
            ArrayList<Integer> jugadoresArray = new ArrayList<>();
            if (MisRegistros.moveToFirst()) {
                do {

                    Log.d("sql", "Cargarjugador provisorio");
                    jugadoresArray.add(MisRegistros.getInt(0));
                }
                while (MisRegistros.moveToNext() == true);

            } else {
                Toast.makeText(getApplicationContext(), "Null en mis registros", Toast.LENGTH_LONG).show();
            }

        return jugadoresArray;
    }

    private ArrayList<Jugador> TraerConvocados(ArrayList<Integer> ConvocadosString) {
        ArrayList<Jugador> localArrayList = new ArrayList<Jugador>();
        Log.d("SQLITE", "Ejecuto la lectura de todos");

        for (int i = 0; i < ConvocadosString.size(); i++) {

            Cursor MisRegistros = this.baseDatos.rawQuery("select nombre,posicion from Jugadores WHERE IdJugador=" + ConvocadosString.get(i), null);
            Log.d("SQLITE", "Verifico si trajo registros");
            if (MisRegistros.moveToFirst()){

                Jugador MiJugador = new Jugador();
                Log.d("sql", "Cargarjugador provisorio");
                MiJugador.Nombre = MisRegistros.getString(0);
                MiJugador.Posicion = MisRegistros.getString(1);
                localArrayList.add(MiJugador);
            }
        }
        return localArrayList;

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
    private void ObtenerReferencias()
    {
        lstConvocados = (ListView) findViewById(R.id.lstConvocados);
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
        rdbPorEquipo=(RadioButton)findViewById(R.id.rdbPorEquipo);
        rdbPorJugador=(RadioButton)findViewById(R.id.rdbPorJugador);
    }



}
