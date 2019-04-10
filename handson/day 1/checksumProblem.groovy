input = "ABCDEF1234567890"

def checksum(input){
    first = []
    sec=[]
    count = 0;
    input.toList().each{
   
       if(count%2==1){
            first << it
        }
        if(count%2==0){
            sec << it
        }
        
    
        count ++
    }
    
    thir = [sec,first].transpose()
    hexStr = []
     thir.each{
         hexStr << Integer.parseInt((""+it[0]+it[1]),16)
     }
     
    // println hexStr.sum()%255
    
}

checksum(input)
def checksumWithStep(input){
    slice = {start,end,step ->
            output = []
            start.step(end,step){
                output << it
            }
            output
    }
    first = slice(0,input.size(),2)
    sec = slice(1,input.size(),2)
    thir = [first,sec].transpose()

    hexStr = [];
     thir.each{


         hexStr << Integer.parseInt((input[it[0]]+input[it[1]]),16)
     }
     println hexStr.sum()%255   
}

checksumWithStep("ABCDEF1234567890")