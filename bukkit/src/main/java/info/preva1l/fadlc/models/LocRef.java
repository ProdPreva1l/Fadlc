package info.preva1l.fadlc.models;

import lombok.Getter;

@Getter
public class LocRef implements ILocRef {
    private final int x;
    private final int y;
    private final int z;

    public LocRef(int x, int y, int z) {
        super();
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
