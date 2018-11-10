package com.gcs.suban.bean;

public class IncomeBean {
    private String date;
    private int orderFlag;
    private double orderCommission;
    private int agentFlag;
    private double agentCommission;
    private int teamFlag;
    private double teamCommission;
    private int stockFlag;
    private double stockCommission;
    private int balanceFlag;
    private double balanceCommission;
    private int manualAgentFlag;
    private double manualAgentCommission;
    private int manualRecommendFlag;
    private double manualRecommendCommission;

    public void setDate(String date){   this.date = date;}
    public void setOrder(int flag){ orderFlag = flag;}
    public void setOrderCommission(double commission){ orderCommission = commission;}
    public void setAgent(int flag){ agentFlag = flag;}
    public void setAgentCommission(double commission){ agentCommission = commission;}
    public void setTeam(int flag){ teamFlag = flag;}
    public void setTeamCommission(double commission){ teamCommission = commission;}
    public void setStock(int flag){ stockFlag = flag;}
    public void setStockCommission(double commission){  stockCommission = commission;}
    public void setBalance(int flag){   balanceFlag = flag;}
    public void setBalanceCommission(double commission){    balanceCommission = commission;}
    public void setManualAgent(int flag){   manualAgentFlag = flag;}
    public void setManualAgentCommission(double commission){    manualAgentCommission = commission;}
    public void setManualRecommend(int flag){   manualRecommendFlag = flag;}
    public void setManualRecommendCommission(double commission){    manualRecommendCommission = commission;}
    public String getDate(){    return date;}
    public int hasOrder(){  return orderFlag;}
    public double getOrderCommission(){ return orderCommission;}
    public int hasAgent(){  return agentFlag;}
    public double getAgentCommission(){ return agentCommission;}
    public int hasTeam(){   return teamFlag;}
    public double getTeamCommission(){  return teamCommission;}
    public int hasStock(){  return stockFlag;}
    public double getStockCommission(){ return stockCommission;}
    public int hasBalance(){    return balanceFlag;}
    public double getBalanceCommission(){   return balanceCommission;}
    public int hasManualAgent(){    return manualAgentFlag;}
    public double getManualAgentCommission(){   return manualAgentCommission;}
    public int hasManualRecommend(){    return manualRecommendFlag;}
    public double getManualRecommendCommission(){   return manualRecommendCommission;}
}
