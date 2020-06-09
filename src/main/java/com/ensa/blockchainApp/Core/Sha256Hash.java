package com.ensa.blockchainApp.Core;

import com.google.common.io.BaseEncoding;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import static com.google.common.base.Preconditions.checkArgument;



public class Sha256Hash implements Comparable<Sha256Hash> {
    public static final int LENGTH = 32;
    public static final Sha256Hash ZERO_HASH = wrap(new byte[LENGTH]);

    private final byte[] bytes;

    public byte[] getBytes() { return bytes; }


    private Sha256Hash(byte[] rawHashBytes) {
        checkArgument(rawHashBytes.length == LENGTH);
        this.bytes = rawHashBytes;
    }

    // Wraps a hash in a Sha256Hash instance.
    public static Sha256Hash wrap(byte[] rawHashBytes) { return new Sha256Hash(rawHashBytes); }
    public static Sha256Hash wrap(String hexString) { return wrap(BaseEncoding.base16().lowerCase().decode(hexString.toLowerCase())); }

    // Creates nex instance containing the calculated hash of the given bytes.
    public static Sha256Hash of(byte[] input) { return wrap(hash(input)); }
    //TODO: Not sure of this.
    public static Sha256Hash of(String input) { return wrap(hash(BaseEncoding.base16().lowerCase().decode(input.toLowerCase()))); }

    // Calculates the SHA-256 hash of the given bytes
    public static byte[] hash(byte[] input) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);  // Can't happen.
        }
        digest.update(input, 0, input.length);
        return digest.digest();
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o ==  null || getClass() != o.getClass()) return false;
        return Arrays.equals(bytes, ((Sha256Hash)o).getBytes());
    }

    @Override
    public String toString() { return BaseEncoding.base16().encode(bytes); }

    @Override
    public int compareTo(final Sha256Hash other) {
        for (int i = LENGTH - 1; i >= 0; i--) {
            final int thisByte = this.bytes[i] & 0xff;
            final int otherByte = other.bytes[i] & 0xff;
            if (thisByte > otherByte)
                return 1;
            if (thisByte < otherByte)
                return -1;
        }
        return 0;
    }
}