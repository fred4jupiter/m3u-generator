# m3u-generator #

Simple project for creating m3u playlists with Spring-Shell.

## Usage ##

To build the project run

    mvn install

To run the programm call (replace VERSION with current version)

    cd target
    java -jar m3u-generator-VERSION.jar

On Spring Shell you have the following options

    spring-shell>m3u forEachDirLevelOnem3u
    spring-shell>m3u oneForAll
    
Examples:

    spring-shell>m3u forEachDirLevelOne --basedir d:/Temp4
    spring-shell>m3u oneForAll --basedir d:/Temp4 --playlistName "Fred.m3u"

For more help, please just enter

    spring-shell>help

## Travis CI ##

The current build status can be found on

## Travis Build Status ##
[![Build Status](https://travis-ci.org/fred4jupiter/m3u-generator.svg?branch=master)](https://travis-ci.org/fred4jupiter/m3u-generator)
