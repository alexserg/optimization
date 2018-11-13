package ru.sberbank.optdemo3;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.profile.GCProfiler;
import org.openjdk.jmh.profile.PausesProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@BenchmarkMode(Mode.AverageTime)
@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class MatrixGCBenchmark {

	private static final int ROWS = 100;

	private static Matrix A, B, C;

	@Setup
	public void setup() throws Exception {
		A = new Matrix(ROWS, ROWS, Value.MatrixType.FLOATING_POINT);
		B = new Matrix(ROWS, ROWS, Value.MatrixType.FLOATING_POINT);
		C = new Matrix(ROWS, ROWS, Value.MatrixType.FLOATING_POINT);

		Random random = new Random();
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < ROWS; j++) {
				double nextDouble = random.nextDouble();
				B = B.update(i, j, new Value(nextDouble));
				C = C.update(i, j, new Value(nextDouble));
			}
		}
	}

	@Benchmark
	public void multiplyObjectImmutable() {
		try {
			for (int i = 0; i < ROWS; i++) {
				for (int j = 0; j < ROWS; j++) {
					for (int k = 0; k < ROWS; k++)
						A = A.update(i, j, new Value(A.get(i, j).getDouble() + B.get(i, k).getDouble() * C.get(k, j).getDouble()));
				}
			}
		} catch (Exception e) {
		}
	}
	
	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder().include(MatrixGCBenchmark.class.getSimpleName()).warmupIterations(2)
				.measurementIterations(5)
				 .addProfiler(PausesProfiler.class)
				 .addProfiler(GCProfiler.class)
				.jvmArgs("-XX:+UseParallelGC")
				.threads(1).forks(1).build();

		new Runner(opt).run();
	}
}
