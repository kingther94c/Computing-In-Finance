
Written in MAC (Unicode-utf-8)
by Kelvin (Jinze) Chen, Financial Mathematics, Courant Institute
========================
****How To Use****
————————————————————————————————————————————————
	// Create an exchange and add a market
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

	// Sweep the exchange with a order
	exchange.sweep( sweepingOrder );

	// Cancel a order
	exchange.cancel( new Cancel( clientId0, clientOrderId0 ) );

	// Check Communications
	exchange.getComms().getFills();
	exchange.getComms().getRestingOrderConfirmations();
	exchange.getComms().getCancelationConfirmations();
	exchange.getComms().getCancelRejections();


****Major DATA Structure Choice****
————————————————————————————————————————————————
>PriceLevels : tree map + linked list

PriceLevels is a TreeMap with the Keys of price and the Value of a LinkedList of Resting Orders. When adding a RestingOrder with an existing key, value will be added to a linkedlist<RestingOrder> for this Key.
In average, It takes O(logn) to find a price level,  O(logn) to insert a new order.

>Sweep()
To Sweep is essentially in-order walk in the binary tree (TreeMap) with some extra conditional statements. (And it will clean the orders with 0 quantity resulted from cancel())
In average, It takes O(n) to sweep.

Specially, my sweep will clean the null price level at the time it occurs, which is slightly different from Lee’s unit test.

>Cancel()
Each order book has a HashMap<ClientOrderId,RestingOrder>, this map keeps track of existing resting orders(once a resting order is cancelled,it is removed from the map, to make sure it can’t be cancelled twice).
In average, it takes O(1) to find the restingOrder and set its quantity to 0.

****Main Structure(fields and non-trivial public methods of main class)****
——————————————————————————————————————————————————————————————————————————————————
>Exchange
	private HashMap<MarketId,Market> _marketMap;
	protected Comms _comms;

>Market
	private Exchange _exchange;
	private MarketId _marketId;
	private OfferBook _offerBook;
	private BidBook _bidBook;
	public Pair<RestingOrder,Fills> sweep(SweepingOrder sweepingOrder)
	public RestingOrder getOrder(ClientOrderId clientOrderId)
	public boolean cancelOrder(ClientOrderId clientOrderId) 

>Book
	protected PriceLevels _priceLevels;
	protected HashMap<ClientOrderId,RestingOrder> _restingOderMap;

	public void add(RestingOrder order)
	public RestingOrder removeTopOrder()	
	public boolean remove(RestingOrder order)
	public boolean isEmpty()
	public abstract Pair<RestingOrder,Fills>  sweep(SweepingOrder sweepingOrder);
	public abstract boolean isSweepable(SweepingOrder sweepingOrder);
	public RestingOrder getOrder(ClientOrderId clientOrderId)	
	public boolean cancelOrder(ClientOrderId clientOrderId)
	protected Pair<RestingOrder,Fills>  sweep(SweepingOrder sweepingOrder, Side bookSide)


>OfferBook extend Book
	protected PriceLevels _priceLevels;
	protected HashMap<ClientOrderId,RestingOrder> _restingOderMap;
	private Comparator<? super Price> comparator

	public void add(RestingOrder order)
	public RestingOrder removeTopOrder()	
	public boolean remove(RestingOrder order)
	public boolean isEmpty()
	public abstract Pair<RestingOrder,Fills>  sweep(SweepingOrder sweepingOrder);
	public abstract boolean isSweepable(SweepingOrder sweepingOrder);
	public RestingOrder getOrder(ClientOrderId clientOrderId)	
	public boolean cancelOrder(ClientOrderId clientOrderId)
	protected Pair<RestingOrder,Fills>  sweep(SweepingOrder sweepingOrder, Side bookSide)
	public boolean isSweepable(SweepingOrder sweepingOrder)
	public Pair<RestingOrder,Fills>  sweep(SweepingOrder sweepingOrder)

>BidBook extend Book
	protected PriceLevels _priceLevels;
	protected HashMap<ClientOrderId,RestingOrder> _restingOderMap;
	private Comparator<? super Price> comparator
	public boolean isSweepable(SweepingOrder sweepingOrder)
	public Pair<RestingOrder,Fills>  sweep(SweepingOrder sweepingOrder)

	public void add(RestingOrder order)
	public RestingOrder removeTopOrder()	
	public boolean remove(RestingOrder order)
	public boolean isEmpty()
	public abstract Pair<RestingOrder,Fills>  sweep(SweepingOrder sweepingOrder);
	public abstract boolean isSweepable(SweepingOrder sweepingOrder);
	public RestingOrder getOrder(ClientOrderId clientOrderId)	
	public boolean cancelOrder(ClientOrderId clientOrderId)
	protected Pair<RestingOrder,Fills>  sweep(SweepingOrder sweepingOrder, Side bookSide)
	public boolean isSweepable(SweepingOrder sweepingOrder)
	public Pair<RestingOrder,Fills>  sweep(SweepingOrder sweepingOrder)

>Order
	private ClientId _clientId;
	private ClientOrderId _clientOrderId;
	private MarketId _marketId;
	private Side _side;
	private Quantity _quantity;
	private Price _price;


>Comms
	private Fills _fills;
	private LinkedList<RestingOrderConfirmation> _restingOrderConfirmations;
	private LinkedList<CancelationConfirmation> _cancelationConfirmations;
	private LinkedList<CancelRejected> _cancelRejections;

	public void addCancelationConfirmation(CancelationConfirmation cancelationConfirmation) 
	public void addCancelRejection(CancelRejected cancelRejection)
	


	






