package vrp;

/**
 *
 * @author jfg14
 */
public class Vehiculo {
    double capacidad;
    double demanda;
    int [] Ruta;
    int id;
    int hilo;
    Vehiculo(int id,int hilo,int capacidad,int[] Ruta, double Coste){
        this.hilo=hilo;
        this.id=id;
        this.capacidad=capacidad;
        this.Ruta=Ruta;
        this.demanda=0;
    }
}


