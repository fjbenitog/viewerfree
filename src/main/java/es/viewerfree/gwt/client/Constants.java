package es.viewerfree.gwt.client;


public interface Constants extends com.google.gwt.i18n.client.Constants {
  

  /**
   * Translated "images/flags/".
   * 
   * @return translated "images/flags/"
   */
  @DefaultStringValue("images/flags/")
  @Key("flagsPath")
  String flagsPath();

  /**
   * Translated "GNU General Public License (GPL)".
   * 
   * @return translated "GNU General Public License (GPL)"
   */
  @DefaultStringValue("GNU General Public License (GPL)")
  @Key("gnuLincense")
  String gnuLincense();


  @DefaultStringArrayValue("es, en")
  @Key("languange")
  String[] languange();

  /**
   * Translated "Javier Benito".
   * 
   * @return translated "Javier Benito"
   */
  @DefaultStringValue("Javier Benito")
  @Key("owner")
  String owner();

  /**
   * Translated "http://javi.viewerfree.es".
   * 
   * @return translated "http://javi.viewerfree.es"
   */
  @DefaultStringValue("http://javi.viewerfree.es")
  @Key("ownerURL")
  String ownerURL();

  /**
   * Translated "http://sourceforge.net/projects/viewerfree/".
   * 
   * @return translated "http://sourceforge.net/projects/viewerfree/"
   */
  @DefaultStringValue("http://sourceforge.net/projects/viewerfree/")
  @Key("projectURL")
  String projectURL();

  /**
   * Translated "3.5.3-SNAPSHOT".
   * 
   * @return translated "3.5.3-SNAPSHOT"
   */
  @DefaultStringValue("3.5.3-SNAPSHOT")
  @Key("softwareVersion")
  String softwareVersion();

  /**
   * Translated "Viewerfree".
   * 
   * @return translated "Viewerfree"
   */
  @DefaultStringValue("Viewerfree")
  @Key("viewerfree")
  String viewerfree();
  
  @DefaultStringValue("images/viewer/")
  @Key("viewerImagesPath")
  String viewerImagesPath();
  
  @DefaultStringValue("Viewer.html")
  @Key("viewerAppPath")
  String viewerAppPath();
  
  @DefaultStringValue("folder_huge.png")
  @Key("imageHugeFolder")
  String imageHugeFolder();
  
  @DefaultStringValue("folder.png")
  @Key("imageFolder")
  String imageFolder();
  
  @DefaultIntValue(150)
  @Key("imageThumbnailSize")
  int imageThumbnailSize();
  
  @DefaultIntValue(400)
  @Key("imageSize")
  int imageSize();
  
  @DefaultIntValue(800)
  @Key("imageLoaderSize")
  int imageLoaderSize();
  
  @DefaultStringValue("ajax-loader.gif")
  @Key("imageLoader")
  String imageLoader();
  
  @DefaultStringValue("big-ajax-loader.gif")
  @Key("imageLoaderBig")
  String imageLoaderBig();
  
  @DefaultStringValue("imageService")
  @Key("imageService")
  String imageService();
}
