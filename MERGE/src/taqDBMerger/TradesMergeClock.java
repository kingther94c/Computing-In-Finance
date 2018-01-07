package taqDBMerger;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import dbReaderFramework.I_DBClock;
import dbReaderFramework.I_DBProcessor;
import dbReaderFramework.I_DBReader;


/** Clock class that implements I_DBClock interface. 
 * 
 * This clock is used by the DBManager to determine the next sequence number 
 * for reading. In this case, the clock obtains its next sequence number by 
 * looking at the sequence number of the next record in the first reader in 
 * a list of readers sorted by sequence numbers. 
 * 
 * @author Lee
 *
 */
public class TradesMergeClock implements I_DBClock {
	protected int _visitCount;	
	protected long _interval = 1000l;
	protected LinkedList<I_DBReader>    _readers;
	protected LinkedList<I_DBProcessor> _processors;

	
	
	/**
	 * Instantiate clock that merges two files at highest level of granularity
	 * 
	 * @param readers List of two readers
	 * @param processors List of two processors
	 */
	public TradesMergeClock( LinkedList<I_DBReader> readers, LinkedList<I_DBProcessor> processors ) {
		_visitCount = 0;
		_readers = readers;
		_processors = processors;
		Collections.sort( 
			_readers,
			new Comparator<I_DBReader>() {
				public int compare( I_DBReader reader1, I_DBReader reader2 ) {
					return Double.compare( reader1.getSequenceNumber(), reader2.getSequenceNumber() );
				}
			}
				
		);
	}
	
	/**
	 * @return True if there are no more active readers or active processors or
	 *         false otherwise. Note that this clock is finished when the files
	 *         that are being read have no more data, not when a certain time
	 *         has elapsed.
	 */
	@Override
	public boolean isFinished() {
		return ( _readers.size() == 0 ) || ( _processors.size() == 0 	);
	}
	
	/**
	 * @return Current sequence number for this clock, obtained by looking at
	 *         the reader with the lowest current sequence number
	 */
	@Override
	public long getNextSequenceNumber() {

		if(_visitCount<2)
		{
			_visitCount++;
			if(_visitCount==2){
				long minNextSequenceNumber = Long.MAX_VALUE;
				for(I_DBReader reader : _readers){
					if(((TradesDBReader)reader).getNextSequenceNumber()<minNextSequenceNumber)
						minNextSequenceNumber = ((TradesDBReader)reader).getNextSequenceNumber();
				}
				return minNextSequenceNumber - _interval;
			}	
		}
		return _readers.get(0).getSequenceNumber() + _interval;	
		
	}

}
