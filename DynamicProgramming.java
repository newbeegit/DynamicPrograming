import java.util.ArrayList;

public class DynamicProgramming {

	/**
	 * 
	 * @param M
	 * @return
	 */
	public static ArrayList<Integer> minCostVC(int[][] M){
		ArrayList<Integer> result = new ArrayList<Integer>();
		
		int tmp[][]=M;
		//save the total path length to the first row elements
		for(int i=M.length-2; i>=0; i--){
			for(int j=0;j<M[0].length;j++){
				//left boundary
				if(j==0){
					tmp[i][j] = tmp[i][j] + Math.min(tmp[i+1][j], tmp[i+1][j+1]);
				}
				//right boundary
				else if(j==M[0].length-1){
					tmp[i][j] = tmp[i][j] + Math.min(tmp[i+1][j-1], tmp[i+1][j]);
				}
				//normal condition
				else{
					tmp[i][j] = tmp[i][j] + Math.min( Math.min(tmp[i+1][j-1], tmp[i+1][j]) ,tmp[i+1][j+1]);
				}
			}
		}
		//get minVC column of start point 
		int StartCol =0; 
		for(int i=0;i<M.length;i++){
			if(tmp[0][i]<tmp[0][StartCol]){
				StartCol = i;
			}
		}
		//path record
		result.add(0);
		result.add(StartCol);
		for(int i=1;i<tmp.length;i++){
			//left boundary
			if(StartCol==0){
				if(tmp[i][StartCol] < tmp[i][StartCol+1]){
					result.add(i);
					result.add(StartCol);
				}
				else{
					result.add(i);
					result.add(StartCol+1);
				}
			}
			//right boundary
			else if(StartCol== (tmp.length-1)){
				if(tmp[i][StartCol] < tmp[i][StartCol-1]){
					result.add(i);
					result.add(StartCol);
				}
				else{
					result.add(i);
					result.add(StartCol+1);
				}
			}
			else{
				int smallestCol = StartCol-1;
				for(int j=StartCol;j<=StartCol+1;j++){
					if(tmp[i][j]<tmp[i][smallestCol]){
						smallestCol = j;
					}
				}
				result.add(i);
				result.add(smallestCol);
			}
		}
		return result;
	}
	
	
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public static String stringAlignment(String x, String y){
		String minCost="";
		if(x.length()==y.length()){
			minCost = x;
		}
		else{
			
		}
		return minCost;
	}
	
	
	private static int cost(char a, char b){
		int result =0; 
		if(a=='$' || b =='$'){
			result = 4;
		}
		else if(a!=b){
			result = 2;
		}
		return result;
	}
}
