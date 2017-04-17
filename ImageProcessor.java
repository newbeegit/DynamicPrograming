import java.awt.Color;

public class ImageProcessor {

	Picture picture;
	int W, H;
	Color[][] OriginalPic;
	
	/**
	 * 
	 * @param imageFile
	 */
	public ImageProcessor(String imageFile){
		picture = new Picture(imageFile);
		W = picture.width();
		H = picture.height();
		OriginalPic = new Color[H][W];
		
		for(int i=0;i<H;i++){
			for(int j=0;j<W;j++){
				//Pixel[x; y] refers to the pixel in xth column and yth row
				OriginalPic[i][j] = picture.get(j, i);
			}
		}
	}
	
	/**
	 * 
	 * @param x
	 * @return
	 */
	public Picture reduceWidth(double x){
		
	}
	
	/**
	 * 
	 * @param M
	 * @return
	 */
	private int[][] YImportance(Color[][] M){
		int[][] N = new int[H][W];
		for(int i=0;i<H;i++){
			for(int j=0;j<W;j++){
				if(i==0){
					N[i][j] = Dist(M[H-1][j], M[i+1][j]);
				}
				else if(i==(H-1)){
					N[i][j] = Dist(M[i-1][j], M[0][j]);
				}
				else{
					N[i][j] = Dist(M[i-1][j], M[i+1][j]);
				}
			}
		}
		return N;
	}
	
	private int[][] XImportance(Color[][] M){
		int[][] N = new int[H][W];
		for(int i=0;i<H;i++){
			for(int j=0;j<W;j++){
				if(j==0){
					N[i][j] = Dist(M[i][W-1], M[i][j+1]);
				}
				else if(j==(W-1)){
					N[i][j] = Dist(M[i][0], M[i][j-1]);
				}
				else{
					N[i][j] = Dist(M[i][j-1], M[i][j+1]);
				}
			}
		}
		return N;
	}
	
	private int[][] Importance(Color[][] M){
		int[][] X = XImportance(M);
		int[][] Y = YImportance(M);
		int[][] result = new int[H][W]; 
		for(int i=0;i<H;i++){
			for(int j=0;j<W;j++){
				result[i][j] = X[i][j] + Y[i][j];
			}
		}
		return result;
	}
	
	private int Dist(Color p, Color q){
		int r = p.getRed() - q.getRed();
		int g = p.getGreen() - q.getGreen();
		int b = p.getBlue() - q.getBlue();
		int result = (int) (Math.pow(r, 2) + Math.pow(g, 2) + Math.pow(b, 2));
		return result;
	}
}
