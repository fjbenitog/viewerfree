package es.viewerfree.gwt.client.common;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.reveregroup.gwt.imagepreloader.ImageLoadEvent;
import com.reveregroup.gwt.imagepreloader.ImageLoadHandler;
import com.reveregroup.gwt.imagepreloader.ImagePreloader;

public class ImageLoader extends Image {

	private ClickHandler clickHandler;

	private ImageLoadHandler imageLoadHandler;

	public ImageLoader(String loaderUrl,String url) {
		super(loaderUrl);
		ImagePreloader.load(url, new ImageLoaderHandlerImpl(this));
	}

	public void addImageLoadHandler(ImageLoadHandler imageLoadHandler){
		this.imageLoadHandler = imageLoadHandler;
	}

	private final class ImageLoaderHandlerImpl implements ImageLoadHandler {

		private ImageLoader imageLoader;
		public ImageLoaderHandlerImpl(ImageLoader imageLoader) {
			super();
			this.imageLoader = imageLoader;
		}

		@Override
		public void imageLoaded(ImageLoadEvent event) {
			Image image = event.takeImage();
			if(clickHandler!=null){
				image.addClickHandler(clickHandler);
			}
			Widget parent = imageLoader.getParent();
			if(parent!=null && parent instanceof Panel){
				((Panel)parent).add(image);
			}
			imageLoader.removeFromParent();
			if(imageLoadHandler!=null){
				imageLoadHandler.imageLoaded(event);
			}
		}

	}

	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		this.clickHandler = handler;
		return new HandlerRegistration() {

			@Override
			public void removeHandler() {
				clickHandler = null;
			}
		};
	}



}
