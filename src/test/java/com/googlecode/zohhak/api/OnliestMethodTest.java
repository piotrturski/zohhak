package com.googlecode.zohhak.api;


import org.junit.runner.RunWith;

import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;

@RunWith(ZohhakRunner.class)
public class OnliestMethodTest {

	@TestWith("1")
	public void onliestMethod(int i){}
	
}
