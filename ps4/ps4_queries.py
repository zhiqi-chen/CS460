#
# CS 460: Problem Set 4: XQuery Programming Problems
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
    //movie[rating = "PG-13"][runtime > 180]/name
"""

#
# 2. Put your query for this problem between the triple quotes found below.
#
query2 = """
    for $m in //movie[rating = "PG-13"]
    where $m/runtime > 180
    order by $m/runtime
    return <long_pg13>{$m/name/text()} ({$m/runtime/text()} minutes)</long_pg13>
"""

#
# 3. Put your query for this problem between the triple quotes found below.
#
query3 = """
    for $p in //person,
        $fo in //oscar[@person_id = $p/@id]
    for $so in //oscar[@person_id = $p/@id]
    where $so/year = $fo/year + 1
    return <back_to_back>
           {                
               <name>{$p/name/text()}</name>,
               <first_win>{$fo/type/text()} ({$fo/year/text()})</first_win>,
               <second_win>{$so/type/text()} ({$so/year/text()})</second_win>
           }
           </back_to_back>
"""

#
# 4. Put your query for this problem between the triple quotes found below.
#
query4 = """
    for $p in //person
    let $o := //oscar[@person_id = $p/@id][contains(type, "BEST-ACT")],
        $m := //movie[contains(@actors, $p/@id)]
    where count($m) >= 6 and count($o) >= 2
    return <legend>
           {                
               <name>{$p/name/text()}</name>,
               <movie_count>{count($m)}</movie_count>,
               for $om in $o[@movie_id = $m/@id]
               let $mn := //movie[@id = $om/@movie_id]
               order by $om/year
               return <won_for>{$mn/name/text()} ({$om/year/text()})</won_for>
           }
           </legend>
"""
