package gov.nih.nci.pa.util.ranking;

/**
 * 
 * @author 
 *
 * @param <T>
 */
public class RankedObject<T> {
    private int rank = 0;
    private T obj;

    /**
     * 
     * @param obj input param
     */
    public RankedObject(T obj) {
        this.obj = obj;
    }

    /**
     * 
     * @return return
     */
    public T getObject() {
        return obj;
    }

    /**
     * 
     * @param objParam input param
     */
    public void setObject(T objParam) {
        this.obj = objParam;
    }

    /**
     * 
     * @return int
     */
    public int getRank() {
        return rank;
    }

    /**
     * 
     * @param rank int
     */
    public void setRank(int rank) {
        this.rank = rank;
    }

    /**
     * 
     * @param x input
     */
    public void addToRank(int x) {
        if (this.rank <= 0) {
            this.rank += x;
        }
    }

    /**
     * 
     * @param x input
     */
    public void substractFromRank(int x) {
        this.rank -= x;
    }

    @Override
    public String toString() {
        return "[" + rank + " : " + String.valueOf(obj) + "]";
    }
}
