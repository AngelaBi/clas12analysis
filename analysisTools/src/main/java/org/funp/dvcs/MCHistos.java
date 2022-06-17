package org.funp.dvcs;
//---- imports for HIPO4 library
import org.jlab.jnp.hipo4.io.*;


import org.jlab.jnp.hipo4.data.*;
//---- imports for GROOT library
import org.jlab.groot.data.*;
import org.jlab.groot.graphics.*;
import org.jlab.groot.fitter.*;
import org.jlab.groot.math.*;

import java.util.ArrayList;

//---- imports for PHYSICS library
import org.jlab.clas.physics.*;
//import org.jlab.jnp.reader.*;

import org.jlab.groot.ui.TCanvas;

//import org.funp.dvcs.DvcsEvent;;

public class MCHistos {

  public H1F hadrP;
  public H1F elecP;
  public H1F photP;//momentum gamma
  public H1F hadrTh;
  public H1F elecTh;
  public H1F photTh;//theta gamma

  public H2F hadrThvsPhi;//Theta vs phi for hadron
  public H2F elecThvsPhi;
  public H2F photThvsPhi;

  public H2F hadrThvsP;//Theta vs mom for hadron
  public H2F elecThvsP;
  public H2F photThvsP;

  public H1F targetmass;
  public H1F hadronmass;

 
  public H1F pionmass2;

  ArrayList<Object> kinehistos;
  ArrayList<Object> exclhistos;
  ArrayList<Object> asymhistos;
  ArrayList<Object> pidhistos;
  
  boolean readMode=false;
  private TDirectory rootDirfile;
  private String baseDir;//NC DC AC
  private String Config;//FT,FT or nothing
  private boolean pi0analysis=false;
  private String excl3part;
  private String excl2part;
  private String excl1part;
  private String gm;

  


  private String outputdir=new String(".");

  public void setAnalysisStrings(){
    if(pi0analysis){//swtich to pion
      excl3part="emh";
      excl2part="em";
      excl1part="m";
      gm="pion";
    }
    else {excl3part="egh";
    excl2part="eg";
    excl1part="g";
    gm="gamma";
    }
  }
public MCHistos(TDirectory rootdir, String basedir,String conf){
  readMode=true;
  rootDirfile=rootdir;
  baseDir=basedir;
  Config=conf;
  SetHisto();
  
}
  

  public MCHistos(boolean pi0mode,String otherdir) {
    this.outputdir=otherdir;
    this.pi0analysis=pi0mode;
    setAnalysisStrings();
    SetHisto();
  }
  public void SetHisto(){
    
  
    
    kinehistos= new ArrayList<Object>();
    
    
   
    hadrP=createHisto("hadrP","Deuteron Momentum","p [GeV/c]",100,0,3,"Kine");
    photP=createHisto("photP", gm+" Energy","E "+gm,100,0,12,"Kine");
    elecP=createHisto("elecP", "Electron energy","E elec",100,0,12,"Kine");
    hadrTh=createHisto("hadrTh", "Deuteron Theta", "th "+gm,100,30,140,"Kine");
    elecTh=createHisto("elecTh", "Electron Theta", "th "+gm,100,0,35,"Kine");
    photTh=createHisto("photTh", gm+" Theta", "th "+gm,100,0,35,"Kine");
     
    targetmass=createHisto("targetmass","target mass","",100,0,3,"Kine");
    hadronmass=createHisto("hadronmass","target mass","",100,0,3,"Kine");

    hadrThvsPhi=createHisto("hadrThvsPhi","Deuteron th vs phi", "", "", 100,-180,180,100,0,180, "Kine");
    photThvsPhi=createHisto("photThvsPhi","Photon th vs phi", "", "", 100,-180,180,100,0,35, "Kine");
    elecThvsPhi=createHisto("elecThvsPhi","Electron th vs phi", "" ,"", 100,-180,180,100,0,35, "Kine");
    hadrThvsP=createHisto("hadrThvsP", "Deuteron th bs p", "", "", 100,0,3.00,100,0,180, "Kine");
    elecThvsP=createHisto("elecThvsP", "Electron th vs p ", "", "",100,0,10.6,100,0,35, "Kine");
    photThvsP=createHisto("photThvsP", "Photon th vs   p", "", "", 100,0,10.6,100,0,35, "Kine");
    
    pionmass2=createHisto("pionmass2", "invariant mass gamma gamma", "", 100, -0.01, 0.05, "Kine");

   
  }
  public H1F createHisto(String name,
      String title,String titlex,
      int nbins,double xmin,double xmax,String type){
        H1F h;
        if(readMode){
          System.out.print("getting the histo "+name+" in "+baseDir+Config+"/"+type+"\n");
          rootDirfile.ls();
          h=(H1F)rootDirfile.getObject(baseDir+Config+"/"+type+"/",name); 
        }
        else{
        h=new H1F(name,title,nbins,xmin,xmax);
        h.setTitleX(titlex);
        if(type == "Kine"){
          kinehistos.add(h);
        }
        else if(type == "Excl"){
          exclhistos.add(h);
        }
        else if(type == "Pid"){
          pidhistos.add(h);
        }
        else if(type == "Asym"){
          asymhistos.add(h);
        }
      }
      return h;
      
  }
  public H2F createHisto(String name,
      String title,String titlex,String titley,
      int nbinsx,double xmin,double xmax,
      int nbinsy,double ymin,double ymax,
      String type){
        H2F h;
        if(readMode){
          System.out.print("getting the histo "+name+" in "+baseDir+Config+"/"+type+"/"+"\n");
          h=(H2F)rootDirfile.getObject(baseDir+Config+"/"+type+"/",name); 
          
        }
        else{

        h=new H2F(name,title,nbinsx,xmin,xmax,nbinsy,ymin,ymax);
        h.setTitleX(titlex);
        h.setTitleY(titley);
        if(type == "Kine"){
          kinehistos.add(h);
        }
        else if(type == "Excl"){
          exclhistos.add(h);
        }
        else if(type == "Pid"){
          pidhistos.add(h);
        }
        else if(type == "Asym"){
          asymhistos.add(h);
        }
      }
      return h;
      
  }

  

  public void fillBasicHisto(DvcsEvent ev) {
    

    
   
    


    //missing quantities of a complete DVCS final state e hadron gamma

     

    hadrThvsPhi.fill(Math.toDegrees(ev.vhadronMC.phi()),Math.toDegrees(ev.vhadronMC.theta()));
    elecThvsPhi.fill(Math.toDegrees(ev.velectronMC.phi()),Math.toDegrees(ev.velectronMC.theta()));
    photThvsPhi.fill(Math.toDegrees(ev.vphotonMC.phi()),Math.toDegrees(ev.vphotonMC.theta()));
    hadrThvsP.fill(ev.vhadronMC.p(),Math.toDegrees(ev.vhadronMC.theta()));
    elecThvsP.fill(ev.velectronMC.p(),Math.toDegrees(ev.velectronMC.theta()));
    photThvsP.fill(ev.vphotonMC.p(),Math.toDegrees(ev.vphotonMC.theta()));

    
    pionmass2.fill(ev.vpionMC.mass2());
    //System.out.println(ev.vpionMC.mass2());
    //Xbj=ev.Xb();
    if(gm=="pion"){
      photTh.fill(Math.toDegrees(ev.vpionMC.theta()));
      photP.fill(ev.vpionMC.p());
    }
    else if(gm=="gamma"){
      photTh.fill(Math.toDegrees(ev.vphotonMC.theta()));
      photP.fill(ev.vphotonMC.p());
    }
    
    hadrTh.fill(Math.toDegrees(ev.vhadronMC.theta()));
    elecTh.fill(Math.toDegrees(ev.velectronMC.theta()));
    
    hadrP.fill(ev.vhadronMC.p());
    elecP.fill(ev.velectronMC.p());
    
    targetmass.fill(ev.vTarget.mass());
    hadronmass.fill(ev.vhadronMC.mass());


  }

  public  void writeHipooutput(TDirectory rootdir,String directory){
    String hipodirectory = "/"+directory;

    String[] sub={hipodirectory+"/Kine",hipodirectory+"/Excl",hipodirectory+"/Pid",hipodirectory+"/Asym"};
    
  
    rootdir.mkdir(sub[0]);
    rootdir.cd(sub[0]);
    for (Object obj: kinehistos) {
      if (obj instanceof H1F){
        rootdir.addDataSet((H1F) obj);
      } else if (obj instanceof H2F) {
        rootdir.addDataSet((H2F) obj);      } 
    }
    rootdir.mkdir(sub[1]);
    rootdir.cd(sub[1]);
    for (Object obj: exclhistos) {
      if (obj instanceof H1F){
        rootdir.addDataSet((H1F) obj);
      } else if (obj instanceof H2F) {
        rootdir.addDataSet((H2F) obj);      } 
    }
    rootdir.mkdir(sub[2]);
    rootdir.cd(sub[2]);
    for (Object obj: pidhistos) {
      if (obj instanceof H1F){
        rootdir.addDataSet((H1F) obj);
      } else if (obj instanceof H2F) {
        rootdir.addDataSet((H2F) obj);      } 
    }
    rootdir.mkdir(sub[3]);
    rootdir.cd(sub[3]);
    for (Object obj: asymhistos) {
      if (obj instanceof H1F){
        rootdir.addDataSet((H1F) obj);
      } else if (obj instanceof H2F) {
        rootdir.addDataSet((H2F) obj);      } 
    }
    
   
  
  }
 

}

  
  
  
