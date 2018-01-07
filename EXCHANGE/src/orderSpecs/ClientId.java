package orderSpecs;

public class ClientId {

	private String _clientId;
	public ClientId(String clientId) {
		_clientId = clientId;
	}
	public String getValue (){return _clientId;}
	public String toString(){
		return getValue();
	}
	public int hashCode(){
		return _clientId.hashCode();
	}
	public boolean equals(Object o){
		if(!(o instanceof ClientId))
			return false;
		return this._clientId==((ClientId) o)._clientId;
	}
}
