package com.lmj.jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;

/**
 * @Author: lmj
 * @Description:
 * @Date: Create in 10:59 上午 2020/12/4
 **/
@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 2)
@Measurement(iterations = 3)
@Fork(1)
@State(value = Scope.Benchmark)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class MRVsLambadaBenchmarking {

    private static final int RANGE_MAX = 100;

    @Benchmark
    public long methodReference() {
        return LongStream.range(0, RANGE_MAX)
                .filter(this::filter)
                .count();
    }

    @Benchmark
    public long lambda() {
        return LongStream.range(0, RANGE_MAX)
                .filter(e -> filter(e))
                .count();
    }


    public boolean filter(long e) {
        return e % 5 == 0 && e % 7 == 0 && e % 3 == 0 && e % 2 == 0;
    }


    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(MRVsLambadaBenchmarking.class.getSimpleName())
                .build();
        new Runner(opt).run();
    }
}