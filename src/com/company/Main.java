package com.company;

import com.company.model.Artist;
import com.company.model.DataSource;
import com.company.model.Song;
import com.company.model.SongArtist;

import javax.security.sasl.SaslClient;
import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.SplittableRandom;

public class Main {

    public static void main(String[] args) {
        DataSource dataSource = new DataSource();
        if (!dataSource.open()) {
            System.out.println("Can't open datasource");
            return;
        }

        List<Artist> artists = dataSource.queryArtists(5);
        if (artists == null) {
            System.out.println("No artists!");
            return;
        }

        for (Artist artist : artists) {
            System.out.println("ID = " + artist.getId() + ", Name = " + artist.getName());
        }
        List<String> albumsForArtists =
                dataSource.queryAlbumsForArtists("Carole King", DataSource.ORDER_BY_DESC);

        for (String album : albumsForArtists) {
            System.out.println(album);
        }

        List<SongArtist> songArtists = dataSource.queryArtistForSong("Go Your Own Way", DataSource.ORDER_BY_DESC);
        if (songArtists == null) {
            System.out.println("Couldn't find the artist for the song");
        }
        for (SongArtist songArtist : songArtists) {
            System.out.println("Artist Name : " + songArtist.getArtistName() + ", Album name : " + songArtist.getAlbumName() +
                    ", Track : " + songArtist.getTrack());
        }

        dataSource.queryForMetaData();

        System.out.println("Number for songs : " + dataSource.getCount(DataSource.TABLE_SONGS));

        dataSource.createViewForSongArtists();

//        Scanner scanner = new Scanner(System.in);
//        System.out.print("Enter song title : ");
//        String title = scanner.nextLine();

//        songArtists = dataSource.querySongInfoView(title);
//        if(songArtists.isEmpty()) {
//            System.out.println("Couldn't find the artist for the song");
//            return;
//        }
//
//        for (SongArtist artist : songArtists) {
//            System.out.println("From View - Artist-Name = " + artist.getArtistName() + " Album-Name = " + artist.getAlbumName() +
//            " Track-Number = " + artist.getTrack());
//        }

        dataSource.insertSong("Ghungaru", "Vishal & Shekhar", "War", 7);

        dataSource.close();
    }
}
