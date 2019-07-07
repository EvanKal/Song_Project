/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.song.repositories;

import com.example.song.entities.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Los_e
 */
@Repository
public interface SongRepo extends JpaRepository<Song, Integer>{
    
    
}
