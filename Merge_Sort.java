//归并排序

import java.util.Scanner;

public class Merge_Sort {
 
	public static void main(String[] args) {
		//读取数组
		Scanner input = new Scanner(System.in);
		System.out.println("归并排序，请输入一行整数,并用空格隔开。例如：1 2 3 4 5");
        String[] ss = input.nextLine().trim().split("\\s+");//读取一行字符串，并用空格切割
        int[] arr = new int[ss.length];//构造一个整型数组
        for (int i = 0; i < arr.length; i++) {
            arr[i]=Integer.parseInt(ss[i]);//从字符串转换成整型
        }
        
        int[] tmp = new int[arr.length];//新建一个临时数组存放
		mergeSort(arr,0,arr.length-1,tmp);
		for(int i=0;i<arr.length;i++){
			System.out.print(arr[i]+" ");
		}
		input.close();
	}
	
	public static void merge(int[] arr,int low,int mid,int high,int[] tmp){
		int i = 0;
		int j = low,k = mid+1;  //左边序列和右边序列起始索引
		while(j <= mid && k <= high){
			if(arr[j] < arr[k]){
				tmp[i++] = arr[j++];
			}else{
				tmp[i++] = arr[k++];
			}
		}
		//若左边序列还有剩余，则将其全部拷贝进tmp[]中
		while(j <= mid){    
			tmp[i++] = arr[j++];
		}
		
		while(k <= high){
			tmp[i++] = arr[k++];
		}
		
		for(int t=0;t<i;t++){
			arr[low+t] = tmp[t];
		}
	}
 
	public static void mergeSort(int[] arr,int low,int high,int[] tmp){
		if(low<high){
			int mid = (low+high)/2;
			mergeSort(arr,low,mid,tmp); //对左边序列进行归并排序
			mergeSort(arr,mid+1,high,tmp);  //对右边序列进行归并排序
			merge(arr,low,mid,high,tmp);    //合并两个有序序列
		}
	}
	
}
