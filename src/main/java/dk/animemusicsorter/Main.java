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
        LinkedList song_list = new LinkedList();
        
        System.out.println("What folder?");
        Scanner scan = new Scanner(System.in);
        String folder = scan.nextLine();
        
        System.out.println("List the songs in your preferred order. Type -1 if you are finished");
        int song_added = 1; //this will be the loop stopper
        
        while(song_added == 1){
            String song_string = scan.nextLine();
            if(song_string.equals("-1")){
                song_added = -1;
                break;
            }
            
            song_list.add(song_string);
        }
        
        if(song_list.isEmpty())
            System.exit(0);
        
        //iterate through the folder to match the first string of the linkedlist
        int size = song_list.size();
        
        for(int i = 0; i < size; i++){
            String current_song = (String) song_list.get(i);
            System.out.println("Current song #" + i + ": " + current_song);
            
            
        }
        
        
         
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
     
    //test if a s1 is the substring of s2 using recursion
    public static boolean isSubstring(String s1, String s2, int m, int n){
        if (m == 0) 
            return true;
        if (n == 0) 
            return false;
             
        // If last characters of two strings are matching
        if (s1.charAt(m-1) == s2.charAt(n-1))
            return isSubstring(s1, s2, m-1, n-1);
 
        // If last characters are not matching
        return isSubstring(s1, s2, m, n-1);
    }
    
    //open files in the directory and search for matching song names
    public static void findingSongs(String s1){
        //search the folder at the file names and use the substring method to find the appropirate songs.
        
    }
}
