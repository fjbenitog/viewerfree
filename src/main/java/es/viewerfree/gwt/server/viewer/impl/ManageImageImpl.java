package es.viewerfree.gwt.server.viewer.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import magick.ImageInfo;
import magick.MagickImage;
import es.viewerfree.gwt.server.viewer.ManageImage;


public class ManageImageImpl implements ManageImage {

	private float _memoryLimit;
	
	public synchronized  void resize(String inImage,String outImage,int height) throws Exception {
		ImageInfo origInfo = new ImageInfo(inImage); //load image info
		MagickImage image = new MagickImage(origInfo); //load image
		int width = (int) (image.getDimension().getWidth()*(height/image.getDimension().getHeight()));
		image = image.scaleImage(width, height); //to Scale image
		image.setFileName(outImage); //give new location
		image.writeImage(origInfo);
		image.destroyImages();
	}
	
	public float getMemoryLimit() {
		return _memoryLimit;
	}

	public void setMemoryLimit(float memoryLimit) {
		_memoryLimit = memoryLimit;
	}
	
	public void getDefaultImage(OutputStream out) throws IOException{

		int width = 300; int height = 300;
		BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

		Font font = new Font("SansSerif",Font.BOLD,32);
		Graphics graphics = bi.getGraphics();
		graphics.setFont(font);
		graphics.setColor(Color.LIGHT_GRAY);
		graphics.fillRect(0, 0, width, height);
		graphics.setColor(Color.BLACK);
		graphics.drawRect(0, 0, width-1, height-1);
		graphics.drawString("Image", 90, 130);
		graphics.drawString("No Preview", 25, 190);
		ImageIO.write( bi, "jpg", out );
	}

	@Override
	public void rotate(String inImage,String outImage,int angle) throws Exception {
			ImageInfo origInfo = new ImageInfo(inImage); //load image info
			MagickImage image = new MagickImage(origInfo); //load image
			image = image.rotateImage(angle);
			image.setFileName(outImage); //give new location
			image.writeImage(origInfo);
			image.destroyImages();
		
	}
	
	public static void main(String arg[]) throws Exception{
//		System.setProperty("jmagick.systemclassloader","false");
//		System.setProperty("java.library.path", "/usr/local/lib");
		ImageInfo origInfo = new ImageInfo("./pictures/dc-comcs.jpg"); //load image info
		MagickImage image = new MagickImage(origInfo); //load image
		int height = 500;
		int width = (int) (image.getDimension().getWidth()*(height/image.getDimension().getHeight()));
		image = image.scaleImage(width, height); //to Scale image
		image.setFileName("./pictures/dc-comcs-rotate.jpg"); //give new location
		image.writeImage(origInfo); //save

//		FileOutputStream outputStream = new FileOutputStream(new File("./pictures/DSC_9869-rotate-2.png"));
//		ManageImageImpl manageImage = new ManageImageImpl();
//		manageImage.setMemoryLimit(1024);
//		manageImage.rotate("./pictures/DSC_9869.jpg", 90, outputStream);
//		manageImage.resize("./pictures/DSC_9869.jpg", 100, outputStream);
//		manageImage.resize("./pictures/DSC_9869-rotate.png", 1000, outputStream);
		
	}


}
