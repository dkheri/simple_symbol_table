package com.company.bugbusters;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class SymbolTable {
    final static String BEGIN_SCOPE = "beginscope";
    final static String DEFINE = "define";
    final static String USE = "use";
    final static String END_SCOPE = "endscope";
    Stack<Scope> scopes;
   int currentLevel;

   public SymbolTable(){
       scopes = new Stack<>();
       currentLevel = -1;
   }

   public void beginScope(){
       currentLevel++;
       scopes.push(new Scope(currentLevel));
   }

   public void endScope(){
       currentLevel--;
       scopes.pop();
   }

   public void define(String name,String value){
       Scope currentScope = scopes.peek();
       currentScope.define(name,Integer.parseInt(value));
   }
   public String lookUp(String name){
       String result = "undefined";

       if(scopes.isEmpty()){
           return result;
       }

       Scope currentScope = scopes.peek();
       //does the current scope have the var
       //if not then look at higher levels i.e excluding other scopes in the current level
       if(currentScope.hasVarDefn(name)){
           Integer value = currentScope.getValue(name);
           result = value.toString();
           return result;
       }

       else{
           for(Scope s:scopes){
               if((currentLevel>s.getLevel())&&s.hasVarDefn(name)){
                   Integer value = s.getValue(name);
                   result = value.toString();
               }
           }
       }
       return result;
   }

    public void parse(String filename){
        File file = new File(filename);
        try {
            Scanner in = new Scanner(file);
            while(in.hasNextLine()){
                String line = in.nextLine();
                String[] delimitedInput = line.split(" ");
                if(delimitedInput[0].equals(BEGIN_SCOPE)){
                    beginScope();
                    System.out.println(line);
                }

                else if (delimitedInput[0].equals(END_SCOPE)){
                  endScope();
                    System.out.println(line);
                }
                else if (delimitedInput[0].equals(DEFINE)){
                    String varName = delimitedInput[1];
                    String varValue = delimitedInput[2];
                    define(varName,varValue);
                    System.out.println(line);
                }
                else if(delimitedInput[0].equals(USE)){
                    String varName = delimitedInput[1];
                    System.out.println(line+" = "+lookUp(varName));
                }
                else{
                    System.out.println(line);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
