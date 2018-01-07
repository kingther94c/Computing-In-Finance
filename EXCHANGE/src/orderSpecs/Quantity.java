package orderSpecs;

@SuppressWarnings("rawtypes")
public class Quantity implements Comparable{

	private long _quantity;
	public Quantity(long quantity) {
		_quantity = quantity;
	}
	public long getValue(){return _quantity;}
	
	public void minus(Quantity quantity){
		_quantity = _quantity - quantity._quantity;
	}
	
	@Override
	public int compareTo(Object o) {
		if(!(o instanceof Quantity))
			throw new ClassCastException();
		Quantity q = (Quantity) o;
		return (int) (this._quantity-q._quantity);
	}
	public int compareTo(int q) {
		return (int) (this._quantity-q);
	}
	public int compareTo(long q) {
		return (int) (this._quantity-q);
	}
	public String toString(){
		return Long.toString(_quantity);
	}
	public int hashCode(){
		return (int) _quantity;
	}
	public boolean equals(Object o){
		if(!(o instanceof Quantity))
			return false;
		return this._quantity==((Quantity) o)._quantity;
	}
	public boolean equals(int i){
		return this._quantity==i;
	}

	public boolean equals(long i){
		return this._quantity==i;
	}
	public void setValue(int i) {
		_quantity = i;
		
	}
	public void setValue(long i) {
		_quantity = i;
		
	}

}
