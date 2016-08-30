package com.sf.Roboletic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.mockito.stubbing.OngoingStubbing;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by NetEase on 2016/7/21 0021.
 */
@RunWith(SFPowerRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
@PowerMockRunnerDelegate(SFRoboletricTestRunner.class)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
@PrepareForTest({PowerMockNewObject.class, PowerMockNewObject.Student.class})
public class PowerMockNewObjectTest {

    @Test
    public void testNewObject() {
        try {
            PowerMockNewObject.Student student=new PowerMockNewObject.Student();

            final PowerMockNewObject.Student mockStudent = PowerMockito.mock(student.getClass());

//            mockStudent.setName("aaaaaaaaaaa");

            PowerMockito.whenNew(PowerMockNewObject.Student.class)
                    .withArguments(Mockito.anyString(),Mockito.anyInt())
                    .thenReturn(mockStudent);
//            mockStudent.setName("mockStudent");

//            PowerMockito.doAnswer(new Answer<String>() {
//
//                @Override
//                public String answer(InvocationOnMock invocation) throws Throwable {
//                    System.out.println("---------InvocationOnMock------------" );
//                    Object object=invocation.getMock();
//                    PowerMockNewObject.Student invocationStudents= (PowerMockNewObject.Student) object;
//                    invocationStudents.setName("----------InvocationOnMock");
//                    return "InvocationOnMock";
//                }
//            }).when(mockStudent).doTest();


            PowerMockNewObject object = new PowerMockNewObject();
            String name = object.getOneStudentName();
            Mockito.verify(mockStudent).doTest();

            System.out.print("name: " + name);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("exception: " + e);
        }
    }
}
