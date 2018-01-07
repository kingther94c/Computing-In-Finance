package orderSpecs;

import java.util.ArrayList;
import java.util.LinkedList;

import orderTypes.RestingOrder;

public class OrderList extends LinkedList<RestingOrder>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ArrayList<RestingOrder> getOrders(){
		return new ArrayList<RestingOrder>(this);
	}
	
}
