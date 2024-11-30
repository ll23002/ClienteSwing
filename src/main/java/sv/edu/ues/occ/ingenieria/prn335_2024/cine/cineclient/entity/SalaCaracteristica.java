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
import jakarta.persistence.Table;
import java.io.Serializable;

/**
 *
 * @author alexander
 */
@Entity
@Table(name = "sala_caracteristica")
@NamedQueries({
    @NamedQuery(name = "SalaCaracteristica.findAll", query = "SELECT s FROM SalaCaracteristica s"),
    @NamedQuery(name = "SalaCaracteristica.findByIdSalaCaracteristica", query = "SELECT s FROM SalaCaracteristica s WHERE s.idSalaCaracteristica = :idSalaCaracteristica"),
    @NamedQuery(name = "SalaCaracteristica.findByValor", query = "SELECT s FROM SalaCaracteristica s WHERE s.valor = :valor")})
public class SalaCaracteristica implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_sala_caracteristica")
    private Long idSalaCaracteristica;
    @Column(name = "valor")
    private String valor;
    @JoinColumn(name = "id_sala", referencedColumnName = "id_sala")
    @ManyToOne(fetch = FetchType.LAZY)
    private Sala idSala;
    @JoinColumn(name = "id_tipo_sala", referencedColumnName = "id_tipo_sala")
    @ManyToOne(fetch = FetchType.LAZY)
    private TipoSala idTipoSala;

    public SalaCaracteristica() {
    }

    public SalaCaracteristica(Long idSalaCaracteristica) {
        this.idSalaCaracteristica = idSalaCaracteristica;
    }

    public Long getIdSalaCaracteristica() {
        return idSalaCaracteristica;
    }

    public void setIdSalaCaracteristica(Long idSalaCaracteristica) {
        this.idSalaCaracteristica = idSalaCaracteristica;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Sala getIdSala() {
        return idSala;
    }

    public void setIdSala(Sala idSala) {
        this.idSala = idSala;
    }

    public TipoSala getIdTipoSala() {
        return idTipoSala;
    }

    public void setIdTipoSala(TipoSala idTipoSala) {
        this.idTipoSala = idTipoSala;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSalaCaracteristica != null ? idSalaCaracteristica.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SalaCaracteristica)) {
            return false;
        }
        SalaCaracteristica other = (SalaCaracteristica) object;
        if ((this.idSalaCaracteristica == null && other.idSalaCaracteristica != null) || (this.idSalaCaracteristica != null && !this.idSalaCaracteristica.equals(other.idSalaCaracteristica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.entity.SalaCaracteristica[ idSalaCaracteristica=" + idSalaCaracteristica + " ]";
    }
    
}
