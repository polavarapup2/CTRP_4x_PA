package gov.nih.nci.pa.util.ranking;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

/**
 * @author: 
 */
public class RankBasedSorterUtils {
    /**
     *  Log details
     */
    private static final Logger LOG = Logger.getLogger(RankBasedSorterUtils.class);
    /**
     * Will sort the input list based on the rank.
     * 
     * @param orginalList - The list to sort
     * @param searchString - The search string, on which the sorting to be applied.
     * @param seralizer  - The serializer that knows how to get a string version of the object being sorted
     * @param <T> a
     * @return  The rank based sorted list is returned. 
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T extends Object> List<T> sort(List<T> orginalList, String searchString , Serializer<T> seralizer) {
        try {
            if (CollectionUtils.isEmpty(orginalList)) {
                return orginalList;
            }
            
            List<RankedObject<T>> rankedList = new ArrayList(orginalList.size());
            Ranker ranker = new Ranker(searchString);
            RankSorter rankSorter = new RankSorter();

            for (T o : orginalList) {
                RankedObject<T> rankedObject =  ranker.rank(o , seralizer);
                rankedList.add(rankedObject);
            }
            rankSorter.sort(rankedList);
            return toList(rankedList);
        } catch (Exception e) {
            //log.warn("unable to compile the pattern", e);
            return orginalList;
        }
    }


    private static <T extends Object> List<T> toList(List<RankedObject<T>> tList) {
        ArrayList<T> list = new ArrayList<T>();
        for (RankedObject<T> r : tList) {
            list.add(r.getObject());
        }
        return list;
    }
    /**
     * assign the ranks and caDSR sort the list
     * @param orginalList orginalList
     * @param  searchString searchString
     * @param  seralizer seralizer
     * @param <T> gene
     * @return return retrun
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T extends Object> List<T> sortCaDSRResults(
        List<T> orginalList, String searchString , 
             Serializer<T> seralizer) {
        try {
            if (CollectionUtils.isEmpty(orginalList)) {
                return orginalList;
            }
            List<RankedObject<T>> rankedList = new ArrayList(orginalList.size());
            Ranker ranker = new Ranker(searchString);
            RankSorter rankSorter = new RankSorter();
            for (T o : orginalList) {
                RankedObject<T> rankedObject =  ranker.rankCaDSR(o , seralizer);
                rankedList.add(rankedObject);
            }
            rankSorter.sort(rankedList);
            return toList(rankedList);
        } catch (Exception e) {
            LOG.error("error while sorting caDSR results", e);
            return orginalList;
        }
    }
}
