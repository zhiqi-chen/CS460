/*
 * TestDriver
 * 
 * A program that can be used for testing your code for Problem 7.
 */

import java.sql.*;      // needed for the JDBC-related classes 
import java.io.*;       // needed for file-related classes

public class TestDriver {
    public static void main(String[] args) 
        throws ClassNotFoundException, SQLException, FileNotFoundException
    {
        // Add your test code below.
        // p1
        // XMLforPeople xml = new XMLforPeople("movie.sqlite");
        // System.out.println(xml.fieldsFor(xml.idFor("Julianne Moore")));
        // System.out.println(xml.fieldsFor("1234567"));   // no person with that id
        // System.out.println(xml.fieldsFor(xml.idFor("Elisabeth Moss")));

        // p2
        // XMLforPeople xml = new XMLforPeople("movie.sqlite");
        // String query1 = "SELECT year, name FROM Movie WHERE year = 2020;";
        // ResultSet results1 = xml.resultsFor(query1);
        // System.out.println(xml.movieElemsFrom(results1));

        // String query2 = "SELECT year, name FROM Movie WHERE earnings_rank = 1;";
        // ResultSet results2 = xml.resultsFor(query2);
        // System.out.println(xml.movieElemsFrom(results2));

        // p3
        // XMLforPeople xml = new XMLforPeople("movie.sqlite");
        // System.out.println(xml.actedIn(xml.idFor("Geena Davis")));
        // System.out.println(xml.actedIn("1234567"));
        // System.out.println(xml.actedIn(xml.idFor("Denzel Washington")));

        // p4
        // XMLforPeople xml = new XMLforPeople("movie.sqlite");
        // System.out.println(xml.directed(xml.idFor("Geena Davis")));
        // System.out.println(xml.directed("1234567"));
        // System.out.println(xml.directed(xml.idFor("Denzel Washington")));

        //p5
        XMLforPeople xml = new XMLforPeople("movie.sqlite");
        System.out.println(xml.elementFor(xml.idFor("Elisabeth Moss")));
        System.out.println(xml.elementFor("1234567"));
        System.out.println(xml.elementFor(xml.idFor("Denzel Washington")));
        
    }
}