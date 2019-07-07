/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.song.utils;

import com.example.song.controllers.SongController;
import com.example.song.entities.Song;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagField;
import org.jaudiotagger.tag.datatype.Artwork;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.ID3v23Frame;
import org.jaudiotagger.tag.id3.ID3v23Frames;
import org.jaudiotagger.tag.id3.ID3v24FieldKey;
import org.jaudiotagger.tag.id3.ID3v24Frame;
import org.jaudiotagger.tag.id3.ID3v24Frames;
import org.jaudiotagger.tag.id3.ID3v24Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Los_e
 */
@Component
public class SongUtils {

    public void getMP3Info(MultipartFile sourcefile, Song song) {

        Map< String, String> hm = new HashMap< String, String>();
        hm.put("title", null);
        hm.put("album", null);
        hm.put("artist", null);
        hm.put("year", null);
//        hm.put("attached_picture", null);

        //create temp file of the uploaded mp3 in order to read its tags
        try {

            File tmp = File.createTempFile("test", ".mp3");
            OutputStream os = Files.newOutputStream(tmp.toPath());
            os.write(sourcefile.getBytes());

            org.jaudiotagger.audio.mp3.MP3File f = null;

            try {
                f = (org.jaudiotagger.audio.mp3.MP3File) AudioFileIO.read(tmp);
            } catch (CannotReadException ex) {
                Logger.getLogger(SongController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(SongController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (org.jaudiotagger.tag.TagException ex) {
                Logger.getLogger(SongController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ReadOnlyFileException ex) {
                Logger.getLogger(SongController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidAudioFrameException ex) {
                Logger.getLogger(SongController.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (f.hasID3v2Tag()) {
                AbstractID3v2Tag v2tag = f.getID3v2Tag();

                for (Map.Entry<String, String> entry : hm.entrySet()) {

                    entry.setValue(getTagContent(v2tag, entry.getKey()));
                    System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());

                }

                song.setTitle(hm.get("title"));
                song.setAlbum(hm.get("album"));
                song.setArtist(hm.get("artist"));
                song.setYearofrelease(hm.get("year"));

                try {
                    song.setImage(v2tag.getFirstArtwork().getBinaryData());
                } catch (Exception e) {
                }
                //Delete tempfile
                tmp.delete();
            }

        } catch (IOException ex) {
            Logger.getLogger(SongController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String getTagContent(AbstractID3v2Tag v2tag, String frametype) {

        String value = null;

        if (v2tag.getClass().getSimpleName().equals("ID3v24Tag")) {
            if (frametype.equals("title")) {
                value = v2tag.getFirst(ID3v24Frames.FRAME_ID_TITLE);
            }
            if (frametype.equals("album")) {
                value = v2tag.getFirst(ID3v24Frames.FRAME_ID_ALBUM);
            }
            if (frametype.equals("artist")) {
                value = v2tag.getFirst(ID3v24Frames.FRAME_ID_ARTIST);
            }
            if (frametype.equals("year")) {
                value = v2tag.getFirst(ID3v24Frames.FRAME_ID_YEAR);
            }
//        if (frametype.equals("attached_picture")) {
//            value = v2tag.getFirstArtwork();
//        }
        }

        if (v2tag.getClass().getSimpleName().equals("ID3v23Tag")) {
            if (frametype.equals("title")) {
                value = v2tag.getFirst(ID3v23Frames.FRAME_ID_V3_TITLE);
            }
            if (frametype.equals("album")) {
                value = v2tag.getFirst(ID3v23Frames.FRAME_ID_V3_ALBUM);
            }
            if (frametype.equals("artist")) {
                value = v2tag.getFirst(ID3v23Frames.FRAME_ID_V3_ARTIST);
            }
            if (frametype.equals("year")) {
                value = v2tag.getFirst(ID3v23Frames.FRAME_ID_V3_TYER);
            }
//        if (frametype.equals("attached_picture")) {
//            value = v2tag.getFirstArtwork();
//        }
        }

        if (value.isEmpty()) {
            value = deepSearch(v2tag, frametype);
            return value;
        } else {
            return value;
        }

//        return null;
    }

    public String deepSearch(AbstractID3v2Tag v2tag, String frametype) {
        //Loop through the contents of the mp3's tags and find desired value based on the tags description.
        //Especially in the case of  an mp3 having been converted from another type of file, its tags might not have been converted correctly

        Iterator<TagField> it = v2tag.getFields();
        String value = null;
        String description = null;
        while (it.hasNext()) {

            if (v2tag.getClass().getSimpleName().equals("ID3v23Tag")) {
                ID3v23Frame tf = (ID3v23Frame) it.next();
                System.out.println(tf.getBody().getLongDescription() + " Value " + tf.getBody().getUserFriendlyValue());
//            System.out.println(tf.getId() +tf.getBody().getLongDescription() );
                description = tf.getBody().getLongDescription();

                if (description.toLowerCase().contains(frametype.toLowerCase())) {
                    value = tf.getBody().getUserFriendlyValue();
//                System.out.println(description + value);
                }
            }
            if (v2tag.getClass().getSimpleName().equals("ID3v24Tag")) {
                ID3v24Frame tf = (ID3v24Frame) it.next();
                System.out.println(tf.getBody().getLongDescription() + " Value " + tf.getBody().getUserFriendlyValue());
//            System.out.println(tf.getId() +tf.getBody().getLongDescription() );
                description = tf.getBody().getLongDescription();

                if (description.toLowerCase().contains(frametype.toLowerCase())) {
                    value = tf.getBody().getUserFriendlyValue();
//                System.out.println(description + value);
                }
            }

        }
        return value;
    }

    public String getLyrics(String artist, String title) {
        String lyrics = null;
        String replacedstr = null;
        JsonNode rootlyrics = null;

        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl = "https://api.lyrics.ovh/v1/" + artist + "/" + title;
        System.out.println(fooResourceUrl);

        ResponseEntity<String> response = null;
        try {
            response = restTemplate.getForEntity(fooResourceUrl, String.class);

            if (response.getStatusCode().equals(HttpStatus.OK)) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = null;
                try {
                    root = mapper.readTree(response.getBody());
                } catch (IOException ex) {
                    Logger.getLogger(SongUtils.class.getName()).log(Level.SEVERE, null, ex);
                }

//            Iterator<Map.Entry<String, JsonNode>> it = root.fields();
//            
//            while(it.hasNext()) {
//                Map.Entry<String, JsonNode> node = it.next();
//                System.out.println(node.getKey() + " " + node.getValue());
//            }
                lyrics = root.get("lyrics").asText();

//            String test = "hello";
                replacedstr = lyrics.replaceAll("\n", "<br/>");
                System.out.println(replacedstr);

            }
        } catch (RestClientException restClientException) {
            Logger.getLogger(SongUtils.class.getName()).log(Level.SEVERE, null, restClientException);
        }

        return replacedstr;

    }
}
