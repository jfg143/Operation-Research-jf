/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vrp;

import java.util.ArrayList;

/**
 *
 * @author jfg14
 */
public class Grafo {
    
    int n;
    int[] TSP;
    double[] demand;
    int capacidad;
    int[]v;
    double Coste;
    ArrayList<Cliente> Ciudad = new ArrayList<Cliente>();
    
    Grafo(int[]v,double Coste){
        this.v=v;
        this.Coste=Coste;
    }
    
    Grafo(){
        
    }
    
    Grafo(int n,double[][]CostMatrix,double[] demand,int capacidad,ArrayList<Cliente> Ciudad,int Procesadores) throws InterruptedException{
        int i;
        this.demand=demand;
        this.capacidad=capacidad;
        this.Ciudad=Ciudad;
        
        double valor;
        int dato=0;
        Algoritmo []q=new Algoritmo[Procesadores];
        for(i=0;i<Procesadores;i++)
        {
            q[i]=new Algoritmo(Integer.toString(i),Procesadores,CostMatrix);
            q[i].t.run();
        }
        for(i=0;i<Procesadores;i++){
            q[i].t.join();
        }
        valor=q[0].Coste[0];
        for(i=1;i<Procesadores;i++){
            if(valor<q[i].Coste[i]){
                valor=q[i].Coste[i];
                dato=i;
            }
        }
        dato=0;
        int[] TSP_aux=q[dato].k[dato];
        for(i=0;i<TSP_aux.length;i++)
        {
            if(TSP_aux[i]==0){dato=i;}
        }
        this.TSP=new int[n];
        for(i=dato;i<TSP_aux.length;i++){
            this.TSP[i-dato]=TSP_aux[i];
        }
        for(i=0;i<dato;i++){
            this.TSP[TSP_aux.length-dato+i]=TSP_aux[i];
        }
    }
    
    public int[][] Ejecutar(int n,double[][]CostMatrix,double[] demanda,int capacidad,ArrayList<Cliente> Ciudad,int Procesadores) throws InterruptedException{
        int i=n-1;
        double [][] G=Sacar_grafo(CostMatrix,TSP,capacidad,Ciudad);
        Grafo v=Dijkstra(G,n,n-1);
        ArrayList<Integer> Dividir=new ArrayList<>();
        Dividir.add(v.v[i]);
        while(i !=0){
            Dividir.add(v.v[v.v[i]]);
            i=v.v[v.v[i]];
        }
        int j;
        int[][]Sol=new int[Dividir.size()][n+1];
        for(i=0;i<Dividir.size();i++){
            for(j=0;j<n+1;j++){
                if(j==0){
                    Sol[i][0]=0;
                }
                else{
                    Sol[i][j]=-1;
                }
            }
        }
        
        int aux2;
        int aux3=n;
        for(i=0;i<Dividir.size();i++){
            aux2=Dividir.get(i);
            for(j=aux2+1;j<aux3;j++){
               Sol[i][j-aux2]=TSP[j];
            }
            aux3=aux2+1;
        }
        
        for(i=0;i<Dividir.size();i++){
            for(j=1;j<n+1;j++){
               if(Sol[i][j]==-1){
                   Sol[i][j]=0;
                   break;
               }
            }
        }
        
        return Sol;
    } 
    
    private int[][] Solucion(int[] v,int n,int[]TSP){
        int i=n-1,j;
        ArrayList<Integer> Dividir=new ArrayList<>();
        int[][]Sol=new int[Dividir.size()][n+1];
        Dividir.add(v[i]);
        while(i !=0){
            Dividir.add(v[v[i]]);
            i=v[v[i]];
        }

        for(i=0;i<Dividir.size();i++){
            for(j=0;j<n+1;j++){
                if(j==0){
                    Sol[i][0]=0;
                }
                else{
                    Sol[i][j]=-1;
                }
            }
        }
        int aux;
        for(i=0;i<Dividir.size();i++){
            aux=Dividir.get(i);
            for(j=aux;j<n;j++){
               Sol[i][j]=TSP[j];
            }
        }

        return Sol;
    }
    
    private double[][] Sacar_grafo(double[][] CostMatrix,int[]TSP,int capacidad,ArrayList<Cliente> Ciudad){
        int i,j,k=0;
        double suma;
        int n=TSP.length;
        double [][] G=new double [n-1][n];
        for(i=0;i<n-1;i++){
            G[i][i+1]=2*CostMatrix[0][TSP[i+1]];
        }
        for(i=0;i<(n-2);i++)
        {
            j=i+2;
            suma=Ciudad.get(TSP[i+1]).demanda+Ciudad.get(TSP[j]).demanda;
            
            while(suma<=capacidad){
                
                G[i][j]=Dato(CostMatrix,i,j,TSP);
                j++;
                if(j>n-1){
                    break;
                }
                suma=Ciudad.get(TSP[j]).demanda+suma;
            }
            
        }
        return G;
    }
    
    private static double Dato(double[][]CostMatrix,int i,int j,int[] TSP){
        double dato;
        int i1;
        dato=CostMatrix[0][TSP[i]];
        for(i1=i;i1<j;i1++){
            dato=dato+CostMatrix[TSP[i1]][TSP[i1+1]];
        }
        dato=dato+CostMatrix[0][TSP[j]];
        return dato;
    }
    
    private Grafo Dijkstra(double[][]G,int n,int T){
        double []Distancia=new double[n];
        boolean []Coger=new boolean[n];
        int[]Modificar=new int[n];
        int i;
        for(i=0;i<n;i++){
            Distancia[i]=G[0][i];
            Coger[i]=false;
            Modificar[i]=0;
        }
        Coger[0]=true;
        int p=Coger_Menor(Distancia,n,Coger);
        Coger[p]=true;
        while(p!=T){
            for(i=1;i<n;i++)
            {
                if(Coger[i]==false)
                {
                    if((Distancia[p]+G[p][i]<Distancia[i] && G[p][i]!=0) || (Distancia[i]==0.0 && G[p][i]!=0)){
                        Distancia[i]=Distancia[p]+G[p][i];
                        Modificar[i]=p;
                    }
                }
                
            }
            p=Coger_Menor(Distancia,n,Coger);
            Coger[p]=true;
        }
        
        Grafo Salida=new Grafo(Modificar,Distancia[Distancia.length-1]);
        return Salida;
    }
    
    private static int Coger_Menor(double[]distancia,int n,boolean[]Coger){
        int menor=0;
        double aux=Double.POSITIVE_INFINITY;
        for(int i=1;i<n;i++){
            if(distancia[i]<aux && Coger[i]==false && distancia[i]!=0){
                menor=i;
                aux=distancia[i];
            }
        }
        return menor;
    }
    
    public static void MostrarGrafo(double[][]G){
        int i,j;
        for(i=0;i<G.length;i++)
        {
            for(j=0;j<G[i].length;j++){
                System.out.print(G[i][j]+" ");
            }
            System.out.println();
        }
    }
    
    public static void MostrarTSP(int []G){
        int i;
        for(i=0;i<G.length;i++)
        {
                System.out.print(G[i]+" ");
        }
        System.out.println();
    }
    
    public static void MostrarTSP_2(double []G){
        int i;
        for(i=0;i<G.length;i++)
        {
                System.out.print(G[i]+" ");
        }
        System.out.println();
    }
    
}
