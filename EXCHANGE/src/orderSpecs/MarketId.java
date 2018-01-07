package orderSpecs;

public class MarketId {

	private String _marketId;
	public MarketId(String marketId) {
		_marketId = marketId;
	}
	public String getValue(){return _marketId;}
	public String toString(){
		return getValue();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_marketId == null) ? 0 : _marketId.hashCode());
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
		MarketId other = (MarketId) obj;
		if (_marketId == null) {
			if (other._marketId != null)
				return false;
		} else if (!_marketId.equals(other._marketId))
			return false;
		return true;
	}
}
