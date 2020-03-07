package id.kineticstreamer.tests.streamed;

import java.util.Objects;

import id.kineticstreamer.annotations.Streamed;

public class StringMessage {

    @Streamed
    public String data;

    public StringMessage() {
    }

    public StringMessage(String data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object obj) {
        StringMessage other = (StringMessage) obj;
        System.out.format("%s == %s\\n", data, other.data);
        return Objects.equals(data, other.data);
    }
}
