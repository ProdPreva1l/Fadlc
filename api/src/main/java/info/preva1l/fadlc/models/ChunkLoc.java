package info.preva1l.fadlc.models;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChunkLoc {
    @Expose
    private final int x;
    @Expose
    private final int z;
    @Expose
    private final String world;
    @Expose
    private final String server;
}
