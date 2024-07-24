package info.preva1l.fadsb.island;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public record IslandType(
        String templateWorldName,
        Environment environment
) {
    public static final IslandType DEFAULT = new IslandType("defaultIsland", Environment.OVERWORLD);
}
