package com.kylegnally.beatbox;

/**
 * Created by kyleg on 1/31/2018.
 */

public class Sound {
    private String mAssetPath;
    private String mName;

    // by specifying Integer we make this a nullable value (for volume 0)
    private Integer mSoundId;

    public Sound(String assetPath) {
        mAssetPath = assetPath;

        // Split the filename in the path by the / character
        String[] components = assetPath.split("/");

        // take the name of the file before the / character
        String fileName = components[components.length - 1];

        // strip out the extension and assign only the title of the file to mName
        mName = fileName.replace(".wav", "");
    }

    public Integer getSoundId() {
        return mSoundId;
    }

    public void setSoundId(Integer soundId) {
        mSoundId = soundId;
    }

    public String getAssetPath() {
        return mAssetPath;
    }

    public String getName() {
        return mName;
    }
}
