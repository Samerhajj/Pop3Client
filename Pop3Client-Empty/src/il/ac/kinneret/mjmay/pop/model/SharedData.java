package il.ac.kinneret.mjmay.pop.model;

public class SharedData {
    private static StringBuilder stringBuilder =  null;

    /**
     * Gets the Stringbuilder instance for sending back responses
     * @return The string builder instance
     */
    public static StringBuilder getSB()
    {
        if ( stringBuilder == null)
        {
            stringBuilder = new StringBuilder();
        }
        return stringBuilder;
    }

    /**
     * Clears the buffer
     */
    public static void clearSB()
    {
        stringBuilder = new StringBuilder();
    }
}
