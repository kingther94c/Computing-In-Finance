package exchangeStructures;

import exchangeStructures.OfferBook;
import fills.Fills;
import orderSpecs.*;
import orderTypes.*;
import util.Pair;

public class Market {
	private Exchange _exchange;
	private MarketId _marketId;
	private OfferBook _offerBook;
	private BidBook _bidBook;
	
	/**
	 * 
	 * @param exchange
	 * @param marketId
	 */
	public Market(Exchange exchange, MarketId marketId) {
		_exchange = exchange;
		_marketId = marketId;
		_offerBook = new OfferBook();
		_bidBook = new BidBook();

	}
	public MarketId getMarketId(){ return _marketId;}
	public Exchange getExchange(){ return _exchange;}
	public OfferBook getOfferBook() {
		return _offerBook;
	}
	public BidBook getBidBook() {
		return _bidBook;
	}
	private Book getBook(Side side){
		if(side == Side.BUY)
			return _bidBook;
		else if(side == Side.SELL)
			return _offerBook;
		return null;
	}
	/**
	 * 
	 * @param side
	 * @return
	 */
	private Book getOppositeBook(Side side){
		if(side == Side.SELL)
			return _bidBook;
		else if(side == Side.BUY)
			return _offerBook;
		return null;
	}
	/**
	 * 
	 * @param sweepingOrder
	 * @return
	 */
	public Pair<RestingOrder,Fills> sweep(SweepingOrder sweepingOrder){
		Side side = sweepingOrder.getSide();
		Book thisSideBook = this.getBook(side);
		Book oppositeSideBook = this.getOppositeBook(side);
		Pair<RestingOrder,Fills> pair = oppositeSideBook.sweep(sweepingOrder);
		thisSideBook.add(pair.getLeft());
		return pair;
		
	}
	/**
	 * 
	 * @param clientOrderId
	 * @return
	 */
	public RestingOrder getOrder(ClientOrderId clientOrderId){
		RestingOrder order = _offerBook.getOrder(clientOrderId);
		if (order==null)
			order = _bidBook.getOrder(clientOrderId);
		return order;
		
	}
	/**
	 * 
	 * @param clientOrderId
	 * @return
	 */
	public boolean cancelOrder(ClientOrderId clientOrderId) {
		return _offerBook.cancelOrder(clientOrderId) || _bidBook.cancelOrder(clientOrderId);
	}
	
}
