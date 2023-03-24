/*
 * InsertRow.java
 *
 * DBMS Implementation
 */

import java.io.*;
import java.util.Arrays;

/**
 * A class that represents a row that will be inserted in a table in a
 * relational database.
 *
 * This class contains the code used to marshall the values of the
 * individual columns to a single key-value pair.
 */
public class InsertRow {
    private Table table;           // the table in which the row will be inserted
    private Object[] columnVals;   // the column values to be inserted
    private RowOutput keyBuffer;   // buffer for the marshalled row's key
    private RowOutput valueBuffer; // buffer for the marshalled row's value
    private int[] offsets;         // offsets for header of marshalled row's value
    
    /** Constants for special offsets **/
    /** The field with this offset has a null value. */
    public static final int IS_NULL = -1;
    
    /** The field with this offset is a primary key. */
    public static final int IS_PKEY = -2;
    
    /**
     * Constructs an InsertRow object for a row containing the specified
     * values that is to be inserted in the specified table.
     *
     * @param  t  the table
     * @param  values  the column values for the row to be inserted
     */
    public InsertRow(Table table, Object[] values) {
        this.table = table;
        this.columnVals = values;
        this.keyBuffer = new RowOutput();
        this.valueBuffer = new RowOutput();
        
        // Note that we need one more offset than value,
        // so that we can store the offset of the end of the record.
        this.offsets = new int[values.length + 1];
    }
    
    /**
     * Takes the collection of values for this InsertRow
     * and marshalls them into a key/value pair.
     * 
     * (Note: We include a throws clause because this method will use 
     * methods like writeInt() that the RowOutput class inherits from 
     * DataOutputStream, and those methods could in theory throw that 
     * exception. In reality, an IOException should *not* occur in the
     * context of our RowOutput class.)
     */
    public void marshall() throws IOException {
        /* 
         * PS 2: Implement this method. 
         * 
         * Feel free to also add one or more private helper methods
         * to do some of the work (e.g., to fill in the offsets array
         * with the appropriate offsets).
         */
        
        int to_be_swap = 0;
   
        Column key_column = table.primaryKeyColumn();//key column
        
        if(key_column!= null){
            int pk_indx = key_column.getIndex();//index of primary key in
            //System.out.println(pk_indx);

            offsets[0] = 2 * offsets.length;
            //System.out.println(2 * offsets.length);//test

            for(int i=0; i< columnVals.length; i++){
                if(i!= pk_indx){
                    Column col_val = table.getColumn(i);
                    if(columnVals[i] != null){
                        if(col_val.getType()==(Column.VARCHAR)){
                            offsets[i+1] = offsets[i]+ ((String)columnVals[i]).length();
                            
                            //System.out.println(offsets[i]);//test
                            //System.out.println(offsets[i+1]);//test


                        }else if(col_val.getType()==(Column.CHAR)|| col_val.getType()==(Column.INTEGER)||col_val.getType()==(Column.REAL)){
                            offsets[i+1] = offsets[i]+ col_val.getLength();//? class java.lang.Integer cannot be cast to class java.lang.String 
                           
                        }   
                    }else if(columnVals[i] == null){
                        to_be_swap = offsets[i];
                        offsets[i+1] = to_be_swap;
                        offsets[i]=-1;
                        //System.out.println(offsets[i]);//test
                        //System.out.println(offsets[i+1]);//test
                        
                    }
                }else if(i==pk_indx){
                    to_be_swap = offsets[i];
                    offsets[i+1] = to_be_swap;
                    offsets[i]=-2;
                    //System.out.println(offsets[0]);//test
                    //System.out.println(offsets[1]);
                }
            }
            //keybuffer
            if(key_column.getType()==Column.CHAR || key_column.getType()==(Column.VARCHAR)) {
                keyBuffer.writeBytes((String)columnVals[pk_indx]);
            }else if(key_column.getType()==Column.INTEGER){
                //String name =  key_column.getName();
                keyBuffer.writeInt(((Integer)columnVals[pk_indx]).intValue());//?? 

            }else if(key_column.getType()==Column.REAL){
                keyBuffer.writeDouble(((Double)columnVals[pk_indx]).doubleValue());//??
            }


            //valuebuffer
            for(int i = 0; i < offsets.length; i++){
                valueBuffer.writeShort(offsets[i]);
            }
            for(int i=0;i<columnVals.length;i++){
                if(i!=(pk_indx)){
                    Column col_val = table.getColumn(i);
                    if(columnVals[i] != null){
                        if(col_val.getType()==(Column.CHAR)|| col_val.getType()==(Column.VARCHAR)) {
                            valueBuffer.writeBytes((String)columnVals[i]);
                        }else if(col_val.getType()==(Column.INTEGER)){
                            valueBuffer.writeInt(((Integer)columnVals[i]).intValue());
                            
                        }else if(col_val.getType()==(Column.REAL)){
                           
                            valueBuffer.writeDouble(((Double)columnVals[i]).doubleValue());
                        }

                    }

                }
            }
        
        }
    }
        
    /**
     * Returns the RowOutput used for the key portion of the marshalled row.
     *
     * @return  the key's RowOutput
     */
    public RowOutput getKeyBuffer() {
        return this.keyBuffer;
    }
    
    /**
     * Returns the RowOutput used for the value portion of the marshalled row.
     *
     * @return  the value's RowOutput
     */
    public RowOutput getValueBuffer() {
        return this.valueBuffer;
    }
    
    /**
     * Returns a String representation of this InsertRow object. 
     *
     * @return  a String for this InsertRow
     */
    public String toString() {
        return "offsets: " + Arrays.toString(this.offsets)
             + "\nkey buffer: " + this.keyBuffer
             + "\nvalue buffer: " + this.valueBuffer;
    }
}
