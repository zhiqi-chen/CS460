/*
 * Problem6.java
 * 
 * CS 460: Problem Set 4
 */

import java.io.IOException;
import java.util.*;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;

import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;

public class Problem6 {
    public static class MyMapper
      extends Mapper<Object, Text, Text, Text> 
    {
        public void map(Object key, Text value, Context context) 
            throws IOException, InterruptedException 
        {
            // convert the Text object for the value to a String.
            String line = value.toString();

            // split the line on the semicolon to get the friend list if includes one
            String[] friend_list = line.split(";");
            String[] fields = line.split(",");
            String ID = fields[0];
            // if length larger than one, which means include a list of friends
            if (friend_list.length > 1) {
                // split the friend list on the commas to get an array containing the inividual friend
                String[] friends = friend_list[1].split(",");
                context.write(new Text("friends"), new Text(ID + "," + friends.length));
            }
            // else, not including a list of friends
            else {
                context.write(new Text("friends"), new Text(ID + ",0"));
            }

        }
    }

    public static class MyReducer
      extends Reducer<Text, Text, Text, IntWritable> 
    {
        public void reduce(Text key, Iterable<Text> values, 
                           Context context) 
          throws IOException, InterruptedException 
        {
            int max = 0;
            String key_max = "";
            for (Text val : values) {
                String v = val.toString();
                int num_friends = Integer.parseInt(v.split(",")[1]);
                if (num_friends > max) {
                    max = num_friends;
                    key_max = v.split(",")[0];
                }
            }
            context.write(new Text(key_max), new IntWritable(max));
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "problem 6");
        job.setJarByClass(Problem6.class);

        // Specifies the names of the first job's mapper and reducer classes.
        job.setMapperClass(MyMapper.class);
        job.setReducerClass(MyReducer.class);

        // Sets the types for the keys and values output by the first reducer.
        /* CHANGE THE CLASS NAMES AS NEEDED IN THESE TWO METHOD CALLS */
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        // Sets the types for the keys and values output by the first mapper.
        /* CHANGE THE CLASS NAMES AS NEEDED IN THESE TWO METHOD CALLS */
        job.setOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        // Configure the type and location of the data being processed.
        job.setInputFormatClass(TextInputFormat.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));

        // Specify where the results should be stored.
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.waitForCompletion(true);
    }
}
