package orderSpecs;

public class ClientOrderId {

	private String _clientOrderId;
	public ClientOrderId(String clientOrderId) {
		_clientOrderId = clientOrderId;
	}
	public String getValue (){return _clientOrderId;}
	public String toString(){
		return getValue();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_clientOrderId == null) ? 0 : _clientOrderId.hashCode());
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
		ClientOrderId other = (ClientOrderId) obj;
		if (_clientOrderId == null) {
			if (other._clientOrderId != null)
				return false;
		} else if (!_clientOrderId.equals(other._clientOrderId))
			return false;
		return true;
	}
}
