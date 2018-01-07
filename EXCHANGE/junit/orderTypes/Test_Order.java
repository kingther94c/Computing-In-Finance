package orderTypes;

import orderSpecs.ClientId;
import orderSpecs.ClientOrderId;
import orderSpecs.MarketId;
import orderSpecs.Price;
import orderSpecs.Quantity;
import orderSpecs.Side;

public class Test_Order extends junit.framework.TestCase{

	public void test_Order(){
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
		System.out.println(restingOrder);

	}
}
