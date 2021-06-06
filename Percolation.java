package a01;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	
	private int[][] siteGrid;
	private WeightedQuickUnionUF gridUnion;
	private int topGrid;
	private int bottomGrid;
	private int size;
	private int openCount;
	
	
	// create N­by­N grid, with all sites blocked
	public Percolation(int N) {
		// siteGrid is a 3d array with a row-column pair for each site, then index 0 stores the open/closed status of a block
		// and index 1 stores whether that block is filled (0 is closed and not filled respectively)
		// create union model to store connected blocks
		if (N < 1) {
			throw new IllegalArgumentException("Warning! The initial grid size needs to be N x N where N is an integer larger than 0!!!");
		}
		size = N;
		siteGrid = new int[size][size];
		//The grid union will count from 1 to N^2, moving left to right then top to bottom on the grid.
		gridUnion = new WeightedQuickUnionUF(size*size + 2);
		topGrid = 0;
		bottomGrid = size*size +1;
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				siteGrid[i][j] = 0;
			}
		}
		
	}
	
	public static void main(String[] args) {
		int N = 2;
		Percolation p1 = new Percolation(N);
		p1.open(0, 1);
		p1.open(1, 1);
		p1.percolates();
	}
	
	
	// open site (row i, column j) if it is not open already
	// create connections in quick union with neighboring open sites
	// check to see if system percolates after each open step
	public void open(int i, int j) {
		validateIndex(i,j);
		openCount++;
		siteGrid[i][j] = 1;
		int siteLabel = this.getLabel(i, j); 
		//check for open neighbors, and connect any using the union method
		
		for (int x = -1; x <= 1; x++) {
			for (int y = -1; y <= 1; y++) {
				
				int neighborI = i + x;
				int neighborJ = j + y;
				if(x == 0 && y == 0 || !isValidIndex(neighborI,neighborJ)) {
					continue;
				}
				
				if(i ==  0) {
					gridUnion.union(siteLabel, topGrid);
				}
				
				if(i ==  size - 1) {
					gridUnion.union(siteLabel, bottomGrid);
				}
				
	
				if (this.isOpen(neighborI, neighborJ)) {
					int neighborSiteLabel = this.getLabel(neighborI, neighborJ);
					gridUnion.union(siteLabel, neighborSiteLabel);
				}
				
			}
		}

	}
	
	
	
	// is site (row i, column j) open?
	public boolean isOpen(int i, int j) {
		validateIndex(i,j);
		if (siteGrid[i][j] == 1) {
			return true;
		}
		return false;
	}
	
	
	// is site (row i, column j) full?
	public boolean isFull(int gridLabel) {
		if(gridUnion.find(gridLabel) == gridUnion.find(topGrid)) {
			return true;
		}
		return false;
	}


	public boolean percolates() {
		//check if there exists a connection between top and bottom grid elements
		if (gridUnion.find(topGrid) == gridUnion.find(bottomGrid)) {
			System.out.println("The grid percolated with: " + openCount + " out of " + size*size + " grids being opened.");
			return true;
		}
		return false;
	}
	
	private boolean isValidIndex(int i, int j) {
		if (i < size && i >= 0  && j < size && j >= 0) {
			return true;
		}
		
		return false;
	}

	private void validateIndex(int i, int j) {
		if (i < size && i >= 0  && j < size && j >= 0) {
			return;
		}
		
			throw new IndexOutOfBoundsException("Index (" + i + "," + j + ") is an invalid index, please use indices between 0 and N(exlcusive)");
	}
	private int getLabel(int i, int j) {
		return i * size + j + 1;
	}
}
