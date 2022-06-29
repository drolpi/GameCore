package net.bote.gamecore.internal.processor.plugin;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Tag;

import java.util.HashMap;
import java.util.Map;

final class PluginYamlBuilder {

    private static final Tag TAG_MAP = Tag.MAP;
    private static final DumperOptions.FlowStyle FLOW_STYLE = DumperOptions.FlowStyle.BLOCK;

    private final Yaml yaml = new Yaml();
    private final StringBuilder raw = new StringBuilder();
    private final Map<String, Object> data;

    PluginYamlBuilder(Map<String, Object> data) {
        this.data = data;
    }

    public PluginYamlBuilder append(String key) {
        if (this.data.containsKey(key)) {
            Map<String, Object> singleMapping = new HashMap<>();
            singleMapping.put(key, this.data.get(key));
            this.raw.append(this.yaml.dumpAs(singleMapping, TAG_MAP, FLOW_STYLE));
        }
        return this;
    }

    public String getRawYamlContents() {
        return this.raw.toString();
    }

}
