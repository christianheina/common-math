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

import org.apache.commons.math3.complex.Complex;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit test for {@link Interpolation}.
 * 
 * @author Christian Heina (developer@christianheina.com)
 */
public class InterpolationTest {

    private static final double EPS = 1e-9;

    @Test
    public void interpftTest1() {
        int numberOfInterpolationPoints = 10;
        List<Double> expectedResults = createExpectedResults1();
        double[] dataToInterpolate = new double[] { 10.0, 12.0, 15.0 };
        List<Double> interpolatedData = Interpolation.interpft(dataToInterpolate, numberOfInterpolationPoints);
        Assert.assertEquals(interpolatedData.size(), numberOfInterpolationPoints);
        Assert.assertEquals(interpolatedData.size(), expectedResults.size());
        for (int i = 0; i < interpolatedData.size(); i++) {
            Assert.assertEquals(interpolatedData.get(i), expectedResults.get(i), EPS);
        }
    }

    private static List<Double> createExpectedResults1() {
        List<Double> expectedResults = new ArrayList<>();
        expectedResults.add(10.0);
        expectedResults.add(9.42755309221487);
        expectedResults.add(9.965015472699127);
        expectedResults.add(11.407094779782215);
        expectedResults.add(13.202965732631291);
        expectedResults.add(14.666666666666668);
        expectedResults.add(15.2391135744518);
        expectedResults.add(14.701651193967542);
        expectedResults.add(13.259571886884455);
        expectedResults.add(11.463700934035378);
        return expectedResults;
    }

    @Test
    public void interpftTest2() {
        int numberOfInterpolationPoints = 20;
        List<Double> expectedResults = createExpectedResults2();
        double[] dataToInterpolate = new double[] { 8.8, 9.9, 11, 12.1 };
        List<Double> interpolatedData = Interpolation.interpft(dataToInterpolate, numberOfInterpolationPoints);
        Assert.assertEquals(interpolatedData.size(), numberOfInterpolationPoints);
        Assert.assertEquals(interpolatedData.size(), expectedResults.size());
        for (int i = 0; i < interpolatedData.size(); i++) {
            Assert.assertEquals(interpolatedData.get(i), expectedResults.get(i), EPS);
        }
    }

    private static List<Double> createExpectedResults2() {
        List<Double> expectedResults = new ArrayList<>();
        expectedResults.add(8.8);
        expectedResults.add(8.61895979135667);
        expectedResults.add(8.743558181759617);
        expectedResults.add(9.083476875572059);
        expectedResults.add(9.508878485169108);
        expectedResults.add(9.9);
        expectedResults.add(10.188715872793994);
        expectedResults.add(10.376604430615501);
        expectedResults.add(10.5233955693845);
        expectedResults.add(10.711284127206005);
        expectedResults.add(10.999999999999998);
        expectedResults.add(11.39112151483089);
        expectedResults.add(11.81652312442794);
        expectedResults.add(12.156441818240385);
        expectedResults.add(12.281040208643333);
        expectedResults.add(12.1);
        expectedResults.add(11.601202821018447);
        expectedResults.add(10.863314263196944);
        expectedResults.add(10.036685736803058);
        expectedResults.add(9.298797178981552);
        return expectedResults;
    }

    @Test
    public void interpftDoubleTest() {
        int numberOfInterpolationPoints = 10;
        List<Double> expectedResults = createExpectedResults1();
        Double[] dataToInterpolate = new Double[] { 10.0, 12.0, 15.0 };
        List<Double> interpolatedData = Interpolation.interpft(dataToInterpolate, numberOfInterpolationPoints);
        Assert.assertEquals(interpolatedData.size(), numberOfInterpolationPoints);
        Assert.assertEquals(interpolatedData.size(), expectedResults.size());
        for (int i = 0; i < interpolatedData.size(); i++) {
            Assert.assertEquals(interpolatedData.get(i), expectedResults.get(i), EPS);
        }
    }

    @Test
    public void interpftPrimitiveFloatTest() {
        int numberOfInterpolationPoints = 10;
        List<Float> expectedResults = createExpectedFloatResults();
        float[] dataToInterpolate = new float[] { 10.0f, 12.0f, 15.0f };
        List<Float> interpolatedData = Interpolation.interpft(dataToInterpolate, numberOfInterpolationPoints);
        Assert.assertEquals(interpolatedData.size(), numberOfInterpolationPoints);
        Assert.assertEquals(interpolatedData.size(), expectedResults.size());
        for (int i = 0; i < interpolatedData.size(); i++) {
            Assert.assertEquals(interpolatedData.get(i), expectedResults.get(i), EPS);
        }
    }

    @Test
    public void interpftFloatTest() {
        int numberOfInterpolationPoints = 10;
        List<Float> expectedResults = createExpectedFloatResults();
        Float[] dataToInterpolate = new Float[] { 10.0f, 12.0f, 15.0f };
        List<Float> interpolatedData = Interpolation.interpft(dataToInterpolate, numberOfInterpolationPoints);
        Assert.assertEquals(interpolatedData.size(), numberOfInterpolationPoints);
        Assert.assertEquals(interpolatedData.size(), expectedResults.size());
        for (int i = 0; i < interpolatedData.size(); i++) {
            Assert.assertEquals(interpolatedData.get(i), expectedResults.get(i), EPS);
        }
    }

    private static List<Float> createExpectedFloatResults() {
        List<Float> expectedResults = new ArrayList<>();
        expectedResults.add(10.0f);
        expectedResults.add(9.42755309221487f);
        expectedResults.add(9.965015472699127f);
        expectedResults.add(11.407094779782215f);
        expectedResults.add(13.202965732631291f);
        expectedResults.add(14.666666666666668f);
        expectedResults.add(15.2391135744518f);
        expectedResults.add(14.701651193967542f);
        expectedResults.add(13.259571886884455f);
        expectedResults.add(11.463700934035378f);
        return expectedResults;
    }

    @Test
    public void interpftComplexTest() {
        int numberOfInterpolationPoints = 5;
        List<Complex> expectedResults = createExpectedComplexResults();
        Complex[] dataToInterpolate = new Complex[] { new Complex(1, 0), new Complex(0, -1), new Complex(-1, -1) };
        List<Complex> interpolatedData = Interpolation.interpft(dataToInterpolate, numberOfInterpolationPoints);
        Assert.assertEquals(interpolatedData.size(), numberOfInterpolationPoints);
        Assert.assertEquals(interpolatedData.size(), expectedResults.size());
        for (int i = 0; i < interpolatedData.size(); i++) {
            Assert.assertTrue(Complex.equals(interpolatedData.get(i), expectedResults.get(i), EPS));
        }
    }

    private static List<Complex> createExpectedComplexResults() {
        List<Complex> expectedResults = new ArrayList<>();
        expectedResults.add(new Complex(1, 0));
        expectedResults.add(new Complex(0.858109730072502, -0.460655337083368));
        expectedResults.add(new Complex(-0.469659020738196, -1.206011329583298));
        expectedResults.add(new Complex(-1.148374968011699, -1.206011329583298));
        expectedResults.add(new Complex(-0.240075741322607, -0.460655337083368));
        return expectedResults;
    }

}
