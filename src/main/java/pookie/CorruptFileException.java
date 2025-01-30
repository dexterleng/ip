package pookie;

/**
 * Thrown to indicate that the saved task file is corrupted or incorrectly formatted.
 * This exception is used to signal errors during task file loading in the {@code Storage} class.
 */
public class CorruptFileException extends Exception {

    /**
     * Constructs a CorruptFileException with a default error message.
     */
    public CorruptFileException() {
        super("Save file is corrupt.");
    }
}