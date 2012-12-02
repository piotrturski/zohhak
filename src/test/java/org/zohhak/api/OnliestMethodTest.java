package org.zohhak.api;


import org.junit.runner.RunWith;
import org.zohhak.api.TestWith;
import org.zohhak.api.runners.ZohhakRunner;

@RunWith(ZohhakRunner.class)
public class OnliestMethodTest {

	@TestWith("1")
	public void onliestMethod(int i){}
	
}
