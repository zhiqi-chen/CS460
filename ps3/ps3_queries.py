#
# CS 460: Problem Set 3: XQuery Programming Problems
#

#
# For each query, use a text editor to add the appropriate XQuery
# command between the triple quotes provided for that query's variable.
#
# For example, here is how you would include a query that finds
# the names of all movies in the database from 1990.
#
sample = """
    for $m in //movie
    where $m/year = 1990
    return $m/name
"""

#
# 1. Put your query for this problem between the triple quotes found below.
#    Follow the same format as the model query shown above.
#
query1 = """
    //name[../contains(pob, "Maine, USA")]
"""

#
# 2. Put your query for this problem between the triple quotes found below.
#
query2 = """
    for $p in //person[name = "Meryl Streep"],
        $o in //oscar,
        $m in //movie
    where $o/@person_id = $p/@id and $o/@movie_id = $m/@id
    return <streep_oscar>
           {
             <year>{$o/year/text()}</year>, 
             <type>{$o/type/text()}</type>, 
             <name>{$m/name/text()}</name>
           }
           </streep_oscar>
"""

#
# 3. Put your query for this problem between the triple quotes found below.
#
query3 = """
    for $m in //movie[rating = "R"],
        $o in //oscar[type = "BEST-PICTURE"]
    where $o/@movie_id = $m/@id
    order by $m/year
    return <winner>
           {$m/name/text(), " (", $o/year/text(), ")"}
           </winner>
"""

#
# 4. Put your query for this problem between the triple quotes found below.
#
query4 = """
    for $m in //movie[earnings_rank <= 200]
    let $o := //oscar[@movie_id = $m/@id]
    where count($o) >= 1
    return <oscar_grosser>
           {
             <name>{$m/name/text()}</name>,
             <earnings_rank>{$m/earnings_rank/text()}</earnings_rank>,
             <num_oscars>{count($o)}</num_oscars>,
             for $o in //oscar[@movie_id = $m/@id]
             return <award>{$o/type/text()}</award>
           }
           </oscar_grosser>
"""

#
# 5. Put your query for this problem between the triple quotes found below.
#
query5 = """
    for $r in distinct-values(//movie/rating)
    return <rating-info>
           {
           <rating>{$r}</rating>,
           let $m := //movie[rating = $r]
           return <num-movies>{count($m)}</num-movies>,
           let $m := //movie[rating = $r]
           return <avg-runtime>{avg($m/runtime)}</avg-runtime>,
           for $m in //movie[rating = $r]
           where $m[earnings_rank <= 20]
           return <top-twenty>{$m/name/text()}</top-twenty>
           }
           </rating-info>
"""
