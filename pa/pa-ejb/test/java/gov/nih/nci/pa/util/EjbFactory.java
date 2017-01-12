/**
 * 
 */
package gov.nih.nci.pa.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.SystemPropertyUtils;

/**
 * @author dkrylov
 * 
 */
@SuppressWarnings("rawtypes")
public final class EjbFactory {

    private List<Object> ejbs;

    public EjbFactory() throws ClassNotFoundException, IOException,
            InstantiationException, IllegalAccessException {

        Collection<Class> beanClasses = findMyTypes("gov.nih.nci.pa.service");
        List<Object> beanInstances = instantitate(beanClasses);
        injectDependencies(beanInstances);
        ejbs = beanInstances;

    }

    private void injectDependencies(List<Object> beanInstances) {
        for (Object ejb : beanInstances) {
            injectDependencies(ejb, beanInstances);
        }
    }

    private void injectDependencies(final Object ejb,
            final List<Object> beanInstances) {
        ReflectionUtils.doWithFields(ejb.getClass(),
                new ReflectionUtils.FieldCallback() {
                    @Override
                    public void doWith(Field f)
                            throws IllegalArgumentException,
                            IllegalAccessException {
                        ReflectionUtils.makeAccessible(f);
                        injectDependency(ejb, f, beanInstances);
                    }
                }, new ReflectionUtils.FieldFilter() {
                    @Override
                    public boolean matches(Field f) {
                        return f.isAnnotationPresent(javax.ejb.EJB.class);
                    }
                });
    }

    protected void injectDependency(Object ejb, Field f,
            List<Object> beanInstances) {
        Class refEjbInterface = f.getType();
        if (!refEjbInterface.isInterface()) {
            throw new RuntimeException(
                    "@EJB reference does not point to an interface: "
                            + f.toString());
        }
        for (Object refEjb : beanInstances) {
            Class refEjbClass = refEjb.getClass();
            if (ArrayUtils.contains(refEjbClass.getGenericInterfaces(),
                    refEjbInterface)) {
                System.out.println(f.toString() + " injected with " + refEjb);
                ReflectionUtils.setField(f, ejb, refEjb);
                return;
            }
        }
        throw new RuntimeException(
                "Unable to resolve and inject an EJB reference: " + f);
    }

    private List<Object> instantitate(Collection<Class> beanClasses)
            throws InstantiationException, IllegalAccessException {
        List<Object> objects = new ArrayList<>();
        for (Class ejbClass : beanClasses) {
            objects.add(ejbClass.newInstance());
        }
        return objects;
    }

    @SuppressWarnings("rawtypes")
    private Set<Class> findMyTypes(String basePackage) throws IOException,
            ClassNotFoundException {
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(
                resourcePatternResolver);

        Set<Class> candidates = new LinkedHashSet<Class>();
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                + resolveBasePackage(basePackage) + "/" + "**/*.class";
        Resource[] resources = resourcePatternResolver
                .getResources(packageSearchPath);
        for (Resource resource : resources) {
            if (resource.isReadable()) {
                MetadataReader metadataReader = metadataReaderFactory
                        .getMetadataReader(resource);
                if (isCandidate(metadataReader)) {
                    candidates.add(Class.forName(metadataReader
                            .getClassMetadata().getClassName()));
                }
            }
        }
        return candidates;
    }

    private String resolveBasePackage(String basePackage) {
        return ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils
                .resolvePlaceholders(basePackage));
    }

    @SuppressWarnings("unchecked")
    private boolean isCandidate(MetadataReader metadataReader)
            throws ClassNotFoundException {
        try {
            Class c = Class.forName(metadataReader.getClassMetadata()
                    .getClassName());
            if (c.getAnnotation(javax.ejb.Stateless.class) != null) {
                return true;
            }
        } catch (Throwable e) {
        }
        return false;
    }

    public static void main(String[] args) throws ClassNotFoundException,
            IOException, InstantiationException, IllegalAccessException {
        System.out.println(new EjbFactory());
    }

    /**
     * @return the ejbs
     */
    public List<Object> getEjbs() {
        return Collections.unmodifiableList(ejbs);
    }

}
