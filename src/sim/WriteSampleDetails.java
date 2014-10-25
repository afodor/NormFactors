package sim;

import java.io.File;

import parsers.OtuWrapper;
import utils.ConfigReader;

public class WriteSampleDetails
{
	public static void main(String[] args) throws Exception
	{
		File inFile = 
				new File(
				ConfigReader.getBigDataScalingFactorsDir() 
				+ File.separator + "risk" + 
				File.separator + "dirk" 
				+ File.separator +
				"may2013_refOTU_Table-subsetTaxaAsColumnsstoolOnly.filtered.txt");
		
		System.out.println("Reading " + inFile);
		OtuWrapper wrapper = new OtuWrapper( inFile);
		
		for(int x=0; x < wrapper.getSampleNames().size(); x++)
			System.out.println(wrapper.getCountsForSample(x));
		
		System.out.println( wrapper.getOtuNames().size() );
		System.out.println( wrapper.getSampleNames().size() );
	}
}
