package Presentacion.Interfaces;

import Dominio.Moneda;

public interface IGestionarMonedaPR {

    public double calcularCambioSegunMonto(String nombreMonedaActual,String nombreMonedaConversion, double monto);

}
