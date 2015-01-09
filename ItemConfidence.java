/*  
      @author Saurabh Nailwal      
*/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;

public class ItemConfidence {

	public static void main(String[] args) throws IOException {

		// Read the output file and store in HashMap
		BufferedReader reader = null;
		HashMap<String, Integer> singlet = null;
		HashMap<String, Integer> doublet = null;
		HashMap<String, Integer> triplet = null;

		// Give the path to need
		String path = "/home/saurabh/workspace/Asg2/Output/";

		// Finding all the files at the path starting with 'part' file name
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			File file = listOfFiles[i];

			if (file.isFile() && file.getName().startsWith("part")) {

				System.out.println(" Abs: " + file.getAbsolutePath() + " Can: "
						+ file.getCanonicalPath());
				try {

					reader = new BufferedReader(new FileReader(
							file.getAbsolutePath()));

					String line = "";
					singlet = new HashMap<String, Integer>();
					doublet = new HashMap<String, Integer>();
					triplet = new HashMap<String, Integer>();
					String[] parts = null, split = null;

					while ((line = reader.readLine()) != null) {

						split = line.split("\\s");
						parts = split[0].split(",");

						String items = "";

						for (i = 0; i < parts.length; i++) {
							items += parts[i];
						}

						if (i == 1) {
							singlet.put(items, Integer.parseInt(split[1]));

						} else if (i == 2) {
							doublet.put(items, Integer.parseInt(split[1]));

						} else if (i == 3) {
							triplet.put(items, Integer.parseInt(split[1]));
						}

					}

				} catch (IOException e) {
					e.printStackTrace();
				} finally {

					if (reader != null) {
						try {
							reader.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}

			}
		}

		System.out.println("Singlet:" + singlet.toString() + " "
				+ singlet.get("1"));
		System.out.println("Doublet:" + doublet.toString() + " "
				+ doublet.size() + " " + doublet.keySet());
		System.out.println("Triplet:" + triplet.toString() + " "
				+ triplet.get("125"));

		TreeMap<String, Double> confidenceTree = new TreeMap<String, Double>();
		String key1, key2, key3;
		float value, value1, value2;
		double confid;

		// Iterating through each element of doublet and finding confidence
		Iterator iterD = doublet.entrySet().iterator();

		while (iterD.hasNext()) {

			HashMap.Entry pairs = (HashMap.Entry) iterD.next();
			key1 = "" + pairs.getKey().toString().charAt(0);
			key2 = "" + pairs.getKey().toString().charAt(1);
			
			System.out.println("key1: " + key1 + " key2: " + key2);
			value = Integer.parseInt(pairs.getValue().toString());
			
			System.out.println("pair value :" + value);
			value1 = singlet.get(key1);
			value2 = singlet.get(key2);

			System.out.println("value1: " + value1);

			confid = value / value1;

			System.out.println("confid: " + confid);

			confidenceTree.put(key1 + "->" + key2, confid);

			confid = value / value2;

			System.out.println("confid: " + confid);

			confidenceTree.put(key2 + "->" + key1, confid);

		}

		// Iterating through each element of triplet and finding confidence
		Iterator iterT = triplet.entrySet().iterator();

		while (iterT.hasNext()) {

			HashMap.Entry pairs = (HashMap.Entry) iterT.next();

			key1 = "" + pairs.getKey().toString().charAt(0);
			key2 = "" + pairs.getKey().toString().charAt(1);
			key3 = "" + pairs.getKey().toString().charAt(2);

			value = Integer.parseInt(pairs.getValue().toString());

			if (doublet.get(key1 + key2) != null) {
				value2 = doublet.get(key1 + key2);

				confid = value / value2;

				confidenceTree.put(key1 + key2 + "->" + key3, confid);
			}

			if (doublet.get(key2 + key3) != null) {
				value2 = doublet.get(key2 + key3);

				confid = value / value2;

				confidenceTree.put(key2 + key3 + "->" + key1, confid);
			}

			if (doublet.get(key1 + key3) != null) {
				value2 = doublet.get(key1 + key3);

				confid = value / value2;

				confidenceTree.put(key1 + key3 + "->" + key2, confid);
			}
			
		}

		System.out.println("Final :" + confidenceTree.toString());

	}

}
