package com.example.manda.Data;

import org.kymjs.kjframe.database.annotate.Id;

import java.io.Serializable;

public class NewWordsData implements Serializable {

    @Id
    private int wordId;
    private String word;
    private String interpre;

    public int getId(){return wordId;}
    public void setId(int id){wordId=id;}
    public String getwords(){return word;}
    public void setWords(String words){word=words;}
    public String getinterpre(){return interpre;}
    public void setInterpre(String i){interpre=i;}
}
