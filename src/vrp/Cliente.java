
package vrp;

/**
 *
 * @author Juan Francisco Gómez González
 * 
 */
public class Cliente{
    double x;
    double y;
    double demanda;
    int id;
    Cliente(double x,double y){
        this.id=0;
        this.x=x;
        this.y=y;
    }
    
    Cliente(int id,double x,double y,double demanda){
        this.id=id;
        this.x=x;
        this.y=y;
        this.demanda=demanda;
    }

}
