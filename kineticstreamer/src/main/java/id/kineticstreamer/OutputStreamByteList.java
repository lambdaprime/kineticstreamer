package id.kineticstreamer;

import static java.util.stream.Collectors.joining;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import id.xfunction.XByte;

/**
 * Converts all data which is written into, to a List of
 * string bytes so that writing "abc" will result to:
 * [61, 62, 63] and "hello" to [68, 65, 6c, 6c, 6f]
 */
public class OutputStreamByteList extends OutputStream {

    public List<Integer> output = new ArrayList<>();
    
    @Override
    public void write(int v) throws IOException {
        output.add(v);
    }

    public String asHexString() {
        return output.stream()
                .map(i -> XByte.toHex(i.byteValue()))
                .collect(joining(", "));
    }
}
