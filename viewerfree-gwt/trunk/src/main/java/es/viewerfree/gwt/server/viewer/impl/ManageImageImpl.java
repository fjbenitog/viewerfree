package es.viewerfree.gwt.server.viewer.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.IOException;
import java.io.OutputStream;

import javax.media.jai.ImageLayout;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.RenderedOp;
import javax.media.jai.operator.SubsampleAverageDescriptor;

import com.sun.media.jai.codec.FileSeekableStream;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageEncoder;
import com.sun.media.jai.codec.JPEGEncodeParam;

import es.viewerfree.gwt.server.viewer.ManageImage;


public class ManageImageImpl implements ManageImage {

	
	private float _memoryLimit;
	
	public synchronized  void resize(String file,int height,OutputStream out) throws IOException {
		
		RenderedOp loaded = JAI.create("stream", new ParameterBlock().add(new FileSeekableStream(file)));
		 
        RenderingHints qualityHints = new RenderingHints(null);
        qualityHints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        
        ImageLayout imgLayout = new ImageLayout();
        setTileSizeForSubsampleAverage(imgLayout, loaded, Math.max(1,  loaded.getHeight() / height), getMemoryLimit());
        qualityHints.put(JAI.KEY_IMAGE_LAYOUT, imgLayout);

		int width = loaded.getWidth();
		float scale = (float)height/(float)width;
		if(scale>1) scale=1;
        
        RenderedOp transformed = SubsampleAverageDescriptor.create(loaded, new Double(scale), new Double(scale), qualityHints);
        JAI.create("encode", new ParameterBlock().addSource(transformed).add(out).add("JPEG"), qualityHints);
	}
	
	/**
     * Prepare an image layout to use as rendering hint for a subsample average descriptor, to prevent out of memory
     * @autor DeadlyPredator
     * @param imgLayout A existing image layout, only tile size will be changed
     * @param sourceImage The source image of the subsample average descriptor
     * @param subSamplingFactor The subSampling factor (big image size / small image size) eg 2 for a 50% resize
     * @param memoryLimitMb The memory limit for a chuck of the image, in mb. Depends on how much memory you have available, and how much you use for the rest of your program
     */
    public static void setTileSizeForSubsampleAverage(ImageLayout imgLayout, RenderedImage sourceImage, int subSamplingFactor, float memoryLimitMb) {
        if(imgLayout == null || sourceImage == null || subSamplingFactor <= 0 || memoryLimitMb <= 0) {
            throw new IllegalArgumentException();
        }
        int pixelCostBits = 0;
        for(int componentSize: sourceImage.getColorModel().getComponentSize()) {
            pixelCostBits += componentSize;
        }
 
        int newSize = Math.max((int)Math.sqrt((memoryLimitMb * (8L * 1024L * 1024L)) / pixelCostBits) / subSamplingFactor, 1);
        imgLayout.setTileHeight(newSize);
        imgLayout.setTileWidth(newSize);
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
		PlanarImage planarimage = PlanarImage.wrapRenderedImage(bi);
		// We need a sample model. The most appropriate is created as shown:
		Font font = new Font("SansSerif",Font.BOLD,40);
		Graphics graphics = bi.getGraphics();
		graphics.setFont(font);
		graphics.setColor(Color.LIGHT_GRAY);
		graphics.fillRect(0, 0, width, height);
		graphics.setColor(Color.BLACK);
		graphics.drawRect(0, 0, width-1, height-1);
		graphics.drawString("Imagen", 90, 130);
		graphics.drawString("No Disponible", 25, 190);
		JPEGEncodeParam encodeParam = new JPEGEncodeParam();
		ImageEncoder encoder = ImageCodec.createImageEncoder("JPEG", out, encodeParam);
		encoder.encode(planarimage);
	}


}
