package DiskManager;

import Utils.Block;
import Utils.ToWriteData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class Disk {

    public static int BLOCK_SIZE = 128;

    private static List<Block> blocks;

    static {

       // blocks = new ArrayList<>();

        var diskUtils = new DiskUtilities(blocks);

        try {
            blocks = diskUtils.LoadDiskState();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (blocks == null) {
            System.out.println("null loaded");
            blocks = new ArrayList<>();
        }
    }



    public static boolean OpenFile(String _fileName, int nbytes) {
        //resolving path
        String filePathString = System.getProperty("user.dir") ;
        filePathString = filePathString + "\\" + _fileName ;


        File f = new File(filePathString) ;
        /**
         * if the file exits or given path is not a file
         * then just printing a warning
         * otherwise the file will created
         *
         * it reserves proper chunks in block DS
         * and puts data buffers into the desired chunks
         */
        if(f.exists() && !f.isDirectory()) {
            System.out.println("File already exists!") ;
        } else {
            try {
                if(f.createNewFile()){
                    RandomAccessFile raf = new RandomAccessFile(f, "rw");

                    //nbytes-1 for the end of the file due to set desired size of nbytes by putting a random byte into it
                    raf.seek(nbytes-1);
                    raf.write(2);
                    //calculate proper chunk size
                    var chunks = nbytes/BLOCK_SIZE;
                    if (nbytes%BLOCK_SIZE > 0) {
                        chunks++;
                    }

                    //putting buffers into chunks
                    for (int i=0; i<chunks; i++) {
                        blocks.add(new Block(_fileName));
                    }

                    //save the disk state for the consistency
                    DiskUtilities utils = new DiskUtilities(blocks);
                    utils.saveDiskState();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true ;
    }
    public static byte[] readFile(String _fileName, int _fileOffset) {

        byte[] buffer = new byte[BLOCK_SIZE];


        // searching through blocks
        for(var block : blocks) {
            if (block != null){
                //find the first equivalent filename in the block DS
                if (block.filename.equals(_fileName)) {
                    //searching the desired offset of file dat chunk
                    if (blocks.indexOf(block) == _fileOffset - 1)
                    {
                        buffer = block.data;
                    }

                }
            }
        }
        return buffer;
    }
    public static byte[] readBlock(int index) {
        //returning the wanted index of data blocks of memory
        return blocks.get(index).data;
    }
    public static boolean writeFile(byte[] _buffer, int _length, String _fileName,
                                    int _fileOffset) {
        //path resolving
        String filePathString = System.getProperty("user.dir") ;
        filePathString = filePathString + "\\" + _fileName ;

        // creating a file out of resolved path
        File file = new File(filePathString);

        //instantiating a file streamer
        //using RandomAccessFile due to its ability to seek over file
        RandomAccessFile raf = null;


        try {
            raf = new RandomAccessFile(file, "rw");

            // putting data buffer in desired block
            for(var block : blocks) {
                if (block != null){
                    if (block.filename.equals(_fileName)) {
                        if (blocks.indexOf(block) == _fileOffset - 1)
                            //comment out code below for not to overwriting
                            //if (block.isDataValidForWritingBytes())
                            block.fillDataBuffer(_buffer);
                    }
                }

            }

            //writing in actual file
            raf.seek(_fileOffset * BLOCK_SIZE);
            raf.write(_buffer);
            raf.close();

            // saving the state for consistency
            var diskUtils = new DiskUtilities(blocks);
            diskUtils.saveDiskState();

        } catch (IOException e) {
            e.printStackTrace();
        }

        //return true if everything goes right :)
        return true;
    }

    // writes all given data to memory block by filenames
    // ToWriteData is a Data structure for saving both desired offset and actual data buffer
    public static void syncDisk(List<ToWriteData> listOfData, String filename) {
        for(var item: listOfData) {
            Disk.writeFile(item.data, item.data.length, filename, item.offset);
        }
    }

    //depicting the blocks of Disk
    public static void printBlock() {
        System.out.println("******************");
        System.out.println("BLOCK SIZE IS 4096");
        System.out.println("******************");

        for (int i=0; i<blocks.size(); i++) {
            System.out.println("blockNO. " + i + " , filename: " +blocks.get(i).filename + " ,data: " + blocks.get(i).getDataAsStringToPrint() );
        }

    }



}
