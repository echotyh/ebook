package com.xiaotangbao.ebook.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author      SunJianwei<327021593@qq.com>
 * @date        16-6-1 14:53
 */
public class PdfUtil {

    /**
     * 获取pdf的指定页，返回图像数据
     *
     * @param   file            pdf的File对象
     * @param   page            要渲染的页数
     * @return                  读出的图像数据
     * @throws  IOException
     */
    public static BufferedImage getPage(File file, int page) throws IOException {
        PDDocument document = PDDocument.load(file);
        try {
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            BufferedImage image = pdfRenderer.renderImageWithDPI(page - 1, 100, ImageType.RGB);
            return image;
        } catch (Exception e) {

        } finally {
            document.close();
        }
        return null;
    }
    
}
