package simulation;


public class NormalRandomVectorGenerator implements RandomVectorGenerator{
	NormalRandomVariableGenerator _RVG;
	/**
	 * Constructs a RandomVectorGenerator with a RandomVariableGenerator, which generates each entry in the vector.
	 * @param seed : Seed for NormalRandomVariableGenerator
	 */
	public NormalRandomVectorGenerator(long seed){
		_RVG = new NormalRandomVariableGenerator(seed);
	}
	/**
	 * Constructs a RandomVectorGenerator with a RandomVariableGenerator, which generates each entry in the vector.
	 */
	public NormalRandomVectorGenerator(){
		_RVG = new NormalRandomVariableGenerator();
	}
	/**
	 * Return a 1-dimensional RandomVectorGenerator with a RandomVariableGenerator, which generates each entry in the vector.
	 * @return : a 1-dimensional Random Vector
	 */
	public double[] getNextVector(){
		return new double[] {_RVG.getNextNumber()};
	}
	/**
	 * Return a dim-dimensional RandomVectorGenerator with a RandomVariableGenerator, which generates each entry in the vector.
	 * @param dim : dimensionality of the Random Vector
	 * @return : a dim-dimensional Random Vector
	 */
	public double[] getNextVector(int dim){
		double[] vector = new double[dim];
		for(int i = 0; i < dim; i++)
			vector[i] = _RVG.getNextNumber();
		return vector;
	}
}