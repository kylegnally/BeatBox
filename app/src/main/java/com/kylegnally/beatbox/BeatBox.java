package com.kylegnally.beatbox;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kyleg on 1/31/2018.
 */

public class BeatBox {

    private static final String TAG = "BeatBox";
    private static final String SOUNDS_FOLDER = "sample_sounds";

    // AssetManager accesses the project assets. You can get an AssetManager
    // from any Context.
    private AssetManager mAssets;
    private List<Sound> mSounds = new ArrayList<>();

    // Takes a Context as a dependency and stashes it in mAssets
    public BeatBox(Context context) {
        mAssets = context.getAssets();

        // load the sound names
        loadSounds();
    }

    private void loadSounds() {

        String[] soundNames;
        try {

            // list the names in the sounds folder and store the list in soundNames
            soundNames = mAssets.list(SOUNDS_FOLDER);
            Log.i(TAG, "Found " + soundNames.length + " sounds");
        } catch (IOException ioe) {
            Log.e(TAG, "Could not list assets", ioe);
            return;
        }

        // Foreach to add each sound name to the ArrayList. The Sound constructor
        // strips out the slashes and extension from the name for us
        for (String fileName : soundNames) {
            String assetPath = SOUNDS_FOLDER + "/" + fileName;
            Sound sound = new Sound(assetPath);
            mSounds.add(sound);
        }
    }

    public List<Sound> getSounds() {
        return mSounds;
    }

}
