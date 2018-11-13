package ru.sberbank.optdemo3;

public class Value {

	public enum MatrixType {
		FLOATING_POINT, INTEGER
	}

	final MatrixType type;
	final Integer iVal;
	final Double dVal;

	Value(int i) {
		type = MatrixType.FLOATING_POINT;
		dVal = 0.0;
		iVal = i;
	}

	Value(double d) {
		type = MatrixType.FLOATING_POINT;
		dVal = d;
		iVal = 0;
	}

	int getInt() throws Exception {
		if(type == MatrixType.INTEGER)
			return iVal;
		else
			throw new Exception();
	}

	double getDouble() throws Exception { 
		if(type == MatrixType.FLOATING_POINT)
			return dVal;
		else
			throw new Exception();
	}
}
