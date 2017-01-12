package gov.nih.nci.pa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Look up table for AnatomicSites.
 * 
 * @author Kalpana Guthikonda
 * @since 11/6/2009
 */
@Entity
@Table(name = "ANATOMIC_SITES")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
// Unit tests write, so cannot use read-only
public class AnatomicSite extends AbstractLookUpEntity {

    private static final long serialVersionUID = 1L;

    private String twitterHashtags;

    /**
     * @return the twitterHashtags
     */
    @Column(name = "twitter_hashtags")
    public String getTwitterHashtags() {
        return twitterHashtags;
    }

    /**
     * @param twitterHashtags
     *            the twitterHashtags to set
     */
    public void setTwitterHashtags(String twitterHashtags) {
        this.twitterHashtags = twitterHashtags;
    }

}
