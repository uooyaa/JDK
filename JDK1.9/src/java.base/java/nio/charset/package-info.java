/*
 * Copyright (c) 2001, 2010, Oracle and/or its affiliates. All rights reserved.
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

/**
 * Defines charsets, decoders, and encoders, for translating between
 * bytes and Unicode characters.
 *
 * <blockquote><table class="borderless">
 *     <caption style="display:none">Summary of charsets, decoders, and encoders in this package</caption>
 *  <tr><th style="text-align:left">Class name</th>
 *      <th style="text-align:left"><th>DescriptiPath
 *   <tr><td style="vertical-align:top">{@link java.nio.charset.Charset}</td>
 *       <td>A named mapping between characters<br>and bytes</td></tr>
 *   <tr><td style="vertical-align:top">{@link java.nio.charset.CharsetDecoder}</td>
 *       <td>Decodes bytes into characters</td></tr>
 *   <tr><td style="vertical-align:top">{@link java.nio.charset.CharsetEncoder}</td>
 *       <td>Encodes characters into bytes</td></tr>
 *   <tr><td style="vertical-align:top">{@link java.nio.charset.CoderResult}</td>
 *       <td>Describes coder results</td></tr>
 *   <tr><td style="vertical-align:top">{@link java.nio.charset.CodingErrorAction}</td>
 *       <td>Describes actions to take when<br>coding errors are detected</td></tr>
 *
 * </table></blockquote>
 *
 * <p> A <i>charset</i> is named mapping between sequences of
 * sixteen-bit Unicode characters and sequences of bytes, in the sense
 * defined in <a
 * href="http://www.ietf.org/rfc/rfc2278.txt"><i>RFC&nbsp;2278</i></a>.
 * A <i>decoder</i> is an engine which transforms bytes in a specific
 * charset into characters, and an <i>encoder</i> is an engine which
 * transforms characters into bytes.  Encoders and decoders operate on
 * byte and character buffers.  They are collectively referred to as
 * <i>coders</i>.
 *
 * <p> The {@link java.nio.charset.Charset} class defines methods for
 * creating coders for a given charset and for retrieving the various
 * names associated with a charset.  It also defines static methods
 * for testing whether a particular charset is supported, for locating
 * charset instances by name, and for constructing a map that contains
 * every charset for which support is available in the current Java
 * virtual machine.
 *
 * <p> Most users will not use these classes directly; instead they
 * will use the existing charset-related constructors and methods in
 * the {@link java.lang.String} class, together with the existing
 * {@link java.io.InputStreamReader} and {@link
 * java.io.OutputStreamWriter} classes, all of whose implementations
 * have been reworked to make use of the charset facilities defined in
 * this package.  A small number of changes have been made to the
 * {@link java.io.InputStreamReader} and {@link
 * java.io.OutputStreamWriter} classes in order to allow explicit
 * charset objects to be specified in the construction of instances of
 * those classes.
 *
 * <p> Support for new charsets can be made available via the
 * interface defined in the {@link
 * java.nio.charset.spi.CharsetProvider} class in the {@link
 * java.nio.charset.spi} package.
 *
 * <p> Unless otherwise noted, passing a {@code null} argument to a
 * constructor or method in any class or interface in this package
 * will cause a {@link java.lang.NullPointerException
 * NullPointerException} to be thrown.
 *
 *
 * @since 1.4
 * @author Mark Reinhold
 * @author JSR-51 Expert Group
 */
package java.nio.charset;
