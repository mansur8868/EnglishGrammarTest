package vn.edu.uit.a15520275.model;

import java.io.Serializable;

/**
 * Created by admin on 10/31/2017.
 */

public class Test implements Serializable {
    private String idTest;
    private String test;
    private short isCompleted;

    public Test() {
    }

    public Test(String idTest, String test, short isCompleted) {
        this.idTest = idTest;
        this.test = test;
        this.isCompleted = isCompleted;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getIdTest() {
        return idTest;
    }

    public void setIdTest(String idTest) {
        this.idTest = idTest;
    }

    public short isCompleted() {
        return isCompleted;
    }

    public void setCompleted(short completed) {
        isCompleted = completed;
    }
}
