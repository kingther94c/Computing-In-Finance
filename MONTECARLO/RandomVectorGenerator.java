package simulation;

public interface RandomVectorGenerator {
    /**
     * @return The next random vector
     */
	public double[] getNextVector();
	/**
	 * 
	 * @param dim
	 * @return The next random vector
	 */
	public double[] getNextVector(int dim);
}
