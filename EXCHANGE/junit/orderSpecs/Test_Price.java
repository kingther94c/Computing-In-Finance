package orderSpecs;

public class Test_Price extends junit.framework.TestCase{

	public void test_Price(){
		Price p = new Price(123456789);
		System.out.println(p);
		assertEquals(p.toString(),"12345.6789");
		p = new Price(123450000);
		System.out.println(p);
		assertEquals(p.toString(),"12345.0000");
	}
}
