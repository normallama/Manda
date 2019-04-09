
public class MandaTest {
	public static void main(String[] args) 
	{
		MFCC mfcc = new MFCC();
		MFCC mfcc2 = new MFCC();
		double[][] result = mfcc.getMfcc("f://4手机存储//2.wav");
		double[][] result2= mfcc2.getMfcc("f://4手机存储/2.wav");
		DTW d1=new DTW(result,result2);
		System.out.println(d1.calDistance());
		 //System.out.println(DTW.DTWDistance(result, result));
		System.out.println("yes!");
		}
}
