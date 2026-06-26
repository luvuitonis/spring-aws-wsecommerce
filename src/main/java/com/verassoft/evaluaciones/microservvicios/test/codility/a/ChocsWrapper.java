package com.verassoft.evaluaciones.microservvicios.test.codility.a;

import java.util.HashSet;
import java.util.Set;

public class ChocsWrapper {

    public int solution(int m, int n) {
        Set<Integer> chocs = new HashSet<>();
        int posicionChoc = 0;

        while (!chocs.contains(posicionChoc % n)) {
            chocs.add(posicionChoc % n);
            posicionChoc+=m;
        }

        return chocs.size();
    }

    static void mmain(String[] args) {
        ChocsWrapper wrappers = new ChocsWrapper();

        System.out.println(":: " +
                wrappers.solution(
                        Integer.parseInt(args[0]),
                        Integer.parseInt(args[1])
                ));
    }
}
