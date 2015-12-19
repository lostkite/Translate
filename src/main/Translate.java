package main;
import java.io.*;
import java.util.*;


public class Translate {
    public static void main(String[] args) {
        getChoice();
        System.out.println("Bey!");
    }
    //命令选择
    public static void getChoice(){
        Scanner choice = new Scanner(System.in);
        String c;
        System.out.println("请选择操作类型(输入操作代号1-3)");
        System.out.println("--- ---1.精确查找");
        System.out.println("--- ---2.模糊查找");
        System.out.println("--- ---3.退出");
        if (!((c = choice.next()).equals("3"))){
            switch (c){
                case "1":
                    searchSingle();
                    break;
                case "2":
                    searchFuzzy();
                    break;
                default:
                    inputError();
                    break;
            }
        }
    }

    public static void searchSingle(){
        Scanner ss = new Scanner(System.in);
        System.out.println("现在开始精确查找,退出请按3");
        if ((!(ss.hasNext()))||(!(ss.next().equals("3")))){
            search();
            System.out.println("是否继续查找？ y/n");
            String choice = ss.next();
            switch (choice){
                case "y":
                case "Y":
                    getChoice();
                    break;
                case "n":
                case "N":
                    System.out.println("查找结束！");
                    break;
                default:
                    inputError();
                    break;
            }
        } else {
            System.out.println("查找结束");
        }
    }
    //实现精确查找
    public static void search() {
        Scanner scanner = new Scanner(System.in);
        Map trans = load();
        String[] temp ;
        System.out.println("请输入需要查找的词语：");
        String word = scanner.next();
        if (trans.containsKey(word)) {
            Set set = trans.entrySet();
            int i = 0;
            int count;
            for (Object s : set.toArray()) {
                Map.Entry entry = (Map.Entry) s;
                if (entry.getKey().equals(word)) {
                    temp = (String[]) entry.getValue();
                    for ( ;i < temp.length;i++){
                        count = i + 1;
                        System.out.println("  "+count+"."+" "+temp[i]+"  ");
                    }
                    System.out.println("该词语共有"+i+"个翻译");
                    break;
                }
            }
        } else {
            printError();
        }
    }

    public static void searchFuzzy() {
        Scanner sf = new Scanner(System.in);
        System.out.println("现在开始模糊查找，退出请按3");
        if ((!(sf.hasNext()))||(!(sf.next().equals("3")))) {
            sSearch();
            System.out.println("是否继续查找？ y/n");
            if ((sf.next().equals("n")) || (sf.next().equals("N"))) {
                System.out.println("查找结束！");
            }else {
                getChoice();
            }
        } else {
            System.out.println("查找结束");
        }
    }
    //实现模糊查找
    public static void sSearch(){
        Scanner ss = new Scanner(System.in);
        System.out.println("请输入需要查找的英文单词片段");
        CharSequence charSequence = ss.next();
        Map trans = load();
        Set set = trans.entrySet();
        int count = 0;
        for (Object s : set.toArray()) {
            Map.Entry entry = (Map.Entry) s;
            String s1 = (String) entry.getKey();
            if (s1.contains(charSequence)) {
                String[] strings = (String[])entry.getValue();
                System.out.print("  "+s1+": ");
                int i =1;
                for (String a:strings){
                    System.out.print(i + ". " + a+"  ");
                    i += 1;
                }
                System.out.println();
                count += 1;
            }
        }
        System.out.println("当前共搜索到匹配单词" + count + "个");
    }

    public static Map load(){
        String word ;
        String[] trans  ;
        Map <String,String[]> dictMap = new HashMap<>();        //用HashMap存储词条
        try {
            FileInputStream fileInputStream = new FileInputStream("dict.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream,"UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            while ((word = bufferedReader.readLine()) != null){
                word = word.substring(1);
                trans = bufferedReader.readLine().substring(6).split("@");    //用spilt对trans分割
                dictMap.put(word,trans);
            }
            bufferedReader.close();
            inputStreamReader.close();
            fileInputStream.close();
            //System.out.println("加载词库成功");
            return dictMap;
        } catch (FileNotFoundException e) {
            System.out.println("打开词库文件 dict.txt 失败");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dictMap;
    }
    //命令输入错误
    public static void inputError(){
        System.out.println("Input Error!");
    }
    //词语输入错误
    public static void printError(){
        System.out.println("当前词库没有这个单词，请确认输入是否有误或升级词库");
    }
}
