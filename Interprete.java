/**
 * Copyright (C), 2022-2023, The_Kiesling FabianJuarez SaraEcheverria
 * FileName: View
 * Author:   Fabian Juarez , Sara Echeverria , Jose Pablo Kiesling
 * Date:     26/02/2022
 * @author Jose Pablo Kiesling, Fabian Juarez y Sara Echeverria
 * @version:
        - Creacion: 26/02/2022
        - Ultima modificacion:
    Clase que tiene como fin ser un
 */

import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Interprete {
    //--------------------------- PROPIEDADES --------------------------
    HashMap<String, Integer> variables = new HashMap<String, Integer>();
    Aritmeticas aritmeticas = new Aritmeticas();

    //--------------------------- METODOS ------------------------------
    /*****************************************************************
     * recibe una expresion y una opcion según dicha expresion
     * @param expresion
     * @param option
     */
    public String operate(String expresion, int option){
        if (option == 1)
            return newVariable(expresion);
        else if (option == 2)
            return Operation(expresion);
        else if (option == 3)
            return quote(expresion);
        else
            return null;
        
    }
    //****************************************************************

    /*****************************************************************
     * crea una nueva variable entera
     * @param expresion
     */
    private String newVariable(String expresion){
        String name = "";
        int value = 0;
        
        //Nombre de la variable
        Pattern pattern = Pattern.compile("[ ]+[a-z]+[ ]+", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(expresion);
        if (matcher.find()) 
            name = matcher.group().trim();
        
        
        //Valor de la variable
        pattern = Pattern.compile("[ ]+[0-9]+[ ]*", Pattern.CASE_INSENSITIVE); 
        matcher = pattern.matcher(expresion);
        if (matcher.find())
            value = Integer.parseInt(matcher.group().trim());
        
        
       //Instanciar la variable y agregarla al arreglo dinámico
       variables.put(name, value);
       return name +": " + value;
    }
    //****************************************************************

    /*****************************************************************
     * operacion que entrar toda la variable.
     * @param expresion
     */
     // --- Reordenar y separar ---
    public String Operation(String expresion) {
        String newExpresion = "";
        String variable = "";
        String[] parts = expresion.split(" ");
        System.out.println("Operacion");
        
        if (parts.length == 2)
            return null;
        for (int i = 0; i < parts.length; i++){
            //Valores de variables
            Pattern pattern = Pattern.compile("([a-z]+)", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(parts[i]);

            if (matcher.find()){
                variable = matcher.group().trim();
                if(!variable.matches("[+-]?\\d*(\\.\\d+)?"))
                    if(verifyVariable(variable) != null)
                        parts[i] = verifyVariable(variable).toString();
                    else
                        return null;
            }
        }
        for (int i = 0; i < parts.length; i++)
            newExpresion += parts[i] + " ";
                
        int resultado = aritmeticas.Evaluate(newExpresion);
        String result = "Resultado: " + resultado;
        return result;
    }
    //****************************************************************

        /*****************************************************************
     * operacion que entrar toda la variable.
     * @param expresion
     */
    public String quote(String expresion){
        String expresionFinal ="";
        String[] expresionSplit = expresion.split(" ");
        int i =0;
        for(i =0; i <expresionSplit.length-1;i++){
            if(expresionSplit[i].equals("quote")||expresionSplit[i].equals("'")){
                i=i+1;
                break;
            }
        }
        for(int j = i ; j<=expresionSplit.length-1; j++) {
        	expresionFinal =expresionSplit[j];
        }
        return expresionFinal;
    }
    //****************************************************************

    /*****************************************************************
     * verifica si existe la variable
     * @param name
     * @return
     */
    private Integer verifyVariable(String name){
        Integer variable = null;
        if(variables.containsKey(name))
            variable = variables.get(name);
        return variable;
    }
    //****************************************************************
}
