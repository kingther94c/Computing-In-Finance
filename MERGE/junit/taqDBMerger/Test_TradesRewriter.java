package taqDBMerger;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import taqDBReaders.ReadGZippedTAQTradesFile;

public class Test_TradesRewriter extends junit.framework.TestCase{

	public void test_filePath(){
		String inputFilePath = "/Users/Kingther/Documents/TAQDATA/sampleTAQ/trades/20070620/IBM_trades.binRT";
		String[] s = inputFilePath.split("/");
		//System.out.println(s[s.length-2]);
		assertEquals("20070620",s[s.length-2]);
	}
	
	public void test_TradesRewriter() throws IOException{
		String inputFilePath = "/Users/Kingther/Documents/TAQDATA/sampleTAQ/trades/20070620/IBM_trades.binRT";
		short stockId = 1;
		TradesRewriter trw = new TradesRewriter( inputFilePath, stockId ) ;
		trw.getClass();
	}
	public void test_write() throws IOException{
		String inputFilePath = "/Users/Kingther/Documents/TAQDATA/sampleTAQ/trades/20070620/IBM_trades.binRT";
		short stockId = 1;
		TradesRewriter trw = new TradesRewriter( inputFilePath, stockId ) ;
		trw.rewrite();
	}
	public void test_read() throws IOException {
		String inputFilePath = "/Users/Kingther/Documents/TAQDATA/sampleTAQ/trades/20070620/IBM_trades.binRT";
		String outputFilePath = "/Users/Kingther/Documents/TAQDATA/sampleTAQ/output/20070620/test_1.dat";
		FileInputStream in = new FileInputStream( outputFilePath );
		DataInputStream dataInputStream = new DataInputStream(in);
		ReadGZippedTAQTradesFile taq = new ReadGZippedTAQTradesFile(inputFilePath);
		for(int i =0;i<taq.getNRecs();i++){
			assertEquals(taq.getMillisecondsFromMidnight(i)%1000,0);
		}
		for (int i =0;i<100;i++){
			System.out.print(dataInputStream.readLong());
			System.out.print(",");
			System.out.print(dataInputStream.readShort());
			System.out.print(",");
			System.out.print(dataInputStream.readInt());
			System.out.print(",");
			System.out.print(dataInputStream.readFloat());
			System.out.println(",");
			//System.out.print(taq.getSecsFromEpoch()+taq.getMillisecondsFromMidnight(i)/1000L);
			//System.out.print(",");
			//System.out.print("1");
			//System.out.print(",");
			//System.out.print(taq.getSize(i));
			//System.out.print(",");
			//System.out.print(taq.getPrice(i));
			//System.out.println(",");
		}
		for (int i =100;i<200;i++){
			assertEquals(dataInputStream.readLong(),taq.getSecsFromEpoch()*1000l+taq.getMillisecondsFromMidnight(i));
			assertEquals(dataInputStream.readShort(),1);
			assertEquals(dataInputStream.readInt(),taq.getSize(i));
			assertEquals(dataInputStream.readFloat(),taq.getPrice(i));
		}
		dataInputStream.close();

		

	}
}
