package orderTypes;

import orderSpecs.ClientId;
import orderSpecs.ClientOrderId;
import orderSpecs.MarketId;
import orderSpecs.Price;
import orderSpecs.Quantity;
import orderSpecs.Side;

public class Order {
	private ClientId _clientId;
	private ClientOrderId _clientOrderId;
	private MarketId _marketId;
	private Side _side;
	private Quantity _quantity;
	private Price _price;
	
	public ClientId getClientId(){return _clientId;}
	public ClientOrderId getClientOrderId(){return _clientOrderId;}
	public MarketId getMarketId(){return _marketId;}
	public Side getSide(){return _side;}
	public Quantity getQuantity(){return _quantity;}
	public void setQuantity(Quantity quantity){_quantity = quantity;}	
	public void setQuantity(long i) {_quantity.setValue(i);}
	public void setQuantity(int i) {_quantity.setValue(i);}
	public Price getPrice(){return _price;}
	public Order(){
		
	}
	public Order(ClientId clientId, ClientOrderId clientOrderId, MarketId marketId, Side side,
			Quantity quantity, Price price) {
		_clientId = clientId;
		_clientOrderId = clientOrderId;
		_marketId = marketId;
		_side = side;
		_quantity = quantity;
		_price = price;
	}
	protected Order( Order o){
		_clientId = o.getClientId();
		_clientOrderId = o.getClientOrderId();
		_marketId = o.getMarketId();
		_side = o.getSide();
		_quantity = o.getQuantity();
		_price = o.getPrice();
	}
	
	public String toString(){
		StringBuffer str = new StringBuffer();
		str.append(_clientId.getValue());
		str.append(":");
		str.append(_side.toString());
		str.append(_quantity.toString());
		str.append("@");
		str.append(_price.toString());
		return str.toString();
	}
	
	public int hashCode(){
		return _clientId.hashCode()*17 + _clientOrderId.hashCode();
	}
	
	public boolean equals(Object o){
		if(!(o instanceof Order))
			return false;
		Order or = (Order) o;
		if(or._clientId.equals(this._clientId) && 
				or._clientOrderId.equals(this._clientOrderId) &&
				or._marketId.equals(this._marketId) &&
				or._clientOrderId.equals(this._clientOrderId) &&
				or._price.equals(this._price) &&
				or._quantity.equals(this._quantity) &&
				or._side.equals(this._side))
			return true;
		else
			return false;
		
	}

}
