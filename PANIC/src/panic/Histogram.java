package panic;

import java.util.ArrayList;
import java.util.List;

import utils.Stats;

/**
 * Generates histogram as a one-to-one map from List<Double> xIntervals to List<Double> yVals
 * 
 * @author Kingther
 *
 */
public class Histogram {
	private List<Double> _values;
	private int _numValues;
	private int _numBins;
	private double _width;
	private double _minVal;
	private double _maxVal;
	private ArrayList<Double> _xIntervals;
	private ArrayList<Double> _yVals;
	
	/**
	 * 
	 * @param values
	 * @param n
	 * @param numBins
	 * @param minVal
	 * @param maxVal
	 * @throws Exception
	 */
	public Histogram(List<Double> values, int numBins, double minVal, double maxVal) throws Exception {
		this._values = values;
		this._numValues =0;
		this._numBins = numBins;
		this._minVal = minVal;
		this._maxVal = maxVal;
		this._width = (maxVal - minVal) / numBins;
		
		// Initialize lists to store x intervals and y values 
		// Since we know the length, ArrayList can be the fastest even with add()
		_xIntervals = new ArrayList<Double>(numBins + 1);
		_yVals = new ArrayList<Double>(numBins);
		
		for (double i = 0; i < numBins; i++) {
			double x = minVal + i * _width;
			_xIntervals.add(x);
			_yVals.add(0.);
		}
		_xIntervals.add(maxVal);
		
		// Compute the _yVals if the values is not null
		if (values != null)
			for (double value:values) {
				feed(value);
				//System.out.println(value);
			}
		
	}
	
	
	/**
	 * This function feed value to compute yVals
	 * xIntervals are like [a, b), except the last one is [a, maxValue]
	 * 
	 * @param values values to be plotted in histogram
	 * @throws Exception
	 */
	private void feed(double value) throws Exception {
		// Check the value boundary
		if (value > _maxVal || value < _minVal)
			throw new Exception("Value is out of Boundary!");
		// Compute which X interval it belongs to
		int index = value < _maxVal? (int)Math.floor((value - _minVal) / _width) : _numBins - 1;		
		// Update the y Vals
		_yVals.set(index, _yVals.get(index) + 1);
		_numValues ++;
	}
	
	/**
	 * Update the Histogram by adding a value
	 * @param value
	 * @throws Exception
	 */
	public void add(double value) throws Exception{
		feed(value);
	}
	
	/**
	 * Update the Histogram by adding the same value num times
	 * @param value
	 * @throws Exception
	 */
	public void add(double value, int num) throws Exception{
		for(int i = 0; i<num;i++)
			feed(value);
	}
	
	/**
	 * Update the Histogram by adding a series of values
	 * @param values
	 * @throws Exception
	 */
	public void add(double[] values) throws Exception{
		for(double value : values)
			feed(value);
	}
	
	/**
	 * compute U by the formula in the paper
	 * If the Histogram is empty or an ideal one return -1
	 * @return
	 */
	public double computeU(int numStocks) {
		// If the Histogram is empty or an ideal one return -1
		if (_values == null){
			return -1;
		}
		
		
		double mean = 0.;
		double sqrMean = 0.;
		double variance = 0.;
		double n =_values.size();
		// Update the mean and sqrMean
		// This way can avoid overflow
		for (double value:_values) {
			mean += value/n;
			sqrMean += value * value/n;
		}
		// Compute the variance via mean and sqrMean
		variance = (sqrMean - mean)*(n/(n-1));
		// Note that the formula given by Lee is in fact wrong!
		double u = (0.25 - variance) * 0.5  / (variance - 0.25 / numStocks);
		return u;		
	}
	
	/**
	 * Return _xIntervals
	 * @return _xIntervals
	 */
	public ArrayList<Double> getXIntervals() {
		return _xIntervals;
	}
	
	public ArrayList<Double> getYVals() {
		return _yVals;
	}
	
	public ArrayList<Double> getYProbs() {
		ArrayList<Double> yProbs = new ArrayList<Double>(_numBins);
		for(int i = 0;i<_numBins;i++){
			yProbs.add(_yVals.get(i)/_numValues);
		}
		return yProbs;
	}
	
	/**
	 * Set the YVals
	 * @param YVals
	 */
	private void setYVals( ArrayList<Double> YVals) {
		_yVals = YVals;
	}
	
	/**
	 * Gets the YVal for a specified xVal
	 * @param xVal
	 * @return
	 */
	public double getYVal(double xVal){
		int index = xVal < _maxVal? (int)Math.floor((xVal - _minVal) / _width) : _numBins - 1;
		return _yVals.get(index);
	}
	
	
	/**
	 * Returns Ideal Histogram computed through the formula
	 * @param numStocks
	 * @return
	 * @throws Exception
	 */
	public Histogram getIdealHistogram(int numStocks) throws Exception {
		// Constructs a empty Histogram
		Histogram histogram = new Histogram(null, _numBins, _minVal, _maxVal) ;
		
		// Compute the Ideal YVals
		ArrayList<Double> idealYVals = getIdealYVals(numStocks);
		
		// Update the histogram with the YVals
		histogram.setYVals( idealYVals) ;
		
		return histogram;
	}
	/**
	 * Compute the getIdealYVals
	 * @param numStocks
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Double> getIdealYVals(int numStocks) throws Exception {
		// Compute the U
		int u = (int) Math.round(computeU(numStocks));
		
		// Use ArrayList here because we know the capacity
		ArrayList<Double> y = new ArrayList<Double>(_numBins);
		
		// Loops to compute the IdealY
		for (int i = 0; i < _numBins; i++) {
			double width = (_maxVal - _minVal) / _numBins;
			
			// Compute the x Values included in the X interval, and then times numStocks
			// xIntervals are like [a, b), except the last one is [a, maxValue]
			// So we should use floor
			int kl = (int) Math.floor((_minVal + i * width) * numStocks);
			int ku = (int) Math.floor((_minVal + (i + 1) * width) * numStocks);
			double y_i = 0;
			
			// Loops on the x Values to compute the sum of Y for the interval
			for (int k = kl; k < ku; k++) {
				
				// Compute the parameter
				double a = Stats.Combination(u + k - 1, k);
				double b = Stats.Combination(numStocks + u - k - 1, numStocks - k);
				double c = Stats.Combination(numStocks + u + u - 1, numStocks);
				double p = a * b / c;
				y_i += p;
			}
			
			// Record the Y values
			y.add(i,  y_i);			
		}
		
		return y;
	}
	

}
