/*  
      @author Saurabh Nailwal      
*/

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

public class ItemSetPartitioner extends MapReduceBase implements Partitioner<Text,IntWritable>{

	@Override
	public int getPartition(Text key, IntWritable value, int numReduceTasks) {
		// TODO Auto-generated method stub
		
		int length = key.getLength();	

		//This is done to avoid performing mod with 0
		if(numReduceTasks == 0)
			return 0;

		//If a single value is passed then we send to first partition
		if(length < 2){
			return 0;
		}
		else 		     
		{
			//If a single value is not passed then we send to second partition
			return 1 % numReduceTasks;
		}
	}

	
}
