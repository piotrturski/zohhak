package com.googlecode.zohhak.parallel;

import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;
import org.junit.Test;
import org.junit.experimental.ParallelComputer;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;

public class ParallelTest {

    @Test
    public void test() {
        Class[] cls={ParallelTest1.class};

        //Parallel among classes
        JUnitCore.runClasses(ParallelComputer.classes(), cls);

        //Parallel among methods in a class
        JUnitCore.runClasses(ParallelComputer.methods(), cls);

        //Parallel all methods in all classes
        JUnitCore.runClasses(new ParallelComputer(true, true), cls);
    }


    @RunWith(ZohhakRunner.class)
    public static class ParallelTest1 {

        @TestWith("a\nb")
        public void a(String a){}
    }

}

