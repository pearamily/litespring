package org.litespring.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.litespring.test.v1.V1AllTest;
import org.litespring.test.v2.V2AllTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({V1AllTest.class, V2AllTest.class})
public class AllTest {
}
