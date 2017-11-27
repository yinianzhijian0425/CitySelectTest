package tech.yunjing.biconlife.jniplugin.view.photoView;

/**
 * 待展示图片对象
 * Created by sun.li on 2017/9/29.
 */

public class ImageInfoObj {

    /** 控件宽度*/
    private int viewWidth;

    /** 控件高度*/
    private int viewHeight;

    /** x坐标*/
    private int xCoordinate;

    /** y坐标*/
    private int yCoordinate;

    /** 图片URL地址*/
    private String url;


    public int getViewWidth() { return viewWidth; }

    public void setViewWidth(int viewWidth) { this.viewWidth = viewWidth; }

    public int getViewHeight() { return viewHeight; }

    public void setViewHeight(int viewHeight) { this.viewHeight = viewHeight; }

    public int getxCoordinate() { return xCoordinate; }

    public void setxCoordinate(int xCoordinate) { this.xCoordinate = xCoordinate; }

    public int getyCoordinate() { return yCoordinate; }

    public void setyCoordinate(int yCoordinate) { this.yCoordinate = yCoordinate; }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }
}
