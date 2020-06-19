/*
 * Copyright 2020 lambdaprime
 */
/**
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