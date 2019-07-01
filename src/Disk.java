import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Disk {
    static int STD_BLOCK_SIZE = 2 ;
    public static boolean OpenFile(String _fileName) {
        String filePathString = System.getProperty("user.dir") ;
        filePathString = filePathString + "\\" + _fileName ;
        File f = new File(filePathString) ;
        if(f.exists() && !f.isDirectory()) {
            System.out.println("File already exists!") ;
        } else {
            try {
                if(f.createNewFile())
                    System.out.println("File is created!");
            } catch (IOException e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return true ;
    }
    public static byte[] readFile(String _fileName, int _fileOffset) {
        String filePathString = System.getProperty("user.dir") ;
        filePathString = filePathString + "\\" + _fileName ;
        File file = new File(filePathString) ;
        byte[] buffer = new byte[STD_BLOCK_SIZE] ;
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(file, "r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            raf.seek(_fileOffset) ;
            raf.read(buffer) ;
            raf.close() ;
        } catch (IOException e) {
            e.printStackTrace();
        }return buffer;
    }
    public static boolean writeFile(byte[] _buffer, int _length, String _fileName,
                                    int _fileOffset) {
        String filePathString = System.getProperty("user.dir") ;
        filePathString = filePathString + "\\" + _fileName ;
        File file = new File(filePathString) ;
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(file, "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            raf.seek(_fileOffset) ;
            raf.write(_buffer) ;
            raf.close() ;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true ;
    }
}
