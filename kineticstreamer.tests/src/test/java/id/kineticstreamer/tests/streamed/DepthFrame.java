package id.kineticstreamer.tests.streamed;

import java.util.Arrays;

import id.kineticstreamer.annotations.Streamed;

public class DepthFrame {

    @Streamed
    public double[] data;

    public DepthFrame() {

    }

    public DepthFrame(double... data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object obj) {
        var other = (DepthFrame) obj;
        System.out.format("%s == %s\n", Arrays.toString(data), Arrays.toString(other.data));
        return Arrays.equals(data, other.data);
    }
}
