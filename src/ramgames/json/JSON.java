//THIS LIBRARY WAS WRITTEN BY RAMGAMES
package ramgames.json;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public interface JSON {
    private static JSONHash genJsonHash(String[] stringlets) {
        JSONHash object = new JSONHash();
        boolean isQuoting = false;
        StringBuilder value = new StringBuilder();
        String keyHolder = "";
        boolean hashing = false;
        boolean arraying = false;
        int skipmany = 0;
        int indention_level = -1;
        for (String stringlet : stringlets) {
            for (char charcoal: stringlet.toCharArray()) {
                if (skipmany <= 0) {
                    if (hashing) {
                        switch (charcoal) {
                            case '{' -> indention_level++;
                            case '}' -> indention_level--;
                        }
                        if (indention_level == 0) {
                            object.put(keyHolder, genJsonHash(new String[]{'{'+value.toString() + ','}));
                            hashing = false;
                            value = new StringBuilder();
                            skipmany++;
                        } else {
                            value.append(charcoal);
                        }
                    } else {
                        if (arraying) {
                            switch (charcoal) {
                                case '[' -> indention_level++;
                                case ']' -> indention_level--;
                            }
                            if (indention_level == 0) {
                                object.put(keyHolder, genJsonArray(new String[]{value.toString() + ','}));
                                arraying = false;
                                value = new StringBuilder();
                                skipmany++;
                            } else {
                                value.append(charcoal);
                            }
                        } else {
                            switch (charcoal) {
                                case ' ' -> {
                                    if (isQuoting) {
                                        value.append(charcoal);
                                    }
                                }
                                case '{' -> {
                                    if (indention_level == 0) {
                                        hashing = true;
                                    }
                                    indention_level++;
                                }
                                case '[' -> {
                                    indention_level++;
                                    arraying = true;
                                }
                                case ',' -> {
                                    if (!isQuoting) {
                                        String valu = value.toString();
                                        addValue(object, keyHolder, valu);
                                        value = new StringBuilder();
                                    }
                                }
                                case ':' -> {
                                    if (!isQuoting) {
                                        keyHolder = value.toString();
                                        value = new StringBuilder();
                                    }
                                }
                                case '"' -> isQuoting = !isQuoting;
                                default -> value.append(charcoal);
                            }
                        }
                    }
                } else {
                    skipmany--;
                }
            }
        }
        return object;
    }
    static JSONHash getJSON(File file) {
        if(!file.getName().endsWith(".json")) {
            throw new IncorrectFileException("file provided isn't .json");
        }
        String[] lines = fileToString(file);
        return genJsonHash(lines);
    }
    private static JSONArray genJsonArray(String[] stringlets) {
        JSONArray object = new JSONArray();
        boolean isQuoting = false;
        StringBuilder value = new StringBuilder();
        boolean hashing = false;
        boolean arraying = false;
        int skipmany = 0;
        int indention_level = -1;
        for (String stringlet : stringlets) {
            for (char charcoal: stringlet.toCharArray()) {
                if (skipmany <= 0) {
                    if (hashing) {
                        switch (charcoal) {
                            case '{' -> indention_level++;
                            case '}' -> indention_level--;
                            default -> value.append(charcoal);
                        }
                        if (indention_level == 0) {
                            object.put(genJsonHash(new String[]{value.toString() + ','}));
                            hashing = false;
                            value = new StringBuilder();
                            skipmany++;
                        }
                    } else {
                        if (arraying) {
                            switch (charcoal) {
                                case '[' -> indention_level++;
                                case ']' -> indention_level--;
                                default -> value.append(charcoal);
                            }
                            if (indention_level == 0) {
                                object.put(genJsonArray(new String[]{value.toString() + ','}));
                                arraying = false;
                                //skipmany++;
                            }
                        } else {
                            switch (charcoal) {
                                case ' ' -> {
                                    if (isQuoting) {
                                        value.append(charcoal);
                                    }
                                }
                                case '{' -> {
                                    if (indention_level == 0) {
                                        hashing = true;
                                    }
                                    indention_level++;
                                }
                                case '[' -> {
                                    indention_level++;
                                    arraying = true;
                                }
                                case ',' -> {
                                    if (!isQuoting) {
                                        String valu = value.toString();
                                        addValue(object, valu);
                                        value = new StringBuilder();
                                    }
                                }
                                case ':' -> {
                                    if (!isQuoting) {
                                        value = new StringBuilder();
                                    }
                                }
                                case '"' -> isQuoting = !isQuoting;
                                default -> value.append(charcoal);
                            }
                        }
                    }
                } else {
                    skipmany--;
                }
            }
        }
        return object;
    }
         private static JSONHash addValue(JSONHash object, String key, String value) {
             try {
                 object.put(key,Integer.parseInt(value));
             } catch (NumberFormatException e) {
                 try {
                     object.put(key,Double.parseDouble(value));
                 } catch (NumberFormatException ex) {
                     if(value.equals("true") || value.equals("false")) {
                         object.put(key,Boolean.parseBoolean(value));
                     } else {
                         object.put(key,value);
                     }
                 }
             }
             return object;
         }
        private static JSONArray addValue(JSONArray object, String value) {
            try {
                object.put(Integer.parseInt(value));
            } catch (NumberFormatException e) {
                try {
                    object.put(Double.parseDouble(value));
                } catch (NumberFormatException ex) {
                    if(value.equals("true") || value.equals("false")) {
                        object.put(Boolean.parseBoolean(value));
                    } else {
                        object.put(value);
                    }
                }
            }
            return object;
        }
        private static String[] fileToString(File file){
            Scanner sc;
            try {
                sc = new Scanner(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            String[] stringlet = new String[1000000];
            int indexer = 0;
            String line;
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                stringlet[indexer] = line;
                indexer += 1;
            }
            stringlet = removeNulls(stringlet);
            for (var i = 0; i < indexer; i++) {
                stringlet[i] = removeRedundantSpaces(stringlet[i]);
            }
            return stringlet;
        }
        private static String removeRedundantSpaces(String string) {
            if(string.equals("")) {return "";}
            char[] stringlet = new char[string.length()];
            for(var i = 0; i < string.length();i++) {
                stringlet[i] = string.charAt(i);
            }
            int length = stringlet.length;
            boolean isfirst = true;
            char spacer = '`';
            for(var i = 0; i < stringlet.length;i++) {
                if (stringlet[i] == ' ') {if(i == 0) {
                    stringlet[i] = spacer;length -= 1;}else{
                    if(i+1 < stringlet.length) {
                        if(isfirst) {stringlet[i] = spacer;length -= 1;
                            if(stringlet[i+1] != ' '){isfirst=false;
                            }}
                        else{
                            if(stringlet[i+1] == ' '){stringlet[i] = spacer;length -= 1;}
                        }}}} else {if(i == 0) {isfirst = false;//print("stopped first search");
                }}
            }
            if (stringlet[stringlet.length-1] == ' ') {stringlet[stringlet.length-1] = '`';length -=1  ;}
            StringBuilder stringl = new StringBuilder();
            for(var i = 0; stringl.length()<length;i++) {
                if(stringlet[i] != '`') {
                    stringl.append(stringlet[i]);}
            }
            if(stringl.toString().equals(" ")) {return "";}
            return stringl.toString();
        }
        private static String[] removeNulls(String[] stringlets) {
            int indexer = 0;
            for (String stringx:stringlets) {
                if(stringx != null) {indexer+=1;}
            }
            int indexy = 0;
            String[] stringls = new String[indexer];
            for (String stringx:stringlets) {
                if(stringx != null) {stringls[indexy] = stringx;indexy +=1;}
            }
            return stringls;
        }

}