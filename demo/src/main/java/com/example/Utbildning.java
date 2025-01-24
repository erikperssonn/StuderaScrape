package com.example;

import org.checkerframework.checker.units.qual.s;

public class Utbildning {
    
     private String namn;
     private String uni;
     private String ort;
     private String utbildningstyp;
     private String platsDistans;
     private String examen;
     private String utbildningNiva;
     private String link;
     private String sida;
     private int studietakt;
     private double antalHP;
     private Betyg betyg;

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
    public void setLink(String link){
        this.link = link;
    }
     public void setStudietakt(int studietakt){
         this.studietakt = studietakt;
     }
     public void setAntalHP(double antalHP){
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
    public String getLink(){
        return link;
    }
     public int getStudietakt(){
         return studietakt;
     }
     public double getAntalHP(){
         return antalHP;
     }

    public void setBetyg(Betyg betyg){
        this.betyg = betyg;
    }

    public void setSida(String sida){
        this.sida = sida;
    }   

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        //sb.append("\"Utbildning\": \n");
        sb.append("{\n");
        sb.append("\t" + "\"Namn\": \"" + namn + "\",\n");
        sb.append("\t" + "\"Universitet\": \"" + uni + "\",\n");
        sb.append("\t" + "\"Ort\": \"" + ort + "\",\n");
        sb.append("\t" + "\"Utbildningstyp\": \"" + utbildningstyp + "\",\n");
        sb.append("\t" + "\"PlatsDistans\": \"" + platsDistans + "\",\n");
        sb.append("\t" + "\"Examen\": \"" + examen + "\",\n");
        sb.append("\t" + "\"Utbildningsniva\": \"" + utbildningNiva + "\",\n");
        sb.append("\t" + "\"Link\": \"" + link + "\",\n");
        sb.append("\t" + "\"Studietakt\": \"" + studietakt + "\",\n");
        sb.append("\t" + "\"AntalHP\": \"" + antalHP + "\",\n");
        sb.append("\t" + "\"Sida\": \"" + sida + "\",\n");
        sb.append("\t" + betyg.toString());
        sb.append("},\n");
        return sb.toString();
    }

}
