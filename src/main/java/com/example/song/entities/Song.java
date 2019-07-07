/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.song.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Los_e
 */
@Entity
@Table(name = "song")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Song.findAll", query = "SELECT s FROM Song s")
    , @NamedQuery(name = "Song.findById", query = "SELECT s FROM Song s WHERE s.id = :id")
    , @NamedQuery(name = "Song.findByTitle", query = "SELECT s FROM Song s WHERE s.title = :title")
    , @NamedQuery(name = "Song.findByArtist", query = "SELECT s FROM Song s WHERE s.artist = :artist")
    , @NamedQuery(name = "Song.findByFilename", query = "SELECT s FROM Song s WHERE s.filename = :filename")
    , @NamedQuery(name = "Song.findByAlbum", query = "SELECT s FROM Song s WHERE s.album = :album")
    , @NamedQuery(name = "Song.findByYearofrelease", query = "SELECT s FROM Song s WHERE s.yearofrelease = :yearofrelease")})
public class Song implements Serializable {

    @Size(max = 255)
    @Column(name = "title")
    private String title;
    @Size(max = 255)
    @Column(name = "artist")
    private String artist;
    @Lob()
    @Column(name = "file")
    private byte[] file;
    @Size(max = 100)
    @Column(name = "filename")
    private String filename;
    @Size(max = 45)
    @Column(name = "album")
    private String album;
    @Size(max = 45)
    @Column(name = "yearofrelease")
    private String yearofrelease;
    @Lob
    @Column(name = "image")
    private byte[] image;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    public Song() {
    }

    public Song(Integer id) {
        this.id = id;
    }

    public Song(Integer id, String title, String artist, byte[] file, String filename) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.file = file;
        this.filename = filename;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        if (!(object instanceof Song)) {
            return false;
        }
        Song other = (Song) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    @Override
    public String toString() {
        return "com.example.song.entities.Song[ id=" + id + " ]";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getYearofrelease() {
        return yearofrelease;
    }

    public void setYearofrelease(String yearofrelease) {
        this.yearofrelease = yearofrelease;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
    
}
