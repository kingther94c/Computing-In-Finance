package taqDBMerger;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import taqDBReaders.ReadGZippedTAQTradesFile;


public class TradesRewriter {
	protected ReadGZippedTAQTradesFile _taq;
	private short _stockId;
	protected String _outputFilePath;
	protected int _nRecs;
	protected String BASIC_OUTPUT_PATH;
	
	/**
	 * Construct a Trades Rewriter
	 *  rewrite to a .dat file record in this format: 
	 * 
	 *  long msFromTheEpoch
	 *  short stockId
	 *  int size 
	 *  float price
	 *  
	 * inputFilePath example: "/Users/Kingther/Documents/TAQDATA/sampleTAQ/trades/20070620/IBM_trades.binRT"
	 * outputFilePath : "/Users/Kingther/Documents/TAQDATA/sampleTAQ/output/20070620/<rd>_1.dat"	
	 * @param inputFilePath
	 * @param stockId
	 * @param folderName
	 * @throws IOException
	 */
	public TradesRewriter( String inputFilePath, short stockId,String outputPath ) throws IOException {
		BASIC_OUTPUT_PATH = outputPath;
		//Initialize
		_stockId = stockId;
		_taq = new ReadGZippedTAQTradesFile(inputFilePath);
		_nRecs = _taq.getNRecs();
		
		//Get the directory for outputFilePath based on the inputFilePath
		String[] s = inputFilePath.split("/");		
		String Date = s[s.length-2];
		String dir = BASIC_OUTPUT_PATH + "/" + Date;
		System.out.println(dir);
		
		//Make a directory if not existed
		File thisDir = new File(dir);
		if (!thisDir.exists()) {
		    if (!thisDir.mkdirs()) {
		    	 throw new IOException("Unable to create " + dir);
		    }
		}
		
		//Get the outputFilePath
		//The name of output file is set to be <currentTimeMillis>_1.dat
		_outputFilePath=dir+"/"+String.format("%d_1.dat", System.currentTimeMillis());     
		System.out.println(_outputFilePath);
	}
	public TradesRewriter( String inputFilePath, short stockId ) throws IOException{
		this(inputFilePath, stockId,"/Users/Kingther/Documents/TAQDATA/sampleTAQ/output");
	}

	/**
	 * Write records into outputFile
	 * @throws IOException
	 */
	public void rewrite() throws IOException {
		DataOutputStream outputFile = new DataOutputStream(new FileOutputStream(_outputFilePath));
		for ( int i=0; i<_nRecs; i++){
			outputFile.writeLong(_taq.getSecsFromEpoch()*1000l+ _taq.getMillisecondsFromMidnight(i));
			outputFile.writeShort(_stockId);
			outputFile.writeInt(_taq.getSize(i));			
			outputFile.writeFloat(_taq.getPrice(i));			
		}
		outputFile.flush();
		outputFile.close();		
	}

}
