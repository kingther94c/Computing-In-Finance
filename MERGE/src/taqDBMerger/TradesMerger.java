package taqDBMerger;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import dbReaderFramework.DBManager;
import dbReaderFramework.I_DBProcessor;
import dbReaderFramework.I_DBReader;

public class TradesMerger {

	protected String _mergePath; //eg. "/Users/Kingther/Documents/TAQDATA/sampleTAQ/output/20070620";	
	private int _nDBReaders;

	
	/**
	 * Construct a Trades Merger
	 * The .dat file record to be merged should be in this format: 
	 * 
	 *  long msFromTheEpochQueue
	 *  short stockId
	 *  int size 
	 *  float price
	 * inputFilePath example: "/Users/Kingther/Documents/TAQDATA/sampleTAQ/trades/20070620/IBM_trades.binRT"
	 * outputFilePath : "/Users/Kingther/Documents/TAQDATA/sampleTAQ/output/20070620/<rd>_1.dat"	
	 * @param nDBReaders
	 * @param mergePath
	 */
	public TradesMerger(int nDBReaders, String mergePath  ) {
		//Initialize
		_nDBReaders = nDBReaders;
		_mergePath = mergePath;
	}

	/**
	 * Merge all the <>_(round).dat file
	 * @throws FileNotFoundException 
	 * @throws Exception 
	 */
	public int merge(int round)  {
		
		// Get the LinkedList of file path end with _i.dat
	    File path = new File(_mergePath);
	    File[] files = path.listFiles();
	    LinkedList<String> filePathList = new LinkedList<String>();
	    for (File file : files){
	    	String[] filename = file.getName().split("_");
	    	if(filename[filename.length-1].equals(Integer.toString(round)+".dat"))
	    		filePathList.add(file.getPath());
	    }
	    int nLeftFiles = 0;

	    int uniqueNum = 0;
		try {
		    while(!filePathList.isEmpty()){
	    		// Define a compressed output stream for merged data
		    	//filePathList.getFirst();
	    		String outputFilePathName = _mergePath + "/" + Integer.toString(uniqueNum) + "_" +Integer.toString(round+1)+".dat";
	    		uniqueNum++;
	    		DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(outputFilePathName));
	    		
		    	// Create readers
				LinkedList<I_DBReader> readers = new LinkedList<I_DBReader>();	    	
				System.out.println(_nDBReaders);
		    	for(int j = 0 ; j < _nDBReaders; j++){
		    		try{
		    			readers.add(new TradesDBReader(filePathList.removeFirst()));
		    		}catch (NoSuchElementException e){
		    			
		    		}
		    	}
	    		// Instantiate merge processor, telling it where to write merged output of two quotes files
	    		TradesDBProcessor mergeProcessor = new TradesDBProcessor(dataOutputStream) ;
	    		
	    		// Create a list of processors, which, in this case, will
	    		// contain only one processor, the one we created above
	    		LinkedList<I_DBProcessor> processors = new LinkedList<I_DBProcessor>();
	    		processors.add( mergeProcessor );    		
	    		
	    		// Create a merge clock
	    		TradesMergeClock clock = new TradesMergeClock( readers, processors );
	    		
	    		// Hand all of the readers, processors, and clock to the DBManager
	    		DBManager dbm = new DBManager( readers, processors, clock );
	    	
	    		// Launch the DBManager
	    		try {
					dbm.launch();
					nLeftFiles ++;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    
		    }
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		clear(_mergePath, round); 
		return nLeftFiles;
	}
	public void mergeAll(){
		int i = 1;
		while(merge(i++)>1);
	}
	
	
	
	/** Erase all  .dat files from previous round
	 * 
	 * @param currentPath
	 * @param round
	 */
	public void clear(String currentPath, int round) {
		File dir = new File( currentPath );
		File[] fileList = dir.listFiles();
		for (File file : fileList) {
			if(file.getName().endsWith(String.format("_%d.dat", round))) 
				file.delete();		
		}	
	}
}

