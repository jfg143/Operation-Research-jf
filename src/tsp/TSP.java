/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp;

public class TSP {

    public static void main(String[] args) throws InterruptedException {
       int i1,i2,igual,Ciudades; 
       int el_mejor,el_mejor_Mejorado,Mejorado;
       int[] Ciudades1={20,40,60,80,100,200,500};
       for(int r=0;r<Ciudades1.length;r++)
       {
        Ciudades=Ciudades1[r];
        i1=0;
        i2=0;
        igual=0;
        el_mejor=-1;
        el_mejor_Mejorado=0;
       for(Mejorado=50;Mejorado>=10;Mejorado--)
       {
        for(int e=0;e<10;e++)
        {
         int p=8;
         int lim=100;
         GenerarProblema auxi=new GenerarProblema(Ciudades,lim);
         int [][]C=auxi.GetC();
         Algoritmo []q=new Algoritmo[p];
         for(int i=0;i<p;i++)
         {
             q[i]=new Algoritmo(Integer.toString(i),p,C,Mejorado);
             q[i].t.run();
         }
         for(int i=0;i<p;i++){
              q[i].t.join();
          }
         System.out.println("Costes");
         for(int i=0;i<p;i++)
         {
             int[]Costs=q[i].Coste;
             System.out.println(Costs[i]);
         }
         
         System.out.println("A partir de aquí todo es mierda");
         
         
         int []Costes;
         int CosteFinal;
         int aux=0,mejor;
         boolean quitar=false;
         int i,max_peor,max_mejor;

         max_peor=-1;
         for(i=0;i<=3;i++)
         {
             Costes=q[i].getCoste();
             if(max_peor==-1 || max_peor>Costes[i]){max_peor=Costes[i];}
         }

         max_mejor=-1;
         for(i=4;i<8;i++)
         {
             Costes=q[i].getCoste();
             if(max_mejor==-1 || max_mejor>Costes[i]){max_mejor=Costes[i];}
         }

         if(max_peor==max_mejor){igual++;quitar=true;}

         if(quitar==false){
             CosteFinal=-1;
             for(i=0;i<p;i++)
             {
                 Costes=q[i].getCoste();
                 if(CosteFinal==-1 || CosteFinal>Costes[i]){CosteFinal=Costes[i];aux=i;}
             }
             if(aux<=3){i1++;}
             else{i2++;}
         }

         //System.out.println("El mejor ha sido: "+aux);
         /*int [][]v=q[aux].getk();
         System.out.println("La ruta es:");
         for (int i=0;i<C.length;i++)
         {
             System.out.println(v[aux][i]);
         }
         System.out.println("Tiene un coste de: "+CosteFinal);*/

      }
        /*System.out.println("No mejorado ha sido: "+i1+ " Los mejorados han sido: "+ i2 + " han quedado igual en: "+igual);*/
        /*if(i1>i2){System.out.println("Ha salido victorioso el algoritmo no mejoraso por "+(i1-i2)+" problemas.");}*/
        if(i1<i2){
            //System.out.println("Ha salido victorioso mi algoritmo, hurra!, hemos ganado en " +(i2-i1)+" problemas con "+Ciudades+" ciudades");
            if(el_mejor<(i2-i1)){el_mejor=(i2-i1);el_mejor_Mejorado=Mejorado; }
        }
    
    }   
    System.out.println("El mejor de todos ha tenido una ganancia en: "+el_mejor+" y esta variable ha sido: "+el_mejor_Mejorado+" esto ha ocurrido para un número de ciudades igual a "+Ciudades);
    } 
    }
}
