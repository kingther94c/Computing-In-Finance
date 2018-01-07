package simulation;

import static org.jocl.CL.CL_DEVICE_TYPE_GPU;
import static org.jocl.CL.clGetDeviceIDs;
import static org.jocl.CL.clGetPlatformIDs;


import org.jocl.cl_device_id;
import org.jocl.cl_platform_id;


public class Test_PayOutGenerator extends junit.framework.TestCase{

	static EuropeanOption opt = getOption();
	static EuropeanOption opt2 = getOption2();

	public static EuropeanOption getOption() {
		String optionName0 = "TestEuropeanCall";
		float volatility = 2f;
		float interestRate = 1;
		float time = 1f;
		float spotPrice = 1f;
		float strikePrice0 = 0f;
		try{
			return new EuropeanOption(optionName0,volatility, interestRate, time, spotPrice, strikePrice0, "Call");
		}
		catch(Exception e){
			return null;
		}
		
	}
	public static EuropeanOption getOption2() {
		String optionName0 = "TestEuropeanCall";
		float volatility = 2f;
		float interestRate = 1;
		float time = 1f;
		float spotPrice = 1f;
		float strikePrice0 = 1f;
		try{
			return new EuropeanOption(optionName0,volatility, interestRate, time, spotPrice, strikePrice0, "Call");
		}
		catch(Exception e){
			return null;
		}
		
	}
	public void test_device(){
        System.out.println("[test_javacl]");
        cl_platform_id[] platforms = new cl_platform_id[1];
        clGetPlatformIDs(1, platforms,null);
        cl_platform_id platform = platforms[0];
        cl_device_id[] devices = new cl_device_id[1];
        clGetDeviceIDs(platform,  CL_DEVICE_TYPE_GPU, 1, devices, null);
        System.out.println(devices);
        cl_device_id device = devices[0];
        System.out.println(device.toString());

	}
	public void test_GenerateFinalPrices(){
		PayOutGenerator pg = new PayOutGenerator(opt);
		float[][] finalPrices2 = pg.getPayOut();
		for(int i = 0;i<10;i++){
			System.out.println(finalPrices2[0][1000*i] +"," +finalPrices2[1][1000*i]);
		}
	}
	//test the final result
	public void test_GenerateFinalPrices_stage4(){
		PayOutGenerator pg = new PayOutGenerator(opt);
		float[][] finalPrices2 = pg.getPayOut();
		StatsCollector statsCollector = new StatsCollector();
		statsCollector.add(finalPrices2[0]);
		statsCollector.add(finalPrices2[1]);
		System.out.println(statsCollector.getMean());
		assertEquals(statsCollector.getMean(),1.00,0.01);
	}
	/**
	 * To test the following parts, we need to comment some lines in the source part
	 */
//	//test the result before discount	
//	public void test_GenerateFinalPrices_stage3(){
//		PayOutGenerator pg = new PayOutGenerator(opt);
//		float[][] finalPrices2 = pg.getPayOut();
//		StatsCollector statsCollector = new StatsCollector();
//		statsCollector.add(finalPrices2[0]);
//		statsCollector.add(finalPrices2[1]);
//		System.out.println(statsCollector.getMean());
//		assertEquals(statsCollector.getMean(),2.73,0.03);
//	}
//	public void test_GenerateFinalPrices2_stage3(){
//		PayOutGenerator pg = new PayOutGenerator(opt2);
//		float[][] finalPrices2 = pg.getPayOut();
//		StatsCollector statsCollector = new StatsCollector();
//		statsCollector.add(finalPrices2[0]);
//		statsCollector.add(finalPrices2[1]);
//		System.out.println(statsCollector.getMean());
//		assertEquals(statsCollector.getMean(),2.22,0.03);
//	}
//	//test the result for final day price
//	public void test_GenerateFinalPrices_stage2(){
//		PayOutGenerator pg = new PayOutGenerator(opt);
//		float[][] finalPrices2 = pg.getPayOut();
//		StatsCollector statsCollector = new StatsCollector();
//		statsCollector.add(finalPrices2[0]);
//		statsCollector.add(finalPrices2[1]);
//		assertEquals(statsCollector.getMean(),2.70,0.03);
//	}
//	//test the result for normal vector
//	public void test_GenerateFinalPrices_stage1(){
//		PayOutGenerator pg = new PayOutGenerator(opt);
//		float[][] finalPrices2 = pg.getPayOut();
//		StatsCollector statsCollector = new StatsCollector();
//		statsCollector.add(finalPrices2[0]);
//		statsCollector.add(finalPrices2[1]);
//		System.out.println(statsCollector.getMean());
//		assertEquals(statsCollector.getMean(),0,0.01);
//		assertEquals(statsCollector.getSampleVariance(),1,0.01);
//		int positive = 0;
//		for(int i = 0; i < 1000000;i++){
//			if (finalPrices2[1][i]>0.)
//				positive++;
//			if (finalPrices2[0][i]>0.)
//				positive++;			
//		}
//		assertEquals(positive/1000000.,1,0.01);
//	}
}
