package taqDBMerger;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class TickerMapper {
	/**
	 * Map tickers to two byte binary codes
	 * For each date sub-directory in the dates sub-directory of the trades sub-directory
	 * Find all unique tickers.
	 * Make a map that converts these tickers to some integer code that only takes up two bytes.
	 */
	//  dataSetPath = "/Users/Kingther/Documents/TAQDATA/sampleTAQ/trades"
	String _dataSetPath;
	public TickerMapper(String dataSetPath ){
		_dataSetPath = dataSetPath;
	}
	/**
	 * generate Ticker Map For a Date
	 * @param date
	 * @return TickerMap a HashMap<String, Short>
	 */
	public HashMap<String, Short> generateTickerMapForDate(String date){
		short stockId = 0;
		HashMap<String, Short> tickerMap = new HashMap<String, Short>();
		String filePath = _dataSetPath + "/" + date;
	    File path = new File(filePath);
	    File[] files = path.listFiles();
	    for (File file : files){
	    	if (file.getName().endsWith(".binRT")){
		    	String ticker = file.getName().split("_")[0];
		    	if(!tickerMap.containsKey(ticker)){
		    		tickerMap.put(ticker, stockId);
		    		stockId++;
		    	}
	    	}
	    }
		return tickerMap;
	}
	/**
	 * update Ticker Map For a Date
	 * @param date
	 */
	public void updateTickerMapForDate(String date, HashMap<String, Short> tickerMap){
		short stockId = (short) tickerMap.size();
		String filePath = _dataSetPath + "/" + date;
	    File path = new File(filePath);
	    File[] files = path.listFiles();
	    for (File file : files){
	    	if (file.getName().endsWith(".binRT")){
		    	String ticker = file.getName().split("_")[0];
		    	if(!tickerMap.containsKey(ticker)){
		    		tickerMap.put(ticker, stockId);
		    		stockId++;
		    	}
	    	}

	    }
	}	
	/**
	 * get all the Dates in the filepath.
	 * @return a ArrayList<String> with entries of Date
	 */
	public ArrayList<String> getDates(){
		String filePath = _dataSetPath;
	    File path = new File(filePath);
	    File[] files = path.listFiles();
	    ArrayList<String> dates = new ArrayList<String>(files.length);
	    for (File file : files){
	    	System.out.println(file.getName());
	    	System.out.println(file.getName().length());
	    	if(file.getName().length()==8){
	    		try{
	    			Integer.parseInt(file.getName());
	    			dates.add(file.getName());
	    		}
	    		catch(NumberFormatException e){continue;}
	    	}
	    }
	    return dates;
	}
	/**
	 * generate Ticker Map For all the date folders
	 * @param date
	 * @return TickerMap a HashMap<String, Short>
	 */
	public HashMap<String, Short> generateTickerMapForAll(){
		ArrayList<String> dates =  getDates();
		int n = dates.size();
		if(n==0)
			return null;
		HashMap<String, Short> tickerMap = generateTickerMapForDate(dates.get(0));
		for(int i =1; i < n; i++){
			updateTickerMapForDate(dates.get(i),tickerMap);
		}
		return tickerMap;
		
	}
	
}
