# m3u-generator #

Simple project for creating m3u playlists with Spring-Shell.

## Usage ##

To build the project run

    mvn install

To run the programm call (replace VERSION with current version)
    
    java -jar target/m3u-generator-VERSION.jar
    
Examples:

    m3u-shell>m3u oneForAll --basedir d:/Temp4
	m3u-shell>m3u oneForAll --basedir d:/Temp4 --playlistName MyList
	m3u-shell>m3u oneForAll --basedir d:/Temp4 --playlistName MyList --sortByTrackNumber
	m3u-shell>m3u forEachArtist --basedir d:/Temp4
	m3u-shell>m3u create --basedir d:/Temp4
	m3u-shell>m3u create --type EVERY_ARTIST_ALBUM --basedir d:/Temp4
    

For more help, please just enter

    m3u-shell>help

## Github Build Status ##
![Github Build Status](https://github.com/fred4jupiter/m3u-generator/actions/workflows/build.yml/badge.svg)