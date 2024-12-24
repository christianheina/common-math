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

package examples;

import java.util.List;

import com.christianheina.math.interpolation.Interpolation;

/**
 * Example class using interpft interpolation
 * 
 * @author Christian Heina (developer@christianheina.com)
 */
public class InterpftExample {

    private static final int NUMBER_OF_INTERPOLATION_POINTS = 10;

    public static void main(String[] args) {
        double[] dataToInterpolate = new double[] { 10.0, 12.0, 15.0, 8.0 };
        System.out.println("Original data size: " + dataToInterpolate.length);
        System.out.println(createDataString(dataToInterpolate, "Original data"));
        List<Double> interpolatedData = Interpolation.interpft(dataToInterpolate, NUMBER_OF_INTERPOLATION_POINTS);
        System.out.println("Interpolated data size: " + interpolatedData.size());

        StringBuilder sb = new StringBuilder();
        sb.append("Interpolated data: ");
        for (double data : interpolatedData) {
            sb.append(data);
            sb.append(", ");
        }
        System.out.println(createDataString(interpolatedData, "Interpolated data"));
    }

    private static String createDataString(double[] dataList, String startMessage) {
        StringBuilder sb = new StringBuilder();
        sb.append(startMessage);
        sb.append(": ");
        for (double data : dataList) {
            sb.append(data);
            sb.append(", ");
        }
        return sb.toString();
    }

    private static String createDataString(List<Double> dataList, String startMessage) {
        return createDataString(dataList.stream().mapToDouble(Double::doubleValue).toArray(), startMessage);
    }

}
