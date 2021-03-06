package sim;

import java.io.File;

import parsers.OtuWrapper;
import utils.ConfigReader;

public class WriteNormalized
{
	public static void main(String[] args) throws Exception
	{
		writeOne("resampled_.txt", -1);
		//writeOne( "rnaResampledNonSparse.txt", -1);
		
		//writeOne("rnaResampled.txt",-1);
		
		/*
		
		writeOne("CF_LONG.txt", -1);
		writeOne("may2013_refOTU_Table-subsetTaxaAsColumnsstoolOnly.filtered.txt", -1);
		
		/*
		writeOne("resampledcontinious_.txt", -1);
		writeOne("resampledcontinious_sameDepth.txt", 4181);
		
		writeOne("resampled_sameDepth.txt", 4181);*/
	}
	
	private static void writeOne(String rawFileName, int rarifcationDepth) throws Exception
	{
		System.out.println(rawFileName + " " + rarifcationDepth);
		
		OtuWrapper wrapper = new OtuWrapper(new File(
				ConfigReader.getBigDataScalingFactorsDir() 
				+ File.separator + "risk" 
				+ File.separator + "dirk" 
				+ File.separator + "resample" + File.separator + 
						rawFileName	));
		
		
		wrapper.writeNormalizedDataToFile(new File(ConfigReader.getBigDataScalingFactorsDir() 
				+ File.separator + "risk" 
				+ File.separator + "dirk" 
				+ File.separator + "resample" + File.separator + 
						rawFileName.replaceAll(".txt", "") + "relativeAbundance.txt"),false);
		
		wrapper.writeRarifiedSpreadhseet(new File(ConfigReader.getBigDataScalingFactorsDir() 
				+ File.separator + "risk" 
				+ File.separator + "dirk" 
				+ File.separator + "resample" + File.separator + 
				rawFileName.replaceAll(".txt", "") + "rarified.txt"),false,rarifcationDepth);
	}
}
