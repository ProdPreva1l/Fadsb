package info.preva1l.fadsb.island;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class IslandType {
    private static final IslandType DEFAULT = new IslandType("defaultIsland", Environment.OVERWORLD);

    private final String templateWorldName;
    private final Environment environment;
}
