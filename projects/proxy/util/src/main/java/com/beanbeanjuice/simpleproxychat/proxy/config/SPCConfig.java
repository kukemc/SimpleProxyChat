package com.beanbeanjuice.simpleproxychat.proxy.config;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class SPCConfig {

    private final File folder;
    private final String fileName;

    private YamlDocument yamlFile;
    private final ArrayList<Runnable> reloadFunctions;

    public SPCConfig(File folder, String fileName) {
        this.folder = folder;
        this.fileName = fileName;

        reloadFunctions = new ArrayList<>();
    }

    public void initialize() {
        try {
            yamlFile = loadConfig(fileName);
            yamlFile.update();
            yamlFile.save();
        } catch (IOException ignored) { }
    }

    public void addReloadListener(Runnable runnable) {
        reloadFunctions.add(runnable);
    }

    public void reload() {
        try {
            yamlFile.reload();
            reloadFunctions.forEach(Runnable::run);
        } catch (IOException ignored) { }
    }

    public YamlDocument getConfig() {
        return yamlFile;
    }

    private YamlDocument loadConfig(String fileName) throws IOException {
        return YamlDocument.create(
                new File(folder, fileName),
                Objects.requireNonNull(getClass().getResourceAsStream("/" + fileName)),
                GeneralSettings.DEFAULT,
                LoaderSettings.builder().setAutoUpdate(true).build(),
                DumperSettings.DEFAULT,
                UpdaterSettings.builder()
                        .setVersioning(new BasicVersioning("file-version"))
                        .setOptionSorting(UpdaterSettings.OptionSorting.SORT_BY_DEFAULTS)

                        // Relocations

                        .build()
        );
    }

}
