package main;
import java.io.*;
import java.util.*;


public class Translate {
    public static void main(String[] args) {
        getChoice();
        System.out.println("Bey!");
    }
    //����ѡ��
    public static void getChoice(){
        Scanner choice = new Scanner(System.in);
        String c;
        System.out.println("��ѡ���������(�����������1-3)");
        System.out.println("--- ---1.��ȷ����");
        System.out.println("--- ---2.ģ������");
        System.out.println("--- ---3.�˳�");
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
        System.out.println("���ڿ�ʼ��ȷ����,�˳��밴3");
        if ((!(ss.hasNext()))||(!(ss.next().equals("3")))){
            search();
            System.out.println("�Ƿ�������ң� y/n");
            String choice = ss.next();
            switch (choice){
                case "y":
                case "Y":
                    getChoice();
                    break;
                case "n":
                case "N":
                    System.out.println("���ҽ�����");
                    break;
                default:
                    inputError();
                    break;
            }
        } else {
            System.out.println("���ҽ���");
        }
    }
    //ʵ�־�ȷ����
    public static void search() {
        Scanner scanner = new Scanner(System.in);
        Map trans = load();
        String[] temp ;
        System.out.println("��������Ҫ���ҵĴ��");
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
                    System.out.println("�ô��ﹲ��"+i+"������");
                    break;
                }
            }
        } else {
            printError();
        }
    }

    public static void searchFuzzy() {
        Scanner sf = new Scanner(System.in);
        System.out.println("���ڿ�ʼģ�����ң��˳��밴3");
        if ((!(sf.hasNext()))||(!(sf.next().equals("3")))) {
            sSearch();
            System.out.println("�Ƿ�������ң� y/n");
            if ((sf.next().equals("n")) || (sf.next().equals("N"))) {
                System.out.println("���ҽ�����");
            }else {
                getChoice();
            }
        } else {
            System.out.println("���ҽ���");
        }
    }
    //ʵ��ģ������
    public static void sSearch(){
        Scanner ss = new Scanner(System.in);
        System.out.println("��������Ҫ���ҵ�Ӣ�ĵ���Ƭ��");
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
        System.out.println("��ǰ��������ƥ�䵥��" + count + "��");
    }

    public static Map load(){
        String word ;
        String[] trans  ;
        Map <String,String[]> dictMap = new HashMap<>();        //��HashMap�洢����
        try {
            FileInputStream fileInputStream = new FileInputStream("dict.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream,"UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            while ((word = bufferedReader.readLine()) != null){
                word = word.substring(1);
                trans = bufferedReader.readLine().substring(6).split("@");    //��spilt��trans�ָ�
                dictMap.put(word,trans);
            }
            bufferedReader.close();
            inputStreamReader.close();
            fileInputStream.close();
            //System.out.println("���شʿ�ɹ�");
            return dictMap;
        } catch (FileNotFoundException e) {
            System.out.println("�򿪴ʿ��ļ� dict.txt ʧ��");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dictMap;
    }
    //�����������
    public static void inputError(){
        System.out.println("Input Error!");
    }
    //�����������
    public static void printError(){
        System.out.println("��ǰ�ʿ�û��������ʣ���ȷ�������Ƿ�����������ʿ�");
    }
}
