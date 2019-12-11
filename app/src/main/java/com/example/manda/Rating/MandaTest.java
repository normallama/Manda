package com.example.manda.Rating;

public class MandaTest {
	public static void main(String[] args) 
	{
		MFCC mfcc = new MFCC();
		MFCC mfcc2 = new MFCC();
		double[][] result = mfcc.getMfcc("f://4手机存储//2.wav");
		double[][] result2= mfcc2.getMfcc("f://4手机存储/2.wav");
		DTW d1=new DTW(result,result2);

		d1.calscore();
		
		
		
		/*for(int i = 0; i < result.length; i++){  
            for(int j = 0; j < result[i].length; j++){  
                System.out.print(result[i][j]);  
            }  
            System.out.println();  
        } */
		 //System.out.println(DTW.DTWDistance(result, result));
		
		
		System.out.println("yes!");
	}
}
