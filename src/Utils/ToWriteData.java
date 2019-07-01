package Utils;

public class ToWriteData {
    public byte[] data;
    public int offset;

    public ToWriteData(int bufferSize, int offset) {
        data = new byte[bufferSize];
        this.offset = offset;
    }

}
