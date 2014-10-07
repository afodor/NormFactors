package sim;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
		
		int sampleID = wrapper.getSampleIdWithMostCounts();
		int numCounts = wrapper.getCountsForSample(sampleID);
		System.out.println(numCounts);
		List<Integer> list = wrapper.getSamplingList(sampleID);
		writeResampledFile(wrapper, list, sampleID, numCounts);
		
	}
	
	private static void writeResampledFile(OtuWrapper wrapper, 
			List<Integer> resampledList, int maxIndex, int maxDepth) throws Exception
			{
				File outFile = 
						new File(
								ConfigReader.getBigDataScalingFactorsDir() 
								+ File.separator + "rna" + File.separator + 
								"rnaResampled.txt");

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
