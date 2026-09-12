package janet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class JanetStorageRecoveryTest {
    @TempDir
    Path temporaryDirectory;

    @Test
    void malformedStorage_requiresResetBeforeCommandsAndRecoversAfterReset() throws IOException, JanetException {
        Path storageFile = temporaryDirectory.resolve("tasks.txt");
        Files.writeString(storageFile, "not a saved task");
        Janet janet = new Janet(new Storage(storageFile.toFile()));

        assertTrue(janet.isStorageRecoveryRequired());
        assertTrue(janet.getStorageRecoveryPrompt().contains("Tasks takes at least 3 positional arguments"));
        assertThrows(JanetException.class, () -> Janet.getResponse(janet, "list"));

        assertFalse(janet.resolveStorageRecovery("keep"));
        assertTrue(janet.isStorageRecoveryRequired());
        assertTrue(janet.resolveStorageRecovery(" reset "));

        assertFalse(janet.isStorageRecoveryRequired());
        assertEquals("", Files.readString(storageFile));
        assertEquals("No tasks listed!", Janet.getResponse(janet, "list").message());
    }
}
