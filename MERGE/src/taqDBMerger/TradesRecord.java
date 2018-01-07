package taqDBMerger;


public class TradesRecord {

	private long		   _msFromTheEpoch;
	private short	   	   _stockId;
	private int	  	   	   _size;
	private float		   _price;
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(_price);
		result = prime * result + (int) (_msFromTheEpoch ^ (_msFromTheEpoch >>> 32));
		result = prime * result + _size;
		result = prime * result + _stockId;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TradesRecord other = (TradesRecord) obj;
		if (Float.floatToIntBits(_price) != Float.floatToIntBits(other._price))
			return false;
		if (_msFromTheEpoch != other._msFromTheEpoch)
			return false;
		if (_size != other._size)
			return false;
		if (_stockId != other._stockId)
			return false;
		return true;
	}

	
	public TradesRecord(long msFromTheEpoch, short stockId, int size, float price){
		_msFromTheEpoch = msFromTheEpoch;
		_stockId = stockId;
		_size = size;
		_price = price;
	}
	public long getMSFromTheEpoch() {
		return _msFromTheEpoch;
	}

	public short getStockId() {
		return _stockId;
	}

	public int getSize() {
		return _size;
	}

	public float getPrice() {
		return _price;
	}

	@Override
	public String toString() {
		return "TradesRecord [Sec=" + _msFromTheEpoch + ", StockId=" + _stockId + ", Size="
				+ _size + ", Price=" + _price + "]";
	}

	
}
