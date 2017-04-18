import java.awt.Color;
import java.util.ArrayList;

public class ImageProcessor {

	Picture picture, output;
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
		if(x>1){
			System.out.println("x should be smaller than 1 to reduce");
		}
		int reduceNum = (int) Math.ceil(W*(1-x));
		
		//System.out.println("H: "+ H +" W: " + W);
		
		//int outputWidth= W-reduceNum;
		output = new Picture(W-reduceNum, H);
		
		for(int i=0;i<reduceNum;i++){
			int[][] input = Importance(OriginalPic);
			ArrayList<Integer> reducePixels = DynamicProgramming.minCostVC(input);
			
			Color[][] newPic = new Color[H][W-1];
			for(int j=0;j<reducePixels.size()/2;j++){ 
				int delete= reducePixels.get(2*j+1);
				System.out.println("delete pixel: "+ j + " "+ delete);
				for(int k=0;k<W-1;k++){
					if(k<delete){
						//Color tmpColor = OriginalPic[j][k];
						//output.set(k, j, tmpColor);
						//newPic[j][k] = OriginalPic[j][k];
						newPic[j][k] = OriginalPic[j][k];
					}
					else  if(k>=delete){
						//Color tmpColor = OriginalPic[j][k+1];
						//output.set(k, j, tmpColor); 
						newPic[j][k] = OriginalPic[j][k+1];
					}
					//System.out.println("newPic["+j+"]["+k+"] "+newPic[j][k]+" orig["+j+"]["+k+"]: "+OriginalPic[j][k]);
				}
			}
			OriginalPic = new Color[H][W-1];
			OriginalPic = newPic;
			W = W-1;
		}
		
		for(int i=0;i<OriginalPic.length;i++){
			for(int j=0;j<OriginalPic[0].length;j++){
				Color tmpColor = OriginalPic[i][j];
				output.set(j, i, tmpColor);
			}
		}
		
		return output;
	}
	
	
	
	
	
	/**
	 * 
	 * @param M
	 * @return
	 */
	private int[][] YImportance(Color[][] M){
		int height, width;
		height = M.length;
		width = M[0].length;
		int[][] N = new int[height][width];
		for(int i=0;i<height;i++){
			for(int j=0;j<width;j++){
				if(i==0){
					N[i][j] = Dist(M[height-1][j], M[i+1][j]);
				}
				else if(i==(height-1)){
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
		int height, width;
		height = M.length;
		width = M[0].length;
		int[][] N = new int[height][width];
		for(int i=0;i<height;i++){
			for(int j=0;j<width;j++){
				if(j==0){
					N[i][j] = Dist(M[i][width-1], M[i][j+1]);
				}
				else if(j==(width-1)){
					N[i][j] = Dist(M[i][0], M[i][j-1]);
				}
				else{
					N[i][j] = Dist(M[i][j-1], M[i][j+1]);
				}
				//System.out.println("row: "+i+" col: "+j);
			}
		}
		return N;
	}
	
	private int[][] Importance(Color[][] M){
		int height, width;
		height = M.length;
		width = M[0].length;
		int[][] X = XImportance(M);
		int[][] Y = YImportance(M);
		int[][] result = new int[height][width]; 
		for(int i=0;i<height;i++){
			for(int j=0;j<width;j++){
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
	
	public static void main(String[] args) {
		ImageProcessor image = new ImageProcessor("./Original.jpg");
		//image.picture.show();
		Picture tmp = image.reduceWidth(0.73);
		tmp.show();
	}
}
