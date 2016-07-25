package com.sf.Roboletic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.robolectric.RobolectricTestRunner;

import static org.mockito.Mockito.*;

import java.util.List;

/**
 * Created by NetEase on 2016/7/18 0018.
 */
@RunWith(RobolectricTestRunner.class)
public class CustomAnswerTest {
    @Test
    public void testCustom() {
        List<String> m = mock(List.class);
        doAnswer(new Answer<String> (){
            public String answer (InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                Integer num = (Integer) args[0];
                System.out.println("num: " + num);
                if (num > 3) {
                    return "yes";
                } else {
                    return "";
                }
            }
        }).when(m).get(anyInt());
        m.get(4);
        m.get(2);

    }
}
