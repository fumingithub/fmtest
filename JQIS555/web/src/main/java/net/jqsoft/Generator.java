
package net.jqsoft;

import org.zcframework.generator.GeneratorFactory;

import java.io.IOException;

/**
 * 代码生成器
 * @author ThinkGem
 * @version 2013-06-21
 */
public class Generator {
    public static void main(String args[]){
    	
        GeneratorFactory factory=new GeneratorFactory();
        try {
            factory.generate();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
