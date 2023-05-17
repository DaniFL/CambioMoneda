package aplicacion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Principal {

    // Conjunto de tipos de monedas válidas
    public static List<Integer> TIPOS_MONEDAS = Arrays.asList(1, 2, 5, 10, 20, 50, 100, 200);

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce el valor del cambio a devolver: ");
        int importe = sc.nextInt();
        sc.close();
        List<Integer> resultado = desgloseMinimo(importe);

        System.out.println("El cambio de " + importe + " euros en el menor número de monedas es:");
        System.out.println(resultado);
        System.out.println("El cambio de " + importe + " euros en el menor número de monedas RECURSIVO es:");
        System.out.println(cambioRecursivo(importe, TIPOS_MONEDAS));
        System.out.println("El cambio de " + importe + " euros en el menor número de monedas ITERATIVO es:");
        System.out.println(cambioIterativo(importe));
        System.out.println("El cambio de " + importe + " euros en el menor número de monedas EXPRESIONES LAMBDA es:");
        System.out.println(cambioLambda(importe));

        System.out.println("----------------------");

        System.out.println("El cambio de " + importe + " euros RECURSIVO indicando el nº de monedas a devolver es:");
        System.out.println(cambioRecursivo2(importe, TIPOS_MONEDAS));
        System.out.println("El cambio de " + importe + " euros ITERATIVO indicando el nº de monedas a devolver es:");
        System.out.println(cambioIterativo2(importe, TIPOS_MONEDAS));
        System.out.println("El cambio de " + importe + " euros EXPRESIONES LAMBDA indicando el nº de monedas a devolver es:");
        System.out.println(cambioLamba2(importe, TIPOS_MONEDAS));

        System.out.println("----------------------");

        System.out.println("El cambio de " + importe + " euros RECURSIVO EXAMEN es:");
        System.out.println(cambioRecursivo3(importe, resultado));
        
    }

    // MÉTODO 1 (PRINCIPAL)

    public static List<Integer> desgloseMinimo(int importe) {
        // Ordenamos los tipos de monedas de mayor a menor valor
        Collections.sort(TIPOS_MONEDAS, Collections.reverseOrder());

        // Creamos una lista para ir guardando las monedas devueltas
        List<Integer> monedasDevueltas = new ArrayList<>();

        // Realizamos el desglose iterativamente
        while (importe > 0) {
            // Buscamos la moneda de mayor valor posible que no supere el importe restante
            int monedaElegida = -1;
            for (int i = 0; i < TIPOS_MONEDAS.size(); i++) {
                if (TIPOS_MONEDAS.get(i) <= importe) {
                    monedaElegida = TIPOS_MONEDAS.get(i);
                    break;
                }
            }

            // Añadimos la moneda elegida a la lista de monedas devueltas y restamos su
            // valor del importe
            monedasDevueltas.add(monedaElegida);
            importe -= monedaElegida;
        }

        return monedasDevueltas;
    }

    // Método 2 (Recursivo)
    public static List<Integer> cambioRecursivo(int cambio, List<Integer> TIPOS_MONEDAS) {
        List<Integer> monedasDevueltas = new ArrayList<>();

        // Caso base: si el cambio es 0, no se necesitan monedas
        if (cambio == 0) {
            return null;
        }
        // 2º caso base: si el cambio es negativo, no se necesitan monedas
        if (cambio < 0) {
            return null;
        }
        // Caso recursivo: si el cambio es mayor que 0, se necesitan monedas
        else {
            // Buscamos la moneda de mayor valor posible que no supere el importe restante
            int monedaElegida = -1;
            for (int i = 0; i < TIPOS_MONEDAS.size(); i++) {
                if (TIPOS_MONEDAS.get(i) <= cambio) {
                    monedaElegida = TIPOS_MONEDAS.get(i);

                }
            }
            // Añadimos la moneda elegida a la lista de monedas devueltas y restamos su
            // valor del importe
            monedasDevueltas.add(monedaElegida);
            cambio -= monedaElegida;
            // Llamada recursiva
            cambioRecursivo(cambio, TIPOS_MONEDAS);
        }
        return monedasDevueltas;
    }

    // MÉTODO 3 (ITERATIVO)

    public static List<Integer> cambioIterativo(int importe) {
        // Ordenamos los tipos de monedas de mayor a menor valor
        Collections.sort(TIPOS_MONEDAS, Collections.reverseOrder());

        // Creamos una lista para ir guardando las monedas devueltas
        List<Integer> monedasDevueltas = new ArrayList<>();

        // Realizamos el desglose iterativamente
        for (int i = 0; i < TIPOS_MONEDAS.size(); i++) {
            while (importe >= TIPOS_MONEDAS.get(i)) {
                monedasDevueltas.add(TIPOS_MONEDAS.get(i));
                importe -= TIPOS_MONEDAS.get(i);
            }
        }
        return monedasDevueltas;
    }

    /*
     * public static List<Integer> desgloseIterativo(int importe) {
     * List<Integer> monedasDevueltas = new ArrayList<>();
     * 
     * // Recorremos los tipos de monedas de mayor a menor valor
     * for (int i = 0; i < TIPOS_MONEDAS.size() && importe > 0; i++) {
     * int monedaActual = TIPOS_MONEDAS.get(i);
     * if (monedaActual > importe) {
     * continue;
     * }
     * 
     * // Calculamos la cantidad de monedas necesarias de la moneda actual
     * int cantidadMonedas = importe / monedaActual;
     * for (int j = 0; j < cantidadMonedas; j++) {
     * monedasDevueltas.add(monedaActual);
     * }
     * 
     * // Actualizamos el importe restante
     * importe -= cantidadMonedas * monedaActual;
     * }
     * 
     * return monedasDevueltas;
     * }
     * }
     * 
     * En esta versión, aplicamos las mismas optimizaciones que en la versión
     * recursiva, es decir, recorremos los tipos de monedas en un bucle for y
     * calculamos la cantidad de monedas necesarias para cada uno de ellos. De esta
     * manera, reducimos la cantidad de llamadas a la función y mejoramos la
     * eficiencia del algoritmo.
     */

    // MÉTODO 4 (EXPRESIONES LAMBDA)

    public static List<Integer> cambioLambda(int importe) {
        List<Integer> monedasDevueltas = new ArrayList<>();
        final int[] importeRestante = { importe };

        TIPOS_MONEDAS.stream()
                .filter(moneda -> moneda <= importeRestante[0])
                .forEach(moneda -> {
                    int cantidadMonedas = importeRestante[0] / moneda;
                    for (int i = 0; i < cantidadMonedas; i++) {
                        monedasDevueltas.add(moneda);
                    }
                    importeRestante[0] -= cantidadMonedas * moneda;
                });

        return monedasDevueltas;
    }
    /*
     * En esta versión, declaramos un array de un elemento llamado importeRestante y
     * lo inicializamos con el valor del importe pasado como parámetro a la función.
     * En el stream, utilizamos importeRestante[0] para obtener el importe restante
     * en lugar de la variable importe. De esta manera, podemos modificar el valor
     * del importe restante dentro del stream.
     */

    // -------------------------------------------------------------------------------------------------------------------------------------------

    // METODO 5 (RECURSIVO - N MONEDAS)

    public static int cambioRecursivo2(int cambio, List<Integer> TIPOS_MONEDAS) {
        // caso base: si el cambio es 0, no se necesitan monedas
        if (cambio == 0) {
            return 0;
        }
        // 2º caso base: si el cambio es negativo, no se necesitan monedas
        if (cambio < 0) {
            return 0;
        }
        // caso recursivo: si el cambio es mayor que 0, se necesitan monedas
        if (cambio > 0) {
            System.out.println("ENTRO");
            if (cambio % TIPOS_MONEDAS.get(0) == 0) {
                return 1;
            } else {
                return 1 + cambioRecursivo2(cambio - TIPOS_MONEDAS.get(0), TIPOS_MONEDAS);
            }
        }
        return 0;
    }

    // MÉTODO 6 (ITERATIVO - N MONEDAS)

    public static int cambioIterativo2(int cambio, List<Integer> sistemaMonedas) {
        int numMonedas = 0;
        for (int i = 0; i < sistemaMonedas.size(); i++) {
            if (cambio >= sistemaMonedas.get(i)) {
                numMonedas += cambio / sistemaMonedas.get(i);
                cambio = cambio % sistemaMonedas.get(i); // cambio * (cambio /
                sistemaMonedas.get(i);
            }
        }
        return numMonedas;
    }

    // MÉTODO 7 (EX LAMBDA - N MONEDAS)

    public static int cambioLamba2(int cambio, List<Integer> sistemaMonedas) {
        // devolver numero monedas necesarias para cambiar el cambio de un sistema de
        // monedas con una lista de monedas y un cambio con expresiones lambda
        return sistemaMonedas.stream().filter(moneda -> cambio >= sistemaMonedas.get(0))
                .mapToInt(moneda -> cambio / sistemaMonedas.get(0)).sum();
    }

    // -------------------------------------------------------------------------------------------------------------------------------------------

    // MÉTODO 8 (RECURSIVO - N MONEDAS EXAMEN)
    public static int cambioRecursivo3(int cambio, List<Integer> sistemaMonedas) {
        int[] resultado = new int[1];
        cambioRecursivo4(cambio, 0 ,sistemaMonedas, resultado);
        return resultado[0];
    }

    private static void cambioRecursivo4(int cambio, int i, List<Integer> sistemaMonedas, int resultado[]){
        // Caso Base 1 (cambio < 0)
        if(cambio < 0){
            return;
        }
        // Caso Base 2 (cambio = 0)
        if(cambio == 0){
            return;
        }
        else{
            //int moneda = sistemaMonedas.get(i);
            if(cambio >= sistemaMonedas.get(i)){
                //System.out.println("Cambio: " + cambio + " i: " + i);
                resultado[0] += cambio / sistemaMonedas.get(i);
                cambio = cambio % sistemaMonedas.get(i);
                cambioRecursivo4(cambio, ++i, sistemaMonedas, resultado);
            }
            else{
                //System.out.println("Cambio: " + cambio + " i: " + i);
                cambioRecursivo4(cambio, ++i, sistemaMonedas, resultado);
            }
        }
    }
}

