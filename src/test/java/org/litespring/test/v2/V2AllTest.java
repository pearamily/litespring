package org.litespring.test.v2;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ApplicationContextTestV2.class,BeanDefinationTestV2.class,BeanDefinationValueResolverTest.class,CustomBooleanEditorTest.class,CustomNumberEditorTest.class,TypeConverterTest.class})
public class V2AllTest {

}
