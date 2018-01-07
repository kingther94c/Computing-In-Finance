package exchangeStructures;

import orderSpecs.ClientId;
import orderSpecs.ClientOrderId;
import orderSpecs.MarketId;
import orderSpecs.Price;
import orderSpecs.Quantity;
import orderSpecs.Side;
import orderTypes.RestingOrder;

public class Test_BidBook extends junit.framework.TestCase{

	public void test_BidBook(){
		MarketId marketId0 = new MarketId( "IBM" );
		ClientId clientId0 = new ClientId( "Lee" );
		ClientOrderId clientOrderId0 = new ClientOrderId( "ABC" );
		Side side0 = Side.BUY;
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
		
		
		BidBook bidBook = new BidBook();
		bidBook.add(p2q2);
		System.out.println(bidBook);
		assertEquals(bidBook.toString(),"<PriceLevels>{129.0222=>[Lee:BUY200@129.0222]}");
		bidBook.add(p0q1);
		System.out.println(bidBook);
		assertEquals(bidBook.toString(),"<PriceLevels>{129.0222=>[Lee:BUY200@129.0222]128.0000=>[Lee:BUY100@128.0000]}");
		bidBook.add(p0q2);
		System.out.println(bidBook);
		assertEquals(bidBook.toString(),"<PriceLevels>{129.0222=>[Lee:BUY200@129.0222]128.0000=>[Lee:BUY100@128.0000, Lee:BUY200@128.0000]}");
		bidBook.add(p0q3);
		System.out.println(bidBook);
		assertEquals(bidBook.toString(),"<PriceLevels>{129.0222=>[Lee:BUY200@129.0222]128.0000=>[Lee:BUY100@128.0000, Lee:BUY200@128.0000, Lee:BUY300@128.0000]}");
		bidBook.add(p1q1);
		System.out.println(bidBook);
		assertEquals(bidBook.toString(),"<PriceLevels>{129.0222=>[Lee:BUY200@129.0222]128.0011=>[Lee:BUY100@128.0011]128.0000=>[Lee:BUY100@128.0000, Lee:BUY200@128.0000, Lee:BUY300@128.0000]}");



	}
}
