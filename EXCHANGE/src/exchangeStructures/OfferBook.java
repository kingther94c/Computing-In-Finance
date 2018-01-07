package exchangeStructures;

import java.util.Comparator;
import java.util.HashMap;

import fills.Fills;
import orderSpecs.ClientOrderId;
import orderSpecs.Price;
import orderSpecs.PriceLevels;
import orderSpecs.Side;
import orderTypes.RestingOrder;
import orderTypes.SweepingOrder;
import util.Pair;


public class OfferBook extends Book{
	private Comparator<? super Price> comparator = new Comparator<Price>() {
		public int compare(Price p1, Price p2) {
			return p1.compareTo(p2);
		}
	};
	public OfferBook() {
		_priceLevels = new PriceLevels(comparator);
		_restingOderMap = new HashMap<ClientOrderId,RestingOrder>();
	}
	
	@Override
	public boolean isSweepable(SweepingOrder sweepingOrder) {
		if (sweepingOrder.getSide()!=Side.BUY)
			return false;
		return sweepingOrder.getPrice().compareTo(getTopPrice()) > 0;
	}

	@Override
	public Pair<RestingOrder,Fills>  sweep(SweepingOrder sweepingOrder) {
		return super.sweep(sweepingOrder, Side.SELL);
	}
	
//	public Pair<RestingOrder,Fills> sweep(SweepingOrder sweepingOrder) {
//		RestingOrder restingOrder =  new RestingOrder(sweepingOrder);
//		if(sweepingOrder.getSide()!=Side.BUY)
//			throw new Error("Wrong Side");
//		Fills fills = new Fills();
//		ClientId clientId = sweepingOrder.getClientId();
//		ClientOrderId clientOrderId = sweepingOrder.getClientOrderId();
//		Quantity leftQuantity = restingOrder.getQuantity();
//		while(leftQuantity.compareTo(0)>0 && sweepingOrder.getPrice().compareTo(getTopPrice()) >= 0){
//			RestingOrder topOrder = this.getTopOrder();
//			if(topOrder.getQuantity().compareTo(leftQuantity)>0){
//				fills.add(new Fill(topOrder.getClientId(),clientId,topOrder.getClientOrderId(),leftQuantity,topOrder.getSide(),topOrder.getPrice()));
//				//System.out.println(fills);
//				fills.add(new Fill(clientId,topOrder.getClientId(),clientOrderId,leftQuantity,sweepingOrder.getSide(),topOrder.getPrice()));
//				//System.out.println(fills);
//				topOrder.getQuantity().minus(leftQuantity);
//				restingOrder = null;
//				break;
//			}
//			else if (topOrder.getQuantity().compareTo(leftQuantity)<=0){
//				fills.add(new Fill(topOrder.getClientId(),clientId,topOrder.getClientOrderId(),topOrder.getQuantity(),topOrder.getSide(),topOrder.getPrice()));
//				fills.add(new Fill(clientId,topOrder.getClientId(),clientOrderId,topOrder.getQuantity(),sweepingOrder.getSide(),topOrder.getPrice()));
//				this.removeTopOrder();
//				leftQuantity.minus(topOrder.getQuantity());
//			}
//		}
//		
//		return new Pair<RestingOrder,Fills>(restingOrder,fills);
//	}
}
