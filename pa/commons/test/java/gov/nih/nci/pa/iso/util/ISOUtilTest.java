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
package gov.nih.nci.pa.iso.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Int;
import gov.nih.nci.iso21090.Ivl;
import gov.nih.nci.iso21090.Pq;
import gov.nih.nci.iso21090.Real;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.pa.util.ISOUtil;

import java.util.HashSet;

import org.junit.Test;

/**
 * @author mshestopalov
 *
 */
public class ISOUtilTest {

    /**
     * Test method for {@link gov.nih.nci.pa.util.ISOUtil#isIiNull(gov.nih.nci.iso21090.Ii)}.
     */
    @Test
    public void testIsIiNull() {
        Ii ii = null;
        assertTrue(ISOUtil.isIiNull(ii));
        ii = new Ii();
        assertTrue(ISOUtil.isIiNull(ii));
        ii.setExtension(null);
        assertTrue(ISOUtil.isIiNull(ii));
        ii.setExtension("");
        assertTrue(ISOUtil.isIiNull(ii));

    }

    /**
     * Test method for {@link gov.nih.nci.pa.util.ISOUtil#isCdNull(gov.nih.nci.iso21090.Cd)}.
     */
    @Test
    public void testIsCdNull() {
        Cd cd = null;
        assertTrue(ISOUtil.isCdNull(cd));
        cd = new Cd();
        assertTrue(ISOUtil.isCdNull(cd));
        cd.setCode(null);
        assertTrue(ISOUtil.isCdNull(cd));
        cd.setCode("");
        assertTrue(ISOUtil.isCdNull(cd));

    }

    /**
     * Test method for {@link gov.nih.nci.pa.util.ISOUtil#isStNull(gov.nih.nci.iso21090.St)}.
     */
    @Test
    public void testIsStNull() {
        St st = null;
        assertTrue(ISOUtil.isStNull(st));
        st = new St();
        st.setValue(null);
        assertTrue(ISOUtil.isStNull(st));
        st.setValue("");
        assertTrue(ISOUtil.isStNull(st));
    }

    /**
     * Test method for {@link gov.nih.nci.pa.util.ISOUtil#isTsNull(gov.nih.nci.iso21090.Ts)}.
     */
    @Test
    public void testIsTsNull() {
        Ts ts = null;
        assertTrue(ISOUtil.isTsNull(ts));
        ts = new Ts();
        ts.setValue(null);
        assertTrue(ISOUtil.isTsNull(ts));
    }

    /**
     * Test method for {@link gov.nih.nci.pa.util.ISOUtil#isBlNull(gov.nih.nci.iso21090.Bl)}.
     */
    @Test
    public void testIsBlNull() {
        Bl bl = null;
        assertTrue(ISOUtil.isBlNull(bl));
    }

    /**
     * Test method for {@link gov.nih.nci.pa.util.ISOUtil#isIntNull(gov.nih.nci.iso21090.Int)}.
     */
    @Test
    public void testIsIntNull() {
        Int in = null;
        assertTrue(ISOUtil.isIntNull(in));
        in = new Int();
        in.setValue(null);
        assertTrue(ISOUtil.isIntNull(in));
    }

    /**
     * Test method for {@link gov.nih.nci.pa.util.ISOUtil#isRealNull(gov.nih.nci.iso21090.Real)}.
     */
    @Test
    public void testIsRealNull() {
        Real real = null;
        assertTrue(ISOUtil.isRealNull(real));
        real = new Real();
        real.setValue(null);
        assertTrue(ISOUtil.isRealNull(real));
        real.setValue(123.321d);
        assertFalse(ISOUtil.isRealNull(real));
    }

    /**
     * Test method for {@link gov.nih.nci.pa.util.ISOUtil#isPqValueNull(gov.nih.nci.iso21090.Pq)}.
     */
    @Test
    public void testIsPqValueNull() {
        Pq pq =null;
        assertTrue(ISOUtil.isPqValueNull(pq));
        pq = new Pq();
        pq.setValue(null);
        assertTrue(ISOUtil.isPqValueNull(pq));
    }

    /**
     * Test method for {@link gov.nih.nci.pa.util.ISOUtil#isPqUnitNull(gov.nih.nci.iso21090.Pq)}.
     */
    @Test
    public void testIsPqUnitNull() {
        Pq pq =null;
        assertTrue(ISOUtil.isPqUnitNull(pq));
        pq = new Pq();
        pq.setUnit(null);
        assertTrue(ISOUtil.isPqUnitNull(pq));
    }

    /**
     * Test method for {@link gov.nih.nci.pa.util.ISOUtil#isIvlHighNull(gov.nih.nci.iso21090.Ivl)}.
     */
    @Test
    public void testIsIvlHighNull() {
        Ivl<Pq> ivl = null;
        assertTrue(ISOUtil.isIvlHighNull(ivl));
        ivl = new Ivl<Pq>();
        Pq pqHigh = null;
        ivl.setHigh(pqHigh);
        assertTrue(ISOUtil.isIvlHighNull(ivl));
        pqHigh = new Pq();
        pqHigh.setValue(null);
        ivl.setHigh(pqHigh);
        assertTrue(ISOUtil.isIvlHighNull(ivl));
    }

    /**
     * Test method for {@link gov.nih.nci.pa.util.ISOUtil#isIvlLowNull(gov.nih.nci.iso21090.Ivl)}.
     */
    @Test
    public void testIsIvlLowNull() {
        Ivl<Pq> ivl = null;
        assertTrue(ISOUtil.isIvlLowNull(ivl));
        ivl = new Ivl<Pq>();
        Pq pqLow = null;
        ivl.setLow(pqLow);
        assertTrue(ISOUtil.isIvlLowNull(ivl));
        pqLow = new Pq();
        pqLow.setValue(null);
        ivl.setLow(pqLow);
        assertTrue(ISOUtil.isIvlLowNull(ivl));
    }

    /**
     * Test method for {@link gov.nih.nci.pa.util.ISOUtil#isIvlUnitNull(gov.nih.nci.iso21090.Ivl)}.
     */
    @Test
    public void testIsIvlUnitNull() {
        Ivl<Pq> ivl = null;
        assertTrue(ISOUtil.isIvlUnitNull(ivl));
        ivl = new Ivl<Pq>();
        Pq pqHigh = null;
        ivl.setHigh(pqHigh);
        assertTrue(ISOUtil.isIvlUnitNull(ivl));
        pqHigh = new Pq();
        ivl.setHigh(pqHigh);
        assertTrue(ISOUtil.isIvlUnitNull(ivl));
        pqHigh.setUnit(null);
        Pq pqLow = new Pq();
        ivl.setHigh(pqHigh);
        ivl.setLow(pqLow);
        assertTrue(ISOUtil.isIvlUnitNull(ivl));
        pqHigh.setUnit("");
        ivl.setHigh(pqHigh);
        pqLow.setUnit("");
        ivl.setLow(pqLow);
        assertFalse(ISOUtil.isIvlUnitNull(ivl));
    }

    @Test
    public void isDsetEmpty() {
        assertTrue(ISOUtil.isDSetEmpty(null));

        DSet<Ii> dset = new DSet<Ii>();
        assertTrue(ISOUtil.isDSetEmpty(dset));

        dset.setItem(null);
        assertTrue(ISOUtil.isDSetEmpty(dset));

        dset.setItem(new HashSet<Ii>());
        assertTrue(ISOUtil.isDSetEmpty(dset));

        dset.getItem().add(new Ii());
        assertFalse(ISOUtil.isDSetEmpty(dset));
    }

    @Test
    public void isDsetNotEmpty() {
        assertFalse(ISOUtil.isDSetNotEmpty(null));

        DSet<Ii> dset = new DSet<Ii>();
        assertFalse(ISOUtil.isDSetNotEmpty(dset));

        dset.setItem(null);
        assertFalse(ISOUtil.isDSetNotEmpty(dset));

        dset.setItem(new HashSet<Ii>());
        assertFalse(ISOUtil.isDSetNotEmpty(dset));

        dset.getItem().add(new Ii());
        assertTrue(ISOUtil.isDSetNotEmpty(dset));
    }
}
