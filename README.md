# AnimeMusicSorter

This is a Java application utliziting Maven and the [MP3agic library](https://github.com/mpatric/mp3agic).

This application will organize anime MP3 files (ripped and downloaded from CDs) according to the song's length and whether it's an OP/ED. This is intended for easy organizing when imported to iTunes or Spotify's local song lists.

The songs will be listed in this order:

1. Oath [TV-Size]
2. Seeing [TV-Size]
3. Oath [Full]
4. Seeing [Full]

TV-Size songs are shorter than full length songs.

The program uses Maven. When this runs, the program will ask for the order of the songs you request. This will further ask for more MP3 information like artist, composer, etc. It will create a folder with all the edited songs after it finishes completion. 