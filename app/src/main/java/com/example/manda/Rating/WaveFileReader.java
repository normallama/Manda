package com.example.manda.Rating;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class WaveFileReader {
	private String filename = null;  
    private int[][] data = null;  //二维数组
  
    private int len = 0;  
      
    private String chunkdescriptor = null; //RIFF WAVE Chunk
    static private int lenchunkdescriptor = 4;//每四个四个读取
  
    private long chunksize = 0;  //数据长度
    static private int lenchunksize = 4;  
  
    private String waveflag = null;  //判断是否为wave文件
    static private int lenwaveflag = 4;  //辅助RIFF WAVE Chunk的数据读入,占4个字节
  
    private String fmtubchunk = null;//Format Chunk，判断ID内容是否为fmt
    static private int lenfmtubchunk = 4;  //辅助读入Format Chunk的ID,内容应该是fmt，占4个字节
      
    private long subchunk1size = 0;  //fmtChunk的size
    static private int lensubchunk1size = 4;  
      
    private int audioformat = 0;  //编码方式
    static private int lenaudioformat = 2;  
      
    private int numchannels = 0;  //声道数
    static private int lennumchannels = 2;  
      
    private long samplerate = 0;  //采样率
    static private int lensamplerate = 2;  
      
    private long byterate = 0;  //音频数据传送速率
    static private int lenbyterate = 4;  
      
    private int blockalign = 0;  //数据块对齐单位（每个采样需要的字节数）
    static private int lenblockling = 2;  
      
    private int bitspersample = 0;  //每个采样需要的bit数
    static private int lenbitspersample = 2;  
      
    private String datasubchunk = null;  //判断是否为Data
    static private int lendatasubchunk = 4;  
      
    private long subchunk2size = 0;
    static private int lensubchunk2size = 4;  
      
      
    private FileInputStream fis = null;  
    private BufferedInputStream bis = null;  
      
    private boolean issuccess = false;  
      
    public WaveFileReader(String filename) {  
          
        this.initReader(filename);  
    }  
      
    // 判断是否创建wav读取器成功  
    public boolean isSuccess() {  
        return issuccess;  
    }  
      
    // 获取每个采样的编码长度，8bit或者16bit  
    public int getBitPerSample(){  
        return this.bitspersample;  
    }  
      
    // 获取采样率  
    public long getSampleRate(){  
        return this.samplerate;  
    }  
      
    // 获取声道个数，1代表单声道 2代表立体声  
    public int getNumChannels(){  
        return this.numchannels;  
    }  
      
    // 获取数据长度，也就是一共采样多少个  
    public int getDataLen(){  
        return this.len;  
    }  
      
    // 获取数据  
    // 数据是一个二维数组，[n][m]代表第n个声道的第m个采样值  
    public int[][] getData(){  
        return this.data;  
    }  
      
    private void initReader(String filename){  
        this.filename = filename;  
  
        try {  
            fis = new FileInputStream(this.filename); //读入文件 
            bis = new BufferedInputStream(fis);  //加马甲
  
            this.chunkdescriptor = readString(lenchunkdescriptor);  //读入四个字节内容
            if(!chunkdescriptor.endsWith("RIFF"))  //头四个字节为RIFF
                throw new IllegalArgumentException("RIFF miss, " + filename + " is not a wave file.");  
              
            this.chunksize = readLong();  //RIFF WAVE Chunk 的size
            this.waveflag = readString(lenwaveflag);  //读入RIFF WAVE Chunk的type，内容应该是WAVE
            if(!waveflag.endsWith("WAVE"))  
                throw new IllegalArgumentException("WAVE miss, " + filename + " is not a wave file.");  
            
            this.fmtubchunk = readString(lenfmtubchunk);  //读入Format Chunk的ID,内容应该是fmt
            if(!fmtubchunk.endsWith("fmt "))  
                throw new IllegalArgumentException("fmt miss, " + filename + " is not a wave file.");  
              
            this.subchunk1size = readLong();  //fmtChunk的size，16/18，18则有附加信息4B
            this.audioformat = readInt();  //编码方式2B
            this.numchannels = readInt();  //声道数目2B
            this.samplerate = readLong();  //采样频率4B
            this.byterate = readLong();  //音频数据传送速率4B
            this.blockalign = readInt();  //数据块对齐单位（每个采样需要的字节数）
            this.bitspersample = readInt();  //每个采样需要的Bit数
              
            this.datasubchunk = readString(lendatasubchunk);  //DataChunk ID
            if(!datasubchunk.endsWith("data"))  
                throw new IllegalArgumentException("data miss, " + filename + " is not a wave file.");  
            this.subchunk2size = readLong(); //datachunk的size ，4B
              
            this.len = (int)(this.subchunk2size/(this.bitspersample/8)/this.numchannels);  //样本数,(this.bitspersample/8)每个采样需要的bit数/8=每个采样需要的字节数，
            //this.subchunk2size显示数据大小，subchunk2size/numchannels=每个声道包含的bit数
              
            this.data = new int[this.numchannels][this.len];  //声道数，样本数
              //8位代表2的8次方--256，16位则代表2的16次方--64K。比较一下，一段相同的音乐信息，16位声卡能把它分为64K个精度单位进行处理，而8位声卡只能处理256个精度单位
            for(int i=0; i<this.len; ++i){  
                for(int n=0; n<this.numchannels; ++n){  //声道
                    if(this.bitspersample == 8){  
                        this.data[n][i] = bis.read(); //获取数据的下一个字节 
                    }
                    else if(this.bitspersample == 16){  
                        this.data[n][i] = this.readInt();  
                    }  
                }  
            }  
              
            issuccess = true;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        finally{  
            try{  
            if(bis != null)  
                bis.close();  
            if(fis != null)  
                fis.close();  
            }  
            catch(Exception e1){  
                e1.printStackTrace();  
            }  
        }  
        System.out.println("采样率：");
        System.out.println(this.getBitPerSample());
    }  
      
    private String readString(int len){  
        byte[] buf = new byte[len];  
        try {  
            if(bis.read(buf)!=len)  
                throw new IOException("no more data!!!");  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return new String(buf);  
    }  
      
    private int readInt(){  
        byte[] buf = new byte[2];  
        int res = 0;  
        try {  
            if(bis.read(buf)!=2)  
                throw new IOException("no more data!!!");  
            res = (buf[0]&0x000000FF) | (((int)buf[1])<<8);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return res;  
    }  
      
    private long readLong(){  
        long res = 0;  
        //总共读入四个字节
        try {  
            long[] l = new long[4];  
            for(int i=0; i<4; ++i){  
                l[i] = bis.read();  
                if(l[i]==-1){  
                    throw new IOException("no more data!!!");  
                }  
            }  
            res = l[0] | (l[1]<<8) | (l[2]<<16) | (l[3]<<24);  //得到RIFF WAVE Chunk size，即chunksize
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return res;  
    }  
      
    private byte[] readBytes(int len){  
        byte[] buf = new byte[len];  
        try {  
            if(bis.read(buf)!=len)  
                throw new IOException("no more data!!!");  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return buf;  
    }
}
