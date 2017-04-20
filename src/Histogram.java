package net.codejava.upload;

import java.awt.image.BufferedImage;
import java.io.File;
//import ChartPanel;
import javax.imageio.ImageIO;
/**@author Nguyen, Truong Thanh
 * @author 1009851 <p>
 * get pixel from image
 * generate histogram array data for CMY*/
public class Histogram {
	private int[] th = new int[256];
	private int[] ch = new int[256];
	private int[] sh = new int[256];
	private int[] nh = new int[256];
	public Histogram(File file) throws Exception{
		this.Cal(file);
	}
	/** Convert from RGB to CMY*/
    public void Cal(File file) throws Exception {
        BufferedImage image = ImageIO.read( file);
        for(int x = 0; x < image.getWidth(); x++)			
            for(int y = 0; y < image.getHeight(); y++) {
                int color = image.getRGB(x, y);				
                int red = (color & 0x00ff0000) >> 16;
                int green = (color & 0x0000ff00) >> 8;
                int blue = color & 0x000000ff;
                ch[(255 - red)]++;						
                sh[(255 - green)]++;
                nh[(255 - blue)]++;
            }
        /**print out 3 matrix of CMY*/
        for(int i = 0; i < ch.length; i++)				
                	System.out.println("cyan[" + i + "] = " + ch[i]);
        for(int j = 0; j < sh.length; j++)
        			System.out.println("magenta[" + j + "] = " + sh[j]);
        for(int m =0; m < nh.length; m++)
        			System.out.println("yellow[" + m + "] = " + nh[m]);
        int k=0;
        for(int i = 0; i < ch.length; i++)
                	th[k]=(int) ch[i];
                	k++;
                }
    /**Get Matrix of Cyan,Magenta,Yellow
     * return array ch,sh and nh */
    public int[] GetHis1(){								
    	return ch;
    	}
    public int[] GetHis2(){
        return sh;
        }
    public int[] GetHis3(){
        return nh;
        }
}