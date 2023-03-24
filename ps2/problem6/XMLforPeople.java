/*
 * XMLforPeople
 * 
 * A class for objects that convert data about people from 
 * the relational database used in PS 1 to XML.
 * 
 * Name: Zhiqi Chen
 * Partner: Siyu Chen
 * 
 */

import java.sql.*;      // needed for the JDBC-related classes
import java.io.*;       // needed for file-related classes

public class XMLforPeople {
    private Connection db;   // a connection to the database
    
    /*
     * XMLforPeople constructor - takes the name of a SQLite file containing
     * a Person table like the one from PS 1, and creates an object that 
     * can be used to convert the data in that table to XML.
     * 
     * ** YOU SHOULD NOT CHANGE THIS METHOD **
     */
    public XMLforPeople(String dbFilename) throws SQLException {
        this.db = DriverManager.getConnection("jdbc:sqlite:" + dbFilename);
    }

    /*
     * simpleElem - a private helper method takes the name and value of 
     * a simple XML element and returns a string representation of that 
     * element
     * 
     * ** YOU SHOULD NOT CHANGE THIS METHOD **
     */
    private String simpleElem(String name, String value) {
        String elem = "<" + name + ">";
        elem += value;
        elem += "</" + name + ">";
        return elem;
    }
    
    /*
     * Takes a string representing a SQL query for the movie database
     * and returns a ResultSet object that represents the results
     * of the query (if any).
     * 
     * ** YOU SHOULD NOT CHANGE THIS METHOD **
     */
    public ResultSet resultsFor(String query) throws SQLException {
        Statement stmt = this.db.createStatement();
        ResultSet results = stmt.executeQuery(query);
        return results;
    }

    /*
     * idFor - takes the name of a person and returns the id number of 
     * that person in the database as a string. If the movie is not in the 
     * database, it returns an empty string.
     * 
     * ** YOU SHOULD NOT CHANGE THIS METHOD **
     */
    public String idFor(String name) throws SQLException {
        String query = "SELECT id FROM Person WHERE name = '" + name + "';";
        ResultSet results = resultsFor(query);
        
        if (results.next()) {    
            String id = results.getString(1);
            return id;
        } else {
            return "";
        }
    }

    /*
     * fieldsFor - takes a string representing the id number of a person
     * and returns a sequence of XML elements for the non-null field values
     * of that person in the database. If there is no person with the 
     * specified id number, the method returns an empty string.
     */
    public String fieldsFor(String personID) throws SQLException {
        String query = "SELECT name, dob, pob FROM Person WHERE id =  '" + personID + "';";
        ResultSet results = resultsFor(query);
        
        // replace this return statement with your implementation
        if (results.next()) {    
            String fields = "";

            String name = results.getString(1);
            if (name != null) {
                fields += "    ";
                fields += simpleElem("name", name);
                fields += "\n";
            }

            String dob = results.getString(2);
            if (dob != null) {
                fields += "    ";
                fields += simpleElem("dob", dob);
                fields += "\n";
            }

            String pob = results.getString(3);
            if (pob != null) {
                fields += "    ";
                fields += simpleElem("pob", pob);
                fields += "\n";
            }

            return fields;
            
        } else {
            return "";
        }
    }

    /*
     * movieElemsFrom - takes a ResultSet for a SQL query that produces
     * tuples of the form (movie year, movie name) and creates a sequence
     * of complex elements of type movie for them. If the result set is
     * empty, the method returns the empty string.
     */
    public String movieElemsFrom(ResultSet results) throws SQLException {
        
        // replace this return statement with your implementation
        if (results.next()) {    
            String movieElems = "";
            String movies = "";

            String year = results.getString(1);
            if (year != null) {
                movieElems += "        ";
                movieElems += simpleElem("year", year);
                movieElems += "\n";
            }

            String name = results.getString(2);
            if (name != null) {
                movieElems += "        ";
                movieElems += simpleElem("name", name);
                movieElems += "\n";
            }
            
            String movie = "      <movie>\n" + movieElems + "      </movie>\n";
            movies += movie;

            while (results.next()) {
                movieElems = "";
                year = results.getString(1);
                if (year != null) {
                    movieElems += "        ";
                    movieElems += simpleElem("year", year);
                    movieElems += "\n";
                }

                name = results.getString(2);
                if (name != null) {
                    movieElems += "        ";
                    movieElems += simpleElem("name", name);
                    movieElems += "\n";
                }

                movie = "      <movie>\n" + movieElems + "      </movie>\n";
                movies += movie;
            }

            return movies;

        } else {
            return "";
        }
    }
    
    /*
     * actedIn - takes a string representing the id number of a person.
     * If the person has acted in one or more movies in the database,
     * the method constructs and returns a single complex XML element named 
     * "actedIn" that contains a nested child element named "movie" for 
     * each movie that person acted in. If the person has not acted
     * in any movies or if the person id is not in the database, the method 
     * returns an empty string.
     */
    public String actedIn(String personID) throws SQLException {
        String query = "SELECT year, name FROM Actor, Movie WHERE movie_id = id AND actor_id =  '" + personID + "' ORDER BY year;";
        ResultSet results = resultsFor(query);

        // replace this return statement with your implementation
        String movies = "";
        
        // check if there are any row in the ResultSet without move the cursor
        if (results.isBeforeFirst()) {   
            movies = movieElemsFrom(results);
            String actedIn = "    <actedIn>\n" + movies + "    </actedIn>\n";
            return actedIn;

        } else {
            return "";
        }
    }    
    
    /*
     * directed - takes a string representing the id number of a person.
     * If the person has directed one or more movies in the database,
     * the method constructs and returns a single complex XML element named 
     * "directed" that contains a nested child element named "movie" for 
     * each movie directed by that person. If the person has not directed
     * any movies or if the person id is not in the database, the method 
     * returns an empty string.
     */
    public String directed(String personID) throws SQLException {
        
        // replace this return statement with your implementation
        String query = "SELECT Movie.year, Movie.name FROM Director, Movie WHERE movie_id = Movie.id AND director_id = '" + personID + "' ORDER BY Movie.year, Movie.name;";
        ResultSet results = resultsFor(query);
        String movies = "";
        if (results.isBeforeFirst()) {   
            movies = movieElemsFrom(results);
            String directed = "    <directed>\n" + movies + "    </directed>\n";
            return directed;

        } else {
            return "";
        }

        

    }    
    
    /*
     * elementFor - takes a string representing the id number of a person
     * and returns a single complex XML element named "person" that contains
     * nested child elements for all of the fields and movies associated
     * with that person in the database. If there is no person with 
     * the specified id number, the method returns an empty string.
     */
    public String elementFor(String personID) throws SQLException {
        
        // replace this return statement with your implementation
        String info = "";
        String results ="";
        String person = fieldsFor(personID);
        String act = actedIn(personID);
        String direct = directed(personID);
        if(!person.equals("")){
            info += person+act+direct;
            results = "  <person id=\"" + personID + "\">\n" + info + "  </person>\n";
        }
        
        return results; 
    }

    /*
     * createFile - creates a text file with the specified filename 
     * containing an XML representation of the entire Person table.
     * 
     * ** YOU SHOULD NOT CHANGE THIS METHOD **
     */
    public void createFile(String filename) 
      throws FileNotFoundException, SQLException 
    {
        PrintStream outfile = new PrintStream(filename);    
        outfile.println("<?xml version=\"1.0\" encoding=\"iso-8859-1\"?>");
        outfile.println("<people>");
        
        // Use a query to get all of the ids from the Movie Table.
        ResultSet results = resultsFor("SELECT id FROM Person;");
        
        // Process one movie id at a time, creating its 
        // XML element and writing it to the output file.
        while (results.next()) {
            String personID = results.getString(1);
            outfile.println(elementFor(personID));
        }
        
        outfile.println("</people>");
        
        // Close the connection to the output file.
        outfile.close();
        System.out.println(filename + " has been written.");
    }
    
    /*
     * closeDB - closes the connection to the database that was opened
     * when the XMLforPeople object was constructed
     * 
     * ** YOU SHOULD NOT CHANGE THIS METHOD **
     */
    public void closeDB() throws SQLException {
        this.db.close();
    }
    
    public static void main(String[] args) 
        throws ClassNotFoundException, SQLException, FileNotFoundException
    {
        XMLforPeople xml = new XMLforPeople("movie.sqlite");
        xml.createFile("people.xml");
        xml.closeDB();
    }
}
