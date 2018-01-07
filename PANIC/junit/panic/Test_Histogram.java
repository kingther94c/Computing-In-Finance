package panic;

import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;

public class Test_Histogram extends TestCase {
		
	public void test() throws Exception{
		List<Double> data=new LinkedList<Double>();
		
		data.add(0.0);
		data.add(1.3);
		data.add(1.9);
		data.add(3.3);
		data.add(4.0);
		data.add(5.0);
		
		// Print Hist to screen and manually check
		// In this case 6 points fall into 5 intervals
		Histogram myHist=new Histogram(data,5,0.0,5.1);
		myHist.getYProbs();
		myHist.getXIntervals();
		
	}
	
	public void test_computeU() throws Exception{
		List<Double> data=new LinkedList<Double>();
		
		data.add(0.1);
		data.add(0.3);
		data.add(0.9);
		data.add(0.3);
		data.add(0.2);
		data.add(0.2);
		
		// Print Hist to screen and manually check
		// In this case 6 points fall into 5 intervals
		Histogram myHist=new Histogram(data,5,0.0,1.0);
		myHist.computeU(6);
		
	}

}
