package taqDBMerger;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import dbReaderFramework.I_DBProcessor;
import dbReaderFramework.I_DBReader;

public class TradesDBProcessor implements I_DBProcessor{
	DataOutputStream _dataOutputStream;
	public TradesDBProcessor(DataOutputStream dataOutputStream){
		_dataOutputStream = dataOutputStream;
	}
	/**
	 * Method used by DBManager to pass readers with data to a processor.
	 * 
	 * @param sequenceNumber         Sequence number that DBManager just asked all readers to read
	 * @param numReadersWithNewData  Number of readers in the readers list that have new data. Note that it may be zero.
	 * @param readers                List of readers, the first n of which have new data, where n = numReadersWithNewData
	 * 
	 * @return True if I_DBProcessor is not finished and false if it is.
	 */
	@Override
	public boolean processReaders(long sequenceNumber, int  numReadersWithNewData, LinkedList<I_DBReader> readers) {
		Iterator<I_DBReader> itr = readers.iterator();
		int countReader = 0;
		while(itr.hasNext() && countReader<numReadersWithNewData){
			TradesDBReader reader = (TradesDBReader)itr.next();
			countReader ++;
			if( reader.getLastSequenceNumberRead() == sequenceNumber ) {
				int nRecs = reader.getNRecsRead();
				try {
					for( int i = 0; i < nRecs; i++ ) {
						TradesRecord rec = reader.getRecord();
						//System.out.println(rec);
						_dataOutputStream.writeLong( rec.getMSFromTheEpoch() );
						_dataOutputStream.writeShort( rec.getStockId() );
						_dataOutputStream.writeInt( rec.getSize() );
						_dataOutputStream.writeFloat( rec.getPrice() );

					}
				} catch (IOException e) {
					return false;
				}
			}
		}
		return true;

	}

	@Override
	public void stop() throws Exception {
		_dataOutputStream.flush();
		_dataOutputStream.close();
	}

}
