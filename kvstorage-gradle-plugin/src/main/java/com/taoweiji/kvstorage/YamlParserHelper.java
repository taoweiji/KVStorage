package com.taoweiji.kvstorage;

import com.alibaba.fastjson.JSONObject;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Set;

public class YamlParserHelper {
    public static JSONObject parser(String filePath) throws FileNotFoundException {
        Yaml yaml = new Yaml();
        Object result = yaml.load(new FileInputStream(filePath));
        return parse(result);
    }

    public static JSONObject parse(Object input) {
        if (!(input instanceof Map)) {
            return null;
        }
        Map<String, Object> nodes = (Map<String, Object>) input;
        JSONObject configs = new JSONObject();
        Set<String> keys = nodes.keySet();
        for (String key : keys) {
            if (key.contains("(") && key.contains(")")) {
                String name = key.substring(0, key.indexOf("("));
                String argStr = key.substring(key.indexOf("(") + 1, key.indexOf(")"));
                String[] args = argStr.split(",");
                String type = args[0];
                JSONObject item = new JSONObject();
//                item.put("name", name);
                item.put("type", type);
                item.put("def", nodes.get(key));
                if (argStr.contains("encrypt")) {
                    item.put("encrypt", true);
                }
                configs.put(name, item);
            } else {
                configs.put(":" + key, parse(nodes.get(key)));
            }
        }
        return configs;
    }


}
