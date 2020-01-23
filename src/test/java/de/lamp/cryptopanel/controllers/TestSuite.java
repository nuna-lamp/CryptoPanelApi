package de.lamp.cryptopanel.controllers;

import junit.framework.TestResult;

import java.util.Enumeration;
import java.util.Vector;

public class TestSuite implements Test{
    private String fname;
    private Vector fTests = new Vector(10);

    public Enumeration tests() {
        return fTests.elements();
    }
    @Override
    public int CountTestCases() {
        int count= 0;
        for (Enumeration e = tests(); e.hasMoreElements(); ) {
            Test test= (Test)e.nextElement();
            count= count + test.CountTestCases();
        }
        return count;
    }

    @Override
    public void run(TestResult result) {

    }

    public void runTest(Test test, TestResult result) {
        test.run(result);
    }
}
