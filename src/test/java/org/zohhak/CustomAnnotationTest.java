package org.zohhak;

import static org.fest.assertions.api.Assertions.assertThat;


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.zohhak.helper.CustomTest;
import org.zohhak.runners.MintakaRunner;

//@RunWith(MintakaRunner.class)
public class CustomAnnotationTest {

	private static int runMethods;
	
	@BeforeClass
	public static void before() {
		runMethods = 0;
	}
	
	@AfterClass
	public static void after() {
		assertThat(runMethods).isEqualTo(1);
	}
	
	@CustomTest("1")
	public void customAnnotation(int i) {
		runMethods ++;
		assertThat(i).isEqualTo(7);
	}
	
}
