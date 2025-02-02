/*
 * Copyright 2022 Christian Heina
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

package com.christianheina.common.math;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.complex.Complex;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Unit test for {@link ComplexAdditions}.
 * 
 * @author Christian Heina (developer@christianheina.com)
 */
public class ComplexAdditionsTest {

    private static final double EPS = 1e-9;

    private static final Complex NEGATIVE_ONE = new Complex(-1, 0);
    private static final List<Complex> EMPTY_LIST = new ArrayList<>();

    private List<Complex> complexList1;
    private List<Complex> complexList2;
    private List<Complex> fftResultList;
    private List<Complex> ifftResultList;

    private double[] doubleArray = new double[] { 10.0, 15.0, 5.0 };
    private List<Complex> doubleFftResult;
    private List<Complex> doubleIfftResult;

    @BeforeMethod
    public void prepare() {
        Complex c = new Complex(1, 1);
        complexList1 = new ArrayList<>();
        complexList1.add(Complex.ONE);
        complexList1.add(NEGATIVE_ONE);
        complexList1.add(NEGATIVE_ONE);
        complexList1.add(c);

        complexList2 = new ArrayList<>();
        complexList2.add(NEGATIVE_ONE);
        complexList2.add(Complex.ONE);
        complexList2.add(NEGATIVE_ONE);
        complexList2.add(c);

        fftResultList = new ArrayList<>();
        fftResultList.add(new Complex(0, 1));
        fftResultList.add(new Complex(1, 2));
        fftResultList.add(new Complex(0, -1));
        fftResultList.add(new Complex(3, -2));

        ifftResultList = new ArrayList<>();
        ifftResultList.add(new Complex(0, 0.25));
        ifftResultList.add(new Complex(0.75, -0.5));
        ifftResultList.add(new Complex(0, -0.25));
        ifftResultList.add(new Complex(0.25, 0.5));

        doubleFftResult = new ArrayList<>();
        doubleFftResult.add(new Complex(30, 0));
        doubleFftResult.add(new Complex(0, -8.660254037844386));
        doubleFftResult.add(new Complex(0, 8.660254037844386));

        doubleIfftResult = new ArrayList<>();
        doubleIfftResult.add(new Complex(10, 0));
        doubleIfftResult.add(new Complex(0, 2.886751345948129));
        doubleIfftResult.add(new Complex(0, -2.886751345948129));
    }

    @Test
    public void pearsonCorrelationTest() {
        Complex corrAB = ComplexAdditions.pearsonCorrelation(complexList1, complexList2);
        Assert.assertTrue(Complex.equals(corrAB, new Complex(0.15789473684210528, 0.0), EPS));

        Complex corrBA = ComplexAdditions.pearsonCorrelation(complexList2, complexList1);
        Assert.assertTrue(Complex.equals(corrBA, new Complex(0.15789473684210528, 0.0), EPS));

        Assert.assertEquals(corrAB, corrBA);

        Complex corrAA = ComplexAdditions.pearsonCorrelation(complexList1, complexList1);
        Assert.assertTrue(Complex.equals(corrAA, Complex.ONE, EPS));

        Complex corrBB = ComplexAdditions.pearsonCorrelation(complexList2, complexList2);
        Assert.assertTrue(Complex.equals(corrBB, Complex.ONE, EPS));
    }

    @Test
    public void dotProductTest() {
        Complex productAB = ComplexAdditions.dotProduct(complexList1, complexList2);
        Assert.assertTrue(Complex.equals(productAB, new Complex(-1, 2), EPS));

        Complex productBA = ComplexAdditions.dotProduct(complexList1, complexList2);
        Assert.assertTrue(Complex.equals(productBA, new Complex(-1, 2), EPS));

        Complex productAA = ComplexAdditions.dotProduct(complexList1, complexList2);
        Assert.assertTrue(Complex.equals(productAA, new Complex(-1, 2), EPS));

        Complex productBB = ComplexAdditions.dotProduct(complexList1, complexList2);
        Assert.assertTrue(Complex.equals(productBB, new Complex(-1, 2), EPS));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void dotProductExceptionTest() {
        ComplexAdditions.dotProduct(complexList1, EMPTY_LIST);
    }

    @Test
    public void covarianceTest() {
        // cov(A,B)
        Complex covAB = ComplexAdditions.covariance(complexList1, complexList2);
        Assert.assertTrue(Complex.equals(covAB, new Complex(0.25, 0.0), EPS));

        // cov(B,A)
        Complex covBA = ComplexAdditions.covariance(complexList2, complexList1);
        Assert.assertTrue(Complex.equals(covBA, new Complex(0.25, 0.0), EPS));

        // cov(A,B) == cov(B,A)
        Assert.assertEquals(covAB, covBA);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void covarianceExceptionTest() {
        ComplexAdditions.covariance(complexList1, EMPTY_LIST);
    }

    @Test
    public void varianceTest() {
        double varA = ComplexAdditions.variance(complexList1);
        Assert.assertEquals(1.5833333333333333, varA, 1e-9);
        Assert.assertEquals(Math.pow(ComplexAdditions.standardDeviation(complexList1), 2), varA, EPS);

        double varB = ComplexAdditions.variance(complexList2);
        Assert.assertEquals(1.5833333333333333, varB, 1e-9);
        Assert.assertEquals(Math.pow(ComplexAdditions.standardDeviation(complexList2), 2), varA, EPS);
    }

    @Test
    public void standardDeviationTest() {
        double stdA = ComplexAdditions.standardDeviation(complexList1);
        Assert.assertEquals(1.2583057392117916, stdA, 1e-9);
        Assert.assertEquals(Math.sqrt(ComplexAdditions.variance(complexList1)), stdA, EPS);

        double stdB = ComplexAdditions.standardDeviation(complexList2);
        Assert.assertEquals(1.2583057392117916, stdB, 1e-9);
        Assert.assertEquals(Math.sqrt(ComplexAdditions.variance(complexList2)), stdB, EPS);
    }

    @Test
    public void sumTest() {
        Complex sumA = ComplexAdditions.sum(complexList1);
        Assert.assertTrue(sumA.equals(new Complex(0, 1)));

        Complex sumB = ComplexAdditions.sum(complexList2);
        Assert.assertTrue(sumB.equals(new Complex(0, 1)));
    }

    @Test
    public void meanTest() {
        Complex meanA = ComplexAdditions.mean(complexList1);
        Assert.assertTrue(meanA.equals(new Complex(0, 0.25)));

        Complex meanB = ComplexAdditions.mean(complexList1);
        Assert.assertTrue(meanB.equals(new Complex(0, 0.25)));
    }

    @Test
    public void fftTest() {
        List<Complex> fftList = ComplexAdditions.fft(complexList1);
        Assert.assertEquals(fftList.size(), complexList1.size());
        for (int i = 0; i < fftList.size(); i++) {
            Assert.assertEquals(fftList.get(i), fftResultList.get(i));
        }
    }

    @Test
    public void fftDoubleArrayTest() {
        List<Complex> fftList = ComplexAdditions.fft(complexList1.toArray(new Complex[complexList1.size()]));
        Assert.assertEquals(fftList.size(), complexList1.size());
        for (int i = 0; i < fftList.size(); i++) {
            Assert.assertEquals(fftList.get(i), fftResultList.get(i));
        }
    }

    @Test
    public void fftDoubleTest() {
        List<Complex> fftResult = ComplexAdditions.fft(doubleArray);
        Assert.assertEquals(fftResult.size(), doubleFftResult.size());
        for (int i = 0; i < fftResult.size(); i++) {
            Assert.assertTrue(Complex.equals(fftResult.get(i), doubleFftResult.get(i), EPS));
        }
    }

    @Test
    public void fftShiftTest() {
        List<Complex> evenFftShiftList = ComplexAdditions.fftShift(complexList1);
        Assert.assertEquals(evenFftShiftList.size(), complexList1.size());
        for (int i = 0; i < complexList1.size(); i++) {
            Assert.assertEquals(complexList1.get(i),
                    evenFftShiftList.get((i + (complexList1.size()) / 2) % complexList1.size()));
        }

        complexList1.add(NEGATIVE_ONE);
        List<Complex> oddFftShiftList = ComplexAdditions.fftShift(complexList1);
        Assert.assertEquals(oddFftShiftList.size(), complexList1.size());
        for (int i = 0; i < complexList1.size(); i++) {
            Assert.assertEquals(complexList1.get(i),
                    oddFftShiftList.get((i + (complexList1.size()) / 2) % complexList1.size()));
        }
    }

    @Test
    public void ifftTest() {
        List<Complex> ifftList = ComplexAdditions.ifft(complexList1);
        Assert.assertEquals(ifftList.size(), complexList1.size());
        for (int i = 0; i < ifftList.size(); i++) {
            Assert.assertEquals(ifftList.get(i), ifftResultList.get(i));
        }
    }

    @Test
    public void ifftComplexArrayTest() {
        List<Complex> ifftList = ComplexAdditions.ifft(complexList1.toArray(new Complex[complexList1.size()]));
        Assert.assertEquals(ifftList.size(), complexList1.size());
        for (int i = 0; i < ifftList.size(); i++) {
            Assert.assertEquals(ifftList.get(i), ifftResultList.get(i));
        }
    }

    @Test
    public void ifftDoubleTest() {
        List<Complex> ifftResult = ComplexAdditions.ifft(doubleArray);
        Assert.assertEquals(ifftResult.size(), doubleIfftResult.size());
        for (int i = 0; i < ifftResult.size(); i++) {
            Assert.assertTrue(Complex.equals(ifftResult.get(i), doubleIfftResult.get(i), EPS));
        }
    }

    @Test
    public void ifftShiftTest() {
        List<Complex> evenIfftShiftList = ComplexAdditions.ifftShift(complexList1);
        Assert.assertEquals(evenIfftShiftList.size(), complexList1.size());
        for (int i = 0; i < complexList1.size(); i++) {
            Assert.assertEquals(complexList1.get(i),
                    evenIfftShiftList.get((i + (complexList1.size() + 1) / 2) % complexList1.size()));
        }

        complexList1.add(NEGATIVE_ONE);
        List<Complex> oddfftShiftList = ComplexAdditions.ifftShift(complexList1);
        Assert.assertEquals(oddfftShiftList.size(), complexList1.size());
        for (int i = 0; i < complexList1.size(); i++) {
            Assert.assertEquals(complexList1.get(i),
                    oddfftShiftList.get((i + (complexList1.size() + 1) / 2) % complexList1.size()));
        }
    }

    @Test
    public void normalizeBySizeOfListTest() {
        List<Complex> normalizedBySizeOfListList = ComplexAdditions.normalizeBySizeOfList(complexList1);
        Assert.assertEquals(normalizedBySizeOfListList.size(), complexList1.size());
        for (int i = 0; i < normalizedBySizeOfListList.size(); i++) {
            Assert.assertEquals(normalizedBySizeOfListList.get(i), complexList1.get(i).divide(complexList1.size()));
        }
    }

    @Test
    public void scaleBySizeOfListTest() {
        List<Complex> scaledBySizeOfListList = ComplexAdditions.scaleBySizeOfList(complexList1);
        for (int i = 0; i < scaledBySizeOfListList.size(); i++) {
            Assert.assertEquals(scaledBySizeOfListList.size(), complexList1.size());
            Assert.assertEquals(scaledBySizeOfListList.get(i), complexList1.get(i).multiply(complexList1.size()));
        }
    }

}
