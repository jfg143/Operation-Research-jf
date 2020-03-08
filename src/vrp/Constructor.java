/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vrp;
import java.util.ArrayList;
import java.util.Random;
/**
 *
 * @author jfg14
 */
public class Constructor {
    int Nclientes;
    int Ncamiones;
    double []Demanda;
    double[][] CostMatrix;
    ArrayList<Vehiculo> Camion = new ArrayList<Vehiculo>();
    ArrayList<Cliente> Ciudad = new ArrayList<Cliente>();
    Constructor(int Nclientes,int procesadores,int Ncamiones,double[] Demanda){
        this.Nclientes=Nclientes;
        this.Demanda=Demanda;
        int i,j;
        for(j=0;j<procesadores;j++){
            for(i=0;i<this.Ncamiones;i++){
                Camion.add(new Vehiculo(i+1,j,0,null,0.0));
            }
        }
        
        Ciudad.add(new Cliente(0,0));
        Random rnd = new Random();
        for(i=1;i<this.Nclientes;i++){
            Ciudad.add(new Cliente(i,rnd.nextDouble()*100,rnd.nextDouble()*100,Demanda[i-1]));
        }
        CostMatrix=new double[Nclientes][Nclientes];
        for(i=0;i<this.Nclientes;i++){
            this.CostMatrix[i][i]=999.0;
            for(j=i+1;j<this.Nclientes;j++){
                this.CostMatrix[i][j]=Math.sqrt(Math.pow((Ciudad.get(i).x-Ciudad.get(j).x), 2)+Math.pow((Ciudad.get(i).y-Ciudad.get(j).y), 2));
                this.CostMatrix[j][i]=this.CostMatrix[i][j];
               
            }
        }
        
    }

    
    
    
}
