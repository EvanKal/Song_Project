/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.song.controllers;

import com.example.song.entities.Song;
import com.example.song.services.SongService;
import com.example.song.utils.SongUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
//import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagField;
import org.jaudiotagger.tag.id3.AbstractID3v2Frame;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.AbstractTagFrameBody;
import org.jaudiotagger.tag.id3.ID3v24Frame;
import org.jaudiotagger.tag.id3.ID3v24Frames;
import org.jaudiotagger.tag.id3.ID3v24Tag;
import org.jaudiotagger.tag.id3.framebody.AbstractFrameBodyTextInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Los_e
 */
@Controller
@RequestMapping(value = "songcontroller")
public class SongController {

    @Autowired
    private SongService songService;
    @Autowired
    private SongUtils songUtils;

    @RequestMapping(value = "/douploadsong", method = RequestMethod.POST)
    public String doUploadSong(
            ModelMap mm,
            @RequestParam(value = "myfile") MultipartFile sourcefile
    ) {

        Song song = new Song();
        song.setFilename(sourcefile.getOriginalFilename());
        try {
            song.setFile(sourcefile.getBytes());
        } catch (IOException ex) {
            Logger.getLogger(SongController.class.getName()).log(Level.SEVERE, null, ex);
        }
        songUtils.getMP3Info(sourcefile, song);
        songService.insertSong(song);

//<editor-fold defaultstate="collapsed" desc="Tests">
//        song.setImage(hm.get("attached_picture"));
//*****************************temporary file gia diavasma**********************************
//        try {
//
//            File tmp = File.createTempFile("test", ".mp3");
//            OutputStream os = Files.newOutputStream(tmp.toPath());
//            os.write(sourcefile.getBytes());
//
//            MP3File f = null;
//
//        try {
//            f = (MP3File) AudioFileIO.read(tmp);
//        } catch (CannotReadException ex) {
//            Logger.getLogger(SongController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(SongController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (org.jaudiotagger.tag.TagException ex) {
//            Logger.getLogger(SongController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ReadOnlyFileException ex) {
//            Logger.getLogger(SongController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (InvalidAudioFrameException ex) {
//            Logger.getLogger(SongController.class.getName()).log(Level.SEVERE, null, ex);
//        }
////        Tag tag = f.getTag();
//        AudioHeader h = f.getAudioHeader();
//        AbstractID3v2Tag v2tag = f.getID3v2Tag();
//        ID3v24Tag v24tag = f.getID3v2TagAsv24();
//
//        System.out.println(v2tag.getFirst(ID3v24Frames.FRAME_ID_ARTIST));
//
//        } catch (IOException ex) {
//            Logger.getLogger(SongController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//************************* Apothikeusi Song sri vasi ws BLOB***********************************8
//        Song song = new Song();
//        song.setFilename(multipart.getOriginalFilename());
//        try {
//            song.setFile(multipart.getBytes());
//        } catch (IOException ex) {
//            Logger.getLogger(SongController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        songService.insertSong(song);
//        return "redirect:/";
//        File file = new File("C:/Users/Los_e/Music/born_to_die__album__by_maarcopngs_dbrogtv/01 Born to Die.wma");
//*************************************Diavasma  tag tou mp3 me looparisma**************************************
//File file = new File("C:/01 Born to Die.mp3");
//
//        MP3File f = null;
//        try {
//            f = (MP3File) AudioFileIO.read(file);
//        } catch (CannotReadException ex) {
//            Logger.getLogger(SongController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(SongController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (org.jaudiotagger.tag.TagException ex) {
//            Logger.getLogger(SongController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ReadOnlyFileException ex) {
//            Logger.getLogger(SongController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (InvalidAudioFrameException ex) {
//            Logger.getLogger(SongController.class.getName()).log(Level.SEVERE, null, ex);
//        }
////        Tag tag = f.getTag();
//        AudioHeader h = f.getAudioHeader();
//        AbstractID3v2Tag v2tag = f.getID3v2Tag();
//        ID3v24Tag v24tag = f.getID3v2TagAsv24();
//
//        System.out.println(v2tag.getFirst(ID3v24Frames.FRAME_ID_ARTIST));
//        System.out.println(v2tag.getFirst(ID3v24Frames.FRAME_ID_ALBUM));
//        System.out.println(v2tag.getFirst(ID3v24Frames.FRAME_ID_YEAR));
//
//        Iterator<TagField> it = v2tag.getFields();
//
//        while (it.hasNext()) {
//            ID3v24Frame tf =(ID3v24Frame) it.next();
////            System.out.println(tf.getBody().getLongDescription() + tf.getBody().getUserFriendlyValue());
//            System.out.println(tf.getId() +tf.getBody().getLongDescription() );
//
//        }
//        ************************************* Fetch sugkekrimeno tag**********************************88
//        System.out.println(tag.getFirst(FieldKey.YEAR));
//                tag.getFirst(FieldKey.ARTIST);
//tag.getFirst(FieldKey.ALBUM);
//tag.getFirst(FieldKey.TITLE);
//tag.getFirst(FieldKey.COMMENT);
//tag.getFirst(FieldKey.YEAR);
//tag.getFirst(FieldKey.TRACK);
//tag.getFirst(FieldKey.DISC_NO);
//tag.getFirst(FieldKey.COMPOSER);
//        Map map = v24tag.frameMap;
//        Iterator it = map.entrySet().iterator();
//        while (it.hasNext()) {
//            Map.Entry pair = ( Map.Entry) it.next();
//            ID3v24Frame fr = (ID3v24Frame)pair.getValue();
//            System.out.println(pair.getKey().toString()+ fr.getBody().getUserFriendlyValue());
//            ID3v24Frame fg =  (ID3v24Frame) tf;
//            System.out.println(fg.getBody().getIdentifier() + " = " + fg.getBody().getUserFriendlyValue());
//            System.out.println(fg.toString());
//        }
//        MP3File mp3file = null;
//        try {
//            mp3file = new MP3File(f);
//        } catch (IOException ex) {
//            Logger.getLogger(SongController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (TagException ex) {
//            Logger.getLogger(SongController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        AbstractID3v2 lala = (ID3v2_4) mp3file.getID3v2Tag();
//        System.out.println(lala.);
//        if (mp3file.hasID3v2Tag()) {
//            ID3v2_4 tag = (ID3v2_4) mp3file.getID3v2Tag();
//            Iterator it = tag.getFrameIterator();
//
//            while (it.hasNext()) {
//                ID3v2_4Frame pair = (ID3v2_4Frame) it.next();
//                System.out.println(pair.getIdentifier() + " = " + pair.getBody().);
//            }
//            System.out.println();
//        }
//        System.out.println(lala.getYearReleased());
//AudioInputStream in= null;
////        File file = new File(filename);
//in = AudioSystem.getAudioInputStream(sourcefile.);
//AudioInputStream din = null;
//AudioFormat baseFormat = in.getFormat();
//AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
//        baseFormat.getSampleRate(),
//        16,
//        baseFormat.getChannels(),
//        baseFormat.getChannels() * 2,
//        baseFormat.getSampleRate(),
//        false);
//din = AudioSystem.getAudioInputStream(decodedFormat, in);
//ID3v2_2 tag = new ID3v2_2(sourcefile);
//        try {
//            MP3File mp3file = new MP3File(sourcefile);
//
//        } catch (IOException ex) {
//            Logger.getLogger(SongController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (TagException ex) {
//            Logger.getLogger(SongController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        } catch (UnsupportedAudioFileException ex) {
//            Logger.getLogger(SongController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(SongController.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                in.close();
//            } catch (IOException ex) {
//                Logger.getLogger(SongController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//</editor-fold>
        return "redirect:/";
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody
    byte[] downloadFile(ModelMap mm,
            @RequestParam("songid") String id,
            HttpServletResponse response,
            HttpSession session) {

        Song song = songService.getSongByID(Integer.parseInt(id));
        response.setHeader("Content-Disposition", "attachment; filename=" + song.getFilename());

        return song.getFile();
    }
    
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteSong(ModelMap mm,
            @RequestParam("songid") String id) {

        songService.deleteSong(Integer.parseInt(id));

        return "redirect: /song/showallsongs";
    }

    @RequestMapping(value = "/image", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody
    void getImageFromBlob(ModelMap mm,
            @RequestParam("songid") String id,
            HttpServletResponse response,
            HttpSession session) {

        Song song = songService.getSongByID(Integer.parseInt(id));
        response.setContentType("image/gif");

        OutputStream output;
        try {
            output = response.getOutputStream();
            try {
                output.write(song.getImage());
                output.close();
            } catch (IOException ex) {
                Logger.getLogger(SongController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(SongController.class.getName()).log(Level.SEVERE, null, ex);
        }

//        response.setHeader("Content-Disposition", "attachment; filename=" + song.getFilename());
//        return song.getFile();
    }

    @RequestMapping(value = "/getlyrics", method = RequestMethod.GET)
    public String getLyrics(ModelMap mm,
            @RequestParam("artist") String artist,
            @RequestParam("title") String title,
            RedirectAttributes ra
    ) {

//        mm.addAttribute("songlist", songService.getAllSongs());
        String lyrics = songUtils.getLyrics(artist, title);
        
        if(lyrics != null) {
                RedirectAttributes addAttribute = ra.addAttribute("success", true);
        } else {
                        RedirectAttributes addAttribute = ra.addAttribute("success", false);

        }
        RedirectAttributes addAttribute = ra.addAttribute("lyrics", lyrics);
        
//        if()
        return "redirect: /song/showallsongs";
    }
}
