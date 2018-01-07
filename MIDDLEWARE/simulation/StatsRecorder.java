package simulation;

public class StatsRecorder {
	private double mean=0;  // mean of value
	private double meanSqr=0; 	// mean of squared value
	private int count=0;
	public StatsRecorder(){	
	}
	
	public void addAll(double[] newvalues){
		double sum_newvalues = 0.;
		double sumSqr_newvalues  = 0.;
		for(int i = 0;i<newvalues.length;i++){
			sum_newvalues += newvalues[i];
			sumSqr_newvalues  += newvalues[i]*newvalues [i];
		}
		addAll(sum_newvalues, sumSqr_newvalues,newvalues.length);
	}
	public void add(double newvalue){
		mean = mean*count/(count+1.) + newvalue/(count+1.);
		meanSqr = meanSqr*count/(count+1.) + newvalue*newvalue/(count+1.);
		count++;

	}
	
	@Override
	public String toString() {
		return "StatsRecorder [mean=" + mean + ", meanSqr=" + meanSqr + ", count=" + count + "]";
	}

	public double getMean(){
		return mean;
	}
	
	public double getSampleStd(){
		return Math.sqrt((meanSqr-mean*mean)*(count)/(count-1));
	}
	
	public double getVariance(){
		return meanSqr-mean*mean;
	}
	
	public double getSampleVariance(){
		return (meanSqr-mean*mean)*(count)/(count-1);
	}
	
	public double getStd(){
		return Math.sqrt(meanSqr-mean*mean);
	}
	
	public int getCount(){
		return count;
	}

	public void addAll(double sum_newvalues, double sumSqr_newvalues, int batchSize) {
		mean = mean*count/(count+batchSize) + sum_newvalues/(count+batchSize);
		meanSqr = meanSqr*count/(count+batchSize) + sumSqr_newvalues/(count+batchSize);
		count = count+batchSize;
	}
}
