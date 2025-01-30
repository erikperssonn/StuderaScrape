package com.example;

public class BetygRow {
    private Betyg betyg;
    private String arOchTermin;
    private String B1 = "";
    private String B2 = "";
    private String HP = "";
    private Double b1Value;
    private Double b2Value;
    private Double hpValue;

    public BetygRow(Betyg betyg) {
        this.betyg = betyg;
    }

    public void setArOchTermin(String arOchTermin) {
        this.arOchTermin = arOchTermin;
    }

    public void setB1(String b1) {
        B1 = b1;
    }

    public void setB2(String b2) {
        B2 = b2;
    }

    public void setHP(String HP) {
        this.HP = HP;
    }

    public String getArOchTermin() {
        return arOchTermin;
    }

    public void setValuesBeforeJSON(){
        try{ 
            b1Value = Double.parseDouble(B1);
        } catch (NumberFormatException e){
            b1Value = 0.0;
            if(B1.equals("")){
                B1 = "X";
            }
        }
        try{
            b2Value = Double.parseDouble(B2);
        } catch (NumberFormatException e){
            b2Value = 0.0;
            if(B2.equals("")){
                B2 = "X";
            }
        }
        try{
            hpValue = Double.parseDouble(HP);
        } catch (NumberFormatException e){
            hpValue = 0.0;
            if(HP.equals("")){
                HP = "X";
            }
        }

    }

    @Override
    public String toString(){
        System.out.println("betgyrow tostring");
        setValuesBeforeJSON();
        return "\"" + arOchTermin + "\": {\"B1\": \"" + B1 + "\", \"B2\": \"" + B2 + "\", \"HP\": \"" + HP + "\", \"B1Value\": \"" + b1Value +  "\", \"B2Value\": \"" + b2Value + "\", \"HPValue\": \"" + hpValue + "\"}";
    }

    @Override 
    public int hashCode(){
        String term = arOchTermin.substring(0,2);
        String year = arOchTermin.substring(2,6);
        String termStr = "";
        switch(term){
            case "HT":
            termStr = "1";
                break;
            case "VT":
            termStr = "2";
                break;
            default:
            termStr = "0";
                break;
        }

        String hashString = year + termStr;
        return Integer.parseInt(hashString);
    } 
}
