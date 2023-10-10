package com.wdk.test;

import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author : Windok
 * @date: 2023-09-26
 * @Description:
 * @version: 1.0
 */
public class ImageTest {

    @Test
    void test(){

        try {
            BufferedImage image = ImageIO.read(new File("src/images/bulletD.gif"));
            assertNotNull(image);

            BufferedImage image2 = ImageIO.read(ImageTest.class.getClassLoader().getResourceAsStream("images/bulletD.gif"));
            assertNotNull(image2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
