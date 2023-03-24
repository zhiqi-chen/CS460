#
# CS 460: Problem Set 1, SQL Programming Problems
#

#
# For each problem, use a text editor to add the appropriate SQL
# command between the triple quotes provided for that problem's variable.
#
# For example, here is how you would include a query that finds the
# names and years of all movies in the database with an R rating:
#
sample = """
    SELECT name, year
    FROM Movie
    WHERE rating = 'R';
"""

#
# Problem 4. Put your SQL command between the triple quotes found below.
#
problem4 = """
    SELECT name, dob
    FROM Person
    WHERE name LIKE 'Will %';
"""

#
# Problem 5. Put your SQL command between the triple quotes found below.
#
problem5 = """
    SELECT name, year
    FROM Movie
    WHERE name IS 'Good Will Hunting' OR name IS 'Mystic River';
"""

#
# Problem 6. Put your SQL command between the triple quotes found below.
#
problem6 = """
    SELECT O.year, O.type, M.name
    FROM Oscar O, Movie M, Person P
    WHERE P.name = 'Meryl Streep'
      AND O.movie_id = M.id
      AND O.person_id = P.id; 
"""

#
# Problem 7. Put your SQL command between the triple quotes found below.
#
problem7 = """
    SELECT COUNT( DISTINCT P.id)
    FROM Actor A, Person P
    WHERE A.actor_id = P.id
      AND P.pob LIKE '%Australia';  
"""

#
# Problem 8. Put your SQL command between the triple quotes found below.
#
problem8 = """
    SELECT name, year
    FROM Movie
    WHERE rating = 'R'
      AND year = (SELECT MIN(year)
		  FROM Movie
		  WHERE rating = 'R');
"""

#
# Problem 9. Put your SQL command between the triple quotes found below.
#
problem9 = """
    SELECT rating, COUNT(id), MIN(earnings_rank)
    FROM Movie
    WHERE earnings_rank IS NOT NULL
    GROUP BY rating
    ORDER BY COUNT(id) DESC;         
"""

#
# Problem 10. Put your SQL command between the triple quotes found below.
#
problem10 = """
    SELECT pob, COUNT(id)
    FROM Person
    WHERE pob IS NOT NULL 
    GROUP BY pob
    HAVING COUNT(id) > 50;
"""

#
# Problem 11. Put your SQL command between the triple quotes found below.
# 
problem11 = """
    SELECT COUNT(DISTINCT actor_id)
    FROM Actor
    WHERE actor_id NOT IN (SELECT actor_id
                           FROM Movie, Actor
                           WHERE id = movie_id
                             AND year >= 2010);      
"""

#
# Problem 12. Put your SQL command between the triple quotes found below.
#
problem12 = """
    SELECT P.name, COUNT(O.type)
    FROM Person P Left JOIN Oscar O on P.id = O.person_id
    WHERE P.pob LIKE '%Spain' 
    GROUP BY P.name;            
"""

#
# Problem 13. Put your SQL command between the triple quotes found below.
#
problem13 = """
    SELECT O.type, AVG(M.runtime)
    FROM Oscar O, Movie M
    WHERE O.movie_id = M.id
    GROUP BY O.type;            
"""

#
# Problem 14. Put your SQL command between the triple quotes found below.
#
problem14 = """
    SELECT name, MIN(M.runtime)
    FROM Movie M
    UNION
    SELECT name, MAX(M.runtime)
    FROM Movie M;            
"""

#
# Problem 15. Put your SQL command between the triple quotes found below.
#
problem15 = """
    UPDATE Movie
    SET earnings_rank = 212
    WHERE name = 'Ratatouille';            
"""
