package nl.rwslinkman.firebasetester;

import nl.rwslinkman.firebasetester.mock.MockGUI;
import nl.rwslinkman.firebasetester.mock.MockHttpClient;
import nl.rwslinkman.firebasetester.mock.MockInputValidator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Rick Slinkman
 */
public class FirebaseTesterTest
{
    private FirebaseTester subj;
    private MockHttpClient mockHttpClient;
    private MockInputValidator mockInputValidator;

    @Before
    public void setUp() throws Exception
    {
        mockHttpClient = new MockHttpClient();
        mockInputValidator = new MockInputValidator();
        subj = new FirebaseTester(mockHttpClient, mockInputValidator, new MockGUI());
    }

    @Test
    public void test_shouldDoSomething_whenCalled()
    {
        // TODO Create test that makes sense
        subj.onFirebaseTestSubmitted("", "");
        assertTrue(mockHttpClient.wasCalledSendRequest());
        assertFalse(mockInputValidator.wasCalled());
    }
}