package Test;

import DiskManager.Disk;

public class Main {
    public static void main(String[] args) {
        String fileName = "MEGAtemp1" ;
        String fileName2 = "MEGAtemp2" ;
        String fileName3 = "MEGAtemp3" ;
        Disk.OpenFile(fileName, 1900);
        DiskManager.Disk.OpenFile(fileName2, 1000) ;
        DiskManager.Disk.OpenFile(fileName3, 15000) ;

        Disk.printBlock();

        byte[] buffer = new byte[48] ;
        buffer[0] = 100;
        buffer[1] = 101;
        buffer[9] = 111;
        buffer[11] = 112;

        Disk.writeFile(buffer, buffer.length, fileName, 1) ;
        buffer[0] = 110;
        buffer[3] = 121;
        buffer[19] = 110;
        buffer[10] = 122;
        Disk.writeFile(buffer, buffer.length, fileName, 3) ;

        //read buffer data
        byte[] buff = Disk.readFile(fileName, 0) ;
        PrintBytes(buff, buff.length) ;



        byte[] buffer2 = new byte[2] ;
        buffer2[0] = 100 ;
        buffer2[1] = 101 ;
        DiskManager.Disk.writeFile(buffer2, buffer2.length, fileName2, 2) ;
        buffer2[0] = 111 ;
        buffer2[1] = 110 ;
        DiskManager.Disk.writeFile(buffer2, buffer2.length, fileName2, 1) ;
        byte[] buff2 = DiskManager.Disk.readFile(fileName2, 3) ;

        System.out.println("read bytes are: ");
        Main.PrintBytes(buff2, buff2.length);

        byte[] buffer3 = new byte[2] ;
        buffer3[0] = 100 ;
        buffer3[1] = 101 ;
        DiskManager.Disk.writeFile(buffer3, buffer3.length, fileName3, 1) ;
        buffer3[0] = 111 ;
        buffer3[1] = 110 ;
        DiskManager.Disk.writeFile(buffer3, buffer3.length, fileName3, 3) ;
        byte[] buff3 = DiskManager.Disk.readFile(fileName3, 4) ;
        Main.PrintBytes(buff3, buff3.length);


        Disk.printBlock();
    }
    public static void PrintBytes(byte[] _bytes, int _length) {
        for(int i = 0 ; i < _length ; i++) {
            System.out.print(_bytes[i] + " ") ;
        }
    }
}
