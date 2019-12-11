package com.example.manda.Rating;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class WaveFileReader {
	private String filename = null;  
    private int[][] data = null;  //��ά����
  
    private int len = 0;  
      
    private String chunkdescriptor = null; //RIFF WAVE Chunk
    static private int lenchunkdescriptor = 4;//ÿ�ĸ��ĸ���ȡ
  
    private long chunksize = 0;  //���ݳ���
    static private int lenchunksize = 4;  
  
    private String waveflag = null;  //�ж��Ƿ�Ϊwave�ļ�
    static private int lenwaveflag = 4;  //����RIFF WAVE Chunk�����ݶ���,ռ4���ֽ�
  
    private String fmtubchunk = null;//Format Chunk���ж�ID�����Ƿ�Ϊfmt
    static private int lenfmtubchunk = 4;  //��������Format Chunk��ID,����Ӧ����fmt��ռ4���ֽ�
      
    private long subchunk1size = 0;  //fmtChunk��size
    static private int lensubchunk1size = 4;  
      
    private int audioformat = 0;  //���뷽ʽ
    static private int lenaudioformat = 2;  
      
    private int numchannels = 0;  //������
    static private int lennumchannels = 2;  
      
    private long samplerate = 0;  //������
    static private int lensamplerate = 2;  
      
    private long byterate = 0;  //��Ƶ���ݴ�������
    static private int lenbyterate = 4;  
      
    private int blockalign = 0;  //���ݿ���뵥λ��ÿ��������Ҫ���ֽ�����
    static private int lenblockling = 2;  
      
    private int bitspersample = 0;  //ÿ��������Ҫ��bit��
    static private int lenbitspersample = 2;  
      
    private String datasubchunk = null;  //�ж��Ƿ�ΪData
    static private int lendatasubchunk = 4;  
      
    private long subchunk2size = 0;
    static private int lensubchunk2size = 4;  
      
      
    private FileInputStream fis = null;  
    private BufferedInputStream bis = null;  
      
    private boolean issuccess = false;  
      
    public WaveFileReader(String filename) {  
          
        this.initReader(filename);  
    }  
      
    // �ж��Ƿ񴴽�wav��ȡ���ɹ�  
    public boolean isSuccess() {  
        return issuccess;  
    }  
      
    // ��ȡÿ�������ı��볤�ȣ�8bit����16bit  
    public int getBitPerSample(){  
        return this.bitspersample;  
    }  
      
    // ��ȡ������  
    public long getSampleRate(){  
        return this.samplerate;  
    }  
      
    // ��ȡ����������1�������� 2����������  
    public int getNumChannels(){  
        return this.numchannels;  
    }  
      
    // ��ȡ���ݳ��ȣ�Ҳ����һ���������ٸ�  
    public int getDataLen(){  
        return this.len;  
    }  
      
    // ��ȡ����  
    // ������һ����ά���飬[n][m]�����n�������ĵ�m������ֵ  
    public int[][] getData(){  
        return this.data;  
    }  
      
    private void initReader(String filename){  
        this.filename = filename;  
  
        try {  
            fis = new FileInputStream(this.filename); //�����ļ� 
            bis = new BufferedInputStream(fis);  //�����
  
            this.chunkdescriptor = readString(lenchunkdescriptor);  //�����ĸ��ֽ�����
            if(!chunkdescriptor.endsWith("RIFF"))  //ͷ�ĸ��ֽ�ΪRIFF
                throw new IllegalArgumentException("RIFF miss, " + filename + " is not a wave file.");  
              
            this.chunksize = readLong();  //RIFF WAVE Chunk ��size
            this.waveflag = readString(lenwaveflag);  //����RIFF WAVE Chunk��type������Ӧ����WAVE
            if(!waveflag.endsWith("WAVE"))  
                throw new IllegalArgumentException("WAVE miss, " + filename + " is not a wave file.");  
            
            this.fmtubchunk = readString(lenfmtubchunk);  //����Format Chunk��ID,����Ӧ����fmt
            if(!fmtubchunk.endsWith("fmt "))  
                throw new IllegalArgumentException("fmt miss, " + filename + " is not a wave file.");  
              
            this.subchunk1size = readLong();  //fmtChunk��size��16/18��18���и�����Ϣ4B
            this.audioformat = readInt();  //���뷽ʽ2B
            this.numchannels = readInt();  //������Ŀ2B
            this.samplerate = readLong();  //����Ƶ��4B
            this.byterate = readLong();  //��Ƶ���ݴ�������4B
            this.blockalign = readInt();  //���ݿ���뵥λ��ÿ��������Ҫ���ֽ�����
            this.bitspersample = readInt();  //ÿ��������Ҫ��Bit��
              
            this.datasubchunk = readString(lendatasubchunk);  //DataChunk ID
            if(!datasubchunk.endsWith("data"))  
                throw new IllegalArgumentException("data miss, " + filename + " is not a wave file.");  
            this.subchunk2size = readLong(); //datachunk��size ��4B
              
            this.len = (int)(this.subchunk2size/(this.bitspersample/8)/this.numchannels);  //������,(this.bitspersample/8)ÿ��������Ҫ��bit��/8=ÿ��������Ҫ���ֽ�����
            //this.subchunk2size��ʾ���ݴ�С��subchunk2size/numchannels=ÿ������������bit��
              
            this.data = new int[this.numchannels][this.len];  //��������������
              //8λ����2��8�η�--256��16λ�����2��16�η�--64K���Ƚ�һ�£�һ����ͬ��������Ϣ��16λ�����ܰ�����Ϊ64K�����ȵ�λ���д�����8λ����ֻ�ܴ���256�����ȵ�λ
            for(int i=0; i<this.len; ++i){  
                for(int n=0; n<this.numchannels; ++n){  //����
                    if(this.bitspersample == 8){  
                        this.data[n][i] = bis.read(); //��ȡ���ݵ���һ���ֽ� 
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
        System.out.println("�����ʣ�");
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
        //�ܹ������ĸ��ֽ�
        try {  
            long[] l = new long[4];  
            for(int i=0; i<4; ++i){  
                l[i] = bis.read();  
                if(l[i]==-1){  
                    throw new IOException("no more data!!!");  
                }  
            }  
            res = l[0] | (l[1]<<8) | (l[2]<<16) | (l[3]<<24);  //�õ�RIFF WAVE Chunk size����chunksize
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
