package org.litespring.context.annotation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.litespring.beans.BeanDefination;
import org.litespring.beans.factory.BeanDefinationStoreException;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.support.BeanDefinationRegistry;
import org.litespring.beans.factory.support.BeanNameGenerator;
import org.litespring.core.io.Resource;
import org.litespring.core.io.support.PackageResourceLoader;
import org.litespring.core.type.classreading.MetadataReader;
import org.litespring.core.type.classreading.SimpleMetadataReader;
import org.litespring.stereotype.Component;
import org.litespring.util.StringUtils;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

public class ClassPathBeanDefinationScanner {
    private final Log logger = LogFactory.getLog(getClass());


    private final BeanDefinationRegistry registry;

    private PackageResourceLoader resourceLoader = new PackageResourceLoader();

    private BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();


    public ClassPathBeanDefinationScanner(BeanDefinationRegistry registry) {
        this.registry = registry;
    }

    public Set<BeanDefination> doScan(String packagesToScan) {

        String[] basePackages = StringUtils.tokenizeToStringArray(packagesToScan, ",");

        Set<BeanDefination> beanDefinations = new LinkedHashSet<BeanDefination>();

        for (String basePackage : basePackages) {
            Set<BeanDefination> candidates = findCandidateComponents(basePackage);
            for (BeanDefination cadidate : candidates
            ) {
                beanDefinations.add(cadidate);
                registry.registerBeanDefination(cadidate.getId(), cadidate);
            }

        }
        return beanDefinations;

    }

    private Set<BeanDefination> findCandidateComponents(String basePackage) {

        Set<BeanDefination> candidates = new LinkedHashSet<BeanDefination>();

        try {
            Resource[] resources = this.resourceLoader.getResources(basePackage);

            for (Resource resource : resources
            ) {
                try {
                    MetadataReader metadataReader = new SimpleMetadataReader(resource);

                    if (metadataReader.getAnnotationMetadata().hasAnnotation(Component.class.getName())) {
                        ScanneredGenericBeanDefination sbd = new ScanneredGenericBeanDefination(metadataReader.getAnnotationMetadata());
                        String beanName = this.beanNameGenerator.generateBeanName(sbd, this.registry);
                        sbd.setId(beanName);
                        candidates.add(sbd);
                    }
                } catch (Throwable ex) {
                    throw new BeanDefinationStoreException("Failed to read candidate component class:" + resource, ex);
                }

            }
        } catch (IOException ex
        ) {
            throw new BeanDefinitionStoreException("I/O failure during classpath scanning", ex);
        }
        return candidates;
    }
}
