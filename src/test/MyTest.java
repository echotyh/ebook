package test;

import com.xiaotangbao.ebook.dao.BookTypeDao;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author SunJianwei<327021593@qq.com>
 * @date 16-5-17 10:46
 */
public class MyTest {

    @Test
    public void test02() throws Exception {
        Date date = new Date();
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
    }

    @Test
    public void test01() throws IOException {
        String pdfFilename = "/home/vg/Java NIO.pdf";
        PDDocument document = PDDocument.load(new File(pdfFilename));
        PDFRenderer pdfRenderer = new PDFRenderer(document);
        //BufferedImage bim = pdfRenderer.renderImage(10);
        // note that the page number parameter is zero based
        BufferedImage bim = pdfRenderer.renderImageWithDPI(10, 300, ImageType.RGB);
        // suffix in filename will be used as the file format
        //ImageIOUtil.writeImage(bim, "/home/vg/temp/pdfbox/JavaNIO" + ".png", 700);
        OutputStream outputStream = new FileOutputStream(new File("/home/vg/PDF"));
        //ImageIOUtil.writeImage(bim, "/home/vg/temp/pdfbox/JavaNIO" + "-" + ".png", outputStream);
        ImageIO.write(bim, "png", outputStream);


        document.close();
    }

}
