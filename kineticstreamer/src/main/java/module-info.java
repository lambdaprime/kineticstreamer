/*
 * Copyright 2020 lambdaprime
 */
/**
 * <p>kineticstreamer - Java module to do (de)serialization of Java objects into streams.
 * By default it supports object conversion to stream of bytes and back. But it is extendible
 * to any other format as well.</p>
 * 
 * <h1>Stream</h1>
 * <p>In terms on kineticstreamer Stream represents sequence of any sort (sequence of bytes,
 * words, etc). The type of elements in such sequence don't need to be the same. Streams have flat
 * schemas opposite to JSON, XML, etc.</p>
 * <p>Example of streams are CSV files.</p>
 * 
 * <p>It parses object tree and allows you to get control over how types are going to be
 * (de)serialized.</p>
 * 
 * <p>Only fields annotated with @Streamed annotation will be (de)serialized.</p> 
 * 
 * <h1>Arrays</h1>
 *
 * <p><b>kineticstreamer</b> does not support serialization of arrays of primitive types. If you still
 * need to use primitive type arrays please use their wrapped version (Integer[], Long[], etc).
 *
 * <p>Any array A serialized in following format:</p>
 *
 * kineticDataOutput.writeInt(A.length) | write(a[0]) | ... | write(a[N])
 *
 * <p>Where write call will be dispatched based on item types: if they primitive it will go to
 * KineticDataOutput if they complex then it will go to KineticStreamWriter.</p>
 * 
 */
module id.kineticstreamer {
    exports id.kineticstreamer;
    exports id.kineticstreamer.annotations;
    exports id.kineticstreamer.streams;
    requires id.xfunction;
}