/*
 * Copyright 2024 Christian Heina
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.christianheina.math.interpolation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.commons.math3.complex.Complex;

import com.christianheina.math.ComplexMath;

/**
 * Provides interpolation functionality
 * 
 * @author Christian Heina (developer@christianheina.com)
 */
public class Interpolation {

    private Interpolation() {
        /* Hidden Constructor */ }

    /**
     * Interpolate using FFT method
     * 
     * @param dataToInterpolate
     *            data to interpolate
     * @param lengthOfInterpolatedDataArray
     *            length of interpolated data
     * 
     * @return interpolated data
     */
    public static List<Float> interpft(Float[] dataToInterpolate, int lengthOfInterpolatedDataArray) {
        double[] dataAsDouble = new double[dataToInterpolate.length];
        IntStream.range(0, dataToInterpolate.length).forEach(idx -> dataAsDouble[idx] = dataToInterpolate[idx]);
        return interpft(dataAsDouble, lengthOfInterpolatedDataArray).stream().map(Double::floatValue)
                .collect(Collectors.toList());
    }

    /**
     * Interpolate using FFT method
     * 
     * @param dataToInterpolate
     *            data to interpolate
     * @param lengthOfInterpolatedDataArray
     *            length of interpolated data
     * 
     * @return interpolated data
     */
    public static List<Float> interpft(float[] dataToInterpolate, int lengthOfInterpolatedDataArray) {
        double[] dataAsDouble = new double[dataToInterpolate.length];
        IntStream.range(0, dataToInterpolate.length).forEach(idx -> dataAsDouble[idx] = dataToInterpolate[idx]);
        return interpft(dataAsDouble, lengthOfInterpolatedDataArray).stream().map(Double::floatValue)
                .collect(Collectors.toList());
    }

    /**
     * Interpolate using FFT method
     * 
     * @param dataToInterpolate
     *            data to interpolate
     * @param lengthOfInterpolatedDataArray
     *            length of interpolated data
     * 
     * @return interpolated data
     */
    public static List<Double> interpft(Double[] dataToInterpolate, int lengthOfInterpolatedDataArray) {
        double[] unboxedData = Stream.of(dataToInterpolate).mapToDouble(d -> d).toArray();
        return interpft(unboxedData, lengthOfInterpolatedDataArray);
    }

    /**
     * Interpolate using FFT method
     * 
     * @param dataToInterpolate
     *            data to interpolate
     * @param lengthOfInterpolatedDataArray
     *            length of interpolated data
     * 
     * @return interpolated data
     */
    public static List<Double> interpft(double[] dataToInterpolate, int lengthOfInterpolatedDataArray) {
        List<Complex> fftedData = ComplexMath.fft(dataToInterpolate);
        List<Complex> interpolatedData = commonInterpft(fftedData, lengthOfInterpolatedDataArray);
        List<Double> realInterpolationResult = new ArrayList<>();
        double multiplier = (double) lengthOfInterpolatedDataArray / fftedData.size();
        for (int i = 0; i < interpolatedData.size(); i++) {
            realInterpolationResult.add(interpolatedData.get(i).getReal() * multiplier);
        }

        return realInterpolationResult;
    }

    /**
     * Interpolate using FFT method
     * 
     * @param dataToInterpolate
     *            data to interpolate
     * @param lengthOfInterpolatedDataArray
     *            length of interpolated data
     * 
     * @return interpolated data
     */
    public static List<Complex> interpft(Complex[] dataToInterpolate, int lengthOfInterpolatedDataArray) {
        List<Complex> fftedData = ComplexMath.fft(dataToInterpolate);
        List<Complex> interpolatedData = commonInterpft(fftedData, lengthOfInterpolatedDataArray);
        double multiplier = (double) lengthOfInterpolatedDataArray / fftedData.size();
        for (int i = 0; i < interpolatedData.size(); i++) {
            interpolatedData.set(i, interpolatedData.get(i).multiply(multiplier));
        }

        return interpolatedData;
    }

    private static List<Complex> commonInterpft(List<Complex> fftedData, int lengthOfInterpolatedDataArray) {
        int originalLength = fftedData.size();
        int nyqst = (int) Math.ceil((originalLength + 1) / 2.0);
        List<Complex> dataWithInterpolatedPoints = new ArrayList<>(fftedData.subList(0, nyqst));
        for (int i = 0; i < lengthOfInterpolatedDataArray - originalLength; i++) {
            dataWithInterpolatedPoints.add(Complex.ZERO);
        }
        dataWithInterpolatedPoints.addAll(fftedData.subList(nyqst, fftedData.size()));

        if (originalLength % 2 == 0) {
            dataWithInterpolatedPoints.set(nyqst - 1, dataWithInterpolatedPoints.get(nyqst - 1).divide(2));
            dataWithInterpolatedPoints.set(nyqst + lengthOfInterpolatedDataArray - originalLength - 1,
                    dataWithInterpolatedPoints.get(nyqst - 1));
        }

        return ComplexMath.ifft(dataWithInterpolatedPoints);
    }

}
