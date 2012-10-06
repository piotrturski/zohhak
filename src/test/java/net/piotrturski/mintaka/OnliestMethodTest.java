package net.piotrturski.mintaka;

import net.piotrturski.mintaka.runners.MintakaRunner;

import org.junit.runner.RunWith;

@RunWith(MintakaRunner.class)
public class OnliestMethodTest {

	@TestWith("1")
	public void onliestMethod(int i){}
	
}
