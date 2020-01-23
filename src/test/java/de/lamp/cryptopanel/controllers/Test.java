package de.lamp.cryptopanel.controllers;

import junit.framework.TestResult;

public interface Test {
    public abstract int CountTestCases();
    public abstract void run(TestResult result);
}
