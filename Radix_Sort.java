//基数排序

import java.util.ArrayList;
import java.util.Scanner;

public class Radix_Sort {
	public static void main(String[] args) {
		//读取数组
		Scanner input = new Scanner(System.in);
		System.out.println("基数排序，请输入一行整数,并用空格隔开。例如：1 2 3 4 5");
        String[] ss = input.nextLine().trim().split("\\s+");//读取一行字符串，并用空格切割
        int[] arr = new int[ss.length];//构造一个整型数组
        for (int i = 0; i < arr.length; i++) {
            arr[i]=Integer.parseInt(ss[i]);//从字符串转换成整型
        }
        
        radixSort(arr);
        printArray(arr);
        
        input.close();
    }
 
    public static void radixSort(int[] arr) {
        int digit = getMaxDigit(arr); // 获取最大的数是多少位
        for (int i = 0; i < digit; i++) {
            bucketSort(arr, i); // 执行 digit 次 bucketSort 排序即可
        }
    }
 
    public static int getMaxDigit(int[] arr) {
        int digit = 1; // 默认只有一位
        int base = 10; // 十进制每多一位，代表其值大了10倍
        for (int i : arr) {
            while (i > base) {
                digit++;
                base *= 10;
            }
        }
        return digit;
    }
 
    public static void bucketSort(int[] arr, int digit) {
        int base = (int) Math.pow(10, digit);
        // init buckets
        ArrayList<ArrayList<Integer>> buckets = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < 10; i++) { // 只有0~9这十个数，所以准备十个桶
            buckets.add(new ArrayList<Integer>());
        }
        // sort
        for (int i : arr) {
            int index = i / base % 10;
            buckets.get(index).add(i);
        }
        // output: copy back to arr
        int index = 0;
        for (ArrayList<Integer> bucket : buckets) {
            for (int i : bucket) {
                arr[index++] = i;
            }
        }
    }
 
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
    }

}
