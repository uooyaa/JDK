/*
 * Copyright (c) 1995, 2001, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */


package org.omg.CORBA;

import org.omg.CORBA.portable.Streamable;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

/**
 * The Holder for {@code Char}. For more information on
 * Holder files, see <a href="doc-files/generatedfiles.html#holder">
 * "Generated Files: Holder Files"</a>.<P>
 * A Holder class for a {@code char}
 * that is used to store "out" and "inout" parameters in IDL methods.
 * If an IDL method signature has an IDL {@code char} as an "out"
 * or "inout" parameter, the programmer must pass an instance of
 * {@code CharHolder} as the corresponding
 * parameter in the method invocation; for "inout" parameters, the programmer
 * must also fill the "in" value to be sent to the server.
 * Before the method invocation returns, the ORB will fill in the
 * value corresponding to the "out" value returned from the server.
 * <P>
 * If {@code myCharHolder} is an instance of {@code CharHolder},
 * the value stored in its {@code value} field can be accessed with
 * {@code myCharHolder.value}.
 *
 * @since       JDK1.2
 */
public final class CharHolder implements Streamable {

    /**
     * The {@code char} value held by this {@code CharHolder}
     * object.
     */
    public char value;

    /**
     * Constructs a new {@code CharHolder} object with its
     * {@code value} field initialized to {@code 0}.
     */
    public CharHolder() {
    }

    /**
     * Constructs a new {@code CharHolder} object for the given
     * {@code char}.
     * @param initial the {@code char} with which to initialize
     *                the {@code value} field of the new
     *                {@code CharHolder} object
     */
    public CharHolder(char initial) {
        value = initial;
    }

    /**
     * Reads from {@code input} and initalizes the value in
     * this {@code CharHolder} object
     * with the unmarshalled data.
     *
     * @param input the InputStream containing CDR formatted data from the wire
     */
    public void _read(InputStream input) {
        value = input.read_char();
    }

    /**
     * Marshals to {@code output} the value in
     * this {@code CharHolder} object.
     *
     * @param output the OutputStream which will contain the CDR formatted data
     */
    public void _write(OutputStream output) {
        output.write_char(value);
    }

    /**
     * Returns the {@code TypeCode} object  corresponding
     * to the value held in
     * this {@code CharHolder} object.
     *
     * @return    the TypeCode of the value held in
     *            this {@code CharHolder} object
     */
    public org.omg.CORBA.TypeCode _type() {
        return ORB.init().get_primitive_tc(TCKind.tk_char);
    }
}
