package middleware;
import simulation.*;

public class SimulatorManager {
	private double _minCountVarRatio;
	private StatsRecorder _statsRecorder;
	
	
	
	/**
	 * Constructs a Simulator Manager
	 * @param option
	 * @param confidenceLevel confidence Level
	 * @param error error tolerance
	 */
	public SimulatorManager(double confidenceLevel, double error){
		double z = StandardNormalDistributionStat.inverseCumulativeProbability((confidenceLevel+1.)/2.)/(error+1e-7);
		_minCountVarRatio = z*z;
		_statsRecorder = new StatsRecorder();
	}

	/**
	 * returns true if the simulation accuracy meets our demand
	 * @return
	 */
	public boolean isFinished(){
		if (_statsRecorder.getCount()<=2)
			return false;
		return _statsRecorder.getCount()/_statsRecorder.getSampleVariance()>=_minCountVarRatio;
	}
	
	public void update(double[] Payouts){
		_statsRecorder.addAll(Payouts);
	}
	public void update(double Payout){
		_statsRecorder.add(Payout);
		//System.out.println(_statsRecorder);
	}
	
	public double getCount(){
		return _statsRecorder.getCount();
	}

	
	public double getPrice(){
		return _statsRecorder.getMean();
	}

	public String getSummary() {
		return String.format("After %d simulations, current price is %g, CountVarRatio/Target = %g / %g", _statsRecorder.getCount(),_statsRecorder.getMean(),_statsRecorder.getCount()/_statsRecorder.getSampleVariance(),_minCountVarRatio);
	}
	
	public String toString(){
		return String.format("After %d simulations, current price is %g", _statsRecorder.getCount(),_statsRecorder.getMean());
	}

	/**
	 * update the result by adding a batch of values with
	 * @param d
	 * @param _batchSize
	 */
	public void update(double sum_newvalues, double sumSqr_newvalues,int batchSize) {
		_statsRecorder.addAll(sum_newvalues, sumSqr_newvalues, batchSize);
	}
	
	


}