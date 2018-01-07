package cluster;

public class DoubleComparator {
	/**
	 * 
	 * @param d1
	 * @param d2
	 * @param tolerance
	 * @return
	 */
	public static int compare( double d1, double d2, double tolerance ) {
		double diff = d1 - d2;
		if( Math.abs( diff ) <= tolerance )
			return( 0 );
		return( (int) Math.signum( diff ) );
	}
	public static int compare( double d1, double d2) {
		return compare(d1,d2,1e-4);
	}

}