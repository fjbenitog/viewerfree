package es.viewerfree.gwt.server.image;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.AutoCloseInputStream;

public class VFImage {

	private BufferedImage image;

	private VFImage(BufferedImage image) {
		super();
		this.image = image;
	}

	public VFImage(String file) throws FileNotFoundException, IOException{
		image = ImageIO.read(new AutoCloseInputStream(new FileInputStream(file)));
	}

	public VFImage scaleImage(int width, int height){
		Image thumbnail = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		BufferedImage bufferedThumbnail = new BufferedImage(thumbnail.getWidth(null),
				thumbnail.getHeight(null),
				BufferedImage.TYPE_INT_RGB);
		bufferedThumbnail.getGraphics().drawImage(thumbnail, 0, 0, null);
		return new VFImage(bufferedThumbnail);
	}

	public void writeImage(String file) throws IOException{
		FileOutputStream output = new FileOutputStream(file);
		ImageIO.write(image, "jpeg",output);
		IOUtils.closeQuietly(output);
	}

	public VFImage rotateImage(int angle){
		if (image == null){throw new IllegalArgumentException("\"image\" param cannot be null.");}
		final int imageWidth = image.getWidth();
		final int imageHeight = image.getHeight();
		final Map<String, Integer> boundingBoxDimensions = calculateRotatedDimensions(imageWidth, imageHeight, angle);

		final int newWidth = boundingBoxDimensions.get("width");
		final int newHeight = boundingBoxDimensions.get("height");

		final BufferedImage newImage = new BufferedImage(newWidth, newHeight, image.getType());
		final Graphics2D newImageGraphic = newImage.createGraphics();

		final AffineTransform transform = new AffineTransform();
		transform.setToTranslation((newWidth-imageWidth)/2, (newHeight-imageHeight)/2);
		transform.rotate(Math.toRadians(angle), imageWidth/2, imageHeight/2);
		newImageGraphic.drawImage(image, transform, null);
		newImageGraphic.dispose();
		return new VFImage(newImage);
	}

	private static Map<String, Integer> calculateRotatedDimensions(final int imageWidth, final int imageHeight, final int angle) {
		final Map<String, Integer> dimensions = new HashMap<String, Integer>();
		// coordinates of our given image
		final int[][] points = {
				{0, 0},
				{imageWidth, 0},
				{0, imageHeight},
				{imageWidth, imageHeight}
		};

		final Map<String, Integer> boundBox = new HashMap<String, Integer>(){{
			put("left", 0);
			put("right", 0);
			put("top", 0);
			put("bottom", 0);
		}};

		final double theta = Math.toRadians(angle);

		for (final int[] point : points) {
			final int x = point[0];
			final int y = point[1];
			final int newX = (int) (x * Math.cos(theta) + y * Math.sin(theta));
			final int newY = (int) (x * Math.sin(theta) + y * Math.cos(theta));

			//assign the bounds
			boundBox.put("left", Math.min(boundBox.get("left"), newX));
			boundBox.put("right", Math.max(boundBox.get("right"), newX));
			boundBox.put("top", Math.min(boundBox.get("top"), newY));
			boundBox.put("bottom", Math.max(boundBox.get("bottom"), newY));
		}

		// now get the dimensions of the new box.
		dimensions.put("width", Math.abs(boundBox.get("right") - boundBox.get("left")));
		dimensions.put("height", Math.abs(boundBox.get("bottom") - boundBox.get("top")));
		return dimensions;
	}

	public double getWidth(){
		return image.getWidth();
	}

	public double getHeight(){
		return image.getHeight();
	}
}
