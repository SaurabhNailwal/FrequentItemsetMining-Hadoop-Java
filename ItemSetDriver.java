/*  
      @author Saurabh Nailwal      
*/

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class ItemSetDriver extends Configured implements Tool {

	public int run(String[] args) throws Exception {
		// creating a JobConf object and assigning a job name for identification
		// purposes
		JobConf job = new JobConf(getConf(), ItemSetDriver.class);
		job.setJobName("ItemsetMiningJob");

		// Setting configuration object with the Data Type of output Key and
		// Value
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		// Providing the mapper and reducer class names
		job.setJarByClass(ItemSetDriver.class);

		job.setMapperClass(ItemSetMapper.class);

		job.setNumReduceTasks(2);
		job.setPartitionerClass(ItemSetPartitioner.class);

		job.setReducerClass(ItemSetReducer.class);
		
		
	    //Reading the input file as key value pair
		job.setInputFormat(KeyValueTextInputFormat.class);
		
		job.set("mapreduce.input.keyvaluelinerecordreader.key.value.separator","\\s");
		
		// input and output directory
		KeyValueTextInputFormat.addInputPath(job, new Path("Input"));// new
																     // Path(args[0]));
																
		FileOutputFormat.setOutputPath(job, new Path("Output"));//new
																// Path(args[1]));
		JobClient.runJob(job);

		return 0;

	}

	public static void main(String[] args) throws Exception {

		int res = ToolRunner
				.run(new Configuration(), new ItemSetDriver(), args);
		System.exit(res);

	}

}
