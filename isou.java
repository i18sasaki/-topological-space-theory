import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class isou{
    public static int S[]={0,1,2,3,4};
    public static int O[][]={{},{0},{1},{0,1},{1,2},{0,1,2},{0,1,2,3,4}};
    public static int M[] = {0,1,2,3};
    
    public static List<Integer> commonSet(List<Integer> a,List<Integer> b){
        List<Integer> set = new ArrayList<Integer>();
        for(int a_0 : a){
            for(int b_0 : b){
                if(a_0 == b_0){
                    set.add(a_0);
                }
            }
        }
        return set;
    }

    public static List<Integer> unionSet(List<Integer> a,List<Integer> b){
        List<Integer> set = new ArrayList<Integer>();
        for(int a_0 : a){
            set.add(a_0);
        }
        for(int b_0 : b){
            int flag=1;
            for(int a_0 : a){
                if(b_0==a_0){
                    flag=0;
                    break;
                }
            }
            if(flag==1){
                set.add(b_0);
            }
        }
        return set;
    }

    public static boolean search(List<Integer> s){
        for(int i=0;i<O.length;i++){
            if(Arrays.equals(O[i],s.stream().mapToInt(Integer::intValue).toArray())){
                return true;
            }
        }
        return false;
    }
    public static boolean searchIn(List<Integer> s,List<Integer> o){
        for(int i=0;i<o.size();i++){
            int f_o = 0;
            for(int j=0;j<s.size();j++){
                if(o.get(i)==s.get(j)){
                    f_o = 1;
                    break;
                }
            }
            if(f_o == 0){
                return false;
            }
        }
        return true;
    }

    public static List<Integer> aryToList(int a[]){
        return Arrays.stream(a).boxed().collect(Collectors.toList());
    }

    public static Map<Integer, Object> divist(List<List<Integer>> list){
        Map<Integer, Object> map = new HashMap<>();
        map.put(0, list.remove(0));
        map.put(1, new ArrayList<>(list));
        return map;
    }

    public static Map<Integer, Object> divistMap(Map<Integer, Object> map_old){
        Map<Integer, Object> map_new = new HashMap<>();
        Object m1 = map_old.get(0);
        List<Integer> m1c = new ArrayList<>();
        if(m1 instanceof List<?>){
            List<?> tmp = (List<?>) m1;
            if(!tmp.isEmpty() && tmp.get(0) instanceof Integer){
                m1c = (List<Integer>) tmp;
            }
        }
        Object m2 = map_old.get(1);
        List<List<Integer>> m2c = new ArrayList<>();
        if(m2 instanceof List<?>){
            List<?> tmp = (List<?>) m2;
            if (!tmp.isEmpty() && tmp.get(0) instanceof List<?>) {
                m2c = (List<List<Integer>>) tmp;
            }
        }
        map_new.put(0,unionSet(m1c,m2c.remove(0)));
        map_new.put(1,m2c);
        return map_new;
    }

    public static void main(String args[]){
        /* 
        (S,О):位相空間の定義
        (O1){},S \in O
        (O2)Sの開集合系の任意の和集合 \in O
        (O3)Sの開集合系の任意の共通集合 \in O
        */
        
        //(O1)判定
        int f_o1 = 0;
        if(search(Collections.emptyList())){
            if(search(Collections.emptyList())){
                f_o1 = 1;
            }
        }
        
        //(O2)判定
        int f_o2 = 1;
        for(int i=0;i<O.length-1;i++){
            for(int j=i+1;j<O.length;j++){
                List<Integer> cs = commonSet(aryToList(O[i]), aryToList(O[j]));
                if(!(search(cs))){
                    f_o2 = 0;
                }
            }
        }
        
        //(O3)判定
        int f_o3 = 1;
        List<List<Integer>> O_list = Arrays.stream(O).map(row -> Arrays.stream(row).boxed().collect(Collectors.toList())).collect(Collectors.toList());
        //List<List<Integer>> common_List = new ArrayList<>();
        List<Map<Integer, Object>> all_map = new ArrayList<>();
        List<Map<Integer, Object>> cur_map = new ArrayList<>();
        for(int i=0;i<O.length-1;i++){
            Map<Integer, Object> r1 = divist(O_list);
            cur_map.add(r1);
        }
        int f = 3;
        while(f > 0){
            f--;
            List<Map<Integer, Object>> last_map = new ArrayList<>();
            for(Map<Integer, Object> map:cur_map){
                Object m1 = map.get(0);
                List<Integer> m1c = new ArrayList<>();
                if(m1 instanceof List<?>){
                    List<?> tmp = (List<?>) m1;
                    if(!tmp.isEmpty() && tmp.get(0) instanceof Integer){
                        m1c = (List<Integer>) tmp;
                    }
                }
                Object m2 = map.get(1);
                List<List<Integer>> m2c = new ArrayList<>();
                if(m2 instanceof List<?>){
                    List<?> tmp = (List<?>) m2;
                    if (!tmp.isEmpty() && tmp.get(0) instanceof List<?>) {
                        m2c = (List<List<Integer>>) tmp;
                    }
                }
                while(!(m2c.isEmpty())){
                    Map<Integer, Object> mo = new HashMap<>();
                    List<List<Integer>> m2cc = new ArrayList<>(m2c);
                    mo.put(0,m1c);
                    mo.put(1,m2cc);
                    Map<Integer, Object> r2 = divistMap(mo);
                    last_map.add(r2);
                    m2c = new ArrayList<>(m2cc);
                }
            }
            all_map.addAll(last_map);
            cur_map = last_map;
        }
        for(Map<Integer, Object> a:all_map){
            List<Integer>  an = (List<Integer>) a.get(0);
            Collections.sort(an);
            if(!(search(an))){
                f_o3 = 0;
            }
        }
        if(f_o1 == 1 && f_o2 == 1 && f_o3 == 1){
            System.out.printf("(S,O)は位相空間である。\n");
        }
        else{
            System.out.printf("(S,O)は位相空間でない。\n");
        }
        //Mの開核
        List<Integer> ml = Arrays.stream(M).boxed().collect(Collectors.toList());
        List<Integer> m_o = new ArrayList<>();
        for(int i=0;i<O.length;i++){
            List<Integer> ol = Arrays.stream(O[i]).boxed().collect(Collectors.toList());;
            if(searchIn(ml,ol)){
                m_o = unionSet(m_o,ol);
            }
        }
        System.out.printf("M=");
        System.out.print(ml);
        System.out.printf("の開核\nM゜=");
        System.out.print(m_o);
    }
}