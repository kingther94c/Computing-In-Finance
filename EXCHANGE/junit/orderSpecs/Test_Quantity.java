package orderSpecs;

public class Test_Quantity  extends junit.framework.TestCase{

	public void test_Quantity(){
		Quantity  q = new Quantity(100);
		System.out.println(q.equals(new Quantity(100)));
	}
}
