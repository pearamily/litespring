package org.litespring.test.v1;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.FileSystemResource;
import org.litespring.core.io.Resource;

import java.io.InputStream;

public class ResourceTest {
    @Test
    public  void testClassPathResoruce() throws  Exception{
        Resource r = new ClassPathResource("petstore-v1.xml");
        InputStream is = null;

        try {
            is = r.getInputStream();
            //the full testCase will test the inputStream and compare it with content of the true file
            //this place we will use simple test case instead
            Assert.assertNotNull(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }


    }

    @Test
    public void testFileSystemResource() throws  Exception{
        //this place is not a good style?
        //this test case will not be use in other computer's location
        Resource r = new FileSystemResource("/Users/liuligong/IdeaProjects/litespring/src/test/resources/petstore-v1.xml");
        InputStream is = null;

        try {
            is = r.getInputStream();
            Assert.assertNotNull(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }



    }
}
