package panic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import utils.DoubleComparator;
import utils.Pair;

public class QuotesReader {
	/**
	 * The Quotes Reader can read a .csv data file
	 * Except header
	 * Each Line should be like these:
	 * "20130821,11.1,22.2,33.3,NA,44.4"
	 * @author Kingther
	 *
	 */
	@Override
	public String toString() {
		return "QuotesReader [_reader=" + _reader + ", _fileName=" + _fileName + ", _tickers="
				+ Arrays.toString(_tickers) + ", _quotesList=" + _quotesList + ", _currentDate=" + _currentDate
				+ ", _extraReadQuotes=" + _extraReadQuotes + ", _dateFormat=" + _dateFormat + "]";
	}

	protected BufferedReader _reader;
	protected String _fileName;
	protected String[] _tickers;
	protected LinkedList<Pair<Date,ArrayList<Double>>> _quotesList;
	protected Date _currentDate;
	protected Pair<Date,ArrayList<Double>> _extraReadQuotes;
	
	SimpleDateFormat _dateFormat = new SimpleDateFormat("yyyyMMdd");
	
	public QuotesReader(String fileName) throws FileNotFoundException, ParseException {	
		_reader = new BufferedReader(new FileReader(fileName));
		_fileName = fileName;
		this._quotesList = new LinkedList<Pair<Date,ArrayList<Double>>>();
		this._currentDate = _dateFormat.parse("00000101");
		this._tickers = null;
	}
	
	/**
	 * Read all the data from the file
	 * @throws Exception 
	 */
	public void readAll() throws Exception {
		Date endDate = _dateFormat.parse(Integer.toString(Integer.MAX_VALUE));
		if(_tickers == null)
			readHeader();
		readData(endDate);		
	}
	
	protected void readHeader() throws IOException{
		// Can only read once!
		if(_tickers!=null)
			return;
		_tickers = _reader.readLine().split(",");
		// The first one in this String array is "", all others are the tickers.
		// Get rid of the first one
		_tickers  = Arrays.copyOfRange(_tickers, 1, _tickers.length);
	}
	
	/**
	 * Read data between _currentDate (not included) and endDate
	 *
	 * @param endDate
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	public void readData(Date endDate) throws ParseException, NumberFormatException, IOException {
		// Read header for first read
		if(_tickers == null)
			readHeader();
		
		// if endDate is before _currentDate, do nothing
		if (endDate.before(_currentDate)){
			return;
		}
		
		// Take the _extraReadQuotes to _quotesList if its date is suitable
		if(_extraReadQuotes != null && endDate.after(_extraReadQuotes.getLeft())){
			_quotesList.add(_extraReadQuotes);
			_extraReadQuotes = null;
		}
		
		String line;
		// Repeat until EOF
		while ((line = _reader.readLine()) != null) {
			Pair<Date,ArrayList<Double>> quotes =  convertToQuotes (line);
			Date date = quotes.getLeft();
			
			// Break after being equal to end date
			if (date.equals(endDate) ) {
				_currentDate = endDate;
				_quotesList.add(quotes);
				break;
			}
			
			// Break after passing to end date
			if (date.after(endDate) ) {
				// Record that we read an extra line
				_extraReadQuotes = quotes;
				break;
			}
			
			// Read quote data for non-boundary condition
			_currentDate = date;
			_quotesList.add(quotes);
		}		
	}
	
	/**
	 * Skip to the line with startDate and get ready to read this line in next read
	 * 
	 * @param startDate
	 * @throws Exception 
	 */
	public void skipToDate(Date startDate) throws Exception {
		// Read header for first read
		if(_tickers == null)
			readHeader();
		
		// Re-open reader if startDate is before _currentDate
		else if (startDate.before(_currentDate) || startDate.equals(_currentDate)){
			_tickers = null;
			_quotesList = new LinkedList<Pair<Date,ArrayList<Double>>>();
			_currentDate = _dateFormat.parse("00000101");
			_extraReadQuotes = null;
			_reader.close();
			_reader = new BufferedReader(new FileReader(_fileName));
			skipToDate(startDate);
		}
		
		//
		String line;
		// Repeat until EOF
		while ((line = _reader.readLine()) != null) {
			
			Pair<Date,ArrayList<Double>> quotes =  convertToQuotes (line);
			
			Date date = quotes.getLeft();
			
			// Break after being equal to or after  startDate
			if (date.after(startDate) ||date.equals(startDate)) {
				// Record that we read an extra line
				_extraReadQuotes = quotes;
				break;
			}
			
			// Read quote data for non-boundary condition
			_currentDate = date;
		}
	}
	
	/**
	 * Read data between start date and end date
	 * 
	 * @param startDate
	 * @param endDate
	 * @throws Exception 
	 */
	public void readData(Date startDate, Date endDate) throws Exception {
		if (startDate.after(endDate)) {
			throw new Exception("Invalid date!");
		}
		_quotesList = new LinkedList<Pair<Date,ArrayList<Double>>>();
		_currentDate = _dateFormat.parse("00000101");
		_extraReadQuotes = null;
		skipToDate(startDate);
		readData(endDate);
	}
	
	
	private Pair<Date,ArrayList<Double>> convertToQuotes (String line){
		// Get date
		Date date;
		try {
			date = _dateFormat.parse(line.substring(0, 8));
		} catch (ParseException e) {
			return null;
		}
        String[] quotesString = line.substring(9).split(",");
        ArrayList<Double> quoteDouble = new ArrayList<Double>(quotesString.length);
        for (int i =0;i<quotesString.length;i++){
        	String s = quotesString[i];
        	quoteDouble.add(s.equalsIgnoreCase("NA")? Double.NaN : Double.parseDouble(s));
        }
        return new Pair<Date,ArrayList<Double>>(date, quoteDouble);     
	}
	
	
	public String[] getTickers() {
		return _tickers;
	}
	
	public LinkedList<Pair<Date,ArrayList<Double>>> getQuotesList() {
		return _quotesList;
	}
	
	/**
	 * Get the UpRatios and NumStocks as a Pair<List<Double>,List<Integer>>
	 * @return the UpRatios and NumStocks as a Pair<List<Double>,List<Integer>>
	 */
	public Pair<List<Double>,List<Integer>> computeUpRatiosAndNumStocks() {
		
		// Use linkedList to append
		LinkedList<Double> ups = new LinkedList<Double>();
		LinkedList<Integer> numStocks = new LinkedList<Integer>();
		
		// Keep track of current quotes and previous quotes
		Pair<Date,ArrayList<Double>> previousQuotes;
		Pair<Date,ArrayList<Double>> currentQuotes;
		//System.out.println(_quotesList);
		
		// remove and get the first element from _quotesList
		previousQuotes = _quotesList.pollFirst();
		
		// Repeat until the _quotesList is empty

		while ((currentQuotes = _quotesList.pollFirst())!=null ) {
			
			List<Double> previousQuote = previousQuotes.getRight();
			List<Double> currentQuote = currentQuotes.getRight();
			
			// Number of all stocks
			double n = 0; 
			// Number of up stocks 
			double u = 0; 
			//System.out.println(previousQuote);
			//System.out.println(currentQuote);			
			for (int i = 0; i < previousQuote.size(); i++) {
				
				// Skip if either current quote or next quote is NaN
				if(previousQuote.get(i).isNaN() || currentQuote.get(i).isNaN()) 
					continue;
				n++;
				if(DoubleComparator.compare(currentQuote.get(i), previousQuote.get(i), 1e-5)>0)
					u++;				
			}
			// Skip if all stock price is NAN
			if (n==0){
				continue;
			}
			else{
				ups.add(u / n);
				numStocks.add((int)n);
			}

			// Update
			previousQuotes = currentQuotes;
		}
		
		// The first quote data for the next year need to compare with the last one of this year.
		// Add back the currentQuotes for the convenience of next computing
		_quotesList.add(currentQuotes);
		
		return new Pair<List<Double>,List<Integer>>(ups,numStocks);
	}
	
	public void close() {
		try {
			_reader.close();
		} catch (IOException e) {
			return;
		}
	}
	
}