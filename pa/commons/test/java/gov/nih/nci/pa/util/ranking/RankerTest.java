package gov.nih.nci.pa.util.ranking;

import junit.framework.TestCase;

/**
 * @author: 
 */
public class RankerTest extends TestCase {

    
    
    public void testRank() throws Exception {

     
        Ranker ranker = new Ranker("biju Joseph");

        Serializer<String> serializer = new Serializer<String>(){
            public String serialize(String object) {
                return object;
            }
        };

        assertEquals(100000, ranker.rank("Biju jOSEph", serializer).getRank());
        assertEquals(9995, ranker.rank("Joel Biju Joseph", serializer).getRank());
        assertEquals(9986, ranker.rank("Some one name Biju Joseph has it", serializer).getRank());
        assertEquals(485, ranker.rank("Last day was rebiju joseph day in here",  serializer).getRank());
        assertEquals(474, ranker.rank("Come here for rebiju on rebiju joseph day sometime in the evening says biju today.", serializer).getRank());
        assertEquals(50000, ranker.rank("Biju jOSEph's world",  serializer).getRank());
        assertEquals(0, ranker.rank("some one stop me",  serializer).getRank());
        assertEquals(982, ranker.rank("This sentence has biju joseph's name mentioned biju joseph says again biju joseph and cannot say biju jose",serializer).getRank());
        assertEquals(0, ranker.rank("hello can I call biju jose for a quick meeting joseph said", serializer).getRank());

        ranker = new Ranker("MD39");

        assertEquals(9977, ranker.rank("M.D Anderson Hospital (MD39)",serializer).getRank());

        ranker = new Ranker("5876");
        assertEquals(49999, ranker.rank("(5876) Test Study",  serializer).getRank());
        assertEquals(9995, ranker.rank("jai (5876) Test Study",  serializer).getRank());
        assertEquals(9990, ranker.rank("jai hind (5876)",  serializer).getRank());

        ranker = new Ranker("study");
        assertEquals(9988, ranker.rank("(5876) Test Study",  serializer).getRank());

        ranker = new Ranker("(5");
        assertEquals(50000, ranker.rank("(5876) Test Study",  serializer).getRank());


        ranker = new Ranker("76)");
        assertEquals(497, ranker.rank("(5876) Test Study",  serializer).getRank());

        ranker = new Ranker("md5");
        assertEquals(49999, ranker.rank("(MD53) hello biju",  serializer).getRank());

    }
    
    
    

  public void testRankCaDSR() throws Exception {
      Ranker ranker = new Ranker("delta");
       Serializer<String> serializer = new Serializer<String>(){
           public String serialize(String object) {
               return object;
           }
       };
       String str ="delta catenin";
       assertEquals(10000, ranker.rankCaDSR(str, serializer).getRank());
       str = "DEGS2 (delta(4)-desaturase, sphingolipid 2; C14orf66, DES2, FADS8)";
       assertEquals(500, ranker.rankCaDSR(str, serializer).getRank());
       ranker = new Ranker("delta catenin");
       str ="delta catenin";
       assertEquals(100000, ranker.rankCaDSR(str, serializer).getRank());
       ranker = new Ranker("PGR");
       str ="PGR (progesterone receptor, NR3C3, PR)";
       assertEquals(100000, ranker.rankCaDSR(str, serializer).getRank());
       ranker = new Ranker("PGR A");
       str ="PGR A (progesterone receptor, NR3C3, PR)";
       assertEquals(100000, ranker.rankCaDSR(str, serializer).getRank());
       ranker = new Ranker("PR");
       str ="PGR (progesterone receptor; NR3C3; PR)";
       assertEquals(50000, ranker.rankCaDSR(str, serializer).getRank());
       ranker = new Ranker("PR");
       str ="PGR (progesterone receptor; NR3C3; PR)";
       assertEquals(50000, ranker.rankCaDSR(str, serializer).getRank());
       ranker = new Ranker("NR3C3");
       str ="PGR (progesterone receptor; NR3C3; PR)";
       assertEquals(50000, ranker.rankCaDSR(str, serializer).getRank());
       ranker = new Ranker("receptor");
       str ="PGR (progesterone receptor; NR3C3; PR)";
       assertEquals(4000, ranker.rankCaDSR(str, serializer).getRank());
       ranker = new Ranker("noMatch");
       str ="PGR (progesterone receptor; NR3C3; PR)";
       assertEquals(500, ranker.rankCaDSR(str, serializer).getRank());
       ranker = new Ranker("PR");
       str ="PPP2R1A (protein phosphatase 2, regulatory subunit A, alpha; PR65A, PP2A-Aalpha,";
       assertEquals(500, ranker.rankCaDSR(str, serializer).getRank());
       ranker = new Ranker("PR");
       str ="SPRY4 (sprouty homolog 4 (Drosophila))";
       assertEquals(700, ranker.rankCaDSR(str, serializer).getRank());
       ranker = new Ranker("PR");
       str ="PELO (Protein Pelota Homolog; Pelota Homolog (Drosophila); CGI-17; PRO1770)";
       assertEquals(500, ranker.rankCaDSR(str, serializer).getRank());
       ranker = new Ranker("PR");
       str ="BMPR2 (BMR2; PPH1; BMPR3; BRK-3; T-ALK; BMPR-II; BMP Type II Receptor; BMP Type-2 Receptor; BMPR-2; Bone Morphogenetic Protein Receptor Type II; Bone Morphogenetic Protein Receptor Type-2; Bone Morphogenetic Protein Receptor, Type II (Serine/Threonine Kin";
       assertEquals(700, ranker.rankCaDSR(str, serializer).getRank());
       ranker = new Ranker("PR");
       str ="PMAIP1 (phorbol-12-myristate-13-acetate-induced protein 1, NOXA, APR; Adult T Cell Leukemia-Derived PMA-Responsive Protein, ATL-Derived PMA-Responsive Protein, Immediate-Early-Response Protein APR, PMA-Induced Protein 1, Protein Noxa))";
       assertEquals(500, ranker.rankCaDSR(str, serializer).getRank());
       ranker = new Ranker("PR");
       str ="MECOM (MDS1 and EVI1 Complex Locus, EVI1, Ecotropic Viral Integration Site 1, MDS1-EVI1, MDS1, MGC163392, MGC97004, Myelodysplasia Syndrome 1, PRDM3, EVI 1 Protein, EVI-1,: Ecotropic Virus Integration 1 Site Protein, Ecotropic Virus Integration Site 1 Pr";
       assertEquals(4000, ranker.rankCaDSR(str, serializer).getRank());
       ranker = new Ranker("t");
       str ="t(12;21)";
       assertEquals(100000, ranker.rankCaDSR(str, serializer).getRank());
       ranker = new Ranker("t");
       str ="EBNA-2 Epstein-Barr Nuclear Antigen 2; EBV Nuclear Antigen 2; BYRF1; Epstein-Barr Virus Nuclear Antigen 2; EBNA2)";
       assertEquals(700, ranker.rankCaDSR(str, serializer).getRank());
       ranker = new Ranker("peci");
       str ="ECI2 (PECI; DRS1; ACBD2; DRS-1; HCA88; Enoyl-CoA Delta Isomerase 2; Enoyl-CoA Delta Isomerase 2, Mitochondrial; DBI-Related Protein 1; D3,D2-Enoyl-CoA Isomerase; Renal Carcinoma Antigen NY-REN-1; Peroxisomal 3,2-Trans-Enoyl-CoA Isomerase; Hepatocellular C";
       assertEquals(50000, ranker.rankCaDSR(str, serializer).getRank());
       ranker = new Ranker("met");
       str ="2-Amino-1-Methyl-6-Phenylimidazo[4,5-b]Pyridine";
       assertEquals(700, ranker.rankCaDSR(str, serializer).getRank());
       ranker = new Ranker("alpha");
       str ="1,3-Butadiene (alpha, gamma-Butadiene; Biethylene; Bivinyl; Divinyl; Erythrene; Vinylethylene)";
       assertEquals(500, ranker.rankCaDSR(str, serializer).getRank());
       ranker = new Ranker("EMA");
       str ="MUC-1 Antigen (Mucin 1; MUC-1; CD227; CA15-3; CA15.3; CA 15.3; CA 15-3; Cancer Antigen 15-3; PEM; PEMT; PUM; EMA; H23AG; Episialin; Epithelial Membrane Antigen; H23 Antigen; CA 15 3; MUC-1 Antigen; Mucin Antigen; CA15-3 Antigen; DF3 Antigen)";
       assertEquals(50000, ranker.rankCaDSR(str, serializer).getRank());
       ranker = new Ranker("tf");
       str ="TF var (Transferrin; Serotransferrin; Siderophilin; Beta-1 Metal-Binding Globulin; Granulocyte/Pollen-Binding Protein; TFQTL1; PRO1557; PRO2086; PRO1400)";
       assertEquals(7000, ranker.rankCaDSR(str, serializer).getRank());
       ranker = new Ranker("MUC-1");
       str ="CA15-3 (CA15-3 Antigen; Cancer Antigen 15-3; CA15.3; CA 15.3; CA 15-3; DF3 Antigen; CA 15 3; MUC-1 Antigen; MUC-1; Mucin Antigen; Sialylated MUC-1)";
       assertEquals(500, ranker.rankCaDSR(str, serializer).getRank());
       ranker = new Ranker("MUC1");
       str ="MUC1 (Mucin 1; CD227; CA15-3; CA15.3; CA 15.3; CA 15-3; Cancer Antigen 15-3; PEM; PEMT; PUM; EMA; H23AG; Episialin; Epithelial Membrane Antigen; H23 Antigen; CA 15 3; MUC-1 Antigen; Mucin Antigen; CA15-3 Antigen; DF3 Antigen)";
       assertEquals(100000, ranker.rankCaDSR(str, serializer).getRank());
       ranker = new Ranker("MCU-1");
       str ="CA27.29 (CA 27.29; CA 27-29; Cancer Antigen 27.29; Peripheral Blood MUC-1; Soluble MUC-1; CA27-29)";
       assertEquals(500, ranker.rankCaDSR(str, serializer).getRank());
  }
}
