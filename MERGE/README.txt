Summer Assignment
Written in MAC (Unicode-utf-8)
by Kelvin (Jinze) Chen, Financial Mathematics, Courant Institute
========================
***********How To Use***********
—————————————————————————————-——
>All-in-One Solution:
Run Test_Merge.java in junit folder.

>Adaptive Solution(Step by Step):

//set working path
dataSetPath = "/Users/Kingther/Documents/TAQDATA/sampleTAQ";
inputFolderName = "trades";
outputFolderName = "output";	
String inputPath = dataSetPath + "/" + inputFolderName;
String outputPath = dataSetPath + "/" + outputFolderName; 

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

*************Design*************
—————————————————————————————-——
There are 8 classes in taqDBMerger package:

1.TickerMapper:

 * Map tickers to two byte binary codes
 * For each date sub-directory in the dates sub-directory of the trades sub-directory
 * Find all unique tickers.
 * Make a map that converts these tickers to some integer code that only takes up two bytes.

2.TradesRewriter:
 * It reads /trades/YYYYMMDD/<ticker>_trades.binRQ and rewrites into /output/YYYYMMDD/<rd>_1.dat. Its output have the following data fields:
 *
 *  long msFromTheEpoch
 *  short stockId
 *  int size 
 *  float price

3.TradesRecord
 * A TradesRecord contains the following data fields:
 *
 *  long msFromTheEpoch
 *  short stockId
 *  int size 
 *  float price

4.TradesDBReader:
 * A TradesDBReader reads trades record in this format: 
 *
 *  long msFromTheEpoch
 *  short stockId
 *  int size 
 *  float price
 * 
 * public int readChunk(long targetSequenceNum):
 * For a target sequence number, the reads records have sequence number no larger than this target sequence number and store it into a linked list of TradesRecord. 
 
5.TradesMergeClock:
 * A TradesMergeClock for DBManager to implement the merge.
 * The getNextSequenceNumber() has been improved to quickly shift the SequenceNumber to the minimal SequenceNumber of all readers instead of starting from 0 within first 2 visits.

6.TradesDBProcessor
 * A I_Processor specified for my new data format file.

7.TradesMerger
 * A TradesMerger will literately merge data files named <someName>_<round>.dat to data files named <someName>_<round+1>.dat  in the <mergePath>
 *
 * Every round it calls <nDBReaders> DBReader and merge <nDBReaders> file into one. All the file for the last round will be cleared before next round.

8.Merge
 * The class provides a all-in-one solution for the homework.

