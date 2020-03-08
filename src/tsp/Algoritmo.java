package tsp;
import java.util.Random;
public class Algoritmo implements Runnable {
    int [][]v;
    int [] Coste;
    Thread t;
    int[][]C;
    int Ciudades;
    int [][]k;
    int Mejorado;
    Random rand = new Random();
    Algoritmo(String Name,int Procesadores,int[][]C,int Mejorado){
        t=new Thread(this,Name);
        v=new int[Procesadores][C.length];
        Coste=new int[Procesadores];
        this.C=C;
        Ciudades=C.length;
    }

    public synchronized int[][] PutRuta(int [][] v, int hilo, int i,int value){
        v[hilo][i]=value;
        return v; 
    }
    
    public synchronized int[] PutCoste(int [] v, int hilo,int value){
        v[hilo]=value;
        return v; 
    }
    
    @Override
    public void run() {
        int i,longitud=C.length,hilo=Integer.parseInt(t.getName()),suma=0;
        int[]w=greedy(Ciudades,C);
        /*Aquí iría la búsqueda local*/
        //MostrarRuta(w);
        if(hilo<=8){
            w=BusquedaLocal(Ciudades,C,w);
        }
        else{
            w=BusquedaLocalMejorada(Ciudades,C,w,Mejorado);
        }
        //MostrarRuta(w);
        
        for (i=0;i<(longitud-1);i++){suma+= C[w[i]][w[i+1]];}
        PutCoste(Coste,hilo,suma);
        k=new int[Coste.length][Ciudades];
        for (i=0;i<Ciudades;i++)
        {
            PutRuta(k,hilo,i,w[i]);
        }  
    }
    
    public int [][]getk(){
        return k;
    }
    
    public int []getCoste(){
        return Coste;
    }
    
    private int[] greedy(int Ciudades, int[][]C){
        int n = rand.nextInt(Ciudades);
        boolean[]esta=new boolean[Ciudades];
        int[]w=new int[Ciudades];
        int aux,i,j;
        
        w[0]=n;
        for (i=0;i<Ciudades;i++)
        {
            esta[i]=false;
        }
        esta[n]=true;
        for (j=1;j<Ciudades;j++)
        {
            aux=-1;
            for(i=0;i<Ciudades;i++)
            {
                if(esta[i]==false)
                {
                if(aux>C[w[j-1]][i] || aux==-1){
                //Put(k,hilo,j,i);
                w[j]=i;
                aux=C[w[j-1]][i];}
                }
            }
            esta[w[j]]=true;
        }
        return w;
    }
    
    private int[] BusquedaLocal(int Ciudades, int[][]C,int []w){
        int auxi;
        boolean volver=true;
        int i,j=0;
        while(volver==true)
        {
            volver=false;
            for(i=0;i<Ciudades;i++)
            {
                //MostrarRuta(w);
                for(j=0;j<Ciudades;j++)
                {
                    
                    if(j!=i)
                    {
                        if(i==0){
                            if(j==1)
                            {
                               //System.out.println("primero 1: "+C[w[1]][w[2]]+ " segundo 1: "+C[w[0]][w[2]]);
                               if(C[w[1]][w[2]] > C[w[0]][w[2]]){volver=true;auxi=w[i];w[i]=w[j];w[j]=auxi;/*System.out.println("Ruta 1: ");MostrarRuta(w);*/} 
                            }
                            //1-2-3-4-5-6-7-8-9
                            //9-2-3-4-5-6-7-8-1
                            else if(j==(Ciudades-1))
                            {
                               //System.out.println("primero 2: "+(C[w[0]][w[1]]+C[w[Ciudades-2]][w[Ciudades-1]])+ " segundo 2: "+(C[w[Ciudades-1]][w[1]]+C[w[Ciudades-2]][w[0]]));
                               if(C[w[0]][w[1]]+C[w[Ciudades-2]][w[Ciudades-1]]>C[w[Ciudades-1]][w[1]]+C[w[Ciudades-2]][w[0]]){volver=true;auxi=w[i];w[i]=w[j];w[j]=auxi;/*System.out.println("Ruta 2: ");MostrarRuta(w);*/} 
                            }
                            else
                            {
                                //System.out.println("i: "+i+" j: "+j);
                                //System.out.println("primero 3: "+(C[w[0]][w[1]]+C[w[j-1]][w[j]]+C[w[j]][w[j+1]])+ " segundo 3: "+(C[w[j]][w[1]]+C[w[j-1]][w[0]]+C[w[0]][w[j+1]]));
                                if(C[w[0]][w[1]]+C[w[j-1]][w[j]]+C[w[j]][w[j+1]]>C[w[j]][w[1]]+C[w[j-1]][w[0]]+C[w[0]][w[j+1]]){volver=true;auxi=w[i];w[i]=w[j];w[j]=auxi;/*System.out.println("Ruta 3: ");MostrarRuta(w)*/;}
                            }
                        }
                        else{
                            //System.out.println("j: "+j);
                            //1-2-3-4-5-6-7-8-9
                            //6-2-3-4-5-1-7-8-9
                           if(j==0)
                           {
                               if(i==1)
                                {
                                   //System.out.println("primero 4: "+C[w[1]][w[2]]+ " segundo 4: "+C[w[0]][w[2]]);
                                   if(C[w[1]][w[2]] > C[w[0]][w[2]]){volver=true;auxi=w[i];w[i]=w[j];w[j]=auxi;/*System.out.println("Ruta 4: ");MostrarRuta(w);*/} 
                                }
                               else if(i==(Ciudades-1))
                                {
                                   //System.out.println("primero 5: "+(C[w[0]][w[1]]+C[w[Ciudades-2]][w[Ciudades-1]])+ " segundo 5: "+(C[w[Ciudades-1]][w[1]]+C[w[Ciudades-2]][w[0]]));
                                   if(C[w[0]][w[1]]+C[w[Ciudades-2]][w[Ciudades-1]]>C[w[Ciudades-1]][w[1]]+C[w[Ciudades-2]][w[0]]){volver=true;auxi=w[i];w[i]=w[j];w[j]=auxi;/*System.out.println("Ruta 5: ");MostrarRuta(w);*/} 
                                }
                               else
                               {
                                   //System.out.println("primero 6: "+(C[w[0]][w[1]]+C[w[i-1]][w[i]]+C[w[i]][w[i+1]])+ " segundo 6: "+(C[w[i]][w[1]]+C[w[i-1]][w[0]]+C[w[0]][w[i+1]]));
                                   if(C[w[0]][w[1]]+C[w[i-1]][w[i]]+C[w[i]][w[i+1]]>C[w[i]][w[1]]+C[w[i-1]][w[0]]+C[w[0]][w[i+1]]){volver=true;auxi=w[i];w[i]=w[j];w[j]=auxi;/*System.out.println("Ruta 6: ");MostrarRuta(w);*/}
                               }
                           }
                           else
                           {
                             if(j==(Ciudades-1))
                                {
                                   if(i==Ciudades-2)
                                   {
                                       if(C[w[Ciudades-3]][w[Ciudades-2]]>C[w[Ciudades-3]][w[Ciudades-1]]){volver=true;auxi=w[i];w[i]=w[j];w[j]=auxi;}
                                   }
                                   //System.out.println("primero 7: "+(C[w[i-1]][w[i]]+C[w[i]][w[i+1]]+C[w[Ciudades-2]][w[Ciudades-1]])+ " segundo 7: "+(C[w[i-1]][w[Ciudades-1]]+C[w[Ciudades-1]][w[i+1]]+C[w[Ciudades-2]][w[i]]));
                                   else if(C[w[i-1]][w[i]]+C[w[i]][w[i+1]]+C[w[Ciudades-2]][w[Ciudades-1]]>C[w[i-1]][w[Ciudades-1]]+C[w[Ciudades-1]][w[i+1]]+C[w[Ciudades-2]][w[i]]){volver=true;auxi=w[i];w[i]=w[j];w[j]=auxi;/*System.out.println("Ruta 7: ");MostrarRuta(w);*/} 
                                }
                             else if(i==(Ciudades-1)){
                                 
                                   if(j==Ciudades-2)
                                   {
                                       if(C[w[Ciudades-3]][w[Ciudades-2]]>C[w[Ciudades-3]][w[Ciudades-1]]){volver=true;auxi=w[i];w[i]=w[j];w[j]=auxi;}
                                   }
                                   //System.out.println("i: "+i+" j: "+j+  " primero 8: "+(C[w[j-1]][w[j]]+C[w[j]][w[j+1]]+C[w[Ciudades-2]][w[Ciudades-1]])+ " segundo 8: "+(C[w[j-1]][w[Ciudades-1]]+C[w[Ciudades-1]][w[j+1]]+C[w[Ciudades-2]][w[j]]));
                                   else if(C[w[j-1]][w[j]]+C[w[j]][w[j+1]]+C[w[Ciudades-2]][w[Ciudades-1]]>C[w[j-1]][w[Ciudades-1]]+C[w[Ciudades-1]][w[j+1]]+C[w[Ciudades-2]][w[j]]){volver=true;auxi=w[i];w[i]=w[j];w[j]=auxi;/*System.out.println("Ruta 8: ");MostrarRuta(w);*/}
                                }
                             
                             else if(i==(j-1)){
                                 if(C[w[i]][w[i-1]]+C[w[j]][w[j+1]]>C[w[j]][w[i-1]]+C[w[i]][w[j+1]]){volver=true;auxi=w[i];w[i]=w[j];w[j]=auxi;}
                             }
                             else if(i==(j+1)){
                                 if(C[w[i]][w[i+1]]+C[w[j]][w[j-1]]>C[w[j]][w[i+1]]+C[w[i]][w[j-1]]){volver=true;auxi=w[i];w[i]=w[j];w[j]=auxi;}
                             }
                             else if(C[w[i-1]][w[i]]+C[w[i]][w[i+1]]+C[w[j-1]][w[j]]+C[w[j]][w[j+1]]>C[w[i-1]][w[j]]+C[w[j]][w[i+1]]+C[w[j-1]][w[i]]+C[w[i]][w[j+1]]){/*System.out.println("primero 9: "+C[w[i-1]][w[i]]+C[w[i]][w[i+1]]+C[w[j-1]][w[j]]+C[w[j]][w[j+1]]+ " segundo 9: "+C[w[i-1]][w[j]]+C[w[j]][w[i+1]]+C[w[j-1]][w[i]]+C[w[i]][w[j+1]]);*/volver=true;auxi=w[i];w[i]=w[j];w[j]=auxi;/*System.out.println("Ruta 9: ");MostrarRuta(w);*/}  
                           }
                        }
                    }
                }
            }
        }
        return w;
    }
    
    private int[] BusquedaLocalMejorada(int Ciudades, int[][]C,int []w, int Mejora){
        int auxi;
        boolean volver=true;
        int i,j;
        while(volver==true)
        {
            volver=false;
            for(i=0;i<Ciudades;i++)
            {
                //MostrarRuta(w);
                for(j=0;j<Ciudades;j++)
                {
                    
                    if(j!=i)
                    {
                        if(i==0){
                            if(j==1)
                            {
                               //System.out.println("primero 1: "+C[w[1]][w[2]]+ " segundo 1: "+C[w[0]][w[2]]);
                               if(C[w[1]][w[2]]+Mejora > C[w[0]][w[2]]){volver=true;auxi=w[i];w[i]=w[j];w[j]=auxi;/*System.out.println("Ruta 1: ");MostrarRuta(w);*/} 
                            }
                            //1-2-3-4-5-6-7-8-9
                            //9-2-3-4-5-6-7-8-1
                            else if(j==(Ciudades-1))
                            {
                               //System.out.println("primero 2: "+(C[w[0]][w[1]]+C[w[Ciudades-2]][w[Ciudades-1]])+ " segundo 2: "+(C[w[Ciudades-1]][w[1]]+C[w[Ciudades-2]][w[0]]));
                               if(C[w[0]][w[1]]+C[w[Ciudades-2]][w[Ciudades-1]]+Mejora>C[w[Ciudades-1]][w[1]]+C[w[Ciudades-2]][w[0]]){volver=true;auxi=w[i];w[i]=w[j];w[j]=auxi;/*System.out.println("Ruta 2: ");MostrarRuta(w);*/} 
                            }
                            else
                            {
                                //System.out.println("i: "+i+" j: "+j);
                                //System.out.println("primero 3: "+(C[w[0]][w[1]]+C[w[j-1]][w[j]]+C[w[j]][w[j+1]])+ " segundo 3: "+(C[w[j]][w[1]]+C[w[j-1]][w[0]]+C[w[0]][w[j+1]]));
                                if(C[w[0]][w[1]]+C[w[j-1]][w[j]]+C[w[j]][w[j+1]]+Mejora>C[w[j]][w[1]]+C[w[j-1]][w[0]]+C[w[0]][w[j+1]]){volver=true;auxi=w[i];w[i]=w[j];w[j]=auxi;/*System.out.println("Ruta 3: ");MostrarRuta(w)*/;}
                            }
                        }
                        else{
                            //System.out.println("j: "+j);
                            //1-2-3-4-5-6-7-8-9
                            //6-2-3-4-5-1-7-8-9
                           if(j==0)
                           {
                               if(i==1)
                                {
                                   //System.out.println("primero 4: "+C[w[1]][w[2]]+ " segundo 4: "+C[w[0]][w[2]]);
                                   if(C[w[1]][w[2]]+Mejora > C[w[0]][w[2]]){volver=true;auxi=w[i];w[i]=w[j];w[j]=auxi;/*System.out.println("Ruta 4: ");MostrarRuta(w);*/} 
                                }
                               else if(i==(Ciudades-1))
                                {
                                   //System.out.println("primero 5: "+(C[w[0]][w[1]]+C[w[Ciudades-2]][w[Ciudades-1]])+ " segundo 5: "+(C[w[Ciudades-1]][w[1]]+C[w[Ciudades-2]][w[0]]));
                                   if(C[w[0]][w[1]]+C[w[Ciudades-2]][w[Ciudades-1]]+Mejora>C[w[Ciudades-1]][w[1]]+C[w[Ciudades-2]][w[0]]){volver=true;auxi=w[i];w[i]=w[j];w[j]=auxi;/*System.out.println("Ruta 5: ");MostrarRuta(w);*/} 
                                }
                               else
                               {
                                   //System.out.println("primero 6: "+(C[w[0]][w[1]]+C[w[i-1]][w[i]]+C[w[i]][w[i+1]])+ " segundo 6: "+(C[w[i]][w[1]]+C[w[i-1]][w[0]]+C[w[0]][w[i+1]]));
                                   if(C[w[0]][w[1]]+C[w[i-1]][w[i]]+C[w[i]][w[i+1]]+Mejora>C[w[i]][w[1]]+C[w[i-1]][w[0]]+C[w[0]][w[i+1]]){volver=true;auxi=w[i];w[i]=w[j];w[j]=auxi;/*System.out.println("Ruta 6: ");MostrarRuta(w);*/}
                               }
                           }
                           else
                           {
                             if(j==(Ciudades-1))
                                {
                                   if(i==Ciudades-2)
                                   {
                                       if(C[w[Ciudades-3]][w[Ciudades-2]]+Mejora>C[w[Ciudades-3]][w[Ciudades-1]]){volver=true;auxi=w[i];w[i]=w[j];w[j]=auxi;}
                                   }
                                   //System.out.println("primero 7: "+(C[w[i-1]][w[i]]+C[w[i]][w[i+1]]+C[w[Ciudades-2]][w[Ciudades-1]])+ " segundo 7: "+(C[w[i-1]][w[Ciudades-1]]+C[w[Ciudades-1]][w[i+1]]+C[w[Ciudades-2]][w[i]]));
                                   else if(C[w[i-1]][w[i]]+C[w[i]][w[i+1]]+C[w[Ciudades-2]][w[Ciudades-1]]+Mejora>C[w[i-1]][w[Ciudades-1]]+C[w[Ciudades-1]][w[i+1]]+C[w[Ciudades-2]][w[i]]){volver=true;auxi=w[i];w[i]=w[j];w[j]=auxi;/*System.out.println("Ruta 7: ");MostrarRuta(w);*/} 
                                }
                             else if(i==(Ciudades-1)){
                                 
                                   if(j==Ciudades-2)
                                   {
                                       if(C[w[Ciudades-3]][w[Ciudades-2]]+Mejora>C[w[Ciudades-3]][w[Ciudades-1]]){volver=true;auxi=w[i];w[i]=w[j];w[j]=auxi;}
                                   }
                                   //System.out.println("i: "+i+" j: "+j+  " primero 8: "+(C[w[j-1]][w[j]]+C[w[j]][w[j+1]]+C[w[Ciudades-2]][w[Ciudades-1]])+ " segundo 8: "+(C[w[j-1]][w[Ciudades-1]]+C[w[Ciudades-1]][w[j+1]]+C[w[Ciudades-2]][w[j]]));
                                   else if(C[w[j-1]][w[j]]+C[w[j]][w[j+1]]+C[w[Ciudades-2]][w[Ciudades-1]]+Mejora>C[w[j-1]][w[Ciudades-1]]+C[w[Ciudades-1]][w[j+1]]+C[w[Ciudades-2]][w[j]]){volver=true;auxi=w[i];w[i]=w[j];w[j]=auxi;/*System.out.println("Ruta 8: ");MostrarRuta(w);*/}
                                }
                             
                             else if(i==(j-1)){
                                 if(C[w[i]][w[i-1]]+C[w[j]][w[j+1]]+Mejora>C[w[j]][w[i-1]]+C[w[i]][w[j+1]]){volver=true;auxi=w[i];w[i]=w[j];w[j]=auxi;}
                             }
                             else if(i==(j+1)){
                                 if(C[w[i]][w[i+1]]+C[w[j]][w[j-1]]+Mejora>C[w[j]][w[i+1]]+C[w[i]][w[j-1]]){volver=true;auxi=w[i];w[i]=w[j];w[j]=auxi;}
                             }
                             else if(C[w[i-1]][w[i]]+C[w[i]][w[i+1]]+C[w[j-1]][w[j]]+C[w[j]][w[j+1]]+Mejora>C[w[i-1]][w[j]]+C[w[j]][w[i+1]]+C[w[j-1]][w[i]]+C[w[i]][w[j+1]]){/*System.out.println("primero 9: "+C[w[i-1]][w[i]]+C[w[i]][w[i+1]]+C[w[j-1]][w[j]]+C[w[j]][w[j+1]]+ " segundo 9: "+C[w[i-1]][w[j]]+C[w[j]][w[i+1]]+C[w[j-1]][w[i]]+C[w[i]][w[j+1]]);*/volver=true;auxi=w[i];w[i]=w[j];w[j]=auxi;/*System.out.println("Ruta 9: ");MostrarRuta(w);*/}  
                           }
                        }
                    }
                }
            }
            if(Mejora!=0){
                Mejora--;
            }
        }
        return w;
    }
    
    private synchronized void MostrarRuta(int[]w){
        int i;
        for(i=0;i<w.length;i++)
        {
            System.out.println(w[i]);
        }
        System.out.println();
    }
    
}
