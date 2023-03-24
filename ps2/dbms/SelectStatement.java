/*
 * SelectStatement.java
 *
 * DBMS Implementation
 */

import java.util.*;
import com.sleepycat.je.*;

/**
 * A class that represents a SELECT statement.
 */
public class SelectStatement extends SQLStatement {
    /* 
     * Used in the selectList for SELECT * statements.
     */
    public static final String STAR = "*";
    
    private ArrayList<Object> selectList;
    private Limit limit;
    private boolean distinctSpecified;
    
    /** 
     * Constructs a SelectStatement object involving the specified
     * columns and other objects from the SELECT clause, the specified
     * tables from the FROM clause, the specified conditional
     * expression from the WHERE clause (if any), the specified Limit
     * object summarizing the LIMIT clause (if any), and the specified
     * value indicating whether or not we should eliminate duplicates.
     *
     * @param  selectList  the columns and other objects from the SELECT clause
     * @param  fromList  the list of tables from the FROM clause
     * @param  where  the conditional expression from the WHERE clause (if any)
     * @param  limit  summarizes the info in the LIMIT clause (if any)
     * @param  distinctSpecified  should duplicates be eliminated?
     */
    public SelectStatement(ArrayList<Object> selectList, 
                           ArrayList<Table> fromList, ConditionalExpression where,
                           Limit limit, Boolean distinctSpecified)
    {
        super(fromList, new ArrayList<Column>(), where);
        this.selectList = selectList;
        this.limit = limit;
        this.distinctSpecified = distinctSpecified.booleanValue();
        
        /* add the columns in the select list to the list of columns */
        for (int i = 0; i < selectList.size(); i++) {
            Object selectItem = selectList.get(i);
            if (selectItem instanceof Column) {
                this.addColumn((Column)selectItem);
            }
        }
    }
    
    /**
     * Returns a boolean value indicating whether duplicates should be
     * eliminated in the result of this statement -- i.e., whether the
     * user specified SELECT DISTINCT.
     */
    public boolean distinctSpecified() {
        return this.distinctSpecified;
    }
    
    public void execute() throws DatabaseException, DeadlockException {
        TableIterator iter = null;
        
        try {
            /* 
             * PS 2: Add code here to implement the rest of the method
             * as described in the assignment.
             */

            Table table = this.getTable(0);
            if (table.open() != OperationStatus.SUCCESS) {
                throw new Exception("open table unsucceed");  // error msg was printed in open()
            }

            if (this.selectList.get(0).equals(STAR)) {
                selectList.remove(0);
                for (int i = 0; i < table.numColumns(); i++) {
                    Object selectItem = table.getColumn(i);
                    if (selectItem instanceof Column) {
                        selectList.add((Column)selectItem);
                    }
                }
            }

            // if more than one table in the FROM clause
            if (this.numTables() != 1) {
                throw new Exception("more than one table in the FROM clause");
            }

            // else if one or more columns selected specified in the SELECT clause
            else if (this.selectList.size() != table.numColumns()) {
                throw new Exception("one or more columns selected specified in the SELECT clause");
            }

            else {
                // If there are no errors, 
                // finish by printing a message that includes the number of tuples selected
                iter = new TableIterator(this, table, true);
                iter.printAll(System.out);
            }

        } catch (Exception e) {
            String errMsg = e.getMessage();
            if (errMsg != null) {
                System.err.println(errMsg + ".");
            }
        }
        
        if (iter != null) {
            iter.close();
        }
    }
}

// javac -cp 'lib/*' -d classes *.java
// java -cp 'lib/*:classes' DBMS
// CREATE TABLE Course(name VARCHAR(20), enrollment REAL);
// INSERT INTO Course VALUES ('CS 460', 0.002);
