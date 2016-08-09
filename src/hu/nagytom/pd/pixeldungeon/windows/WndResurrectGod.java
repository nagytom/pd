package hu.nagytom.pd.pixeldungeon.windows;

import hu.nagytom.pd.noosa.BitmapTextMultiline;
import hu.nagytom.pd.noosa.Game;
import hu.nagytom.pd.pixeldungeon.actors.hero.Hero;
import hu.nagytom.pd.pixeldungeon.scenes.InterlevelScene;
import hu.nagytom.pd.pixeldungeon.scenes.PixelScene;
import hu.nagytom.pd.pixeldungeon.ui.RedButton;
import hu.nagytom.pd.pixeldungeon.ui.Window;

public class WndResurrectGod extends Window {

    private static final String TXT_MESSAGE = "You died, but your spirit is powerful enough to resurrect.";
    private static final String TXT_YES     = "I will fight!";
    private static final String TXT_NO      = "I give up";

    private static final int WIDTH      = 120;
    private static final int BTN_HEIGHT = 20;
    private static final float GAP      = 2;

    private Object causeOfDeath;

    public WndResurrectGod(Object causeOfDeathArg) {
        this.causeOfDeath = causeOfDeathArg;

        BitmapTextMultiline message = PixelScene.createMultiline(TXT_MESSAGE, 6);
        message.maxWidth = WIDTH;
        message.measure();
        message.y = 0;
        add(message);

        RedButton btnYes = new RedButton(TXT_YES) {
            @Override
            protected void onClick() {
                hide();
                InterlevelScene.mode = InterlevelScene.Mode.RESURRECT;
                Game.switchScene(InterlevelScene.class);
            }
        };
        btnYes.setRect(0, message.y + message.height() + GAP, WIDTH, BTN_HEIGHT);
        add(btnYes);

        RedButton btnNo = new RedButton(TXT_NO) {
            @Override
            protected void onClick() {
                hide();
                Hero.reallyDie(causeOfDeath);
            }
        };
        btnNo.setRect(0, btnYes.bottom() + GAP, WIDTH, BTN_HEIGHT);
        add(btnNo);

        resize(WIDTH, (int) btnNo.bottom());
    }

    @Override
    public void onBackPressed() {

    }

}
