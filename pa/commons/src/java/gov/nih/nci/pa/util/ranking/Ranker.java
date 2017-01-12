package gov.nih.nci.pa.util.ranking;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author
 *
 */
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.TooManyMethods" })
public class Ranker {

     private static final int WHOLE_SENTENCE_MATCH = 100000;
     private static final int BEGINING_OF_SENTENCE = 50000;
     private static final int WHOLE_WORD_MATCH = 10000;
     private static final int WHOLE_WORD_MATCH_PRIMARY = 7000;
     private static final int BEGINING_OF_WORD = 1000;
     private static final int PART_OF_SENTENCE = 500;
     private static final int PART_OF_SENTENCE_PRIMARY = 700;
     private static final int WHOLE_WORD_MATCH_SYNONYM = 4000;
    private final Pattern p;
    private final String searchStr;
    //private final String escapedSearchStr;
    private final int patternLength;
    
    /**
     * constructor. 
     * @param searchStr  input
     */
    public Ranker(String searchStr) {
        String escapedSearchStr = "";
        this.searchStr = searchStr;
        patternLength = searchStr.length();
        escapedSearchStr = searchStr;
        //11 characters with special meanings: the opening square bracket [, the backslash \, the caret ^, 
        //the dollar sign $, the period or dot ., the vertical bar
        //or pipe symbol |, the question mark ?, the asterisk or star *, the plus sign +, the opening round bracket 
        //( and the closing round bracket ).These special characters are often called "metacharacters".
        char[] metachars = new char[]{'\\' , '(', ')' , '[', ']' , '^' , '$' , '.' , '?' , '+' , '*' , '|'};
        if (StringUtils.containsAny(searchStr, metachars)) {
           String[] metaStr = {"\\", "(", ")", "[", "]", "^", "$", ".", "?", "+", "*", "|"};
       String[] metaEscapedStr = {"\\\\", "\\(", "\\)", "\\[", "\\]", "\\^", "\\$", "\\.", "\\?", "\\+", "\\*", "\\|"};
           escapedSearchStr = StringUtils.replaceEach(searchStr, metaStr, metaEscapedStr);
        }
        p = Pattern.compile(escapedSearchStr, Pattern.CASE_INSENSITIVE);
    }
    
    /**
     * Will rank an object, based on the following rules :-
     *  1) If there is a full text match - Highest rank "WHOLE_SENTENCE_MATCH" is given.
     *  1) Starting of the sentence if match - 2nd Highest rank "BEGINING_OF_SENTENCE" is given.
     *  2) Begining of any word match, - 3rd Highest rank "BEGINING_OF_WORD" is given.
     *  3) Anywhere in the sentence match - The lowest rank PART_OF_SENTENCE is given.
     * 
     * @param 
     * @param <T> 
     */
    

    /**
     * @param serializer serializer
     * @param obj obj
     * @param <T> gene
     * @return return retrun
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public <T extends Object> RankedObject rank(T obj, Serializer<T> serializer) {
        RankedObject<T> rankedObject = new RankedObject(obj);
        String str = serializer.serialize(obj);
        int l = str.length();

        //whole sentence
        if (StringUtils.equalsIgnoreCase(str, searchStr)) {
            rankedObject.addToRank(WHOLE_SENTENCE_MATCH);
        }
        Matcher m = p.matcher(str);
        if (m.find()) {

            int start = m.start();
            if (start == 0) {
                //begining of sentence
                rankedObject.addToRank(BEGINING_OF_SENTENCE);
            } else {
                int i = start - 1;
                int j = start - 2;
                char iChar = str.charAt(i);

                //begining of sentence.
                if ((j == 0 && str.charAt(j) == '(')
                        || (i == 0 && iChar == '(')) {
                    rankedObject.addToRank(BEGINING_OF_SENTENCE);
                }
                    

                if (iChar == ' ' || iChar == '(') {

                    int k = start + patternLength;
                    if (k == l ||  (k < l && (str.charAt(k) == ' ' || str.charAt(k) == ')'))) {
                       //whole word match
                        rankedObject.addToRank(WHOLE_WORD_MATCH);
                    }

                    //begining of word
                    rankedObject.addToRank(BEGINING_OF_WORD);
                }
            }
            //part of sentence
            rankedObject.addToRank(PART_OF_SENTENCE);
            rankedObject.substractFromRank(start);
        }
        return rankedObject;
    }
    
    /**
     * @param serializer serializer
     * @param obj obj
     * @param <T> gene
     * @return return retrun
     */
    @SuppressWarnings({ "rawtypes", "unchecked", "PMD.ExcessiveMethodLength" })
    public <T extends Object> RankedObject rankCaDSR(T obj,
              Serializer<T> serializer) {
        RankedObject<T> rankedObject = new RankedObject(obj);
        String str = serializer.serialize(obj);
        String[] bracketTrimmedString = 
             str.split("(\\)(\\(.*?\\)|[^\\(])*?)|(\\((\\(.*?\\)|[^\\(])*?)(\\)|$|\\(\\w*.+|\\)*.+)");
        for (String innerString : bracketTrimmedString) {
            String[] synonymTrimmedString = str.split(innerString.trim());
             // test for exact primary value
            if (StringUtils.equalsIgnoreCase(innerString.trim(), searchStr)) {
                rankedObject.addToRank(WHOLE_SENTENCE_MATCH);
            } else if (synonymTrimmedString.length > 1) {
             // test for exact Synonym value
                String synonymString = synonymTrimmedString[1].replaceAll("\\(", "");
                synonymString = synonymString.replaceAll("\\)", "");
                String[] synonymsTrimmedString = synonymString.split("\\;");
                for (String innersyno : synonymsTrimmedString) {
                    if (StringUtils.equalsIgnoreCase(innersyno.trim(), searchStr)) {
                         rankedObject.addToRank(BEGINING_OF_SENTENCE);
                    }
                }
                
                for (String innersyno : synonymsTrimmedString) {
                  String[] wordTrimmedString = innersyno.split(" ");
                    for (String innerword : wordTrimmedString) {
                        if (StringUtils.equalsIgnoreCase(innerword, searchStr)) {
                            rankedObject.addToRank(WHOLE_WORD_MATCH_SYNONYM);
                        } 
                    }
                }
            } else {
                // Whole word match primary when no synonym is there
                String[] wordTrimmedString = str.split(" ");
                for (String innerword : wordTrimmedString) {
                    if (StringUtils.equalsIgnoreCase(innerword, searchStr)) {
                        rankedObject.addToRank(WHOLE_WORD_MATCH);
                    } 
                }
            }
            String[] wordTrimmedString = innerString.split(" ");
            for (String innerword : wordTrimmedString) {
                if (StringUtils.equalsIgnoreCase(innerword, searchStr)) {
                    rankedObject.addToRank(WHOLE_WORD_MATCH_PRIMARY);
                } 
            }
         if (StringUtils.containsIgnoreCase(innerString.trim(), searchStr)) {
            rankedObject.addToRank(PART_OF_SENTENCE_PRIMARY);
         }
        }
     // has no exact match but it is a part of word
        rankedObject.addToRank(PART_OF_SENTENCE); 
        return rankedObject;
    }
    
}
