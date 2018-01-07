package simulation;


public class Pair<Type1,Type2> {
	
	private Type1 _left;
	private Type2 _right;
	
	public Pair( Type1 left, Type2 right ){
		_left = left;
		_right = right;
	}
	
	public Type1 getLeft() { return _left; }
	
	public Type2 getRight() { return _right; }
	
	public void setRight(Type2 right) {_right=right;}
	
	public void setLeft(Type1 left) {_left=left;}
		
	public int hashCode(){
		return 31*_left.hashCode()+_right.hashCode();
	}
	public boolean equals(Object o){
		if(! (o instanceof Pair<?,?>))
			return false;
		Pair<?, ?> p = (Pair<?, ?>) o;
		if (p._left.equals(_left)&&(p._right.equals(_right)))
			return true;
		else 
			return false;
		
	}
	public String toString(){
		StringBuffer str = new StringBuffer ("<");
		str.append(_left.toString());
		str.append(",");
		str.append(_right.toString());
		str.append(">");
		return str.toString();
	}

}