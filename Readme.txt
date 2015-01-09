

Frequent Itemset Mining Readme 
------------------------------

The MapReduce program finds out singleton and doubleton relations between products in transactions which will help us to find the confidence of giving prediction of buying a product a given b and c are purchased.

To find the confidence, we need to feed the output of mapreduce to the confidence generator code.

To run the code for calculating the confidence 'ItemConfidence.java' follow below steps:


[1]Give the path for the output folder where you have kept the output of the mapreduce.

(Note:- The files of the output should be named as "part-")


[2] Run the code and the confidence for various items sets will be stored in TreeMap variable 'confidenceTree'.

