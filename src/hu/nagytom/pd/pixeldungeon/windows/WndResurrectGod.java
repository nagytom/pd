package hu.nagytom.pd.pixeldungeon.windows;

import hu.nagytom.pd.noosa.Game;
import hu.nagytom.pd.pixeldungeon.actors.hero.Hero;
import hu.nagytom.pd.pixeldungeon.scenes.InterlevelScene;

public class WndResurrectGod extends WndOptions {

    private static final String TXT_TITLE   = "You died";
    private static final String TXT_MESSAGE = "Do you want to resurrect?";
    private static final String TXT_YES     = "Yes, I will fight!";
    private static final String TXT_NO      = "No, I give up";

    private Object causeOfDeath;

    public WndResurrectGod(Object causeOfDeathArg) {
        super(TXT_TITLE, TXT_MESSAGE, TXT_YES, TXT_NO);
        this.causeOfDeath = causeOfDeathArg;
    }

    @Override
    protected void onSelect(int index) {
        if (index == 0) {
            InterlevelScene.mode = InterlevelScene.Mode.RESURRECT;
            Game.switchScene(InterlevelScene.class);
        } else {
            Hero.reallyDie(causeOfDeath);
        }
    }

    @Override
    public void onBackPressed() {
        // must choose a button
    }

}
