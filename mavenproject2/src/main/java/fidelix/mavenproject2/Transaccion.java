
package fidelix.mavenproject2;

import java.util.Date;

public class Transaccion {
    private String tipo;
    private double monto;
    private Date fecha;

    public Transaccion(String tipo, double monto) {
        this.tipo = tipo;
        this.monto = monto;
        this.fecha = new Date();
    }

    public String getTipo() {
        return tipo;
    }

    public double getMonto() {
        return monto;
    }

    public Date getFecha() {
        return fecha;
    }

    @Override
    public String toString() {
        return tipo + ": " + monto + " en " + fecha;
    }
}
