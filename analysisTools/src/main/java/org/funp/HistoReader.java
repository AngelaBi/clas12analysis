package org.funp;

//package org.funp.dvcs;
import org.jlab.groot.ui.TCanvas;
//---- imports for HIPO4 library
import org.jlab.jnp.hipo4.io.*;
import org.jlab.jnp.hipo4.data.*;
//---- imports for GROOT library
import org.jlab.groot.data.*;
import org.jlab.groot.graphics.*;
//---- imports for PHYSICS library
import org.jlab.clas.physics.*;
//import org.jlab.jnp.reader.*;

import org.funp.dvcs.*;
import org.funp.utilities.*;

import java.util.*;
import java.io.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jlab.groot.data.TDirectory;

//---- imports for HIPO4 library
import org.jlab.jnp.hipo4.io.*;
import org.jlab.jnp.hipo4.data.*;
//---- imports for GROOT library
import org.jlab.groot.data.*;
import org.jlab.groot.graphics.*;
import org.jlab.groot.fitter.*;
import org.jlab.groot.math.*;
//---- imports for PHYSICS library
import org.jlab.clas.physics.*;
//import org.jlab.jnp.reader.*;

import org.jlab.groot.ui.TCanvas;
import org.jlab.groot.data.TDirectory;

public class HistoReader {
  static TDirectory hipobasedir;
  static TCanvas ec4;
  static TCanvas ecA;
  static DvcsHisto hDCFT;
  static DvcsHisto hDCFD;
  static DvcsHisto hCCFT;
  static DvcsHisto hCCFD;
  static DvcsHisto hACFT;

  static DvcsHisto hDC;
  static DvcsHisto hCC;
  static DvcsHisto hAC;
  
  static DvcsHisto hACFD;
  static processInput inputParam;
  private static double corr=1;
  public static void main(String[] args) throws FileNotFoundException, IOException {
    inputParam = new processInput(args);
    
    onefilePlots(inputParam.detectorType,inputParam.cutType);
    
    //mergeThreeRunperiods();
    //mergePlot(inputParam.detectorType,inputParam.cutType);

  }
  public static void onefilePlots(String detector,String cut){
    
    hipobasedir = new TDirectory();
    hipobasedir.readFile(inputParam.gethipoFile());
    hDC = new DvcsHisto(hipobasedir, "DC", detector);
    hCC = new DvcsHisto(hipobasedir, "CC", detector);
    hAC = new DvcsHisto(hipobasedir, "AC", detector);
    
    TCanvas cprelim = new TCanvas("ExcCutPrelim"+detector,1000,500);
    displayPrelim(cprelim, hDC,hCC,detector);
    TCanvas cexclDC=new TCanvas("ExcCutsDC"+detector,500,500);
    displayExcCuts(cexclDC, hDC,detector,"DC");
    TCanvas cexclDC2=new TCanvas("ExcCutsDC2"+detector,500,500);
    displayExcCuts2(cexclDC2, hDC,detector,"DC");

    TCanvas cexclCC=new TCanvas("ExcCutsCC"+detector,500,500);
    displayExcCuts(cexclCC, hCC,detector ,"CC");
    TCanvas cexclCC2=new TCanvas("ExcCutsCC2"+detector,500,500);
    displayExcCuts2(cexclCC2, hCC,detector,"CC");

    TCanvas cexclAC=new TCanvas("ExcCutsAC"+detector,500,500);
    displayExcCuts(cexclAC, hAC,detector,"AC");
    TCanvas cexclAC2=new TCanvas("ExcCutsAC2"+detector,500,500);
    displayExcCuts2(cexclAC2, hAC,detector,"AC");
    

    TCanvas cprotDC=new TCanvas("ExclPlotwithProtDC"+detector,800,500);
    displayExclPlotProt(cprotDC, hDC,detector,"DC");
    TCanvas cprotCC=new TCanvas("ExclPlotwithProtCC"+detector,800,500);
    displayExclPlotProt(cprotCC, hCC,detector,"CC");
    TCanvas cprotAC=new TCanvas("ExclPlotwithProtAC"+detector,800,500);
    displayExclPlotProt(cprotAC, hAC,detector,"AC");


    // TCanvas ect1 = new TCanvas("Asymt"+detector, 1200, 500);
    // drawAsymtbins(ect1, hAC);
    // TCanvas ecQ1 = new TCanvas("Asymq2"+detector, 1200, 500);
    // drawAsymQ2bins(ecQ1, hAC);
    // TCanvas ecxb1 = new TCanvas("Asymxb"+detector, 1200, 500);
    // drawAsymxbbins(ecxb1, hAC);
    // TCanvas ect1pi0 = new TCanvas("AsymtFTpi0", 1200, 500);
    // drawAsymtbinspi0(ect1pi0, hAC);

    
    
    TCanvas oc = new TCanvas("other cuts"+detector, 1200, 500);
    displayOthercuts(oc,hAC);
  
    TCanvas bc = new TCanvas("binning"+detector,1200,500);
    ShowBinning(bc,hAC);
 
    TCanvas ecasym = new TCanvas("Asym"+detector, 500, 500);
    drawAsym(ecasym, hAC);
  
  // TCanvas ec4 = new TCanvas("ExclDCFD", 1400, 1200);
  // displayExcCuts(ec4, hDCFD,"FD");
  // ec4.getCanvas().save(inputParam.getOutputDir()+"/"+ec4.getTitle()+".pdf");
  // TCanvas ec5 = new TCanvas("ExclCCFD", 1400, 1200);
  // displayExcCuts(ec5, hCCFD,"FD");
  // ec5.getCanvas().save(inputParam.getOutputDir()+"/"+ec5.getTitle()+".pdf");
  // TCanvas ec6 = new TCanvas("ExclACFD", 1400, 1200);
  // displayExcCuts(ec6, hACFD,"FD");
  // ec6.getCanvas().save(inputParam.getOutputDir()+"/"+ec6.getTitle()+".pdf");
  //TCanvas ecA = new TCanvas("Asym FT", 1400, 1200);
  //drawAsym(ecA, hACFT);
  //TCanvas ecA2 = new TCanvas("Asym FD", 1400, 1200);
  //drawAsym(ecA2, hACFD);
  
  // TCanvas ect2 = new TCanvas("AsymtFD", 1200, 500);
  //HistoReader.corr=0.69;
  // drawAsymtbins(ect2, hACFD);
  // ect2.getCanvas().save(inputParam.getOutputDir()+"/"+ect2.getTitle()+".pdf");
  
  // TCanvas ecQ2 = new TCanvas("Asymq2FD", 1200, 500);
  // drawAsymQ2bins(ecQ2, hACFD);
  // ecQ2.getCanvas().save(inputParam.getOutputDir()+"/"+ecQ2.getTitle()+".pdf");
  

  // TCanvas ecxb2 = new TCanvas("AsymxbFD", 1200, 500);
  // drawAsymxbbins(ecxb2, hACFD);
  // ecxb2.getCanvas().save(inputParam.getOutputDir()+"/"+ecxb2.getTitle()+".pdf");


 
 
  // TCanvas ect2pi0 = new TCanvas("AsymtFDpi0", 1200, 500);
  // drawAsymtbinspi0(ect2pi0, hACFD);
  // ect2pi0.getCanvas().save(inputParam.getOutputDir()+"/"+ect2pi0.getTitle()+".pdf");
  // //HistoReader.corr=0.69;


 

  // TCanvas bc2 = new TCanvas("binning FD",1200,500);
  // ShowBinning(bc2,hACFD);
  }
  
  public static void displayOthercuts(TCanvas c, DvcsHisto h){
    c.divide(2, 2);
    c.cd(0).draw(h.VertexElectron);
    drawCut(-6., h.VertexElectron, c, 7);
    drawCut(0., h.VertexElectron, c, 7);

    c.cd(1).draw(h.vertexElecVSvertexDeut);

    c.cd(2).draw(h.pionmass2);
    h.pionmass2.setOptStat(10);

  }
  public static void displayPrelim(TCanvas ec, DvcsHisto hDC,DvcsHisto hCC,String detector){
    ec.divide(2, 1);
    ec.cd(0).draw(hDC.coneanglevsedXM2);
    ec.cd(1).draw(hCC.coneanglevsedXM2);
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    ec.getCanvas().save(inputParam.getOutputDir()+"/"+ec.getTitle()+".pdf");
  }
  public static void displayExcCuts(TCanvas ec, DvcsHisto h,String detector,String cut) {

    ec.divide(2, 2);
    // ec.cd(0).draw(h.coneanglevsedXM2);

    ec.cd(0).draw(h.edXmissingM2);
    if(cut=="CC")drawCut(-1., h.edXmissingM2, ec, 0);

    ec.cd(1).draw(h.edgXmissingE);
    h.edgXmissingE.setOptStat(10);
    
    if(cut=="CC"){
    if(detector =="FT")
    drawCut(1, h.edgXmissingE, ec, 1);
    else
    drawCut(2, h.edgXmissingE, ec, 1);
    }
    ec.cd(2).draw(h.pPerphisto);
    if(cut=="CC"){
    drawCut(0.5, h.pPerphisto, ec, 2);
    }
    ec.cd(3).draw(h.edgXmissingP);
    if(cut=="CC"){
    if(detector =="FT")
    drawCut(0.5, h.edgXmissingP, ec, 3);
    else
    drawCut(0.8, h.edgXmissingP, ec, 3);
    }
    // ec.cd(5).draw(h.edgXmissingM2);
    // //drawCut(0.7, h.edXmissingM, ec, 5);

    // ec.cd(6).draw(h.edgXmissingPz);

    // ec.cd(7).draw(h.DeltaPhiPlaneHist);

    // ec.cd(8).draw(h.egXmissingM2);

    try {
      Thread.sleep(500);
    } catch (InterruptedException e6) {
      // TODO Auto-generated catch block
      e6.printStackTrace();
    }
    ec.getCanvas().save(inputParam.getOutputDir()+"/"+ec.getTitle()+".pdf");

    

  }

  public static void displayExcCuts2(TCanvas ec, DvcsHisto h,String detector,String cut) {

    ec.divide(2, 2);
 

    // ec.cd(0).draw(h.coneanglevsedXM2);

    // ec.cd(0).draw(h.edXmissingM2);
    // drawCut(-1., h.edXmissingM2, ec, 0);

    // ec.cd(1).draw(h.edgXmissingE);
    // if(detector =="FT")
    // drawCut(1, h.edgXmissingE, ec, 1);
    // else
    // drawCut(2, h.edgXmissingE, ec, 1);

    // ec.cd(2).draw(h.pPerphisto);
    // drawCut(0.5, h.pPerphisto, ec, 2);

    // ec.cd(3).draw(h.edgXmissingP);
    // if(detector =="FT")
    // drawCut(0.5, h.edgXmissingP, ec, 3);
    // else
    // drawCut(0.8, h.edgXmissingP, ec, 3);

    ec.cd(0).draw(h.edgXmissingM2);
    //drawCut(0.7, h.edXmissingM, ec, 5);

    ec.cd(1).draw(h.edgXmissingPz);

    ec.cd(2).draw(h.DeltaPhiPlaneHist);

    ec.cd(3).draw(h.egXmissingM2);

    try {
      Thread.sleep(500);
    } catch (InterruptedException e6) {
      // TODO Auto-generated catch block
      e6.printStackTrace();
    }
    ec.getCanvas().save(inputParam.getOutputDir()+"/"+ec.getTitle()+".pdf");

    

  }
  public static void displayExclPlotProt(TCanvas ec, DvcsHisto h,String detector,String cut){
    ec.divide(3, 3);
    ec.cd(0).draw(h.edgXmissingE_mis);
    ec.cd(1).draw(h.edgXmissingM2_mis);
    ec.cd(2).draw(h.edgXmissingP_mis);
    ec.cd(3).draw(h.pPerphisto_mis);
    ec.cd(4).draw(h.edXmissingM2_mis);
    ec.cd(5).draw(h.egXmissingM2_mis);
    ec.cd(6).draw(h.coneanglevsedXM2_mis);



    

    try {
      Thread.sleep(500);
    } catch (InterruptedException e6) {
      // TODO Auto-generated catch block
      e6.printStackTrace();
    }
    ec.getCanvas().save(inputParam.getOutputDir()+"/"+ec.getTitle()+".pdf");

  }

 

  public static void drawCut(double cut, H1F histo, TCanvas canvas, int pad) {
    DataLine line = new DataLine(cut, 0, cut, histo.getMax());
    line.setLineColor(2);
    line.setLineWidth(2);
    canvas.cd(pad).draw(line);
  }

  public static H1F buildAsym(H1F hp,H1F hm) {
    H1F num;
    H1F denom;
    H1F Asym;
    num = new H1F("num", 10, 0, 360);
    denom = new H1F("denom", 10, 0, 360);
    Asym = new H1F("Asymmetry", "Asymmetry", 10, 0, 360);
    num.add(hp);
    num.sub(hm);
    denom.add(hp);
    denom.add(hm);
    
    // num.add(h.phiplustbin[0]);
    // num.sub(h.phiminustbin[0]);
    // denom.add(h.phiplustbin[0]);
    // denom.add(h.phiminustbin[0]);

    Asym = H1F.divide(num, denom);
    Asym.divide(0.85);
    Asym.divide(HistoReader.corr);
    Asym.setTitleX("#phi [deg.]");
    Asym.setTitleY("A_LU(#phi)");

    // H1F Asym = (H1F) dir2.getObject(dir + "/", directory);
    return Asym;
  }

  public static void drawAsym(TCanvas ec, DvcsHisto h) {
    ec.getPad().setAxisRange(0, 360, -0.8, 0.8);
    ec.draw((buildAsym(h.Phiplus,h.Phiminus)), "E");

    F1D Asymfunc = new F1D("Asymfunc", "[A]*sin(x * 2 * 3.14 /360)  ", 0, 360);
    // Asymfunc.setParameter(0,0.1);
    // Asymfunc.setParameter(1,0.01);
    // Asymfunc.setParameter(2,-0.1);
    Asymfunc.setParameter(0, 0.1);
    // Asymfunc.setParameter(1,0.01);
    // Asymfunc.setParameter(2,-0.01);
    DataFitter.fit(Asymfunc, buildAsym(h.Phiplus,h.Phiminus), "");
    ec.draw(Asymfunc, "same");
  }
  public static void drawAsymtbins(TCanvas ec, DvcsHisto h) {
    
    
    ec.divide(3,1);
    for(int i=0;i<3;i++){
      H1F hp=h.phiplustbin[i];
      H1F hm=h.phiminustbin[i];
      ec.cd(i);
      ec.getPad().setAxisRange(0, 360, -0.8, 0.8);
    ec.draw(buildAsym(hp,hm), "E");

    F1D Asymfunc = new F1D("Asymfunc", "[A]*sin(x * 2 * 3.14 /360)  ", 0, 360);
    // Asymfunc.setParameter(0,0.1);
    // Asymfunc.setParameter(1,0.01);
    // Asymfunc.setParameter(2,-0.1);
    Asymfunc.setParameter(0, 0.1);
    // Asymfunc.setParameter(1,0.01);
    // Asymfunc.setParameter(2,-0.01);
    DataFitter.fit(Asymfunc, buildAsym(hp,hm), "");
    ec.draw(Asymfunc, "same");
    try {
      Thread.sleep(500);
    } catch (InterruptedException e3) {
      // TODO Auto-generated catch block
      e3.printStackTrace();
    }
    ec.getCanvas().save(inputParam.getOutputDir()+"/"+ec.getTitle()+".pdf");
    }
  }
  public static void drawAsymQ2bins(TCanvas ec, DvcsHisto h) {
    ec.divide(3,1);
    for(int i=0;i<3;i++){
      ec.cd(i);
      ec.getPad().setAxisRange(0, 360, -0.8, 0.8);
    ec.draw((buildAsym(h.phiplusQ2bin[i],h.phiminusQ2bin[i])), "E");

    F1D Asymfunc = new F1D("Asymfunc", "[A]*sin(x * 2 * 3.14 /360)  ", 0, 360);
    // Asymfunc.setParameter(0,0.1);
    // Asymfunc.setParameter(1,0.01);
    // Asymfunc.setParameter(2,-0.1);
    Asymfunc.setParameter(0, 0.1);
    // Asymfunc.setParameter(1,0.01);
    // Asymfunc.setParameter(2,-0.01);
    DataFitter.fit(Asymfunc, buildAsym(h.phiplusQ2bin[i],h.phiminusQ2bin[i]), "");
    ec.draw(Asymfunc, "same");
    try {
      Thread.sleep(500);
    } catch (InterruptedException e3) {
      // TODO Auto-generated catch block
      e3.printStackTrace();
    }
    ec.getCanvas().save(inputParam.getOutputDir()+"/"+ec.getTitle()+".pdf");
    }
  }
  public static void drawAsymxbbins(TCanvas ec, DvcsHisto h) {
    ec.divide(3,1);
    for(int i=0;i<3;i++){
      ec.cd(i);
      ec.getPad().setAxisRange(0, 360, -0.8, 0.8);
    ec.draw((buildAsym(h.phiplusxbbin[i],h.phiminusxbbin[i])), "E");

    F1D Asymfunc = new F1D("Asymfunc", "[A]*sin(x * 2 * 3.14 /360)  ", 0, 360);
    // Asymfunc.setParameter(0,0.1);
    // Asymfunc.setParameter(1,0.01);
    // Asymfunc.setParameter(2,-0.1);
    Asymfunc.setParameter(0, 0.1);
    // Asymfunc.setParameter(1,0.01);
    // Asymfunc.setParameter(2,-0.01);
    DataFitter.fit(Asymfunc, buildAsym(h.phiplusQ2bin[i],h.phiminusQ2bin[i]), "");
    ec.draw(Asymfunc, "same");
    try {
      Thread.sleep(500);
    } catch (InterruptedException e3) {
      // TODO Auto-generated catch block
      e3.printStackTrace();
    }
    ec.getCanvas().save(inputParam.getOutputDir()+"/"+ec.getTitle()+".pdf");
    }
  }
  public static void drawAsymtbinspi0(TCanvas ec, DvcsHisto h) {
    
    
    ec.divide(3,1);
    for(int i=0;i<3;i++){
      H1F hp=h.phipluspi0tbin[i];
      H1F hm=h.phiminuspi0tbin[i];
      ec.cd(i);
      ec.getPad().setAxisRange(0, 360, -0.8, 0.8);
    ec.draw(buildAsym(hp,hm), "E");

    F1D Asymfunc = new F1D("Asymfunc", "[A]*sin(x * 2 * 3.14 /360)  ", 0, 360);
    // Asymfunc.setParameter(0,0.1);
    // Asymfunc.setParameter(1,0.01);
    // Asymfunc.setParameter(2,-0.1);
    Asymfunc.setParameter(0, 0.1);
    // Asymfunc.setParameter(1,0.01);
    // Asymfunc.setParameter(2,-0.01);
    DataFitter.fit(Asymfunc, buildAsym(hp,hm), "");
    ec.draw(Asymfunc, "same");
    try {
      Thread.sleep(500);
    } catch (InterruptedException e3) {
      // TODO Auto-generated catch block
      e3.printStackTrace();
    }
    ec.getCanvas().save(inputParam.getOutputDir()+"/"+ec.getTitle()+".pdf");
    }
  }
  //BAD programming to quickly merge run periods
  public static void mergeThreeRunperiods(){
    TDirectory hipobasedir1 = new TDirectory();
    TDirectory hipobasedir2 = new TDirectory();
    TDirectory hipobasedir3 = new TDirectory();
    hipobasedir1.readFile("S19.hipo");
    hipobasedir2.readFile("F19.hipo");
    hipobasedir3.readFile("S20.hipo");
    DvcsHisto hACFT1= new DvcsHisto(hipobasedir1, "AC", "FT");
    DvcsHisto hACFD1 = new DvcsHisto(hipobasedir1, "AC", "FD");
    DvcsHisto hACFT2= new DvcsHisto(hipobasedir2, "AC", "FT");
    DvcsHisto hACFD2 = new DvcsHisto(hipobasedir2, "AC", "FD");
    DvcsHisto hACFT3= new DvcsHisto(hipobasedir3, "AC", "FT");
    DvcsHisto hACFD3 = new DvcsHisto(hipobasedir3, "AC", "FD");
    TCanvas ect1 = new TCanvas("AsymFTbinnedt", 1200, 500);
    drawAsymtbins3runperiods(ect1, hACFT1,hACFT2,hACFT3);
    ect1.getCanvas().save(inputParam.getOutputDir()+"/"+ect1.getTitle()+".pdf");
    TCanvas ect2 = new TCanvas("AsymFDbinnedt", 1200, 500);
   
    drawAsymtbins3runperiods(ect2, hACFD1, hACFD2, hACFD3);
    ect2.getCanvas().save(inputParam.getOutputDir()+"/"+ect2.getTitle()+".pdf");

 //HistoReader.corr=0.69;


    TCanvas ect3 = new TCanvas("AsymFTbinnedQ2", 1200, 500);
    drawAsymQ2bins3runperiods(ect3, hACFT1,hACFT2,hACFT3);
    ect3.getCanvas().save(inputParam.getOutputDir()+"/"+ect3.getTitle()+".pdf");

    TCanvas ect4 = new TCanvas("AsymFDbinnedQ2", 1200, 500);
    drawAsymQ2bins3runperiods(ect4, hACFD1, hACFD2, hACFD3);
    ect4.getCanvas().save(inputParam.getOutputDir()+"/"+ect4.getTitle()+".pdf");

    TCanvas ect5 = new TCanvas("AsymFTbinnedxb", 1200, 500);
    drawAsymxbbins3runperiods(ect5, hACFT1,hACFT2,hACFT3);
    ect5.getCanvas().save(inputParam.getOutputDir()+"/"+ect5.getTitle()+".pdf");
    TCanvas ect6 = new TCanvas("AsymFDbinnedxb", 1200, 500);
    
    drawAsymxbbins3runperiods(ect6, hACFD1, hACFD2, hACFD3);
    ect6.getCanvas().save(inputParam.getOutputDir()+"/"+ect6.getTitle()+".pdf");

    TCanvas ect7 = new TCanvas("Asym", 500, 500);
    drawAsym3runperiods(ect7, hACFT1,hACFT2,hACFT3);
    ect7.getCanvas().save(inputParam.getOutputDir()+"/"+ect7.getTitle()+".pdf");
  }
  public static void drawAsymtbins3runperiods(TCanvas ec, DvcsHisto h1, DvcsHisto h2, DvcsHisto h3){
    ec.divide(3,1);
    H1F[] totPhip=new H1F[3];;
    H1F[] totPhim=new H1F[3];;
    for(int i=0;i<3;i++){
    totPhip[i]= new H1F("totPhip"+i, 10, 0, 360);
    totPhim[i]= new H1F("totPhim"+i, 10, 0, 360);
    }
    for(int i=0;i<3;i++){
      ec.cd(i);
      ec.getPad().setAxisRange(0, 360, -0.8, 0.8);
      totPhip[i].add(h1.phiplustbin[i]);
      totPhip[i].add(h2.phiplustbin[i]);
      totPhip[i].add(h3.phiplustbin[i]);
      totPhim[i].add(h1.phiminustbin[i]);
      totPhim[i].add(h2.phiminustbin[i]);
      totPhim[i].add(h3.phiminustbin[i]);

      System.out.println(totPhip[i].integral()+" "+totPhim[i].integral());
      ec.draw((buildAsym(totPhip[i],totPhim[i])), "E");
      F1D Asymfunc = new F1D("Asymfunc", "[A]*sin(x * 2 * 3.14 /360)  ", 0, 360);
      Asymfunc.setParameter(0, 0.1);
      DataFitter.fit(Asymfunc, buildAsym(totPhip[i],totPhim[i]), "");
      ec.draw(Asymfunc, "same");
    }

  }

  public static void drawAsymQ2bins3runperiods(TCanvas ec, DvcsHisto h1, DvcsHisto h2, DvcsHisto h3){
    ec.divide(3,1);
    H1F[] totPhip=new H1F[3];;
    H1F[] totPhim=new H1F[3];;
    for(int i=0;i<3;i++){
    totPhip[i]= new H1F("totPhip"+i, 10, 0, 360);
    totPhim[i]= new H1F("totPhim"+i, 10, 0, 360);
    }
    for(int i=0;i<3;i++){
      ec.cd(i);
      ec.getPad().setAxisRange(0, 360, -0.8, 0.8);
      totPhip[i].add(h1.phiplusQ2bin[i]);
      totPhip[i].add(h2.phiplusQ2bin[i]);
      totPhip[i].add(h3.phiplusQ2bin[i]);
      totPhim[i].add(h1.phiminusQ2bin[i]);
      totPhim[i].add(h2.phiminusQ2bin[i]);
      totPhim[i].add(h3.phiminusQ2bin[i]);

      System.out.println(totPhip[i].integral()+" "+totPhim[i].integral());
      ec.draw((buildAsym(totPhip[i],totPhim[i])), "E");
      F1D Asymfunc = new F1D("Asymfunc", "[A]*sin(x * 2 * 3.14 /360)  ", 0, 360);
      Asymfunc.setParameter(0, 0.1);
      DataFitter.fit(Asymfunc, buildAsym(totPhip[i],totPhim[i]), "");
      ec.draw(Asymfunc, "same");
    }

  }
  public static void drawAsymxbbins3runperiods(TCanvas ec, DvcsHisto h1, DvcsHisto h2, DvcsHisto h3){
    ec.divide(3,1);
    H1F[] totPhip=new H1F[3];;
    H1F[] totPhim=new H1F[3];;
    for(int i=0;i<3;i++){
    totPhip[i]= new H1F("totPhip"+i, 10, 0, 360);
    totPhim[i]= new H1F("totPhim"+i, 10, 0, 360);
    }
    for(int i=0;i<3;i++){
      ec.cd(i);
      ec.getPad().setAxisRange(0, 360, -0.8, 0.8);
      totPhip[i].add(h1.phiplusxbbin[i]);
      totPhip[i].add(h2.phiplusxbbin[i]);
      totPhip[i].add(h3.phiplusxbbin[i]);
      totPhim[i].add(h1.phiminusxbbin[i]);
      totPhim[i].add(h2.phiminusxbbin[i]);
      totPhim[i].add(h3.phiminusxbbin[i]);

      System.out.println(totPhip[i].integral()+" "+totPhim[i].integral());
      ec.draw((buildAsym(totPhip[i],totPhim[i])), "E");
      F1D Asymfunc = new F1D("Asymfunc", "[A]*sin(x * 2 * 3.14 /360)  ", 0, 360);
      Asymfunc.setParameter(0, 0.1);
      DataFitter.fit(Asymfunc, buildAsym(totPhip[i],totPhim[i]), "");
      ec.draw(Asymfunc, "same");
    }

  }

  public static void drawAsym3runperiods(TCanvas ec, DvcsHisto h1, DvcsHisto h2, DvcsHisto h3){
    
    H1F totPhip=new H1F("totPhip", 10, 0, 360);
    H1F totPhim=new H1F("totPhim", 10, 0, 360);;
    
   
      
      ec.getPad().setAxisRange(0, 360, -0.8, 0.8);
      totPhip.add(h1.Phiplus);
      totPhip.add(h2.Phiplus);
      totPhip.add(h3.Phiplus);
      totPhim.add(h1.Phiminus);
      totPhim.add(h2.Phiminus);
      totPhim.add(h3.Phiminus);
      

      System.out.println(totPhip.integral()+" "+totPhim.integral());
      ec.draw((buildAsym(totPhip,totPhim)), "E");
      F1D Asymfunc = new F1D("Asymfunc", "[A]*sin(x * 2 * 3.14 /360)  ", 0, 360);
      Asymfunc.setParameter(0, 0.1);
      DataFitter.fit(Asymfunc, buildAsym(totPhip,totPhim), "");
      ec.draw(Asymfunc, "same");
    

  }


  public static void ShowBinning(TCanvas c, DvcsHisto h){
    c.divide(3, 1);
    c.cd(0).draw(h.tHhisto);
    h.tHhisto.setOptStat(111111);
    //drawCut(h.tbins[1], h.thisto, c, 0);
    //drawCut(h.tbins[2], h.thisto, c, 0);
    //drawCut(h.tbins[3], h.thisto, c, 0);
    c.cd(1).draw(h.Q2);
    h.Q2.setOptStat(111111);
    //drawCut(h.q2bins[1], h.Q2, c,1);
    //drawCut(h.q2bins[2], h.Q2, c, 1);
    //drawCut(h.q2bins[3], h.Q2, c, 1);

    
    c.cd(2).draw(h.Xbj);
    h.Xbj.setOptStat(111111);
    //drawCut(h.xbbins[1], h.Xbj, c,2);
    //drawCut(h.xbbins[2], h.Xbj, c, 2);
    //drawCut(h.xbbins[3], h.Xbj, c, 2);

  }




 //BAD programming to quickly merge run periods
 public static void mergePlot(String detector,String cut){

  TDirectory[] hipobasedir=new TDirectory[3];
  String[] namefile=new String[] {"S19.hipo","F19.hipo","S20.hipo"};
  DvcsHisto h[]=new DvcsHisto[3];
  TCanvas cc = new TCanvas("Plotkine", 1200, 400);
  cc.divide(3, 1);
  H1F ttot=new H1F();
  H1F qtot=new H1F();
  H1F xtot=new H1F();
 
  for(int i=0;i<3;i++){
    hipobasedir[i]=new TDirectory();
    hipobasedir[i].readFile(namefile[i]);
    
    h[i]= new DvcsHisto(hipobasedir[i], cut, detector);
    if(i==0) {
      //ttot.set(h[0].thisto.getXaxis().getNBins(),h[0].thisto.getXaxis().getLimits()[0],h[0].thisto.getXaxis().getLimits()[1]);
      ttot.set(100,0,2);

      ttot.setName("-t");
    }
    ttot.add(h[i].thisto);
    ttot.setOptStat(101);
    ttot.setTitle("");
    //cc.cd(i);
    //cc.draw(h[i].Xbj);


  
  
    if(i==0) {
      //qtot.set(h[0].Q2.getXaxis().getNBins(),h[0].Q2.getXaxis().getLimits()[0],h[0].Q2.getXaxis().getLimits()[1]);
      qtot.set(100,0.1,4);
      qtot.setName("Q^2");
      qtot.setTitle("");
    }
    qtot.add(h[i].Q2);
    qtot.setOptStat(101);


    if(i==0) {
      //xtot.set(h[0].Xbj.getXaxis().getNBins(),h[0].Xbj.getXaxis().getLimits()[0],h[0].Xbj.getXaxis().getLimits()[1]);
      xtot.set(100,0,1);
      xtot.setName("X_b");
      xtot.setTitle("");
    }
    xtot.add(h[i].Xbj);
    xtot.setOptStat(101);
  }
  
  cc.cd(0);
  cc.draw(ttot);
  cc.cd(1);
  cc.draw(qtot);
  cc.cd(2);
  cc.draw(xtot);
  
  
 
  
}

}