package com.apass.esp.utils;

import org.apache.commons.jexl2.Main;

class Test1 {
	public void draw(){
		System.out.println("Test1 draw()");
	}
	
	public Test1(){
		System.out.println("Test1() before draw()");
		draw();
		System.out.println("Test1() after draw()");
	}
	
}

class Test2 extends Test1{
	private int radius = 1;
	Test2(int i){
		radius = i;
		System.out.println("Test2.Test2(),radius="+radius);
	}
	
	public void draw(){
		System.out.println("Test2.draw(),radius="+radius);
	}
	
}
public class Test{
	public static void main(String[] args) {
		new Test2(5);
	}
}
