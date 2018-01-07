package taqDBMerger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Merge {
	private String _dataSetPath;
	private String _inputFolderName;
	private String _outputFolderName;
	
	public Merge(String dataSetPath, String inputFolderName, String outputFolderName){
		_dataSetPath = dataSetPath;
		_inputFolderName = inputFolderName;
		_outputFolderName = outputFolderName;		
	}
	
	public Merge(){
		_dataSetPath = "/Users/Kingther/Documents/TAQDATA/sampleTAQ";
		_inputFolderName = "trades";
		_outputFolderName = "output";		
	}
	
    public void merge() throws IOException {
    	//set working path
    	String inputPath = _dataSetPath + "/" + _inputFolderName;
    	String outputPath = _dataSetPath + "/" + _outputFolderName; 
    	
    	//Create TickerMapper
        TickerMapper tickerMapper = new TickerMapper(inputPath);
        HashMap<String, Short> tickerMap = tickerMapper.generateTickerMapForAll();
        ArrayList<String> dates = tickerMapper.getDates();
        
        //Rewrite
        for(String date : dates){
        	String inputFilePath = inputPath + "/" + date;
        	File[] files = (new File(inputFilePath)).listFiles();
        	for(File file : files){
        		String ticker = file.getName().split("_")[0];
        		short stockId = tickerMap.get(ticker);
        		TradesRewriter tradesRewriter = new TradesRewriter( file.getAbsolutePath(), stockId,outputPath);
        		tradesRewriter.rewrite();
        	}
        }
        
        //Merge
        for(String date : dates){
        	String mergePath = outputPath + "/" + date;
        	TradesMerger tradesMerger = new TradesMerger(2,mergePath);
        	tradesMerger.mergeAll();
        }        
    	
    	
    }
}
