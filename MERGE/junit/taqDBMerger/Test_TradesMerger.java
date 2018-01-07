package taqDBMerger;

import java.io.File;
import java.io.IOException;

public class Test_TradesMerger extends junit.framework.TestCase{
	String TESTMERGE_PATH="/Users/Kingther/Documents/TAQDATA/sampleTAQ/testMerger/20070620";	
	String outputfilePath = "/Users/Kingther/Documents/TAQDATA/sampleTAQ/testMerger";
	public void test_listAllFile(){

	    File path = new File(TESTMERGE_PATH);
	    File[] files = path.listFiles();
	    for (File file : files){
	    	System.out.println(file.getName().length());
	    	System.out.println(file.getName().substring(file.getName().length()-6));
	    	System.out.println(file.getName());
	    	System.out.println(file.getPath());	    	
	    }
	}
	public void test_TradesMerger(){
		TradesMerger tm = new TradesMerger(2,TESTMERGE_PATH);
		tm.getClass();
	}
	public void test_merge() throws IOException{
		String inputFilePath = "/Users/Kingther/Documents/TAQDATA/sampleTAQ/trades/20070620/IBM_trades.binRT";
		short stockId = 1;
		for(int i = 0; i<5; i++){
			TradesRewriter trw = new TradesRewriter( inputFilePath, stockId,outputfilePath) ;
			trw.rewrite();
		}
		TradesMerger tm = new TradesMerger(2,TESTMERGE_PATH);
		tm.merge(1);
		tm.merge(2);
		tm.merge(3);
		String filePath = "/Users/Kingther/Documents/TAQDATA/sampleTAQ/testMerger/20070620/0_4.dat";
		TradesDBReader tradesDBReader = new TradesDBReader(filePath);
		tradesDBReader.readChunk(1182346241000L);
		TradesRecord rec;
		while((rec = tradesDBReader.getRecord()) != null)
			System.out.println(rec);
		tm.clear(TESTMERGE_PATH, 4);
	}
	public void test_mergeAll() throws IOException{
		String inputFilePath = "/Users/Kingther/Documents/TAQDATA/sampleTAQ/trades/20070620/IBM_trades.binRT";
		
		short stockId = 1;
		for(int i = 0; i<5; i++){
			TradesRewriter trw = new TradesRewriter( inputFilePath, stockId,outputfilePath) ;
			trw.rewrite();
		}
		TradesMerger tm = new TradesMerger(2,TESTMERGE_PATH);
		tm.mergeAll();
		String filePath = "/Users/Kingther/Documents/TAQDATA/sampleTAQ/testMerger/20070620/0_4.dat";
		TradesDBReader tradesDBReader = new TradesDBReader(filePath);
		tradesDBReader.readChunk(1182346241000L);
		TradesRecord rec;
		while((rec = tradesDBReader.getRecord()) != null)
			System.out.println(rec);
		tm.clear(TESTMERGE_PATH, 4);
	}
	
	

}
