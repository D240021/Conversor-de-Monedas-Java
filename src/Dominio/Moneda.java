package Dominio;

public class Moneda {

    private String nombre;
    private double valor;


    public Moneda(String nombre, double valor) {
        this.nombre = nombre;
        this.valor = valor;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return
                "Nombre: '" + nombre + '\'' +
                "Valor: " + valor;
    }
}
