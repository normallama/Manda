package com.example.manda.Data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.NotNull;

@Entity
public class NewWordsData {
    @Id(autoincrement = true)
    private Long wordId;

    @Index(unique = true)
    private String word;  //单词
    private String interpre;   //解释
    @NotNull
    private Long mywordId;//在USerdata引用
    @Generated(hash = 182521305)
    public NewWordsData(Long wordId, String word, String interpre,
            @NotNull Long mywordId) {
        this.wordId = wordId;
        this.word = word;
        this.interpre = interpre;
        this.mywordId = mywordId;
    }
    @Generated(hash = 660094573)
    public NewWordsData() {
    }
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
    public void setInterpre(String interpre) {
        this.interpre = interpre;
    }
    public Long getMywordId() {
        return this.mywordId;
    }
    public void setMywordId(Long mywordId) {
        this.mywordId = mywordId;
    }


}
