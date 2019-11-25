package com.example.manda.Data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class NewWordsData {
    @Id(autoincrement = true)
    private Long wordId;

    @Index(unique = true)
    private String word;  //单词
    private String interpre;   //解释

    @Generated(hash = 1619944378)
    public NewWordsData(Long wordId, String word, String interpre) {
        this.wordId = wordId;
        this.word = word;
        this.interpre = interpre;
    }
    @Generated(hash = 660094573)
    public NewWordsData() {
    }

    public void setInterpre(String i){interpre=i;}
    public Long getWordId() {
        return this.wordId;
    }
    public void setWordId(Long wordId) {
        this.wordId = wordId;
    }
    public String getWord() {
        return this.word;
    }
    public void setWord(String word) {
        this.word = word;
    }
    public String getInterpre() {
        return this.interpre;
    }
}
