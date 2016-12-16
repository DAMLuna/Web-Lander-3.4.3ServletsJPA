/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alfas
 */
@Entity
@Table(name = "andlun_registry")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AndlunRegistry.findAll", query = "SELECT a FROM AndlunRegistry a")
    , @NamedQuery(name = "AndlunRegistry.findByIdPlay", query = "SELECT a FROM AndlunRegistry a WHERE a.idPlay = :idPlay")
    , @NamedQuery(name = "AndlunRegistry.findByIdUser", query = "SELECT a FROM AndlunRegistry a WHERE a.idUser.idUser =:code ORDER BY a.speed DESC")
    , @NamedQuery(name = "AndlunRegistry.findByGlobalPoints", query = "SELECT a FROM AndlunRegistry a JOIN FETCH a.idUser ORDER BY a.speed DESC")
    , @NamedQuery(name = "AndlunRegistry.findByEndDateOrder", query = "SELECT a FROM AndlunRegistry a JOIN FETCH a.idUser WHERE a.endDate=(SELECT max(b.endDate) FROM AndlunRegistry b WHERE a.idUser=b.idUser) ORDER BY a.endDate DESC ")
    , @NamedQuery(name = "AndlunRegistry.findByStartDate", query = "SELECT a FROM AndlunRegistry a WHERE a.startDate = :startDate")
    , @NamedQuery(name = "AndlunRegistry.findByEndDate", query = "SELECT a FROM AndlunRegistry a WHERE a.endDate = :endDate")
    , @NamedQuery(name = "AndlunRegistry.findBySpeed", query = "SELECT a FROM AndlunRegistry a WHERE a.speed = :speed")})
public class AndlunRegistry implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_play")
    private Integer idPlay;
    @Basic(optional = false)
    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Basic(optional = false)
    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @Basic(optional = false)
    @Column(name = "speed")
    private float speed;
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    @ManyToOne(optional = false)
    private AndlunUserGame idUser;

    public AndlunRegistry() {
    }

    public AndlunRegistry(Integer idPlay) {
        this.idPlay = idPlay;
    }

    public AndlunRegistry(Integer idPlay, Date startDate, Date endDate, float speed) {
        this.idPlay = idPlay;
        this.startDate = startDate;
        this.endDate = endDate;
        this.speed = speed;
    }

    public Integer getIdPlay() {
        return idPlay;
    }

    public void setIdPlay(Integer idPlay) {
        this.idPlay = idPlay;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public AndlunUserGame getIdUser() {
        return idUser;
    }

    public void setIdUser(AndlunUserGame idUser) {
        this.idUser = idUser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPlay != null ? idPlay.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AndlunRegistry)) {
            return false;
        }
        AndlunRegistry other = (AndlunRegistry) object;
        if ((this.idPlay == null && other.idPlay != null) || (this.idPlay != null && !this.idPlay.equals(other.idPlay))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.AndlunRegistry[ idPlay=" + idPlay + " ]";
    }
    
}
