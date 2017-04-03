/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gato;

import org.jpl7.Query;
import java.util.HashMap;

/**
 *
 * @author Rod
 */
public class Jugador {
    private static Jugador jugador;
    private String nombreJugador;
    private int juegos;
    private int victorias;
    private int derrotas;
    private int empates;
    
    public Jugador(){
    }
    
    public static Jugador getJugador(){
        if(jugador==null){
            jugador=new Jugador();
        }
        return jugador;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public int getJuegos() {
        return juegos;
    }

    public void setJuegos(int juegos) {
        this.juegos = juegos;
    }

    public int getVictorias() {
        return victorias;
    }

    public void setVictorias(int victorias) {
        this.victorias = victorias;
    }

    public int getDerrotas() {
        return derrotas;
    }

    public void setDerrotas(int derrotas) {
        this.derrotas = derrotas;
    }

    public int getEmpates() {
        return empates;
    }

    public void setEmpates(int empates) {
        this.empates = empates;
    }
    
    private void baseDatosIni(){
        String t1="abrir";
        Query q1=new Query(t1);
        System.out.println(t1+" "+(q1.hasSolution()? "correcto" : "fallo"));
    }
    
    public boolean existe(String nomb){
        boolean existe=false;
        
        baseDatosIni();
        String t1="leer_nombres(X)";
        Query q1=new Query(t1);
        
        while (q1.hasMoreSolutions()) {
            HashMap hm=(HashMap) q1.nextSolution();
            if(nomb.equals(hm.get("X").toString())){
                existe=true;
                break;
            }
        }
        
        return existe;
    }
    
    public void llenarDatos(){
        String nombre=getNombreJugador();
        String t1="leer_datos('"+nombre+"',V,D,E).";
        Query q1=new Query(t1);
        
        while(q1.hasMoreSolutions()){
            HashMap hm=(HashMap)q1.nextSolution();
            setVictorias(Integer.parseInt(hm.get("V").toString()));
            setDerrotas(Integer.parseInt(hm.get("D").toString()));
            setEmpates(Integer.parseInt(hm.get("E").toString()));
            setJuegos(victorias+derrotas+empates);
        }
    }
    
    public void actualizar(String dato, int cant){
        String campos="'"+nombreJugador+"','"+dato+"',"+cant;
        String t1="actualiza_datos("+campos+")";
        Query q1=new Query(t1);
        if(!q1.hasSolution())
            System.out.println("Fallo");
    }
}
