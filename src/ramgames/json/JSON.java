package ramgames.json;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public interface JSON {
    private static JSONHash genJsonHash(String[] stringlets) {
        System.out.println(Arrays.toString(stringlets));
        JSONHash object = new JSONHash();
        boolean isQuoting = false;
        StringBuilder value = new StringBuilder();
        String keyHolder = "";
        boolean hashing = false;
        boolean arraying = false;
        int skipmany = 0;
        int indention_level = -1;
        for(var i = 0; i < stringlets.length;i++) {
            String stringlet = stringlets[i];
            for(var ii = 0; ii < stringlet.length();ii++) {
                char charcoal = stringlet.charAt(ii);
                if(skipmany <= 0) {
                    if(hashing) {
                        switch(charcoal) {
                            case '{' -> indention_level++;
                            case '}' -> indention_level--;
                            default -> value.append(charcoal);
                        }
                        if(indention_level == 0) {
                            object.put(keyHolder, genJsonHash(new String[]{value.toString()+','}));
                            hashing = false;
                            value = new StringBuilder();
                            //keyHolder = "";
                            skipmany++;
                        }
                        System.out.printf("{%d,%d} -%s\n",i,ii,value);
                    } else {
                       if(arraying) {
                           switch(charcoal) {
                               case '[' -> indention_level++;
                               case ']' -> indention_level--;
                               default -> value.append(charcoal);
                           }
                           if(indention_level == 0) {
                               object.put(keyHolder,genJsonArray(new String[]{value.toString()+','}));
                               arraying = false;
                               skipmany++;
                           }
                           System.out.printf("{%d,%d} -%s\n",i,ii,value);
                       } else {
                           System.out.printf("{%d,%d} -%s\n",i,ii,charcoal);
                           switch(charcoal) {
                               case ' ' -> {
                                   if(isQuoting) {
                                       value.append(charcoal);
                                   }
                               }
                               case '{' -> {
                                   if(indention_level == 0) {
                                       hashing = true;
                                   }
                                   indention_level++;


                               }
                               case '[' ->{
                                   indention_level++;
                                   arraying = true;
                               }
                               case ',' -> {
                                   if(!isQuoting) {
                                       String valu = value.toString();
                                       BaseMethods.addValue(object, keyHolder, valu);
                                       value = new StringBuilder();
                                   }
                               }
                               case ':' -> {
                                   if(!isQuoting) {
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
        String[] lines = BaseMethods.fileToString(file);
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
        for(var i = 0; i < stringlets.length;i++) {
            String stringlet = stringlets[i];
            for(var ii = 0; ii < stringlet.length();ii++) {
                char charcoal = stringlet.charAt(ii);
                if(skipmany <= 0) {
                    if(hashing) {
                        switch(charcoal) {
                            case '{' -> indention_level++;
                            case '}' -> indention_level--;
                            default -> value.append(charcoal);
                        }
                        if(indention_level == 0) {
                            object.put(genJsonHash(new String[]{value.toString()+','}));
                            hashing = false;
                            value = new StringBuilder();
                            //keyHolder = "";
                            skipmany++;
                        }
                        System.out.printf("{%d,%d} -%s\n",i,ii,value);
                    } else {
                        if(arraying) {
                            switch(charcoal) {
                                case '[' -> indention_level++;
                                case ']' -> indention_level--;
                                default -> value.append(charcoal);
                            }
                            if(indention_level == 0) {
                                object.put(genJsonArray(new String[]{value.toString()+','}));
                                arraying = false;
                                //skipmany++;
                            }
                            System.out.printf("{%d,%d} -%s\n",i,ii,value);
                        } else {
                            System.out.printf("{%d,%d} -%s\n",i,ii,charcoal);
                            switch(charcoal) {
                                case ' ' -> {
                                    if(isQuoting) {
                                        value.append(charcoal);
                                    }
                                }
                                case '{' -> {
                                    if(indention_level == 0) {
                                        hashing = true;
                                    }
                                    indention_level++;


                                }
                                case '[' ->{
                                    indention_level++;
                                    arraying = true;
                                }
                                case ',' -> {
                                    if(!isQuoting) {
                                        String valu = value.toString();
                                        BaseMethods.addValue(object, valu);
                                        value = new StringBuilder();
                                    }
                                }
                                case ':' -> {
                                    if(!isQuoting) {
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

    interface BaseMethods {

         static JSONHash addValue(JSONHash object, String key, String value) {
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

         static JSONArray addValue(JSONArray object, String value) {
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

        static String[] fileToString(File file){
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
        static String removeRedundantSpaces(String string) {
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
                            if(isfirst) {stringlet = new char[]{' '};}else{
                                if(stringlet[i+1] == ' '){stringlet[i] = spacer;length -= 1;}}}}}} else {if(i == 0) {isfirst = false;//print("stopped first search");
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
        static String everythingBefore(String string, int index,boolean quoteCare) {
            StringBuilder stringl = new StringBuilder("");
            for(var i = 0; i < string.length();i++) {if(i < index) {
                if(quoteCare && isEven(countOfChar(everythingBefore(string,'"',false),'"'),true)) {
                    stringl.append(string.charAt(i));
                }
                if(!quoteCare) {
                    stringl.append(string.charAt(i));}}}
            return stringl.toString();
        }
        static String everythingBefore(String string, int index) {
            return everythingBefore(string,index,false);
        }
        static String[] removeNulls(String[] stringlets) {
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
        static int countOfChar(String string, char letter,boolean quoteCare) {
            int count = 0;
            char lettar;
            for(var i = 0; i < string.length();i++) {
                lettar = string.charAt(i);
                if(quoteCare) {
                    if(lettar == letter && isEven(countOfChar(everythingBefore(string,i),'"',false),true)) {
                        count += 1;
                    }
                } else {
                    if (lettar == letter) {
                        count += 1;
                    }
                }
            }
            return count;
        }
        static int countOfChar(String string, char letter) {
            return countOfChar(string,letter,false);
        }
        static boolean isEven(int num,boolean countzero) {
            if(num == 0 && countzero) {return true;}
            return (float) num / 2 == Math.round((float) num / 2) && num != 0;
        }
    }
}
