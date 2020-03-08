/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp;
import java.util.Random;

public class GenerarProblema {
    
    int[][]C;
    int n;
    GenerarProblema(int Ciudades,int lim){
        C=new int[Ciudades][Ciudades];
        int i,j;
        Random rand = new Random();
        for(i=0;i<Ciudades;i++)
        {
            for(j=i+1;j<Ciudades;j++)
            {
                n = rand.nextInt(lim);
                C[i][j]=n;
            }
        }
        
        for(i=0;i<Ciudades;i++)
        {
            for(j=i+1;j<Ciudades;j++)
            {
                n = rand.nextInt(lim);
                C[j][i]=C[i][j];
            }
        }
        
        for(i=0;i<Ciudades;i++)
        {
            C[i][i]=lim*10;
        }
        
    }
    
    public int[][] GetC(){return C;}
}
