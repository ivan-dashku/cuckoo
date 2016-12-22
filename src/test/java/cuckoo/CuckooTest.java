package cuckoo;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertTrue;

public class CuckooTest {
    @Test
    public void putGetTest(){
        Random rand = new Random();
        int expected[] = new int[50];
        Cuckoo cuckoo = new Cuckoo<Integer,Double>();
        for (int i = 0; i < 50; i++){
            expected[i] = rand.nextInt(50);
            cuckoo.put(i,expected[i]);
        }
        for (int i = 0; i < 50; i++){
            assertTrue((int)cuckoo.get(i) == expected[i]);
        }
    }

}
