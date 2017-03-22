/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gato;

import java.awt.Image;
import org.jpl7.Query;
import java.util.HashMap;
import javax.swing.*;

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

    public String getFicha() {
        return ficha;
    }

    public void setFicha(String ficha) {
        this.ficha = ficha;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
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
    
    public ImageIcon movimiento(){
        ImageIcon icon;
        if(ficha.equals("o")){
            icon=new ImageIcon(this.getClass().getResource("circulo.png"));
            ficha="x";
        } else{
            icon=new ImageIcon(this.getClass().getResource("equis.png"));
            ficha="o";
        }
        
        if(turno.equals("jugador"))
            turno="maquina";
        else
            turno="jugador";
        
        icon=new ImageIcon(icon.getImage().getScaledInstance(63, -1, 1));
        return icon;
    }
    
    public void prologMysql(){
        String t1="consult('mysql.pl')";
        Query q1=new Query(t1);
        System.out.println(t1+" "+(q1.hasSolution()? "correcto" : "fallo"));
    }
    
    private void baseDatosIni(){
        String t1="abrir";
        Query q1=new Query(t1);
        System.out.println(t1+" "+(q1.hasSolution()? "correcto" : "fallo"));
    }
    
    private int lastID(){
        int ID=0;
        String t1="leer_id(X).";
        Query q1=new Query(t1);
        while(q1.hasMoreSolutions()){
            HashMap hm=(HashMap)q1.nextSolution();
            ID=Integer.parseInt(hm.get("X").toString());
            break;
        }
        
        return ID;
    }
    
    public void baseDatosAñadir(String nombre){
        baseDatosIni();
        int ID=lastID()+1;
        String t1="insertar("+ID+",'"+nombre+"',X).";
        Query q1=new Query(t1);
        System.out.println(t1+" "+(q1.hasMoreSolutions()? "correcto" : "fallo"));
    }
}
