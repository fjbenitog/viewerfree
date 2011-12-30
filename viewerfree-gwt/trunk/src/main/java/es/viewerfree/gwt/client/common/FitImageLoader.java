package es.viewerfree.gwt.client.common;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.reveregroup.gwt.imagepreloader.FitImage;
import com.reveregroup.gwt.imagepreloader.FitImageLoadEvent;
import com.reveregroup.gwt.imagepreloader.FitImageLoadHandler;

public class FitImageLoader extends Image {

	private ClickHandler clickHandler;

	private FitImageLoadHandler fitImageLoadHandler;
	
	private FitImage fitImage;

	public FitImageLoader(String loaderUrl,String url,int maxWidth, int maxHeight) {
		super(loaderUrl);
		fitImage = new FitImage(url, maxWidth, maxHeight);
		fitImage.addFitImageLoadHandler(new FitImageLoaderHandlerImpl(this));
	}

	public void addFitImageLoadHandler(FitImageLoadHandler fitImageLoadHandler){
		this.fitImageLoadHandler = fitImageLoadHandler;
	}

	private final class FitImageLoaderHandlerImpl implements FitImageLoadHandler {

		private FitImageLoader imageLoader;
		
		public FitImageLoaderHandlerImpl(FitImageLoader imageLoader) {
			super();
			this.imageLoader = imageLoader;
		}

		@Override
		public void imageLoaded(FitImageLoadEvent event) {
			Image image = event.getFitImage();
			if(clickHandler!=null){
				image.addClickHandler(clickHandler);
			}
			Widget parent = imageLoader.getParent();
			if(parent!=null && parent instanceof Panel){
				((Panel)parent).add(image);
			}
			imageLoader.removeFromParent();
			if(fitImageLoadHandler!=null){
				fitImageLoadHandler.imageLoaded(event);
			}
			image.setTitle(imageLoader.getTitle());
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
