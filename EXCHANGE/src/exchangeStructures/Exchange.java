package exchangeStructures;


import java.util.HashMap;


import fills.Fills;
import messages.Cancel;
import messages.CancelRejected;
import messages.CancelationConfirmation;
import messages.RestingOrderConfirmation;
import orderSpecs.ClientOrderId;
import orderSpecs.MarketId;
import orderTypes.RestingOrder;
import orderTypes.SweepingOrder;
import util.Pair;

public class Exchange {
	private HashMap<MarketId,Market> _marketMap;
	protected Comms _comms;
	//private long _exchangeOrderId;
	
	public HashMap<MarketId,Market> getMarketMap() {return _marketMap;}
	public Comms getComms() { return _comms;}
	/**
	 * 
	 * @param clientOrderId
	 * @return
	 */
	public RestingOrder getOrder(ClientOrderId clientOrderId) {
		RestingOrder order;
		for(MarketId marketId:_marketMap.keySet()){
			order = _marketMap.get(marketId).getOrder(clientOrderId);
			if(order!=null)
				return order;
		}
		return null;

	}

	/**
	 * 
	 */
	public Exchange(){
		_marketMap = new HashMap<MarketId,Market>();
		_comms = new Comms();
		//_exchangeOrderId = 1l;
	}
	public void addMarket(Market market) {
		_marketMap.put(market.getMarketId(), market);
	}
	public Market getMarket(MarketId marketId) {
		return _marketMap.get(marketId);
	}
	/**
	 * sweep with a sweeping order
	 * @param sweepingOrder
	 */
	public void sweep(SweepingOrder sweepingOrder) {
		//Sweep
		Pair<RestingOrder,Fills> pair = _marketMap.get(sweepingOrder.getMarketId()).sweep(sweepingOrder);		
		//Comms
		_comms.addRestingOrderConfirmation(new RestingOrderConfirmation(pair.getLeft()));
		_comms.addFills(pair.getRight());
	}
	/**
	 * 
	 * @param cancel
	 */
	public void cancel_olderversion(Cancel cancel){
		for(MarketId marketId:_marketMap.keySet()){
			if(_marketMap.get(marketId).cancelOrder(cancel.getClientOrderId())){
				_comms.addCancelationConfirmation(new CancelationConfirmation(cancel));
				return;
			}
		}
		_comms.addCancelRejection(new CancelRejected(cancel));
	}
	/**
	 * Cancel a order (set the quantity to 0)
	 * @param cancel
	 */
	public void cancel(Cancel cancel){
		for(MarketId marketId:_marketMap.keySet()){
			RestingOrder order = _marketMap.get(marketId).getOrder(cancel.getClientOrderId());
			if(order!=null){
				order.setQuantity(0);
				_marketMap.get(marketId).cancelOrder(cancel.getClientOrderId());
				_comms.addCancelationConfirmation(new CancelationConfirmation(cancel));
				return;
			}
		}
		_comms.addCancelRejection(new CancelRejected(cancel));
	}

}
