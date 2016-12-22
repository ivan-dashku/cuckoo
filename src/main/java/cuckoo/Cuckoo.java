package cuckoo;

import java.util.*;

public class Cuckoo<Key, Value>
{
    private static final int MAX_SIZE = 50;
    Random random = new Random();
    private int size = 0;
    private int h1 = 0;
    private int h2 = 0;
    Entry<Key,Value>[] entries;

    private int hash1(Key key) {
        return Math.abs(h1 * key.hashCode() ) % entries.length;
    }

    private int hash2(Key key){
        return  Math.abs(h2 * key.hashCode() ) % entries.length;
    }

    public Cuckoo() {
        entries = new Entry[MAX_SIZE];
        while (h1 == h2) {
            h1 = ((random.nextInt()) * 100 + 99);
            h2 = ((random.nextInt()) * 100 + 99);
        }
    }

    public class Entry<Key, Value> {

        private Key key;
        private Value value;

        public Entry(Key key,Value value) {
            this.key = key;
            this.value = value;
        }

        public Key getKey() {
            return key;
        }

        public Value getValue() {
            return value;
        }

        public void setValue(Value value) {
            this.value = value;
        }
    }

    public int size() {
        return size;
    }

    public Value get(Key key) {
        if (entries[hash1(key)] != null) {
            if (key.equals(entries[hash1(key)].getKey())) {
                return entries[hash1(key)].getValue();
            }
        }
        else if (entries[hash2(key)] != null) {
            if (key.equals(entries[hash2(key)].getKey())) {
                return entries[hash2(key)].getValue();
            }
        }

        return  null;
    }

    public void put(Key key, Value value) {
        int hash1 = hash1(key);
        int hash2 = hash2(key);
        Entry tmp1 = new Entry(key, value);
        Entry tmp2 = new Entry(key, value);
        int koo = 0;
        boolean flag = true;

        while (flag == true) {
            if (entries[hash1] == null) {
                entries[hash1] = tmp1;
                flag = false;
            } else if (entries[hash2] == null) {
                entries[hash2] = tmp1;
                flag = false;
            } else {
                Key key1 = entries[hash1].getKey();
                Value value1 = entries[hash1].getValue();
                Key key2 = entries[hash2].getKey();
                Value value2 = entries[hash2].getValue();
                if (koo % 2 == 0) {
                    entries[hash1] = tmp1;
                    tmp1 = new Entry(key1, value1);
                    hash2 = (hash2(key1) == hash1) ? hash1(key1) : hash2(key);
                    koo++;
                    flag = (tmp1 != tmp2);
                } else {
                    entries[hash2] = tmp1;
                    tmp1 = new Entry(key2, value2);
                    hash1 = (hash2(key2) == hash2) ? hash1(key2) : hash2(key2);
                    koo++;
                    flag = (tmp1 != tmp2);
                }
            }
        }

        if (koo != 0 && (entries[hash1] == tmp2 || entries[hash2] == tmp2)){
            Cuckoo copy = new Cuckoo<Key, Value>();
            for (int i = 0; i < entries.length; i++) {
                if (entries[i] != null) {
                    Key k = entries[i].getKey();
                    Value v = entries[i].getValue();
                    copy.put(k, v);
                }
            }

            copy.put(key, value);
            size = copy.size;
            h1 = copy.h1;
            h2 = copy.h2;
            entries = copy.entries;
        }
    }
}

