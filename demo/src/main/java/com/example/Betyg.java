package com.example;

import java.util.ArrayList;
import java.util.List;

import org.checkerframework.checker.units.qual.t;

import java.util.HashSet;
import java.util.Iterator;

public class Betyg {
    private boolean BI;
    private boolean BII;
    private boolean HP;
    private List<String> stringList;
    private List<BetygRow> betygRowList;
    private HashSet<String> termSet;
    private List<BetygRow> finalList;

    public Betyg() {
        this.stringList = new ArrayList<>();
        this.betygRowList = new ArrayList<>();
        this.termSet = new HashSet<>();
        this.finalList = new ArrayList<>(); 
    }

    public void setBetygBooleans(){
        for (String s : stringList){
            if (s.contains("BI")){
                BI = true;
                System.out.println("BI: " + BI);
            }
            if (s.contains("BII")){
                BII = true;
                System.out.println("BII: " + BII);
            }
            if (s.contains("HP")){
                HP = true;
                System.out.println("HP: " + HP);
            } 
        }
    }

    public void filterList(){
        for (String s : stringList){
            String[] arr = s.split(" ");
            String HTellerVT = arr[0].substring(0, 2);
            System.out.println("HTellerVT: " + HTellerVT);
            
            setBetygBooleans();

            if(HTellerVT.equals("HT") || HTellerVT.equals("VT")){
                BetygRow betygRow = new BetygRow(this);
                betygRow.setArOchTermin(arr[0]);
                betygRowList.add(betygRow);

                switch (arr.length){
                    case 2:
                        if(BI){
                            betygRow.setB1(arr[1]);
                        }
                        else if(BII){
                            betygRow.setB2(arr[1]);
                        }
                        else if(HP){
                            betygRow.setHP(arr[1]);
                        }
                        break;
                    case 3:
                        System.out.println("case 3");
                        System.out.println("arr[1]: " + arr[1] + "arr[2]: " + arr[2]);
                        if(BI && BII){
                            System.out.println("BI && BII");
                            betygRow.setB1(arr[1]);
                            betygRow.setB2(arr[2]);
                        }
                        else if(BI && HP){
                            System.out.println("BI && HP");
                            betygRow.setB1(arr[1]);
                            betygRow.setHP(arr[2]);
                        }
                        else if(BII && HP){
                            System.out.println("BII && HP");
                            betygRow.setB2(arr[2]);
                            betygRow.setHP(arr[1]);
                        }
                        break;
                    case 4:
                        betygRow.setB1(arr[1]);
                        betygRow.setB2(arr[3]);
                        betygRow.setHP(arr[2]);
                        break;
                }
            } else{

            }
            

        }


    }

    public void filterOutDupes(){
        
        for(BetygRow b : betygRowList){
            String arOchTermin = b.getArOchTermin();
            if(!termSet.contains(arOchTermin)){
                finalList.add(b);
                termSet.add(arOchTermin);
            }
            
        }
    }

    public void addToStringList(String s){
        stringList.add(s);
    }

    @Override
    public String toString(){
        filterOutDupes();
        StringBuilder sb = new StringBuilder();
        sb.append("\"Betyg\": {\n");
        System.out.println(betygRowList.size() + " size of betygRowList");
        

        for(int i = 0; i < finalList.size(); i++){
            if(i == finalList.size() - 1){
                sb.append("\t" + "\t" + finalList.get(i).toString() + "\n");
            }
            else{
                sb.append("\t" + "\t" + finalList.get(i).toString() + ",\n");
            }
        }

        sb.append("\t" + "}\n");
        String rtnString = sb.toString();
        System.out.println("betyg tosting " + rtnString);
        return rtnString;
    }


}
