public class Main {
    public static void main(String[] args) {
        String fileName = "temp" ;
        Disk.OpenFile(fileName) ;
        byte[] buffer = new byte[2] ;
        buffer[0] = 100 ;
        buffer[1] = 101 ;
        Disk.writeFile(buffer, buffer.length, fileName, 10) ;
        buffer[0] = 111 ;
        buffer[1] = 110 ;
        Disk.writeFile(buffer, buffer.length, fileName, 5) ;
        byte[] buff = Disk.readFile(fileName, 6) ;
        PrintBytes(buff, buff.length) ;
    }
    public static void PrintBytes(byte[] _bytes, int _length) {
        for(int i = 0 ; i < _length ; i++) {
            System.out.println(_bytes[i]) ;
        }
    }
}
