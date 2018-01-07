package orderSpecs;

import orderTypes.RestingOrder;

public class Test_PriceLevels extends junit.framework.TestCase{

	public void test_PriceLevels(){
		MarketId marketId0 = new MarketId( "IBM" );
		ClientId clientId0 = new ClientId( "Lee" );
		ClientOrderId clientOrderId0 = new ClientOrderId( "ABC" );
		Side side0 = Side.BUY;
		Quantity quantity0 = new Quantity( 1000L );
		Price price0 = new Price( 1280000 );
		RestingOrder restingOrder = new RestingOrder(
				clientId0,
				clientOrderId0,
				marketId0,
				side0,
				quantity0,
				price0
			);
		PriceLevels pl = new PriceLevels();
		pl.put(restingOrder.getPrice(), restingOrder);
		System.out.println(pl.toString());
		
	}
}
