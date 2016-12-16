/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author alfas
 */
@Entity
@Table(name = "andlun_user_game")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AndlunUserGame.findAll", query = "SELECT a FROM AndlunUserGame a")
    , @NamedQuery(name = "AndlunUserGame.findByIdUser", query = "SELECT a FROM AndlunUserGame a WHERE a.idUser = :idUser")
    , @NamedQuery(name = "AndlunUserGame.findByNameUser", query = "SELECT a FROM AndlunUserGame a WHERE a.nameUser = :nameUser")
    , @NamedQuery(name = "AndlunUserGame.findByPasswd", query = "SELECT a FROM AndlunUserGame a WHERE a.passwd = :passwd")
    , @NamedQuery(name = "AndlunUserGame.findByEmail", query = "SELECT a FROM AndlunUserGame a WHERE a.email = :email")})
public class AndlunUserGame implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_user")
    private Integer idUser;
    @Basic(optional = false)
    @Column(name = "name_user")
    private String nameUser;
    @Basic(optional = false)
    @Column(name = "passwd")
    private String passwd;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUser")
    private List<AndlunRegistry> andlunRegistryList;

    public AndlunUserGame() {
    }

    public AndlunUserGame(Integer idUser) {
        this.idUser = idUser;
    }

    public AndlunUserGame(Integer idUser, String nameUser, String passwd, String email) {
        this.idUser = idUser;
        this.nameUser = nameUser;
        this.passwd = passwd;
        this.email = email;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlTransient
    public List<AndlunRegistry> getAndlunRegistryList() {
        return andlunRegistryList;
    }

    public void setAndlunRegistryList(List<AndlunRegistry> andlunRegistryList) {
        this.andlunRegistryList = andlunRegistryList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUser != null ? idUser.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AndlunUserGame)) {
            return false;
        }
        AndlunUserGame other = (AndlunUserGame) object;
        if ((this.idUser == null && other.idUser != null) || (this.idUser != null && !this.idUser.equals(other.idUser))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.AndlunUserGame[ idUser=" + idUser + " ]";
    }
    
}
