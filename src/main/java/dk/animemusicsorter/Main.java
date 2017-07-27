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
import java.io.*;


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
        int size = song_list.size();
        
        String[] song_queue = new String[size*2];
        //should contain a array of all the songs in the order of TV Size, Full etc.
        song_queue = findingSongs(folder, song_list);

        //make another method to take bother song_list and song_queue to do the mp3 changes in order.
         
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
    
    //open files in the directory and search for matching song names
    //you should return an array of strings (around 5) that contains the actual names of songs 
    public static String[] findingSongs(String folder_name, LinkedList song_list) throws UnsupportedTagException, InvalidDataException, IOException, NotSupportedException{
        //search the folder at the file names and use the substring method to find the appropirate songs.
        int size = song_list.size();
        String[] results = new String[size*2];
        int placement = 0; //used to place the songs in the array
        
        File folder = new File(folder_name);
        File[] files = folder.listFiles();
        
        //go through every string in the LinkedList
        for(int a = 0; a < song_list.size(); a++){
            String song_string = (String)song_list.get(a);
            
            //search through every file in the folder
            for(int i = 0; i < files.length; i++){
                if(files[i].isFile()){
                    String current_file = files[i].getName();
                    
                    //search 
                    if(current_file.contains(song_string)){
                        //save the 2 (TV-Size and Full songs)
                        if(results[placement] == null && placement % 2 == 0){ //it's every two songs
                            results[placement] = current_file; //saving the name of the file in the array
                            placement++;
                        }
                        else if(results[placement] == null && placement % 2 == 1){ //the cursor moved to the second bit
                            String song1_s = results[placement - 1];
                            Mp3File song1 = new Mp3File(folder_name + "/" + song1_s);
                            Mp3File song2 = new Mp3File(folder_name + "/" + current_file);
                            long length1 = song1.getLengthInSeconds();
                            long length2 = song2.getLengthInSeconds();
                            
                            if(length1 > length2){
                                results[placement] = results[placement - 1];
                                results[placement-1] = current_file;
                            }else{
                                results[placement] = current_file;
                            }
                            placement++;
                        }
                    }
                }
            }
        }
        //at the end of the loop
        
        return results;
        
    }
}
