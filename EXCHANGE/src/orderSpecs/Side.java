package orderSpecs;

public class Side {

	public static final Side BUY = new Side("BUY");
	public static final Side SELL = new Side("SELL");
	private String _side;
	private Side(String side){_side =side;}
	public Side getOppositeSide(){
		if(this==BUY)
			return SELL;
		else if(this==SELL)
			return BUY;
		else
			return null;
	}
	
	public String toString(){
		return _side;
	}
	
}
