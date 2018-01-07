Summer Assignment
Written in MAC (Unicode-utf-8)
by Kelvin (Jinze) Chen, Financial Mathematics, Courant Institute
========================
How To Use
————————————————————————
The original problem (a collections of Points) can be any instance of Collection<? extends Point>. And you need to input 2 parameters when constructing the KMeans instance to specify what is your task and another 2 parameters when solving the task to tell the level of tolerance of convergence.

/**
 * Constructor Method
 * @param pointList : a collections of Points that need to be clustered.
 * @param numCluster : the number of clusters that you want to get.
 * @throws Exception
 */
	KMeans(Collection<? extends Point> pointList, int numCluster) throws Exception

/**
 * a method used directly to solve the problem
 * @param itr_limit : the maximal times of iteration for this algorithm
 * @param eps : the level of tolerance of convergence. When the relative difference of cost is less than the eps, the algorithm can be considered as convergence and will return the result.
 * @return an ArrayList<Centroid> which is a list of the Centroids of the final clusters.
 * @throws Exception
 */
	public ArrayList<Centroid> solve(int itr_limit, double eps) 

/**
* Measures whether the solution is good enough. The smaller the value is, the better the solution is.
* @return The cost value of the current solution. The cost value is defined as the average distance between the point and the centroid of the cluster that the point belongs to.
*/
	public double getCost() {return _cost;}


Here is a example if you have a original problem input as pointSet and want to apply KMeans2 to cluster the points in the pointSet

//firstly we construct a KMeans to describe the problem that we need to solve
km = new KMeans2(pointSet,input_numCluster);

//And then use solve (or solve_showcost if you want to print out the cost function for every steps) method to solve the problem at a specified tolerance level
ArrayList<Centroid> answer = km.solve_showcost(10000,0.00000001);

//If wanted, you could use getCost method to check whether the result is good enough.
System.out.println(km.getCost)



Structure
————————————————————————
>Interface 
>>IDistance<T>

>Class
>>Point implements IDistance<Point>
	protected double[] _x;
	protected int _dim;

>>Centroid extends Point
	protected double[] _x;
	protected int _dim;

>>Cluster extends LinkedList<Point>

>>Pair<Type1,Type2> 
	private Type1 _left;
	private Type2 _right;
>>Fitness

>>KMeans
	protected Collection<? extends Point> _pointList;
	protected int _numCluster;
	protected ArrayList<Cluster> _clusterList;
	protected ArrayList<Centroid> _centroidList;
	double _cost;
	double _costDelta;	

>>KMeans2 extends KMeans
	protected Collection<? extends Point> _pointList;
	protected int _numCluster;
	protected ArrayList<Cluster> _clusterList;
	protected ArrayList<Centroid> _centroidList;
	double _cost;
	double _costDelta;

Result
————————————————————————
To solve Lee’s problem with KMeans2, the Method should be as follows:
	
	public static LinkedList<Point> lee_pointSet() throws Exception{
		LinkedList<Point> pointSet = new LinkedList<Point>();
		for(int i=0;i<100;i++)
			for(int j=0;j<100;j++){
				double[] x = {i,j};
				pointSet.add(new Point(x));
			}
		return pointSet;
	}

	public void test_lee() throws Exception{
		
		LinkedList<Point> pointSet2 = lee_pointSet();
		int input_numCluster = 500;
		KMeans2 km = new KMeans2(pointSet2,input_numCluster);
		ArrayList<Centroid> answer = km.solve(10000,0.0000001);
		System.out.println(km.getCost());
		System.out.println();
		System.out.println(answer);
		System.out.println();
		//this one is too long to show here:
		//System.out.println(km.getClusterList());
	}

Intuitively, we know the solution: we would put one cluster centroid at the center of every square with the following width and height in blocks, the optimal cost value (average distance between the point and the centroid of the cluster that the point belongs to) should be about 1.6964 (According to the test_computeFitness() in test_Fitness). However, the solution here could only achieve about 2.268073790881155. It is not a optimal solution and KMeans2 is also not a good algorithm.

Whereas, the cost value for the same problem with KMeans is 1.7331206317320742, which is quite a optimal one. Although the numbers of points in every cluster is not equal, it is still quite close to equality. So it is a better idea to apply KMeans2 on the results come from KMeans for this specific problem.



Lee’s Solution

K-cluster
find nearest cluster
swap cluster if decrease the distance