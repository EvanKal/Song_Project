/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.song.controllers;

import com.example.song.entities.Song;
import com.example.song.services.SongService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Los_e
 */
@Controller
public class HomeController {
    @Autowired
    private SongService songService;

    @RequestMapping(value = "/uploadsong", method = RequestMethod.GET)
    public String showUploadPage(ModelMap mm
    ) {

        return "uploadsong";
    }
    
    @RequestMapping(value = "/showallsongs", method = RequestMethod.GET)
    public String showAllSongs(ModelMap mm
            ,            @ModelAttribute("lyrics") String lyrics
            ,            @ModelAttribute("success") String success
    ) {
        
        mm.addAttribute("lyrics", lyrics);
        mm.addAttribute("success", success);
        mm.addAttribute("songlist", songService.getAllSongs());
        return "viewallsongs";
    }

    

}
