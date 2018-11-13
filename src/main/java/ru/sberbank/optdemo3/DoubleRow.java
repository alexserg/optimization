package ru.sberbank.optdemo3;

public class DoubleRow {

	final double[] theRow;
	public final int numColumns;
	public int a;

	DoubleRow(int ncols) {
		this.numColumns = ncols;
		theRow = new double[ncols];
	}

	private DoubleRow(double[] row, int cols) {
		this.theRow = row;
		this.numColumns = cols;
	}

	Value get(int col) throws Exception {
		return new Value(theRow[col]);
	}

	public DoubleRow update(int col, Value val) throws Exception {
		theRow[col] = val.getDouble();
		return this;
//		Double[] row = new Double[numColumns];
//		for (int i = 0; i < numColumns; i++) {
//			row[i] = (i == col) ? val.getDouble() : theRow[i];
//		}
//		return new DoubleRow(row, numColumns);
	}
	
	void set(int col, double v) throws Exception {
		theRow[col] = v;
	}
}
