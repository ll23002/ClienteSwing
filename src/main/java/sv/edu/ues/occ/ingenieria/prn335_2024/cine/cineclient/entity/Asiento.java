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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Set;

/**
 *
 * @author alexander
 */
@Entity
@Table(name = "asiento")
@NamedQueries({
    @NamedQuery(name = "Asiento.findAll", query = "SELECT a FROM Asiento a"),
    @NamedQuery(name = "Asiento.findByIdAsiento", query = "SELECT a FROM Asiento a WHERE a.idAsiento = :idAsiento"),
    @NamedQuery(name = "Asiento.findByNombre", query = "SELECT a FROM Asiento a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "Asiento.findByActivo", query = "SELECT a FROM Asiento a WHERE a.activo = :activo")})
public class Asiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_asiento")
    private Long idAsiento;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "activo")
    private Boolean activo;
    @OneToMany(mappedBy = "idAsiento", fetch = FetchType.LAZY)
    private Set<ReservaDetalle> reservaDetalleSet;
    @JoinColumn(name = "id_sala", referencedColumnName = "id_sala")
    @ManyToOne(fetch = FetchType.LAZY)
    private Sala idSala;
    @OneToMany(mappedBy = "idAsiento", fetch = FetchType.LAZY)
    private Set<AsientoCaracteristica> asientoCaracteristicaSet;

    public Asiento() {
    }

    public Asiento(Long idAsiento) {
        this.idAsiento = idAsiento;
    }

    public Long getIdAsiento() {
        return idAsiento;
    }

    public void setIdAsiento(Long idAsiento) {
        this.idAsiento = idAsiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Set<ReservaDetalle> getReservaDetalleSet() {
        return reservaDetalleSet;
    }

    public void setReservaDetalleSet(Set<ReservaDetalle> reservaDetalleSet) {
        this.reservaDetalleSet = reservaDetalleSet;
    }

    public Sala getIdSala() {
        return idSala;
    }

    public void setIdSala(Sala idSala) {
        this.idSala = idSala;
    }

    public Set<AsientoCaracteristica> getAsientoCaracteristicaSet() {
        return asientoCaracteristicaSet;
    }

    public void setAsientoCaracteristicaSet(Set<AsientoCaracteristica> asientoCaracteristicaSet) {
        this.asientoCaracteristicaSet = asientoCaracteristicaSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAsiento != null ? idAsiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Asiento)) {
            return false;
        }
        Asiento other = (Asiento) object;
        if ((this.idAsiento == null && other.idAsiento != null) || (this.idAsiento != null && !this.idAsiento.equals(other.idAsiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.entity.Asiento[ idAsiento=" + idAsiento + " ]";
    }
    
}
