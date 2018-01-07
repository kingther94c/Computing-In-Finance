package exchangeStructures;

import java.util.HashMap;

import fills.Fill;
import fills.Fills;
import orderSpecs.ClientId;
import orderSpecs.ClientOrderId;
import orderSpecs.Price;
import orderSpecs.PriceLevels;
import orderSpecs.Quantity;
import orderSpecs.Side;
import orderTypes.RestingOrder;
import orderTypes.SweepingOrder;
import util.Pair;

public abstract class Book {
	protected PriceLevels _priceLevels;
	protected HashMap<ClientOrderId,RestingOrder> _restingOderMap;

	public PriceLevels getPriceLevels(){
		return _priceLevels;
	}
	
	public RestingOrder getTopOrder(){
		return _priceLevels.getTopOrder();
	}
	
	public Price getTopPrice(){
		return _priceLevels.firstKey();
	}

	public void add(RestingOrder order){
		if(order!=null){
			_restingOderMap.put(order.getClientOrderId(), order);
			_priceLevels.put(order.getPrice(), order);
		}
			
		
	}
	
	//remove the top (the best price & the first come) order
	public RestingOrder removeTopOrder(){
		return _restingOderMap.remove(_priceLevels.pollTopOrder().getClientOrderId());
	}
	
	public boolean remove(RestingOrder order){	
		_restingOderMap.remove(order.getClientOrderId());
		return _priceLevels.remove(order);
	}
	
	public boolean isEmpty(){
		return _priceLevels.isEmpty();
	}

	public abstract Pair<RestingOrder,Fills>  sweep(SweepingOrder sweepingOrder);
	public abstract boolean isSweepable(SweepingOrder sweepingOrder);

	public String toString(){
		return _priceLevels.toString();
	}
	
	public RestingOrder getOrder(ClientOrderId clientOrderId){
		return _restingOderMap.get(clientOrderId);
	}
	
	public boolean cancelOrder(ClientOrderId clientOrderId){
		return _restingOderMap.remove(clientOrderId)!=null;
	}
	public boolean cancelOrder_orderversion(ClientOrderId clientOrderId){
		RestingOrder ro =_restingOderMap.get(clientOrderId);
		if(ro==null)
			return false;
		_restingOderMap.remove(clientOrderId);
		return _priceLevels.remove(ro);
	}
	/**
	 * 
	 * @param sweepingOrder
	 * @param bookSide
	 * @return
	 */
	protected Pair<RestingOrder,Fills>  sweep(SweepingOrder sweepingOrder, Side bookSide) {
		int sideFlag;
		if (bookSide==Side.BUY)
			sideFlag =1;
		else
			sideFlag = -1;
		if (!sweepingOrder.getSide().equals(bookSide.getOppositeSide()))
			throw new Error("Wrong Side");
		
		if(this._priceLevels.isEmpty()){
			return new Pair<RestingOrder,Fills>(new RestingOrder(sweepingOrder),null);
		}
		
		
		Fills fills = new Fills();
		ClientId clientId = sweepingOrder.getClientId();
		ClientOrderId clientOrderId = sweepingOrder.getClientOrderId();
		Quantity leftQuantity = sweepingOrder.getQuantity();
		while(leftQuantity.compareTo(0)>0 && !_priceLevels.isEmpty() &&sweepingOrder.getPrice().compareTo(getTopPrice())*sideFlag <= 0){
			RestingOrder topOrder = this.getTopOrder();
			if(topOrder.getQuantity().compareTo(leftQuantity)>0){
				fills.add(new Fill(topOrder.getClientId(),clientId,topOrder.getClientOrderId(),new Quantity(leftQuantity.getValue()),topOrder.getSide(),topOrder.getPrice()));
				fills.add(new Fill(clientId,topOrder.getClientId(),clientOrderId,new Quantity(leftQuantity.getValue()),sweepingOrder.getSide(),topOrder.getPrice()));
				topOrder.getQuantity().minus(leftQuantity);
				leftQuantity.minus(leftQuantity);
				break;
			}
			else if(topOrder.getQuantity().equals(0)){
				_priceLevels.remove(topOrder);
			}
			else if (topOrder.getQuantity().compareTo(leftQuantity)<=0){
				fills.add(new Fill(topOrder.getClientId(),clientId,topOrder.getClientOrderId(),new Quantity(topOrder.getQuantity().getValue()),topOrder.getSide(),topOrder.getPrice()));
				fills.add(new Fill(clientId,topOrder.getClientId(),clientOrderId,new Quantity(topOrder.getQuantity().getValue()),sweepingOrder.getSide(),topOrder.getPrice()));
				//this.removeTopOrder();
				_priceLevels.remove(topOrder);
				_restingOderMap.remove(topOrder.getClientOrderId());
				
				leftQuantity.minus(topOrder.getQuantity());
				topOrder.getQuantity().minus(topOrder.getQuantity());
			}
		}
		RestingOrder restingOrder;
		if(leftQuantity.equals(0))
			restingOrder = null;
		else
			restingOrder = new RestingOrder(sweepingOrder);
		return new Pair<RestingOrder,Fills>(restingOrder,fills);
	}
}
