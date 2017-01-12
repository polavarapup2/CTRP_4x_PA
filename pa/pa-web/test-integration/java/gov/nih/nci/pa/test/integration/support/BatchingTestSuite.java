/**
 * 
 */
package gov.nih.nci.pa.test.integration.support;

import java.util.ArrayList;
import java.util.List;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

/**
 * @author dkrylov
 * 
 */
public class BatchingTestSuite extends Suite {

    /**
     * @param klass
     * @param builder
     * @throws InitializationError
     */
    public BatchingTestSuite(Class<?> klass, RunnerBuilder builder)
            throws InitializationError {
        super(klass, builder);
    }

    /**
     * @param builder
     * @param classes
     * @throws InitializationError
     */
    public BatchingTestSuite(RunnerBuilder builder, Class<?>[] classes)
            throws InitializationError {
        super(builder, classes);

    }

    /**
     * @param klass
     * @param suiteClasses
     * @throws InitializationError
     */
    public BatchingTestSuite(Class<?> klass, Class<?>[] suiteClasses)
            throws InitializationError {
        super(klass, suiteClasses);

    }

    /**
     * @param klass
     * @param runners
     * @throws InitializationError
     */
    public BatchingTestSuite(Class<?> klass, List<Runner> runners)
            throws InitializationError {
        super(klass, runners);

    }

    /**
     * @param builder
     * @param klass
     * @param suiteClasses
     * @throws InitializationError
     */
    public BatchingTestSuite(RunnerBuilder builder, Class<?> klass,
            Class<?>[] suiteClasses) throws InitializationError {
        super(builder, klass, suiteClasses);

    }

    @Override
    public List<Runner> getChildren() {
        final List<Runner> tests = super.getChildren();

        String batchNumberStr = System.getProperty("testbatch",
                System.getenv("testbatch"));
        if (batchNumberStr == null || batchNumberStr.trim().length() == 0) {
            System.out
                    .println("No batch number specified; running all the tests.");
            return tests;
        }

        final Integer batchNum = Integer.valueOf(batchNumberStr);
        System.out.println("Only running the tests within batch " + batchNum
                + " or without Batch annotation.");

        final List<Runner> filtered = new ArrayList<>();
        for (Runner runner : tests) {
            Description descr = runner.getDescription();
            Class cls = descr.getTestClass();
            Batch batch = (Batch) cls.getAnnotation(Batch.class);
            if (batch != null) {
                if (batch.number() == batchNum) {
                    filtered.add(runner);
                    System.out
                            .println("Including test with a matching batch number: "
                                    + cls.getSimpleName());
                } else {
                    System.out
                            .println("Excluding test: " + cls.getSimpleName());
                }
            } else {
                filtered.add(runner);
                System.out.println("Including test with no batch: "
                        + cls.getSimpleName());
            }
        }
        return filtered;
    }
}
