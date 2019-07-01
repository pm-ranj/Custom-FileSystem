import DiskManager.Disk;

public class Main {
    public static void main(String[] args) {
        String fileName = "MEGAtemp2" ;
//        String fileName2 = "MEGAtemp3" ;
//        String fileName3 = "MEGAtemp4" ;
        Disk.OpenFile(fileName, 900);
//        DiskManager.Disk.OpenFile(fileName2, 1000) ;
//        DiskManager.Disk.OpenFile(fileName3, 15000) ;

        Disk.printBlock();

        byte[] buffer = new byte[4] ;
        buffer[0] = 100 ;
        buffer[1] = 101 ;
        Disk.writeFile(buffer, buffer.length, fileName, 1) ;
        buffer[0] = 111 ;
        buffer[1] = 110 ;
        Disk.writeFile(buffer, buffer.length, fileName, 3) ;
        byte[] buff = Disk.readFile(fileName, 0) ;


//        Disk.readFile()
//        PrintBytes(buff, buff.length) ;



//        byte[] buffer2 = new byte[2] ;
//        buffer2[0] = 100 ;
//        buffer2[1] = 101 ;
//        DiskManager.Disk.writeFile(buffer2, buffer2.length, fileName2, 10) ;
//        buffer2[0] = 111 ;
//        buffer2[1] = 110 ;
//        DiskManager.Disk.writeFile(buffer2, buffer2.length, fileName2, 5) ;
//        byte[] buff2 = DiskManager.Disk.readFile(fileName2, 6) ;
//
//
//        byte[] buffer3 = new byte[2] ;
//        buffer3[0] = 100 ;
//        buffer3[1] = 101 ;
//        DiskManager.Disk.writeFile(buffer3, buffer3.length, fileName3, 10) ;
//        buffer3[0] = 111 ;
//        buffer3[1] = 110 ;
//        DiskManager.Disk.writeFile(buffer3, buffer3.length, fileName3, 5) ;
//        byte[] buff3 = DiskManager.Disk.readFile(fileName3, 6) ;


        Disk.printBlock();
    }
    public static void PrintBytes(byte[] _bytes, int _length) {
        for(int i = 0 ; i < _length ; i++) {
            System.out.println(_bytes[i]) ;
        }
    }
}
