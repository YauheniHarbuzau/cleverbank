package ru.clevertec.cleverbank.util.yamlread;

import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import ru.clevertec.cleverbank.util.yamlread.entity.Properties;

/**
 * Утилитарный класс для чтения конфигурационного yaml-файла
 *
 * @see Properties
 */
public class YamlReadUtil {

    public static Properties readYaml() {
        var inputStream = Properties.class.getClassLoader().getResourceAsStream("application.yaml");
        var yaml = new Yaml(new Constructor(Properties.class, new LoaderOptions()));
        return yaml.load(inputStream);
    }
}
