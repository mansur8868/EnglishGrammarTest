package vn.edu.uit.a15520275.model;

import java.io.Serializable;

/**
 * Created by admin on 11/3/2017.
 */

public class TestContent implements Serializable {
    private String idTestContent;
    private String content;
    private String A;
    private String B;
    private String C;
    private String D;
    private short key;

    public TestContent() {
    }

    public TestContent(String idTestContent, String content, String a, String b, String c, String d, short key) {
        this.idTestContent = idTestContent;
        this.content = content;
        A = a;
        B = b;
        C = c;
        D = d;
        this.key = key;
    }

    public String getIdTestContent() {
        return idTestContent;
    }

    public void setIdTestContent(String idTestContent) {
        this.idTestContent = idTestContent;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getA() {
        return A;
    }

    public void setA(String a) {
        A = a;
    }

    public String getB() {
        return B;
    }

    public void setB(String b) {
        B = b;
    }

    public String getC() {
        return C;
    }

    public void setC(String c) {
        C = c;
    }

    public String getD() {
        return D;
    }

    public void setD(String d) {
        D = d;
    }

    public short getKey() {
        return key;
    }

    public void setKey(short key) {
        this.key = key;
    }
}
