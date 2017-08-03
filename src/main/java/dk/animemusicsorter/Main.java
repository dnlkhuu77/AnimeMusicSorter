/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dk.animemusicsorter;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v1Tag;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.ID3v24Tag;
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
        String folder = "./" + scan.nextLine() + "/";
        String album_name = "";
        
        System.out.println("List the songs in your preferred order. Type -1 if you are finished");
        int song_added = 1; //this will be the loop stopper
        
        while(song_added == 1){
            String song_string = scan.nextLine();
            System.out.println();
            
            if(song_string.equals("-1")){
                System.out.println("What is the album name?");
                Scanner scan_2 = new Scanner(System.in);
                album_name = scan_2.nextLine();
                song_added = -1;
                break;
            }
            
            song_list.add(song_string);
        }
        
        if(song_list.isEmpty()){
            System.out.println("You didn't add any songs");
            System.exit(0); 
        }
        int size = song_list.size();
        
        //should contain a array of all the songs in the order of TV Size, Full etc.
        String[] song_queue = new String[size*2];
        
        //POSSIBLE ERROR OF PASSING BY REFERENCE
        song_queue = findingSongs(folder, song_list);
        
        if(song_queue == null){
            System.out.println("There are no files in the folder");
            System.exit(0);
        }

        //make another method to take bother song_list and song_queue to do the mp3 changes in order.
        namingSongs(folder, song_queue, song_list, album_name);
        //FINAL
    }
    
    //open files in the directory and search for matching song names
    //you should return an array of strings (around 5) that contains the actual names of songs 
    public static String[] findingSongs(String folder_name, LinkedList song_list) throws UnsupportedTagException, InvalidDataException, IOException, NotSupportedException{
        //search the folder at the file names and use the substring method to find the appropirate songs.
        int size = song_list.size();
        String[] results = new String[size*2];
        int placement = 0; //used to place the songs in the array
        
        File folder = new File(folder_name);
        if(!folder.exists())
            return null;
        File[] files = folder.listFiles();
        if(files == null)
            System.out.println("The files folder is null");
        //System.out.println("The size of the files array is: " + files.length);
        
        //go through every string in the LinkedList
        for(int a = 0; a < song_list.size(); a++){
            String song_string = (String) song_list.get(a);
            
            //search through every file in the folder
            for(int i = 0; i < files.length; i++){
                if(files[i].isFile() && files[i].getName().endsWith(".mp3")){
                    String current_file = files[i].getName();
                    //System.out.println("Files in loop: " + current_file);
                    
                    //search 
                    if(current_file.contains(song_string)){
                        //save the 2 (TV-Size and Full songs)
                        if(results[placement] == null && placement % 2 == 0){ //at the first element
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
    
    public static void namingSongs(String folder, String[] songs, LinkedList song_list, String album_name) throws UnsupportedTagException, InvalidDataException, IOException, NotSupportedException{
        //the array is always half_size;
        //the songs are order 1. Oath Sign [TV-Size], 2. Oath Sign[Full], 3. to the beginning [TV-Size]
        //must change this ordering 
        
        //make a new folder with the modified songs
        File new_dir = new File("./" + album_name);
        if(!new_dir.exists()){
            System.out.println("Creating a new directory: " + new_dir.getName());
            new_dir.mkdir();
        }
        
        //USED FOR INDEX FOR ARRAY, BUT MUST +1 TO USE FOR ENTERING THE TAG
        int f_marker = 0; //increment by every odd number
        int l_marker = songs.length / 2; //increment by every even number
        String current = "";
        int title_index = 0; //this is to remade the song_title
        
        for(int i = 0; i < songs.length; i++){
            //we're going through the entire linkedlist of song titles (half of the the songs[])
            if(i % 2 == 0){
                current = (String) song_list.get(title_index);
                title_index++; 
            }
            
            Mp3File mp3file = new Mp3File(folder + "/" + songs[i]);
            
            if (mp3file.hasId3v1Tag()) {
                mp3file.removeId3v1Tag();
            }
            if (mp3file.hasCustomTag()) {
              mp3file.removeCustomTag();
            }
            //mp3file.save("Mp3FileWithoutTags.mp3");
           
            
            ID3v2 v2;
            if(mp3file.hasId3v2Tag()){
                v2 = mp3file.getId3v2Tag();
            }else{
                v2 = new ID3v24Tag();
                mp3file.setId3v2Tag(v2);
            }
            
            //IN THIS LOOP, ASK FOR ARTIST AND PICTURE
            //USE A SCANNER HERE
            if((i+1) % 2 == 1){ //if the track is odd
                v2.setTitle(current + " [TV-Size]");
                v2.setTrack(Integer.toString(f_marker+1));
                f_marker++;
                v2.setAlbum(album_name);
                v2.setYear("");
                mp3file.save("./" + album_name + "/" + current + " [TV-Size].mp3");
            }else{
                v2.setTitle(current + " [Full]");
                v2.setTrack(Integer.toString(l_marker+1));
                l_marker++;
                v2.setAlbum(album_name);
                v2.setYear("");
                mp3file.save("./" + album_name + "/" + current + " [Full].mp3");
            }
        }
        
    }
}
