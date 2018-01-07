package utils;

import java.util.List;

public class Stats  
{   
	/**
	 * Returns the Permutation number
	 * @param up
	 * @param bellow
	 * @return
	 * @throws Exception 
	 */
	public static int Permutation(int below, int up) throws Exception  
	{  
		int result=1;  
		// Check the boundary condition
		if (below<0 || up>below){
			throw new Exception("Invalid Input");
		}
		for(int i=up;i>0;i--)  
		{  
			result*=below;  
			below--;  
		}  
		return result;  
	}  
	/**
	 * Returns the Combination number
	 * @param up
	 * @param below
	 * @return
	 * @throws Exception 
	 */
	public static int Combination(int below, int up) throws Exception
	{  
		// Check the boundary condition
		if (below<0 || up>below){
			throw new Exception("Invalid Input");
		}
		int helf=below/2;
		// Compare the up to below
		if(up>helf)  {  
			// Use the property to cut down computation
			up=below-up; 
		}  
		int denominator=Permutation(up,up);
		int numerator=Permutation(below,up);
		return numerator/denominator;  
        
	}  
	/**
	 * Computes the mean for a List<? extends Number>
	 * @param numberList
	 * @return
	 */
	public static Double computeMean(List<? extends Number> numberList){
		double sum = 0;
		for (Number n : numberList){
			sum += n.doubleValue();
		}
		return sum/numberList.size();
	}
}  
