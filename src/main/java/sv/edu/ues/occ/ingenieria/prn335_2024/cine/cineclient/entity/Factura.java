/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 *
 * @author alexander
 */
@Entity
@Table(name = "factura")
@NamedQueries({
    @NamedQuery(name = "Factura.findAll", query = "SELECT f FROM Factura f"),
    @NamedQuery(name = "Factura.findByIdFactura", query = "SELECT f FROM Factura f WHERE f.idFactura = :idFactura"),
    @NamedQuery(name = "Factura.findByCliente", query = "SELECT f FROM Factura f WHERE f.cliente = :cliente"),
    @NamedQuery(name = "Factura.findByDui", query = "SELECT f FROM Factura f WHERE f.dui = :dui"),
    @NamedQuery(name = "Factura.findByFecha", query = "SELECT f FROM Factura f WHERE f.fecha = :fecha"),
    @NamedQuery(name = "Factura.findByComentarios", query = "SELECT f FROM Factura f WHERE f.comentarios = :comentarios")})
public class Factura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_factura")
    private Long idFactura;
    @Column(name = "cliente")
    private String cliente;
    @Column(name = "dui")
    private String dui;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "comentarios")
    private String comentarios;
    @OneToMany(mappedBy = "idFactura", fetch = FetchType.LAZY)
    private Set<FacturaDetalleSala> facturaDetalleSalaSet;
    @OneToMany(mappedBy = "idFactura", fetch = FetchType.LAZY)
    private Set<Pago> pagoSet;
    @OneToMany(mappedBy = "idFactura", fetch = FetchType.LAZY)
    private Set<FacturaDetalleProducto> facturaDetalleProductoSet;

    public Factura() {
    }

    public Factura(Long idFactura) {
        this.idFactura = idFactura;
    }

    public Long getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(Long idFactura) {
        this.idFactura = idFactura;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getDui() {
        return dui;
    }

    public void setDui(String dui) {
        this.dui = dui;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Set<FacturaDetalleSala> getFacturaDetalleSalaSet() {
        return facturaDetalleSalaSet;
    }

    public void setFacturaDetalleSalaSet(Set<FacturaDetalleSala> facturaDetalleSalaSet) {
        this.facturaDetalleSalaSet = facturaDetalleSalaSet;
    }

    public Set<Pago> getPagoSet() {
        return pagoSet;
    }

    public void setPagoSet(Set<Pago> pagoSet) {
        this.pagoSet = pagoSet;
    }

    public Set<FacturaDetalleProducto> getFacturaDetalleProductoSet() {
        return facturaDetalleProductoSet;
    }

    public void setFacturaDetalleProductoSet(Set<FacturaDetalleProducto> facturaDetalleProductoSet) {
        this.facturaDetalleProductoSet = facturaDetalleProductoSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFactura != null ? idFactura.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Factura)) {
            return false;
        }
        Factura other = (Factura) object;
        if ((this.idFactura == null && other.idFactura != null) || (this.idFactura != null && !this.idFactura.equals(other.idFactura))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.entity.Factura[ idFactura=" + idFactura + " ]";
    }
    
}
