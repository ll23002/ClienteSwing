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
import java.io.Serializable;
import java.util.Set;

/**
 *
 * @author alexander
 */
@Entity
@Table(name = "tipo_reserva")
@NamedQueries({
    @NamedQuery(name = "TipoReserva.findAll", query = "SELECT t FROM TipoReserva t"),
    @NamedQuery(name = "TipoReserva.findByIdTipoReserva", query = "SELECT t FROM TipoReserva t WHERE t.idTipoReserva = :idTipoReserva"),
    @NamedQuery(name = "TipoReserva.findByNombre", query = "SELECT t FROM TipoReserva t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TipoReserva.findByActivo", query = "SELECT t FROM TipoReserva t WHERE t.activo = :activo"),
    @NamedQuery(name = "TipoReserva.findByComentarios", query = "SELECT t FROM TipoReserva t WHERE t.comentarios = :comentarios")})
public class TipoReserva implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_reserva")
    private Integer idTipoReserva;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "activo")
    private Boolean activo;
    @Column(name = "comentarios")
    private String comentarios;
    @OneToMany(mappedBy = "idTipoReserva", fetch = FetchType.LAZY)
    private Set<Reserva> reservaSet;

    public TipoReserva() {
    }

    public TipoReserva(Integer idTipoReserva) {
        this.idTipoReserva = idTipoReserva;
    }

    public Integer getIdTipoReserva() {
        return idTipoReserva;
    }

    public void setIdTipoReserva(Integer idTipoReserva) {
        this.idTipoReserva = idTipoReserva;
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

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Set<Reserva> getReservaSet() {
        return reservaSet;
    }

    public void setReservaSet(Set<Reserva> reservaSet) {
        this.reservaSet = reservaSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoReserva != null ? idTipoReserva.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoReserva)) {
            return false;
        }
        TipoReserva other = (TipoReserva) object;
        if ((this.idTipoReserva == null && other.idTipoReserva != null) || (this.idTipoReserva != null && !this.idTipoReserva.equals(other.idTipoReserva))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.entity.TipoReserva[ idTipoReserva=" + idTipoReserva + " ]";
    }
    
}
