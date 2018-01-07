package orderTypes;

import orderSpecs.ClientId;
import orderSpecs.ClientOrderId;
import orderSpecs.MarketId;
import orderSpecs.Price;
import orderSpecs.Quantity;
import orderSpecs.Side;

public class RestingOrder extends Order{

	public RestingOrder(
			ClientId clientId, 
			ClientOrderId clientOrderId, 
			MarketId marketId, 
			Side side, 
			Quantity quantity,
			Price price) {
		super(clientId, clientOrderId, marketId, side, quantity, price);
		// TODO Auto-generated constructor stub
	}

	public RestingOrder(SweepingOrder so){
		super(so);
	}
	public RestingOrder(Order o){
		super(o);
	}



}
