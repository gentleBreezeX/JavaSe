package com.breeze.interview.ref;

import java.util.HashMap;
import java.util.WeakHashMap;

public class WeakHashMapDemo {
    public static void main(String[] args) {

        myHashMap();
        System.out.println("==============");
        myWeakHashMap();
    }

    private static void myWeakHashMap() {
        WeakHashMap<Integer,String> map = new WeakHashMap<>();
        Integer key = new Integer(2);
        String value = "WeakHashMap";

        map.put(key, value);
        System.out.println(map);//{2=WeakHashMap}

        key = null;
        System.out.println(map);//{2=WeakHashMap}

        System.gc();
        System.out.println(map + "\t" + map.size());//{} 0
    }

    private static void myHashMap() {
        HashMap<Integer,String> map = new HashMap<>();
        Integer key = new Integer(1);
        String value = "HashMaP";

        map.put(key, value);
        System.out.println(map);//{1=HashMaP}

        key = null;
        System.out.println(map);//{1=HashMaP}

        System.gc();
        System.out.println(map + "\t" + map.size());//{1=HashMaP}	1
    }
}
