package com.kylegnally.beatbox;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
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
    private static final int MAX_SOUNDS = 5;

    // AssetManager accesses the project assets. You can get an AssetManager
    // from any Context.
    private AssetManager mAssets;
    private List<Sound> mSounds = new ArrayList<>();
    private SoundPool mSoundPool;

    // Takes a Context as a dependency and stashes it in mAssets
    public BeatBox(Context context) {
        mAssets = context.getAssets();

        // This old constructor is deprecated but we need it for compatibility
        // Required params:
        // int - maximum simultaneous sounds
        // int - audio stream integer (notification, call volume, music volume, etc)
        // int - quality (ignored?!?)
        mSoundPool = new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0);

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
            try {
                String assetPath = SOUNDS_FOLDER + "/" + fileName;
                Sound sound = new Sound(assetPath);
                load(sound);
                mSounds.add(sound);
            } catch (IOException ioe) {
                Log.e(TAG, "Could not load sound " + fileName, ioe);
            }
        }
    }

    private void load(Sound sound) throws IOException {
        // set an AssetFileDescriptor instance using the getAssetPath method of
        // the AssetManager instance
        AssetFileDescriptor afd = mAssets.openFd(sound.getAssetPath());

        // load the sound file and set an integer to its Id
        int soundId = mSoundPool.load(afd, 1);

        // set the Id of the sound using the integer we just set
        sound.setSoundId(soundId);
    }

    public void play(Sound sound) {
        Integer soundId = sound.getSoundId();
        if (soundId == null) {
            return;
        }
        // Play the sound!
        // Params:
        // soundId - Id number of the sound that we set in load()
        // leftVolume - the left speaker volume
        // rightVolume - the right speaker volume
        // priority - ignored
        // loop - whether the sound plays over again indefinitely (-1 to loop forever)
        // rate - the playback rate of the sound
        mSoundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void release() {

        // This unloads the sounds
        mSoundPool.release();
    }

    public List<Sound> getSounds() {
        return mSounds;
    }

}
