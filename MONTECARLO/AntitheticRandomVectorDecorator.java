package simulation;

public class AntitheticRandomVectorDecorator implements RandomVectorGenerator{
	//To store the given RandomVectorGenerator
	private RandomVectorGenerator _inner;
	//To indicate which step the generator in during the antithetic process
	private Boolean _flag = false;
	//To store the vector in the last step
	private double[] _prevVector;
	

	@Override
	public double[] getNextVector() {
		if(_flag){
			_flag = false;
			_prevVector = AntitheticVector(_prevVector);	
		}
		else{
			_prevVector = _inner.getNextVector();
			_flag = true;
		}
		
		return _prevVector;
	}
	
	@Override
	public double[] getNextVector(int dim) {
		if(_flag){
			_flag = false;
			_prevVector = AntitheticVector(_prevVector);	
		}
		else{
			_prevVector = _inner.getNextVector(dim);
			_flag = true;
		}
		
		return _prevVector;
	}
	
	/**
	 * Construct an AntitheticRandomVectorDecorator
	 * @param generator
	 */
	public AntitheticRandomVectorDecorator(RandomVectorGenerator generator) {
		this._inner = generator;
	}
	
	/**
	 * Get the Anti-thetic Vector
	 * @param vector
	 * @return
	 */
	protected double[] AntitheticVector(double[] vector){
		double[] vector_ = new double[vector.length];
		for(int i = 0; i<vector.length; i++){
			vector_[i]  = -1.*vector[i];
		}
		return vector_;
	}
	
}
