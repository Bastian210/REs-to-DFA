import Operate.Match;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by 费慧通 on 2017/10/25.
 */
public class Main {
    private List<String> double_operator = Arrays.asList("==",">=","<=","+=","-=","*=","/=","&&","||","!=","/*","*/");
    private List<String> single_operator = Arrays.asList("{","}",";","(",")","[","]",":","\"",",","+","-","*","/",">","<","|", "&","!", "=" );

    /**
     * 分割读入的文本为多个单词
     * @param code
     * @return
     */
    public String[] getSplitList(String code){
        String s = "";
        boolean is_operator = false;
        for(int i=0;i<code.length();i++){
            String current = code.substring(i,i+1);
            if(IsContainSingleOperator(current)){
                if(is_operator&&IsContainDoubleOperator(code.substring(i-1,i+1))){
                    s = s+current;
                }else{
                    s = s+" "+current;
                }
                is_operator = true;
            }else{
                if(is_operator){
                    s = s+" "+current;
                    is_operator = false;
                }else{
                    s = s+current;
                }
            }
        }
        return s.split(" ");
    }

    /**
     * 判断code是否为double_operator中的一个
     * @param code
     * @return
     */
    public boolean IsContainDoubleOperator(String code){
        for(int i=0;i<double_operator.size();i++){
            if(code.equals(double_operator.get(i))){
                return true;
            }
        }
        return false;
    }

    /**
     * 判断code是否为single_operator中的一个
     * @param code
     * @return
     */
    public boolean IsContainSingleOperator(String code){
        for(int i=0;i<single_operator.size();i++){
            if(code.equals(single_operator.get(i))){
                return true;
            }
        }
        return false;
    }

    /**
     * 读文件
     * @return
     */
    public String getCodeFromFile(){
        String result = "";
        try {
            Scanner sc = new Scanner(new File("src/resources/input.txt"));
            while (sc.hasNextLine()){
                String line = sc.nextLine();
                result += line;
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args){
        Match match = new Match();
        Main main = new Main();
        String enter = main.getCodeFromFile();
        String[] code = main.getSplitList(enter);
        for(int i=0;i<code.length;i++){
            if(code[i].length()>0) {
                System.out.println(match.getToken(code[i]));
            }
        }
    }
}
