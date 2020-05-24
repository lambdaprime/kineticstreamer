package id.kineticstreamer.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import id.kineticstreamer.OutputStreamByteList;

public class OutputStreamByteListTest {

    @Test
    public void test() throws Exception {
        try (var out = new OutputStreamByteList()) {
            out.write(-16);
            out.write(0);
            out.write(126);
            assertEquals("f0, 00, 7e", out.asHexString());
        }
    }
}
