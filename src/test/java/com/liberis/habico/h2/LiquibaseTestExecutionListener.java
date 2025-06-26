package com.liberis.habico.h2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

@Slf4j
public class LiquibaseTestExecutionListener implements TestExecutionListener {

    @Override
    public void beforeTestMethod(TestContext testContext) {
        log.info("beforeTestMethod is called");
        TestDatabaseHelper helper = testContext.getApplicationContext().getBean(TestDatabaseHelper.class);
        helper.resetDB();
    }
}
