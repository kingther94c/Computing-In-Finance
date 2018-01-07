package taqDBMerger;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

import dbReaderFramework.I_DBReader;

public class TradesDBReader implements I_DBReader {
/**
 * This class implements an I_DBReader for a .dat file record in this format: 
 * 
 *  long msFromTheEpochQueue
 *  short stockId
 *  int size 
 *  float price
 *  
 * 
 * @author Kingther
 */
	protected DataInputStream 		   _dataInputStream;
//	protected int                      _nRecsRead;    // Number of records read, to be gotten which is just _RecsQueue.size()
	protected boolean				   _extraRead;    // Whether the last record in the _RecsQueue have a higher SequenceNum than target
	protected boolean                  _isFinished;
	protected long                     _lastSequenceNumberRead; //last SequenceNumber for which records were successfully read
	protected long   				   _currentSequenceNumber; // current SequenceNumber we have read up to 
	protected long   				   _nextSequenceNumber; // sequence number of next record (default = _interval)
	protected long					   _interval = 1000l;
	protected LinkedList<TradesRecord> _RecsQueue;
	

	public TradesDBReader(String filePath) {
		_RecsQueue = new LinkedList<TradesRecord>();		
		FileInputStream in;
		try {
			in = new FileInputStream( filePath );
			_dataInputStream = new DataInputStream(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		_extraRead = false;
		//_nRecsRead = 0;
		_isFinished = false;
		_lastSequenceNumberRead = 0;
		_currentSequenceNumber = 0;
		_nextSequenceNumber = _currentSequenceNumber + _interval;
	}

	/**
	 * Return and remove a read record, 
	 * If the next record is extra read one or there is nothing in the queue return null.
	 * @return the first TradesRecord in the queue
	 */
	public TradesRecord getRecord() {
		if(_RecsQueue.size() <= (_extraRead? 1:0) )
			return null;
			//throw new Exception("All data read have been put!");
		//_nRecsRead--;
		return _RecsQueue.removeFirst();
	}
	
	/**
	 * Method to read all records with sequence numbers less than or equal to the
	 * specified sequence number
	 * 
	 * @param sequenceNum Sequence number specified for the above task
	 * @return Number of records that were ready to get (The extra read one cannot be gotten) 
	 */
	
	@Override
	public int readChunk(long targetSequenceNum) {
		// Update the _currentSequenceNumber for the extra read record
		long extraReadSequenceNumber;
		if (_extraRead){
			extraReadSequenceNumber = _RecsQueue.getLast().getMSFromTheEpoch();
			if(targetSequenceNum >= extraReadSequenceNumber)
				_lastSequenceNumberRead = extraReadSequenceNumber;
			else
			{
				if(_currentSequenceNumber < targetSequenceNum)
					_currentSequenceNumber = targetSequenceNum;
				return 0;
			}

		}


			
		if( _isFinished )
			return 0;
		// Iterate over records until we hit last record in data or we hit
		// a sequence number that is higher than the target sequence number
		try {
			while( true ) {

				// Check if we hit last record in data
				if(_dataInputStream.available()==0){
					if(_extraRead)
						_extraRead = false;
					_isFinished = true;
					break;

				}

				// Read next record & Update _currentSequenceNumber
				_nextSequenceNumber = _dataInputStream.readLong();
				_RecsQueue.add(new TradesRecord(_nextSequenceNumber, _dataInputStream.readShort(), _dataInputStream.readInt(), _dataInputStream.readFloat()));
				//_nRecsRead ++;
				
				// Check if we hit a sequence number that is higher
				// than the target sequence number
				if( _nextSequenceNumber > targetSequenceNum ){
					// Note that internally, we have read a extra piece of data 
					// which have a SequenceNumber larger than target
					_extraRead = true;
					break;
				}
				else
					_lastSequenceNumberRead= _nextSequenceNumber;
				
			}
		}	catch (IOException e) {
			e.printStackTrace();	
		}
		
		// Save the sequence number that was just read
		_currentSequenceNumber = targetSequenceNum;
		
		// Save number of records read and return it
		return _RecsQueue.size() - (_extraRead? 1 : 0);
	}
	
	/**
	 * Get sequence number of next record. Internally, this reader is looking at some
	 * record of a larger data set. That record has a sequence number, as might some
	 * number of records that follow it. This sequence number represents a new chunk
	 *  - a collection of records that has been read but has not yet been processed.
	 * 
	 * @return Sequence number of next record
	 */
	@Override
	public long getSequenceNumber() {
		return _currentSequenceNumber;
	}
	
	/**
	 * Get next sequence number of next record that we have no access to now.
	 * 
	 * @return Minimum sequence number for next chuck
	 */
	public long getNextSequenceNumber() {
		return _nextSequenceNumber;
	}
	/**
	 * Stop reading and close all files. 
	 */
	@Override
	public void stop() {
		try {
			_dataInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isFinished() {
		return _isFinished; 
	}
	
	/**
	 * Return the last sequence number for which records were successfully
	 * read by this reader.
	 */
	@Override
	public long getLastSequenceNumberRead() {
		return _lastSequenceNumberRead;
	}
	/**
	 * Return the number of valid records read (The last extra one does not count)
	 * @return
	 */
	public int getNRecsRead(){
		return _RecsQueue.size() - (_extraRead? 1 : 0);
		
	}


}
