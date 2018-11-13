package ru.sberbank.optdemo3;

public class Matrix {
	final DoubleRow[] rows;
	final int nRows, nColumns;
	final Value.MatrixType type;

	Matrix(int rows, int cols, Value.MatrixType type) {
		this.type = type;
		this.nRows = rows;
		this.nColumns = cols;
		this.rows = new DoubleRow[this.nRows];
		for (int i = 0; i < this.nRows; i++)
			this.rows[i] = new DoubleRow(this.nColumns);
	}

	public Matrix(DoubleRow[] newRows, Value.MatrixType type2, int nRows2, int nColumns2) {
		this.rows = newRows;
		this.type = type2;
		this.nRows = nRows2;
		this.nColumns = nColumns2;
	}

	public Matrix update(int row, int col, Value val) throws Exception {
		DoubleRow[] newRows = new DoubleRow[nRows];
		for (int i = 0; i < nRows; i++)
			newRows[i] = (i == row) ? rows[i].update(col, val) : rows[i];
		return new Matrix(newRows, type, nRows, nColumns);
	}

	Value get(int row, int col) throws Exception {
		return rows[row].get(col);
	}
	
	public void set(int row, int col, double v) throws Exception {
		rows[row].set(col, v);
	}
}




