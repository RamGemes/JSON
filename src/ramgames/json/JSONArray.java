package ramgames.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JSONArray implements JSONObject {
    private final HashMap<Integer, Integer> int_array;
    private final HashMap<Integer,String> string_array;
    private final HashMap<Integer,Double> double_array;
    private final HashMap<Integer,Boolean> boolean_array;
    private final HashMap<Integer,JSONHash> hash_array;
    private final HashMap<Integer,JSONArray> array_array;
    int index = 0;
    public JSONArray() {
        this.int_array = new HashMap<>();
        this.double_array = new HashMap<>();
        this.boolean_array = new HashMap<>();
        this.string_array = new HashMap<>();
        this.hash_array = new HashMap<>();
        this.array_array = new HashMap<>();
    }
    void put(int v) {
        this.int_array.put(index,v);
        index ++;
    }
    void put(double v) {
        this.double_array.put(index,v);
        index ++;
    }
    void put(String v) {
        this.string_array.put(index,v);
        index ++;
    }
    void put(boolean v) {
        this.boolean_array.put(index,v);
        index ++;
    }
    void put(JSONHash v) {
        this.hash_array.put(index,v);
        index ++;
    }
    void put(JSONArray v) {
        this.array_array.put(index,v);
        index ++;
    }
    void remove(int k) {
        removeNoReorder(k);
        reorder();
    }
    private void removeNoReorder(int k) {
        if(k >= index || k < 0) {
            throw new ArrayIndexOutOfBoundsException(String.format("index '%d' is out of bounds for array size '%s'",k,index));
        } else {
            int_array.remove(k);
            string_array.remove(k);
            double_array.remove(k);
            boolean_array.remove(k);
            hash_array.remove(k);
            array_array.remove(k);
        }
    }
    void reorder() {
        List<Integer> taggers = new ArrayList<>();
        taggers.addAll(int_array.keySet());
        taggers.addAll(double_array.keySet());
        taggers.addAll(boolean_array.keySet());
        taggers.addAll(string_array.keySet());
        taggers.addAll(hash_array.keySet());
        taggers.addAll(array_array.keySet());
        if(taggers.size()+1 < index) {
            int missing = -1;
            for(int i = 0; i < index-1;i++) if (!taggers.contains(i)) {
                    missing = i;
                }
            for(int k: int_array.keySet()) {
                if(k >= missing) {
                    int_array.put(k-1,int_array.get(k));
                    int_array.remove(k);
                }
            }
            for(int k: double_array.keySet()) {
                if(k >= missing) {
                    double_array.put(k-1,double_array.get(k));
                    double_array.remove(k);
                }
            }
            for(int k: hash_array.keySet()) {
                if(k >= missing) {
                    hash_array.put(k-1,hash_array.get(k));
                    hash_array.remove(k);
                }
            }
            for(int k: boolean_array.keySet()) {
                if(k >= missing) {
                    boolean_array.put(k-1,boolean_array.get(k));
                    boolean_array.remove(k);
                }
            }
            for(int k: string_array.keySet()) {
                if(k >= missing) {
                    string_array.put(k-1,string_array.get(k));
                    string_array.remove(k);
                }
            }
            for(int k: array_array.keySet()) {
                if(k >= missing) {
                    array_array.put(k-1,array_array.get(k));
                    array_array.remove(k);
                }
            }

        }
    }
    void overwrite(int i, int v) {
        removeNoReorder(i);
        put(v);
    }
    void overwrite(int i, double v) {
        removeNoReorder(i);
        put(v);
    }
    void overwrite(int i, boolean v) {
        removeNoReorder(i);
        put(v);
    }
    void overwrite(int i, String v) {
        removeNoReorder(i);
        put(v);
    }
    void overwrite(int i, JSONHash v) {
        removeNoReorder(i);
        put(v);
    }
    void overwrite(int i, JSONArray v) {
        removeNoReorder(i);
        put(v);
    }

    public int queryInt(int k) {
        if(int_array.containsKey(k)) {
            return int_array.get(k);
        } else {
            throw new UnknownKeyException(String.format("given hash doesn't contain key '%s'",k));
        }
    }
    public String queryString(int k) {
        if(string_array.containsKey(k)) {
            return string_array.get(k);
        } else {
            throw new UnknownKeyException(String.format("given hash doesn't contain key '%s'",k));
        }
    }
    public double queryDouble(int k) {
        if(double_array.containsKey(k)) {
            return double_array.get(k);
        } else {
            throw new UnknownKeyException(String.format("given hash doesn't contain key '%s'",k));
        }
    }
    public boolean queryBoolean(int k) {
        if(boolean_array.containsKey(k)) {
            return boolean_array.get(k);
        } else {
            throw new UnknownKeyException(String.format("given hash doesn't contain key '%s'",k));
        }
    }
    public JSONHash queryHash(int k) {
        if(hash_array.containsKey(k)) {
            return hash_array.get(k);
        } else {
            throw new UnknownKeyException(String.format("given hash doesn't contain key '%s'",k));
        }
    }
    public JSONArray queryArray(int k) {
        if(array_array.containsKey(k)) {
            return array_array.get(k);
        } else {
            throw new UnknownKeyException(String.format("given hash doesn't contain key '%s'",k));
        }
    }
    @Override
    public String toString() {
        System.out.println(index);
        StringBuilder builder = new StringBuilder("[");
        for(var i = 0; i < index;i++) {
            if (int_array.get(i) != null) {
                builder.append(String.format("%d:%s,",i,queryInt(i)));
            }
            if (string_array.get(i) != null) {
                builder.append(String.format("%d:%s,",i,queryString(i)));
            }
            if (boolean_array.get(i) != null) {
                builder.append(String.format("%d:%s,",i,queryBoolean(i)));
            }
            if (double_array.get(i) != null) {
                builder.append(String.format("%d:%s,",i,queryDouble(i)));
            }
            if (hash_array.get(i) != null) {
                builder.append(String.format("%d:%s,",i,queryHash(i)));
            }
            if (array_array.get(i) != null) {
                builder.append(String.format("%d:%s,",i,queryArray(i)));
            }
        }
        if(index != 0) {
            builder.deleteCharAt(builder.length()-1);
        }
        builder.append('}');
        return builder.toString();
    }

}