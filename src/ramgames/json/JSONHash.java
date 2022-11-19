package ramgames.json;

import java.lang.constant.Constable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JSONHash implements JSONObject {
    private final HashMap<String, Integer> int_array;
    private final HashMap<String,String> string_array;
    private final HashMap<String,Double> double_array;
    private final HashMap<String,Boolean> boolean_array;
    private final HashMap<String,JSONHash> hash_array;
    private final HashMap<String,JSONArray> array_array;
    private final List<String> tags;

    public JSONHash() {
        this.int_array = new HashMap<>();
        this.double_array = new HashMap<>();
        this.boolean_array = new HashMap<>();
        this.string_array = new HashMap<>();
        this.hash_array = new HashMap<>();
        this.array_array = new HashMap<>();
        this.tags = new ArrayList<>();


        System.out.println("declared JSONHash");
    }
    void put(String k, int v) {
        if(tags.contains(k)) {
            throw new KeyAlreadyExistsException(String.format("key value '%s' already exists in the given hash",k));
        } else {
            tags.add(k);
            System.out.printf("putting int %s as %s\n",k,v);
            this.int_array.put(k,v);
        }

    }
    void put(String k, double v) {
        if(tags.contains(k)) {
            throw new KeyAlreadyExistsException(String.format("key value '%s' already exists in the given hash",k));
        } else {
            tags.add(k);
            System.out.printf("putting double %s as %s\n",k,v);
            this.double_array.put(k,v);
        }
    }
    void put(String k, String v) {
        if(tags.contains(k)) {
            throw new KeyAlreadyExistsException(String.format("key value '%s' already exists in the given hash",k));
        } else {
            tags.add(k);
            System.out.printf("putting String %s as %s\n",k,v);
            this.string_array.put(k,v);
        }
    }
    void put(String k, boolean v) {
        if(tags.contains(k)) {
            throw new KeyAlreadyExistsException(String.format("key value '%s' already exists in the given hash",k));
        } else {
            tags.add(k);
            System.out.printf("putting boolean %s as %s\n",k,v);
            this.boolean_array.put(k,v);
        }
    }
    void put(String k, JSONHash v) {
        if(tags.contains(k)) {
            throw new KeyAlreadyExistsException(String.format("key value '%s' already exists in the given hash",k));
        } else {
            tags.add(k);
            System.out.printf("putting JSONHash %s as %s\n",k,v);
            this.hash_array.put(k,v);
        }
    }
    void put(String k, JSONArray v) {
        if(tags.contains(k)) {
            throw new KeyAlreadyExistsException(String.format("key value '%s' already exists in the given hash",k));
        } else {
            tags.add(k);
            System.out.printf("putting JSONArray %s as %s\n",k,v);
            this.array_array.put(k,v);
        }
    }

    void remove(String k) {
        if(!tags.contains(k)) {
            throw new UnknownKeyException(String.format("key value '%s' doesn't exist in the given hash",k));
        } else {
            int_array.remove(k);
            string_array.remove(k);
            double_array.remove(k);
            boolean_array.remove(k);
            hash_array.remove(k);
            array_array.remove(k);
        }
    }
    boolean containsKey(String k) {
        return tags.contains(k);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("{");
        for(String k: int_array.keySet()) {
            builder.append(String.format("%s:%s,",k,int_array.get(k)));
        }
        for(String k: double_array.keySet()) {
            builder.append(String.format("%s:%s,",k,double_array.get(k)));
        }
        for(String k: boolean_array.keySet()) {
            builder.append(String.format("%s:%s,",k,boolean_array.get(k)));
        }
        for(String k: string_array.keySet()) {
            builder.append(String.format("%s:%s,",k,string_array.get(k)));
        }
        for(String k: hash_array.keySet()) {
            builder.append(String.format("%s:%s,",k,hash_array.get(k)));
        }
        for(String k: array_array.keySet()) {
            builder.append(String.format("%s:%s,",k,array_array.get(k)));
        }
        if(!tags.isEmpty()) {
            builder.deleteCharAt(builder.length()-1);
        }

        builder.append('}');
        return builder.toString();
    }
    public int queryInt(String k) {
        if(int_array.containsKey(k)) {
            return int_array.get(k);
        } else {
            throw new UnknownKeyException(String.format("given hash doesn't contain key '%s'",k));
        }
    }
    public String queryString(String k) {
        if(string_array.containsKey(k)) {
            return string_array.get(k);
        } else {
            throw new UnknownKeyException(String.format("given hash doesn't contain key '%s'",k));
        }
    }
    public double queryDouble(String k) {
        if(double_array.containsKey(k)) {
            return double_array.get(k);
        } else {
            throw new UnknownKeyException(String.format("given hash doesn't contain key '%s'",k));
        }
    }
    public boolean queryBoolean(String k) {
        if(boolean_array.containsKey(k)) {
            return boolean_array.get(k);
        } else {
            throw new UnknownKeyException(String.format("given hash doesn't contain key '%s'",k));
        }
    }
    public JSONHash queryHash(String k) {
        if(hash_array.containsKey(k)) {
            return hash_array.get(k);
        } else {
            throw new UnknownKeyException(String.format("given hash doesn't contain key '%s'",k));
        }
    }
    public JSONArray queryArray(String k) {
        if(array_array.containsKey(k)) {
            return array_array.get(k);
        } else {
            throw new UnknownKeyException(String.format("given hash doesn't contain key '%s'",k));
        }
    }

}
