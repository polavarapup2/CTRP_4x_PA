package gov.nih.nci.pa.decorator;

import gov.nih.nci.pa.dto.TrialFundingWebDTO;

import org.displaytag.decorator.TableDecorator;

/**
 * @author Hugh Reinhart
 * @since Jul 11, 2013
 */
public class TrialFundingTableDecorator extends TableDecorator {

    /**
     * @return funding percent with % sign appended
     */
    public String getFundingPercent() {
        String fp = ((TrialFundingWebDTO) this.getCurrentRowObject()).getFundingPercent();
        return  fp == null ? "" : fp + "%";
    }
}
