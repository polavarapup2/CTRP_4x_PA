class RetryUtil {
    
       static retry(int times, long sleeptime, Closure c){
           Throwable catchedThrowable = null
           for(int i=0; i<times; i++){
               try {
                   return c.call()
               } catch(Throwable t){
                   catchedThrowable = t
                   println"failed to call closure. ${i+1} of $times runs"
                   Thread.sleep(sleeptime)
               }
           }
          
           throw catchedThrowable
       }
   }