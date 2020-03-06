package id.kineticstreamer.tests;

import id.kineticstreamer.annotations.Streamed;

public class StringMessage {

    @Streamed
    public String data;

    public StringMessage(String data) {
        this.data = data;
    }
    
}
