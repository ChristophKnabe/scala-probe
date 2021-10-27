package samples;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

public class JUnitJavaTest {

    @Test
    public void testOK(){
        assertTrue(true);
    }

    @Test
    public void testKO(){
        assertTrue(false);
    }

}


