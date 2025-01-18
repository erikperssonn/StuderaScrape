package com.example;

public class Utbildning {
    
     private String namn;
     private String uni;
     private String ort;
     private String utbildningstyp;
     private String platsDistans;
     private String examen;
     private String utbildningNiva;
     private int studietakt;
     private int antalHP;

     public Utbildning  (){

     }

     public void setNamn(String namn){
         this.namn = namn;
     }

     public void setUni(String uni){
         this.uni = uni;
     }
     public void setOrt(String ort){
         this.ort = ort;
     }
     public void setUtbildningstyp(String utbildningstyp){
         this.utbildningstyp = utbildningstyp;
     }
     public void setPlatsDistans(String platsDistans){
         this.platsDistans = platsDistans;
     }
     public void setExamen(String examen){
         this.examen = examen;
     }
     public void setUtbildningNiva(String utbildningNiva){
         this.utbildningNiva = utbildningNiva;
     }
     public void setStudietakt(int studietakt){
         this.studietakt = studietakt;
     }
     public void setAntalHP(int antalHP){
         this.antalHP = antalHP;
     }
     public String getNamn(){
         return namn;
     }
     public String getUni(){
         return uni;
     }
     public String getOrt(){
         return ort;
     }
     public String getUtbildningstyp(){
         return utbildningstyp;
     }
     public String getPlatsDistans(){
         return platsDistans;
     }
     public String getExamen(){
         return examen;
     }
     public String getUtbildningNiva(){
         return utbildningNiva;
     }
     public int getStudietakt(){
         return studietakt;
     }
     public int getAntalHP(){
         return antalHP;
     }



}
