package taqDBMerger;



public class Test_TickerMapper extends junit.framework.TestCase{
//	public void test_filename(){
//		String dataSetPath = "/Users/Kingther/Documents/TAQDATA/sampleTAQ/trades";
//	    File path = new File(dataSetPath);
//	    File[] files = path.listFiles();
//	    for(File file :files){
//	    	System.out.println(file.getName());
//	    }
//	}
	public void test_getDates(){
		String dataSetPath = "/Users/Kingther/Documents/TAQDATA/sampleTAQ/trades";
		TickerMapper tm = new TickerMapper(dataSetPath);
		System.out.println(tm.getDates());
		assertEquals("[20070620, 20070621]",tm.getDates().toString());
	}
	
	public void test_generateTickerMap(){
		String dataSetPath = "/Users/Kingther/Documents/TAQDATA/sampleTAQ/trades";
		TickerMapper tm = new TickerMapper(dataSetPath);
		assertEquals(tm.generateTickerMapForDate("20070620").toString(),"{IBM=0, MSFT=1}");
	}
	public void test_generateTickerMap2(){
		String dataSetPath = "/Users/Kingther/Documents/TAQDATA/sampleTAQ/trades";
		TickerMapper tm = new TickerMapper(dataSetPath);
		assertEquals(tm.generateTickerMapForAll().toString(),"{IBM=0, MSFT=1}");
	}
}
