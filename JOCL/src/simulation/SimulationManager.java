package simulation;


public class SimulationManager {
	private double _minCountVarRatio;
	private StatsCollector _statsCollector;
	private PayOutGenerator _payOutGenerator;
	
	/**
	 * Constructs a Simulator Manager
	 * @param option
	 * @param confidenceLevel confidence Level
	 * @param error error tolerance
	 */
	public SimulationManager(EuropeanOption option,double confidenceLevel, double error){
		_payOutGenerator = new PayOutGenerator(option);
		double z = normal_invcpf((confidenceLevel+1.)/2.)/(error+1e-7);
		_minCountVarRatio = z*z;
		_statsCollector = new StatsCollector();
	}
	/**
	 * simulate and compute the result
	 * @return
	 */
	public double simulate(){
		while(!isFinished()){
			float[][] payOuts = _payOutGenerator.getPayOut();
			_statsCollector.add(payOuts[0]);
			_statsCollector.add(payOuts[1]);
			System.out.println(getSummary());
		}
		return _statsCollector.getMean();
		
	}
	
	/**
	 * returns true if the simulation accuracy meets our demand
	 * @return
	 */
	public boolean isFinished(){
		if (_statsCollector.getCount()<=2)
			return false;
		return _statsCollector.getCount()/_statsCollector.getSampleVariance()>=_minCountVarRatio;
	}
	
	public void update(float[] Payouts){
		_statsCollector.addAll(Payouts);
	}
	public void update(float Payout){
		_statsCollector.add(Payout);
		//System.out.println(_statsRecorder);
	}
	
	public double getCount(){
		return _statsCollector.getCount();
	}

	
	public double getPrice(){
		return _statsCollector.getMean();
	}

	public String getSummary() {
		return String.format("After %d simulations, current price is %g, CountVarRatio/Target = %g / %g", _statsCollector.getCount(),_statsCollector.getMean(),_statsCollector.getCount()/(_statsCollector.getSampleVariance()+1e-5),_minCountVarRatio);
	}
	
	public String toString(){
		return String.format("After %d simulations, current price is %g", _statsCollector.getCount(),_statsCollector.getMean());
	}

	/**
	 * update the result by adding a batch of values with
	 * @param d
	 * @param _batchSize
	 */
	public void update(double sum_newvalues, double sumSqr_newvalues,int batchSize) {
		_statsCollector.addAll(sum_newvalues, sumSqr_newvalues, batchSize);
	}
	
	
	/**
	 * Computes the quantile function of this distribution with high accuracy
	 * @param p
	 * @return
	 * @throws Exception 
	 */
    public static double normal_invcpf(double p_) {
        if (p_ < 0.0){
        	System.err.println("OutOfRangeException");
        	return  Double.NEGATIVE_INFINITY;
        }
        else if(p_ > 1.0){
        	System.err.println("OutOfRangeException");
        	return  Double.POSITIVE_INFINITY;
        }

        double x = 2 * p_ - 1;
        double w = - Math.log((1.0 - x) * (1.0 + x));
        double p;
        if (w < 6.25) {
            w -= 3.125;
            p =  -3.6444120640178196996e-21;
            p =   -1.685059138182016589e-19 + p * w;
            p =   1.2858480715256400167e-18 + p * w;
            p =    1.115787767802518096e-17 + p * w;
            p =   -1.333171662854620906e-16 + p * w;
            p =   2.0972767875968561637e-17 + p * w;
            p =   6.6376381343583238325e-15 + p * w;
            p =  -4.0545662729752068639e-14 + p * w;
            p =  -8.1519341976054721522e-14 + p * w;
            p =   2.6335093153082322977e-12 + p * w;
            p =  -1.2975133253453532498e-11 + p * w;
            p =  -5.4154120542946279317e-11 + p * w;
            p =    1.051212273321532285e-09 + p * w;
            p =  -4.1126339803469836976e-09 + p * w;
            p =  -2.9070369957882005086e-08 + p * w;
            p =   4.2347877827932403518e-07 + p * w;
            p =  -1.3654692000834678645e-06 + p * w;
            p =  -1.3882523362786468719e-05 + p * w;
            p =    0.0001867342080340571352 + p * w;
            p =  -0.00074070253416626697512 + p * w;
            p =   -0.0060336708714301490533 + p * w;
            p =      0.24015818242558961693 + p * w;
            p =       1.6536545626831027356 + p * w;
        } else if (w < 16.0) {
            w = Math.sqrt(w) - 3.25;
            p =   2.2137376921775787049e-09;
            p =   9.0756561938885390979e-08 + p * w;
            p =  -2.7517406297064545428e-07 + p * w;
            p =   1.8239629214389227755e-08 + p * w;
            p =   1.5027403968909827627e-06 + p * w;
            p =   -4.013867526981545969e-06 + p * w;
            p =   2.9234449089955446044e-06 + p * w;
            p =   1.2475304481671778723e-05 + p * w;
            p =  -4.7318229009055733981e-05 + p * w;
            p =   6.8284851459573175448e-05 + p * w;
            p =   2.4031110387097893999e-05 + p * w;
            p =   -0.0003550375203628474796 + p * w;
            p =   0.00095328937973738049703 + p * w;
            p =   -0.0016882755560235047313 + p * w;
            p =    0.0024914420961078508066 + p * w;
            p =   -0.0037512085075692412107 + p * w;
            p =     0.005370914553590063617 + p * w;
            p =       1.0052589676941592334 + p * w;
            p =       3.0838856104922207635 + p * w;
        } else if (!Double.isInfinite(w)) {
            w = Math.sqrt(w) - 5.0;
            p =  -2.7109920616438573243e-11;
            p =  -2.5556418169965252055e-10 + p * w;
            p =   1.5076572693500548083e-09 + p * w;
            p =  -3.7894654401267369937e-09 + p * w;
            p =   7.6157012080783393804e-09 + p * w;
            p =  -1.4960026627149240478e-08 + p * w;
            p =   2.9147953450901080826e-08 + p * w;
            p =  -6.7711997758452339498e-08 + p * w;
            p =   2.2900482228026654717e-07 + p * w;
            p =  -9.9298272942317002539e-07 + p * w;
            p =   4.5260625972231537039e-06 + p * w;
            p =  -1.9681778105531670567e-05 + p * w;
            p =   7.5995277030017761139e-05 + p * w;
            p =  -0.00021503011930044477347 + p * w;
            p =  -0.00013871931833623122026 + p * w;
            p =       1.0103004648645343977 + p * w;
            p =       4.8499064014085844221 + p * w;
        } else {
            p = Double.POSITIVE_INFINITY;
        }

        return Math.sqrt(2) * p * x;
    }


}