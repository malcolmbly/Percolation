package a01;


public class PercolationStats {
	
	private int[] gridsOpened;
	private int trialCount;
	// perform T independent experiments on an N­by­N grid   
	public PercolationStats(int N, int T) {
		gridsOpened = new int[T];
		trialCount = T;
		for (int i = 0; i < T; i++) {
			Percolation p = new Percolation (N);
			//randomly open grids until it percolates, save the number of grids opened in a list
			
		}
			
	}
	
	// sample mean of percolation threshold
	public double mean() {
		int sum = 0;
		for (int num : gridsOpened) {
			sum += num;
		}
		
		return sum / trialCount;
		
	}
	
	// sample standard deviation of percolation threshold
	public double stddev() {
		int var = 0;
		
		for (int num : gridsOpened) {
			var += (num - mean()) * (num - mean());
		}
		return var / (trialCount - 1);
	}
	
	// low  endpoint of 95% confidence interval
	public double confidenceLow() {
		
		double lowerBound = mean() - 1.96 * stddev() / Math.sqrt(trialCount);
		
		return lowerBound;
		
	}
	
	// high endpoint of 95% confidence interval
	public double confidenceHigh() {
		double upperBound = mean() + 1.96 * stddev() / Math.sqrt(trialCount);
		
		return upperBound;
	}
}
