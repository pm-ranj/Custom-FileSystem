package Utils;

public class Block {
    public byte[] data;
    public String filename = "AKVAR";


    private final int  size = 4096;

    public Block(String filename) {
        this.filename = filename;
        data  = new byte[size];
    }

    public Block() {
        data  = new byte[size];
    }

    public String getDataAsStringToPrint() {
        String res = "";
        for (byte b : data) {
            res += (b + " ");
        }
        return res;
    }
    // to fill data byte buffer with buffers with less than Global buffer size
    public void fillDataBuffer(byte [] bytes){
        for (int i=0; i<bytes.length; i++) {
            data[i] = bytes[i];
        }
    }

    public boolean isDataValidForWritingBytes() {
        for (byte b : data) {
            if(b != 0) {
                return false;
            }
        }

        return true;
    }


}
