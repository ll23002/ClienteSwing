/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.control;

import jakarta.websocket.ClientEndpoint;//el ocupa javax
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.boundary.FrmTipoSala;

/**
 *
 * @author alexander
 */
@ClientEndpoint
public class TipoSalaEndpoint {
    
    FrmTipoSala frmTipoSala;
    
    @OnOpen
    public void onOpen(Session s){
        System.out.println("conecto: " + s);
    }
    
    @OnMessage
    public void onMessage(String mensaje, Session sesion){
        System.out.println("Recibio datos: " + mensaje + "sesion: " + sesion);
        frmTipoSala.cargarDatos();
        frmTipoSala.invalidate();
        frmTipoSala.getParent().validate();
        frmTipoSala.getParent().repaint();
    }

    public FrmTipoSala getFrmTipoSala() {
        return frmTipoSala;
    }

    public void setFrmTipoSala(FrmTipoSala frmTipoSala) {
        this.frmTipoSala = frmTipoSala;
    }
    
    
}
