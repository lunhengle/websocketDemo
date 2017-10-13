import org.junit.Test;

import java.util.Random;

public class TestRandom {
    @Test
    public void testRandom() {
        for (int i = 0; i < 100; i++) {
            Random random = new Random();
            Long myId = Long.valueOf(random.nextInt(2)+1);
            System.out.println(myId);
        }
    }
}
