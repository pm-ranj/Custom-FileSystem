import Utils.Block;
import Utils.ToWriteData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class Disk {

    private static final List<Block> blocks;
    static {
        blocks = new ArrayList<>();
    }

    static int BLOCK_SIZE = 4096 ;
    public static boolean OpenFile(String _fileName, int nbytes) {
        String filePathString = System.getProperty("user.dir") ;
        filePathString = filePathString + "\\" + _fileName ;
        File f = new File(filePathString) ;
        if(f.exists() && !f.isDirectory()) {
            System.out.println("File already exists!") ;
        } else {
            try {
                if(f.createNewFile()){
                    RandomAccessFile raf = new RandomAccessFile(f, "rw");
                    raf.seek(nbytes-1);
                    raf.write(2);

                    var chunks = nbytes/BLOCK_SIZE;
                    if (nbytes%BLOCK_SIZE > 0) {
                        chunks++;
                    }
                    for (int i=0; i<chunks; i++) {
                        blocks.add(new Block(_fileName));
                    }


                }

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
        byte[] buffer = new byte[BLOCK_SIZE] ;
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
        }
        return buffer;
    }
    public static boolean writeFile(byte[] _buffer, int _length, String _fileName,
                                    int _fileOffset) {


        String filePathString = System.getProperty("user.dir") ;
        filePathString = filePathString + "\\" + _fileName ;
        File file = new File(filePathString);

        RandomAccessFile raf = null;

        try {
            raf = new RandomAccessFile(file, "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            //temp list for current file addresses
            List<Integer> currentFileBlockPos = new ArrayList<>();
            // putting data buffer in desired block
            for(var block : blocks) {
                if (block != null){
                    if (block.filename.equals(_fileName)) {
                        currentFileBlockPos.add(blocks.indexOf(block));
                        if (blocks.indexOf(block) == _fileOffset - 1)
//                        if (block.isDataValidForWritingBytes())
                            block.fillDataBuffer(_buffer);
                    }
                }

            }

            for (var i : currentFileBlockPos) {

            }


            //writing in actual file
            raf.seek(_fileOffset * BLOCK_SIZE);
            raf.write(_buffer) ;

            raf.close() ;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true ;
    }

    public static void syncDisk(List<ToWriteData> listOfData, String filename) {
        for(var item: listOfData) {
            Disk.writeFile(item.data, item.data.length, filename, item.offset);
        }
    }

    public static void printBlock() {
        System.out.println("BLOCK SIZE IS 4096");
        for (int i=0; i<blocks.size(); i++) {
            System.out.println("blockNO. " + i + " , filename: " +blocks.get(i).filename + " ,data: " + blocks.get(i).getDataAsStringToPrint() );
        }

//        for(var item : blocks) {
//            System.out.println("filename: " + item.filename + " ,data: " + item.data);
//        }
    }
}
