package es.viewerfree.gwt.client.viewer;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import es.viewerfree.gwt.shared.dto.AlbumDto;

public class ShowImageHandler implements ClickHandler {

	private AlbumDto albumDto;

	private int selectedPic;
	
	private ImagesPanel imagesPanel;

	public ShowImageHandler(ImagesPanel imagesPanel, AlbumDto albumDto,int selectedPic) {
		this.imagesPanel = imagesPanel;
		this.albumDto = albumDto;
		this.selectedPic = selectedPic;
	}
	@Override

	public void onClick(ClickEvent event) {
		getSlidePanel().show();
	}

	private SlidePanel getSlidePanel(){
		albumDto.setSelectedPic(selectedPic);
		return new SlidePanel(imagesPanel,albumDto);
	}

}
