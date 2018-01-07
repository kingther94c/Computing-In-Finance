Written in MAC (Unicode-utf-8)
by Kelvin (Jinze) Chen, Financial Mathematics, Courant Institute
========================
——————About the design———
QuotesReader:  
* Read data into linked list of Price record object;
* Can read data flexibly including skip to some date or read back to some date
* Calculate change of price for each stock on each date and aggregate the result of the up ratio and stock numbers for each date into a Pair<List<Double>,List<Integer>>.

Histogram: 
* Construct a historical Histogram for input data
* Can construct a ideal Histogram based on a historical Histogram
* Essentially be a one-to-one map between x (up ratio) interval and y values (frequency)
* Can compute the corresponding y value given a species x.

Optimizer:
* Compute the MSE between real value and predicted value
* Predict the future distribution for up ratios using the ideal histogram computed through data of lookback days and optimize the lookback parameter by comparing MSE



———How to run——-
// Problem 1.
// Make a historical Histogram and an ideal Histogram

	QuotesReader reader = new QuotesReader("/Users/kingther/Documents/workspace/Panic/testUpRatio.csv");
	Date startDate = reader._dateFormat.parse("20070101”);
	Date endDate = reader._dateFormat.parse("20080101”);
	reader.readData(startDate, endDate);
	Pair<List<Double>, List<Integer>> p_ = _reader.computeUpRatiosAndNumStocks();
	int numStocks_ = (int)Math.round(Stats.computeMean( p.getRight()));
			

	Histogram historicalHistogram = new Histogram(p_.getLeft(), 10 ,0, 1);
	Histogram idealHistogram = historicalHistogram.getIdealHistogram(numStocks_);

// Problem 2.
// Predict the future distribution for up ratios using the ideal histogram computed through data of lookback days and optimize the lookback parameter by comparing MSE.

	int parameterStart =  20;
	int parameterStep = 5;
	int parameterEnd = 365;
	QuotesReader reader =  new QuotesReader("/Users/kingther/Documents/workspace/Panic/panicData.csv");
	Optimizer op = new Optimizer(reader, parameterStart,parameterStep,parameterEnd);
	op.optimize();

// the result : 
// optimal lookback = 235
Thus the optimal lookback parameter is 235