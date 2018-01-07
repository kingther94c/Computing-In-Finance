package simulation;

public class StatsCollector {
	private double mean=0;  // mean of value
	private double meanSqr=0; 	// mean of squared value
	private int count=0;
	public StatsCollector(){	
	}
	/**
	 * add a batch of payouts
	 * @param payouts new value to be recorded
	 */
	public void addAll(float[] payouts){
		double sum_newvalues = 0.;
		double sumSqr_newvalues  = 0.;
		for(int i = 0;i<payouts.length;i++){
			sum_newvalues += payouts[i];
			sumSqr_newvalues  += payouts[i]*payouts [i];
		}
		addAll(sum_newvalues, sumSqr_newvalues,payouts.length);
	}
	
	/**
	 * add a batch of values
	 * @param payouts new value to be recorded
	 */	
	public void add(float[] values){
		for(int i = 0;i<values.length;i++){
			add(values[i]);
		}
		
	}	
	/**
	 * add a value
	 * @param newvalue new value to be recorded
	 */	
	public void add(double newvalue){
		if(Double.isInfinite(newvalue)){
			return;
		}
		mean = count/(count+1.)*mean + newvalue/(count+1.);
		meanSqr = count/(count+1.)*meanSqr + newvalue*newvalue/(count+1.);
		count++;

	}
	
	@Override
	public String toString() {
		return "StatsRecorder [mean=" + mean + ", meanSqr=" + meanSqr + ", count=" + count + "]";
	}
	/**
	 * returns the mean
	 * @return mean
	 */
	public double getMean(){
		return mean;
	}
	/**
	 * returns the sample std
	 * @return sample std
	 */	
	public double getSampleStd(){
		return Math.sqrt((meanSqr-mean*mean)*(count)/(count-1));
	}
	/**
	 * returns the variance
	 * @return  variance
	 */	
	public double getVariance(){
		return meanSqr-mean*mean;
	}
	/**
	 * returns the sample variance
	 * @return sample variance
	 */	
	public double getSampleVariance(){
		return (meanSqr-mean*mean)*(count)/(count-1);
	}
	/**
	 * returns the std
	 * @return std
	 */	
	public double getStd(){
		return Math.sqrt(meanSqr-mean*mean);
	}
	/**
	 * returns the count
	 * @return count
	 */	
	public int getCount(){
		return count;
	}
	/**
	 * add a batch of values by input the sum of values and sum of squared values
	 * @param sum_newvalues the sum of values 
	 * @param sumSqr_newvalues sum of squared values
	 * @param batchSize size of the batch
	 */
	public void addAll(double sum_newvalues, double sumSqr_newvalues, int batchSize) {
		mean = mean*count/(count+batchSize) + sum_newvalues/(count+batchSize);
		meanSqr = meanSqr*count/(count+batchSize) + sumSqr_newvalues/(count+batchSize);
		count = count+batchSize;
	}
}
