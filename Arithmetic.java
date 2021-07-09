import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Arithmetic {
	
	private static List<String> parseToSuffixExpression(List<String> expressionList) {
        //创建一个栈用于保存操作符
        Stack<String> opStack = new Stack<>();
        //创建一个list用于保存后缀表达式
        List<String> suffixList = new ArrayList<>();
        for(String item : expressionList){
            //得到数或操作符
            if(isOperator(item)){
                //是操作符 判断操作符栈是否为空
                if(opStack.isEmpty() || "(".equals(opStack.peek()) || priority(item) > priority(opStack.peek())){
                    //为空或者栈顶元素为左括号或者当前操作符大于栈顶操作符直接压栈
                    opStack.push(item);
                }else {
                    //否则将栈中元素出栈如队，直到遇到大于当前操作符或者遇到左括号时
                    while (!opStack.isEmpty() && !"(".equals(opStack.peek())){
                        if(priority(item) <= priority(opStack.peek())){
                            suffixList.add(opStack.pop());
                        }
                    }
                    //当前操作符压栈
                    opStack.push(item);
                }
            }else if(isNumber(item)){
                //是数字则直接入队
                suffixList.add(item);
            }else if("(".equals(item)){
                //是左括号，压栈
                opStack.push(item);
            }else if(")".equals(item)){
                //是右括号 ，将栈中元素弹出入队，直到遇到左括号，左括号出栈，但不入队
                while (!opStack.isEmpty()){
                    if("(".equals(opStack.peek())){
                        opStack.pop();
                        break;
                    }else {
                        suffixList.add(opStack.pop());
                    }
                }
            }else {
                throw new RuntimeException("有非法字符！");
            }
        }
        //循环完毕，如果操作符栈中元素不为空，将栈中元素出栈入队
        while (!opStack.isEmpty()){
            suffixList.add(opStack.pop());
        }
        return suffixList;
    }
    /**
     * 判断字符串是否为操作符
     * @param op
     * @return
     */
    public static boolean isOperator(String op){
        return op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/");
    }

    /**
     * 判断是否为数字
     * @param num
     * @return
     */
    public static boolean isNumber(String num){
        return num.matches("\\d+");
    }

    /**
     * 获取操作符的优先级
     * @param op
     * @return
     */
    public static int priority(String op){
        if(op.equals("*") || op.equals("/")){
            return 1;
        }else if(op.equals("+") || op.equals("-")){
            return 0;
        }
        return -1;
    }
    
    /**
     * 将表达式转为list
     * @param expression
     * @return
     */
    private static List<String> expressionToList(String expression) {
        int index = 0;
        List<String> list = new ArrayList<>();
        do{
            char ch = expression.charAt(index);
            if(ch < 48 || ch > 58){
                //是操作符，直接添加至list中
                index ++ ;
                list.add(ch+"");
            }else if(ch >= 48 && ch <= 58){
                //是数字,判断多位数的情况
                String str = "";
                while (index < expression.length() && expression.charAt(index) >=48 && expression.charAt(index) <= 58){
                    str += expression.charAt(index);
                    index ++;
                }
                list.add(str);
            }
        }while (index < expression.length());
        return list;
    }
    
    /**
     * 根据后缀表达式list计算结果
     * @param list
     * @return
     */
    private static int calculate(List<String> list) {
        Stack<Integer> stack = new Stack<>();
        for(int i=0; i<list.size(); i++){
            String item = list.get(i);
            if(item.matches("\\d+")){
                //是数字
                stack.push(Integer.parseInt(item));
            }else {
                //是操作符，取出栈顶两个元素
                int num2 = stack.pop();
                int num1 = stack.pop();
                int res = 0;
                if(item.equals("+")){
                    res = num1 + num2;
                }else if(item.equals("-")){
                    res = num1 - num2;
                }else if(item.equals("*")){
                    res = num1 * num2;
                }else if(item.equals("/")){
                    res = num1 / num2;
                }else {
                    throw new RuntimeException("运算符错误！");
                }
                stack.push(res);
            }
        }
        return stack.pop();
    }
    
    
    
    public static void main(String []args){

        Scanner input = new Scanner(System.in);
		System.out.println("请输入四则表达式");
        String expression = input.next();

        List<String> expressionList = expressionToList(expression);
        System.out.println("表达式为：" + expressionList);
        //将中缀表达式转换为后缀表达式
        List<String> suffixList = parseToSuffixExpression(expressionList);
        System.out.println("其后缀表达式为：" + suffixList);
        //根据后缀表达式计算结果
        int calculateResult = calculate(suffixList);
        System.out.printf(expression+"=%d\n",calculateResult);
        input.close();
}
    
    

}
