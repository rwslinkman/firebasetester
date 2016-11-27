package nl.rwslinkman.firebasetester.gui;

/**
 * @author Rick Slinkman
 */
public interface RequestBodyParser
{
    String getEmpty();
    String parseRequestBody(String rawInput);
}
