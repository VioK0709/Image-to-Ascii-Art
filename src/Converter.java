import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Converter implements TextGraphicsConverter {

    private int width;
    private int height;
    private double maxRatio;
    private TextColorSchema schema;
    private double ratio;

    public Converter() {
        schema = new ColorSchema();
    }

    @Override
    public String convert(String url) throws IOException, BadImageSizeException {
        BufferedImage img = ImageIO.read(new URL(url));
        maximumRatio(img);
        int newHeight;
        int newWidth;
        double scaleW = (double) img.getWidth() / width;
        double scaleH = (double) img.getHeight() / height;
        double maxScale = Math.max(scaleW, scaleH);
        if (img.getWidth() > width || img.getHeight() > height) {
            newWidth = (int) (img.getWidth() / maxScale);
            newHeight = (int) (img.getHeight() / maxScale);
        } else {
            newWidth = img.getWidth(null);
            newHeight = img.getHeight(null);
        }

        Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);
        BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphics = bwImg.createGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);
        var bwRaster = bwImg.getRaster();
        char[][] picture = new char[newWidth][newHeight];
        for (int w = 0; w < newWidth; w++) {
            for (int h = 0; h < newHeight; h++) {
                int color = bwRaster.getPixel(w, h, new int[3])[0];
                char c = schema.convert(color);
                picture[w][h] = c;
            }
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < picture.length; i++) {
            for (int j = 0; j < picture.length; j++) {
                builder.append(picture[j][i]);
                builder.append(picture[j][i]);
                builder.append(picture[j][i]);
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    private void maximumRatio(BufferedImage img) throws BadImageSizeException {
        if (img.getWidth() > width || img.getHeight() > height) {
            ratio = (double) img.getWidth(null) / (double) img.getHeight(null);
        } else {
            ratio = (double) img.getHeight(null) / (double) img.getWidth(null);
        }
        if (ratio > maxRatio && maxRatio != 0) {
            throw new BadImageSizeException(maxRatio, ratio);
        }
    }

    @Override
    public void setMaxWidth(int width) {
        this.width = width;
    }

    @Override
    public void setMaxHeight(int height) {
        this.height = height;
    }

    @Override
    public void setMaxRatio(double maxRatio) {
        this.maxRatio = maxRatio;
    }

    @Override
    public void setTextColorSchema(TextColorSchema schema) {
        this.schema = schema;
    }
}