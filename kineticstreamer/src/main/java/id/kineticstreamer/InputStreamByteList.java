/*
 * Copyright 2020 lambdaprime
 */

package id.kineticstreamer;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Creates an InputStream from list of bytes
 */
public class InputStreamByteList extends InputStream {

    public Iterator<Integer> iterator;
    
    public InputStreamByteList(List<Integer> output) {
        this.iterator = output.iterator();
    }

    public InputStreamByteList(String byteString) {
        this(Pattern.compile(",").splitAsStream(byteString)
            .map(String::strip)
            .mapToInt(b -> Integer.valueOf(b, 16))
            .boxed()
            .collect(toList()));
    }

    @Override
    public int read() throws IOException {
        return iterator.next();
    }
}
