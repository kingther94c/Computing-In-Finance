package exchangeStructures;

import fills.Fill;
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

public class Test_OfferBook extends junit.framework.TestCase{

	public void test_OfferBook(){
		MarketId marketId0 = new MarketId( "IBM" );
		ClientId clientId0 = new ClientId( "Lee" );
		ClientOrderId clientOrderId0 = new ClientOrderId( "ABC" );
		Side side0 = Side.SELL;
		Quantity quantity1 = new Quantity( 100L );
		Quantity quantity2 = new Quantity( 200L );
		Quantity quantity3 = new Quantity( 300L );
		Price price0 = new Price( 1280000 );
		Price price1 = new Price( 1280011 );
		Price price2 = new Price( 1290222 );
		
		RestingOrder p0q1 = new RestingOrder(
				clientId0,
				clientOrderId0,
				marketId0,
				side0,
				quantity1,
				price0
			);
		RestingOrder p0q2 = new RestingOrder(
				clientId0,
				clientOrderId0,
				marketId0,
				side0,
				quantity2,
				price0
			);
		RestingOrder p0q3 = new RestingOrder(
				clientId0,
				clientOrderId0,
				marketId0,
				side0,
				quantity3,
				price0
			);
		RestingOrder p1q1 = new RestingOrder(
				clientId0,
				clientOrderId0,
				marketId0,
				side0,
				quantity1,
				price1
			);
		RestingOrder p2q2 = new RestingOrder(
				clientId0,
				clientOrderId0,
				marketId0,
				side0,
				quantity2,
				price2
			);
		
		
		OfferBook offerBook = new OfferBook();
		offerBook.add(p2q2);
		System.out.println(offerBook);
		assertEquals(offerBook.toString(),"<PriceLevels>{129.0222=>[Lee:SELL200@129.0222]}");
		offerBook.add(p0q2);
		System.out.println(offerBook);
		assertEquals(offerBook.toString(),"<PriceLevels>{128.0000=>[Lee:SELL200@128.0000]129.0222=>[Lee:SELL200@129.0222]}");
		offerBook.add(p0q1);
		System.out.println(offerBook);
		assertEquals(offerBook.toString(),"<PriceLevels>{128.0000=>[Lee:SELL200@128.0000, Lee:SELL100@128.0000]129.0222=>[Lee:SELL200@129.0222]}");
		offerBook.add(p0q3);
		System.out.println(offerBook);
		assertEquals(offerBook.toString(),"<PriceLevels>{128.0000=>[Lee:SELL200@128.0000, Lee:SELL100@128.0000, Lee:SELL300@128.0000]129.0222=>[Lee:SELL200@129.0222]}");
		offerBook.add(p1q1);
		System.out.println(offerBook);
		assertEquals(offerBook.toString(),"<PriceLevels>{128.0000=>[Lee:SELL200@128.0000, Lee:SELL100@128.0000, Lee:SELL300@128.0000]128.0011=>[Lee:SELL100@128.0011]129.0222=>[Lee:SELL200@129.0222]}");
	}
	public void test_Sweep(){
		// Create a client order
		OfferBook book = new OfferBook();
		MarketId marketId0 = new MarketId( "IBM" );
		ClientId clientId0 = new ClientId( "Lee" );
		ClientOrderId clientOrderId0 = new ClientOrderId( "ABC" );
		Side side0 = Side.SELL;
		Quantity quantity0 = new Quantity( 1000L );
		Price price0 = new Price( 1200000 );
		SweepingOrder sweepingOrder = new SweepingOrder(
			clientId0,
			clientOrderId0,
			marketId0,
			side0,
			quantity0,
			price0
		);
		
		// Sweep the exchange with this order
		book.add( new RestingOrder(sweepingOrder) );
		
		// No part of the first order to buy 1000 shares at $128
		// was executed because the offer book was empty. So 
		// the entire order became a resting order in the bid 
		// book at a price level of $128.
		
		// There should be one price level in the bid book
		assertTrue( book.getPriceLevels().size() == 1 );
		
		// Make sure that the market sent an alert to the client
		// about the new resting order via the fake comms link
		RestingOrder restingOrder = book.getTopOrder();
		System.out.println(restingOrder);
		System.out.println(book);
		assertTrue( restingOrder.equals( new RestingOrder( sweepingOrder ) ) );
		assertTrue( restingOrder.getQuantity().equals( new Quantity( 1000 ) ) );
		
		// We're moving on to our second order, a sell of 500
		// shares. It will match partially with our 1st order
		// which is now a resting order in the bid book.
		ClientId clientId1 = new ClientId( "Bob" );
		ClientOrderId clientOrderId1 = new ClientOrderId( "VZFZF" );
		MarketId marketId1 = new MarketId( "IBM" );
		Side side1 = Side.BUY;
		Quantity quantity1 = new Quantity( 500L ); // Half of the 1000 that's already in the book
		Price price1 = new Price( 1280000L );
		SweepingOrder sweepingOrder1 = new SweepingOrder(
			clientId1,
			clientOrderId1,
			marketId1,
			side1,
			quantity1,
			price1
		);
		// Sweep exchange with this order
		Pair<RestingOrder,Fills> pair = book.sweep( sweepingOrder1 );
		RestingOrder restingOrder_ = pair.getLeft();
		System.out.println(restingOrder_);
		System.out.println(pair);
		assertTrue(restingOrder_==null);
		// This order should have generated two fills of 500 each.
		// One goes to Bob, whose sweeping order caused the fill.
		// And the other goes to Lee, whose resting order was
		// filled by the sweeping order. Lee is Bob's counter party.

		Fill fill = pair.getRight().get(0);
		assertTrue( fill.getClientId().equals( new ClientId( "Lee" ) ) );
		assertTrue( fill.getCounterpartyId().equals( new ClientId( "Bob" ) ) );
		assertTrue( fill.getQuantity().equals( new Quantity( 500 ) ) );
		// The clientOrderId of the first fill should be the client
		// order id of the resting order that was filled by the 
		// sweeping order
		assertTrue( fill.getClientOrderId().equals( sweepingOrder.getClientOrderId() ) );
		// Here is the second fill - the fill of the sweeping order
		fill = pair.getRight().get(1);
		assertTrue( fill.getClientId().equals( new ClientId( "Bob" ) ) );
		assertTrue( fill.getCounterpartyId().equals( new ClientId( "Lee" ) ) );
		assertTrue( fill.getQuantity().equals( new Quantity( 500 ) ) );
		// The clientOrderId of the second fill should be the clientOrderId
		// of the sweeping order that produced the match
		assertTrue( fill.getClientOrderId().equals( sweepingOrder1.getClientOrderId() ) );

	}
}
