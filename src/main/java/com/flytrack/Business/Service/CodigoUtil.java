package com.flytrack.Business.Service;

public class CodigoUtil {

    public static String generarCodigo() {
        return String.valueOf((int)(Math.random() * 900000) + 100000);
    }

}