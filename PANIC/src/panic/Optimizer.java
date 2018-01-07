package panic;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import utils.Pair;
import utils.Stats;
/**
 * We want to predict the future distribution for up ratios using 
 * the ideal histogram computed through data of lookback days and
 * optimize the lookback parameter
 * 
 * @author Kingther
 *
 */
public class Optimizer {
	
	private int _parameterStart;
	private int _parameterStep;
	private int _parameterEnd;
	private QuotesReader _reader;
	/**
	 * Constructs a Optimizer
	 * @param reader the reader of DB
	 * @param parameterStart parameterStart
	 * @param parameterStep parameterStep
	 * @param parameterEnd parameterEnd
	 * @throws Exception 
	 */
	public Optimizer(QuotesReader reader, int parameterStart,int parameterStep,int parameterEnd) throws Exception{
		if (parameterStart>parameterEnd || parameterStep <=0)
			throw new Exception();
		_parameterStart =  parameterStart;
		_parameterStep = parameterStep;
		_parameterEnd = parameterEnd;
		_reader =  reader;
	}

	
	/**
	 * optimal parameter
	 * @return optimal parameter
	 * @throws Exception
	 */
	public int optimize() throws Exception{
		// Construct a calendar to adjust date
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");		
		Calendar c = Calendar.getInstance();
		
		// We also want to find the optimal lookback which minimizes MSE
		int optimal_lookback = _parameterStart;
		// Output histograms for year 2008
		
		// Create start date
		Date startDate = dateFormat.parse("20080101");
		c.setTime(startDate); 
		c.add(Calendar.YEAR, 1);
			
		// Get end date
		Date endDate = c.getTime();
			
		// Read data in specific time period
		_reader.readData(startDate, endDate);
			
		// Compute UpRatios And NumStock
		Pair<List<Double>, List<Integer>> p = _reader.computeUpRatiosAndNumStocks();
			
		// Constructs a histogram with x axis starting from 0 to 1 with 10 bins
		Histogram h = new Histogram(p.getLeft(), 10, 0, 1);
		List<Double> yTarget = h.getYProbs();
			
			
		// Optimization
		// From the end of the year 2008, we try to find the optimal parameter
		// of daysLookback to predict the really Y value
		double minMSE = Double.MAX_VALUE;
		
		// Search form 20 to 365 with step of 5
		for (int j = _parameterStart; j < (_parameterEnd+1); j+=_parameterStep) {
			
			// change startDate and endDate
			endDate = dateFormat.parse("20080101");
			c.setTime(endDate); 
			c.add(Calendar.DATE, -_parameterStep);
			startDate = c.getTime();
			
			// Reload data
			_reader.readData(startDate, endDate);
			
			// Compute UpRatios And NumStock
			Pair<List<Double>, List<Integer>> p_ = _reader.computeUpRatiosAndNumStocks();
			int numStocks_ = (int)Math.round(Stats.computeMean( p.getRight()));
			
			// Create a temporary histogram to calculate Ideal YVals
			Histogram temp = new Histogram(p_.getLeft(), 10 ,0, 1);
			List<Double> y = temp.getIdealYVals(numStocks_);
			
			// Calculate MSE between y and yhat
			double MSE = ComputeMSE(y, yTarget);
			if (MSE < minMSE) {
				// Store the min MSE and corespond lookback days
				minMSE = MSE;
				optimal_lookback = j;
			}
		}
		// Print result
		System.out.println("The optimal lookback is :" + optimal_lookback);

		return optimal_lookback;
	}
	
	
	/**
	 * This function is used to calculate the MSE, used 
	 * to quantify the goodness of fitting 
	 * @param y The true values
	 * @param yhat The fitting values
	 * @return
	 * @throws Exception
	 */
	private static double ComputeMSE (List<Double> y, List<Double> yhat) throws Exception {
		double mse = 0;
		if (y.size() != yhat.size())
			throw new Exception("Invalid input list!");
		for (int i = 0 ; i < y.size(); i++) {
			mse += (y.get(i) - yhat.get(i)) * (y.get(i) - yhat.get(i)) / y.size();
		}
		return mse;
	}
	
}
