package org.litespring.test.v4;

        import org.junit.runner.RunWith;
        import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ApplicationContextTestV4.class,AutoWiredAnnotationProcessTest.class,ClassPathBeanDefinationScannnerTest.class,ClassReaderTest.class,DependencyDescripterTest.class,InjectionMetadataDataTest.class,MetadataReaderTest.class,PackageResourceLoaderTest.class,XmlBeanDefinationReaderTest.class})
public class V4AllTest {
}
