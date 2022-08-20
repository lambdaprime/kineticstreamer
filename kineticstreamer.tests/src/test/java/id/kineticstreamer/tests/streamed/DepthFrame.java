/*
 * Copyright 2022 kineticstreamer project
 * 
 * Website: https://github.com/lambdaprime/kineticstreamer
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
package id.kineticstreamer.tests.streamed;

import java.util.Arrays;

/**
 * @author lambdaprime intid@protonmail.com
 */
public class DepthFrame extends Frame {

    public double[] data = new double[0];

    public DepthFrame() {}

    public DepthFrame(int width, int height, double... data) {
        super(width, height);
        this.data = data;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        var other = (DepthFrame) obj;
        System.out.format("%s == %s\n", Arrays.toString(data), Arrays.toString(other.data));
        return Arrays.equals(data, other.data);
    }
}
