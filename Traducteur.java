import java.lang.Integer;
import java.nio.ByteBuffer;

public class Traducteur {

    public static byte[] send(){
        short[] data = {1,4,5,6};
        int len = data.length;
        byte[] out = new byte[len*2];
        byte[] buf;
        ByteBuffer bb = ByteBuffer.allocate(len * 2);
        bb.clear();

        for(int i = 0; i < len; i++){
            //bb.putShort(out[i]);
            buf = convertToByteArray(data[i]);
            out[2*i] = buf[0];
            out[2*i+1] = buf[1];
        }

        return out;
    }

    private static byte[] convertToByteArray(short value) {

        byte[] bytes = new byte[2];
        ByteBuffer b = ByteBuffer.allocate(bytes.length);
        b.putShort(value);
        return b.array();


    }
}
