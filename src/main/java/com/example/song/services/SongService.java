/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.song.services;

import com.example.song.entities.Song;
import com.example.song.repositories.SongRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Los_e
 */
@Service
public class SongService {

    @Autowired
    private SongRepo songRepo;

    public void insertSong(Song song) {
        songRepo.save(song);
    }

    public List<Song> getAllSongs() {
        return songRepo.findAll();
    }

    public Song getSongByID(int id) {
        return songRepo.findById(id).get();
    }

    public void deleteSong(int id) {
        songRepo.deleteById(id);
    }
}
