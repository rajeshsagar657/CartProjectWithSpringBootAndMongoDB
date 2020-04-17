package com.ultipro.main;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

public class ApplicationStartTest {

    @Test
    public void testApplicationStartFunctionality() {
        ApplicationStart.main(new String[] {});
    }
}