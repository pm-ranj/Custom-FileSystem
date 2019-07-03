# Implementing A Simple File-System
**Introduction** 

The goal is to obtain experience with file systems. A file system is a core component of most operating systems and the implementation of this system will provide experience designing complex systems. File systems, by their nature, incorporate aspects of naming, fault-tolerance, concurrency control, and storage management. In addition, issues of security and networking appear in more ambitious designs.
We are going to build a disk emulation library that will emulate a disk on top of a single file. Its interface includes functions to read, write, and flush disk blocks.

**Dependencies**

    *JDK 11 +
    *json-simple-1.1.1.jar   

***Disk library***

A file system is able to store its data in a persistent manner. The persistent medium will be disk. This code is designing a simple disk library that reads and writes 4-Kbyte disk blocks. File system will be built on top of this interface.
Disk library will divide the file into 4-Kbyte blocks. Blocks will be written using a "block number".

**Block DataStructure**

Each block data structures saves data of each file and name of each file


## **Code Samples**

There are some explanation about methods and its usage (the code is fluent commenting)

**Opening File**

 `public static boolean OpenFile(String _fileName, int nbytes) { }`
 
 This method is using for reading and writing and returns a descriptor used to access the disk


                          
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

**ReadFile**

there are two methods that reads from file:

**1.** Reading the whole data from file:

`public static byte[] readFile(String _fileName, int _fileOffset) { }`

this method gets "filename" and "fileoffset" to read the whole data of file

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
         
         

**2.** Reading a specific block's data:
it gets index of block and read whole data of block.

    public static byte[] readBlock(int index) {
             //returning the wanted index of data blocks of memory
             return blocks.get(index).data;
         }


**WriteFile**

    public static boolean writeFile(byte[] _buffer, int _length, String _fileName, int _fileOffset) { }

Writes into disk block:
       
      try {
                  //temp list for current file addresses
                  List<Integer> currentFileBlockPos = new ArrayList<>();
                  // putting data buffer in desired block
                  for(var block : blocks) {
                      if (block != null){
                          if (block.filename.equals(_fileName)) {
                              currentFileBlockPos.add(blocks.indexOf(block));
                              if (blocks.indexOf(block) == _fileOffset - 1)
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
      
**SyncDisk**      
                  
    public static void syncDisk(List<ToWriteData> listOfData, String filename) { }

forces all outstanding writes to disk:

     for(var item: listOfData) {
              Disk.writeFile(item.data, item.data.length, filename, item.offset);
     }

**DiskUtilities Class (ensuring consistency)** 

DiskUtilities class saves the current state of operation. For instance, when the operation get interrupted(due to power consumption or unexpected reasons).it also loads previous state of the disk on the system startup.


it uses JSON for formatting the data for save and load functions.

    public void saveDiskState() {
             String json = serialize().toJSONString();
             try {
                 var file = LoadOrCreateFile();
                 FileOutputStream fis = new FileOutputStream(file);
                 fis.write(json.getBytes());
                 fis.close();
     
             } catch (IOException e) {
                 e.printStackTrace();
             }
     
         }
     
         public List<Block> LoadDiskState() throws Exception {
     
             var file = LoadOrCreateFile();
             BufferedReader reader = new BufferedReader(new FileReader(file));
             var jsonStr = reader.readLine();
             reader.close();
     
             if (jsonStr != null) {
                     blocks = new ArrayList<>();
                     var obj = deserialize(jsonStr);
                     var jsonArray = (List)obj.get("list");
                     for (int i=0; i<jsonArray.size(); i++) {
                         var b = new Block();
                         b.deserialize( jsonArray.get(i).toString());
                         blocks.add(b);
                     }
     
                     return blocks;
             } else {
                 return null;
             }



> **Note:** For using JSON functionalities in java this project uses the "json-simple1.1.1" library which is attached to this project. it should be add to this priject before running.

**Serializable interface**

For the usage of JSON serializing for the classes an Interface is provided with two methods,

          JSONObject serialize();
          JSONObject deserialize(String str) throws ParseException;
          
 
Which is implemented on desired classes to be formatted in JSON.

it has two defferent implementation on the Block and DiskUtilities classes.

For instance in the Block class deserialize function in implemented in a way to change the class attribute values and on the other hand on the DiskUtilities class it just parse the given String into a JSONObject

***Block:***

     @Override
     public JSONObject deserialize(String str) throws ParseException {
 
         JSONParser parser = new JSONParser();
         var json = (JSONObject)parser.parse(str);
         this.filename = (String) json.get("filename");
         this.data = buildDataFromString((String) json.get("data"));
 
         return json;
     }

***DiskUtilities:***

        @Override
         public JSONObject deserialize(String str) throws ParseException {
             JSONParser parser = new JSONParser();
     
             JSONObject obj = (JSONObject)parser.parse(str);
             return obj;
         }     

