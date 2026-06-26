package com.verassoft.evaluaciones.microservvicios.test;

public class ValidateInput {
    public static boolean isElegible(int age) {
        return switch (age) {
            case int a when 17 < a -> true;
            case int a when a < 0 -> throw new IllegalArgumentException("Edad no válida.");
            default -> false;
        };
    }
    public static String trxOper(String chars) {
        return switch (chars) {
            case String remesa when remesa.equals("REM") -> "REM";
            case String swift when swift.equals("SWF") -> "SWF";
            case String visa when visa.equals("VIS") -> "VIS";
            default -> "Sin clasificacion.";
        };
    }

}
