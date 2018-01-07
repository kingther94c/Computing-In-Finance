package exchangeStructures;

import fills.Fills;
import orderSpecs.ClientId;
import orderSpecs.ClientOrderId;
import orderSpecs.MarketId;
import orderSpecs.Price;
import orderSpecs.Quantity;
import orderSpecs.Side;
import orderTypes.RestingOrder;
import orderTypes.SweepingOrder;
import util.Pair;

public class Test_Market extends junit.framework.TestCase{
	public void test_sweep1(){
		// Create an exchange and add one market
		Exchange exchange = new Exchange();
		MarketId marketId0 = new MarketId( "IBM" );
		Market market = new Market( exchange, marketId0 );
		exchange.addMarket( market );
		
		// Create a client order
		ClientId clientId0 = new ClientId( "Lee" );
		ClientOrderId clientOrderId0 = new ClientOrderId( "ABC" );
		Side side0 = Side.BUY;
		Quantity quantity0 = new Quantity( 1000L );
		Price price0 = new Price( 1280000 );
		SweepingOrder sweepingOrder = new SweepingOrder(
			clientId0,
			clientOrderId0,
			marketId0,
			side0,
			quantity0,
			price0
		);

		// Sweep the exchange with this order
		Pair<RestingOrder,Fills> pair = market.sweep( sweepingOrder );
		// No part of the first order to buy 1000 shares at $128
		// was executed because the offer book was empty. So 
		// the entire order became a resting order in the bid 
		// book at a price level of $128.
		
		// There should be one price level in the bid book
		assertTrue( market.getBidBook().getPriceLevels().size() == 1 );
		// There should be no price levels in the offer book
		assertTrue( market.getOfferBook().getPriceLevels().size() == 0 );
		// We're moving on to our second order, a sell of 500
		// shares. It will match partially with our 1st order
		// which is now a resting order in the bid book.
		ClientId clientId1 = new ClientId( "Bob" );
		ClientOrderId clientOrderId1 = new ClientOrderId( "VZFZF" );
		MarketId marketId1 = new MarketId( "IBM" );
		Side side1 = Side.SELL;
		Quantity quantity1 = new Quantity( 500L ); // Half of the 1000 that's already in the book
		Price price1 = new Price( 1200000L );
		SweepingOrder sweepingOrder1 = new SweepingOrder(
			clientId1,
			clientOrderId1,
			marketId1,
			side1,
			quantity1,
			price1
		);
		// Sweep exchange with this order
		pair = market.sweep( sweepingOrder1 );
		assertEquals("<null,[Lee/Bob:BUY500@128.0000, Bob/Lee:SELL500@128.0000]>",pair.toString());
	}
	public void test_sweep2(){
		// Create an exchange and add one market
		Exchange exchange = new Exchange();
		MarketId marketId0 = new MarketId( "IBM" );
		Market market = new Market( exchange, marketId0 );
		exchange.addMarket( market );
		
		// Create a client order
		ClientId clientId0 = new ClientId( "Lee" );
		ClientOrderId clientOrderId0 = new ClientOrderId( "ABC" );
		Side side0 = Side.BUY;
		Quantity quantity0 = new Quantity( 1000L );
		Price price0 = new Price( 1280000 );
		SweepingOrder sweepingOrder = new SweepingOrder(
			clientId0,
			clientOrderId0,
			marketId0,
			side0,
			quantity0,
			price0
		);

		// Sweep the exchange with this order
		Pair<RestingOrder,Fills> pair = market.sweep( sweepingOrder );
		// No part of the first order to buy 1000 shares at $128
		// was executed because the offer book was empty. So 
		// the entire order became a resting order in the bid 
		// book at a price level of $128.
		
		// There should be one price level in the bid book
		assertTrue( market.getBidBook().getPriceLevels().size() == 1 );
		// There should be no price levels in the offer book
		assertTrue( market.getOfferBook().getPriceLevels().size() == 0 );
		// We're moving on to our second order, a sell of 500
		// shares. It will match partially with our 1st order
		// which is now a resting order in the bid book.
		ClientId clientId1 = new ClientId( "Bob" );
		ClientOrderId clientOrderId1 = new ClientOrderId( "VZFZF" );
		MarketId marketId1 = new MarketId( "IBM" );
		Side side1 = Side.SELL;
		Quantity quantity1 = new Quantity( 1600L ); // Half of the 1000 that's already in the book
		Price price1 = new Price( 1200000L );
		SweepingOrder sweepingOrder1 = new SweepingOrder(
			clientId1,
			clientOrderId1,
			marketId1,
			side1,
			quantity1,
			price1
		);
		// Sweep exchange with this order
		pair = market.sweep( sweepingOrder1 );
		assertEquals("<Bob:SELL1600@120.0000,[Lee/Bob:BUY1000@128.0000, Bob/Lee:SELL1000@128.0000]>",pair.toString());
	}
}
