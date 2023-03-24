#
# CS 460: Problem Set 5: MongoDB Query Problems
#

#
# For each query, use a text editor to add the appropriate XQuery
# command between the triple quotes provided for that query's variable.
#
# For example, here is how you would include a query that finds
# the names of all movies in the database from 1990.
#
sample = """
    db.movies.find( { year: 1990 }, 
                    { name: 1, _id: 0 } )
"""

#
# 1. Put your query for this problem between the triple quotes found below.
#    Follow the same format as the model query shown above.
#
query1 = """
    db.movies.find( { rating: "R", runtime: { $lt: 100 } },
                    { _id: 0, name: 1, runtime: 1 } )

"""

#
# 2. Put your query for this problem between the triple quotes found below.
#
query2 = """
    db.movies.find( { name: /Story/ },
                    { _id: 0, name: 1, year: 1 } )
"""

#
# 3. Put your query for this problem between the triple quotes found below.
#
query3 = """
    db.oscars.find( { type: /SUPPORTING/, year: { $gte: 2015 } },
                    { _id: 0, "person.name": 1, year: 1 } )
"""

#
# 4. Put your query for this problem between the triple quotes found below.
#
query4 = """
    db.people.find( { dob: /1972/, hasActed: true },
                    { _id: 0, name: 1, dob: 1 } )

"""

#
# 5. Put your query for this problem between the triple quotes found below.
#
query5 = """
    db.movies.distinct( "directors.name",
                        { "actors.name": "Daniel Radcliffe" },
                        { _id: 0, "directors.name": 1 } )
"""

#
# 6. Put your query for this problem between the triple quotes found below.
#
query6 = """
    db.movies.aggregate( { $group: { _id: "$rating",
                                     count: { $sum: 1 },
                                     avg_runtime: { $avg: "$runtime" } } },
                         { $project: { _id: 0,
                                       avg_runtime: "$avg_runtime",
                                       movie_count: "$count",
                                       rating: "$_id"} } )
    
"""

#
# 7. Put your query for this problem between the triple quotes found below.
#
query7 = """
    db.people.aggregate( { $match: { hasActed: true } },
                         { $sort: { dob: -1 } },
                         { $limit: 1 },
                         { $project: { _id: 0,
                                       name: 1,
                                       dob: 1 } } )

"""

#
# 8. Put your query for this problem between the triple quotes found below.
#
query8 = """
    db.people.aggregate( { $match: { $and: [ { dob: { $gte: '2000-01-01' } },
                                             { dob: { $lte: '2009-12-31' } } ] } },
                         { $group: { _id: null, count: { $sum: 1 } } },
                         { $project: {_id: 0, num_first_decade: "$count" } } )
"""

#
# 9. Put your query for this problem between the triple quotes found below.
#
query9 = """
    db.movies.aggregate( { $match: { earnings_rank: { $exists: true } } },
                         { $unwind: "$directors" },
                         { $group: { _id: "$directors.name", count: { $sum: 1 } } },
                         { $match: { count: { $gte: 4 } } },
                         { $project: {_id: 0,
                                      director: "$_id",
                                      num_top_grossers: "$count" } } )

"""

#
# 10. Put your query for this problem between the triple quotes found below.
#
query10 = """
db.oscars.aggregate( { $match: { type: { $not: /BEST-PICTURE/ } } }, 
                     { $group: { _id: "$person.name",
                                 count: { $sum: 1 },
                                 movies: { $addToSet: "$movie.name" } } },
                     { $sort: { count: -1 } }, 
                     { $limit: 10 },
                     { $project: { _id: 0,
                                   award_count: "$count",
                                   movies: "$movies",
                                   big_winner: "$_id" } } )

"""
