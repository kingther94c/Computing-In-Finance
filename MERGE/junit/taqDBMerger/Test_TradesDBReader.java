package taqDBMerger;

public class Test_TradesDBReader extends junit.framework.TestCase{
	public void test_TradesDBReader(){
		String filePath = "/Users/Kingther/Documents/TAQDATA/sampleTAQ/output/20070620/test_1.dat";
		TradesDBReader tradesDBReader = new TradesDBReader(filePath);
		tradesDBReader.getClass();
	}
	public void test_readChunk(){
		String filePath = "/Users/Kingther/Documents/TAQDATA/sampleTAQ/output/20070620/test_1.dat";
		TradesDBReader tradesDBReader = new TradesDBReader(filePath);
		//System.out.println(tradesDBReader._RecsQueue);
		assertEquals(tradesDBReader.readChunk(0L),0);
		//System.out.println(tradesDBReader._RecsQueue);
		//System.out.println(tradesDBReader.getSequenceNumber());
		assertEquals(tradesDBReader.getSequenceNumber(),0l);
		tradesDBReader.readChunk(1182346241000L);
		System.out.println(tradesDBReader._RecsQueue);
		System.out.println(tradesDBReader.getSequenceNumber());
		assertEquals(tradesDBReader.getSequenceNumber(),1182346241000L);
	}
	public void test_getRecord_last_is_extra(){
		String filePath = "/Users/Kingther/Documents/TAQDATA/sampleTAQ/output/20070620/test_1.dat";
		TradesDBReader tradesDBReader = new TradesDBReader(filePath);
		tradesDBReader.readChunk(1182346241000L);
		TradesRecord tr = tradesDBReader.getRecord();
		assertEquals(tr,new TradesRecord(1182346241000l,(short)1, 85200, 106.5f));
		tr = tradesDBReader.getRecord();
		assertEquals(tr,null);
		
	}
	public void test_getRecord_readnull_again(){
		String filePath = "/Users/Kingther/Documents/TAQDATA/sampleTAQ/output/20070620/test_1.dat";
		TradesDBReader tradesDBReader = new TradesDBReader(filePath);
		tradesDBReader.readChunk(0L);
		assertEquals(tradesDBReader.getRecord(),null);
		tradesDBReader.readChunk(0L);
		assertEquals(tradesDBReader.getRecord(),null);
		tradesDBReader.readChunk(0L);
		assertEquals(tradesDBReader.getRecord(),null);
		tradesDBReader.readChunk(1182346241000L);
		tradesDBReader.getRecord();
		tradesDBReader.readChunk(1182346241000L);
		assertEquals(tradesDBReader.getRecord(),null);
		tradesDBReader.readChunk(1182346241000L);
		assertEquals(tradesDBReader.getRecord(),null);
	}
	public void test_getRecord_read_after_getall(){
		String filePath = "/Users/Kingther/Documents/TAQDATA/sampleTAQ/output/20070620/test_1.dat";
		TradesDBReader tradesDBReader = new TradesDBReader(filePath);
		tradesDBReader.readChunk(1182346242000L);
		TradesRecord tr = tradesDBReader.getRecord();
		while((tr = tradesDBReader.getRecord() )!= null);
		assertEquals(tr,null);
		tradesDBReader.readChunk(1182346243000L);	
		System.out.println(tradesDBReader.getLastSequenceNumberRead());
		System.out.println(tradesDBReader.getSequenceNumber());
		tr = tradesDBReader.getRecord() ;
		assertEquals(tr,new TradesRecord(1182346243000l,(short)1, 200, 106.5f));
		System.out.println(tradesDBReader.getRecord());
	}
	
	public void test_getRecord_readnull_updateSecNum(){
		String filePath = "/Users/Kingther/Documents/TAQDATA/sampleTAQ/output/20070620/test_1.dat";
		TradesDBReader tradesDBReader = new TradesDBReader(filePath);
		System.out.println("test_getRecord_readnull_updateSecNum");
		tradesDBReader.readChunk(0L);
		System.out.println(tradesDBReader._RecsQueue);
		assertEquals(tradesDBReader.getSequenceNumber(),0L);
		tradesDBReader.readChunk(1000L);
		System.out.println(tradesDBReader._RecsQueue);
		assertEquals(tradesDBReader.getSequenceNumber(),1000L);
		tradesDBReader.readChunk(1000L);
		System.out.println(tradesDBReader._RecsQueue);
		assertEquals(tradesDBReader.getSequenceNumber(),1000L);
		tradesDBReader.readChunk(0L);
		System.out.println(tradesDBReader._RecsQueue);
		assertEquals(tradesDBReader.getSequenceNumber(),1000L);

	}
	
	/*some data in test_1
	1182346241000,1,85200,106.5,
	1182346242000,1,100,106.44,
	1182346242000,1,200,106.5,
	1182346242000,1,100,106.5,
	1182346242000,1,400,106.5,
	1182346242000,1,200,106.5,
	1182346242000,1,200,106.5,
	1182346242000,1,700,106.5,
	1182346242000,1,200,106.5,
	1182346242000,1,400,106.5,
	1182346242000,1,100,106.5,
	1182346242000,1,700,106.5,
	1182346242000,1,300,106.5,
	1182346242000,1,500,106.5,
	1182346242000,1,500,106.5,
	1182346242000,1,500,106.5,
	1182346242000,1,200,106.5,
	1182346242000,1,100,106.5,
	1182346242000,1,700,106.5,
	1182346242000,1,3300,106.5,
	1182346242000,1,300,106.5,
	1182346242000,1,800,106.5,
	1182346242000,1,200,106.5,
	1182346242000,1,400,106.5,
	1182346242000,1,600,106.5,
	1182346242000,1,100,106.5,
	1182346242000,1,400,106.5,
	1182346242000,1,100,106.5,
	1182346242000,1,500,106.5,
	1182346242000,1,500,106.5,
	1182346242000,1,200,106.5,
	1182346242000,1,300,106.5,
	1182346243000,1,200,106.5,
	1182346243000,1,200,106.5,
	1182346243000,1,200,106.5,
	1182346243000,1,200,106.5,
	1182346243000,1,100,106.5,
	1182346243000,1,700,106.5,
	1182346243000,1,100,106.44,
	1182346244000,1,300,106.44,
	1182346244000,1,200,106.44,
	1182346244000,1,100,106.44,
	1182346244000,1,100,106.44,
	1182346245000,1,200,106.44,
	1182346245000,1,300,106.44,
	1182346245000,1,200,106.5,
	1182346245000,1,11600,106.5,
	1182346245000,1,20100,106.5,
	1182346246000,1,3300,106.5,
	1182346248000,1,200,106.45,
	1182346249000,1,100,106.5,
	1182346249000,1,100,106.5,
	1182346249000,1,100,106.5,
	1182346249000,1,100,106.5,
	1182346249000,1,100,106.5,
	1182346249000,1,500,106.5,
	1182346249000,1,200,106.42,
	1182346249000,1,400,106.5,
	1182346249000,1,100,106.48,
	1182346250000,1,100,106.49,
	1182346250000,1,100,106.49,
	1182346250000,1,100,106.49,
	1182346250000,1,100,106.49,
	1182346251000,1,100,106.49,
	1182346251000,1,100,106.49,
	1182346252000,1,100,106.49,
	1182346253000,1,100,106.5,
	1182346253000,1,100,106.5,
	1182346261000,1,100,106.49,
	1182346267000,1,100,106.46,
	1182346268000,1,100,106.47,
	1182346268000,1,100,106.47,
	1182346268000,1,300,106.47,
	1182346269000,1,100,106.47,
	1182346270000,1,100,106.47,
	1182346270000,1,100,106.47,
	1182346270000,1,100,106.47,
	1182346270000,1,100,106.48,
	1182346270000,1,100,106.48,
	1182346270000,1,100,106.47,
	*/
}
