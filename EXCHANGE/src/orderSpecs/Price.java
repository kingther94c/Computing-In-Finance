package orderSpecs;

public class Price implements Comparable<Price>{

	private int _price;
	public Price(int price) {
		_price = price;
	}
	public Price(long price) {
		_price = (int) price;
	}
	public int getValue(){return _price;}
	@Override
	public int compareTo(Price p) {
		return this._price-p._price;
	}
	public String toString(){
		StringBuffer str = new StringBuffer();
		str.append(_price/10000);
		str.append(".");
		str.append(String.format("%04d", _price%10000));
		return str.toString();
	}

	public int hashCode(){
		return _price;
	}

	public boolean equals(Object o){
		if(!(o instanceof Price))
			return false;
		Price p = (Price) o ;
		return this._price==p._price;
		
	}

}
