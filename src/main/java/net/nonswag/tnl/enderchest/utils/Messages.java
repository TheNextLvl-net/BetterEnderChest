package net.nonswag.tnl.enderchest.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.nonswag.core.api.annotation.FieldsAreNonnullByDefault;
import net.nonswag.core.api.message.Message;
import net.nonswag.core.api.message.key.SystemMessageKey;

@FieldsAreNonnullByDefault
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Messages {
    public static final SystemMessageKey ENDER_CHEST_TITLE = new SystemMessageKey("ender-chest-title").register();

    public static void init() {
        Message.ROOT.setDefault(ENDER_CHEST_TITLE, "Ender Chest (%rows%)");
    }
}
