package gov.nih.nci.pa.util.ranking;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author:
 */
public class RankSorter {

    /**
     * Will sort based on the rank.
     * @param list list to sort
     * @param <T> return
     */
    public  <T extends Object>  void sort(List<RankedObject<T>> list) {

      //sorts based on descending order of rank i.e. lowest rank first. 
       Collections.sort(list, new Comparator<RankedObject<T>>() {
        @SuppressWarnings("rawtypes")
        public int compare(RankedObject o1, RankedObject o2) {
            return o2.getRank() - o1.getRank();  
        }
      });
      
    }
}
