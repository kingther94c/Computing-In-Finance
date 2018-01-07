package panic;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import utils.Pair;

public class Test_QuotesReader extends junit.framework.TestCase{
	public void test_readHeader() throws IOException, ParseException{
		QuotesReader reader = new QuotesReader("/Users/kingther/Documents/workspace/Panic/testData.csv");
		reader.readHeader();
		String[] tikers = reader.getTickers();
		assertEquals(tikers[0],"FLWS");
		assertEquals(tikers[3],"FOX");		
		reader.close();
	}
	
	public void test_skipToDate_normal() throws Exception{
		QuotesReader reader = new QuotesReader("/Users/kingther/Documents/workspace/Panic/testData.csv");
		Date startDate = reader._dateFormat.parse("20130821");
		Date endDate = reader._dateFormat.parse("20130823");
		
		reader.skipToDate(startDate);
		//System.out.println(reader);
		assertEquals(reader.getQuotesList().size(),0);		
		reader.readData(endDate);		
		assertEquals(reader.getQuotesList().size(),3);	
		//System.out.println(reader);
		reader.close();
	}	
	
	public void test_skipToDate_reOpen() throws Exception{
		QuotesReader reader = new QuotesReader("/Users/kingther/Documents/workspace/Panic/testData.csv");
		Date startDate = reader._dateFormat.parse("20130821");
		Date endDate = reader._dateFormat.parse("20130823");
		
		reader.readData(endDate);		
		assertEquals(reader.getQuotesList().size(),4);	
		reader.skipToDate(startDate);
		assertEquals(reader.getQuotesList().size(),0);		

		//System.out.println(reader);
		reader.close();
	}	

	public void test_readData1() throws Exception {
		QuotesReader reader = new QuotesReader("/Users/kingther/Documents/workspace/Panic/testData.csv");
		Date startDate = reader._dateFormat.parse("20130821");
		Date endDate = reader._dateFormat.parse("20130823");

		//System.out.println(endDate);
		reader.readData(startDate, endDate);
		assertEquals(3,reader.getQuotesList().size());
		
		endDate = reader._dateFormat.parse("20130830");
		reader.readData(endDate);		
		assertEquals(8,reader.getQuotesList().size());		
	}
	
	public void test_readData2() throws Exception {
		QuotesReader reader = new QuotesReader("/Users/kingther/Documents/workspace/Panic/testData.csv");
		
		Date endDate = reader._dateFormat.parse("20130830");
		reader.readData(endDate);		
		assertEquals(9,reader.getQuotesList().size());		
	}	
	
	public void test_readData3() throws Exception {
		QuotesReader reader = new QuotesReader("/Users/kingther/Documents/workspace/Panic/testData.csv");
		
		Date endDate = reader._dateFormat.parse("20130830");
		reader.readData(endDate);		
		assertEquals(9,reader.getQuotesList().size());		
		reader.readData(endDate);	
		assertEquals(9,reader.getQuotesList().size());	
	}	
	
	public void test_readData4() throws Exception {
		QuotesReader reader = new QuotesReader("/Users/kingther/Documents/workspace/Panic/testData.csv");
		
		Date endDate = reader._dateFormat.parse("20130830");
		reader.readData(endDate);		
		reader.readData(endDate);	
		reader.readData(endDate);	
		assertEquals(9,reader.getQuotesList().size());		
		reader.readData(endDate);	
		assertEquals(9,reader.getQuotesList().size());	
	}	
	
	public void test_computeUpRatiosAndNumStocks() throws Exception {
		QuotesReader reader = new QuotesReader("/Users/kingther/Documents/workspace/Panic/testUpRatio.csv");
		Date startDate = reader._dateFormat.parse("20130820");
		Date endDate = reader._dateFormat.parse("20130827");
		reader.readData(startDate, endDate);
		Pair<List<Double>, List<Integer>> p = reader.computeUpRatiosAndNumStocks();
		assertEquals(p.getLeft().toString(),"[0.5, 0.6666666666666666, 0.3333333333333333, 0.25]");
		assertEquals(p.getRight().toString(),"[4, 3, 3, 4]");
		System.out.println(p);
		
	}
}
