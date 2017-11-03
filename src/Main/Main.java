package Main;

import Operate.Match;
import Operate.SplitCode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by 费慧通 on 2017/10/25.
 */
public class Main {
    public static void Write(String s){
        try {
            FileWriter fw = new FileWriter("src/resources/output.txt",true);
            fw.write(s+"\n");
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        Match match = new Match();
        SplitCode splitCode = new SplitCode();
        String[] code = splitCode.getCodeFromFile();
        for(int i=0;i<code.length;i++){
            if(code[i].length()>0) {
                Write(match.getToken(code[i]).toString());
            }
        }
    }
}
