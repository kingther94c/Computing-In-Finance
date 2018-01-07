package utils;


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
		if(this._left!=null && this._right!=null)
			return "<"+this._left.toString() +","+this._right.toString()+">";
		else if(this._left==null && this._right!=null)
			return "<null,"+this._right.toString()+">";
		else if(this._left!=null && this._right==null)
			return "<"+this._left.toString() +",null>";
		else
			return "<null,null>";
		
	}

}