package no.webtech.objectserializeutil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.zip.GZIPOutputStream;


public class SnapperUtil {

    /**
     * Utility method that search for "searchFor"-bytes in buffer, starting from
     * iStartBufferOffset
     *
     * @param buffer
     * @param searchFor
     * @param iStartBufferOffset
     * @return -1 if not found, else the offset plus the length of the searchFor
     *         byte array
     */
    public static int searchFor(byte[] buffer, byte[] searchFor, int iStartBufferOffset) {
        int iWrk = iStartBufferOffset;
        int iX;

        if (buffer == null)
            throw new NullPointerException("buffer is null");
        if (searchFor == null)
            throw new NullPointerException("searchFor is null");
        if (iStartBufferOffset < 0)
            throw new IllegalArgumentException("iStartBufferOffset < 0");
        if (iStartBufferOffset > buffer.length)
            throw new IllegalArgumentException("iStartBufferOffset > buffer.length");

        while (iWrk < buffer.length - searchFor.length +1) {
            iX = -1;
            boolean testRemaining = iX < searchFor.length - 1;
            boolean sofarCorrect = true;

            while (testRemaining && sofarCorrect) {
                iX++;
                sofarCorrect = (buffer[iWrk + iX] == searchFor[iX]);
                testRemaining = iX < searchFor.length - 1;
            }
            if (testRemaining == false && sofarCorrect) {
                return iWrk + iX + 1;
            }
            iWrk++;
        }
        return -1;
    }


    /**
     * Creates a base64 encoded stringBuffer representation of the actual object
     * @param toBeSerialized
     * @return the string buffer
     */
    public static StringBuffer createSerializedFormattedOutput(
            Serializable toBeSerialized) {
        StringBuffer sb = new StringBuffer();

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(new GZIPOutputStream(baos));
            oos.writeObject(toBeSerialized);
            oos.close();

            sb.append("-----BEGIN SERIALIZED "
                    + toBeSerialized.getClass().getCanonicalName() + " "
                    + "-----\r\n");
            char[] ca2 = Base64Encoder.encodeAsCharArray(baos.toByteArray());
            for (int start = 0; start < ca2.length; start += 60) {
                int iLen = 60;
                if (iLen > ca2.length - start)
                    iLen = ca2.length - start;
                sb.append(ca2, start, iLen);
                sb.append("\r\n");
            }
            sb.append("-----END SERIALIZED-----\r\n");
        } catch (NotSerializableException e) { // Will not happen since argument is Serializable
            sb.append("Sorry - Exception occured, ")
                    .append(toBeSerialized.getClass().getCanonicalName())
                    .append(" did throw NotSerializableException.");
        } catch (IOException e) {
            // Never happens since we are using byte arrays..
            e.printStackTrace();
        }
        return sb;
    }
}