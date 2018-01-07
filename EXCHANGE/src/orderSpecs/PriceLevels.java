package orderSpecs;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import orderTypes.RestingOrder;
/**
 * DataStruct : TreeMap + LinkedList
 * PriceLevels is a TreeMap which allows duplicate Keys :
 * The Value is a LinkedList of Resting Orders.
 * When adding a RestingOrder with an existing key, value will be added to a linkedlist<RestingOrder> for this Key.
 * In average, It takes O(logn) to find a price level,  O(logn) to insert a new order.
 * @author KELVIN
 */



public class PriceLevels extends TreeMap<Price,OrderList>{
	
	private static final long serialVersionUID = 1L;

	public PriceLevels(){
		super();
	}
	public PriceLevels(Comparator<? super Price> comparator){
		super(comparator);
	}
	
	public void put(Price price,RestingOrder order){
		if(this.containsKey(price)){
			this.get(price).addLast(order);
		}
		else{
			OrderList orders = new OrderList ();
			orders.add(order);
			super.put(price,orders);
		}	
	}
	/**
	 * Removes the mapping for this order from this PriceLevels if present.
	 * @param order order which should be removed
	 * @return the previous order associated with same price (if there is not, return the first order of the next price), or null if there was no mapping for key. (A null return can also indicate that the map previously associated null with key.)
	 */
	public boolean remove(RestingOrder order){
		OrderList orders = this.get(order.getPrice());
		if (orders == null)
			return false;
		if(orders.remove(order)){
			if (orders.size()==0)
				return (this.remove(order.getPrice())!=null);
		}
		return false;
	}
	
	

	public RestingOrder getTopOrder() {
		return this.get(this.firstKey()).getFirst();
	}
	
	public RestingOrder pollTopOrder() {
		RestingOrder restingOrder;
		if( this.isEmpty())
			return null;
		OrderList orderList = this.firstEntry().getValue();
		restingOrder = orderList.poll();
		if(orderList.isEmpty())
			this.pollFirstEntry();
		return restingOrder;
	}
	
	public String toString(){
		StringBuffer str = new StringBuffer();
		str.append("<PriceLevels>{");
		for(java.util.Map.Entry<Price, OrderList> entry : this.entrySet()) {
			str.append(entry.getKey().toString());
			str.append("=>");
			str.append(entry.getValue().toString());
		}
		str.append("}");
		return str.toString();
	}
	public RestingOrder getOrder(ClientOrderId clientOrderId) {
		for (Map.Entry<Price,OrderList> mapEntry : this.entrySet()){
			for(RestingOrder restingOrder :  mapEntry.getValue()){
				if(restingOrder.getClientOrderId().equals(clientOrderId))
					return restingOrder;
			}
		}
		return null;
	}

	public boolean cancelOrder(ClientOrderId clientOrderId) {
		Iterator<Map.Entry<Price,OrderList>> itrMap = this.entrySet().iterator();
		while (itrMap.hasNext()){
			LinkedList<RestingOrder> orders = itrMap.next().getValue();
			if(orders.size()==0){
				itrMap.remove();
				continue;
			}
			Iterator<RestingOrder> itr = orders.iterator();
			while (itr.hasNext()){
				if(itr.next().getClientOrderId().equals(clientOrderId)){
					itr.remove();
					return true;
				}
			}
		}
		return false;
	}
	

		
	
	
}
