package itemSetMining;

import java.io.IOException;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

public class ItemSetMapper extends MapReduceBase implements
		Mapper<Text, Text, Text, IntWritable> {

	private final static IntWritable one = new IntWritable(1);
	private Text outKey = new Text();

	public void map(Text key, Text value,
			OutputCollector<Text, IntWritable> output, Reporter reporter)
			throws IOException {

		System.out.println("key : " + key + "\nValue : " + value);

		String[] items = value.toString().split(",");

		System.out.println("split[0]" + items[0]);
		int length = items.length;

		// Getting the Triplets
		for (int i = 0; i < length - 2; i++) {
			for (int j = i + 1; j < length - 1; j++) {
				for (int k = j + 1; k < length; k++) {
					outKey.set(items[i] + "," + items[j]);
					output.collect(outKey, one);
				}
			}
		}
		
		// Getting the Doublets
		for (int i = 0; i < length - 1; i++) {
			for (int j = i + 1; j < length; j++) {
				outKey.set(items[i] + "," + items[j]);
				output.collect(outKey, one);
			}
		}

		// Getting the Singlets
		for (int i = 0; i < length; i++) {
			outKey.set(items[i]);
			output.collect(outKey, one);
		}

	}
}
