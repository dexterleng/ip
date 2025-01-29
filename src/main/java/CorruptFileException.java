public class CorruptFileException extends Exception {
    public CorruptFileException() {
        super("Save file is corrupt.");
    }
}