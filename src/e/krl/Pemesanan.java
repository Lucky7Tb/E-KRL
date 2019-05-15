/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package e.krl;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author Lucky
 */
@Entity
@Table(name = "pemesanan", catalog = "db_e_ticket", schema = "")
@NamedQueries({
    @NamedQuery(name = "Pemesanan.findAll", query = "SELECT p FROM Pemesanan p")
    , @NamedQuery(name = "Pemesanan.findByIdPemesanan", query = "SELECT p FROM Pemesanan p WHERE p.idPemesanan = :idPemesanan")
    , @NamedQuery(name = "Pemesanan.findByIdPemberangkatan", query = "SELECT p FROM Pemesanan p WHERE p.idPemberangkatan = :idPemberangkatan")
    , @NamedQuery(name = "Pemesanan.findByNamapemesan", query = "SELECT p FROM Pemesanan p WHERE p.namapemesan = :namapemesan")
    , @NamedQuery(name = "Pemesanan.findByTanggalPemesanan", query = "SELECT p FROM Pemesanan p WHERE p.tanggalPemesanan = :tanggalPemesanan")
    , @NamedQuery(name = "Pemesanan.findByTotalHarga", query = "SELECT p FROM Pemesanan p WHERE p.totalHarga = :totalHarga")})
public class Pemesanan implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_pemesanan")
    private String idPemesanan;
    @Basic(optional = false)
    @Column(name = "id_pemberangkatan")
    private String idPemberangkatan;
    @Basic(optional = false)
    @Column(name = "Nama_pemesan")
    private String namapemesan;
    @Basic(optional = false)
    @Column(name = "tanggal_pemesanan")
    @Temporal(TemporalType.DATE)
    private Date tanggalPemesanan;
    @Basic(optional = false)
    @Column(name = "total_harga")
    private int totalHarga;

    public Pemesanan() {
    }

    public Pemesanan(String idPemesanan) {
        this.idPemesanan = idPemesanan;
    }

    public Pemesanan(String idPemesanan, String idPemberangkatan, String namapemesan, Date tanggalPemesanan, int totalHarga) {
        this.idPemesanan = idPemesanan;
        this.idPemberangkatan = idPemberangkatan;
        this.namapemesan = namapemesan;
        this.tanggalPemesanan = tanggalPemesanan;
        this.totalHarga = totalHarga;
    }

    public String getIdPemesanan() {
        return idPemesanan;
    }

    public void setIdPemesanan(String idPemesanan) {
        String oldIdPemesanan = this.idPemesanan;
        this.idPemesanan = idPemesanan;
        changeSupport.firePropertyChange("idPemesanan", oldIdPemesanan, idPemesanan);
    }

    public String getIdPemberangkatan() {
        return idPemberangkatan;
    }

    public void setIdPemberangkatan(String idPemberangkatan) {
        String oldIdPemberangkatan = this.idPemberangkatan;
        this.idPemberangkatan = idPemberangkatan;
        changeSupport.firePropertyChange("idPemberangkatan", oldIdPemberangkatan, idPemberangkatan);
    }

    public String getNamapemesan() {
        return namapemesan;
    }

    public void setNamapemesan(String namapemesan) {
        String oldNamapemesan = this.namapemesan;
        this.namapemesan = namapemesan;
        changeSupport.firePropertyChange("namapemesan", oldNamapemesan, namapemesan);
    }

    public Date getTanggalPemesanan() {
        return tanggalPemesanan;
    }

    public void setTanggalPemesanan(Date tanggalPemesanan) {
        Date oldTanggalPemesanan = this.tanggalPemesanan;
        this.tanggalPemesanan = tanggalPemesanan;
        changeSupport.firePropertyChange("tanggalPemesanan", oldTanggalPemesanan, tanggalPemesanan);
    }

    public int getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(int totalHarga) {
        int oldTotalHarga = this.totalHarga;
        this.totalHarga = totalHarga;
        changeSupport.firePropertyChange("totalHarga", oldTotalHarga, totalHarga);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPemesanan != null ? idPemesanan.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pemesanan)) {
            return false;
        }
        Pemesanan other = (Pemesanan) object;
        if ((this.idPemesanan == null && other.idPemesanan != null) || (this.idPemesanan != null && !this.idPemesanan.equals(other.idPemesanan))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "e.krl.Pemesanan[ idPemesanan=" + idPemesanan + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
