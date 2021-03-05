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
@Warmup(iterations = 10)
@Measurement(iterations = 10)
@Fork(1)
@State(value = Scope.Benchmark)
@OutputTimeUnit(TimeUnit.SECONDS)
public class FilterBenchmarking {

    private static final int RANGE_MAX = 1_000_000;

    @Benchmark
    public long simpleFilter() {
        return LongStream.range(0, RANGE_MAX)
                .filter(e -> e % 5 == 0 && e % 7 == 0 && e % 3 == 0 && e % 2 == 0)
                .count();
    }

    @Benchmark
    public long doubleFilter() {
        return LongStream.range(0, RANGE_MAX)
                .filter(e -> e % 5 == 0 && e % 7 == 0)
                .filter(e -> e % 3 == 0 && e % 2 == 0)
                .count();
    }

    @Benchmark
    public long tripleFilter() {
        return LongStream.range(0, RANGE_MAX)
                .filter(e -> e % 5 == 0 && e % 7 == 0)
                .filter(e -> e % 3 == 0)
                .filter(e -> e % 2 == 0)
                .count();
    }

    @Benchmark
    public long quarterFilter() {
        return LongStream.range(0, RANGE_MAX)
                .filter(e -> e % 5 == 0)
                .filter(e -> e % 7 == 0)
                .filter(e -> e % 3 == 0)
                .filter(e -> e % 2 == 0)
                .count();
    }


    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(FilterBenchmarking.class.getSimpleName())
                .build();
        new Runner(opt).run();
    }
}