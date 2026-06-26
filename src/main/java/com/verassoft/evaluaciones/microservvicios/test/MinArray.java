package com.verassoft.evaluaciones.microservvicios.test;

import java.util.*;

import static com.verassoft.evaluaciones.microservvicios.test.ValidateInput.isElegible;
import static com.verassoft.evaluaciones.microservvicios.test.ValidateInput.trxOper;

public class MinArray {

    public static int mayorNoContenido(int[] B) {

        if ( B == null)
//            throw new RuntimeException("rrr");
//            throw new Exception("Exception, not found");
//            throw new Throwable("Redireccion de error hacia el controlador de errores universales");
            throw new ArithmeticException("Operaion aritmetica no permitida.");

        OptionalInt mayor = Arrays.stream(B).max();

        if (mayor.isPresent() && 0 < mayor.getAsInt()) {
            return mayor.getAsInt() + 1;
        }

        return 1;
    }

    public static int solution(int[] A) {
        int i = 1;
        Set<Integer> set = new HashSet<>();

        for (int n : A)
            set.add(n);

        TreeSet<Integer> arbol = new TreeSet<>();
        for(int a: A)
            arbol.add(a);

        while (set.contains(i))
            i++;

        return i;
    }

    public static void solution_b() {
        //   5
        int[] aaa = null;
        //int[] aaa = {1,3,6,4,1, 2,7,8,9,10,11,12,13,14,15,16,17,15,12,12,13,14,16}; //   5
        //int[] aaa = {1,3,6,4,1, 2}; //   5
        /**/
        //int[] aaa = {1,2,3};     //    4
        /**int[] aaa = {-15,-14,-13,-12,-1, -3,-20,-21,-22};    //    1
         /**/

        //int solucion = solution(aaa);
        int solucion = 1;

        try {
            solucion = mayorNoContenido(aaa);
//        } catch (NullPointerException e) {
//            System.out.println("N::  Problema al intentar procesar la inforamción...");
//        } catch (RuntimeException e) {
//            System.out.println("R::  La lista no es válida, verificar los datos.");
//        } catch (Exception e) {
//            System.out.println("E::  Verificar el flujo o los datos ingresados.");
        } catch (Throwable e) {
            System.out.println("T ::  Manejador de errores universal activo. Error -> ("+e.getClass().getName()+")");
        }

        System.out.println(":::"+solucion);
    }

    static void mmain() {
        //System.out.println(isElegible(-1));
        System.out.println(isElegible(0));      // false
        System.out.println(isElegible(1));      // false
        System.out.println(isElegible(17));     // false
        System.out.println(isElegible(18));     //  true
        System.out.println(isElegible(19));     //  true

        System.out.println(trxOper("null"));
        System.out.println(trxOper("REM"));
        System.out.println(trxOper("SWF"));
        System.out.println(trxOper("VIS"));
    }
}
