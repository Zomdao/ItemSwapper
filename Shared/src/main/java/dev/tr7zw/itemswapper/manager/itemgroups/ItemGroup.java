package dev.tr7zw.itemswapper.manager.itemgroups;

import java.util.Collections;
import java.util.Set;

import net.minecraft.resources.ResourceLocation;

/**
 * Lombok afaik doesn't work for Loom, so using spark to generate the builder
 * 
 * @author tr7zw
 *
 */
public class ItemGroup {

    private final ResourceLocation id;
    private final int priority;
    private final boolean disableAutoLink;
    private final ResourceLocation fallbackLink;
    private final ResourceLocation forcedLink;
    private final ItemEntry[] items;
    private final Set<ItemEntry> openOnlyItems;
    private final Set<ItemEntry> ignoreItems;

    private ItemGroup(Builder builder) {
        this.id = builder.id;
        this.priority = builder.priority;
        this.disableAutoLink = builder.disableAutoLink;
        this.fallbackLink = builder.fallbackLink;
        this.forcedLink = builder.forcedLink;
        this.items = builder.items;
        this.openOnlyItems = builder.openOnlyItems;
        this.ignoreItems = builder.ignoreItems;
    }

    public ResourceLocation getId() {
        return id;
    }

    public int getPriority() {
        return priority;
    }

    public ResourceLocation getFallbackLink() {
        return fallbackLink;
    }

    public ResourceLocation getForcedLink() {
        return forcedLink;
    }

    public ItemEntry[] getItems() {
        return items;
    }

    public Set<ItemEntry> getOpenOnlyItems() {
        return openOnlyItems;
    }

    public Set<ItemEntry> getIgnoreItems() {
        return ignoreItems;
    }
    
    public boolean autoLinkDisabled() {
        return disableAutoLink;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private ResourceLocation id;
        private int priority;
        private boolean disableAutoLink;
        private ResourceLocation fallbackLink;
        private ResourceLocation forcedLink;
        private ItemEntry[] items;
        private Set<ItemEntry> openOnlyItems = Collections.emptySet();
        private Set<ItemEntry> ignoreItems = Collections.emptySet();

        private Builder() {
        }

        public Builder withId(ResourceLocation id) {
            this.id = id;
            return this;
        }

        public Builder withPriority(int priority) {
            this.priority = priority;
            return this;
        }

        public Builder withDisableAutoLink(boolean disableAutoLink) {
            this.disableAutoLink = disableAutoLink;
            return this;
        }

        public Builder withFallbackLink(ResourceLocation fallbackLink) {
            this.fallbackLink = fallbackLink;
            return this;
        }

        public Builder withForcedLink(ResourceLocation forcedLink) {
            this.forcedLink = forcedLink;
            return this;
        }

        public Builder withItems(ItemEntry[] items) {
            this.items = items;
            return this;
        }

        public Builder withOpenOnlyItems(Set<ItemEntry> openOnlyItems) {
            this.openOnlyItems = openOnlyItems;
            return this;
        }

        public Builder withIgnoreItems(Set<ItemEntry> ignoreItems) {
            this.ignoreItems = ignoreItems;
            return this;
        }

        public ItemGroup build() {
            return new ItemGroup(this);
        }
    }
    
    
    
}
