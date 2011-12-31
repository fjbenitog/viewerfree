package es.viewerfree.gwt.client.common;

import java.util.HashSet;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.reveregroup.gwt.imagepreloader.FitImage;
import com.reveregroup.gwt.imagepreloader.FitImageLoadEvent;
import com.reveregroup.gwt.imagepreloader.FitImageLoadHandler;

public class FitImageLoader extends Image  implements Handler{

	private FitImage fitImage;
	
	public FitImage getFitImage() {
		return fitImage;
	}

	private static HashSet<String> URLS = new HashSet<String>();
	
	private String url;
	

	public FitImageLoader(String loaderUrl,String url,int maxWidth, int maxHeight) {
		super(loaderUrl);
		this.url = url;
		fitImage = new FitImage(url, maxWidth, maxHeight);
		fitImage.addFitImageLoadHandler(new FitImageLoaderHandlerImpl(this));
		addAttachHandler(this);
	}
	
	public FitImageLoader(String loaderUrl,String url) {
		super(loaderUrl);
		this.url = url;
		fitImage = new FitImage(url);
		fitImage.addFitImageLoadHandler(new FitImageLoaderHandlerImpl(this));
		addAttachHandler(this);
	}
	
	@Override
	public void onAttachOrDetach(AttachEvent event) {
		if(URLS.contains(url) && event.isAttached()){
			updatePanel(fitImage, this);
		}
		
	}

	private final class FitImageLoaderHandlerImpl implements FitImageLoadHandler {

		private FitImageLoader imageLoader;
		
		public FitImageLoaderHandlerImpl(FitImageLoader imageLoader) {
			super();
			this.imageLoader = imageLoader;
		}

		@Override
		public void imageLoaded(FitImageLoadEvent event) {
			URLS.add(url);
			Image image = event.getFitImage();
			updatePanel(image,imageLoader);
		}
	}
	
	private void updatePanel(Image image,FitImageLoader imageLoader) {
		Widget parent = imageLoader.getParent();
		if(parent!=null && parent instanceof Panel){
			((Panel)parent).add(image);
			parent.setSize(image.getWidth()+"px", image.getHeight()+"px");
		}
		imageLoader.removeFromParent();
		image.setTitle(imageLoader.getTitle());
	}
	
	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return fitImage.addClickHandler(handler);
	}

	public HandlerRegistration addFitImageAttachHandler(Handler handler) {
		return fitImage.addAttachHandler(handler);
	}

}
