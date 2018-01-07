package messages;

import orderSpecs.ClientId;
import orderSpecs.ClientOrderId;

public class CancelationConfirmation {
	private ClientId _clientId;
	private ClientOrderId _clientOrderId;
	@Override
	public String toString() {
		return "CancelationConfirmation [ClientId=" + _clientId + ", ClientOrderId=" + _clientOrderId + "]";
	}
	public ClientId getClientId(){return _clientId;}
	public ClientOrderId getClientOrderId(){return _clientOrderId;}	
	
	public CancelationConfirmation(Cancel cancel){
		_clientId = cancel.getClientId();
		_clientOrderId = cancel.getClientOrderId();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_clientId == null) ? 0 : _clientId.hashCode());
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
		CancelationConfirmation other = (CancelationConfirmation) obj;
		if (_clientId == null) {
			if (other._clientId != null)
				return false;
		} else if (!_clientId.equals(other._clientId))
			return false;
		if (_clientOrderId == null) {
			if (other._clientOrderId != null)
				return false;
		} else if (!_clientOrderId.equals(other._clientOrderId))
			return false;
		return true;
	}
}
