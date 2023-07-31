package run.fleek.utils;

import com.google.common.base.Joiner;

public final class JoinUtil {
    public static final Joiner HYPHEN_JOINER = Joiner.on("-").skipNulls();
    public static final Joiner COMMA_JOINER = Joiner.on(",").skipNulls();
    public static final Joiner SPACE_JOINER = Joiner.on(" ").skipNulls();
    public static final Joiner SLASH_JOINER = Joiner.on("/").skipNulls();
    public static final Joiner COLON_JOINER = Joiner.on(": ").skipNulls();
    public static final Joiner NEW_LINE_JOINER = Joiner.on("\n").skipNulls();
}
