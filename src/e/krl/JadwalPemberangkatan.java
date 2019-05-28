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
@Table(name = "jadwal_pemberangkatan", catalog = "db_e_ticket", schema = "")
@NamedQueries({
    @NamedQuery(name = "JadwalPemberangkatan.findAll", query = "SELECT j FROM JadwalPemberangkatan j")
    , @NamedQuery(name = "JadwalPemberangkatan.findById", query = "SELECT j FROM JadwalPemberangkatan j WHERE j.id = :id")
    , @NamedQuery(name = "JadwalPemberangkatan.findByStasiunPemberangkatan", query = "SELECT j FROM JadwalPemberangkatan j WHERE j.stasiunPemberangkatan = :stasiunPemberangkatan")
    , @NamedQuery(name = "JadwalPemberangkatan.findByStasiunTujuan", query = "SELECT j FROM JadwalPemberangkatan j WHERE j.stasiunTujuan = :stasiunTujuan")
    , @NamedQuery(name = "JadwalPemberangkatan.findByTanggal", query = "SELECT j FROM JadwalPemberangkatan j WHERE j.tanggal = :tanggal")
    , @NamedQuery(name = "JadwalPemberangkatan.findByWaktuBerangkat", query = "SELECT j FROM JadwalPemberangkatan j WHERE j.waktuBerangkat = :waktuBerangkat")
    , @NamedQuery(name = "JadwalPemberangkatan.findByWaktuSampai", query = "SELECT j FROM JadwalPemberangkatan j WHERE j.waktuSampai = :waktuSampai")
    , @NamedQuery(name = "JadwalPemberangkatan.findByHarga", query = "SELECT j FROM JadwalPemberangkatan j WHERE j.harga = :harga")
    , @NamedQuery(name = "JadwalPemberangkatan.findBySisaTiket", query = "SELECT j FROM JadwalPemberangkatan j WHERE j.sisaTiket = :sisaTiket")
    , @NamedQuery(name = "JadwalPemberangkatan.findByKelas", query = "SELECT j FROM JadwalPemberangkatan j WHERE j.kelas = :kelas")})
public class JadwalPemberangkatan implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @Column(name = "stasiun_pemberangkatan")
    private String stasiunPemberangkatan;
    @Basic(optional = false)
    @Column(name = "stasiun_tujuan")
    private String stasiunTujuan;
    @Basic(optional = false)
    @Column(name = "tanggal")
    @Temporal(TemporalType.DATE)
    private Date tanggal;
    @Basic(optional = false)
    @Column(name = "waktu_berangkat")
    @Temporal(TemporalType.TIME)
    private Date waktuBerangkat;
    @Basic(optional = false)
    @Column(name = "waktu_sampai")
    @Temporal(TemporalType.TIME)
    private Date waktuSampai;
    @Basic(optional = false)
    @Column(name = "harga")
    private int harga;
    @Basic(optional = false)
    @Column(name = "sisa_tiket")
    private int sisaTiket;
    @Basic(optional = false)
    @Column(name = "kelas")
    private String kelas;

    public JadwalPemberangkatan() {
    }

    public JadwalPemberangkatan(String id) {
        this.id = id;
    }

    public JadwalPemberangkatan(String id, String stasiunPemberangkatan, String stasiunTujuan, Date tanggal, Date waktuBerangkat, Date waktuSampai, int harga, int sisaTiket, String kelas) {
        this.id = id;
        this.stasiunPemberangkatan = stasiunPemberangkatan;
        this.stasiunTujuan = stasiunTujuan;
        this.tanggal = tanggal;
        this.waktuBerangkat = waktuBerangkat;
        this.waktuSampai = waktuSampai;
        this.harga = harga;
        this.sisaTiket = sisaTiket;
        this.kelas = kelas;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        String oldId = this.id;
        this.id = id;
        changeSupport.firePropertyChange("id", oldId, id);
    }

    public String getStasiunPemberangkatan() {
        return stasiunPemberangkatan;
    }

    public void setStasiunPemberangkatan(String stasiunPemberangkatan) {
        String oldStasiunPemberangkatan = this.stasiunPemberangkatan;
        this.stasiunPemberangkatan = stasiunPemberangkatan;
        changeSupport.firePropertyChange("stasiunPemberangkatan", oldStasiunPemberangkatan, stasiunPemberangkatan);
    }

    public String getStasiunTujuan() {
        return stasiunTujuan;
    }

    public void setStasiunTujuan(String stasiunTujuan) {
        String oldStasiunTujuan = this.stasiunTujuan;
        this.stasiunTujuan = stasiunTujuan;
        changeSupport.firePropertyChange("stasiunTujuan", oldStasiunTujuan, stasiunTujuan);
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        Date oldTanggal = this.tanggal;
        this.tanggal = tanggal;
        changeSupport.firePropertyChange("tanggal", oldTanggal, tanggal);
    }

    public Date getWaktuBerangkat() {
        return waktuBerangkat;
    }

    public void setWaktuBerangkat(Date waktuBerangkat) {
        Date oldWaktuBerangkat = this.waktuBerangkat;
        this.waktuBerangkat = waktuBerangkat;
        changeSupport.firePropertyChange("waktuBerangkat", oldWaktuBerangkat, waktuBerangkat);
    }

    public Date getWaktuSampai() {
        return waktuSampai;
    }

    public void setWaktuSampai(Date waktuSampai) {
        Date oldWaktuSampai = this.waktuSampai;
        this.waktuSampai = waktuSampai;
        changeSupport.firePropertyChange("waktuSampai", oldWaktuSampai, waktuSampai);
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        int oldHarga = this.harga;
        this.harga = harga;
        changeSupport.firePropertyChange("harga", oldHarga, harga);
    }

    public int getSisaTiket() {
        return sisaTiket;
    }

    public void setSisaTiket(int sisaTiket) {
        int oldSisaTiket = this.sisaTiket;
        this.sisaTiket = sisaTiket;
        changeSupport.firePropertyChange("sisaTiket", oldSisaTiket, sisaTiket);
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        String oldKelas = this.kelas;
        this.kelas = kelas;
        changeSupport.firePropertyChange("kelas", oldKelas, kelas);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof JadwalPemberangkatan)) {
            return false;
        }
        JadwalPemberangkatan other = (JadwalPemberangkatan) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "e.krl.JadwalPemberangkatan[ id=" + id + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
