package sim;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import parsers.OtuWrapper;
import utils.ConfigReader;

public class WriteRNA
{
	public static void main(String[] args) throws Exception
	{
		File outFile = new File(ConfigReader.getBigDataScalingFactorsDir() + File.separator + 
				"rna" + File.separator + "pivotedGenesAsColumns.txt");
		
		OtuWrapper.transpose(ConfigReader.getBigDataScalingFactorsDir() + File.separator+ 
									"rna" + File.separator  + "pivotedSamplesAsColumns.txt", outFile.getAbsolutePath(), false);
		
		
		OtuWrapper wrapper = new OtuWrapper(outFile);
		
		
		HashMap<String , Double> map = wrapper.getTaxaListSortedByNumberOfCounts();
		
		for( String s : map.keySet())
			System.out.println(s + " " + map.get(s));
		
		HashSet<String> excludedTaxa= new HashSet<String>();

		List<String> keys = new ArrayList<String>(map.keySet());
		//excludedTaxa.add(keys.get(0)); excludedTaxa.add(keys.get(1));excludedTaxa.add(keys.get(2));
		
		for( int x=53; x < keys.size(); x++)
			excludedTaxa.add(keys.get(x));
		
		wrapper = new OtuWrapper(outFile, new HashSet<String>(), excludedTaxa);
		wrapper.writeUnnormalizedDataToFile(new File(ConfigReader.getBigDataScalingFactorsDir() + File.separator + 
				"rna" + File.separator + "pivotedGenesAsColumnsNonSparse.txt"));
		
		int sampleID = wrapper.getSampleIdWithMostCounts();
		int numCounts = wrapper.getCountsForSample(sampleID);
		
		System.out.println(numCounts + " " + wrapper.getFractionZeroForSample(sampleID) + " " + wrapper.getOtuNames().size());
		
		
		List<Integer> list = wrapper.getSamplingList(sampleID);
		writeResampledFile(wrapper, list, sampleID, numCounts);
		
	}
	
	private static void writeResampledFile(OtuWrapper wrapper, 
			List<Integer> resampledList, int maxIndex, int maxDepth) throws Exception
			{
				File outFile = 
						new File(
								ConfigReader.getBigDataScalingFactorsDir() + File.separator + 
								"risk" + File.separator + "dirk" + File.separator +  "resample" +File.separator +
								"rnaResampledNonSparse.txt");

				System.out.println(outFile.getAbsolutePath());

				BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));

				writer.write( "sample" );

				for( int x=0; x < wrapper.getOtuNames().size(); x++)
					writer.write("\t" + wrapper.getOtuNames().get(x));

				writer.write("\n");

				for( int depth=maxDepth; depth >=5000; depth = depth - 50000 )
				{
					System.out.println( " depth= " + depth);
					writer.write("sample_" + depth);
					int[] counts = WriteResampled.resample(wrapper, resampledList,  depth);
	
					for( int y=0; y < counts.length; y++)
						writer.write("\t" + counts[y]);
	
					writer.write("\n");
					writer.flush();
				}
				
			}
	}
