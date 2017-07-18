/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dk.animemusicsorter;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.NotSupportedException;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.io.IOException;
import java.util.*;
import java.nio.file.Paths;


/**
 *
 * @author dnlkhuu77
 */
public class Main {
     public static void main(String[] args) throws UnsupportedTagException, InvalidDataException, IOException, NotSupportedException{
        Mp3File mp3file = new Mp3File("Anime/Hibike/DREAM SOLISTER [Full].mp3");
        System.out.println("Length of this mp3 is: " + mp3file.getLengthInSeconds() + " seconds");
        System.out.println("Bitrate: " + mp3file.getBitrate() + " kbps " + (mp3file.isVbr() ? "(VBR)" : "(CBR)"));
        System.out.println("Sample rate: " + mp3file.getSampleRate() + " Hz");
        System.out.println("Has ID3v1 tag?: " + (mp3file.hasId3v1Tag() ? "YES" : "NO"));
        System.out.println("Has ID3v2 tag?: " + (mp3file.hasId3v2Tag() ? "YES" : "NO"));
        System.out.println("Has custom tag?: " + (mp3file.hasCustomTag() ? "YES" : "NO"));
        
         if (mp3file.hasId3v1Tag()) {
        	ID3v1 id3v1Tag = mp3file.getId3v1Tag();
        	System.out.println("Track: " + id3v1Tag.getTrack());
        	System.out.println("Artist: " + id3v1Tag.getArtist());
        	System.out.println("Title: " + id3v1Tag.getTitle());
        	System.out.println("Album: " + id3v1Tag.getAlbum());
        	System.out.println("Year: " + id3v1Tag.getYear());
        	System.out.println("Genre: " + id3v1Tag.getGenre() + " (" + id3v1Tag.getGenreDescription() + ")");
        	System.out.println("Comment: " + id3v1Tag.getComment());
        }else{
             System.out.println("Nothing, the directory is: " + Paths.get(".").toAbsolutePath().normalize().toString());
         }
    }
}
