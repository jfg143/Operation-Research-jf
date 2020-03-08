
package vrp;
import java.util.ArrayList;
/**
 *
 * @author jfg14
 */
public class VRP {

    
    private static void mostrar_matriz_d(int[][] r){
        for(int i=0;i<r.length;i++)
        {
            for(int j=0;j<r[i].length;j++)
            {
                System.out.print(r[i][j]+" ");
            }
            System.out.println();
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        int p=4;//Procesadores
        double[] demanda={20.0,25.3,21.4,20.9,50.1,45.65,15.42};
        Constructor nuevo=new Constructor(8,4,3,demanda);
        Grafo aux=new Grafo(8,nuevo.CostMatrix,demanda,100,nuevo.Ciudad,4);
        int[][]Sol=aux.Ejecutar(8,nuevo.CostMatrix,demanda,100,nuevo.Ciudad,4);
        mostrar_matriz_d(Sol);
    } 
}
