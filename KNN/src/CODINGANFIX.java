
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import jxl.Sheet;
import jxl.Workbook;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author PEACE
 */
public class CODINGANFIX {
    //cross validation
    

    static DATA[] dataTRAINING=new DATA[4000];
    static DATA[] dataTESTING=new DATA[1000];
    
    static DATA[] data1=new DATA[3500];
    static DATA[] data2=new DATA[3500];
    static DATA[] data3= new DATA[3500];
    static DATA[] data4= new DATA[3500];
    static DATA[] data5= new DATA[3500];
    static DATA[] data6= new DATA[3500];
    static DATA[] data7= new DATA[3500];
    static DATA[] data8= new DATA[3500];

    static DATA[] datavalidation1= new DATA[500]; 
    static DATA[] datavalidation2= new DATA[500];
    static DATA[] datavalidation3= new DATA[500];
    static DATA[] datavalidation4= new DATA[500];
    static DATA[] datavalidation5= new DATA[500];
    static DATA[] datavalidation6= new DATA[500];
    static DATA[] datavalidation7= new DATA[500];
    static DATA[] datavalidation8= new DATA[500];
    
//IMPORT FILE 
    public static DATA[] IMPORTFILE(int barisawal,int barisakhir ,int Startingarray ,  DATA[] data,int sheeet){
    //baris awal = 
        
    File fileexcel =new File ("Dataset Tugas 3 AI 1718.xls");
    int counterarrayawal=Startingarray;
   
    
        if(fileexcel.exists()){
            try{
                Workbook workbook=Workbook.getWorkbook(fileexcel);
                Sheet sheet= workbook.getSheets()[sheeet];
                for (int row=barisawal;row<=barisakhir;row++){
                    DATA dataaa= new DATA();
                    for (int column=0;column<sheet.getColumns();column++){
                        String content=sheet.getCell(column,row).getContents();
                          
                        if (column==0){                   
                            dataaa.setBerita(content);   
                        }
                        else if(column==1){
                            int like = (int) Double.parseDouble(content);
                            dataaa.setLike(like);
                        }
                        else if (column==2){
                            int provokasi = (int) Double.parseDouble(content);
                            dataaa.setProvokasi(provokasi);
                      
                        }
                        else if(column==3){
                            int komentar = (int) Double.parseDouble(content);
                            dataaa.setKomentar(komentar);
                        }
                        else if (column==4){
                            int emosi = (int) Double.parseDouble(content);
                            dataaa.setEmosi(emosi);
                        }
                        else if (column==5){
                            int hoax = Integer.parseInt(content);
                            dataaa.setHoax(hoax);
                        }                   
                    }
                    data[counterarrayawal]=dataaa;
                    counterarrayawal++;
                }   
            }
            catch(Exception e){
                
            }
        }
        else{
            System.out.println("excel tidak tersedia");
        }
     return data;    
    }
    //RUMUS
    public static double rumus (DATA x,DATA y){
         double a= Math.pow(x.getEmosi()-y.getEmosi(),2)
                            + Math.pow(x.getKomentar()-y.getKomentar(), 2)
                            + Math.pow(x.getLike()-y.getLike(), 2)  
                            + Math.pow(x.getProvokasi()-y.getProvokasi(), 2);
        double Hasil= Math.sqrt(a);
        return Hasil;
    }
    
    //SORTING
    public static ArrayList SORTING (ArrayList<HASIL> hasil){
        Collections.sort(hasil, new Comparator<HASIL>() {
               @Override
               public int compare(HASIL o1, HASIL o2) {
                   if (o1.getHasil()>o2.getHasil()){
                       return 1;
                   }
                   else if(o1.getHasil()<o2.getHasil()){
                       return -1;
                   }
               return 0;          
               }
            });
        return hasil;
    }
    
    public static int MODEL (DATA[] x , DATA[] y , int k){
        // x adalah data testing
        //y adalah data training
  
        ArrayList<HASIL> hasil=new ArrayList<>();
        int HOAX;
        int noHOAX;
        int VALIDATE=0;
        
        for (int i=0;i<500;i++){
            hasil.clear();
            HOAX=0;
            noHOAX=0;
               
            for (int j=0;j<3500;j++){
                double hasilrumus = rumus(x[i],y[j]); //hasilrumus
                int status=y[j].getHoax();
                HASIL hasill= new HASIL(hasilrumus,status);
                hasil.add(hasill);               
            }
            
            //SORTING
            hasil=SORTING(hasil);
            
            
               
            // AMBIL NILAI TERENDAH SESUAI K;
            int counter=0;
            for (HASIL hasill:hasil){
                if (counter<k){
                    if (hasill.getHoax()==1){
                        HOAX++;
                    }
                    else if (hasill.getHoax()==0){
                        noHOAX++;
                    }
                }
                counter++;
            }
             
   
            //MENVALIDASI AKURASI DENGAN HASIL YANG SEBENERNYA
         //   System.out.println("DATA YANG SEBENARNYA"+x[i].getHoax());
          
            
            if (HOAX>noHOAX){
             //   System.out.println("HASIL MODEL HOAX");
                if (x[i].getHoax()==1){
                    VALIDATE++;
                }
            }
            else {
              //  System.out.println("HASIL MODEL TIDAK HOAX");
                if (x[i].getHoax()==0){
                    VALIDATE++;
                }
            }
      
         
        }
    //OUTPUT AKURASI ;
      //  System.out.println("AKURASI "+VALIDATE/10 +"%");
      return VALIDATE/5;
  }
    
  //TESTING   
  public static void TESTING (DATA[] x , DATA[] y , int k){
        // x adalah data training
        //y adalah data testing
  
        ArrayList<HASIL> hasil=new ArrayList<>();
        int HOAX;
        int noHOAX;
        int counterDATA=1;
        
        for (int i=0;i<1000;i++){
            hasil.clear();
            HOAX=0;
            noHOAX=0;
               
            for (int j=0;j<4000;j++){
                double hasilrumus = rumus(x[i],y[j]); //hasilrumus
                int status=y[j].getHoax();
                HASIL hasill= new HASIL(hasilrumus,status);
                hasil.add(hasill);               
            }
            
            //SORTING
            hasil=SORTING(hasil);
            
            
               
            // AMBIL NILAI TERENDAH SESUAI K;
            int counter=0;
            for (HASIL hasill:hasil){
                if (counter<k){
                    if (hasill.getHoax()==1){
                        HOAX++;
                    }
                    else if (hasill.getHoax()==0){
                        noHOAX++;
                    }
                }
                counter++;
            }
             
   
           System.out.print("DATA ke : "+counterDATA +"=>");       
            if (HOAX>noHOAX){
                System.out.println("HOAX");             
            }
            else {
                System.out.println("TIDAK HOAX");              
            }
            counterDATA++;
        } 
  }
    
    public static void main(String[] args) {
    int total=0;
    dataTRAINING=IMPORTFILE(1,4000,0,dataTRAINING,0);
    dataTESTING=IMPORTFILE(1,1000,0, dataTESTING, 1);
     
    
    /*
     //UNTUK BAGI PER 1000 
     for (int k=1;k<=99;k=k+2){
     data1=IMPORTFILE(1,3000,0,data1,0);
     datavalidation1=IMPORTFILE(3001,4000,0,datavalidation1,0);
     total=MODEL(datavalidation1,data1,k);
     
     data2=IMPORTFILE(1,2000,0,data2,0);
     data2=IMPORTFILE(3001,4000,2000,data2,0);
     datavalidation2=IMPORTFILE(2001,3000,0,datavalidation2,0);
     total=total+MODEL(datavalidation1,data1,k);
     
     data3=IMPORTFILE(1,1000,0,data3,0);
     datavalidation3=IMPORTFILE(1001,2000,0,datavalidation3,0);
     data3=IMPORTFILE(2001,4000,1000,data3,0);
     total=total+MODEL(datavalidation3,data3,k);
     
     data4=IMPORTFILE(1001,4000,0,data4,0);
     datavalidation4=IMPORTFILE(1,1000,0,datavalidation4,0);
     total=total+MODEL(datavalidation4,data4,k);
         System.out.println(k);
     System.out.println("RATA2 ="+total/4); 
     }
     */
     
    // TESTING(dataTESTING,dataTRAINING,3);
    
          
      // UNTUK BAGI PER 500  
        int nilaiK=25;
        int nilaiakurasi;
        data1=IMPORTFILE(1,3500, 0, data1,0);
        datavalidation1=IMPORTFILE(3501,4000,0,datavalidation1,0);
        nilaiakurasi=MODEL(datavalidation1,data1,nilaiK);
        total=total+nilaiakurasi;
        System.out.println("MODEL 1 :"+nilaiakurasi);
        
        data2=IMPORTFILE (1,3000,0,data2,0);
        data2=IMPORTFILE (3501,4000,3000,data2,0);
        datavalidation2=IMPORTFILE (3001,3500,0,datavalidation2,0);
        nilaiakurasi=MODEL(datavalidation2,data2,nilaiK);
        total=total+nilaiakurasi;
        System.out.println("MODEL 2 :"+nilaiakurasi);
        
        data8=IMPORTFILE (1,3000,0,data8,0);
        data8=IMPORTFILE (3006,4000,3000,data8,0);
        datavalidation8=IMPORTFILE (3001,3500,0,datavalidation8,0);
        nilaiakurasi=MODEL(datavalidation8,data8,nilaiK);
        total=total+nilaiakurasi;
        System.out.println("MODEL 3 :"+nilaiakurasi);
        
        data3=IMPORTFILE (1,2500,0,data3,0);
        data3=IMPORTFILE (3001,4000,2500,data3,0);
        datavalidation3=IMPORTFILE (2501,3000,0,datavalidation3,0);
        nilaiakurasi=MODEL(datavalidation3,data3,nilaiK);
        total=total+nilaiakurasi;
        System.out.println("MODEL 4 :"+nilaiakurasi);
        
        data4=IMPORTFILE (1,1500,0,data4,0);
        datavalidation4=IMPORTFILE (1501,2000,0,datavalidation4,0);
        data4=IMPORTFILE (2001,4000,1500,data4,0);
        nilaiakurasi=MODEL(datavalidation4,data4,nilaiK);
        total=total+nilaiakurasi;
        System.out.println("MODEL 5 :"+nilaiakurasi);
        
        data5=IMPORTFILE (1,1000,0,data5,0);
        datavalidation5=IMPORTFILE (1001,1500,0,datavalidation5,0);
        data5=IMPORTFILE (1501,4000,1000,data5,0);
        nilaiakurasi=MODEL(datavalidation5,data5,nilaiK);
        total=total+nilaiakurasi;
        System.out.println("MODEL 6 :"+nilaiakurasi);
        
        data6=IMPORTFILE (1,500,0,data6,0);
        data6=IMPORTFILE (1001,4000,500,data6,0);
        datavalidation6=IMPORTFILE (501,1000,0,datavalidation6,0);     
        nilaiakurasi=MODEL(datavalidation6,data6,nilaiK);
        total=total+nilaiakurasi;
        System.out.println("MODEL 7 :"+nilaiakurasi);
        
        data7=IMPORTFILE (501,4000,0,data7,0);
        datavalidation7=IMPORTFILE (1,500,0,datavalidation7,0);
        nilaiakurasi=MODEL(datavalidation7,data7,nilaiK);
        total=total+nilaiakurasi;
        System.out.println("MODEL 8 :"+nilaiakurasi);
        
        System.out.println("RATA2 AKURASI ="+total/8+"%");
        TESTING(dataTESTING,dataTRAINING,nilaiK);
       
    }
}