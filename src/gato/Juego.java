/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gato;

import org.jpl7.Query;

/**
 *
 * @author Sonny
 */
public class Juego {
    private static Juego juego;
    private Jugador jugador=Jugador.getJugador();
    private String mensaje;
    private String turno;
    private String ficha;
    private int fx;
    private int fo;

    private Juego() {
    }
    
    public static Juego getJuego(){
        if(juego==null){
            juego=new Juego();
        }
        return juego;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getFx() {
        return fx;
    }

    public void setFx(int fx) {
        this.fx = fx;
    }

    public int getFo() {
        return fo;
    }

    public void setFo(int fo) {
        this.fo = fo;
    }
    
    public void asignarFicha(){
        if(turno.equals("jugador")){
            turno="jugador";
            ficha="o";
        } else{
            turno="maquina";
            ficha="o";
        }
    }
    
    public void prologMysql(){
        String t1="consult('mysql.pl')";
        Query q1=new Query(t1);
        System.out.println(t1+" "+(q1.hasSolution()? "correcto" : "fallo"));
    }
    
    public void baseDatosIni(){
        String t1="abrir";
        Query q1=new Query(t1);
        System.out.println(t1+" "+(q1.hasSolution()? "correcto" : "fallo"));
    }
    
    public void baseDatosAñadir(String nombre){
        baseDatosIni();
        String t1="insertar(1,"+nombre+")";
        Query q1=new Query(t1);
        System.out.println(t1+" "+(q1.hasSolution()? "correcto" : "fallo"));
    }
}
