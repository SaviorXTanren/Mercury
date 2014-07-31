package radirius.merc.tutorials;

import radirius.merc.audio.Audio;
import radirius.merc.framework.Core;
import radirius.merc.framework.Runner;
import radirius.merc.graphics.Graphics;
import radirius.merc.resource.Loader;

/**
 * @author wessles
 */

public class PlayingAudio extends Core {
    Runner runner = Runner.getInstance();
    
    public PlayingAudio() {
        super("Audible Thingies");
        runner.init(this, 500, 500);
        runner.run();
    }
    
    public static void main(String[] args) {
        new PlayingAudio();
    }
    
    Audio whack;
    
    public void init() {
        whack = Audio.getAudio(Audio.getWAVBuffer(Loader.streamFromClasspath("radirius/merc/tutorials/whack.wav")));
        // Twice as high pitched
        whack.setPitch(2f);
        // Half as loud
        whack.setVolume(0.5f);
        // No looping
        whack.setLooping(false);
        whack.play();
    }
    
    public void update(float delta) {
    }
    
    public void render(Graphics g) {
        
    }
    
    public void cleanup() {
    }
    
}