package fills;

import orderSpecs.*;

public class Fill {
	private ClientId _clientId;
	private CounterpartyId _counterpartyId;
	private ClientOrderId _clientOrderId;
	private Quantity _quantity;
	private Side _side;
	private Price _price;

	public ClientId getClientId(){return _clientId;}
	public CounterpartyId getCounterpartyId(){return _counterpartyId;}
	public ClientOrderId getClientOrderId(){return _clientOrderId;}
	public Quantity getQuantity(){return _quantity;	}
	public Side getSide(){return _side;}
	
	public Fill(ClientId clientId, CounterpartyId counterpartyId, ClientOrderId clientOrderId, Quantity quantity, Side side, Price price){
		_clientId = clientId;
		_counterpartyId = counterpartyId;
		_clientOrderId = clientOrderId;
		_quantity = quantity;
		_side = side;
		_price = price;
	}
	
	public Fill(ClientId clientId, ClientId counterpartyId, ClientOrderId clientOrderId, Quantity quantity, Side side, Price price){
		_clientId = clientId;
		_counterpartyId = new CounterpartyId(counterpartyId);
		_clientOrderId = clientOrderId;
		_quantity = quantity;
		_side = side;
		_price = price;
	}
	
	public String toString(){
		StringBuffer str = new StringBuffer();
		str.append(_clientId.getValue());
		str.append("/");
		str.append(_counterpartyId.getValue());
		str.append(":");
		str.append(_side.toString());
		str.append(_quantity.toString());
		str.append("@");
		str.append(_price.toString());
		return str.toString();
	}
	
}
