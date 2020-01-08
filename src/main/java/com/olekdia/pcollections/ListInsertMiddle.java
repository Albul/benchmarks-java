/**
 * Copyright 2019 Oleksandr Albul
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.olekdia.pcollections;

import org.javimmutable.collections.util.JImmutables;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.pcollections.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
public class ListInsertMiddle {

    public static final int SIZE = 100_000;

    volatile Object[] mValues = null;

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(ListInsertMiddle.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    @Setup
    public void setup() {
        mValues = new Object[SIZE];

        Random random = new Random();
        for (int i = 0; i < SIZE; i++) {
            mValues[i] = Integer.valueOf(random.nextInt(SIZE));
        }
    }

    @Benchmark
    public Object Stack() {
        return CollectionHelper.listAddMid(new Stack(), mValues);
    }

    @Benchmark
    public Object LinkedList() {
        return CollectionHelper.listAddMid(new LinkedList(), mValues);
    }

    @Benchmark
    public Object ArrayList() {
        return CollectionHelper.listAddMid(new ArrayList(), mValues);
    }

    @Benchmark
    public Object ArrayListPredefinedSize() {
        return CollectionHelper.listAddMid(new ArrayList(SIZE), mValues);
    }

    @Benchmark
    public Object ConsPStack() {
        return CollectionHelper.pSequencePlusMid(ConsPStack.empty(), mValues); // todo
    }

    @Benchmark
    public Object TreePVector() {
        return CollectionHelper.pSequencePlusMid(TreePVector.empty(), mValues);
    }

    @Benchmark
    public Object JImmutableList() {
        return CollectionHelper.jImmutableListInsertMid(JImmutables.list(), mValues);
    }

}
/**
 Benchmark                       Mode  Cnt           Score           Error  Units
 JImmutableList             avgt    3    20964836.738 ±   1722225.979  ns/op
 TreePVector                avgt    3    71693585.551 ±  40239888.036  ns/op
 ArrayList                  avgt    3   353459261.333 ±  47117176.924  ns/op
 ArrayListPredefinedSize    avgt    3   353432854.111 ±  12926121.517  ns/op
 Stack                      avgt    3   354646074.300 ±  46478198.199  ns/op
 LinkedList                 avgt    3  4779051906.167 ± 249346891.513  ns/op
 */