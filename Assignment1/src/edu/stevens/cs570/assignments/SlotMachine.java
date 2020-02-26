package edu.stevens.cs570.assignments;


import java.util.ArrayList;

public class SlotMachine {
    public enum Symbol {
        BELLS("Bells", 10), FLOWERS("Flowers", 5), FRUITS("Fruits", 3),
        HEARTS("Hearts", 2), SPADES("Spades", 1);

        // symbol name
        private final String name;

        // payout factor (i.e. multiple of wager) when matching symbols of this type
        private final int payoutFactor;


        Symbol(String name, int payoutFactor) {
            this.name = name;
            this.payoutFactor = payoutFactor;
        }

        public String getName() {
            return name;
        }

        public int getPayoutFactor() {
            return payoutFactor;
        }

    }

    int numReels;
    double [] odds;
    int wagerUnitValue;
    double total_in,total_out =0;
    //Symbol []symbol=new Symbol[5]; //Static Version
    ArrayList<Symbol> symbol=new ArrayList<>(numReels); //Dynamic Version


    //Constructor
    public SlotMachine(int numReels, double []odds,int wagerUnitValue) {
        this.numReels = numReels;
        this.odds=odds;
        this.wagerUnitValue=wagerUnitValue;
    }

    public Symbol getSymbolForAReel() {
        Symbol sy=Symbol.BELLS;
        double math=Math.random();
        if(math>=0 && math<odds[0])
        {
            sy=Symbol.BELLS;
        }
        if(math>=odds[0] && math<odds[0]+odds[1])
        {
            sy=Symbol.FLOWERS;
        }
        if(math>=odds[0]+odds[1] && math<odds[0]+odds[1]+odds[2])
        {
            sy=Symbol.FRUITS;
        }
        if(math>=odds[0]+odds[1]+odds[2] && math<odds[3]+odds[0]+odds[1]+odds[2])
        {
            sy=Symbol.HEARTS;
        }
        if(math>=odds[0]+odds[1]+odds[2]+odds[3] && math<1)
        {
            sy=Symbol.SPADES;
        }
        return sy;
    }//Symbol

    public long calcPayout(Symbol[] reelSymbols, int wagerValue) {
        int []count= new int[numReels];
        long earning=0;
        for(int i=0;i<numReels;i++)//find count from 1 to length/2
        {
            count[i]=0;
            for(int j=i;j<numReels;j++)
            {
                if(reelSymbols[i].getPayoutFactor() == reelSymbols[j].getPayoutFactor())
                {
                    count[i]++;
                }
            }
            if(count[0] == numReels)
            {
                earning= reelSymbols[0].getPayoutFactor()*wagerValue*2;
                return earning;
            }
        }
        for(int i=0;i<numReels;i++)
        {
            if(count[i]>numReels/2)
            {
                earning= reelSymbols[i].getPayoutFactor()*wagerValue;
            }
        }
        return earning;
    }//payout rules

    public void pullLever(int numWagerUnits) {
        symbol.clear();// Dynamic Version
        for(int i =0;i<numReels;i++)//print symbol
        {
            //symbol[i]= getSymbolForAReel();
            //System.out.println(symbol[i].getName()+' ');//Static Version
            symbol.add(getSymbolForAReel());
            System.out.println(symbol.get(i).getName()+' ');//Dynamic Version

        }
        Symbol []sy=new Symbol[symbol.size()];
        sy=symbol.toArray(sy);
        double payout=calcPayout(sy,numWagerUnits * wagerUnitValue);
        System.out.println(payout);
        total_in += numWagerUnits*wagerUnitValue;
        total_out += payout;
    }

    public double getPayoutPercent() {
        if(total_in == 0){
            return 0;
        }
        double percent = total_out/total_in * 100;
        return percent;
    }//percent

    public void reset() {
        total_in=0;
        total_out=0;
    }//reset
}

