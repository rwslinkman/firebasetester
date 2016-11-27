package nl.rwslinkman.firebasetester.validator;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Rick Slinkman
 */
public class InputValidatorTest
{
    private ProdInputValidator inputValidator;

    @Before
    public void setUp() throws Exception {
        this.inputValidator = new ProdInputValidator();
    }

    @Test
    public void test_shouldReturnEmptyList_whenGivenValidInput() throws Exception
    {
        List<String> output = this.inputValidator.validateInput("", "");
        assertNotNull(output);
        assertEquals(0, output.size());
    }

}