/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The pa
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This pa Software License (the License) is between NCI and You. You (or
 * Your) shall mean a person or an entity, and all other entities that control,
 * are controlled by, or are under common control with the entity. Control for
 * purposes of this definition means (i) the direct or indirect power to cause
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares,
 * or (iii) beneficial ownership of such entity.
 *
 * This License is granted provided that You agree to the conditions described
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up,
 * no-charge, irrevocable, transferable and royalty-free right and license in
 * its rights in the pa Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the pa Software; (ii) distribute and
 * have distributed to and by third parties the pa Software and any
 * modifications and derivative works thereof; and (iii) sublicense the
 * foregoing rights set out in (i) and (ii) to third parties, including the
 * right to license such rights to further third parties. For sake of clarity,
 * and not by way of limitation, NCI shall have no right of accounting or right
 * of payment from You or Your sub-licensees for the rights granted under this
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the
 * above copyright notice, this list of conditions and the disclaimer and
 * limitation of liability of Article 6, below. Your redistributions in object
 * code form must reproduce the above copyright notice, this list of conditions
 * and the disclaimer of Article 6 in the documentation and/or other materials
 * provided with the distribution, if any.
 *
 * Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: This product includes software
 * developed by 5AM and the National Cancer Institute. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM"
 * to endorse or promote products derived from this Software. This License does
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the
 * terms of this License.
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this
 * Software into Your proprietary programs and into any third party proprietary
 * programs. However, if You incorporate the Software into third party
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software
 * into such third party proprietary programs and for informing Your
 * sub-licensees, including without limitation Your end-users, of their
 * obligation to secure any required permissions from such third parties before
 * incorporating the Software into such third party proprietary software
 * programs. In the event that You fail to obtain such permissions, You agree
 * to indemnify NCI for any claims against NCI by such third parties, except to
 * the extent prohibited by law, resulting from Your failure to obtain such
 * permissions.
 *
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the Software, or any derivative works of the
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.iso21090.Ivl;
import gov.nih.nci.iso21090.Pq;
import gov.nih.nci.pa.domain.PlannedSubstanceAdministration;
import gov.nih.nci.pa.iso.dto.PlannedSubstanceAdministrationDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.ISOUtil;

/**
 * The Class PlannedSubstanceAdministrationConverter.
 *
 * @author Kalpana Guthikonda
 * @since 21/10/2009
 */
public class PlannedSubstanceAdministrationConverter
    extends AbstractConverter<PlannedSubstanceAdministrationDTO, PlannedSubstanceAdministration> {

    /**
     * {@inheritDoc}
     */
    @Override
    public PlannedSubstanceAdministrationDTO convertFromDomainToDto(PlannedSubstanceAdministration psa) {
        PlannedSubstanceAdministrationDTO psaDTO = (PlannedSubstanceAdministrationDTO)
        PlannedActivityConverter.convertFromDomainToDTO(psa, new PlannedSubstanceAdministrationDTO());
        psaDTO.setDoseDescription(StConverter.convertToSt(psa.getDoseDescription()));
        psaDTO.setDoseRegimen(StConverter.convertToSt(psa.getDoseRegimen()));
        psaDTO.setDoseFormCode(CdConverter.convertStringToCd(psa.getDoseFormCode()));
        psaDTO.setDoseFrequencyCode(CdConverter.convertStringToCd(psa.getDoseFrequencyCode()));
        psaDTO.setRouteOfAdministrationCode(CdConverter.convertStringToCd(psa.getRouteOfAdministrationCode()));

        IvlConverter.JavaPq minDose  = new IvlConverter.JavaPq(psa.getDoseMinUnit(), psa.getDoseMinValue(), null);
        IvlConverter.JavaPq maxDose  = new IvlConverter.JavaPq(psa.getDoseMaxUnit(), psa.getDoseMaxValue(), null);
        Ivl<Pq> ivlDose = IvlConverter.convertPq().convertToIvl(minDose, maxDose);
        psaDTO.setDose(ivlDose);

        IvlConverter.JavaPq minDoseTotal  = new IvlConverter.JavaPq(psa.getDoseTotalMinUnit(),
                psa.getDoseTotalMinValue(), null);
        IvlConverter.JavaPq maxDoseTotal  = new IvlConverter.JavaPq(psa.getDoseTotalMaxUnit(),
                psa.getDoseTotalMaxValue(), null);
        Ivl<Pq> ivlDoseTotal = IvlConverter.convertPq().convertToIvl(minDoseTotal, maxDoseTotal);
        psaDTO.setDoseTotal(ivlDoseTotal);

        Pq doseDuration = new Pq();
        doseDuration.setValue(psa.getDoseDurationValue());
        if (psa.getDoseDurationUnit() != null) {
            doseDuration.setUnit(psa.getDoseDurationUnit());
        }
        psaDTO.setDoseDuration(doseDuration);
        psaDTO.setApproachSiteCode(CdConverter.convertStringToCd(psa.getApproachSiteCode()));
        psaDTO.setTargetSiteCode(CdConverter.convertStringToCd(psa.getTargetSiteCode()));
        psaDTO.setDisplayOrder(IntConverter.convertToInt(psa.getDisplayOrder()));
        return psaDTO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlannedSubstanceAdministration convertFromDtoToDomain(PlannedSubstanceAdministrationDTO psaDTO) {
        PlannedSubstanceAdministration psa = new PlannedSubstanceAdministration();
        convertFromDtoToDomain(psaDTO, psa);
        return psa;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void convertFromDtoToDomain(PlannedSubstanceAdministrationDTO psaDTO, PlannedSubstanceAdministration psa) {
        new PlannedActivityConverter().convertFromDtoToDomain(psaDTO, psa);
        if (!ISOUtil.isStNull(psaDTO.getDoseDescription())) {
            psa.setDoseDescription(StConverter.convertToString(psaDTO.getDoseDescription()));
        }
        if (!ISOUtil.isStNull(psaDTO.getDoseRegimen())) {
            psa.setDoseRegimen(StConverter.convertToString(psaDTO.getDoseRegimen()));
        }
        if (!ISOUtil.isCdNull(psaDTO.getDoseFormCode())) {
            psa.setDoseFormCode(CdConverter.convertCdToString(psaDTO.getDoseFormCode()));
        }
        if (!ISOUtil.isCdNull(psaDTO.getDoseFrequencyCode())) {
            psa.setDoseFrequencyCode(CdConverter.convertCdToString(psaDTO.getDoseFrequencyCode()));
        }
        psa.setDisplayOrder(IntConverter.convertToInteger(psaDTO.getDisplayOrder()));
        convertOtherInfo(psaDTO, psa);
        convertDose(psaDTO, psa);
        convertDoseDuration(psaDTO, psa);
        convertDoseTotal(psaDTO, psa);
    }

    private void convertOtherInfo(PlannedSubstanceAdministrationDTO psaDTO, PlannedSubstanceAdministration psa) {
        if (!ISOUtil.isCdNull(psaDTO.getRouteOfAdministrationCode())) {
            psa.setRouteOfAdministrationCode(CdConverter.convertCdToString(psaDTO.getRouteOfAdministrationCode()));
        }
        if (!ISOUtil.isCdNull(psaDTO.getApproachSiteCode())) {
            psa.setApproachSiteCode(CdConverter.convertCdToString(psaDTO.getApproachSiteCode()));
        }
        if (!ISOUtil.isCdNull(psaDTO.getTargetSiteCode())) {
            psa.setTargetSiteCode(CdConverter.convertCdToString(psaDTO.getTargetSiteCode()));
        }
    }

    private void convertDose(PlannedSubstanceAdministrationDTO psaDTO, PlannedSubstanceAdministration psa) {
        if (psaDTO.getDose() != null) {
            if (!ISOUtil.isIvlLowNull(psaDTO.getDose())) {
                psa.setDoseMinValue(psaDTO.getDose().getLow().getValue());
            }
            if (!ISOUtil.isIvlUnitNull(psaDTO.getDose())) {
                psa.setDoseMinUnit(psaDTO.getDose().getLow().getUnit());
            }
            if (!ISOUtil.isIvlHighNull(psaDTO.getDose())) {
                psa.setDoseMaxValue(psaDTO.getDose().getHigh().getValue());
            }
            if (!ISOUtil.isIvlUnitNull(psaDTO.getDose())) {
                psa.setDoseMaxUnit(psaDTO.getDose().getHigh().getUnit());
            }
        }
    }

    private void convertDoseDuration(PlannedSubstanceAdministrationDTO psaDTO, PlannedSubstanceAdministration psa) {
        if (psaDTO.getDoseDuration() != null) {
            if (!ISOUtil.isPqValueNull(psaDTO.getDoseDuration())) {
                psa.setDoseDurationValue(psaDTO.getDoseDuration().getValue());
            }
            if (!ISOUtil.isPqUnitNull(psaDTO.getDoseDuration())) {
                psa.setDoseDurationUnit(psaDTO.getDoseDuration().getUnit());
            }
        }
    }

    private void convertDoseTotal(PlannedSubstanceAdministrationDTO psaDTO, PlannedSubstanceAdministration psa) {
        if (psaDTO.getDoseTotal() != null) {
            if (!ISOUtil.isIvlLowNull(psaDTO.getDoseTotal())) {
                psa.setDoseTotalMinValue(psaDTO.getDoseTotal().getLow().getValue());
            }
            if (!ISOUtil.isIvlUnitNull(psaDTO.getDoseTotal())) {
                psa.setDoseTotalMinUnit(psaDTO.getDoseTotal().getLow().getUnit());
            }
            if (!ISOUtil.isIvlHighNull(psaDTO.getDoseTotal())) {
                psa.setDoseTotalMaxValue(psaDTO.getDoseTotal().getHigh().getValue());
            }
            if (!ISOUtil.isIvlUnitNull(psaDTO.getDoseTotal())) {
                psa.setDoseTotalMaxUnit(psaDTO.getDoseTotal().getHigh().getUnit());
            }
        }
    }
}
