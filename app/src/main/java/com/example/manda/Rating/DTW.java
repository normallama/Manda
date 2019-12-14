package com.example.manda.Rating;

public class DTW {
	private double[][] test;
    private double[][] reference;
    // variance for each feature dimension 
    private double[] variance;
    protected int globalPathConstraint = 20;  
    public DTW(double[][] test, double[][] reference)
    {
        this.test = test;
        this.reference = reference;
        // by default, we initialize variance as 1 for each dimension. 
        variance = new double[test[0].length];
        for (int i = 0; i < variance.length; i++)
            variance[i] = 1;
    }
    public double getMin(double ... num){
        double min = Double.MAX_VALUE;
        for (int i=0; i<num.length; i++){
            if (num[i] < min)
                min = num[i];
        }
         
        return min;
    }
    public DTW(double[][] test, double[][] reference, double[] variance)
    {
        this.test = test;
        this.reference = reference;
        this.variance = variance;
    }
    
    private double calDistance() {
        
        int n = test.length;//测试的长度
        int m = reference.length;//参考的长度
        
        // DP for calculating the minimum distance between two vector. 
        // DTW[i,j] = minimum distance between vector test[0..i] and reference[0..j]
        double[][] DTW = new double[n][m];
        
        // initialization
        for(int i = 0; i < n; i++)
            for(int j = 0; j < m; j++)
                DTW[i][j] = Double.MAX_VALUE;
        
        // initialize base case
        DTW[0][0] =  getDistance(test[0],reference[0]);
        
        // initialize boundary condition. 
        for (int i = 1; i < n; i++)
            DTW[i][0] = DTW[i-1][0] + getDistance(test[i],reference[0]);  
       
        for(int i = 1; i < m; i++)
            DTW[0][i] = DTW[0][i-1] + getDistance(test[0],reference[i]);
       
        // DP comes here...
        for (int i = 1; i < n; i++)
        {
            for (int j = Math.max(1, i-globalPathConstraint); j < Math.min(m, i+globalPathConstraint); j++)
            {   // consider five different moves. 
                double cost = getDistance(test[i],reference[j]);
                double d1 = cost + DTW[i-1][j];
                double d2 = cost + DTW[i][j-1];
                double d3 = 2 * cost + DTW[i-1][j-1];
                double d4 = Double.MAX_VALUE;
                if (j > 1)
                    d4 = 3 * cost + DTW[i-1][j-2];
                double d5 = Double.MAX_VALUE;
                if (i > 1)
                    d5 = 3 * cost + DTW[i-2][j-1];
                
                DTW[i][j] = getMin(d1,d2,d3,d4,d5);
            }
        }
        
        return DTW[n-1][m-1] /(m+n);
    }
    
    
    
    
    
    private double getDistance(double[] vec1, double[] vec2)
    {
        double distance = 0.0;
        for(int i = 0; i < vec1.length; i++)
            distance += (vec1[i] - vec2[i]) * (vec1[i] - vec2[i]) / variance[i];
      
        return Math.sqrt(distance);
    }  
    
    public double calscore()
    {
		
		double dis_for_dtw,score,a_for_score,b_for_score;
		
		a_for_score=Math.log(1.864)/Math.log(2);
        dis_for_dtw=calDistance();
		
		System.out.println(dis_for_dtw);
		
		
		score=100/(1+a_for_score*dis_for_dtw);
		System.out.println(score);
    	return score;
    }
    
}
