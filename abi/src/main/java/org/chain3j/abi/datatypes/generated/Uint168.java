package org.chain3j.abi.datatypes.generated;

import java.math.BigInteger;
import org.chain3j.abi.datatypes.Uint;

/**
 * Auto generated code.
 * <p><strong>Do not modifiy!</strong>
 * <p>Please use org.chain3j.codegen.AbiTypesGenerator in the 
 * <a href="https://github.com/chain3j/chain3j/tree/master/codegen">codegen module</a> to update.
 */
public class Uint168 extends Uint {
    public static final Uint168 DEFAULT = new Uint168(BigInteger.ZERO);

    public Uint168(BigInteger value) {
        super(168, value);
    }

    public Uint168(long value) {
        this(BigInteger.valueOf(value));
    }
}