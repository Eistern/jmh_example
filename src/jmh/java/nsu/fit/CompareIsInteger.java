package nsu.fit;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

@BenchmarkMode(Mode.All)
@Warmup(iterations = 4, time = 1)
@Measurement(iterations = 32, batchSize = 8, time = 1)
@State(Scope.Thread)
public class CompareIsInteger {
    private static final String INPUT = "56786422";

    @Benchmark
    public void isIntegerException(final Blackhole blackhole) {
        try {
            Integer.parseInt(INPUT);
            blackhole.consume(true);
        } catch (NumberFormatException e) {
            blackhole.consume(false);
        }
    }

    @Benchmark
    public void isIntegerLinear(final Blackhole blackhole) {
        for (byte stringByte : INPUT.getBytes()) {
            if (!Character.isDigit(stringByte)) {
                blackhole.consume(false);
                return;
            }
        }
        blackhole.consume(true);
    }

    @Benchmark
    public void isIntegerRegexp(final Blackhole blackhole) {
        boolean matches = INPUT.matches("^\\d+$");
        blackhole.consume(matches);
    }
}


