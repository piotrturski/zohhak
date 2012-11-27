package org.zohhak;


import org.junit.runner.RunWith;
import org.zohhak.TestWith;
import org.zohhak.runners.MintakaRunner;

@RunWith(MintakaRunner.class)
public class OnliestMethodTest {

	@TestWith("1")
	public void onliestMethod(int i){}
	
}
