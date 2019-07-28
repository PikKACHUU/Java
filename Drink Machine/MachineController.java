package ac.liv.csc.comp201;

import ac.liv.csc.comp201.model.IMachine;
import ac.liv.csc.comp201.model.IMachineController;
import ac.liv.csc.comp201.simulate.Cup;
import ac.liv.csc.comp201.simulate.Hoppers;
import java.awt.event.WindowEvent;
import java.util.Calendar;

public class MachineController  extends Thread implements IMachineController {
	
	private boolean running=true;
	
	private IMachine machine;
	private boolean coin=false ;
        private boolean tem;
	private static final String version="1.22";
        private final double small = Cup.SMALL;
	private final double medium = Cup.MEDIUM;
        private final double large = Cup.LARGE;
        private static final int SMALL_CUP=Cup.SMALL_CUP;
	private static final int MEDIUM_CUP=Cup.MEDIUM_CUP;
	private static final int LARGE_CUP=Cup.LARGE_CUP;
        private final double costS=0;
        private final double costM = 20;
        private final double costL =25;
        @Override
	public void startController(IMachine machine) {
		this.machine=machine;				// Machine that is being controlled
		
		super.start();
	}
	
	
	public MachineController() {
					
	}
	
	
	private synchronized void runStateMachine() {		
                if(machine.getHoppers().getHopperLevelsGrams(0)<2){
                if(machine.getHoppers().getHopperLevelsGrams(1)<3){
                if(machine.getHoppers().getHopperLevelsGrams(2)<5){
                if(machine.getHoppers().getHopperLevelsGrams(0)<28){
                    machine.getCoinHandler().lockCoinHandler();
                    machine.getDisplay().setTextString("Machine is out of service.");
                   
                }
                }
                }
                }else{
                machine.getCoinHandler().unlockCoinHandler();
                GetCoinValue();
                welcome("Three recipes are prepared for you!"); 
                GetCoinValue();               
                welcome("The Black Coffee/");
                GetCoinValue();                           
                welcome("The white Coffee/");
                GetCoinValue();              
                welcome("My favourite hot chocolate/ OoO");
                GetCoinValue();
               
                controlWaterTem(80);               
                String keyCode=GetKeycode();	
		switch (keyCode) {
                       
                        case "":
                            
                            break;
                        case "101" :
			      makeBlackCoffee(small,SMALL_CUP,costS);                               
                                break;
			case "5101" :
				makeBlackCoffee(medium,MEDIUM_CUP,costM);  								
				break;
			case "6101" :
				makeBlackCoffee(large,LARGE_CUP,costL);  
                                break;
                                //black coffee
			case "102" :
				makeBlackSugarCoffee(small,SMALL_CUP,costS);
				break;
			case "5102" :
				makeBlackSugarCoffee(medium,MEDIUM_CUP,costM);
                            break;
			case "6102" :
				makeBlackSugarCoffee(large,LARGE_CUP,costL);
                            break;
                                //black coffee with sugar
			case "201" :
			   makeWhiteCoffee(small,SMALL_CUP,costS);	
                            break;			
			case "5201" :
				makeWhiteCoffee(medium,MEDIUM_CUP,costM);
				break;                               
			case "6201" :
				makeWhiteCoffee(large,LARGE_CUP,costL);
				break;
                                //white coffee
                       case "202" :
                           makeWhiteSugarCoffee(small,SMALL_CUP,costS);
                           break;
                       case "5202" :
                           makeWhiteSugarCoffee(medium,MEDIUM_CUP,costM);
                           break;
                       case "6202" :
                           makeWhiteSugarCoffee(large,LARGE_CUP,costL);
                           break;
                           //white coffee with sugar
                       case "300" :
                           makeChocolate(small,SMALL_CUP,costS);
                           break;
                       case "5300" :
                           makeChocolate(medium,MEDIUM_CUP,costM);
                           break;
                       case "6300" :
                           makeChocolate(large,LARGE_CUP,costL);
                           break;
                           //hot chocolate
                       case "9":                            
                            CalcuChange();
                            machine.setBalance(0);                           
                            machine.getDisplay().setTextString("Thanks for your shopping!");                                                     
                            clearDisplay(2000);
                            coin = false;
                            controlWaterTem(80); 
                            break;
                        default:                                                                               
                            
                            machine.getDisplay().setTextString("Sorry, Please reselect drinks.");                          
                            clearDisplay(2000);
                            controlWaterTem(80);
                            break;
                }
                }
        }
        	
	public void run() {
		// Controlling thread for coffee machine
		int counter=1;
		while (running) {
			//machine.getDisplay().setTextString("Running drink machine controller "+counter);
			counter++;
			try {
				Thread.sleep(10);		// Set this delay time to lower rate if you want to increase the rate
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			runStateMachine();
		}		
	}
	
	public void updateController() {
		//runStateMachine();
	}	
	public void stopController() {
		running=false;
	}
private String GetKeycode(){
  
  String a = "";
  int keyCode=machine.getKeyPad().getNextKeyCode();
  if(machine.getBalance()>=110){
  
  
  if ( keyCode==5 || keyCode==6){
  long b = System.currentTimeMillis();
   a =a+keyCode;
   while(a.length()<4&& System.currentTimeMillis()<b+10000){
  controlWaterTem(80);
   keyCode=machine.getKeyPad().getNextKeyCode();
   if(keyCode!=-1)
   a =a+keyCode;
   }
  // System.out.println(a);
  return a.trim(); 
  }
  else if (keyCode==1||keyCode==2||keyCode==3){
   long b=System.currentTimeMillis();
   a =a+keyCode;
   while(a.length()<3 && System.currentTimeMillis()<b+10000){
    controlWaterTem(80);
    keyCode=machine.getKeyPad().getNextKeyCode();
   if(keyCode!=-1)
   a =a+keyCode;
   }
   
   // System.out.println(a);       
  return a.trim();
  }
    else if(keyCode==9){
       a =a+keyCode;
     //  System.out.println(a);
    return a;}
  }else
  {
      if(coin)
      machine.getDisplay().setTextString("credit is not enough to purchase");
      clearDisplay(1000);
      controlWaterTem(80); 
  }
  if(keyCode!=-1 && machine.getBalance()==0){
      machine.getDisplay().setTextString("Please insert coins.");
      clearDisplay(1000);
     controlWaterTem(80);
  }
  
   // System.out.println(a);
    return a.trim();
}
private void GetCoinValue(){
    int balance = machine.getBalance();
    String coinCode=machine.getCoinHandler().getCoinKeyCode();
    if(coinCode!=null){
        switch (coinCode){
        case "ab":
            balance+=1;
            System.out.println("Got coin ..1p");
            machine.getDisplay().setTextString("Got coin ..1p "+"| The balance is "+balance);
           clearDisplay(1000);
           controlWaterTem(80); 
            break;
        case "ac":
            balance+=5;
            System.out.println("Got coin ..5p");
            machine.getDisplay().setTextString("Got coin ..5p "+"| The balance is "+balance);
            clearDisplay(1000);
            controlWaterTem(80); 
            break;
        case "ba":
            balance+=10;
            System.out.println("Got coin ..10p");
            machine.getDisplay().setTextString("Got coin ..10p "+"| The balance is "+balance);
           clearDisplay(1000);
        controlWaterTem(80);
            break;
        case "bc":
            balance+=20;
            System.out.println("Got coin ..20p");
            machine.getDisplay().setTextString("Got coin ..20p "+"| The balance is "+balance);
            clearDisplay(1000);
            controlWaterTem(80); 
            break;
        case "bd":
            balance+=50;
            System.out.println("Got coin ..50p");
            machine.getDisplay().setTextString("Got coin ..50p "+"| The balance is "+balance);
            clearDisplay(1000);
           controlWaterTem(80); 
            break;
        case "ef":
            balance+=100;
            System.out.println("Got coin ..100p");
            machine.getDisplay().setTextString("Got coin ..100p "+"| The balance is "+balance);
            clearDisplay(1000);
          controlWaterTem(80);
            break;
        default:
            break;
    }
    }
    machine.setBalance(balance);
    if(balance>0.1)
        coin=true;
    if(coin){
    machine.getDisplay().setTextString("The balance is "+balance);
    clearDisplay(400);
    controlWaterTem(80);}
    System.out.println(balance);
    }
private void controlWaterTem(float tem){
float currentTem=machine.getWaterHeater().getTemperatureDegreesC();
if(currentTem<=tem){
machine.getWaterHeater().setHeaterOn();
}

if(currentTem>tem){
    machine.getWaterHeater().setHeaterOff();
   
}
if(currentTem==100){
machine.shutMachineDown();
machine.getDisplay().setTextString("ERROR");
    }
float currentTem2 = machine.getWaterHeater().getTemperatureDegreesC();
if(currentTem2<machine.getWaterHeater().getTemperatureDegreesC() && machine.getWaterHeater().getHeaterOnStatus()==false){
machine.shutMachineDown();
machine.getDisplay().setTextString("ERROR");
clearDisplay(8000);
System.out.println("ERROR");
}
}
private void controlCupTem(Cup cup,double capacity, double tem){

while(cup.getTemperatureInC()!=tem && cup.getWaterLevelLitres()<capacity){
  
    controlWaterTem(90); 
if(cup.getTemperatureInC()>tem){
    machine.getWaterHeater().setHotWaterTap(false);
    machine.getWaterHeater().setColdWaterTap(true); 
    if(cup.getTemperatureInC()<tem){
    machine.getWaterHeater().setColdWaterTap(false);}

}
if(cup.getTemperatureInC()<tem){
    machine.getWaterHeater().setColdWaterTap(false);
    machine.getWaterHeater().setHotWaterTap(true); 
}
System.out.println(cup.getTemperatureInC()+" "+cup.getWaterLevelLitres()+" "+cup.getCoffeeGrams());
}
machine.getWaterHeater().setHeaterOff();
if( cup.getWaterLevelLitres()==capacity){
machine.getWaterHeater().setHotWaterTap(false);
machine.getWaterHeater().setColdWaterTap(false);
}

}
private void clearDisplay(int b){
long a = System.currentTimeMillis();
while(true){
       controlWaterTem(80);                  
    if(System.currentTimeMillis()>a+b){
                                machine.getDisplay().setTextString("  ");
                                break;
                            }
}
}
private void welcome(String a){
 if(coin==false){
 machine.getDisplay().setTextString(a);
 clearDisplay(800);
 controlWaterTem(80);
 }
}
private void makeBlackCoffee(double size,int cupType ,double addPrice){
double scale = size/small;
if(machine.getHoppers().getHopperLevelsGrams(0)>=2*scale && machine.getBalance()>=120+addPrice){                                  
                                machine.vendCup(cupType);                             
                                while(true){
                                   controlWaterTem((float) 96.2);
                                   System.out.println(1);
                                if(machine.getWaterHeater().getTemperatureDegreesC()>=96.2){
                                machine.getWaterHeater().setHeaterOff();                            
                                break;}                               
                                }                               
                                    Cup cup=machine.getCup();                                                                   
                                    if(cup!=null && cup.getWaterLevelLitres()==0){
                                    while(cup.getWaterLevelLitres()<size*0.2 || cup.getCoffeeGrams()<2*scale){
                                    controlWaterTem((float) 96.2);
                                     if(cup.getCoffeeGrams()<2*scale){
                                    machine.getHoppers().setHopperOn(Hoppers.COFFEE);
                                     System.out.println(cup.getCoffeeGrams());
                                     }
                                    controlWaterTem((float) 96.2);
                                     if(cup.getWaterLevelLitres()<size*0.2){
                                    machine.getWaterHeater().setHotWaterTap(true);
                                     System.out.println(cup.getWaterLevelLitres());
                                     }     
                                     controlWaterTem((float) 96.2);
                                     if(cup.getTemperatureInC()<95.9 && cup.getWaterLevelLitres()<size*0.4){
                                         if(machine.getWaterHeater().getTemperatureDegreesC()>=96)
                                         machine.getWaterHeater().setHotWaterTap(true);
                                     }
                                     if(cup.getCoffeeGrams()>=2*scale){
                                        machine.getHoppers().setHopperOff(Hoppers.COFFEE);
                                    System.out.println(cup.getCoffeeGrams());
                                    }
                                    controlWaterTem((float) 96.2);
                                    if(cup.getWaterLevelLitres()>=size*0.2){
                                        machine.getWaterHeater().setHotWaterTap(false);
                                    System.out.println(cup.getWaterLevelLitres());}
                                     controlWaterTem((float) 96.2);
                                    }
                                     machine.getHoppers().setHopperOff(Hoppers.COFFEE);
                                     machine.getWaterHeater().setHotWaterTap(false);
                                     controlCupTem(cup,size,82.5);
                                     machine.setBalance((int) (machine.getBalance()-120-addPrice));
                                     machine.getDisplay().setTextString("The balance is "+machine.getBalance()+"|Enjoy the coffee ^_^.");
                                     clearDisplay(2000);
                                     controlWaterTem(80); 
                                     System.out.println(cup.getCoffeeGrams()+" "+cup.getWaterLevelLitres()+" "+cup.getTemperatureInC());
                                
                                    
                                    }
                                }else if(machine.getHoppers().getHopperLevelsGrams(0)<2*scale){
                                machine.getDisplay().setTextString("The ingredients is not enough.");
                                clearDisplay(2000);
                                controlWaterTem(80); 
                                }else if(machine.getBalance()<120+addPrice){
                                machine.getDisplay().setTextString("The credit is not enough.");
                                clearDisplay(2000);
                                controlWaterTem(80); 
                                }
}
private void makeBlackSugarCoffee(double size,int cupType ,double addPrice){
double scale = size/small;
if(machine.getHoppers().getHopperLevelsGrams(0)>=2*scale && machine.getHoppers().getHopperLevelsGrams(2)>=5*scale && machine.getBalance()>=130+addPrice){                                  
                                machine.vendCup(cupType);                             
                                while(true){
                                   controlWaterTem((float) 96.2);
                                   System.out.println(1);
                                if(machine.getWaterHeater().getTemperatureDegreesC()>=96.2){
                                machine.getWaterHeater().setHeaterOff();                            
                                break;}                               
                                }                               
                                    Cup cup=machine.getCup();                                                                   
                                    if(cup!=null && cup.getWaterLevelLitres()==0){
                                    while(cup.getWaterLevelLitres()<size*0.2 || cup.getCoffeeGrams()<2*scale || cup.getSugarGrams()<5*scale){
                                      controlWaterTem((float) 96.2);
                                     if(cup.getCoffeeGrams()<2*scale){
                                    machine.getHoppers().setHopperOn(Hoppers.COFFEE);
                                     System.out.println(cup.getCoffeeGrams());
                                     }
                                     controlWaterTem((float) 96.2);
                                     if(cup.getWaterLevelLitres()<size*0.2){
                                    machine.getWaterHeater().setHotWaterTap(true);
                                     System.out.println(cup.getWaterLevelLitres());
                                     } 
                                       controlWaterTem((float) 96.2);
                                     if(cup.getSugarGrams()<5*scale){
                                    machine.getHoppers().setHopperOn(Hoppers.SUGAR);
                                    System.out.println(cup.getSugarGrams());
                                     }
                                      controlWaterTem((float) 96.2);
                                    if(cup.getTemperatureInC()<95.9 && cup.getWaterLevelLitres()<size*0.4){
                                         if(machine.getWaterHeater().getTemperatureDegreesC()>=96)
                                         machine.getWaterHeater().setHotWaterTap(true);
                                     }
                                    if(cup.getCoffeeGrams()>=2*scale){
                                        machine.getHoppers().setHopperOff(Hoppers.COFFEE);
                                    System.out.println(cup.getCoffeeGrams());
                                    }
                                
                                    if(cup.getWaterLevelLitres()>=size*0.2){
                                        machine.getWaterHeater().setHotWaterTap(false);
                                    System.out.println(cup.getWaterLevelLitres());
                                    }
                                     controlWaterTem((float) 96.2);
                                    if(cup.getSugarGrams()>=5*scale){
                                    machine.getHoppers().setHopperOff(Hoppers.SUGAR);
                                    } 
                                  
                                    }
                                     machine.getHoppers().setHopperOff(Hoppers.SUGAR);
                                     machine.getHoppers().setHopperOff(Hoppers.COFFEE);
                                     machine.getWaterHeater().setHotWaterTap(false);
                                     controlCupTem(cup,size,82.5);
                                     machine.setBalance((int) (machine.getBalance()-130-addPrice));
                                     machine.getDisplay().setTextString("The balance is "+machine.getBalance()+"|Enjoy the coffee ^_^.");
                                     clearDisplay(2000);
                                     controlWaterTem(80); 
                                     System.out.println(cup.getCoffeeGrams()+" "+cup.getWaterLevelLitres()+" "+cup.getTemperatureInC()+" "+cup.getSugarGrams() );                                                              
                                    }
                                }else if(machine.getHoppers().getHopperLevelsGrams(0)<2*scale || machine.getHoppers().getHopperLevelsGrams(2)<5*scale){
                                machine.getDisplay().setTextString("The ingredient is not enough.");
                                clearDisplay(2000);
                                controlWaterTem(80); 
                                }
                                else if(machine.getBalance()<130+addPrice){
                                machine.getDisplay().setTextString("The credit is not enough.");
                                clearDisplay(2000);
                                controlWaterTem(80); 
                                }
}   
private void makeWhiteCoffee(double size,int cupType ,double addPrice){
double scale = size/small;
if(machine.getHoppers().getHopperLevelsGrams(0)>=2*scale &&machine.getHoppers().getHopperLevelsGrams(1)>=3*scale && machine.getBalance()>=120+addPrice){                                  
                                machine.vendCup(cupType);                             
                                while(true){
                                  controlWaterTem((float) 96.2);
                                   System.out.println(1);
                                if(machine.getWaterHeater().getTemperatureDegreesC()>=96.2){
                                machine.getWaterHeater().setHeaterOff();                            
                                break;}                               
                                }                               
                                    Cup cup=machine.getCup();                                                                   
                                    if(cup!=null && cup.getWaterLevelLitres()==0){
                                    while(cup.getWaterLevelLitres()<size*0.2 || cup.getCoffeeGrams()<2*scale || cup.getMilkGrams()<3*scale){
                                   controlWaterTem((float) 96.2);
                                     if(cup.getCoffeeGrams()<2*scale){
                                    machine.getHoppers().setHopperOn(Hoppers.COFFEE);
                                     System.out.println(cup.getCoffeeGrams());
                                     }
                                    controlWaterTem((float) 96.2);
                                     if(cup.getWaterLevelLitres()<size*0.2){
                                    machine.getWaterHeater().setHotWaterTap(true);
                                     System.out.println(cup.getWaterLevelLitres());
                                     } 
                                    controlWaterTem((float) 96.2);
                                     if(cup.getMilkGrams()<3*scale){
                                    machine.getHoppers().setHopperOn(Hoppers.MILK);
                                    System.out.println(cup.getMilkGrams());
                                     }
                                     controlWaterTem((float) 96.2);
                                     if(cup.getTemperatureInC()<95.9 && cup.getWaterLevelLitres()<size*0.6){
                                         if(machine.getWaterHeater().getTemperatureDegreesC()>=96)
                                         machine.getWaterHeater().setHotWaterTap(true);
                                     }
                                    if(cup.getCoffeeGrams()>=2*scale){
                                        machine.getHoppers().setHopperOff(Hoppers.COFFEE);
                                    System.out.println(cup.getCoffeeGrams());
                                    }
                                   controlWaterTem((float) 96.2);
                                    if(cup.getWaterLevelLitres()>=size*0.2){
                                        machine.getWaterHeater().setHotWaterTap(false);
                  
                                    System.out.println(cup.getWaterLevelLitres());
                                    }
                                   
                                    if(cup.getMilkGrams()>=3*scale){
                                    machine.getHoppers().setHopperOff(Hoppers.MILK);
                                    System.out.println(cup.getMilkGrams());
                                     }
                                   controlWaterTem((float) 96.2);
                                    }
                                     machine.getHoppers().setHopperOff(Hoppers.MILK);
                                     machine.getHoppers().setHopperOff(Hoppers.COFFEE);
                                     machine.getWaterHeater().setHotWaterTap(false);
                                     controlCupTem(cup,size,82.5);
                                     machine.setBalance((int) (machine.getBalance()-120-addPrice));
                                     machine.getDisplay().setTextString("The balance is "+machine.getBalance()+"|Enjoy the coffee ^_^.");
                                     clearDisplay(2000);
                                     controlWaterTem(80); 
                                     System.out.println(cup.getCoffeeGrams()+" "+cup.getWaterLevelLitres()+" "+cup.getTemperatureInC()+" "+cup.getMilkGrams());
                                
                                    
                                    }
                                }else if(machine.getHoppers().getHopperLevelsGrams(0)<2*scale || machine.getHoppers().getHopperLevelsGrams(1)<3*scale){
                                machine.getDisplay().setTextString("The ingredients is not enough.");
                                clearDisplay(2000);
                                controlWaterTem(80); 
                                }else if(machine.getBalance()<120+addPrice){
                                machine.getDisplay().setTextString("The credit is not enough.");
                                clearDisplay(2000);
                                controlWaterTem(80); 
                                }
  }  
private void makeWhiteSugarCoffee(double size,int cupType ,double addPrice){
double scale = size/small;
if(machine.getHoppers().getHopperLevelsGrams(0)>=2*scale &&machine.getHoppers().getHopperLevelsGrams(1)>=3*scale &&machine.getHoppers().getHopperLevelsGrams(2)>=5*scale && machine.getBalance()>=130+addPrice){                                  
                                machine.vendCup(cupType);                             
                                while(true){
                                   controlWaterTem((float) 96);
                                   System.out.println(1);
                                if(machine.getWaterHeater().getTemperatureDegreesC()>=96){
                                machine.getWaterHeater().setHeaterOff();                            
                                break;}                               
                                }                               
                                    Cup cup=machine.getCup();                                                                   
                                    if(cup!=null && cup.getWaterLevelLitres()==0){
                                    while(cup.getWaterLevelLitres()<size*0.2 || cup.getCoffeeGrams()<2*scale || cup.getMilkGrams()<3*scale ||cup.getSugarGrams()<5*scale){
                                    controlWaterTem((float) 96);
                                     if(cup.getCoffeeGrams()<2*scale){
                                    machine.getHoppers().setHopperOn(Hoppers.COFFEE);
                                     System.out.println(cup.getCoffeeGrams());
                                     }
                                     controlWaterTem((float) 96);
                                     if(cup.getWaterLevelLitres()<size*0.2){
                                    machine.getWaterHeater().setHotWaterTap(true);
                                     System.out.println(cup.getWaterLevelLitres());
                                     } 
                                     controlWaterTem((float) 96);
                                     if(cup.getMilkGrams()<3*scale){
                                    machine.getHoppers().setHopperOn(Hoppers.MILK);
                                    System.out.println(cup.getMilkGrams());
                                     }
                                    controlWaterTem((float) 96);
                                     if(cup.getTemperatureInC()<95.9 && cup.getWaterLevelLitres()<size*0.65){
                                         if(machine.getWaterHeater().getTemperatureDegreesC()>=96)
                                         machine.getWaterHeater().setHotWaterTap(true);
                                     }
                                     if(cup.getSugarGrams()<5*scale){
                                         machine.getHoppers().setHopperOn(Hoppers.SUGAR);
                                         System.out.println(cup.getSugarGrams());
                                     }
                                     controlWaterTem((float) 96);
                                    if(cup.getCoffeeGrams()>=2*scale){
                                        machine.getHoppers().setHopperOff(Hoppers.COFFEE);
                                    System.out.println(cup.getCoffeeGrams());
                                    }
                                    
                                    if(cup.getWaterLevelLitres()>=size*0.2){
                                        machine.getWaterHeater().setHotWaterTap(false);
                  
                                    System.out.println(cup.getWaterLevelLitres());
                                    }
                                    controlWaterTem((float) 96);
                                    if(cup.getMilkGrams()>=3*scale){
                                    machine.getHoppers().setHopperOff(Hoppers.MILK);
                                    System.out.println(cup.getMilkGrams());
                                     }
                                   
                                    if(cup.getSugarGrams()>=5*scale){
                                    machine.getHoppers().setHopperOff(Hoppers.SUGAR);
                                    System.out.println(cup.getSugarGrams());
                                    }
                                    controlWaterTem((float) 96);
                                    }
                                     machine.getHoppers().setHopperOff(Hoppers.SUGAR);
                                     machine.getHoppers().setHopperOff(Hoppers.MILK);
                                     machine.getHoppers().setHopperOff(Hoppers.COFFEE);
                                     machine.getWaterHeater().setHotWaterTap(false);
                                     controlCupTem(cup,size,82);
                                     machine.setBalance((int) (machine.getBalance()-130-addPrice));
                                     machine.getDisplay().setTextString("The balance is "+machine.getBalance()+"|Enjoy the coffee ^_^.");
                                     clearDisplay(2000);
                                     controlWaterTem(80); 
                                     System.out.println(cup.getCoffeeGrams()+" "+cup.getWaterLevelLitres()+" "+cup.getTemperatureInC()+" "+cup.getMilkGrams()+" "+cup.getSugarGrams());
                                
                                    
                                    }
                                }else if(machine.getHoppers().getHopperLevelsGrams(0)<2*scale || machine.getHoppers().getHopperLevelsGrams(1)<3*scale || machine.getHoppers().getHopperLevelsGrams(2)<5*scale){
                                machine.getDisplay().setTextString("The ingredients is not enough.");
                                clearDisplay(2000);
                                controlWaterTem(80); 
                                }else if(machine.getBalance()<130+addPrice){
                                machine.getDisplay().setTextString("The credit is not enough.");
                                clearDisplay(2000);
                                controlWaterTem(80); 
                                }
  } 
private void makeChocolate(double size,int cupType ,double addPrice){
double scale = size/small;
if(machine.getHoppers().getHopperLevelsGrams(3)>=28*scale && machine.getBalance()>=110+addPrice){                                  
                                machine.vendCup(cupType);                             
                                while(true){
                                   controlWaterTem((float) 92.7);
                                   System.out.println(1);
                                if(machine.getWaterHeater().getTemperatureDegreesC()>=92.7){
                                machine.getWaterHeater().setHeaterOff();                            
                                break;}                               
                                }                               
                                    Cup cup=machine.getCup();                                                                   
                                    if(cup!=null && cup.getWaterLevelLitres()==0){
                                    while(cup.getWaterLevelLitres()<size*0.4 || cup.getChocolateGrams()<28*scale){
                                     controlWaterTem((float) 92.7);
                                     if(cup.getChocolateGrams()<28*scale){
                                    machine.getHoppers().setHopperOn(Hoppers.CHOCOLATE);
                                     System.out.println(cup.getChocolateGrams());
                                     }
                                     controlWaterTem((float) 92.7);
                                     if(cup.getWaterLevelLitres()<size*0.4){
                                    machine.getWaterHeater().setHotWaterTap(true);
                                     System.out.println(cup.getWaterLevelLitres());
                                     }  
                                     controlWaterTem((float) 92.7);
                                    if(cup.getChocolateGrams()>=28*scale){                                 
                                    System.out.println(cup.getChocolateGrams());
                                    }                                  
                                    if(cup.getWaterLevelLitres()>=size*0.4){
                                       machine.getWaterHeater().setHotWaterTap(false);
                                    System.out.println(cup.getWaterLevelLitres());}
                                   
                                    }
                                     machine.getHoppers().setHopperOff(Hoppers.CHOCOLATE);
                                     machine.getWaterHeater().setHotWaterTap(false);
                                     controlCupTem(cup,size,83);
                                     machine.setBalance((int) (machine.getBalance()-110-addPrice));
                                     machine.getDisplay().setTextString("The balance is "+machine.getBalance()+"|Enjoy the Chocolate ^_^.");
                                     clearDisplay(2000);
                                     controlWaterTem(80); 
                                     System.out.println(cup.getChocolateGrams()+" "+cup.getWaterLevelLitres()+" "+cup.getTemperatureInC());
                                
                                    
                                    }
                                }else if(machine.getHoppers().getHopperLevelsGrams(3)<28*scale){
                                machine.getDisplay().setTextString("The ingredients is not enough.");
                                clearDisplay(2000);
                                controlWaterTem(80); 
                                }else if(machine.getBalance()<110+addPrice){
                                machine.getDisplay().setTextString("The credit is not enough.");
                                clearDisplay(2000);
                                controlWaterTem(80); 
                                }
}
private void CalcuChange(){
    //"ab","ac","ba","bc","bd","ef",
int balance = machine.getBalance();
while(balance>=100){
if(machine.getCoinHandler().coinAvailable("ef")){
machine.getCoinHandler().dispenseCoin("ef");
balance-=100;
}else if(machine.getCoinHandler().coinAvailable("bd")){
machine.getCoinHandler().dispenseCoin("bd");
balance-=50;
}else if(machine.getCoinHandler().coinAvailable("bc")){
machine.getCoinHandler().dispenseCoin("bc");
balance-=20;
}else if(machine.getCoinHandler().coinAvailable("ba")){
machine.getCoinHandler().dispenseCoin("ba");
balance-=10;
}else if(machine.getCoinHandler().coinAvailable("ac")){
machine.getCoinHandler().dispenseCoin("ac");
balance-=5;
}else if(machine.getCoinHandler().coinAvailable("ab")){
machine.getCoinHandler().dispenseCoin("ab");
    balance-=1;
}else{
machine.getDisplay().setTextString("Error,Wait for help.");
}
}
while(balance<100 && balance>0){
if(machine.getCoinHandler().coinAvailable("bd") && balance>=50){
machine.getCoinHandler().dispenseCoin("bd");
balance-=50;
}else if(machine.getCoinHandler().coinAvailable("bc") && balance>=20){
machine.getCoinHandler().dispenseCoin("bc");
balance-=20;
}else if(machine.getCoinHandler().coinAvailable("ba") && balance>=10){
machine.getCoinHandler().dispenseCoin("ba");
balance-=10;
}else if(machine.getCoinHandler().coinAvailable("ac") && balance>=5){
machine.getCoinHandler().dispenseCoin("ac");
    balance-=5;
}else if(machine.getCoinHandler().coinAvailable("ab") && balance>=1){
machine.getCoinHandler().dispenseCoin("ab");
    balance-=1;
}else{
machine.getDisplay().setTextString("Error,Wait for help.");
}
}
waitforT(20000);
machine.getCoinHandler().clearCoinTry();

}
private void waitforT(long a){
long b = System.currentTimeMillis();
while(true){
       controlWaterTem(80);                  
    if(System.currentTimeMillis()>a+b){                               
                                break;
                            }
}
}

}

