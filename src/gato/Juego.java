/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gato;

import java.awt.Component;
import java.awt.event.ActionListener;
import org.jpl7.Query;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.Utilities;

/**
 *
 * @author Sonny
 */
public class Juego {
    private static Juego juego;
    private Jugador jugador;
    private boolean fin;
    private String mensaje;
    private String turno;
    private String ficha;
    private int clicks;

    private Juego() {
    }
    
    public static Juego getJuego(){
        if(juego==null){
            juego=new Juego();
        }
        return juego;
    }
    
    public Jugador getJugador(){
        jugador=Jugador.getJugador();
        return jugador;
    }

    public boolean isFin() {
        return fin;
    }

    public void setFin(boolean fin) {
        this.fin = fin;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
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
        clicks+=1;
        
        icon=new ImageIcon(icon.getImage().getScaledInstance(63, -1, 1));
        return icon;
    }
    
    public void gano(Tablero t){
        boolean gano=false;
        boolean empate=false;
        String[][] botones={{t.boton1.getName(),t.boton2.getName(), t.boton3.getName()}, 
                            {t.boton4.getName(), t.boton5.getName(), t.boton6.getName()}, 
                            {t.boton7.getName(), t.boton8.getName(), t.boton9.getName()}};
        
        //Checar victoria horizontal
        for(int i=0; i<3; i++){
            String b1=botones[i][0];
            String b2=botones[i][1];
            String b3=botones[i][2];
            if(b1.equals(b2) && b2.equals(b3)){
                gano=true; 
                break;
            }
        }
        
        //Checar victoria Vertical
        if(!gano)
            for(int i=0; i<3; i++){
                String b1=botones[0][i];
                String b2=botones[1][i];
                String b3=botones[2][i];
                if(b1.equals(b2) && b2.equals(b3)){
                    gano=true; 
                    break;
                }
            }
        
        //Checar vicotia diagonal
        if(!gano){
            String b1=botones[0][0];
            String b2=botones[1][1];
            String b3=botones[2][2];
            if(b1.equals(b2) && b2.equals(b3))
                gano=true;
        }
        
        //Checar victoria diagonal invertida
        if(!gano){
            String b1=botones[0][2];
            String b2=botones[1][1];
            String b3=botones[2][0];
            if(b1.equals(b2) && b2.equals(b3))
                gano=true;
        }
        
        //Si aún no se gana y ya se hicieron todos los movimientos
        if(!gano && clicks==9){
            JOptionPane.showMessageDialog(null, "Has empatado", "Empate", JOptionPane.INFORMATION_MESSAGE);
            jugador.actualizar("empates", jugador.getEmpates()+1);
            panNuevoJuego(t);
            empate=true;
        }
        
        if(gano){
            if(turno.equals("jugador")){
                JOptionPane.showMessageDialog(null, "Felicidades, ganaste :)", "Victoria", JOptionPane.INFORMATION_MESSAGE);
                jugador.actualizar("victorias", jugador.getVictorias()+1);
            }else{
                JOptionPane.showMessageDialog(null, "Mala suerte, perdiste :(", "Derrota", JOptionPane.INFORMATION_MESSAGE);
                jugador.actualizar("derrotas", jugador.getDerrotas()+1);
            }
            
            panNuevoJuego(t);
            
        } else
            if(!empate)
                nextTurn();
    }
    
    private void panNuevoJuego(Tablero t){
        int nuevoJuego= JOptionPane.showConfirmDialog(
                    null, "¿Deseas iniciar otra partida?", "Nuevo juego", 
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(nuevoJuego==JOptionPane.YES_OPTION){
                t.dispose();
                clicks=0;
                nextTurn();
                Tablero rematch=new Tablero();
                rematch.setLocationRelativeTo(null);
                rematch.setVisible(true);    
            } else{
                fin=true;
                clicks=0;
                t.dispose();
            }
            
            
    }
    
    private void nextTurn(){
        if(turno.equals("jugador")){
            turno="maquina";
        }
        else{
            turno="jugador";
        }
    }
    
    public void turno(Tablero t){

        if(turno.equals("maquina"))
            movimientoMaquina(t);
        
        actualizarMensaje(t);
    }
    
    private void movimientoMaquina(Tablero t){
        int boton=jugadaMaquina(t);
        JButton[] botones={t.boton1, t.boton2, t.boton3, 
                            t.boton4, t.boton5, t.boton6, 
                            t.boton7, t.boton8, t.boton9};
        
        botones[boton-1].doClick();
    }
    
    private void actualizarMensaje(Tablero t){
        try{
            Document doc=t.txtMensajes.getDocument();
            Element rootElem= doc.getDefaultRootElement();
            int numLines= rootElem.getElementCount();
            Element lineElem= rootElem.getElement(numLines-1);
            int start= lineElem.getStartOffset();
            int end= lineElem.getEndOffset();
            doc.remove(start, end-start-1);
            
            mensaje="Turno de: "+turno;
            t.txtMensajes.append(mensaje);
          
        } catch(BadLocationException ex){
            ex.printStackTrace();
        }
    }
    public void prologMysql(){
        String t1="consult('mysql.pl')";
        Query q1=new Query(t1);
        if(!q1.hasSolution())
            System.out.println("fallo");
    }
    
    public void prologGato(){
        String t1="consult('gato.pl')";
        Query q1=new Query(t1);
        if(!q1.hasSolution())
            System.out.println("fallo");
    }
    
    private void baseDatosIni(){
        String t1="abrir";
        Query q1=new Query(t1);
        if(!q1.hasSolution())
            System.out.println("fallo");
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
        if(!q1.hasSolution())
            System.out.println("fallo");
    }
    
    public void cargarCombo(JComboBox<String> c){
        baseDatosIni();
        
        c.removeAllItems();
        String t1="leer_nombres(X)";
        Query q1=new Query(t1);
        
        while (q1.hasMoreSolutions()) {
            HashMap hm=(HashMap) q1.nextSolution();
            String name=hm.get("X").toString();
            c.addItem(name.substring(0,1).toUpperCase()+name.substring(1));
        }
    }
    
    private String convTablero(Tablero t){
        String res="[";
        String[][] botones={{t.boton1.getName(),t.boton2.getName(), t.boton3.getName()}, 
                            {t.boton4.getName(), t.boton5.getName(), t.boton6.getName()}, 
                            {t.boton7.getName(), t.boton8.getName(), t.boton9.getName()}};
        
        for(int i=0; i<botones.length; i++){
            res+="[";
            for(int j=0; j<botones[0].length; j++){
                res+=botones[i][j]+",";
            }
            res=res.substring(0, res.length()-1);
            res+="],";
        }
        res=res.substring(0, res.length()-1);
        res+="]";
        return res;
    }
    
    private boolean ocupado(Tablero t, int boton){
        boolean ocupado=false;
        String[] botones={t.boton1.getName(),t.boton2.getName(), t.boton3.getName(), 
                            t.boton4.getName(), t.boton5.getName(), t.boton6.getName(), 
                            t.boton7.getName(), t.boton8.getName(), t.boton9.getName()};
        
        if(botones[boton-1].equals("o") || botones[boton-1].equals("x"))
            ocupado=true;
        
        return ocupado;
    }
    
    public int jugadaMaquina(Tablero t){
        prologGato();
        
        int pos=0;
        String tablero=convTablero(t);
        
        do{
            String t1="jugada("+tablero+",X)";
            Query q1= new Query(t1);
            while(q1.hasMoreSolutions()){
                HashMap hm=(HashMap)q1.nextSolution();
                pos=Integer.parseInt(hm.get("X").toString());
                break;
            }
        } while(ocupado(t, pos));
        
        return pos;
    }
}
