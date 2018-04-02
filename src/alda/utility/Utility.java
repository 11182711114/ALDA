package alda.utility;

import java.util.Arrays;

public class Utility {
	
	public static void main(String[] args){
		String[] strings = {"Ett", "Två", "Tree","Fyra","Fem","Sex","Sju","Åtta","Nio"};
		Arrays.sort(strings);
		for(String s : strings)
			System.out.print(s + " ");
		System.out.println();
		
		System.out.println(contains(strings,"Test"));
		
		int[] ints = {123,123,4324,345346,456456,45642345,345345,-1234,0};
		for(int i : ints)
			System.out.print(i + " ");
		
		System.out.println();
		
		System.out.println(intArrayAverage(ints));
	}
	
	public static boolean contains(String[] arr, String str) {
		int index = arr.length/2;
		int compare = str.compareTo(arr[index]);
		if(arr.length == 1) {
			return arr[0] == str;
		}
		
		if(compare < 0 ) {
			return contains(
					Arrays.copyOfRange(
							arr,
							0,
							index), 
					str);
		}
		else if ( compare > 0) {
			return contains(
					Arrays.copyOfRange(
							arr, 
							index, 
							arr.length), 
					str);
		}
		else {
			return true;
		}
	}
	
	public static int[] sort(int[] arr){
		int[] toReturn = new int[arr.length];
		
		for (int i = 0; i<arr.length; i++){
			Integer min = null;
			for(int j = 0; j<arr.length; j++){
				if (min == null || arr[j] < min)
					min = arr[j];
			}
			toReturn[i] = min;
		}
		return toReturn;
	}
	
	public static String charArrToString(char[] arr) {
		String toReturn = "";
		for(char c : arr)
			toReturn+=c;
		return toReturn;
	}
	
	public static double intArrayAverage(int[] arr) {
		double toReturn = 0;
		
		for (int i = 0; i<arr.length; i++) {
			toReturn+=arr[i];
		}
		return toReturn/=arr.length;
	}
}
