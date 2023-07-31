// package main.java.com.kchan.chromascan.model;
// package main.java.com.chromascan.model;

// import java.awt.Color;
// import java.awt.image.BufferedImage;
// import java.awt.image.ColorModel;
// import java.io.File;
// import java.io.IOException;
// import javax.imageio.ImageIO;

// /**
//  * Models Source object which stores the source file path
//  * and the buffered image.
//  */
// public class Source {
//     private File srcImgFile;
//     private BufferedImage image;

//     public Source(String src){
//         try {
//             this.srcImgFile = new File(src);
//             image = ImageIO.read(this.srcImgFile);
//         } catch (IOException e) {
//             System.out.println(e);
//         }
//     }

//     public File getSourceImg(){
//         return this.srcImgFile;
//     }

//     public BufferedImage getImage(){
//         return this.image;
//     }

//     public Rgb getRgbFromPosition(int x, int y){
//         int pixel = image.getRGB(x, y);
//         Color color = new Color(pixel, true);
//         return new Rgb(color.getRed(), color.getGreen(), color.getBlue());
//     }
// }
