package Presentacion;

import Datos.MonedaDA;
import Dominio.Moneda;
import Presentacion.Interfaces.IGestionarMonedaPR;

import java.util.Scanner;

public class CambioMoneda implements IGestionarMonedaPR {

    private final MonedaDA monedaDA;

    public CambioMoneda() {
        this.monedaDA = new MonedaDA();
    }

    public void mostrarMenuInicial() {

        System.out.println("Bienvenido, usuario!\n" +
                "A continuación los cambios de moneda disponibles:\n" +
                "\n"+
                "1.Colón Costarricense (CRC) --> Peso Argentino (ARS)\n" +
                "2.Peso Argentino (ARS) --> Colón Costarricense (CRC)\n" +
                "3.Colón Costarricense (CRC) --> Dólar Estadounidense (USD)\n" +
                "4.Dólar Estadounidense (USD) --> Colón Costarricense (CRC)\n" +
                "5.Colón Costarricense (CRC) --> Euro (EUR)\n" +
                "6.Euro (EUR) --> Colón Costarricense (CRC)\n");

    }

    public void iniciarPrograma(){

        this.mostrarMenuInicial();
        this.operarOpcionUsuario();
        this.verificarNuevoCambio();
    }

    public void operarOpcionUsuario() {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Seleccione el número de opción a seleccionar (1-6):");

            if (!scanner.hasNextInt()) {
                System.out.println("Entrada no válida. Por favor, inserte un número entre 1 y 6.");
                scanner.next(); // Consumir la entrada no válida
                continue;
            }

            int numeroOpcion = scanner.nextInt();
            double montoConsulta;

            if (numeroOpcion <= 0 || numeroOpcion > 6) {
                System.out.println("Inserte una opción válida (1-6).");
            } else {
                while (true) {
                    System.out.println("Digite el monto a consultar:");

                    if (!scanner.hasNextDouble()) {
                        System.out.println("Entrada no válida. Por favor, inserte un número positivo.");
                        scanner.next(); // Consumir la entrada no válida
                        continue;
                    }

                    montoConsulta = scanner.nextDouble();

                    if (montoConsulta <= 0) {
                        System.out.println("Inserte un monto válido (mayor que 0).");
                        continue;
                    }
                    break;
                }

                String[] monedas = this.obtenerNombresMonedas(numeroOpcion);
                String nombreMonedaActual = monedas[0];
                String nombreMonedaAConvertir = monedas[1];
                double resultado = this.calcularCambioSegunMonto(nombreMonedaActual, nombreMonedaAConvertir, montoConsulta);

                System.out.println("\n" + montoConsulta + " " + monedas[0] + " equivale a " + resultado + " " + monedas[1]);
                break;
            }
        }
    }

    public void verificarNuevoCambio() {

        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("Desea realizar otro cambio de moneda? (S|N)");
            String respuesta = scanner.next();

            if(!respuesta.equals("s") && !respuesta.equals("n")){
                System.out.println("Inserte un valor válido (S|N)");
            } else if( respuesta.equals("s") ) {
                this.iniciarPrograma();
            }else {
                break;
            }
        }


    }


    public String[] obtenerNombresMonedas(int numeroOpcion) {

        String[] monedas;

        switch (numeroOpcion) {
            case 1:
                monedas = new String[]{"CRC", "ARS"};
                break;
            case 2:
                monedas = new String[]{"ARS", "CRC"};
                break;
            case 3:
                monedas = new String[]{"CRC", "USD"};
                break;
            case 4:
                monedas = new String[]{"USD", "CRC"};
                break;
            case 5:
                monedas = new String[]{"CRC", "EUR"};
                break;
            default:
                monedas = new String[]{"EUR", "CRC"};
                break;
        }

        return monedas;
    }

    @Override
    public double calcularCambioSegunMonto(String nombreMonedaActual,String nombreMonedaConversion, double monto) {

        Moneda[] monedas = this.monedaDA.obtenerCambiosSegunMoneda(nombreMonedaActual);

        for (int i = 0; i < monedas.length; i++){
            if (monedas[i].getNombre().equals(nombreMonedaConversion)){
                return  monedas[i].getValor() * monto;
            }

        }

        return 0;
    }


}
